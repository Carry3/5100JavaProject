package edu.neu.mgen;

public class App {
    public static void main(String[] args) {
        EngClass engClass = new EngClass();
        engClass.students.add(new Student(1, "Weiming", "Chen"));
        engClass.students.add(new Student(2, "Jack", "Oaa"));
        engClass.printStudents();
        engClass.removeStudent(1);
        engClass.printStudents();
    }
}
