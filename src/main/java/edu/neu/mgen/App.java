package edu.neu.mgen;

public class App {
    public static void main(String[] args) {
        // Create animals
        Bird crow = new Bird("Crow", false, true);
        Bird owl = new Bird("Owl", true, true);
        LandAnimal lion = new LandAnimal("Lion", true, 4);
        LandAnimal rabbit = new LandAnimal("Human", true, 2);
        Fish shark = new Fish("Shark", true, 7);
        Fish goldfish = new Fish("Goldfish", false, 6);

        // Create animal array and print information
        Animal[] animals = { crow, owl, lion, rabbit, shark, goldfish };
        int countOfPredators = 0;
        for (Animal animal : animals) {
            System.out.println(animal.getInfo());

            if (animal.isPredator()) {
                countOfPredators++;
            }

            if (animal instanceof Bird) {
                Bird bird = (Bird) animal;
                System.out.println("  " + bird.fly());
            } else if (animal instanceof LandAnimal) {
                LandAnimal landAnimal = (LandAnimal) animal;
                System.out.println("  " + landAnimal.run());
            } else if (animal instanceof Fish) {
                Fish fish = (Fish) animal;
                System.out.println("  " + fish.swim());
            }
            System.out.println();
        }
        System.out.println("Count of predators: " + countOfPredators);
    }
}
