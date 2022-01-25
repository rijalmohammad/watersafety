package it.univaq.disim.se4iot.watersafety.mqtt;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.io.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class SensorSimulator implements MqttCallback, Runnable {
	
	MqttClient client;
	
	String location;
	String parameter;
	String sensorName;
	Thread t;
	
	@Override
	public void connectionLost(Throwable arg0) {
		// TODO Auto-generated method stub
		System.out.println("The connection from sensor with the server is lost. !!!!");
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub
		System.out.println("The delivery from sensor has been complete. The delivery token is " + arg0.toString());
	}
	
	@Override
	public void messageArrived(String arg0, MqttMessage message) throws Exception {
		System.out.println("A new message for sensor from: \"" + arg0 + "\". The content is " + message.toString());
	}
	
	public SensorSimulator(String location, String parameter) {
		this.location = location;
		this.parameter = parameter;
		this.sensorName = parameter + location;
	}
	
	public void run() {
		try {
			publish();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void start() {
	    if (t == null) {
	    	t = new Thread (this, sensorName);
	        t.start();
	    }
	}
	
	public void publish() throws Exception {
		try {
			Helper helper = new Helper();
			
			String locationName = helper.getLocation(location);
			String dataPath = helper.getParameterData(parameter);
		
		    List<Map<String, String>> valueList = helper.CsvToJson(dataPath);
		    		    
		    String topic = "/" + location + "/water/" + parameter;
		    
			client = new MqttClient("tcp://0.0.0.0:1883", sensorName);
			client.connect();
			client.setCallback(this);
			MqttMessage message = new MqttMessage();
			ObjectMapper mapper = new ObjectMapper();

			
			for(int i=0; i<valueList.size(); i++) {
				valueList.get(i).put("location", locationName);
				String payloadSent = mapper.writeValueAsString(valueList.get(i));
				System.out.println(payloadSent);

				message.setPayload(payloadSent.getBytes());
				message.setRetained(true);
				client.publish(topic, message);
		        Thread.sleep(1000);
			}
			client.disconnect();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}