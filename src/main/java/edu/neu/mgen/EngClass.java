package edu.neu.mgen;

import java.util.ArrayList;

public class EngClass {
  ArrayList<Student> students = new ArrayList<>();

  public void addStudent(Student student) {
    students.add(student);
  }

  public void removeStudent(int studentId) {
    Student student = null;
    for (Student s : students) {
      if (s.studentId == studentId) {
        student = s;
        break;
      }
    }
    if (student != null) {
      students.remove(student);
    }
  }

  public void printStudents() {
    System.out.println("Here are " + students.size() + " students in the class:");
    for (Student student : students) {
      System.out.println(student.studentId + " " + student.nameFirst + " " + student.nameLast);
    }
  }
}
