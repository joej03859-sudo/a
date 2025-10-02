package com.example.httpd

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.net.NetworkInterface
import java.util.*

class MainActivity : AppCompatActivity() {

    private var server: SimpleHttpServer? = null
    private val port = 3456

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.statusText).text = "Starting server on port $port..."

        // Start server
        try {
            server = SimpleHttpServer(port)
            server?.start()
            findViewById<TextView>(R.id.statusText).text =
                "Server running on port $port\nAccessible on local interfaces:\n${getLocalAddresses()}"
        } catch (e: Exception) {
            findViewById<TextView>(R.id.statusText).text = "Failed to start server: ${e.message}"
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            server?.stop()
            server = null
        } catch (ignored: Exception) {
        }
    }

    private fun getLocalAddresses(): String {
        val ips = mutableListOf<String>()
        try {
            val ifs = NetworkInterface.getNetworkInterfaces()
            while (ifs.hasMoreElements()) {
                val nif = ifs.nextElement()
                val addrs = nif.inetAddresses
                while (addrs.hasMoreElements()) {
                    val addr = addrs.nextElement()
                    if (!addr.isLoopbackAddress) {
                        val s = addr.hostAddress
                        // skip IPv6 link-local addresses for brevity
                        if (!s.contains(":")) {
                            ips.add(s)
                        }
                    }
                }
            }
        } catch (_: Exception) {}
        return if (ips.isEmpty()) "127.0.0.1" else ips.joinToString("\n")
    }
}