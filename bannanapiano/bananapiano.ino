#include <CapacitiveSensor.h>

int led = 13; //led pin
int speaker = 12; //speaker pin
int notes[8] = {262, 294, 330, 350, 392, 440, 494, 523}; //hertz values for notes
int sensor3Value, sensor4Value, sensor5Value, sensor6Value, sensor7Value, sensor8Value;

CapacitiveSensor sensor3 = CapacitiveSensor(2, 3); //2 is the shared arduino pin, 3-8 are unique to the sensor
CapacitiveSensor sensor4 = CapacitiveSensor(2, 4);
CapacitiveSensor sensor5 = CapacitiveSensor(2, 5);
CapacitiveSensor sensor6 = CapacitiveSensor(2, 6);
CapacitiveSensor sensor7 = CapacitiveSensor(2, 7);
CapacitiveSensor sensor8 = CapacitiveSensor(2, 8);

void printSensorValue(int sensor, int sensorValue)
{
  Serial.print("Sensor ");
  Serial.print(sensor);
  Serial.print(":\t");
  Serial.println(sensorValue);
}

void playTone(int note)
{
  tone(speaker, notes[note], 100);
  digitalWrite(led, HIGH);
  delay(100);
  digitalWrite(led, LOW);
}

void setup()                    
{
  pinMode(led, OUTPUT);
  pinMode(speaker, OUTPUT);
  Serial.begin(9600);
}

void loop()                    
{
  if ((sensor3Value=sensor3.capacitiveSensor(30)) > 100)
  {
    playTone(2);
    printSensorValue(3, sensor3Value);
  }
  else noTone(speaker);

  if ((sensor4Value=sensor4.capacitiveSensor(30)) > 100)
  {
    playTone(3);
    printSensorValue(4, sensor4Value);
  }
  else noTone(speaker);

  if ((sensor5Value=sensor5.capacitiveSensor(30)) > 100)
  {
    playTone(4);
    printSensorValue(5, sensor5Value);
  }
  else noTone(speaker);

  if ((sensor6Value=sensor6.capacitiveSensor(30)) > 100)
  {
    playTone(5);
    printSensorValue(6, sensor6Value);
  }
  else noTone(speaker);

  if ((sensor7Value=sensor7.capacitiveSensor(30)) > 100)
  {
    playTone(6);
    printSensorValue(7, sensor7Value);
  }
  else noTone(speaker);

  if ((sensor8Value=sensor8.capacitiveSensor(30)) > 100)
  {
    playTone(7);
    printSensorValue(8, sensor8Value);
  }
  else noTone(speaker);
}
