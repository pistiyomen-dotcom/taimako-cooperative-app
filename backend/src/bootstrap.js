const express=require("express");
const bcrypt=require("bcryptjs");
const crypto=require("crypto");
const rateLimit=require("express-rate-limit");
module.exports=function firstAdmin(pool){
  const router=express.Router();
  router.post("/first-admin",rateLimit({windowMs:15*60*1000,max:5,standardHeaders:"draft-7",legacyHeaders:false}),async(req,res,next)=>{
    try{
      const expected=process.env.BOOTSTRAP_SECRET;
      const supplied=req.get("x-bootstrap-secret");
      if(!expected||!supplied||supplied.length!==expected.length||
        !crypto.timingSafeEqual(Buffer.from(supplied),Buffer.from(expected)))
        return res.status(404).json({error:"Not found"});
      const {fullName,username,password}=req.body||{};
      if(typeof fullName!=="string"||fullName.trim().length<2||fullName.length>120||
        typeof username!=="string"||!/^[A-Za-z][A-Za-z0-9_-]{3,31}$/.test(username)||
        typeof password!=="string"||password.length<12||password.length>128)
        return res.status(400).json({error:"Invalid administrator details"});
      const db=await pool.connect();
      try{
        await db.query("BEGIN");
        await db.query("SELECT pg_advisory_xact_lock(8842901)");
        const found=await db.query("SELECT 1 FROM accounts WHERE role='ADMIN' LIMIT 1");
        if(found.rowCount){await db.query("ROLLBACK");return res.status(409).json({error:"Administrator already exists"});}
        const hash=await bcrypt.hash(password,12);
        const inserted=await db.query(
          "INSERT INTO accounts(full_name,username,credential_hash,role,must_change_credential) VALUES($1,$2,$3,'ADMIN',TRUE) RETURNING id",
          [fullName.trim(),username,hash]);
        for(const permission of ["CREATE_MEMBER","CREDIT_CASH","APPROVALS","MEMBERS","CREATE_ADMIN","ADMIN_PERMISSIONS"])
          await db.query("INSERT INTO admin_permissions(account_id,permission) VALUES($1,$2)",
            [inserted.rows[0].id,permission]);
        await db.query("INSERT INTO security_events(account_id,event_type) VALUES($1,'FIRST_ADMIN_CREATED')",[inserted.rows[0].id]);
        await db.query("COMMIT");
        res.status(201).json({ok:true,requiresPasswordChange:true});
      }catch(e){await db.query("ROLLBACK");throw e;}
      finally{db.release();}
    }catch(e){next(e);}
  });
  return router;
};
