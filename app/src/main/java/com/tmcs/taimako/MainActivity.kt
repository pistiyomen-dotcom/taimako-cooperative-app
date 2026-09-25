package com.tmcs.taimako

import android.os.Bundle
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
                "MEMBERSHIP" -> MembershipPage(open = { page = it }, back = { page = "HOME" })
                "LOGIN" -> LoginPage(login = { page = "MEMBER DASHBOARD" }, back = { page = "MEMBERSHIP" })
                "REGISTER" -> RegisterPage(contact = { page = "CONTACT US" }, back = { page = "MEMBERSHIP" })
                "ADMIN LOGIN" -> AdminLoginPage(login = { page = "ADMIN DASHBOARD" }, back = { page = "HOME" })
                "ADMIN DASHBOARD" -> AdminDashboardPage(open = { page = it }, back = { page = "ADMIN LOGIN" })
                "CREATE MEMBER" -> CreateMemberPage { page = "ADMIN DASHBOARD" }
                "CREDIT CASH" -> CreditCashPage { page = "ADMIN DASHBOARD" }
                "APPROVALS" -> ApprovalsPage { page = "ADMIN DASHBOARD" }
                "MEMBERS" -> MembersPage { page = "ADMIN DASHBOARD" }
                "CREATE ADMIN" -> CreateAdminPage { page = "ADMIN DASHBOARD" }
                "ADMIN PERMISSIONS" -> AdminPermissionsPage { page = "ADMIN DASHBOARD" }
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
    val menus = listOf("SAVINGS","LOAN","INVESTMENT","AGRICULTURE","FLEXIBLE","MEMBERSHIP","BYE-LAW","ABOUT US","CONTACT US","ADMIN LOGIN")
    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(30.dp))
        Text("TAIMAKO MULTIPURPOSE COOPERATIVE SOCIETY LTD", style=MaterialTheme.typography.headlineSmall, fontWeight=FontWeight.Bold, color=Color(0xFF146B3A))
        Spacer(Modifier.height(14.dp))
        Text("Welcome to TMCS LTD", style=MaterialTheme.typography.titleLarge, fontWeight=FontWeight.Bold, color=Color(0xFFD4AF37))
        Text("A cooperative movement for achieving financial independence.")
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
            "SAVINGS" -> SavingsPage()
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
fun SavingsPage() {
    Text("Savings plans for registered cooperative members.", fontWeight=FontWeight.Bold)
    Spacer(Modifier.height(12.dp))
    Text("REGULAR\nTARGET\nCONSTANT\nWELFARE\nFLEXIBLE")
    Spacer(Modifier.height(12.dp))
    Text("Stage 1 navigation is active. Detailed individual savings pages will be added in the next controlled step.")
}

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
fun LoginPage(login: () -> Unit, back: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp)) {
        Spacer(Modifier.height(28.dp))
        TextButton(onClick=back) { Text("← BACK") }
        Text("MEMBER LOGIN", style=MaterialTheme.typography.headlineMedium, fontWeight=FontWeight.Bold, color=Color(0xFFD4AF37))
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(username,{ if (it.length <= 5 && it.all(Char::isDigit)) username=it },label={Text("5-digit Username")},modifier=Modifier.fillMaxWidth())
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(password,{password=it},label={Text("Password")},modifier=Modifier.fillMaxWidth())
        Spacer(Modifier.height(16.dp))
        Button(onClick=login, enabled=username.length==5 && password.isNotBlank(), modifier=Modifier.fillMaxWidth(), colors=ButtonDefaults.buttonColors(containerColor=Color(0xFFD4AF37), contentColor=Color.Black)) { Text("LOGIN") }
        Spacer(Modifier.height(12.dp))
        Text("STAGE 3 TEST LOGIN: Enter any 5-digit username and any non-empty password to open the Member Dashboard. Real authentication is not connected yet.", color=Color.Gray)
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
    Text("TMCS LTD BYE-LAW", fontWeight=FontWeight.Bold, color=Color(0xFFD4AF37))
    Spacer(Modifier.height(12.dp))
    Text("The cooperative Bye-law will be presented here in clearly arranged sections for public reading.")
    Spacer(Modifier.height(12.dp))
    Text("The full approved Bye-law text will be entered in a controlled step so that the wording is preserved accurately.")
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
    var fullName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var tempPassword by remember { mutableStateOf("") }
    val validUsername = username.length == 5 && username.all { it.isDigit() }
    val validPassword = tempPassword.length == 4 && tempPassword.all { it.isDigit() }
    val ready = fullName.isNotBlank() && phone.isNotBlank() && validUsername && validPassword

    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(28.dp))
        TextButton(onClick=back, modifier=Modifier.align(Alignment.Start)) { Text("← BACK") }
        Text("CREATE MEMBER", style=MaterialTheme.typography.headlineLarge, fontWeight=FontWeight.Bold, color=Color(0xFFD4AF37), textAlign=TextAlign.Center, modifier=Modifier.fillMaxWidth())
        Spacer(Modifier.height(6.dp))
        Text("Stage 9 interface test — member account is not created in the backend yet.", color=Color.Gray, textAlign=TextAlign.Center)
        Spacer(Modifier.height(18.dp))

        OutlinedTextField(
            value=fullName,
            onValueChange={fullName=it},
            label={Text("Member Full Name")},
            modifier=Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(
            value=phone,
            onValueChange={ value -> if (value.all { it.isDigit() || it == '+' }) phone=value },
            label={Text("Phone Number")},
            modifier=Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(
            value=username,
            onValueChange={ value -> if (value.length <= 5 && value.all { it.isDigit() }) username=value },
            label={Text("5-digit Username")},
            modifier=Modifier.fillMaxWidth()
        )
        Text("Username must contain exactly 5 numeric digits.", color=Color.Gray, style=MaterialTheme.typography.bodySmall, modifier=Modifier.fillMaxWidth())
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(
            value=tempPassword,
            onValueChange={ value -> if (value.length <= 4 && value.all { it.isDigit() }) tempPassword=value },
            label={Text("4-digit Temporary Password")},
            modifier=Modifier.fillMaxWidth()
        )
        Text("Temporary password is intended for one successful login only. The member will be required to change it after first login when backend authentication is connected.", color=Color.Gray, style=MaterialTheme.typography.bodySmall, modifier=Modifier.fillMaxWidth())

        Spacer(Modifier.height(18.dp))
        Button(
            onClick={},
            enabled=ready,
            modifier=Modifier.height(44.dp),
            contentPadding=PaddingValues(horizontal=20.dp, vertical=4.dp),
            colors=ButtonDefaults.buttonColors(containerColor=Color(0xFFD4AF37), contentColor=Color.Black)
        ) { Text("CREATE MEMBER", fontWeight=FontWeight.Bold) }

        Spacer(Modifier.height(14.dp))
        Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(14.dp), horizontalAlignment=Alignment.CenterHorizontally) {
                Text("ACCOUNT RULES", fontWeight=FontWeight.Bold, color=Color(0xFFD4AF37))
                Spacer(Modifier.height(6.dp))
                Text("• Member username: exactly 5 numeric digits.\n• Initial password: exactly 4 numeric digits.\n• Temporary password will expire after the first successful login.\n• Admin will not be able to read the member's new personal password.", textAlign=TextAlign.Start, modifier=Modifier.fillMaxWidth())
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreditCashPage(back: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var memberChecked by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var amount by remember { mutableStateOf("") }
    val destinations = listOf("REGULAR","TARGET","CONSTANT","WELFARE","FLEXIBLE","LOAN","INTEREST","REGISTRATION")
    val usernameValid = username.length == 5 && username.all { it.isDigit() }
    val amountValid = amount.toLongOrNull()?.let { it > 0 } == true
    val ready = memberChecked && selected.isNotBlank() && amountValid

    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp), horizontalAlignment=Alignment.CenterHorizontally) {
        Spacer(Modifier.height(28.dp))
        TextButton(onClick=back, modifier=Modifier.align(Alignment.Start)) { Text("← BACK") }
        Text("CREDIT CASH", style=MaterialTheme.typography.headlineLarge, fontWeight=FontWeight.Bold, color=Color(0xFFD4AF37), textAlign=TextAlign.Center, modifier=Modifier.fillMaxWidth())
        Spacer(Modifier.height(6.dp))
        Text("Office cash payment — Stage 10 interface test only.", color=Color.Gray, textAlign=TextAlign.Center)
        Spacer(Modifier.height(18.dp))

        OutlinedTextField(
            value=username,
            onValueChange={ value ->
                if (value.length <= 5 && value.all { it.isDigit() }) {
                    username=value
                    memberChecked=false
                }
            },
            label={Text("Member Username (5 digits)")},
            modifier=Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(10.dp))
        Button(
            onClick={memberChecked=true},
            enabled=usernameValid,
            modifier=Modifier.height(44.dp),
            contentPadding=PaddingValues(horizontal=18.dp, vertical=4.dp),
            colors=ButtonDefaults.buttonColors(containerColor=Color(0xFF146B3A), contentColor=Color.White)
        ) { Text("SEARCH MEMBER", fontWeight=FontWeight.Bold) }

        if (memberChecked) {
            Spacer(Modifier.height(12.dp))
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(14.dp), horizontalAlignment=Alignment.CenterHorizontally) {
                    Text("MEMBER FOUND", fontWeight=FontWeight.Bold, color=Color(0xFF146B3A))
                    Spacer(Modifier.height(6.dp))
                    Text("FULL NAME", fontWeight=FontWeight.Bold)
                    Text("Member Full Name", color=Color(0xFF146B3A), fontWeight=FontWeight.Bold, style=MaterialTheme.typography.titleLarge, textAlign=TextAlign.Center)
                    Spacer(Modifier.height(6.dp))
                    Text("USERNAME", fontWeight=FontWeight.Bold)
                    Text(username, color=Color(0xFF146B3A), fontWeight=FontWeight.Bold, style=MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.height(6.dp))
                    Text("Stage 10 preview uses a placeholder full name. Backend member search will display the exact full name and username stored on the member account dashboard.", color=Color.Gray, textAlign=TextAlign.Center)
                }
            }

            Spacer(Modifier.height(14.dp))
            ExposedDropdownMenuBox(expanded=expanded, onExpandedChange={expanded=!expanded}) {
                OutlinedTextField(
                    value=selected,
                    onValueChange={},
                    readOnly=true,
                    label={Text("Credit To")},
                    trailingIcon={ExposedDropdownMenuDefaults.TrailingIcon(expanded=expanded)},
                    modifier=Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(expanded=expanded, onDismissRequest={expanded=false}) {
                    destinations.forEach { item ->
                        DropdownMenuItem(text={Text(item)}, onClick={selected=item; expanded=false})
                    }
                }
            }

            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value=amount,
                onValueChange={ value -> if (value.all { it.isDigit() }) amount=value },
                label={Text("Cash Amount (₦)")},
                modifier=Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(18.dp))
            Button(
                onClick={},
                enabled=ready,
                modifier=Modifier.height(44.dp),
                contentPadding=PaddingValues(horizontal=20.dp, vertical=4.dp),
                colors=ButtonDefaults.buttonColors(containerColor=Color(0xFFD4AF37), contentColor=Color.Black)
            ) { Text("CONFIRM", fontWeight=FontWeight.Bold) }
        }

        Spacer(Modifier.height(16.dp))
        Text("Final system rule: office cash credit will post immediately after Admin confirmation. It will not require the transfer-receipt approval workflow. No balance is changed in this Stage 10 test build.", color=Color.Gray, textAlign=TextAlign.Center)
    }
}


@Composable
fun ApprovalsPage(back: () -> Unit) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp), horizontalAlignment=Alignment.CenterHorizontally) {
        Spacer(Modifier.height(28.dp))
        TextButton(onClick=back, modifier=Modifier.align(Alignment.Start)) { Text("← BACK") }
        Text("APPROVALS", style=MaterialTheme.typography.headlineLarge, fontWeight=FontWeight.Bold, color=Color(0xFFD4AF37), textAlign=TextAlign.Center, modifier=Modifier.fillMaxWidth())
        Spacer(Modifier.height(6.dp))
        Text("Stage 11 interface preview — no live submissions yet.", color=Color.Gray, textAlign=TextAlign.Center)
        Spacer(Modifier.height(18.dp))

        Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(14.dp)) {
                Text("PENDING BANK TRANSFER", fontWeight=FontWeight.Bold, color=Color(0xFFD4AF37), modifier=Modifier.fillMaxWidth(), textAlign=TextAlign.Center)
                Spacer(Modifier.height(10.dp))
                Text("FULL NAME", fontWeight=FontWeight.Bold)
                Text("Member Full Name", color=Color(0xFF146B3A), fontWeight=FontWeight.Bold, style=MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(6.dp))
                Text("USERNAME", fontWeight=FontWeight.Bold)
                Text("12345", color=Color(0xFF146B3A), fontWeight=FontWeight.Bold, style=MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(10.dp))
                Text("PAYMENT DETAILS", fontWeight=FontWeight.Bold)
                Text("Amount: ₦10,000\nPay For: REGULAR\nReceipt: Payment receipt attached")
                Spacer(Modifier.height(14.dp))
                Button(onClick={}, modifier=Modifier.align(Alignment.CenterHorizontally).height(42.dp), contentPadding=PaddingValues(horizontal=14.dp, vertical=4.dp), colors=ButtonDefaults.buttonColors(containerColor=Color(0xFF146B3A), contentColor=Color.White)) {
                    Text("VIEW RECEIPT", fontWeight=FontWeight.Bold)
                }
                Spacer(Modifier.height(14.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement=Arrangement.Center) {
                    Button(onClick={}, modifier=Modifier.height(42.dp), contentPadding=PaddingValues(horizontal=18.dp, vertical=4.dp), colors=ButtonDefaults.buttonColors(containerColor=Color(0xFFD4AF37), contentColor=Color.Black)) {
                        Text("APPROVE", fontWeight=FontWeight.Bold)
                    }
                    Spacer(Modifier.width(12.dp))
                    OutlinedButton(onClick={}, modifier=Modifier.height(42.dp), contentPadding=PaddingValues(horizontal=18.dp, vertical=4.dp)) {
                        Text("REJECT", fontWeight=FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))
        Text("Final workflow: APPROVE will automatically post the verified amount to the selected member account destination. Admin must not credit the same transfer manually. REJECT will leave the member balance unchanged.", color=Color.Gray, textAlign=TextAlign.Center)
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
