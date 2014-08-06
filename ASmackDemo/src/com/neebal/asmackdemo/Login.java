
package com.neebal.asmackdemo;

import org.jivesoftware.smack.XMPPConnection;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class Login extends Activity {

    //================================================
    // UI 
    //================================================

    //================================================
    // VARIABLES
    //================================================
    private XMPPConnection connection;

    //================================================
    // ANDROID ACTIVITY METHODS
    //================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    //================================================
    // CLASS METHODS
    //================================================
    public void onLogin(View v) {

    }

    /*public void connect() {

        final ProgressDialog dialog = ProgressDialog.show(this, "Connecting...", "Please wait...", false);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                // Create a connection
                ConnectionConfiguration connConfig = new ConnectionConfiguration(HOST, PORT, SERVICE);

                XMPPConnection connection = new XMPPTCPConnection(connConfig);

                try {
                    connection.connect();
                    Log.i("XMPPChatDemoActivity", "[SettingsDialog] Connected to " + connection.getHost());
                }
                catch (XMPPException ex) {
                    Log.e("XMPPChatDemoActivity", "[SettingsDialog] Failed to connect to " + connection.getHost());
                    Log.e("XMPPChatDemoActivity", ex.toString());
                    setConnection(null);
                }
                catch (SmackException ex) {
                    ex.printStackTrace();
                    Log.e("XMPPChatDemoActivity", "[SettingsDialog] Failed to connect to " + connection.getHost());
                    Log.e("XMPPChatDemoActivity", ex.toString());
                    setConnection(null);
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                    Log.e("XMPPChatDemoActivity", "[SettingsDialog] Failed to connect to " + connection.getHost());
                    Log.e("XMPPChatDemoActivity", ex.toString());
                    setConnection(null);
                }

                try {
                    connection.login(USERNAME, PASSWORD);
                    Log.i("XMPPChatDemoActivity", "Logged in as" + connection.getUser());

                    // Set the status to available
                    Presence presence = new Presence(Presence.Type.available);
                    connection.sendPacket(presence);
                    setConnection(connection);

                    Roster roster = connection.getRoster();
                    Collection<RosterEntry> entries = roster.getEntries();
                    for (RosterEntry entry : entries) {

                        Log.d("XMPPChatDemoActivity", "--------------------------------------");
                        Log.d("XMPPChatDemoActivity", "RosterEntry " + entry);
                        Log.d("XMPPChatDemoActivity", "User: " + entry.getUser());
                        Log.d("XMPPChatDemoActivity", "Name: " + entry.getName());
                        Log.d("XMPPChatDemoActivity", "Status: " + entry.getStatus());
                        Log.d("XMPPChatDemoActivity", "Type: " + entry.getType());
                        Presence entryPresence = roster.getPresence(entry.getUser());

                        Log.d("XMPPChatDemoActivity", "Presence Status: " + entryPresence.getStatus());
                        Log.d("XMPPChatDemoActivity", "Presence Type: " + entryPresence.getType());

                        Presence.Type type = entryPresence.getType();
                        if (type == Presence.Type.available)
                            Log.d("XMPPChatDemoActivity", "Presence AVIALABLE");
                        Log.d("XMPPChatDemoActivity", "Presence : " + entryPresence);
                    }
                }
                catch (XMPPException e) {
                    Log.e("XMPPChatDemoActivity", "Failed to log in as " + USERNAME);
                    Log.e("XMPPChatDemoActivity", e.toString());
                    setConnection(null);
                }
                catch (SaslException e) {
                    e.printStackTrace();
                    Log.e("XMPPChatDemoActivity", "Failed to log in as " + USERNAME);
                    Log.e("XMPPChatDemoActivity", e.toString());
                    setConnection(null);
                }
                catch (SmackException e) {
                    e.printStackTrace();
                    Log.e("XMPPChatDemoActivity", "Failed to log in as " + USERNAME);
                    Log.e("XMPPChatDemoActivity", e.toString());
                    setConnection(null);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    Log.e("XMPPChatDemoActivity", "Failed to log in as " + USERNAME);
                    Log.e("XMPPChatDemoActivity", e.toString());
                    setConnection(null);
                }

                dialog.dismiss();
            }
        });
        t.start();
        dialog.show();
    }*/
}
