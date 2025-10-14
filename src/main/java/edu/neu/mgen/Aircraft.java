package edu.neu.mgen;

public class Aircraft extends Vehicle {
    String engineModel;
    String bodyType;
    String wingType;

    public Aircraft(String make, String model, int year, double speedMax, String engineModel,
            String bodyType, String wingType) {
        super("Aircraft", make, model, year, speedMax);
        this.engineModel = engineModel;
        this.bodyType = bodyType;
        this.wingType = wingType;
    }

    public void takeOff() {
        System.out.println(make + " " + model + " is taking off!");
    }

    public void land() {
        System.out.println(make + " " + model + " is landing safely.");
        stop();
    }

    @Override
    public String toString() {
        return super.toString() + " - Engine: " + engineModel + ", Body: " + bodyType + ", Wing: " + wingType;
    }
}
