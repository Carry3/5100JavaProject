package edu.neu.mgen;

public class Motorbike extends Vehicle {
    String engineModel;
    String bodyType;

    public Motorbike(String make, String model, int year, double speedMax, String engineModel,
            String bodyType) {
        super("Motorbike", make, model, year, speedMax);
        this.engineModel = engineModel;
        this.bodyType = bodyType;
    }

    @Override
    public String toString() {
        return super.toString() + " - Engine: " + engineModel + ", Body: " + bodyType;
    }
}
