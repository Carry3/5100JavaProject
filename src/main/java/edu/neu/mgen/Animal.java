package edu.neu.mgen;

public abstract class Animal {
    protected String name;
    protected boolean isPredator;
    
    public Animal(String name, boolean isPredator) {
        this.name = name;
        this.isPredator = isPredator;
    }
    
    public String getName() { return name; }
    public boolean isPredator() { return isPredator; }
    
    public abstract String getAnimalType();
    public abstract String makeSound();
    
    public String getInfo() {
        return name + " (" + getAnimalType() + ") - " + 
               (isPredator ? "Carnivore" : "Herbivore") + " - " + makeSound();
    }
}
