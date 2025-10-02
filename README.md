# Simple HTTPD Android app (port 3456)

This project is a minimal Android app that runs a tiny HTTP server (NanoHTTPD) on port 3456. It starts the server when the main activity starts and stops it on destroy.

How to build
1. Install the Android SDK and JDK 17 (or compatible).
2. Open the project in Android Studio and run it on a device/emulator.
3. Or build from the command line:
   - ./gradlew assembleDebug
   - The APK will be in `app/build/outputs/apk/debug/app-debug.apk`.

How to use
- Launch the app on a device.
- The server listens on port 3456 on the device's network interfaces.
- From another device on the same network, open: `http://<device-ip>:3456/`
- You should get a simple "Hello from NanoHTTPD on port 3456" response.

Notes
- The app requests the INTERNET permission.
- On modern Android versions, background/foreground networking behavior and firewall/NAT rules may affect access from other devices.