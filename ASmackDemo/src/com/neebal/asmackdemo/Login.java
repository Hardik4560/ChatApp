
package com.neebal.asmackdemo;

import java.io.IOException;
import java.util.Collection;

import org.apache.harmony.javax.security.sasl.SaslException;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.util.StringUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.neebal.utils.SharedPrefs;

public class Login extends Activity {

    private static final String USERNAME = "user";
    private static final String PASSWORD = "pass";
    private static final String HOST = "host";
    private static final String PORT = "port";

    protected static final String SERVICE = "xmpp-service";
    //================================================
    // UI 
    //================================================
    private EditText edtUsername;
    private EditText edtPassword;
    private EditText edtHost;
    private EditText edtPort;

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

        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);

        edtHost = (EditText) findViewById(R.id.edtHost);
        edtPort = (EditText) findViewById(R.id.edtPort);

        SharedPrefs.initialize(this);
    }

    //================================================
    // CLASS METHODS
    //================================================
    public void onLogin(View v) {
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();

        String host = edtHost.getText().toString();
        String port = edtPort.getText().toString();

        if (TextUtils.isEmpty(username) ||
            TextUtils.isEmpty(password) ||
            TextUtils.isEmpty(host) ||
            TextUtils.isEmpty(port)) {

            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_LONG).show();
        }
        else {
            SharedPrefs.getInstance().add(USERNAME, username);
            SharedPrefs.getInstance().add(PASSWORD, password);
            SharedPrefs.getInstance().add(HOST, host);
            SharedPrefs.getInstance().add(PORT, Integer.valueOf(port));

            connect();
        }
    }

    public void connect() {
        final ProgressDialog dialog = ProgressDialog.show(this, "Connecting...", "Please wait...", false);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                // Create a connection
                ConnectionConfiguration connConfig = new ConnectionConfiguration((String) SharedPrefs.getInstance().get(HOST, ""),
                        (Integer) SharedPrefs.getInstance().get(PORT, Integer.valueOf(0)),
                        SERVICE);

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
    }

    /**
     * Called by Settings dialog when a connection is establised with 
     * the XMPP server
     */
    public void setConnection(XMPPConnection connection) {
        this.connection = connection;
    }
}
