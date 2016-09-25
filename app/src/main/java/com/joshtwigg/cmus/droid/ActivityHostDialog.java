package com.joshtwigg.cmus.droid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

/**
 * Created by josh on 31/01/14.
 */
public class ActivityHostDialog extends Activity {

    private static final String INTENT_EXTRA_HOST = "HOST_ADDRESS";

    private String _hostAddress = "";
    private EditText _host;
    private EditText _port;
    private EditText _password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_dialog);
        _host = (EditText) findViewById(R.id.host);
        _port = (EditText) findViewById(R.id.port);
        _password = (EditText) findViewById(R.id.password);
        // check if we have a host
        _hostAddress = getIntent().getStringExtra(INTENT_EXTRA_HOST);
        if (_hostAddress == null || _hostAddress.equals("")) {
            // type new address
            Log.d(getClass().getSimpleName(), "host is empty.");
            _port.setText(getResources().getText(R.integer.default_port).toString());
        } else {
            // load saved or default values
            _host.setText(_hostAddress);
            int port = Storage.getPort(this, _hostAddress);
            String password = Storage.getPassword(this, _hostAddress);
            _port.setText(String.valueOf(port));
            _password.setText(password);
            Log.d(getClass().getSimpleName(), String.format("host{%s}port{%s}passwordlen{%s}", _hostAddress, port, password.length()));
        }
    }

    public void onClickOkay(View view) {
        String newHost = _host.getText().toString();
        String password = _password.getText().toString();
        int port;

        if (newHost.length() < 1) {
            AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setTitle("Problem");
            b.setMessage("You must specify a host. Try the IP address of your machine.");
            b.setPositiveButton("Okay", null);
            b.show();
            return;
        }

        try {
            port = Integer.parseInt(_port.getText().toString());
        } catch (Exception e) {
            AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setTitle("Problem");
            b.setMessage(String.format("You must specify a port, the default is %s.",
                    getResources().getText(R.integer.default_port)));
            b.setPositiveButton("Okay", null);
            b.show();
            return;
        }

        Storage.save(this, _hostAddress, newHost, port, password);
        setResult(RESULT_OK);
        finish();
    }

    public void onClickCancel(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public static Intent getStartIntent(Context context, String hostAddress) {
        Intent intent = new Intent(context, ActivityHostDialog.class);
        intent.putExtra(INTENT_EXTRA_HOST, hostAddress);
        return intent;
    }
}
