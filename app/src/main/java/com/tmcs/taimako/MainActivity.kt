package com.tmcs.taimako

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.background
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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
            if (page == "HOME") HomePage { page = it } else InfoPage(page) { page = "HOME" }
        }
    }
}

@Composable
fun HomePage(open: (String) -> Unit) {
    val menus = listOf("SAVINGS","LOAN","INVESTMENT","AGRICULTURE","FLEXIBLE","MEMBERSHIP","ABOUT US","CONTACT US")
    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(30.dp))
        Text("TAIMAKO MULTIPURPOSE COOPERATIVE SOCIETY LTD", style=MaterialTheme.typography.headlineSmall, fontWeight=FontWeight.Bold, color=Color(0xFF146B3A))
        Spacer(Modifier.height(14.dp))
        Text("Welcome to TMCS LTD", style=MaterialTheme.typography.titleLarge, fontWeight=FontWeight.Bold, color=Color(0xFF146B3A))
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
            "MEMBERSHIP" -> Text("LOGIN\n\nREGISTER\n\nPublic self-registration is not available. REGISTER will lead to the Contact Us form. Member accounts are created by an Admin.")
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
