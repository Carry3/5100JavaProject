package edu.neu.mgen;

public class Fish extends Animal {
    private int numberOfFins;
    
    public Fish(String name, boolean isPredator, int numberOfFins) {
        super(name, isPredator);
        this.numberOfFins = numberOfFins;
    }
    
    @Override
    public String getAnimalType() {
        return "Fish";
    }
    
    @Override
    public String makeSound() {
        if (name.contains("Shark")) {
            return "Silent";
        } else {
            return "Bubble";
        }
    }
    
    public String swim() {
        return name + " swims with " + numberOfFins + " fins.";
    }
}
