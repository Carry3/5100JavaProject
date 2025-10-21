package edu.neu.mgen;

public class LandAnimal extends Animal {
    private int numberOfLegs;
    
    public LandAnimal(String name, boolean isPredator, int numberOfLegs) {
        super(name, isPredator);
        this.numberOfLegs = numberOfLegs;
    }
    
    @Override
    public String getAnimalType() {
        return "Land Animal";
    }
    
    @Override
    public String makeSound() {
        if (name.contains("Lion")) {
            return "Roar";
        } else if (name.contains("Rabbit")) {
            return "Squeak";
        } else {
            return "Animal sound";
        }
    }
    
    public String run() {
        return name + " runs on " + numberOfLegs + " legs.";
    }
}
