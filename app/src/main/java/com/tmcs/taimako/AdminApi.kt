package com.tmcs.taimako

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

internal suspend fun adminLogin(username: String, password: String): Pair<Int, JSONObject> = withContext(Dispatchers.IO) {
    val conn = URL("$TAIMAKO_API_URL/auth/login").openConnection() as HttpURLConnection
    try {
        conn.requestMethod = "POST"
        conn.connectTimeout = 15000
        conn.readTimeout = 15000
        conn.useCaches = false
        conn.doOutput = true
        conn.setRequestProperty("Content-Type", "application/json")
        val data = JSONObject().put("username", username).put("credential", password).toString()
        conn.outputStream.use { it.write(data.toByteArray(Charsets.UTF_8)) }
        val code = conn.responseCode
        val body = (if (code in 200..299) conn.inputStream else conn.errorStream)
            ?.bufferedReader()?.use { it.readText() } ?: "{}"
        code to JSONObject(body)
    } finally { conn.disconnect() }
}
