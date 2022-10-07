import java.util.*;

// Class: DigitalSchool
public class DigitalSchool {
    // Root node of the tree
    Node root;
    // List of teachers and students
    ArrayList<Teacher> teachers;
    ArrayList<Student> students;
    // Map to get subjects from hashed string and vice versa
    HashMap<String, Node> subjects;
    HashMap<Node, String> reverseSubjects;
    // Map to get parent of a node
    HashMap<Node, Node> parent;

    // Constructor
    public DigitalSchool() {
        root = new Node("Subjects");
        teachers = new ArrayList<Teacher>();
        students = new ArrayList<Student>();
    }

    // Node class
    class Node {
        // Data: name of the subject and list of children
        String text;
        ArrayList<Node> children;

        // Constructor
        public Node() {

        }

        // Constructor to initialize data members
        public Node(String text) {
            this.text = text;
            this.children = new ArrayList<Node>();
        }

        // Printing the tree
        public void print(int level) {
            // Print while maintaining the tree structure
            for (int i = 0; i < level; i++) {
                System.out.print(" ");
            }
            System.out.print("|-" + text);
            System.out.println();
            for (Node child : this.children) {
                // Recursively call print on each child
                child.print(level + 1);
            }
        }

        // Printing the tree with the hashed index of each node(Overloaded)
        public void print(int level, boolean showIndex) {
            // Print while maintaining the tree structure
            for (int i = 0; i < level; i++) {
                System.out.print(" ");
            }
            System.out.print("|-" + text + " : " + reverseSubjects.get(this));
            System.out.println();
            for (Node child : this.children) {
                // Recursively call print on each child
                child.print(level + 1, showIndex);
            }
        }

        // Print path from root to the node
        public void printPath(String location) {
            // Check whether a node with the given hashed index exists
            Node node = subjects.get(location);
            if (node == null) {
                System.out.println("No such subject");
            } else {
                printRootToNode(node);
            }
        }

        // Logic to print path from root to the node
        private void printRootToNode(Node node) {
            if (node == null) {
                return;
            }
            // Use stack to mimic recursion while going iteratively
            Stack<String> stack = new Stack<String>();
            while (node != root) {
                stack.push(node.text);
                stack.push(" -> ");
                node = parent.get(node);
            }
            stack.push(root.text);
            // Print the path
            while (!stack.isEmpty()) {
                System.out.print(stack.pop());
            }
            System.out.println();
        }

        // Count the number of nodes in the subtree
        public int countChildren(Node node) {
            int count = 0;
            for (Node child : node.children) {
                // Recursively call countChildren on each child
                count += countChildren(child);
            }
            return count + 1;
        }
    }

    // Hashing of Subjects
    public void hashSubjects() {
        subjects = new HashMap<String, Node>();
        reverseSubjects = new HashMap<Node, String>();
        parent = new HashMap<Node, Node>();
        hash(root, subjects);
    }

    // Custom data structure to store the coordinates of a node
    class Tuple {
        Node node;
        int x;
        int y;

        public Tuple() {

        }

        public Tuple(Node node, int x, int y) {
            this.node = node;
            this.x = x;
            this.y = y;
        }
    }

    // Logic to hash the subjects
    private void hash(Node node, HashMap<String, Node> subjects) {
        // Clear the map with old data
        subjects.clear();
        reverseSubjects.clear();
        parent.clear();
        // Queue to store the nodes in BFS order
        Queue<Tuple> queue = new LinkedList<Tuple>();
        queue.add(new Tuple(node, 0, 0));
        int x = 0, y = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Tuple tuple = queue.poll();
                // Hashed string= x+$+y (Ex: 22$12)
                String hash = tuple.x + "$" + tuple.y;
                // Add the hash,node pair to the map
                subjects.put(hash, tuple.node);
                // Add the node,hash pair to the map
                reverseSubjects.put(tuple.node, hash);
                for (Node child : tuple.node.children) {
                    queue.add(new Tuple(child, x + 1, ++y));
                    // Add the parent of the node to the map
                    parent.put(child, tuple.node);
                }
            }
            x++;
        }
    }

    // Action class
    class Action {
        // enum to store the type of action
        enum operation {
            ADD, DELETE, REORDER, MOVE
        };

        // Data: type of action, subject name, index
        operation op;
        String text;
        int index;
        Node root;
        Node newRoot;

        // Constructor
        public Action(operation op, String text, int index, Node root) {
            this.op = op;
            this.text = text;
            this.index = index;
            this.root = root;
            this.newRoot = null;
        }

        // Handle the move action
        public Action(operation op, String text, int index, Node root, Node newRoot) {
            this.op = op;
            this.text = text;
            this.index = index;
            this.root = root;
            this.newRoot = newRoot;
        }
    }

    // Teacher class
    class Teacher {
        // Data: name,email(not mandatory),phone(not mandatory), list of actions
        String name;
        String email;
        String phone;
        ArrayList<Action> actions;

        // Constructor
        public Teacher() {

        }

        // Constructor to initialize data members
        public Teacher(String name) {
            this.name = name;
            this.actions = new ArrayList<Action>();
        }

        // Create a new subject and add it to the tree with respect to the given root
        public void createSubject(String text, Node root) {
            // Create a new node
            root.children.add(new Node(text));
            // Add the action to the list of actions (operation=ADD,text=subject
            // name,index=0,root=tree root)
            actions.add(new Action(Action.operation.ADD, text, root.children.size() - 1, root));
        }

        // Delete a subject from the tree with respect to the given root
        public void deleteSubject(String text, Node root) {
            for (int i = 0; i < root.children.size(); i++) {
                // Check if the subject is present in the list of children
                if (root.children.get(i).text.equals(text)) {
                    // Remove the node from the list of children
                    root.children.remove(i);
                    // Add the action to the list of actions (operation=DELETE,text=subject
                    // name,index=0,root=tree root)
                    actions.add(new Action(Action.operation.DELETE, text, i, root));
                    return;
                }
            }
            // If the subject is not present in the list of children
            System.out.println("No such subject");
        }

        // Reorder the subjects in the tree with respect to the given root
        public void reorderSubject(String text, Node root, int index) {
            for (int i = 0; i < root.children.size(); i++) {
                // Check if the subject is present in the list of children
                if (root.children.get(i).text.equals(text)) {
                    // Remove the subject from the list of children
                    Node temp = root.children.get(i);
                    root.children.remove(i);
                    // Add the subject to the list of children at the desired index
                    root.children.add(index, temp);
                    // Add the action to the list of actions (operation=REORDER,text=subject
                    // name,index=old index,root=tree root)
                    actions.add(new Action(Action.operation.REORDER, text, i, root));
                    return;
                }
            }
            // If the subject is not present in the list of children
            System.out.println("No such subject");
        }

        // Move a subject from one node to another
        public void moveSubject(String text, Node root, Node newRoot) {
            for (int i = 0; i < root.children.size(); i++) {
                // Check if the subject is present in the list of children
                if (root.children.get(i).text.equals(text)) {
                    // Remove the subject from the list of children
                    Node temp = root.children.get(i);
                    root.children.remove(i);
                    // Add the subject to the list of children of the new root
                    newRoot.children.add(temp);
                    // Add the action to the list of actions (operation=MOVE,text=subject
                    // name,index=old index,root=tree root,newRoot=new tree root)
                    actions.add(new Action(Action.operation.MOVE, text, i, root, newRoot));
                    return;
                }
            }
            // If the subject is not present in the list of children
            System.out.println("No such subject");
        }

        // Add lesson to a subject
        public void addLesson(String lesson, String subject) {
            // Get the node corresponding to the subject
            Node location = search(subject, root);
            if (location != null) {
                location.children.add(new Node(lesson));
                actions.add(new Action(Action.operation.ADD, lesson, location.children.size() - 1, root));
            } else {
                System.out.println("Subject not found");
            }
        }

        // Search for a subject in the tree with respect to the given root using DFS
        public Node search(String text, Node root) {
            // Base case
            if (root == null) {
                return null;
            }
            // Subject found
            if (root.text.equals(text)) {
                return root;
            }
            // Search in the list of children
            for (Node child : root.children) {
                Node temp = search(text, child);
                if (temp != null) {
                    return temp;
                }
            }
            // Subject not found
            return null;
        }

        // Add workbooks to a lesson
        public void addWorkBook(String workbook, String subject, String lesson) {
            // Search node corresponding to the subject with the root of the tree as the
            // root
            Node location = search(subject, root);
            // If the subject is found
            if (location != null) {
                // Search lesson corresponding to the subject with the subject as the root
                Node lessonLocation = search(lesson, location);
                // If the lesson is found
                if (lessonLocation != null) {
                    // Add the workbook to the list of children of the lesson
                    lessonLocation.children.add(new Node(workbook));
                    // Add the action to the list of actions
                    actions.add(new Action(Action.operation.ADD, workbook, lessonLocation.children.size() - 1, root));
                }
                // If the lesson is not found
                else {
                    System.out.println("Lesson not found");
                }

            }
            // If the subject is not found
            else {
                System.out.println("Subject not found");
            }
        }

        // Undo the last action
        public void undo() {
            // No actions to undo
            if (actions.size() == 0) {
                System.out.println("No actions to undo");
                return;
            }
            // Get the last action
            Action action = actions.get(actions.size() - 1);
            // Remove the last action from the list of actions
            actions.remove(actions.size() - 1);
            // Reverse the actions
            switch (action.op) {
                case ADD:
                    deleteSubject(action.text, action.root);
                    break;
                case DELETE:
                    action.root.children.add(new Node(action.text));
                    break;
                case REORDER:
                    reorderSubject(action.text, action.root, action.index);
                    break;
                case MOVE:
                    moveSubject(action.text, action.newRoot, action.root);
                    break;
            }
        }
    }

    // Student class
    class Student {
        // Data members
        String name;
        String email;
        String phone;

        // Constructor
        public Student() {

        }

        // Constructor to initialize data members
        public Student(String name, String email, String phone) {
            this.name = name;
            this.email = email;
            this.phone = phone;
        }
    }
}
