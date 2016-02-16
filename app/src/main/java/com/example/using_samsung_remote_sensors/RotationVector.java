//package com.example.using_samsung_remote_sensors;
//
//import android.hardware.Sensor;
//
//import com.example.using_samsung_remote_sensors.MainActivity.SensorCallBack;
//import com.samsung.android.remotesensor.RemoteSensorEvent;
//import com.samsung.android.remotesensor.RemoteSensorNode;
//
//public class RotationVector extends MainActivity implements SensorCallBack {
//
//	private RemoteSensorNode nodeToMonitor;
//	float[] values;
//
//	public RotationVector(RemoteSensorNode node){
//		nodeToMonitor=node;
//	}
//
//	@Override
//	public void onRemoteSensorDataAvailable(RemoteSensorEvent event) {
//		if(event.getNode().getName().equals(nodeToMonitor.getName()) && event.getSensorType()==Sensor.TYPE_GYROSCOPE)
//		{
//			values = event.getValues();
//		}
//
//	}
//	public float[] getSensorValues()
//	{
//		return values;
//	}
//
//}
