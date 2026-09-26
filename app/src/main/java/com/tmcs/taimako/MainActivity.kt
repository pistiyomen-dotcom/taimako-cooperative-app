package com.tmcs.taimako

import android.os.Bundle
import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.background
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { TaimakoApp() }
    }
}

@Composable
fun TaimakoApp() {
    val tmcsColors = lightColorScheme(
        primary = Color(0xFF146B3A),
        onPrimary = Color.White,
        secondary = Color(0xFFD4AF37),
        background = Color.White,
        surface = Color.White
    )
    MaterialTheme(colorScheme = tmcsColors) {
        var page by remember { mutableStateOf("HOME") }
        Surface(Modifier.fillMaxSize()) {
            when (page) {
                "HOME" -> HomePage { page = it }
                "SAVINGS" -> SavingsMenuPage(open = { page = it }, back = { page = "HOME" })
                "REGULAR SAVINGS", "CONSTANT SAVINGS", "TARGET SAVINGS", "WELFARE SAVINGS", "FLEXIBLE SAVINGS" -> SavingsInfoPage(page) { page = "SAVINGS" }
                "MEMBERSHIP" -> MembershipPage(open = { page = it }, back = { page = "HOME" })
                "FLEXIBLE" -> FlexibleEntryPage(open = { page = it }, back = { page = "HOME" })
                "FLEXIBLE LOGIN" -> FlexibleLoginPage(login = { page = "FLEXIBLE DASHBOARD" }, back = { page = "FLEXIBLE" })
                "FLEXIBLE REGISTER" -> RegisterPage(contact = { page = "CONTACT US" }, back = { page = "FLEXIBLE" })
                "FLEXIBLE INFO" -> SavingsInfoPage("FLEXIBLE SAVINGS") { page = "FLEXIBLE" }
                "FLEXIBLE DASHBOARD" -> FlexibleDashboardPage(open = { page = it }, back = { page = "FLEXIBLE" })
                "FLEXIBLE SAVE" -> FlexibleSavePage { page = "FLEXIBLE DASHBOARD" }
                "FLEXIBLE TRANSFER" -> FlexibleTransferPage { page = "FLEXIBLE DASHBOARD" }
                "FLEXIBLE WITHDRAW" -> FlexibleWithdrawPage { page = "FLEXIBLE DASHBOARD" }
                "LOGIN" -> LoginPage(open = { page = it }, back = { page = "MEMBERSHIP" })
                "REGISTER" -> RegisterPage(contact = { page = "CONTACT US" }, back = { page = "MEMBERSHIP" })
                "ADMIN DASHBOARD" -> AdminDashboardPage(open = { page = it }, back = { page = "LOGIN" })
                "CREATE MEMBER" -> CreateMemberPage { page = "ADMIN DASHBOARD" }
                "CREDIT CASH" -> CreditCashPage { page = "ADMIN DASHBOARD" }
                "APPROVALS" -> ApprovalsPage { page = "ADMIN DASHBOARD" }
                "MEMBERS" -> MembersPage { page = "ADMIN DASHBOARD" }
                "CREATE ADMIN" -> CreateAdminPage { page = "ADMIN DASHBOARD" }
                "ADMIN PERMISSIONS" -> AdminPermissionsPage { page = "ADMIN DASHBOARD" }
                "CHANGE MEMBER PIN" -> ChangePinPage("CHANGE TEMPORARY PIN", { page = "MEMBER DASHBOARD" }, { page = "LOGIN" })
                "CHANGE FLEXIBLE PIN" -> ChangePinPage("CHANGE TEMPORARY PIN", { page = "FLEXIBLE DASHBOARD" }, { page = "FLEXIBLE LOGIN" })
                "MEMBER DASHBOARD" -> MemberDashboardPage(open = { page = it }, back = { page = "MEMBERSHIP" })
                "PAY" -> PayPage { page = "MEMBER DASHBOARD" }
                "TRANSACTION HISTORY" -> TransactionHistoryPage { page = "MEMBER DASHBOARD" }
                "WITHDRAW" -> WithdrawPage { page = "MEMBER DASHBOARD" }
                "APPLY FOR LOAN" -> LoanApplicationPage { page = "MEMBER DASHBOARD" }
                else -> InfoPage(page) { page = "HOME" }
            }
        }
    }
}

@Composable
fun HomePage(open: (String) -> Unit) {
    val context = LocalContext.current
    val menus = listOf("SAVINGS","LOAN","INVESTMENT","AGRICULTURE","FLEXIBLE","MEMBERSHIP","BYE-LAW","ABOUT US","CONTACT US")
    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(30.dp))
        Text("TAIMAKO MULTIPURPOSE COOPERATIVE SOCIETY LTD", style=MaterialTheme.typography.headlineSmall, fontWeight=FontWeight.Bold, color=Color(0xFF146B3A))
        Spacer(Modifier.height(14.dp))
        Text("Welcome to TMCS LTD", style=MaterialTheme.typography.titleLarge, fontWeight=FontWeight.Bold, color=Color(0xFFD4AF37))
        Text("A cooperative movement for achieving financial independence.")
        Spacer(Modifier.height(12.dp))
        OutlinedButton(onClick={context.startActivity(Intent(context, AdminTestActivity::class.java))}) { Text("TEST LIVE ADMIN LOGIN") }
        Spacer(Modifier.height(24.dp))
        menus.chunked(2).forEach { row ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement=Arrangement.spacedBy(12.dp)) {
                row.forEach { item ->
                    Button(onClick={open(item)}, modifier=Modifier.weight(1f).height(64.dp), colors=ButtonDefaults.buttonColors(containerColor=Color(0xFF146B3A), contentColor=Color.White)) { Text(item) }
                }
                if(row.size==1) Spacer(Modifier.weight(1f))
            }
            Spacer(Modifier.height(12.dp))
        }
    }
}

@Composable
fun InfoPage(page: String, back: () -> Unit) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp)) {
        Spacer(Modifier.height(28.dp))
        TextButton(onClick=back) { Text("← BACK") }
        Text(page, style=MaterialTheme.typography.headlineMedium, fontWeight=FontWeight.Bold, color=Color(0xFFD4AF37))
        Spacer(Modifier.height(16.dp))
        when(page) {
            "LOAN" -> Text("LOAN FOR NON-COOPERATIVE MEMBERS\n\nMembers of the community can apply for a loan. A non-member is required to provide a guarantor who is a member of TMCS LTD, or deposit collateral.\n\nLoan duration: 30 days\nInterest charge: 8%\n\nAPPLY HERE — Contact Us")
            "INVESTMENT" -> Text("Members and non-members can participate in the TMCS LTD Investment Plan.\n\nMinimum investment: ₦10,000\nMinimum duration: 6 months\nReturn on Investment: 1% monthly, unconditional.")
            "AGRICULTURE" -> Text("COMING SOON", style=MaterialTheme.typography.headlineSmall, fontWeight=FontWeight.Bold)
            "FLEXIBLE" -> Text("Suitable for petty traders, students and the general public.\n\n• Save any amount any day.\n• Minimum duration: 30 days.\n• No membership registration; purchase of record card only.\n• No monthly dividend shares.\n• Withdraw 100% after 30 days.\n• One free withdrawal every 30 days.\n• 20% charge for withdrawal before 30 days.")
            "BYE-LAW" -> ByeLawPage()
            "ABOUT US" -> AboutPage()
            "CONTACT US" -> ContactPage()
        }
    }
}

@Composable
fun SavingsMenuPage(open: (String) -> Unit, back: () -> Unit) {
    val plans = listOf("REGULAR SAVINGS","CONSTANT SAVINGS","TARGET SAVINGS","WELFARE SAVINGS","FLEXIBLE SAVINGS")
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp), horizontalAlignment=Alignment.CenterHorizontally) {
        Spacer(Modifier.height(28.dp))
        TextButton(onClick=back, modifier=Modifier.align(Alignment.Start)) { Text("← BACK") }
        Text("SAVINGS", style=MaterialTheme.typography.headlineLarge, fontWeight=FontWeight.Bold, color=Color(0xFFD4AF37), textAlign=TextAlign.Center, modifier=Modifier.fillMaxWidth())
        Spacer(Modifier.height(6.dp))
        Text("Select a savings type to read its complete information.", color=Color.Gray, textAlign=TextAlign.Center)
        Spacer(Modifier.height(18.dp))
        plans.forEach { plan ->
            Button(onClick={open(plan)}, modifier=Modifier.fillMaxWidth().height(58.dp), colors=ButtonDefaults.buttonColors(containerColor=Color(0xFF146B3A), contentColor=Color.White)) {
                Text(plan.removeSuffix(" SAVINGS"), fontWeight=FontWeight.Bold)
            }
            Spacer(Modifier.height(10.dp))
        }
    }
}

@Composable
fun SavingsInfoPage(page: String, back: () -> Unit) {
    val title = page.removeSuffix(" SAVINGS")
    val info = when(page) {
        "REGULAR SAVINGS" -> """General savings plan running from November to October.

• Primarily for member savings.
• Minimum ₦5,000 per share; reviewable at the AGM.
• Monthly savings are not compulsory and there is no penalty for a missed month.
• A member is entitled to monthly dividend shares when contribution reaches at least one minimum share.
• Loan entitlement is up to 90% of savings at 5% interest per 30 days.
• Total savings are disbursed after the cooperative year ends in October.
• Dividend is paid at the AGM.
• Withdrawal before year end requires at least 30 days' notice and attracts a 20% charge."""
        "CONSTANT SAVINGS" -> """A long-term savings plan separate from Regular Savings.

• Minimum ₦5,000 per share; reviewable by management.
• Monthly savings are compulsory.
• Failure to save the minimum share, or any short-saved amount in a month, attracts a 10% charge/penalty which must be paid or deducted.
• Monthly dividend shares apply when contribution reaches the minimum share.
• Loan entitlement is up to 90% of savings at 3% interest per 30 days.
• Savings are not disbursed at the end of the cooperative year; completed-year savings roll into the following year until the savings duration is completed.
• A minimum balance of ₦300,000 is required after 5 years before withdrawal.
• Withdrawal under 5 years without achieving the ₦300,000 minimum balance attracts a 20% charge, and the minimum ₦300,000 is maintained until 5 years.
• If ₦300,000 is not achieved after 5 years, a 5% charge applies to the short-saved amount before disbursement.
• Excess above ₦300,000 may be withdrawn if the minimum balance is achieved before 5 years.
• Withdrawal of excess without charge requires at least 30 days' notice."""
        "TARGET SAVINGS" -> """A savings plan suitable for project plans.

• The member decides a unique savings target.
• Weekly or monthly minimum savings are calculated from the chosen target.
• Monthly saving of the monthly target is compulsory.
• Failure to pay the monthly minimum, or a short-saved amount, attracts a 10% charge.
• Monthly dividend shares apply when contribution reaches the minimum share.
• A reward/support equal to 1% of a fulfilled monthly target applies.
• Minimum target duration is 24 weeks / 6 months.
• Withdrawal before the target period ends requires at least 30 days' notice and attracts a 20% charge.
• For a target duration of up to one year, loan entitlement is up to 90% of savings at 3% interest per 30 days."""
        "WELFARE SAVINGS" -> """A savings plan suitable for education and retirement.

• Contributions are flexible: save any amount on any day.
• Minimum savings duration is one year.
• After the savings duration, total savings are spread across a member-chosen welfare/disbursement duration of at least one year.
• Monthly welfare payment = monthly share + 1% of the current/opening balance, continuing until the end of the selected duration.
• Monthly dividend shares apply during the savings period.
• There is no end-of-cooperative-year disbursement.
• Withdrawal during the savings or welfare period attracts a 20% charge and requires at least 30 days' notice.

Example: Starting balance ₦240,000 over 12 months; base monthly share ₦20,000.
Month 1: ₦22,400; balance ₦220,000
Month 2: ₦22,200; balance ₦200,000
Month 3: ₦22,000; balance ₦180,000
Month 4: ₦21,800; balance ₦160,000
Month 5: ₦21,600; balance ₦140,000
Month 6: ₦21,400; balance ₦120,000
Month 7: ₦21,200; balance ₦100,000
Month 8: ₦21,000; balance ₦80,000
Month 9: ₦20,800; balance ₦60,000
Month 10: ₦20,600; balance ₦40,000
Month 11: ₦20,400; balance ₦20,000
Month 12: ₦20,200; balance ₦0"""
        else -> """A flexible savings plan suitable for petty traders, students and the general public.

• Save any amount on any day.
• Minimum savings duration is 30 days.
• No cooperative membership registration is required; only a record card is purchased.
• Flexible savers are not entitled to monthly dividend shares.
• 100% withdrawal is available after 30 days.
• One free withdrawal is allowed in every 30-day period.
• Withdrawal before 30 days attracts a 20% charge.

Registered cooperative members can also opt into Flexible Savings through their member account."""
    }
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp)) {
        Spacer(Modifier.height(28.dp))
        TextButton(onClick=back) { Text("← BACK") }
        Text(title, style=MaterialTheme.typography.headlineLarge, fontWeight=FontWeight.Bold, color=Color(0xFFD4AF37))
        Spacer(Modifier.height(16.dp))
        Text(info)
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
fun FlexibleEntryPage(open:(String)->Unit,back:()->Unit){Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),horizontalAlignment=Alignment.CenterHorizontally){Spacer(Modifier.height(28.dp));TextButton(onClick=back,modifier=Modifier.align(Alignment.Start)){Text("← BACK")};Text("FLEXIBLE",style=MaterialTheme.typography.headlineLarge,fontWeight=FontWeight.Bold,color=Color(0xFFD4AF37));Spacer(Modifier.height(20.dp));listOf("LOGIN" to "FLEXIBLE LOGIN","REGISTER" to "FLEXIBLE REGISTER","FLEXIBLE INFO" to "FLEXIBLE INFO").forEach{(label,dest)->Button(onClick={open(dest)},modifier=Modifier.fillMaxWidth().height(58.dp),colors=ButtonDefaults.buttonColors(containerColor=Color(0xFF146B3A),contentColor=Color.White)){Text(label,fontWeight=FontWeight.Bold)};Spacer(Modifier.height(10.dp))}}}

@Composable
fun FlexibleLoginPage(login:()->Unit,back:()->Unit){var username by remember{mutableStateOf("")};var pin by remember{mutableStateOf("")};val valid=username.length==4&&username.startsWith("F")&&username.drop(1).all{it.isDigit()}&&pin.length==4&&pin.all{it.isDigit()};Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp)){Spacer(Modifier.height(28.dp));TextButton(onClick=back){Text("← BACK")};Text("FLEXIBLE LOGIN",style=MaterialTheme.typography.headlineMedium,fontWeight=FontWeight.Bold,color=Color(0xFFD4AF37));Spacer(Modifier.height(16.dp));OutlinedTextField(username,{v->val up=v.uppercase();if(up.length<=4&&(up.isEmpty()||(up.startsWith("F")&&up.drop(1).all{it.isDigit()})))username=up},label={Text("Username (F + 3 digits)")},modifier=Modifier.fillMaxWidth());Spacer(Modifier.height(10.dp));OutlinedTextField(pin,{v->if(v.length<=4&&v.all{it.isDigit()})pin=v},label={Text("4-digit PIN")},modifier=Modifier.fillMaxWidth());Spacer(Modifier.height(16.dp));Button(onClick=login,enabled=valid,modifier=Modifier.fillMaxWidth(),colors=ButtonDefaults.buttonColors(containerColor=Color(0xFFD4AF37),contentColor=Color.Black)){Text("LOGIN",fontWeight=FontWeight.Bold)};Spacer(Modifier.height(12.dp));Text("Interface test only. Real username and PIN verification will be connected to the backend. The Admin-generated PIN is temporary and must be changed on first successful login before dashboard access.",color=Color.Gray)}}

@Composable
fun FlexibleDashboardPage(open:(String)->Unit,back:()->Unit){Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),horizontalAlignment=Alignment.CenterHorizontally){Spacer(Modifier.height(28.dp));TextButton(onClick=back,modifier=Modifier.align(Alignment.Start)){Text("← BACK")};Card(Modifier.fillMaxWidth().height(72.dp)){Box(Modifier.fillMaxSize(),contentAlignment=Alignment.Center){Text("FLEXIBLE DASHBOARD",style=MaterialTheme.typography.headlineMedium,fontWeight=FontWeight.Bold,color=Color(0xFFD4AF37),textAlign=TextAlign.Center)}};Spacer(Modifier.height(12.dp));Text("Flexible Saver Full Name",fontWeight=FontWeight.Bold,style=MaterialTheme.typography.titleLarge);Text("Username: F001",fontWeight=FontWeight.Bold);Spacer(Modifier.height(16.dp));Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.spacedBy(10.dp)){listOf("TOTAL SAVINGS" to "₦0","START DATE" to "—").forEach{(label,value)->Card(Modifier.weight(1f).height(100.dp)){Column(Modifier.fillMaxSize().padding(8.dp),verticalArrangement=Arrangement.Center,horizontalAlignment=Alignment.CenterHorizontally){Text(label,fontWeight=FontWeight.Bold,textAlign=TextAlign.Center);Spacer(Modifier.height(6.dp));Text(value,fontWeight=FontWeight.Bold,color=Color(0xFF146B3A),style=MaterialTheme.typography.titleLarge)}}}};Spacer(Modifier.height(18.dp));Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.spacedBy(10.dp)){Button(onClick={open("FLEXIBLE SAVE")},modifier=Modifier.weight(1f),colors=ButtonDefaults.buttonColors(containerColor=Color(0xFF146B3A),contentColor=Color.White)){Text("SAVE",fontWeight=FontWeight.Bold)};Button(onClick={open("FLEXIBLE WITHDRAW")},modifier=Modifier.weight(1f),colors=ButtonDefaults.buttonColors(containerColor=Color(0xFFD4AF37),contentColor=Color.Black)){Text("WITHDRAW",fontWeight=FontWeight.Bold)}};Spacer(Modifier.height(10.dp));Button(onClick={open("FLEXIBLE TRANSFER")},modifier=Modifier.fillMaxWidth(),colors=ButtonDefaults.buttonColors(containerColor=Color(0xFF146B3A),contentColor=Color.White)){Text("TRANSFER",fontWeight=FontWeight.Bold)};Spacer(Modifier.height(12.dp));Text("Stage preview uses sample saver details. Live values will come from the backend.",color=Color.Gray,textAlign=TextAlign.Center)}}

@Composable
fun FlexibleSavePage(back:()->Unit){var amount by remember{mutableStateOf("")};var receipt by remember{mutableStateOf<String?>(null)};val launcher=rememberLauncherForActivityResult(ActivityResultContracts.GetContent()){uri->receipt=uri?.toString()};Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),horizontalAlignment=Alignment.CenterHorizontally){Spacer(Modifier.height(28.dp));TextButton(onClick=back,modifier=Modifier.align(Alignment.Start)){Text("← BACK")};Text("SAVE",style=MaterialTheme.typography.headlineLarge,fontWeight=FontWeight.Bold,color=Color(0xFFD4AF37));Spacer(Modifier.height(16.dp));Card(Modifier.fillMaxWidth()){Column(Modifier.padding(16.dp),horizontalAlignment=Alignment.CenterHorizontally){Text("BANK TRANSFER DETAILS",fontWeight=FontWeight.Bold,style=MaterialTheme.typography.titleLarge);Spacer(Modifier.height(10.dp));Text("FCMB",fontWeight=FontWeight.Bold);Text("1027050172",fontWeight=FontWeight.Bold,style=MaterialTheme.typography.titleLarge);Text("TAIMAKO MULTIPURPOSE COOPERATIVE SOCIETY LTD",fontWeight=FontWeight.Bold,textAlign=TextAlign.Center)}};Spacer(Modifier.height(16.dp));OutlinedTextField(amount,{v->if(v.all{it.isDigit()||it=='.'})amount=v},label={Text("Amount")},modifier=Modifier.fillMaxWidth());Spacer(Modifier.height(12.dp));Button(onClick={launcher.launch("image/*")}){Text(if(receipt==null)"UPLOAD RECEIPT" else "RECEIPT SELECTED")};Spacer(Modifier.height(12.dp));Button(onClick={},enabled=amount.isNotBlank()&&receipt!=null,colors=ButtonDefaults.buttonColors(containerColor=Color(0xFFD4AF37),contentColor=Color.Black)){Text("SUBMIT",fontWeight=FontWeight.Bold)};Spacer(Modifier.height(12.dp));Text("Submission will go to Admin for verification before Flexible savings are credited when the backend is connected.",color=Color.Gray,textAlign=TextAlign.Center)}}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlexibleTransferPage(back:()->Unit){
    var amount by remember{mutableStateOf("")}
    var transferTo by remember{mutableStateOf("")}
    var expanded by remember{mutableStateOf(false)}
    val destinations=listOf("REGULAR","TARGET","CONSTANT","WELFARE","LOAN","INTEREST","REGISTRATION")
    val validAmount=amount.toLongOrNull()?.let{it>0}==true
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),horizontalAlignment=Alignment.CenterHorizontally){
        Spacer(Modifier.height(28.dp));TextButton(onClick=back,modifier=Modifier.align(Alignment.Start)){Text("← BACK")}
        Text("TRANSFER",style=MaterialTheme.typography.headlineLarge,fontWeight=FontWeight.Bold,color=Color(0xFFD4AF37))
        Spacer(Modifier.height(8.dp));Text("Transfer from Flexible balance to your linked Regular member account.",color=Color.Gray,textAlign=TextAlign.Center)
        Spacer(Modifier.height(18.dp))
        OutlinedTextField(amount,{v->if(v.all{it.isDigit()})amount=v},label={Text("Amount")},modifier=Modifier.fillMaxWidth())
        Spacer(Modifier.height(12.dp))
        ExposedDropdownMenuBox(expanded=expanded,onExpandedChange={expanded=!expanded}){
            OutlinedTextField(transferTo,{},readOnly=true,label={Text("Transfer To")},trailingIcon={ExposedDropdownMenuDefaults.TrailingIcon(expanded=expanded)},modifier=Modifier.menuAnchor().fillMaxWidth())
            ExposedDropdownMenu(expanded=expanded,onDismissRequest={expanded=false}){destinations.forEach{item->DropdownMenuItem(text={Text(item)},onClick={transferTo=item;expanded=false})}}
        }
        Spacer(Modifier.height(18.dp))
        Button(onClick={},enabled=validAmount&&transferTo.isNotBlank(),colors=ButtonDefaults.buttonColors(containerColor=Color(0xFFD4AF37),contentColor=Color.Black)){Text("SUBMIT TRANSFER",fontWeight=FontWeight.Bold)}
        Spacer(Modifier.height(12.dp))
        Text("Flexible-to-Regular transfer has no withdrawal condition or charge. The selected destination will be credited from the Flexible balance when the backend is connected.",color=Color.Gray,textAlign=TextAlign.Center)
    }
}

@Composable
fun FlexibleWithdrawPage(back:()->Unit){var amount by remember{mutableStateOf("")};Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),horizontalAlignment=Alignment.CenterHorizontally){Spacer(Modifier.height(28.dp));TextButton(onClick=back,modifier=Modifier.align(Alignment.Start)){Text("← BACK")};Text("FLEXIBLE WITHDRAW",style=MaterialTheme.typography.headlineMedium,fontWeight=FontWeight.Bold,color=Color(0xFFD4AF37));Spacer(Modifier.height(16.dp));OutlinedTextField(amount,{v->if(v.all{it.isDigit()||it=='.'})amount=v},label={Text("Withdrawal Amount")},modifier=Modifier.fillMaxWidth());Spacer(Modifier.height(12.dp));Text("Final backend will check the Flexible account start/last withdrawal date and apply the approved 30-day withdrawal rule.",color=Color.Gray,textAlign=TextAlign.Center)}}

@Composable
fun AboutPage() {
    Text("HISTORY", fontWeight=FontWeight.Bold, color=Color(0xFFD4AF37))
    Text("TAIMAKO MULTIPURPOSE COOPERATIVE SOCIETY LTD was founded in 2015 by Bro. Monday Musa as a small group involving close business neighbours, relatives and friends.\n\nIn 2023, the cooperative gained the confidence of interested individuals within the community who shared common cooperative goals. Their participation led to remarkable growth, enabling the cooperative to form a Management Committee for full cooperative society operations.")
    Spacer(Modifier.height(14.dp))
    Text("MISSION", fontWeight=FontWeight.Bold, color=Color(0xFFD4AF37)); Text("To help members attain business independence.")
    Spacer(Modifier.height(14.dp))
    Text("VALUES", fontWeight=FontWeight.Bold, color=Color(0xFFD4AF37)); Text("Integrity • Equity • Self-Help")
    Spacer(Modifier.height(14.dp))
    Text("MEETINGS", fontWeight=FontWeight.Bold, color=Color(0xFFD4AF37)); Text("AGM\nExecutive Council Meeting\nEmergency Meeting\nTraining and Seminar")
    Spacer(Modifier.height(14.dp))
    Text("COOPERATIVE OPERATIONS", fontWeight=FontWeight.Bold, color=Color(0xFFD4AF37)); Text("Savings • Loan • Investment • Commerce")
}

@Composable
fun ContactPage() {
    var name by remember { mutableStateOf("") }; var phone by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }; var message by remember { mutableStateOf("") }
    Text("Send a message to TMCS LTD.")
    Spacer(Modifier.height(12.dp))
    OutlinedTextField(name,{name=it},label={Text("Full Name")},modifier=Modifier.fillMaxWidth())
    OutlinedTextField(phone,{phone=it},label={Text("Phone Number")},modifier=Modifier.fillMaxWidth())
    OutlinedTextField(subject,{subject=it},label={Text("Subject")},modifier=Modifier.fillMaxWidth())
    OutlinedTextField(message,{message=it},label={Text("Message")},modifier=Modifier.fillMaxWidth(),minLines=4)
    Spacer(Modifier.height(12.dp))
    Button(onClick={}, enabled=name.isNotBlank()&&phone.isNotBlank()&&subject.isNotBlank()&&message.isNotBlank(), colors=ButtonDefaults.buttonColors(containerColor=Color(0xFFD4AF37), contentColor=Color.Black)) { Text("SUBMIT") }
    Text("Stage 1 form only — backend submission will be connected in a later stage.")
}


@Composable
fun MembershipPage(open: (String) -> Unit, back: () -> Unit) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp)) {
        Spacer(Modifier.height(28.dp))
        TextButton(onClick=back) { Text("← BACK") }
        Text("MEMBERSHIP", style=MaterialTheme.typography.headlineMedium, fontWeight=FontWeight.Bold, color=Color(0xFFD4AF37))
        Spacer(Modifier.height(20.dp))
        Button(onClick={open("LOGIN")}, modifier=Modifier.fillMaxWidth().height(58.dp), colors=ButtonDefaults.buttonColors(containerColor=Color(0xFF146B3A), contentColor=Color.White)) { Text("LOGIN") }
        Spacer(Modifier.height(14.dp))
        Button(onClick={open("REGISTER")}, modifier=Modifier.fillMaxWidth().height(58.dp), colors=ButtonDefaults.buttonColors(containerColor=Color(0xFF146B3A), contentColor=Color.White)) { Text("REGISTER") }
    }
}

@Composable
fun LoginPage(open: (String) -> Unit, back: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val validMemberUsername = username.length == 5 && username.all { it.isDigit() }
    val validAdminUsername = username.length >= 4 && username.all { it.isLetterOrDigit() }
    val ready = (validMemberUsername || validAdminUsername) && password.isNotBlank()

    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp)) {
        Spacer(Modifier.height(28.dp))
        TextButton(onClick=back) { Text("← BACK") }
        Text("LOGIN", style=MaterialTheme.typography.headlineMedium, fontWeight=FontWeight.Bold, color=Color(0xFFD4AF37))
        Spacer(Modifier.height(6.dp))
        Text("Members and Admins use this same secure login panel.", color=Color.Gray)
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            username,
            { value -> if (value.all { it.isLetterOrDigit() }) username=value },
            label={Text("Username")},
            modifier=Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(password,{password=it},label={Text("Password")},modifier=Modifier.fillMaxWidth())
        Spacer(Modifier.height(16.dp))
        Button(
            onClick={
                // Interface test only: 5 numeric digits represent a Member account.
                // Other valid usernames represent an Admin account until backend role lookup is connected.
                if (validMemberUsername) open("CHANGE MEMBER PIN") else open("ADMIN DASHBOARD")
            },
            enabled=ready,
            modifier=Modifier.fillMaxWidth(),
            colors=ButtonDefaults.buttonColors(containerColor=Color(0xFFD4AF37), contentColor=Color.Black)
        ) { Text("LOGIN") }
        Spacer(Modifier.height(12.dp))
        Text("INTERFACE TEST ONLY: the final backend will authenticate credentials and determine the account role. Any Admin-generated password/PIN is temporary and must be changed on the first successful login before dashboard access.", color=Color.Gray)
    }
}

@Composable
fun ChangePinPage(title:String,continueTo:()->Unit,back:()->Unit){
    var newPin by remember{mutableStateOf("")};var confirm by remember{mutableStateOf("")}
    val ready=newPin.length==4&&newPin.all{it.isDigit()}&&confirm==newPin
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),horizontalAlignment=Alignment.CenterHorizontally){
        Spacer(Modifier.height(28.dp));TextButton(onClick=back,modifier=Modifier.align(Alignment.Start)){Text("← BACK")}
        Text(title,style=MaterialTheme.typography.headlineMedium,fontWeight=FontWeight.Bold,color=Color(0xFFD4AF37),textAlign=TextAlign.Center)
        Spacer(Modifier.height(8.dp));Text("Your Admin-generated PIN is temporary. Create your personal 4-digit PIN before accessing the account.",color=Color.Gray,textAlign=TextAlign.Center)
        Spacer(Modifier.height(18.dp));OutlinedTextField(newPin,{v->if(v.length<=4&&v.all{it.isDigit()})newPin=v},label={Text("New 4-digit PIN")},modifier=Modifier.fillMaxWidth())
        Spacer(Modifier.height(10.dp));OutlinedTextField(confirm,{v->if(v.length<=4&&v.all{it.isDigit()})confirm=v},label={Text("Confirm New PIN")},modifier=Modifier.fillMaxWidth())
        if(confirm.isNotEmpty()&&confirm!=newPin){Spacer(Modifier.height(6.dp));Text("PINs do not match.",color=MaterialTheme.colorScheme.error)}
        Spacer(Modifier.height(18.dp));Button(onClick=continueTo,enabled=ready,modifier=Modifier.fillMaxWidth(),colors=ButtonDefaults.buttonColors(containerColor=Color(0xFFD4AF37),contentColor=Color.Black)){Text("CHANGE PIN & CONTINUE",fontWeight=FontWeight.Bold)}
        Spacer(Modifier.height(12.dp));Text("Interface checkpoint only. The backend will enforce this screen only on the first successful login and securely store the new PIN hash.",color=Color.Gray,textAlign=TextAlign.Center)
    }
}

@Composable
fun RegisterPage(contact: () -> Unit, back: () -> Unit) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp)) {
        Spacer(Modifier.height(28.dp))
        TextButton(onClick=back) { Text("← BACK") }
        Text("REGISTER", style=MaterialTheme.typography.headlineMedium, fontWeight=FontWeight.Bold, color=Color(0xFFD4AF37))
        Spacer(Modifier.height(16.dp))
        Text("Public self-registration is not available. Member accounts are created by an authorized Admin.")
        Spacer(Modifier.height(16.dp))
        Button(onClick=contact, modifier=Modifier.fillMaxWidth(), colors=ButtonDefaults.buttonColors(containerColor=Color(0xFFD4AF37), contentColor=Color.Black)) { Text("CONTACT US TO REGISTER") }
    }
}


@Composable
fun ByeLawPage() {
    Text("TMCS LTD BYE-LAW", fontWeight=FontWeight.Bold, color=Color(0xFFD4AF37), style=MaterialTheme.typography.headlineSmall)
    Spacer(Modifier.height(6.dp))
    Text("Updated -October 2025", color=Color.Gray, fontWeight=FontWeight.Bold)
    Spacer(Modifier.height(14.dp))
    Text(
        text = """1
The Bye Laws
TAIMAKO
MULTI-PURPOSE
CO-OPERATIVE SOCIETY LTD
1.0 INTRODUCTION
TAIMAKO MULTIPURPOSE CO-OPERATIVE SOCIETY 
LTD was founded in the year 2015 by Bro. Monday 
Musa as a small group involving closed business 
neighbors, relatives and friends.
In 2023 it gain the confidence of interested 
individuals of the community sharing common co￾operative goals, who joined the co-operative leading 
to a remarkable growth, hence the co-operative was 
able to form management committee for a real-time 
co-operative society operations.
2.0 INTERPRETATION
The content of this title will be generated when all is 
completed
3.0 DEFINITION
TAIMAKO MULTIPURPOSE CO-OPERATIVE SOCIETY 
LTD is a Community Society forum that aims to 
support the sustainability of businesses, traders and 
farmers.
4.0 MISSION
To help members attain business independent
5.0 VALUES
Integrity, Equity and Self-Help
6.0 TARGET BENEFICIARY
Traders, Business and Farmers. However, interested 
Civil Servants and others can choose to participate
7.0 AREA OF OPERATIONS
Location: Romi and its Environs
Address: #7 Wambai street @Wambai Street 
junction) Lussa’a
MODE OF OPERATIONS
8.0 MEMBERSHIP
Members of the community as prescribed by law
8.1 ADMISSION TO MEMBERSHIP
i. By purchase of membership application form duly 
signed with relevant information provided. Thus, it's 
a one-time Membership registration. However, 
renewal applies to membership passbook and yearly 
administration fee or others as may be decided at 
AGM.
ii. Registration, Annual renewal fee, cost of 
application form and others shall be decided at the 
AGM
8.2 WITHDRAWAL OF MEMBERSHIP
i. written application for withdrawal
ii. 20% charge of total savings apply
iii. Pay off can take up to 30 days time
8.3 NEXT OF KIN
a. Every member of the co-operative shall nominate 
a person or persons (NEXT-OF-KIN) to whom his or 
her entitlement shall be paid to in the event of death
or permanent disability. Where no person/persons 
has been so appointed as the NEXT-OF -KIN of the 
affected cooperator prior to his/her death or 
permanent disability, a letter of attestation shall be 
required from a person/persons who claim to be the 
heir to the deceased or on the ground of 
incapacitation.
8.4 SAVINGS (THRIFT)
REGULAR SAVINGS: normal general savings plan 
from November to October
1. A savings account primarily for member savings
2. Minimum share, shall be as decided at the AGM
2
3. Monthly savings NOT Compulsory, NO Penalty for 
failure to contribute in any month
5. Entitled to monthly dividend shares, IF 
contribution is made up to a minimum share
6. Entitled to loan of 90% of savings @5% interest 
per 30days
7. Total savings is disbursed at the end of 
cooperative year (end of Oct)
8. Dividend is paid at AGM
8. 20% charge applies on withdrawal before end of 
cooperative year
CONSTANT SAVINGS: Suitable for long term plans
1. A savings account separately from Regular savings 
@minimum of 5 years locked savings period
2. Minimum amount per share is announced and can 
be review by the management
3. Compulsory monthly savings
4. 10% Charges applies as Penalty for failure to save a
minimum share or any short saved amount in any 
month; charges must be paid or be deducted from 
savings
5. Entitled to monthly dividend shares, IF 
contribution is made up to a minimum share
6. Entitled to loan of 90% of savings @3% interest 
per 30days
7. No end of year disbursement, but completed year 
savings shall be spread over proceeding year 
repeatedly until completion of savings duration
8. Minimum balance of N300,000 after 5 Years is 
required before withdrawal
9. 20% charge applies on withdrawal under 5 years if 
minimum balance of N300,000 isn't achieved, 
however, minimum balance of 300,000 must be 
maintained until 5 years, IF minimum balance of 
N300,000 is not achieved after 5years, 5% charges of 
short saved amount will be charged before 
disbursement
10. Withdrawal is possible from excess of minimum 
balance if it's achieved before 5 years.
11. Can withdraw excess of minimum balance 
without charge @minimum of 30 days notice
TARGET SAVINGS: Suitable for project plans
1. A unique savings account to be decided by 
member
2. Minimum amount is calculated by target amount 
for weekly or monthly savings
3. Compulsory monthly savings of monthly target
4. 10% charges apply for failure to pay minimum 
monthly amount or short saved amount to meet up 
target in any month
5. Entitled to monthly dividend shares, IF 
contribution is made up to a minimum share
6. Entitled to 1% of fulfilled monthly target as reward
or support to the member to achieve target
7. Minimum target savings duration: 24 weeks (6 
months)
8. 20% charge applies on withdrawal before end of 
target period
9. Entitled to loan of 90% of savings @3% interest 
per 30days, IF target savings duration is up to one 
year
3
WELFARE SAVINGS: Suitable for education and 
retirement plans
1. Runs Flexible savings, -Save any amount any day as
you fit
2. Minimum savings duration is one year
3. After savings duration is completed, the total 
savings shall be spread into desired duration by the 
member (minimum of one year) shall be paid 
monthly share plus 1% of total balance until end of 
disbursement duration, either by descending or 
ascending order.
4. Entitled to monthly dividend shares during savings 
period
5. No end of cooperative year disbursement
6. 20% charge applies on withdrawal either during 
savings or welfare period; however, withdrawal 
notice of minimum of 30days is required
8.5 THRIFT (SAVINGS) DISCIPLINE
1. A co-operative society member who failed to 
contribute a minimum of share amount in any 
month, shall not be entitle to monthly share of 
dividend for the respective month/months
2. Any member who demand to withdraw savings 
before end of cooperative year will be charge 20% of 
total savings. However, withdrawal payment can 
take up to 30 days.
8.6 FLEXIBLE SAVINGS: Suitable for petty traders, 
student and the general public
1. Flexible savings, -Save any amount any day as you
fit
2. Minimum savings duration is one month (30 days)
3. NO membership registration, only purchase of
record card.
4. NOT Entitled to monthly dividend shares
5. Entitled to withdraw 100% of savings after 30days,
FREE withdrawal once in 30 days
6. 20% charge applies on withdrawal before 30days
8.7 LOAN
a. i. All finances shall be available for loan to co￾operative members and members of the community.
ii. Members of the co-operative and members of the 
community who need loan shall apply through loan 
form, loan form must be duly signed by applicant and
guarantors as may be required based on the amount.
iii. Loan request above member's savings cannot be 
granted unless by endorsement of guarantors or by 
deposit of collateral.
iv. Loan is for a period of 30days renewable by 
paying only the interest.
v. Any member who is yet to pay back loan before 
the end of co-operative year will not be entitled to 
his/her savings until payment is completed or except 
the member consent to means of minus the loan 
balance from savings.
vi. If anyone failed to fulfill repayment requirements, 
in the case where there is deposited collateral, legal 
procedures shall be applied to recover the funds 
involved.
b. GROUP LOAN
-REQUIREMENTS
i. Minimum of three members
ii. Purchase of nonrefundable application form
iii. 10% deposit of loan amount as collateral
iv. Must provide a guarantor (preferably a member 
of TMCS LTD)
-CONDITIONS
i. Every member of the group will be held responsible
if any member defaulted
ii. No disbursement or withdrawal in the case of any 
default
4
c. COLLATERAL
i. As the case may be, the cooperative may demand 
for collateral before grant of loan
ii. Failure to pay on the due date means the collateral
has been confiscated as a property of TAIMAKO 
MULTIPURPOSE COOPERATIVE SOCIETY LTD.
8.8 DUTIES OF A GUARANTOR
A guarantor -preferably an active member of the co￾operative society and:
i. shall personally guarantee repayment of the loan
ii. Must authenticate the capability of the borrower
iii. Shall be held responsible to pay the loan if the 
beneficiary failed to pay.
8.9
9.0 CO-OPERATIVE MEETINGS
i. AGM
ii. Executive Council Meeting
iii. Emergency Meeting
iv. Training and Seminar
9.1 SOURCE OF FUNDS
i. Members contribution
ii. Investors
iii. Application forms
iv. Loan interest
v. Business profit
vi. Bank loan
9.2 SOURCE OF CO-OPERATIVE FUNDS
i. Registration fee
ii. Renewal fee
iii. Business profit
9.3 CUSTODY OF FUNDS
Co-operative funds not required for immediate use 
may be invested in any legal yielding business
9.4 OPERATION OF BANK ACCOUNT
The executive council shall operate the society's bank
accounts whereby the chairman, assistant chairman, 
secretary and treasurer shall serve as signatories in 
the following order:
Category A: 1. Chairman 2. Assistant Chairman
Category B: 1. Secretary 2. Treasurer
MANDATE
A+B (One signatory from each category makes valid 
transaction)
9.5 BOOKS AND ACCOUNTS
a. PRESCRIBED BOOK AND RECORDS:
Accounts and records shall be maintained in the 
forms prescribed by the Registrar and shall include 
the following:
i. Membership register showing the names, 
addresses and occupations of members, the date of 
admission to membership.
ii. Attendance register: members’ attendance at 
meetings.
ii. General ledger: A cash book showing the receipts, 
expenditure and balance on day to day business of 
the Society.
iii. Personal ledger for each member
iv. Loan Register showing installments for repayment
of loans.
v. Minutes book for proceedings of General and 
Management Council meetings.
vi. Loan Bond files containing bonds for all loans 
issued.
vii. Such other records as may be prescribed by the 
Registrar.
b. PASS BOOK:
i. Every member shall keep and maintain a passbook 
showing particulars of his accounts with the society.
ii. Every registered member or beneficiary of TMCS 
LTD shall obtain this membership passbook at a fee 
decided at the last AGM
iii. Every passbook holder must present the card at all
payment occasion for record purposes. Card 
replacement shall be made at same amount.
5
9.6 AMENDMENT OF THE BYE-LAW
An amendment of TAIMAKO MULTI-PURPOSE 
COOPERATIVE SOCIETY LIMITED bye laws shall be 
made by a resolution of members of the society at a 
General Meeting.
9.7 DISCIPLINARY PROCEDURES:
i. The Management Committee shall have the power 
to suspend any erring member of the co-operative 
for gross Misconduct. Gross misconduct includes all 
actions and behaviors deemed by members as 
constituting a hindrance to the smooth conduct of 
the co-operative activities. Others include deliberate 
failure to implement the decisions of the 
Management Council or any other misconduct 
deemed to constitute gross indiscipline.
ii. All decision taken in any meeting applies to all 
members of the cooperative either present or 
absent.
 (iv) The decision to suspend an erring member of 
the Management Council shall be endorsed by, at 
least, 2/3 majority of the members of the 
Management Council.
9.8 OFFICERS AND THEIR DUTIES
The Chairman
Is the Chairman of the Management Council and 
Board of the Cooperative Society.
b. He/She is the Chief Executive Officer of the Society
that executes and controls the day to day activities of
the society.
c. He's responsible for administrative and investment
decisions.
d. He/She calls for the monthly and emergency 
meeting of the Executive Council.
ii. The Vice-Chairman
The duties of the Vice-Chairman shall be:
a. Shall assist the Chairman in the discharge of his 
duties
b. Shall perform duties assigned to him/her by the 
Chairman Shall act as the Chairman in his/her 
absence on the consent of the Chairman.
d. Any other duty that may be assigned from time to 
time.
iii. Secretary.
The duties of the Secretary shall be:
a. To take and keep minutes of the Society's 
meetings
b. To extract, circulate and implement Executive 
decisions
c. To receive and respond to all correspondences as 
directed by the Executive Council.
d. To receive all loan applications and bring same 
before the Management Committee.
e. To summon all meetings of the co-operative 
society as directed by the chairman
f. Any other duty assigned by the Management 
Committee
iv. Assistant Secretary
The duties of the Assistant Secretary
The Assistant Secretary
a. Shall assist the Secretary in the discharge of 
his duties,
b. Shall perform duties assigned to him/her by 
the Secretary
c. Shall act as the Secretary in his/her absence 
on the consent of the Secretary.
d. Any other duty that may be assigned from time
to time by the Management Committee.
v. Treasurer
The duties of the Treasurer shall be to:
a. Receive and take charge of all monies belonging to
the co-operative society.
b. Keep record of such monies on the prescribed 
form and of all monies paid to other members of the 
society.
c. Disburse money to any members of the society as 
approved by the Executive committee.
6
d. Keep, separate, all monies belonging to the society
and on no account shall co-operative society monies 
be used for other activities.
e. Shall give account at all times when called upon by
the Management Committee, the Registrar, the 
Auditor or any other person authorized by law.
vi. Financial Secretary
a. Record all transaction of the society in the 
appropriate books
b. Issue receipt and .other documents as prescribed 
by the Management Committee.
c. Prepare all payment vouchers and act as directed 
by the Management Committee
d. Prepare all financial reports including the Annual 
Accounts such as Income and Expenditure Account, 
Cash Flow Statement, Balance Sheet, etc for the 
Management Committee.
e. Perform any other duty as may be assigned by the 
Management Committee.
9.9 CONSTITUTION AND DUTIES OF ANNUAL 
GENERAL MEETING (AGM)
Is the General Body of members, shall meet once a 
year to direct the affairs/activities of the Society 
during the Annual General Meeting (AGM).
a. Quorum:
a. The presence of at least 2/3 of members shall be 
mandatory for the conduct of deliberations and 
resolutions at the Annual General Meeting (AGM). 
Thus the presence of 2/3 of members therefore shall 
form a quorum. When there is no quorum at the 
Annual General Meeting, a motion for Adjournment 
shall be moved and seconded before a new date is 
fixed for the AGM.
b. lf at the adjourned date and a quorum is not 
formed, the AGM shall hold regardless of a quorum.
c. The AGM shall hold not later than 30th November 
every year.
The duties of the AGM shall be:
i. To consider the reports of the Management 
Committee and the annual accounts analysis as 
prepared by the Financial Secretary or the person 
authorized to do so.
ii. To decide, approve or review any co-operative 
function or program
iii. To take decision on any major issue critical to the 
growth and development of the society and to the 
advantage and benefit of members.
10.0 TRUSTEES:
i. The Management Committee shall be the trustees 
of the co-operative Society and shall be responsible 
for the policy formulation and advisory matters, 
acquisition and disposal of the Society's assets and 
liabilities as may be approved by the management, 
shall propose and apply for any Government funds 
allocation for cooperative societies or Bank loan 
within payable capacity for especially farmers and 
other business owners
ii. Shall act on behalf of the co-operative Society on 
all legal matters or engage the services of a legal 
practitioner.
iii. They are also empowered to engage the services 
of any consultant or professional as the need arises.
10.1 SEAL
The Secretary shall keep in safe custody the seal of 
the co-operative society on behalf of the Trustees. 
The seal shall be of a kind approved by the Registrar. 
Documents shall be signed by at least two of the 
Trustees, one of which shall be the Secretary.
THIS COPY OF THE BYE LAW IS UNDER PUBLICATION
PROCESS; HOWEVER, UNTIL FINAL COPY OF THE BYE
LAW IS FULFILLED, THIS SERVES AS MEMBERSHIP 
HANDBOOK AND BINDING AUTHORITY UPON ALL 
MEMBERS AND CLIENTS.
7
Updated -October 2025""".trimIndent(),
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
fun MemberDashboardPage(open: (String) -> Unit, back: () -> Unit) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp)) {
        Spacer(Modifier.height(28.dp))
        TextButton(onClick=back) { Text("← BACK") }
        Card(Modifier.fillMaxWidth().height(72.dp)) { Box(Modifier.fillMaxSize(), contentAlignment=Alignment.Center) { Text("MEMBER DASHBOARD", style=MaterialTheme.typography.headlineLarge, fontWeight=FontWeight.Bold, color=Color(0xFFD4AF37), textAlign=TextAlign.Center, modifier=Modifier.fillMaxWidth()) } }
        Spacer(Modifier.height(6.dp))
        Text("Stage 3 interface preview — sample values only.", color=Color.Gray)
        Spacer(Modifier.height(18.dp))

        val fields = listOf(
            "REGISTRATION" to "₦0",
            "REGULAR" to "₦0",
            "TARGET" to "₦0",
            "CONSTANT" to "₦0",
            "WELFARE" to "₦0",
            "FLEXIBLE" to "₦0",
            "TOTAL SAVINGS — CURRENT MONTH" to "₦0",
            "NUMBER OF SHARES" to "0",
            "DIVIDEND — PREVIOUS MONTH" to "₦0",
            "ACTIVE LOAN" to "₦0",
            "PAYMENT DUE DATE" to "—",
            "LOAN INTEREST" to "₦0"
        )
        fields.chunked(2).forEach { pair ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement=Arrangement.spacedBy(10.dp)) {
                pair.forEach { (label,value) ->
                    Card(Modifier.weight(1f).height(92.dp)) {
                        Column(Modifier.fillMaxSize().padding(8.dp), verticalArrangement=Arrangement.Center, horizontalAlignment=Alignment.CenterHorizontally) {
                            Text(label, fontWeight=FontWeight.Bold, style=MaterialTheme.typography.titleMedium, textAlign=TextAlign.Center, modifier=Modifier.fillMaxWidth())
                            Spacer(Modifier.height(6.dp))
                            Text(value, color=Color(0xFF146B3A), fontWeight=FontWeight.Bold, style=MaterialTheme.typography.titleLarge, textAlign=TextAlign.Center, modifier=Modifier.fillMaxWidth())
                        }
                    }
                }
                if(pair.size==1) Spacer(Modifier.weight(1f))
            }
            Spacer(Modifier.height(10.dp))
        }

        Spacer(Modifier.height(8.dp))
        val actions = listOf(
            "PAY" to "PAY",
            "HISTORY" to "TRANSACTION HISTORY",
            "WITHDRAW" to "WITHDRAW",
            "LOAN" to "APPLY FOR LOAN"
        )
        actions.chunked(3).forEach { row ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement=Arrangement.spacedBy(8.dp)) {
                row.forEach { (label,destination) ->
                    Button(onClick={open(destination)}, modifier=Modifier.weight(1f).height(52.dp), contentPadding=PaddingValues(horizontal=4.dp), colors=ButtonDefaults.buttonColors(containerColor=Color(0xFF146B3A), contentColor=Color.White)) {
                        Text(label, style=MaterialTheme.typography.labelLarge)
                    }
                }
                repeat(3-row.size) { Spacer(Modifier.weight(1f)) }
            }
            Spacer(Modifier.height(10.dp))
        }
    }
}

@Composable
fun MemberPlaceholderPage(title: String, back: () -> Unit) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp)) {
        Spacer(Modifier.height(28.dp))
        TextButton(onClick=back) { Text("← BACK") }
        Text(title, style=MaterialTheme.typography.headlineMedium, fontWeight=FontWeight.Bold, color=Color(0xFFD4AF37))
        Spacer(Modifier.height(16.dp))
        Text("Stage 3 navigation test only. This function will be implemented and connected in a later controlled step.")
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PayPage(back: () -> Unit) {
    var amount by remember { mutableStateOf("") }
    var selected by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var receiptName by remember { mutableStateOf("") }
    val clipboardManager = LocalClipboardManager.current
    val destinations = listOf("REGULAR","TARGET","CONSTANT","WELFARE","FLEXIBLE","LOAN","INTEREST","REGISTRATION")
    val receiptPicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        receiptName = if (uri != null) "Receipt selected" else ""
    }

    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp)) {
        Spacer(Modifier.height(28.dp))
        TextButton(onClick=back) { Text("← BACK") }
        Text("PAY", style=MaterialTheme.typography.headlineLarge, fontWeight=FontWeight.Bold, color=Color(0xFFD4AF37), modifier=Modifier.fillMaxWidth(), textAlign=TextAlign.Center)
        Spacer(Modifier.height(16.dp))

        Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(14.dp)) {
                Text("BANK TRANSFER DETAILS", fontWeight=FontWeight.Bold, color=Color(0xFFD4AF37), style=MaterialTheme.typography.titleLarge, textAlign=TextAlign.Center, modifier=Modifier.fillMaxWidth())
                Spacer(Modifier.height(10.dp))
                Row(Modifier.fillMaxWidth(), verticalAlignment=Alignment.CenterVertically, horizontalArrangement=Arrangement.Center) {
                    Text("Account Number: 1027050172", fontWeight=FontWeight.Bold, style=MaterialTheme.typography.titleLarge, textAlign=TextAlign.Center)
                    Spacer(Modifier.width(4.dp))
                    TextButton(onClick={ clipboardManager.setText(AnnotatedString("1027050172")) }, contentPadding=PaddingValues(horizontal=6.dp, vertical=2.dp)) { Text("COPY", style=MaterialTheme.typography.labelSmall) }
                }
                Text("Bank Name: FCMB\nAccount Name: TAIMAKO MULTIPURPOSE COOPERATIVE SOCIETY LTD", fontWeight=FontWeight.Bold, style=MaterialTheme.typography.titleLarge, lineHeight=28.sp, textAlign=TextAlign.Center, modifier=Modifier.fillMaxWidth())
            }
        }

        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value=amount,
            onValueChange={ value -> if (value.all { it.isDigit() }) amount=value },
            label={Text("Amount (₦)")},
            modifier=Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))

        ExposedDropdownMenuBox(expanded=expanded, onExpandedChange={expanded=!expanded}) {
            OutlinedTextField(
                value=selected,
                onValueChange={},
                readOnly=true,
                label={Text("Pay For")},
                trailingIcon={ExposedDropdownMenuDefaults.TrailingIcon(expanded=expanded)},
                modifier=Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(expanded=expanded, onDismissRequest={expanded=false}) {
                destinations.forEach { item ->
                    DropdownMenuItem(text={Text(item)}, onClick={selected=item; expanded=false})
                }
            }
        }

        Spacer(Modifier.height(14.dp))
        Button(
            onClick={receiptPicker.launch("image/*")},
            modifier=Modifier.align(Alignment.CenterHorizontally).height(44.dp),
            contentPadding=PaddingValues(horizontal=12.dp, vertical=4.dp),
            colors=ButtonDefaults.buttonColors(containerColor=Color(0xFF146B3A), contentColor=Color.White)
        ) { Text(if(receiptName.isBlank()) "UPLOAD PAYMENT RECEIPT" else "RECEIPT SELECTED ✓") }

        Spacer(Modifier.height(8.dp))
        Text("Payment receipt is required before submission.", color=Color.Gray)
        Spacer(Modifier.height(16.dp))
        Button(
            onClick={},
            enabled=amount.isNotBlank() && amount.toLongOrNull()?.let { it > 0 } == true && selected.isNotBlank() && receiptName.isNotBlank(),
            modifier=Modifier.align(Alignment.CenterHorizontally).height(44.dp),
            contentPadding=PaddingValues(horizontal=18.dp, vertical=4.dp),
            colors=ButtonDefaults.buttonColors(containerColor=Color(0xFFD4AF37), contentColor=Color.Black)
        ) { Text("SUBMIT", fontWeight=FontWeight.Bold) }
        Spacer(Modifier.height(10.dp))
        Text("Stage 4 interface test only. SUBMIT does not send or credit money yet.", color=Color.Gray)
    }
}


@Composable
fun TransactionHistoryPage(back: () -> Unit) {
    val sampleTransactions = listOf(
        Triple("REGULAR", "₦0", "—"),
        Triple("TARGET", "₦0", "—"),
        Triple("CONSTANT", "₦0", "—"),
        Triple("WELFARE", "₦0", "—"),
        Triple("FLEXIBLE", "₦0", "—")
    )
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp)) {
        Spacer(Modifier.height(28.dp))
        TextButton(onClick=back) { Text("← BACK") }
        Text("TRANSACTION HISTORY", style=MaterialTheme.typography.headlineLarge, fontWeight=FontWeight.Bold, color=Color(0xFFD4AF37), textAlign=TextAlign.Center, modifier=Modifier.fillMaxWidth())
        Spacer(Modifier.height(6.dp))
        Text("Stage 5 interface preview — no live transaction records yet.", color=Color.Gray, textAlign=TextAlign.Center, modifier=Modifier.fillMaxWidth())
        Spacer(Modifier.height(18.dp))

        Card(Modifier.fillMaxWidth()) {
            Row(Modifier.fillMaxWidth().padding(12.dp), horizontalArrangement=Arrangement.SpaceBetween) {
                Text("TYPE", fontWeight=FontWeight.Bold)
                Text("AMOUNT", fontWeight=FontWeight.Bold)
                Text("DATE", fontWeight=FontWeight.Bold)
            }
        }
        Spacer(Modifier.height(8.dp))

        sampleTransactions.forEach { (type, amount, date) ->
            Card(Modifier.fillMaxWidth()) {
                Row(Modifier.fillMaxWidth().padding(14.dp), verticalAlignment=Alignment.CenterVertically) {
                    Text(type, fontWeight=FontWeight.Bold, modifier=Modifier.weight(1.2f))
                    Text(amount, color=Color(0xFF146B3A), fontWeight=FontWeight.Bold, textAlign=TextAlign.Center, modifier=Modifier.weight(1f))
                    Text(date, textAlign=TextAlign.End, modifier=Modifier.weight(1f))
                }
            }
            Spacer(Modifier.height(8.dp))
        }

        Spacer(Modifier.height(10.dp))
        Text("Approved deposits, cash credits, withdrawals, loan payments and other account movements will appear here after backend integration.", color=Color.Gray, textAlign=TextAlign.Center, modifier=Modifier.fillMaxWidth())
    }
}


@Composable
fun WithdrawPage(back: () -> Unit) {
    var amount by remember { mutableStateOf("") }
    var accepted by remember { mutableStateOf(false) }
    var showChargeNotice by remember { mutableStateOf(false) }

    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp)) {
        Spacer(Modifier.height(28.dp))
        TextButton(onClick=back) { Text("← BACK") }
        Text("WITHDRAW", style=MaterialTheme.typography.headlineLarge, fontWeight=FontWeight.Bold, color=Color(0xFFD4AF37), textAlign=TextAlign.Center, modifier=Modifier.fillMaxWidth())
        Spacer(Modifier.height(6.dp))
        Text("Stage 6 interface test — no withdrawal will be submitted yet.", color=Color.Gray, textAlign=TextAlign.Center, modifier=Modifier.fillMaxWidth())
        Spacer(Modifier.height(18.dp))

        OutlinedTextField(
            value=amount,
            onValueChange={ value ->
                if (value.all { it.isDigit() }) {
                    amount=value
                    accepted=false
                }
            },
            label={Text("Withdrawal Amount (₦)")},
            modifier=Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(14.dp))

        Button(
            onClick={showChargeNotice=true},
            enabled=amount.toLongOrNull()?.let { it > 0 } == true,
            modifier=Modifier.align(Alignment.CenterHorizontally).height(44.dp),
            contentPadding=PaddingValues(horizontal=14.dp, vertical=4.dp),
            colors=ButtonDefaults.buttonColors(containerColor=Color(0xFF146B3A), contentColor=Color.White)
        ) { Text("CHECK WITHDRAWAL CHARGE") }

        Spacer(Modifier.height(14.dp))
        if (accepted) {
            Text("Withdrawal charge accepted ✓", color=Color(0xFF146B3A), fontWeight=FontWeight.Bold, textAlign=TextAlign.Center, modifier=Modifier.fillMaxWidth())
            Spacer(Modifier.height(10.dp))
        }

        Button(
            onClick={},
            enabled=amount.toLongOrNull()?.let { it > 0 } == true && accepted,
            modifier=Modifier.align(Alignment.CenterHorizontally).height(44.dp),
            contentPadding=PaddingValues(horizontal=18.dp, vertical=4.dp),
            colors=ButtonDefaults.buttonColors(containerColor=Color(0xFFD4AF37), contentColor=Color.Black)
        ) { Text("SUBMIT", fontWeight=FontWeight.Bold) }
    }

    if (showChargeNotice) {
        AlertDialog(
            onDismissRequest={showChargeNotice=false},
            title={Text("WITHDRAWAL CHARGE", fontWeight=FontWeight.Bold)},
            text={Text("Applicable charge depends on the month of withdrawal:\n\nNovember – June: 20%\nJuly: 15%\nAugust: 10%\nSeptember: 5%\n\nOctober withdrawal rule will be confirmed before backend activation.")},
            confirmButton={
                TextButton(onClick={accepted=true; showChargeNotice=false}) { Text("ACCEPT", fontWeight=FontWeight.Bold) }
            },
            dismissButton={
                TextButton(onClick={accepted=false; showChargeNotice=false}) { Text("DECLINE") }
            }
        )
    }
}


@Composable
fun LoanApplicationPage(back: () -> Unit) {
    var loanAmount by remember { mutableStateOf("") }
    var guarantor by remember { mutableStateOf("") }
    // Stage 7 test values only. Backend will supply the member's real savings.
    val totalSavings = 20000L
    val ownLoanLimit = (totalSavings * 90) / 100
    val requested = loanAmount.toLongOrNull() ?: 0L
    val needsGuarantor = requested > ownLoanLimit
    val shortfall = if (needsGuarantor) requested - ownLoanLimit else 0L
    val guarantorFormatValid = guarantor.length == 5 && guarantor.all { it.isDigit() }
    val canSubmit = requested > 0 && (!needsGuarantor || guarantorFormatValid)

    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp)) {
        Spacer(Modifier.height(28.dp))
        TextButton(onClick=back) { Text("← BACK") }
        Text("APPLY FOR LOAN", style=MaterialTheme.typography.headlineLarge, fontWeight=FontWeight.Bold, color=Color(0xFFD4AF37), textAlign=TextAlign.Center, modifier=Modifier.fillMaxWidth())
        Spacer(Modifier.height(6.dp))
        Text("Stage 7 interface test — no loan application will be submitted yet.", color=Color.Gray, textAlign=TextAlign.Center, modifier=Modifier.fillMaxWidth())
        Spacer(Modifier.height(18.dp))

        Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(14.dp), horizontalAlignment=Alignment.CenterHorizontally) {
                Text("TOTAL SAVINGS", fontWeight=FontWeight.Bold, style=MaterialTheme.typography.titleMedium)
                Text("₦20,000", color=Color(0xFF146B3A), fontWeight=FontWeight.Bold, style=MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(8.dp))
                Text("90% LOAN VALUE", fontWeight=FontWeight.Bold, style=MaterialTheme.typography.titleMedium)
                Text("₦18,000", color=Color(0xFF146B3A), fontWeight=FontWeight.Bold, style=MaterialTheme.typography.titleLarge)
            }
        }

        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value=loanAmount,
            onValueChange={ value -> if (value.all { it.isDigit() }) loanAmount=value },
            label={Text("Loan Amount (₦)")},
            modifier=Modifier.fillMaxWidth()
        )

        if (needsGuarantor) {
            Spacer(Modifier.height(12.dp))
            Text("Amount above your 90% loan value. Guarantor required.", color=Color(0xFFD4AF37), fontWeight=FontWeight.Bold)
            Text("Required guarantor savings to cover shortfall: ₦$shortfall", color=Color.Gray)
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(
                value=guarantor,
                onValueChange={ value -> if (value.length <= 5 && value.all { it.isDigit() }) guarantor=value },
                label={Text("Guarantor Username (5 digits)")},
                modifier=Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(6.dp))
            Text("Stage 7 checks username format only. Guarantor account and savings balance will be verified by the backend.", color=Color.Gray)
        } else if (requested > 0) {
            Spacer(Modifier.height(12.dp))
            Text("Guarantor not required for this amount.", color=Color(0xFF146B3A), fontWeight=FontWeight.Bold)
        }

        Spacer(Modifier.height(18.dp))
        Button(
            onClick={},
            enabled=canSubmit,
            modifier=Modifier.align(Alignment.CenterHorizontally).height(44.dp),
            contentPadding=PaddingValues(horizontal=18.dp, vertical=4.dp),
            colors=ButtonDefaults.buttonColors(containerColor=Color(0xFFD4AF37), contentColor=Color.Black)
        ) { Text("SUBMIT", fontWeight=FontWeight.Bold) }

        Spacer(Modifier.height(12.dp))
        Text("Member loan term: 30 days. Interest: 5%. Approved loan details will later appear on the Member Dashboard.", color=Color.Gray, textAlign=TextAlign.Center, modifier=Modifier.fillMaxWidth())
    }
}

@Composable
fun AdminLoginPage(login: () -> Unit, back: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp), horizontalAlignment=Alignment.CenterHorizontally) {
        Spacer(Modifier.height(28.dp))
        TextButton(onClick=back, modifier=Modifier.align(Alignment.Start)) { Text("← BACK") }
        Text("ADMIN LOGIN", style=MaterialTheme.typography.headlineLarge, fontWeight=FontWeight.Bold, color=Color(0xFFD4AF37), textAlign=TextAlign.Center, modifier=Modifier.fillMaxWidth())
        Spacer(Modifier.height(20.dp))
        OutlinedTextField(username,{username=it},label={Text("Admin Username")},modifier=Modifier.fillMaxWidth())
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(password,{password=it},label={Text("Password")},modifier=Modifier.fillMaxWidth())
        Spacer(Modifier.height(16.dp))
        Button(onClick=login, enabled=username.isNotBlank() && password.isNotBlank(), modifier=Modifier.height(44.dp), contentPadding=PaddingValues(horizontal=24.dp, vertical=4.dp), colors=ButtonDefaults.buttonColors(containerColor=Color(0xFFD4AF37), contentColor=Color.Black)) { Text("LOGIN", fontWeight=FontWeight.Bold) }
        Spacer(Modifier.height(12.dp))
        Text("Stage 8 Admin interface test only. Real Admin authentication is not connected yet.", color=Color.Gray, textAlign=TextAlign.Center)
    }
}

@Composable
fun AdminDashboardPage(open: (String) -> Unit, back: () -> Unit) {
    val actions = listOf("CREATE MEMBER","CREDIT CASH","APPROVALS","MEMBERS","CREATE ADMIN","ADMIN PERMISSIONS")
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp), horizontalAlignment=Alignment.CenterHorizontally) {
        Spacer(Modifier.height(28.dp))
        TextButton(onClick=back, modifier=Modifier.align(Alignment.Start)) { Text("← LOGOUT") }
        Card(Modifier.fillMaxWidth().height(72.dp)) {
            Box(Modifier.fillMaxSize(), contentAlignment=Alignment.Center) {
                Text("ADMIN DASHBOARD", style=MaterialTheme.typography.headlineLarge, fontWeight=FontWeight.Bold, color=Color(0xFFD4AF37), textAlign=TextAlign.Center, modifier=Modifier.fillMaxWidth())
            }
        }
        Spacer(Modifier.height(8.dp))
        Text("Stage 8 interface preview — no live Admin actions yet.", color=Color.Gray, textAlign=TextAlign.Center)
        Spacer(Modifier.height(20.dp))
        actions.chunked(2).forEach { row ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement=Arrangement.spacedBy(10.dp)) {
                row.forEach { action ->
                    Button(onClick={open(action)}, modifier=Modifier.weight(1f).height(64.dp), contentPadding=PaddingValues(horizontal=6.dp), colors=ButtonDefaults.buttonColors(containerColor=Color(0xFF146B3A), contentColor=Color.White)) {
                        Text(action, fontWeight=FontWeight.Bold, textAlign=TextAlign.Center)
                    }
                }
            }
            Spacer(Modifier.height(10.dp))
        }
    }
}

@Composable
fun CreateAdminPage(back: () -> Unit) {
    var fullName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var tempPassword by remember { mutableStateOf("") }
    val validUsername = username.length >= 4 && username.all { it.isLetterOrDigit() }
    val validPassword = tempPassword.length >= 4
    val ready = fullName.isNotBlank() && validUsername && validPassword

    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp), horizontalAlignment=Alignment.CenterHorizontally) {
        Spacer(Modifier.height(28.dp))
        TextButton(onClick=back, modifier=Modifier.align(Alignment.Start)) { Text("← BACK") }
        Text("CREATE ADMIN", style=MaterialTheme.typography.headlineLarge, fontWeight=FontWeight.Bold, color=Color(0xFFD4AF37), textAlign=TextAlign.Center, modifier=Modifier.fillMaxWidth())
        Spacer(Modifier.height(6.dp))
        Text("Create another Admin account. Permissions will be assigned separately under ADMIN PERMISSIONS.", color=Color.Gray, textAlign=TextAlign.Center)
        Spacer(Modifier.height(18.dp))

        OutlinedTextField(value=fullName, onValueChange={fullName=it}, label={Text("Admin Full Name")}, modifier=Modifier.fillMaxWidth())
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(value=username, onValueChange={v -> if (v.all { it.isLetterOrDigit() }) username=v}, label={Text("Admin Username")}, modifier=Modifier.fillMaxWidth())
        Text("Use at least 4 letters or numbers.", color=Color.Gray, style=MaterialTheme.typography.bodySmall, modifier=Modifier.fillMaxWidth())
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(value=tempPassword, onValueChange={tempPassword=it}, label={Text("Temporary Password")}, modifier=Modifier.fillMaxWidth())
        Text("Temporary password is for initial Admin access. Secure password handling will be connected with backend authentication.", color=Color.Gray, style=MaterialTheme.typography.bodySmall, modifier=Modifier.fillMaxWidth())
        Spacer(Modifier.height(20.dp))
        Button(onClick={}, enabled=ready, modifier=Modifier.height(46.dp), contentPadding=PaddingValues(horizontal=24.dp, vertical=6.dp), colors=ButtonDefaults.buttonColors(containerColor=Color(0xFFD4AF37), contentColor=Color.Black)) {
            Text("CREATE ADMIN", fontWeight=FontWeight.Bold)
        }
        Spacer(Modifier.height(16.dp))
        Text("Stage 13 interface only — no Admin account is created until the backend and authentication system are connected.", color=Color.Gray, textAlign=TextAlign.Center)
    }
}

@Composable
fun AdminPermissionsPage(back: () -> Unit) {
    var adminUsername by remember { mutableStateOf("") }
    var searched by remember { mutableStateOf(false) }
    var createMember by remember { mutableStateOf(false) }
    var creditCash by remember { mutableStateOf(false) }
    var approvals by remember { mutableStateOf(false) }
    var members by remember { mutableStateOf(false) }
    var createAdmin by remember { mutableStateOf(false) }
    var managePermissions by remember { mutableStateOf(false) }
    val validUsername = adminUsername.length >= 4 && adminUsername.all { it.isLetterOrDigit() }

    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp), horizontalAlignment=Alignment.CenterHorizontally) {
        Spacer(Modifier.height(28.dp))
        TextButton(onClick=back, modifier=Modifier.align(Alignment.Start)) { Text("← BACK") }
        Text("ADMIN PERMISSIONS", style=MaterialTheme.typography.headlineLarge, fontWeight=FontWeight.Bold, color=Color(0xFFD4AF37), textAlign=TextAlign.Center, modifier=Modifier.fillMaxWidth())
        Spacer(Modifier.height(6.dp))
        Text("Search an Admin account, then select the functions that Admin is allowed to use.", color=Color.Gray, textAlign=TextAlign.Center)
        Spacer(Modifier.height(18.dp))

        OutlinedTextField(value=adminUsername, onValueChange={v -> if(v.all { it.isLetterOrDigit() }) { adminUsername=v; searched=false }}, label={Text("Admin Username")}, modifier=Modifier.fillMaxWidth())
        Spacer(Modifier.height(10.dp))
        Button(onClick={searched=true}, enabled=validUsername, colors=ButtonDefaults.buttonColors(containerColor=Color(0xFF146B3A), contentColor=Color.White)) {
            Text("SEARCH ADMIN", fontWeight=FontWeight.Bold)
        }

        if (searched) {
            Spacer(Modifier.height(18.dp))
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.fillMaxWidth().padding(16.dp), horizontalAlignment=Alignment.CenterHorizontally) {
                    Text("ADMIN ACCOUNT", fontWeight=FontWeight.Bold, color=Color(0xFFD4AF37), style=MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.height(8.dp))
                    Text("Admin Full Name", fontWeight=FontWeight.Bold, style=MaterialTheme.typography.titleMedium)
                    Text("Username: $adminUsername", fontWeight=FontWeight.Bold)
                }
            }
            Spacer(Modifier.height(14.dp))
            Text("ALLOWED FUNCTIONS", fontWeight=FontWeight.Bold, style=MaterialTheme.typography.titleLarge, modifier=Modifier.fillMaxWidth())

            @Composable fun PermissionRow(label: String, checked: Boolean, change: (Boolean)->Unit) {
                Row(Modifier.fillMaxWidth(), verticalAlignment=Alignment.CenterVertically) {
                    Checkbox(checked=checked, onCheckedChange=change)
                    Text(label, fontWeight=FontWeight.Bold)
                }
            }
            PermissionRow("CREATE MEMBER", createMember) { createMember=it }
            PermissionRow("CREDIT CASH", creditCash) { creditCash=it }
            PermissionRow("APPROVALS", approvals) { approvals=it }
            PermissionRow("MEMBERS", members) { members=it }
            PermissionRow("CREATE ADMIN", createAdmin) { createAdmin=it }
            PermissionRow("ADMIN PERMISSIONS", managePermissions) { managePermissions=it }

            Spacer(Modifier.height(14.dp))
            Button(onClick={}, modifier=Modifier.height(46.dp), colors=ButtonDefaults.buttonColors(containerColor=Color(0xFFD4AF37), contentColor=Color.Black)) {
                Text("SAVE PERMISSIONS", fontWeight=FontWeight.Bold)
            }
            Spacer(Modifier.height(14.dp))
            Text("Stage 14 interface only. Permission selections will be enforced after secure backend authentication is connected. The main Admin will retain overall control.", color=Color.Gray, textAlign=TextAlign.Center)
        }
    }
}

@Composable
fun AdminPlaceholderPage(title: String, back: () -> Unit) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp), horizontalAlignment=Alignment.CenterHorizontally) {
        Spacer(Modifier.height(28.dp))
        TextButton(onClick=back, modifier=Modifier.align(Alignment.Start)) { Text("← BACK") }
        Text(title, style=MaterialTheme.typography.headlineLarge, fontWeight=FontWeight.Bold, color=Color(0xFFD4AF37), textAlign=TextAlign.Center, modifier=Modifier.fillMaxWidth())
        Spacer(Modifier.height(18.dp))
        Text("This Admin function will be implemented and tested separately in the next controlled step.", color=Color.Gray, textAlign=TextAlign.Center)
    }
}


@Composable
fun CreateMemberPage(back: () -> Unit) {
    var accountType by remember { mutableStateOf("REGULAR") }
    var fullName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var pin by remember { mutableStateOf("") }
    var linkRegular by remember { mutableStateOf("") }
    var linkChecked by remember { mutableStateOf(false) }
    val validUsername = if (accountType=="REGULAR") username.length==5 && username.all { it.isDigit() } else username.length==4 && username.startsWith("F") && username.drop(1).all { it.isDigit() }
    val validPin = pin.length==4 && pin.all { it.isDigit() }
    val ready = fullName.isNotBlank() && phone.isNotBlank() && validUsername && validPin
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp), horizontalAlignment=Alignment.CenterHorizontally) {
        Spacer(Modifier.height(28.dp)); TextButton(onClick=back,modifier=Modifier.align(Alignment.Start)){Text("← BACK")}
        Text("CREATE MEMBER",style=MaterialTheme.typography.headlineLarge,fontWeight=FontWeight.Bold,color=Color(0xFFD4AF37),textAlign=TextAlign.Center,modifier=Modifier.fillMaxWidth())
        Spacer(Modifier.height(14.dp))
        Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.spacedBy(10.dp)){ listOf("REGULAR","FLEXIBLE").forEach { type -> Button(onClick={accountType=type;username=""},modifier=Modifier.weight(1f),colors=ButtonDefaults.buttonColors(containerColor=if(accountType==type) Color(0xFFD4AF37) else Color(0xFF146B3A),contentColor=if(accountType==type) Color.Black else Color.White)){Text(type,fontWeight=FontWeight.Bold)} } }
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(fullName,{fullName=it},label={Text("Full Name")},modifier=Modifier.fillMaxWidth()); Spacer(Modifier.height(10.dp))
        OutlinedTextField(phone,{v->if(v.all{it.isDigit()||it=='+'})phone=v},label={Text("Phone Number")},modifier=Modifier.fillMaxWidth()); Spacer(Modifier.height(10.dp))
        OutlinedTextField(username,{v->val up=v.uppercase();if(accountType=="REGULAR"){if(up.length<=5&&up.all{it.isDigit()})username=up}else{if(up.length<=4&&(up.isEmpty()||(up.startsWith("F")&&up.drop(1).all{it.isDigit()})))username=up}},label={Text(if(accountType=="REGULAR")"5-digit Numeric Username" else "Flexible Username (F + 3 digits)")},modifier=Modifier.fillMaxWidth()); Spacer(Modifier.height(10.dp))
        OutlinedTextField(pin,{v->if(v.length<=4&&v.all{it.isDigit()})pin=v},label={Text("4-digit Numeric PIN")},modifier=Modifier.fillMaxWidth()); Spacer(Modifier.height(18.dp))
        if(accountType=="FLEXIBLE"){
            OutlinedTextField(linkRegular,{v->if(v.length<=5&&v.all{it.isDigit()}){linkRegular=v;linkChecked=false}},label={Text("Link to Regular Username (optional)")},modifier=Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            Button(onClick={linkChecked=true},enabled=linkRegular.length==5,colors=ButtonDefaults.buttonColors(containerColor=Color(0xFF146B3A),contentColor=Color.White)){Text("SEARCH REGULAR MEMBER",fontWeight=FontWeight.Bold)}
            if(linkChecked){Spacer(Modifier.height(8.dp));Text("Regular Member Full Name • $linkRegular",color=Color(0xFF146B3A),fontWeight=FontWeight.Bold,textAlign=TextAlign.Center)}
            Spacer(Modifier.height(12.dp))
        }
        Button(onClick={},enabled=ready,colors=ButtonDefaults.buttonColors(containerColor=Color(0xFFD4AF37),contentColor=Color.Black)){Text("CREATE ACCOUNT",fontWeight=FontWeight.Bold)}
        Spacer(Modifier.height(14.dp)); Text(if(accountType=="REGULAR")"Regular username: exactly 5 numeric digits. Login PIN: exactly 4 numeric digits." else "Flexible username: F followed by exactly 3 numeric digits. Login PIN: exactly 4 numeric digits.",color=Color.Gray,textAlign=TextAlign.Center)
        Text("The Admin-generated PIN is TEMPORARY. On the first successful login, the user must change it before accessing the account. Interface only until backend authentication is connected.",color=Color.Gray,textAlign=TextAlign.Center)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreditCashPage(back: () -> Unit) {
    var accountType by remember { mutableStateOf("REGULAR") }
    var username by remember { mutableStateOf("") }
    var accountChecked by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var amount by remember { mutableStateOf("") }
    val regularDestinations = listOf("REGULAR","TARGET","CONSTANT","WELFARE","FLEXIBLE","LOAN","INTEREST","REGISTRATION")
    val destinations = if(accountType=="REGULAR") regularDestinations else listOf("FLEXIBLE")
    val usernameValid = if(accountType=="REGULAR") username.length==5 && username.all{it.isDigit()} else username.length==4 && username.startsWith("F") && username.drop(1).all{it.isDigit()}
    val amountValid = amount.toLongOrNull()?.let{it>0}==true
    val ready = accountChecked && selected.isNotBlank() && amountValid

    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),horizontalAlignment=Alignment.CenterHorizontally){
        Spacer(Modifier.height(28.dp));TextButton(onClick=back,modifier=Modifier.align(Alignment.Start)){Text("← BACK")}
        Text("CREDIT CASH",style=MaterialTheme.typography.headlineLarge,fontWeight=FontWeight.Bold,color=Color(0xFFD4AF37),textAlign=TextAlign.Center,modifier=Modifier.fillMaxWidth())
        Spacer(Modifier.height(14.dp))
        Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.spacedBy(10.dp)){listOf("REGULAR","FLEXIBLE").forEach{type->Button(onClick={accountType=type;username="";accountChecked=false;selected=if(type=="FLEXIBLE")"FLEXIBLE" else "";amount=""},modifier=Modifier.weight(1f),colors=ButtonDefaults.buttonColors(containerColor=if(accountType==type)Color(0xFFD4AF37) else Color(0xFF146B3A),contentColor=if(accountType==type)Color.Black else Color.White)){Text(type,fontWeight=FontWeight.Bold)}}}
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(username,{v->val up=v.uppercase();if(accountType=="REGULAR"){if(up.length<=5&&up.all{it.isDigit()}){username=up;accountChecked=false}}else{if(up.length<=4&&(up.isEmpty()||(up.startsWith("F")&&up.drop(1).all{it.isDigit()}))){username=up;accountChecked=false}}},label={Text(if(accountType=="REGULAR")"Member Username (5 digits)" else "Saver Username (F + 3 digits)")},modifier=Modifier.fillMaxWidth())
        Spacer(Modifier.height(10.dp));Button(onClick={accountChecked=true;if(accountType=="FLEXIBLE")selected="FLEXIBLE"},enabled=usernameValid,colors=ButtonDefaults.buttonColors(containerColor=Color(0xFF146B3A),contentColor=Color.White)){Text(if(accountType=="REGULAR")"SEARCH MEMBER" else "SEARCH SAVER",fontWeight=FontWeight.Bold)}
        if(accountChecked){Spacer(Modifier.height(12.dp));Card(Modifier.fillMaxWidth()){Column(Modifier.padding(14.dp),horizontalAlignment=Alignment.CenterHorizontally){Text(if(accountType=="REGULAR")"MEMBER FOUND" else "SAVER FOUND",fontWeight=FontWeight.Bold,color=Color(0xFF146B3A));Spacer(Modifier.height(6.dp));Text("FULL NAME",fontWeight=FontWeight.Bold);Text(if(accountType=="REGULAR")"Member Full Name" else "Flexible Saver Full Name",color=Color(0xFF146B3A),fontWeight=FontWeight.Bold,style=MaterialTheme.typography.titleLarge,textAlign=TextAlign.Center);Spacer(Modifier.height(6.dp));Text("USERNAME",fontWeight=FontWeight.Bold);Text(username,color=Color(0xFF146B3A),fontWeight=FontWeight.Bold,style=MaterialTheme.typography.titleLarge)}}
            Spacer(Modifier.height(14.dp))
            if(accountType=="REGULAR"){ExposedDropdownMenuBox(expanded=expanded,onExpandedChange={expanded=!expanded}){OutlinedTextField(selected,{},readOnly=true,label={Text("Credit To")},trailingIcon={ExposedDropdownMenuDefaults.TrailingIcon(expanded=expanded)},modifier=Modifier.menuAnchor().fillMaxWidth());ExposedDropdownMenu(expanded=expanded,onDismissRequest={expanded=false}){destinations.forEach{item->DropdownMenuItem(text={Text(item)},onClick={selected=item;expanded=false})}}}} else {OutlinedTextField(selected,{},readOnly=true,label={Text("Credit To")},modifier=Modifier.fillMaxWidth())}
            Spacer(Modifier.height(12.dp));OutlinedTextField(amount,{v->if(v.all{it.isDigit()})amount=v},label={Text("Cash Amount (₦)")},modifier=Modifier.fillMaxWidth());Spacer(Modifier.height(18.dp))
            Button(onClick={},enabled=ready,colors=ButtonDefaults.buttonColors(containerColor=Color(0xFFD4AF37),contentColor=Color.Black)){Text("SUBMIT FOR APPROVAL",fontWeight=FontWeight.Bold)}
        }
        Spacer(Modifier.height(16.dp));Text("All cash-credit submissions go to Admin approval. No balance changes until an authorized Admin approves the submission.",color=Color.Gray,textAlign=TextAlign.Center)
    }
}

@Composable
fun ApprovalsPage(back: () -> Unit) {
    var selectedType by remember { mutableStateOf("BANK TRANSFER") }
    val types=listOf("BANK TRANSFER","CASH CREDIT","WITHDRAWAL","FLEXIBLE TRANSFER")
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),horizontalAlignment=Alignment.CenterHorizontally){
        Spacer(Modifier.height(28.dp));TextButton(onClick=back,modifier=Modifier.align(Alignment.Start)){Text("← BACK")}
        Text("APPROVALS",style=MaterialTheme.typography.headlineLarge,fontWeight=FontWeight.Bold,color=Color(0xFFD4AF37),textAlign=TextAlign.Center,modifier=Modifier.fillMaxWidth())
        Spacer(Modifier.height(6.dp));Text("Select a submission type to review.",color=Color.Gray,textAlign=TextAlign.Center);Spacer(Modifier.height(14.dp))
        types.chunked(2).forEach{row->Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.spacedBy(8.dp)){row.forEach{type->Button(onClick={selectedType=type},modifier=Modifier.weight(1f),contentPadding=PaddingValues(horizontal=4.dp,vertical=4.dp),colors=ButtonDefaults.buttonColors(containerColor=if(selectedType==type)Color(0xFFD4AF37) else Color(0xFF146B3A),contentColor=if(selectedType==type)Color.Black else Color.White)){Text(type,fontWeight=FontWeight.Bold,textAlign=TextAlign.Center,style=MaterialTheme.typography.bodySmall)}}};Spacer(Modifier.height(8.dp))}
        Spacer(Modifier.height(8.dp))
        Card(Modifier.fillMaxWidth()){Column(Modifier.padding(14.dp)){
            Text("PENDING $selectedType",fontWeight=FontWeight.Bold,color=Color(0xFFD4AF37),modifier=Modifier.fillMaxWidth(),textAlign=TextAlign.Center);Spacer(Modifier.height(10.dp))
            Text("FULL NAME",fontWeight=FontWeight.Bold);Text(if(selectedType=="FLEXIBLE TRANSFER")"Flexible Saver Full Name" else "Account Holder Full Name",color=Color(0xFF146B3A),fontWeight=FontWeight.Bold,style=MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(6.dp));Text("USERNAME",fontWeight=FontWeight.Bold);Text(if(selectedType=="FLEXIBLE TRANSFER")"F001" else "12345",color=Color(0xFF146B3A),fontWeight=FontWeight.Bold,style=MaterialTheme.typography.titleLarge);Spacer(Modifier.height(10.dp))
            when(selectedType){
                "BANK TRANSFER"->{Text("SUBMISSION DETAILS",fontWeight=FontWeight.Bold);Text("Amount: ₦10,000\nDestination: REGULAR\nReceipt: Payment receipt attached");Spacer(Modifier.height(12.dp));Button(onClick={},modifier=Modifier.align(Alignment.CenterHorizontally),colors=ButtonDefaults.buttonColors(containerColor=Color(0xFF146B3A),contentColor=Color.White)){Text("VIEW RECEIPT",fontWeight=FontWeight.Bold)}}
                "CASH CREDIT"->{Text("SUBMISSION DETAILS",fontWeight=FontWeight.Bold);Text("Account Type: REGULAR / FLEXIBLE\nAmount: ₦10,000\nCredit To: selected destination")}
                "WITHDRAWAL"->{Text("SUBMISSION DETAILS",fontWeight=FontWeight.Bold);Text("Amount: ₦10,000\nAccount Type: REGULAR / FLEXIBLE\nApplicable charge: calculated by account rules")}
                "FLEXIBLE TRANSFER"->{Text("SUBMISSION DETAILS",fontWeight=FontWeight.Bold);Text("From: FLEXIBLE\nAmount: ₦10,000\nTransfer To: selected linked Regular-account destination\nCharge: NONE")}
            }
            Spacer(Modifier.height(14.dp));Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.Center){Button(onClick={},colors=ButtonDefaults.buttonColors(containerColor=Color(0xFFD4AF37),contentColor=Color.Black)){Text("APPROVE",fontWeight=FontWeight.Bold)};Spacer(Modifier.width(12.dp));OutlinedButton(onClick={}){Text("REJECT",fontWeight=FontWeight.Bold)}}
        }}
        Spacer(Modifier.height(16.dp))
        Text(when(selectedType){
            "BANK TRANSFER"->"Approval credits the verified amount automatically to the selected destination. The same transfer must never be credited manually."
            "CASH CREDIT"->"Cash credit remains pending until an authorized Admin approves it; approval then credits the selected account destination."
            "WITHDRAWAL"->"Withdrawal approval will debit the account according to its approved withdrawal rules and applicable charge."
            else->"Flexible transfer has no withdrawal condition or charge. Approval moves the amount from Flexible balance to the selected linked Regular-account destination."
        },color=Color.Gray,textAlign=TextAlign.Center)
        Spacer(Modifier.height(6.dp));Text("Interface preview only — live pending submissions and balance changes will be connected through the backend.",color=Color.Gray,textAlign=TextAlign.Center)
    }
}

@Composable
fun MembersPage(back: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var searched by remember { mutableStateOf(false) }
    val validUsername = username.length == 5 && username.all { it.isDigit() }

    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp), horizontalAlignment=Alignment.CenterHorizontally) {
        Spacer(Modifier.height(28.dp))
        TextButton(onClick=back, modifier=Modifier.align(Alignment.Start)) { Text("← BACK") }
        Text("MEMBERS", style=MaterialTheme.typography.headlineLarge, fontWeight=FontWeight.Bold, color=Color(0xFFD4AF37), textAlign=TextAlign.Center, modifier=Modifier.fillMaxWidth())
        Spacer(Modifier.height(6.dp))
        Text("Search a member account by the 5-digit username.", color=Color.Gray, textAlign=TextAlign.Center)
        Spacer(Modifier.height(18.dp))

        OutlinedTextField(
            value=username,
            onValueChange={ value ->
                if (value.length <= 5 && value.all { it.isDigit() }) {
                    username=value
                    searched=false
                }
            },
            label={Text("Member Username (5 digits)")},
            modifier=Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(10.dp))
        Button(
            onClick={searched=true},
            enabled=validUsername,
            modifier=Modifier.height(44.dp),
            contentPadding=PaddingValues(horizontal=18.dp, vertical=4.dp),
            colors=ButtonDefaults.buttonColors(containerColor=Color(0xFF146B3A), contentColor=Color.White)
        ) { Text("SEARCH MEMBER", fontWeight=FontWeight.Bold) }

        if (searched) {
            Spacer(Modifier.height(16.dp))
            Card(Modifier.fillMaxWidth().height(72.dp)) {
                Box(Modifier.fillMaxSize(), contentAlignment=Alignment.Center) {
                    Text("MEMBER DASHBOARD", style=MaterialTheme.typography.headlineLarge, fontWeight=FontWeight.Bold, color=Color(0xFFD4AF37), textAlign=TextAlign.Center, modifier=Modifier.fillMaxWidth())
                }
            }
            Spacer(Modifier.height(12.dp))
            Text("Member Full Name", color=Color(0xFF146B3A), fontWeight=FontWeight.Bold, style=MaterialTheme.typography.titleLarge, textAlign=TextAlign.Center, modifier=Modifier.fillMaxWidth())
            Text("Username: $username", fontWeight=FontWeight.Bold, textAlign=TextAlign.Center, modifier=Modifier.fillMaxWidth())
            Spacer(Modifier.height(16.dp))

            val fields = listOf(
                "REGISTRATION" to "₦0",
                "REGULAR" to "₦0",
                "TARGET" to "₦0",
                "CONSTANT" to "₦0",
                "WELFARE" to "₦0",
                "FLEXIBLE" to "₦0",
                "TOTAL SAVINGS — CURRENT MONTH" to "₦0",
                "NUMBER OF SHARES" to "0",
                "DIVIDEND — PREVIOUS MONTH" to "₦0",
                "ACTIVE LOAN" to "₦0",
                "PAYMENT DUE DATE" to "—",
                "LOAN INTEREST" to "₦0"
            )
            fields.chunked(2).forEach { pair ->
                Row(Modifier.fillMaxWidth(), horizontalArrangement=Arrangement.spacedBy(10.dp)) {
                    pair.forEach { (label,value) ->
                        Card(Modifier.weight(1f).height(92.dp)) {
                            Column(Modifier.fillMaxSize().padding(8.dp), verticalArrangement=Arrangement.Center, horizontalAlignment=Alignment.CenterHorizontally) {
                                Text(label, fontWeight=FontWeight.Bold, style=MaterialTheme.typography.titleMedium, textAlign=TextAlign.Center, modifier=Modifier.fillMaxWidth())
                                Spacer(Modifier.height(6.dp))
                                Text(value, color=Color(0xFF146B3A), fontWeight=FontWeight.Bold, style=MaterialTheme.typography.titleLarge, textAlign=TextAlign.Center, modifier=Modifier.fillMaxWidth())
                            }
                        }
                    }
                }
                Spacer(Modifier.height(10.dp))
            }
            Text("Stage 12 preview uses placeholder member name and values. Backend search will display the searched member's exact dashboard data.", color=Color.Gray, textAlign=TextAlign.Center)
        }
    }
}
