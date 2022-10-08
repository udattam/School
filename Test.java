import java.io.*;

public class Test extends DigitalSchool {
    public static void main(String[] args) throws FileNotFoundException {
        // Redirecting output to a file
        PrintStream writeToFile = new PrintStream(new File("results.txt"));
        System.setOut(writeToFile);
        // Write test cases for testing the code
        DigitalSchool ds = new DigitalSchool();
        // Create teachers
        Teacher t1 = ds.new Teacher("Teacher 1");
        Teacher t2 = ds.new Teacher("Teacher 2");
        Teacher t3 = ds.new Teacher("Teacher 3");
        // Add teachers to the list
        ds.teachers.add(t1);
        ds.teachers.add(t2);
        ds.teachers.add(t3);
        // Print teachers
        System.out.println("Teachers:");
        for (Teacher t : ds.teachers) {
            System.out.println(t.name);
        }
        System.out.println("---------------------------------------");
        // Create students
        Student s1 = ds.new Student("Student 1", "s1@gmail.com", "1234567890");
        Student s2 = ds.new Student("Student 2", "s2@gmail.com", "2345678901");
        Student s3 = ds.new Student("Student 3", "s3@gmail.com", "3456789012");
        Student s4 = ds.new Student("Student 4", "s4@gmail.com", "4567890123");
        Student s5 = ds.new Student("Student 5", "s5@gmail.com", "5678901234");
        Student s6 = ds.new Student("Student 6", "s6@gmail.com", "6789012345");
        // Add students to the list
        ds.students.add(s1);
        ds.students.add(s2);
        ds.students.add(s3);
        ds.students.add(s4);
        ds.students.add(s5);
        ds.students.add(s6);
        // Print students
        System.out.println("Students:");
        for (Student s : ds.students) {
            System.out.println(s.name + " " + s.email + " " + s.phone);
        }
        System.out.println("---------------------------------------");
        // Create subjects
        t1.createSubject("English", ds.root);
        t1.createSubject("Physics", ds.root);
        t1.createSubject("Chemistry", ds.root);
        t1.createSubject("Maths", ds.root);
        t1.createSubject("Computer Science", ds.root);
        // Print initial tree structure
        System.out.println("Initial tree structure");
        ds.root.print(0);
        System.out.println("---------------------------------------");
        // Add lessons
        t1.addLesson("Lesson 1", "English");
        t1.addLesson("Lesson 2", "English");
        t1.addLesson("Lesson 3", "English");
        t1.addLesson("Lesson 1", "Physics");
        t1.addLesson("Lesson 2", "Physics");
        t1.addLesson("Lesson 3", "Physics");
        t1.addLesson("Lesson 1", "Chemistry");
        t1.addLesson("Lesson 2", "Chemistry");
        t1.addLesson("Lesson 3", "Chemistry");
        t1.addLesson("Lesson 1", "Maths");
        t1.addLesson("Lesson 2", "Maths");
        t1.addLesson("Lesson 3", "Maths");
        t1.addLesson("Lesson 1", "Computer Science");
        t1.addLesson("Lesson 2", "Computer Science");
        t1.addLesson("Lesson 3", "Computer Science");
        // Print tree structure after adding lessons
        System.out.println("Tree structure after adding lessons");
        ds.root.print(0);
        System.out.println("---------------------------------------");
        // Add workbooks
        t1.addWorkBook("Workbook 1", "English", "Lesson 1");
        t1.addWorkBook("Workbook 1", "English", "Lesson 2");
        t1.addWorkBook("Workbook 2", "English", "Lesson 2");
        t1.addWorkBook("Workbook 1", "Physics", "Lesson 2");
        t1.addWorkBook("Workbook 1", "Physics", "Lesson 1");
        t1.addWorkBook("Workbook 2", "Physics", "Lesson 1");
        t1.addWorkBook("Workbook 1", "Chemistry", "Lesson 3");
        t1.addWorkBook("Workbook 2", "Chemistry", "Lesson 3");
        t1.addWorkBook("Workbook 3", "Chemistry", "Lesson 3");
        t1.addWorkBook("Workbook 1", "Maths", "Lesson 1");
        t1.addWorkBook("Workbook 2", "Maths", "Lesson 1");
        t1.addWorkBook("Workbook 3", "Maths", "Lesson 1");
        t1.addWorkBook("Workbook 1", "Computer Science", "Lesson 2");
        t1.addWorkBook("Workbook 2", "Computer Science", "Lesson 2");
        t1.addWorkBook("Workbook 3", "Computer Science", "Lesson 2");
        // Print tree structure after adding workbooks
        System.out.println("Tree structure after adding workbooks");
        ds.root.print(0);
        System.out.println("---------------------------------------");
        // Reorder subjects
        t1.reorderSubject("English", ds.root, 2);
        t1.reorderSubject("Physics", ds.root, 1);
        t1.reorderSubject("Chemistry", ds.root, 0);
        t1.reorderSubject("Maths", ds.root, 3);
        t1.reorderSubject("Computer Science", ds.root, 4);
        // Print tree structure after reordering subjects
        System.out.println("Tree structure after reordering subjects");
        ds.root.print(0);
        System.out.println("---------------------------------------");
        // Move subjects
        t2.createSubject("Semester 1", ds.root);
        t2.createSubject("Semester 2", ds.root);
        t1.reorderSubject("Semester 1", ds.root, 0);
        t1.reorderSubject("Semester 2", ds.root, 1);
        t1.moveSubject("English", ds.root, ds.root.children.get(0));
        t1.moveSubject("Physics", ds.root, ds.root.children.get(0));
        t1.moveSubject("Chemistry", ds.root, ds.root.children.get(0));
        t1.moveSubject("Maths", ds.root, ds.root.children.get(1));
        t1.moveSubject("Computer Science", ds.root, ds.root.children.get(1));
        // Print tree structure after moving subjects
        System.out.println("Tree structure after moving subjects");
        ds.root.print(0);
        System.out.println("---------------------------------------");
        // Delete subjects
        System.out.println("Adding subjects to delete");
        t3.createSubject("Dummy 1", ds.root);
        t3.createSubject("Dummy 2", ds.root);
        ds.root.print(0);
        System.out.println("---------------------------------------");
        t3.deleteSubject("Dummy 1", ds.root);
        t3.deleteSubject("Dummy 2", ds.root);
        // Print tree structure after deleting subjects
        System.out.println("Tree structure after deleting subjects");
        ds.root.print(0);
        System.out.println("---------------------------------------");
        // Actions performed by last teacher
        System.out.println("Actions");
        for (Action action : t3.actions) {
            System.out.println(action.op + " " + action.text);
        }
        System.out.println("---------------------------------------");
        // Undo actions
        System.out.println("Undo actions");
        t3.undo();
        t3.undo();
        // Print tree structure after undoing actions
        System.out.println("Tree structure after undoing actions");
        ds.root.print(0);
        System.out.println("---------------------------------------");
        t3.deleteSubject("Dummy 1", ds.root);
        t3.deleteSubject("Dummy 2", ds.root);
        // Hashing every node
        System.out.println("Hashing every node");
        ds.hashSubjects();
        // Print tree structure after hashing
        System.out.println("Tree structure after hashing");
        ds.root.print(0, true);
        System.out.println("---------------------------------------");
        // Print root to node path
        System.out.println("Root to node(4$37) path");
        ds.root.printPath("4$37");
        System.out.println("---------------------------------------");
        // Count number of nodes
        System.out.println("Number of nodes: " + ds.root.countChildren(ds.root));
        System.out.println("---------------------------------------");
        // End of tests
        System.out.println("End of tests. Add more tests here in the Test.java file.");
    }

}
