package edu.neu.mgen;

public abstract class Vehicle {
  String type;
  String make;
  String model;
  int year;
  double speedMax;
  double speedCurrent;

  public Vehicle(String type, String make, String model, int year, double speedMax) {
    this.type = type;
    this.make = make;
    this.model = model;
    this.year = year;
    this.speedMax = speedMax;
    this.speedCurrent = 0;
  }

  public void drive() {
    System.out.println("Driving " + make + " " + model);
  }

  public void accelerate(double speed) {
    if (speedCurrent + speed <= speedMax) {
      speedCurrent += speed;
      System.out.println(make + " " + model + " accelerated to " + speedCurrent + " km/h");
    } else {
      speedCurrent = speedMax;
      System.out.println(make + " " + model + " reached maximum speed of " + speedMax + " km/h");
    }
  }

  public void brake(double speed) {
    if (speedCurrent - speed >= 0) {
      speedCurrent -= speed;
      System.out.println(make + " " + model + " slowed down to " + speedCurrent + " km/h");
    } else {
      speedCurrent = 0;
      System.out.println(make + " " + model + " has stopped");
    }
  }

  public void stop() {
    speedCurrent = 0;
    System.out.println(make + " " + model + " has stopped");
  }

  @Override
  public String toString() {
    return type + " - " + make + " " + model + " (" + year + ") - Max Speed: " + speedMax + " km/h";
  }

  // Getter methods
  public String getType() { return type; }
  public String getMake() { return make; }
  public String getModel() { return model; }
  public int getYear() { return year; }
  public double getSpeedMax() { return speedMax; }
  public double getSpeedCurrent() { return speedCurrent; }

  // Setter methods
  public void setSpeedCurrent(double speedCurrent) {
    if (speedCurrent >= 0 && speedCurrent <= speedMax) {
      this.speedCurrent = speedCurrent;
    }
  }
}
