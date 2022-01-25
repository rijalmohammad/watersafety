package it.univaq.disim.se4iot.watersafety.mqtt;



public class Main {
	public static void main(String[] args) throws Exception {
		SensorSimulator conditionSensorManggarai = new SensorSimulator("manggarai-floodgate", "condition");
		SensorSimulator conditionSensorCiliwung = new SensorSimulator("ciliwung-river", "condition");
		conditionSensorManggarai.start();
		conditionSensorCiliwung.start();
	}
}