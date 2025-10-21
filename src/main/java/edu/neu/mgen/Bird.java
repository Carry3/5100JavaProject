package edu.neu.mgen;

public class Bird extends Animal {
    private boolean canFly;
    
    public Bird(String name, boolean isPredator, boolean canFly) {
        super(name, isPredator);
        this.canFly = canFly;
    }
    
    @Override
    public String getAnimalType() {
        return "Bird";
    }
    
    @Override
    public String makeSound() {
        if (name.contains("Crow")) {
            return "Caw";
        } else if (name.contains("Owl")) {
            return "Hoot";
        } else {
            return "Chirp";
        }
    }
    
    public String fly() {
        return canFly ? name + " can fly!" : name + " cannot fly.";
    }
}
