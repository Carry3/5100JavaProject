package edu.neu.mgen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        Vehicle[] vehicles = {
                new Car("Toyota", "Camry", 2023, 200, 4, "205/55R16", "gasoline", "Automatic", "Front-Wheel Drive"),
                new Car("Tesla", "Model 3", 2024, 250, 4, "205/55R16", "electric", "Automatic", "Rear-Wheel Drive"),
                new Motorbike("Honda", "CBR", 2024, 200, "gasoline", "Two-wheeled motorcycle"),
                new Aircraft("Boeing", "747", 2020, 1000, "gasoline", "Front-Wheel Drive", "Wing"),
                new Ship("Ferretti Yachts", "500", 2025, 100, "gasoline", "Hull", "Steel")
        };

        System.out.println("Show all vehicles:");
        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle.toString());
        }
       
        System.out.println("----------------------------------------------------------------");

        System.out.println("Show the Fastest Car and let it honk and open doors:");
        Car fastestCar = null;
        for (Vehicle vehicle : vehicles) {
            if (vehicle instanceof Car) {
                Car car = (Car) vehicle;
                if (fastestCar == null || car.speedMax > fastestCar.speedMax) {
                    fastestCar = car;
                }
            }
        }
        if (fastestCar != null) {
            System.out.println("The fastest car is " + fastestCar.make + " " + fastestCar.model +
                    " with engine model: " + fastestCar.engineModel);
            fastestCar.honk();
            fastestCar.openDoors();
        } else {
            System.out.println("No cars found");
        }

        System.out.println("----------------------------------------------------------------");
        
        System.out.println("Show the Average Maximum Speed:");
        double totalMaxSpeed = 0;
        for (Vehicle vehicle : vehicles) {
            totalMaxSpeed += vehicle.speedMax;
        }
        double averageMaxSpeed = totalMaxSpeed / vehicles.length;
        System.out.println("Average maximum speed: " + String.format("%.2f", averageMaxSpeed) + " km/h");

        System.out.println("----------------------------------------------------------------");

        System.out.println("Show the Vehicles by Year:");
        Map<Integer, List<Vehicle>> vehiclesByYear = new HashMap<>();
        for (Vehicle vehicle : vehicles) {
            List<Vehicle> vehiclesByYearList = vehiclesByYear.get(vehicle.year);
            if (vehiclesByYearList == null) {
                vehiclesByYearList = new ArrayList<Vehicle>();
                vehiclesByYear.put(vehicle.year, vehiclesByYearList);
            }
            vehiclesByYearList.add(vehicle);
        }
        for (Integer year : vehiclesByYear.keySet()) {
            String vehiclesString = "";
            boolean first = true;
            for (Vehicle vehicle : vehiclesByYear.get(year)) {
                if (first) {
                    vehiclesString += vehicle.make + " " + vehicle.model;
                    first = false;
                } else {
                    vehiclesString += ", " + vehicle.make + " " + vehicle.model;
                }
            }
            System.out.println(year + ": " + vehiclesString);
        }
    }
}
