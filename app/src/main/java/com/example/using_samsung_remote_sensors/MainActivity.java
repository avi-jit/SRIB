package com.example.using_samsung_remote_sensors;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.samsung.android.remotesensor.RemoteSensorCallback;
import com.samsung.android.remotesensor.RemoteSensor;
import com.samsung.android.remotesensor.RemoteSensorEvent;
import com.samsung.android.remotesensor.RemoteSensorManager;
import com.samsung.android.remotesensor.RemoteSensorNode;
import com.samsung.android.sdk.SsdkUnsupportedException;


public class MainActivity extends Activity implements RemoteSensorCallback{

    private static final String TAG = "Remote Sensors";
	private boolean isMasterRunning=false,isNodeRunning=false;
    private RemoteSensorManager remoteSensorManager;
    private RemoteSensorNode remoteSensorNode;
    float acceleration[] = new float[3];
    float gyroscope[] = new float[3];
    float rotation[] = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
       remoteSensorManager=RemoteSensorManager.getInstance();
       initUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    private void initUI()
    {
				if(isMasterRunning==false)
				{
					try
					{
						remoteSensorManager.start(getApplicationContext(),MainActivity.this,Looper.getMainLooper());
						isMasterRunning=true;
                        Log.i(TAG, "master starts");
                        Toast.makeText(getApplicationContext(), "master start",
                                Toast.LENGTH_LONG).show();
                       // updateMasterStatus();
					}
					catch(SsdkUnsupportedException e){
						  Log.e(TAG, e.getMessage(), e)
                          ;
	                        Toast.makeText(getApplicationContext(), "Failed to start Remote Sensor Manager", Toast.LENGTH_LONG)
	                             .show();
	                    } catch (IOException e) {
	                        Log.e(TAG, e.getMessage(), e);
	                        Toast.makeText(getApplicationContext(), "Failed to start Remote Sensor Manager:" + e.getMessage(),
	                                       Toast.LENGTH_LONG).show();
	                    }
	                }
				else
				{
					try{
						remoteSensorManager.stop();
					 isMasterRunning=false;
					 //updateMasterStatus();
					}
					catch (IOException e) {
                        Log.e(TAG, e.getMessage(), e);
                        Toast.makeText(getApplicationContext(), "Failed to stop Remote Sensor Manager:" + e.getMessage(),
                                       Toast.LENGTH_LONG).show();
                    }
				}
		}

    @Override
    public void onSensorNodeFound(RemoteSensorNode remoteSensorNode) {
        Toast.makeText(getApplicationContext(), "node found", Toast.LENGTH_LONG).show();
            startSensors(remoteSensorNode);

        if(remoteSensorManager.isRunning())
            Toast.makeText(getApplicationContext(), "manger working", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSensorNodeLost(RemoteSensorNode remoteSensorNode) {
        Toast.makeText(getApplicationContext(), "node lost", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRemoteSensorChanged(RemoteSensorEvent remoteSensorEvent) {
        if(remoteSensorEvent.getNode().getName().equals(remoteSensorNode.getName())&& remoteSensorEvent.getSensorType()== Sensor.TYPE_GYROSCOPE)
            gyroscope = remoteSensorEvent.getValues();
        Toast.makeText(getApplicationContext(),gyroscope[0]+"hello",Toast.LENGTH_LONG).show();
        if(remoteSensorEvent.getNode().getName().equals(remoteSensorNode.getName())&& remoteSensorEvent.getSensorType()== Sensor.TYPE_ROTATION_VECTOR)
            rotation = remoteSensorEvent.getValues();

        Toast.makeText(getApplicationContext(),rotation[0]+"hello",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSensorManagerStopped() {

    			if(isMasterRunning==false)
				{ 
					Toast.makeText(getApplicationContext(), "Start Master to play the game", Toast.LENGTH_LONG).show();
				}
				else
				{ 
				if(isNodeRunning==false)
				{
						Log.i(TAG, "All the Sensor nodes are disconnected");
		                Toast.makeText(getApplicationContext(), "All the Sensor nodes are disconnected",
		                               Toast.LENGTH_LONG).show();
		        }
			}

    }
    protected void onPause() {
       /* if (remoteSensorManager.isRunning()) {
            try {
                remoteSensorManager.stop();
                isMasterRunning = false;
            } catch (IOException e) {
                Log.e(TAG, e.getMessage(), e);
            }*/
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
       /* if (remoteSensorManager.isRunning()) {
            isMasterRunning = true;
            updateMasterStatus();
            findNodes();
        } else {
            isMasterRunning = false;
            updateMasterStatus();
        }*/
    }
/*
    private void updateMasterStatus() {
        if (isMasterRunning) {
            startMasterButton.setText(getString(R.string.stop_master));
        } else {
            startMasterButton.setText(getString(R.string.start_master));
        }
    }*/

	/*private void findNodes()
	{
		List<RemoteSensorNode> nodes;
		int position = 0;
		try {
		nodes=remoteSensorManager.getSensorNodes();
		if (nodes.isEmpty()) {
			isNodeRunning=false;
            Log.i(TAG, "All the Sensor nodes are disconnected");
            Toast.makeText(getApplicationContext(), "All the Sensor nodes are disconnected",
                           Toast.LENGTH_LONG).show();
        }
		else 
		{
			isNodeRunning=true;
			startSensors(nodes.get(position));
		}
		}
		catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            Toast.makeText(getApplicationContext(), "Failed to fetch Remote Sensor nodes:" + e.getMessage(),
                           Toast.LENGTH_LONG).show();
        }

	}*/
		public void startSensors(RemoteSensorNode node)
		{
			try {
				remoteSensorManager.startSensor(node,Sensor.TYPE_ACCELEROMETER,SensorManager.SENSOR_DELAY_UI );
				remoteSensorManager.startSensor(node,Sensor.TYPE_ROTATION_VECTOR,SensorManager.SENSOR_DELAY_UI );
				remoteSensorManager.startSensor(node,Sensor.TYPE_GYROSCOPE,SensorManager.SENSOR_DELAY_UI );
			}
			catch (IOException e) {
                Log.e(TAG, "Failed to start Sensor:" + " on node:" + node.getName(), e);
                Toast.makeText(getApplicationContext(),
                               "Failed to start Sensor:" + " on node:" + node.getName(),
                               Toast.LENGTH_LONG).show();
            }
		}
	
}