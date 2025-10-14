package edu.neu.mgen;

public class Car extends Vehicle {
    int numDoors;
    String tireModel;
    String engineModel;
    String transmissionModel;
    String driveMode;

    public Car(String make, String model, int year, double speedMax, int numDoors, String tireModel, String engineModel,
            String transmissionModel, String driveMode) {
        super("Car", make, model, year, speedMax);
        this.numDoors = numDoors;
        this.tireModel = tireModel;
        this.engineModel = engineModel;
        this.transmissionModel = transmissionModel;
        this.driveMode = driveMode;
    }

    public void honk() {
        System.out.println(make + " " + model + " is honking: BEEP BEEP!");
    }

    public void openDoors() {
        System.out.println("Opening " + numDoors + " doors of " + make + " " + model);
    }

    @Override
    public String toString() {
        return super.toString() + " - Engine: " + engineModel + ", Doors: " + numDoors + ", Tire: " + tireModel + ", Transmission: " + transmissionModel + ", Drive Mode: " + driveMode;
    }
}
