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
import androidx.compose.ui.unit.dp
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
                "MEMBER DASHBOARD" -> MemberDashboardPage(open = { page = it }, back = { page = "MEMBERSHIP" })
                "PAY" -> PayPage { page = "MEMBER DASHBOARD" }
                "TRANSACTION HISTORY" -> MemberPlaceholderPage("TRANSACTION HISTORY") { page = "MEMBER DASHBOARD" }
                "WITHDRAW" -> MemberPlaceholderPage("WITHDRAW") { page = "MEMBER DASHBOARD" }
                "APPLY FOR LOAN" -> MemberPlaceholderPage("APPLY FOR LOAN") { page = "MEMBER DASHBOARD" }
                else -> InfoPage(page) { page = "HOME" }
            }
        }
    }
}

@Composable
fun HomePage(open: (String) -> Unit) {
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
                Text("BANK TRANSFER DETAILS", fontWeight=FontWeight.Bold, color=Color(0xFFD4AF37))
                Spacer(Modifier.height(6.dp))
                Text("Account Number: 1027050172\nBank Name: FCMB\nAccount Name: TAIMAKO MULTI-PURPOSE COOPERATIVE SOCIETY LTD", fontWeight=FontWeight.Bold)
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
            modifier=Modifier.fillMaxWidth(),
            colors=ButtonDefaults.buttonColors(containerColor=Color(0xFF146B3A), contentColor=Color.White)
        ) { Text(if(receiptName.isBlank()) "UPLOAD PAYMENT RECEIPT" else "RECEIPT SELECTED ✓") }

        Spacer(Modifier.height(8.dp))
        Text("Payment receipt is required before submission.", color=Color.Gray)
        Spacer(Modifier.height(16.dp))
        Button(
            onClick={},
            enabled=amount.isNotBlank() && amount.toLongOrNull()?.let { it > 0 } == true && selected.isNotBlank() && receiptName.isNotBlank(),
            modifier=Modifier.fillMaxWidth().height(54.dp),
            colors=ButtonDefaults.buttonColors(containerColor=Color(0xFFD4AF37), contentColor=Color.Black)
        ) { Text("SUBMIT", fontWeight=FontWeight.Bold) }
        Spacer(Modifier.height(10.dp))
        Text("Stage 4 interface test only. SUBMIT does not send or credit money yet.", color=Color.Gray)
    }
}
