package edu.neu.mgen;

public class Ship extends Vehicle {
    String engineModel;
    String bodyType;
    String hullMaterial;

    public Ship(String make, String model, int year, double speedMax, String engineModel,
            String bodyType, String hullMaterial) {
        super("Ship", make, model, year, speedMax);
        this.engineModel = engineModel;
        this.bodyType = bodyType;
        this.hullMaterial = hullMaterial;
    }

    public void anchor() {
        System.out.println(make + " " + model + " is dropping anchor.");
        stop();
    }

    public void sail() {
        System.out.println(make + " " + model + " is sailing on the water!");
    }

    @Override
    public String toString() {
        return super.toString() + " - Engine: " + engineModel + ", Body: " + bodyType + ", Hull Material: " + hullMaterial;
    }
}
