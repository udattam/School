import java.util.*;

public class DigitalSchool {
    Node root;
    ArrayList<Teacher> teachers;
    HashMap<String, Node> subjects;
    HashMap<Node, String> reverseSubjects;
    HashMap<Node, Node> parent;

    public DigitalSchool() {
        root = new Node("Subjects");
        teachers = new ArrayList<Teacher>();
    }

    public void hashSubjects() {
        subjects = new HashMap<String, Node>();
        reverseSubjects = new HashMap<Node, String>();
        parent = new HashMap<Node, Node>();
        hash(root, subjects);
    }

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

    private void hash(Node node, HashMap<String, Node> subjects) {
        subjects.clear();
        Queue<Tuple> queue = new LinkedList<Tuple>();
        queue.add(new Tuple(node, 0, 0));
        int x = 0, y = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Tuple tuple = queue.poll();
                subjects.put(tuple.x + "$" + tuple.y, tuple.node);
                reverseSubjects.put(tuple.node, tuple.x + "$" + tuple.y);
                for (Node child : tuple.node.children) {
                    queue.add(new Tuple(child, x + 1, ++y));
                    parent.put(child, tuple.node);
                }
            }
            x++;
        }
    }

    class Node {
        String text;
        ArrayList<Node> children;

        public Node() {

        }

        public Node(String text) {
            this.text = text;
            this.children = new ArrayList<Node>();
        }

        public void print(int level) {
            // Print while maintaining the tree structure

            for (int i = 0; i < level; i++) {
                System.out.print(" ");
            }
            System.out.print("|-" + text);
            System.out.println();
            for (Node child : this.children) {
                child.print(level + 1);
            }
        }

        public void print(int level, boolean showIndex) {
            for (int i = 0; i < level; i++) {
                System.out.print(" ");
            }
            System.out.print("|-" + text + " : " + reverseSubjects.get(this));
            System.out.println();
            for (Node child : this.children) {
                child.print(level + 1, showIndex);
            }
        }

        public void printPath(String location) {
            Node node = subjects.get(location);
            if (node == null) {
                System.out.println("No such subject");
            } else {
                printRootToNode(node);
            }
        }

        private void printRootToNode(Node node) {
            if (node == null) {
                return;
            }
            Stack<String> stack = new Stack<String>();
            while (node != root) {
                stack.push(node.text);
                stack.push(" -> ");
                node = parent.get(node);
            }
            stack.push(root.text);
            while (!stack.isEmpty()) {
                System.out.print(stack.pop());
            }
            System.out.println();
        }

        public int countChildren(Node node) {
            int count = 0;
            for (Node child : node.children) {
                count += countChildren(child);
            }
            return count + 1;
        }
    }

    class Action {
        enum operation {
            ADD, DELETE, REORDER, MOVE
        };

        operation op;
        String text;
        int index;

        public Action(operation op, String text, int index) {
            this.op = op;
            this.text = text;
            this.index = index;
        }
    }

    class Student {
        String name;
        String email;
        String phone;

        public Student() {

        }

        public Student(String name, String email, String phone) {
            this.name = name;
            this.email = email;
            this.phone = phone;
        }
    }

    class Teacher {
        String name;
        String email;
        String phone;
        ArrayList<Action> actions;

        public Teacher() {

        }

        public Teacher(String name) {
            this.name = name;
            this.actions = new ArrayList<Action>();
        }

        public void createSubject(String text, Node root) {
            root.children.add(new Node(text));
            actions.add(new Action(Action.operation.ADD, text, root.children.size() - 1));
        }

        public void deleteSubject(String text, Node root) {
            for (int i = 0; i < root.children.size(); i++) {
                if (root.children.get(i).text.equals(text)) {
                    root.children.remove(i);
                    actions.add(new Action(Action.operation.DELETE, text, i));
                    break;
                }
            }
        }

        public void reorderSubject(String text, Node root, int index) {
            for (int i = 0; i < root.children.size(); i++) {
                if (root.children.get(i).text.equals(text)) {
                    Node temp = root.children.get(i);
                    root.children.remove(i);
                    root.children.add(index, temp);
                    actions.add(new Action(Action.operation.REORDER, text, index));
                    break;
                }
            }
        }

        public void moveSubject(String text, Node root, Node newRoot) {
            for (int i = 0; i < root.children.size(); i++) {
                if (root.children.get(i).text.equals(text)) {
                    Node temp = root.children.get(i);
                    root.children.remove(i);
                    newRoot.children.add(temp);
                    actions.add(new Action(Action.operation.MOVE, text, newRoot.children.size() - 1));
                    break;
                }
            }
        }

        public void addLesson(String lesson, String subject) {
            Node location = search(subject, root);
            if (location != null) {
                location.children.add(new Node(lesson));
                actions.add(new Action(Action.operation.ADD, lesson, location.children.size() - 1));
            } else {
                System.out.println("Subject not found");
            }
        }

        public void addWorkBook(String workbook, String subject, String lesson) {
            Node location = search(subject, root);
            if (location != null) {
                Node lessonLocation = search(lesson, location);
                if (lessonLocation != null) {
                    lessonLocation.children.add(new Node(workbook));
                    actions.add(new Action(Action.operation.ADD, workbook, lessonLocation.children.size() - 1));
                } else {
                    System.out.println("Lesson not found");
                }
            } else {
                System.out.println("Subject not found");
            }
        }

        public Node search(String text, Node root) {
            if (root.text.equals(text)) {
                return root;
            }
            for (Node child : root.children) {
                Node temp = search(text, child);
                if (temp != null) {
                    return temp;
                }
            }
            return null;
        }

        public void undo() {
            if (actions.size() == 0) {
                System.out.println("No actions to undo");
                return;
            }
            Action action = actions.get(actions.size() - 1);
            actions.remove(actions.size() - 1);
            switch (action.op) {
                case ADD:
                    deleteSubject(action.text, root);
                    break;
                case DELETE:
                    root.children.add(action.index, new Node(action.text));
                    break;
                case REORDER:
                    reorderSubject(action.text, root, action.index);
                    break;
                case MOVE:
                    moveSubject(action.text, root, root);
                    break;
            }
        }
    }
}
