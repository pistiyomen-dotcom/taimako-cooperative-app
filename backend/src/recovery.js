const express=require("express");
const bcrypt=require("bcryptjs");
const crypto=require("crypto");
const rateLimit=require("express-rate-limit");
module.exports=function(pool){
 const router=express.Router();
 router.post("/diagnose",rateLimit({windowMs:15*60*1000,max:5,standardHeaders:"draft-7",legacyHeaders:false}),async(req,res,next)=>{
  try{
   const expected=process.env.ADMIN_RECOVERY_SECRET;
   const provided=req.body&&req.body.recoverySecret;
   if(typeof expected!=="string"||expected.length<40||typeof provided!=="string"||
      !crypto.timingSafeEqual(crypto.createHash("sha256").update(expected).digest(),crypto.createHash("sha256").update(provided).digest()))
     return res.status(403).json({error:"Recovery secret unavailable or invalid"});
   const q=await pool.query("SELECT id,active FROM accounts WHERE username=$1 AND role='ADMIN'",["Shugaba"]);
   if(!q.rowCount)return res.json({status:"SHUGABA_NOT_FOUND"});
   if(!q.rows[0].active)return res.json({status:"SHUGABA_INACTIVE"});
   const used=await pool.query("SELECT 1 FROM security_events WHERE account_id=$1 AND event_type='FIRST_ADMIN_RECOVERY_USED' LIMIT 1",[q.rows[0].id]);
   return res.json({status:used.rowCount?"RECOVERY_ALREADY_USED":"SHUGABA_READY_FOR_RECOVERY"});
  }catch(e){next(e)}
 });
 router.post("/shugaba",rateLimit({windowMs:15*60*1000,max:5,standardHeaders:"draft-7",legacyHeaders:false}),async(req,res,next)=>{
  const expected=process.env.ADMIN_RECOVERY_SECRET;
  const provided=req.body&&req.body.recoverySecret;
  if(typeof expected!=="string"||expected.length<40||typeof provided!=="string"||
   !crypto.timingSafeEqual(crypto.createHash("sha256").update(expected).digest(),crypto.createHash("sha256").update(provided).digest()))
   return res.status(403).json({error:"Recovery unavailable or invalid"});
  const password=req.body.newPassword;
  if(typeof password!=="string"||password.length<12||password.length>128)
   return res.status(400).json({error:"Password must be 12 to 128 characters"});
  const db=await pool.connect();
  try{
   await db.query("BEGIN");
   await db.query("SELECT pg_advisory_xact_lock(8842902)");
   const account=await db.query("SELECT id FROM accounts WHERE username=$1 AND role='ADMIN' AND active=TRUE FOR UPDATE",["Shugaba"]);
   if(account.rowCount!==1){await db.query("ROLLBACK");return res.status(404).json({error:"Account not found"});}
   const id=account.rows[0].id;
   const used=await db.query("SELECT 1 FROM security_events WHERE account_id=$1 AND event_type='FIRST_ADMIN_RECOVERY_USED' LIMIT 1",[id]);
   if(used.rowCount){await db.query("ROLLBACK");return res.status(409).json({error:"Recovery already used"});}
   const hash=await bcrypt.hash(password,12);
   await db.query("UPDATE accounts SET credential_hash=$1,must_change_credential=TRUE,failed_logins=0,locked_until=NULL WHERE id=$2",[hash,id]);
   await db.query("INSERT INTO security_events(account_id,event_type) VALUES($1,'FIRST_ADMIN_RECOVERY_USED')",[id]);
   await db.query("COMMIT");
   return res.json({ok:true,loginRequiresPasswordChange:true});
  }catch(e){await db.query("ROLLBACK");next(e)}
  finally{db.release()}
 });
 return router;
};
