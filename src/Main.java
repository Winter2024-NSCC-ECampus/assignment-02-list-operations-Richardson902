class Node {
    int data;
    Node next;

    public Node(int data) {
        this.data = data;
        this.next = null;
    }
}

class LinkedList {
    Node head;

    // Insertion operations

    // Insert at the beginning
    public void insertAtBeginning(int data) {
        Node newNode = new Node(data);
        newNode.next = head;
        head = newNode;
    }

    // Insert at the end
    public void insertAtEnd(int data) {
        Node newNode = new Node(data);

        // If list is empty, make new node the head
        if (head == null) {
            head = newNode;
            return;
        }

        // Traverse to the end of list
        Node current = head;
        while (current.next != null) {
            current = current.next;
        }

        current.next = newNode;
    }

    // Insert in sorted list
    public void insertSorted(int data) {
        Node newNode = new Node(data);

        // If list is empty or new node should be placed before head
        if (head == null || head.data >= data) {
            newNode.next = head;
            head = newNode;
            return;
        }

        // Find the position to insert
        Node current = head;
        while (current.next != null && current.next.data < data) {
            current = current.next;
        }

        newNode.next = current.next;
        current.next = newNode;
    }

    // Deletion operations

    // Delete first node
    public void deleteFirst() {
        if (head == null) {
            System.out.println("List is empty");
            return;
        }

        head = head.next;
    }

    // Delete last node
    public void deleteLast() {
        if (head == null) {
            System.out.println("List is empty");
            return;
        }

        // If only one node exists
        if (head.next == null) {
            head = null;
            return;
        }

        // Find the second last node
        Node current = head;
        while (current.next.next != null) {
            current = current.next;
        }

        current.next = null;
    }

    // Delete node at given index
    public void deleteAtIndex(int index) {
        if (head == null) {
            System.out.println("List is empty");
            return;
        }

        // If index is 0, delete head
        if (index == 0) {
            head = head.next;
            return;
        }

        Node current = head;
        int count = 0;

        // Find the node before the one to delete
        while (current != null && count < index - 1) {
            current = current.next;
            count++;
        }

        // If index is out of bounds
        if (current == null || current.next == null) {
            System.out.println("Index out of bounds");
            return;
        }

        // Delete the node
        current.next = current.next.next;
    }

    // Split into front and back halves
    public LinkedList[] frontBackSplit() {
        LinkedList[] result = new LinkedList[2];
        result[0] = new LinkedList(); // Front list
        result[1] = new LinkedList(); // Back list

        if (head == null) {
            return result; // Return two empty lists
        }

        if (head.next == null) {
            result[0].head = new Node(head.data);
            return result; // Return front list with 1 element and empty back list
        }

        // Find the middle using slow and fast pointers
        Node slow = head;
        Node fast = head;

        // Previous pointer to track the node before slow
        Node prev = null;

        // Fast moves 2 at a time, slow moved 1 at a time. By time fast reaches end, slow will be in middle
        // prev will be just before the middle in case of odd elements
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            prev = slow;
            slow = slow.next;
        }

        // Create the front list (head to prev)
        result[0].head = head;

        // Create the back list (slow to end)
        result[1].head = slow;

        // Terminate the front list
        prev.next = null;

        return result;
    }

    // Sort and merge
    public static LinkedList mergeAndSort(LinkedList list1, LinkedList list2) {
        // Sort the lists
        list1.sort();
        list2.sort();

        // Merge the sorted lists
        LinkedList mergedList = new LinkedList();
        Node current1 = list1.head;
        Node current2 = list2.head;

        while (current1 != null && current2 != null) {
            if (current1.data <= current2.data) {
                mergedList.insertAtEnd(current1.data);
                current1 = current1.next;
            } else {
                mergedList.insertAtEnd(current2.data);
                current2 = current2.next;
            }
        }

        // Add remaining elements from list1
        while (current1 != null) {
            mergedList.insertAtEnd(current1.data);
            current1 = current1.next;
        }

        // Add remaining elements from list2
        while (current2 != null) {
            mergedList.insertAtEnd(current2.data);
            current2 = current2.next;
        }

        return mergedList;
    }

    // Method to sort the list (using Merge Sort)
    public void sort() {
        head = mergeSort(head);
    }

    private Node mergeSort(Node head) {
        // Base case: if head is null or has only one node
        if (head == null || head.next == null) {
            return head;
        }

        // Get the middle of the list
        Node middle = getMiddle(head);
        Node nextOfMiddle = middle.next;

        // Set the next of middle node to null
        middle.next = null;

        // Apply mergeSort on left list
        Node left = mergeSort(head);

        // Apply mergeSort on right list
        Node right = mergeSort(nextOfMiddle);

        // Merge the left and right lists
        Node sortedList = sortedMerge(left, right);
        return sortedList;
    }

    private Node sortedMerge(Node a, Node b) {
        Node result = null;

        // Base cases
        if (a == null)
            return b;
        if (b == null)
            return a;

        // Pick either a or b, and recur
        if (a.data <= b.data) {
            result = a;
            result.next = sortedMerge(a.next, b);
        } else {
            result = b;
            result.next = sortedMerge(a, b.next);
        }

        return result;
    }

    private Node getMiddle(Node head) {
        if (head == null)
            return head;

        Node slow = head, fast = head;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    // Print the linked list
    public void display() {
        Node current = head;
        System.out.print("List: ");
        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }
}

public class Main {
    public static void main(String[] args) {
        // Testing the linked list operations
        // Null shows the end of the list

        // Create a new list
        LinkedList list = new LinkedList();

        // Test insertions
        System.out.println("Testing insertions:");
        list.insertAtEnd(5);
        list.insertAtBeginning(1);
        list.insertSorted(3);
        list.insertSorted(2);
        list.insertSorted(9);
        list.display();  // Expected: 1 -> 2 -> 3 -> 5 -> 9 -> null

        // Test deletions
        System.out.println("\nTesting deletions:");
        list.deleteFirst();
        list.display();  // Expected: 2 -> 3 -> 5 -> 9 -> null

        list.deleteLast();
        list.display();  // Expected: 2 -> 3 -> 5 -> null

        list.deleteAtIndex(1);
        list.display();  // Expected: 2 -> 5 -> null

        // Recreate list for split test
        list = new LinkedList();
        list.insertAtEnd(2);
        list.insertAtEnd(3);
        list.insertAtEnd(5);
        list.insertAtEnd(7);
        list.insertAtEnd(11);

        // c) Test front-back split
        System.out.println("\nTesting front-back split:");
        list.display();  // Original: 2 -> 3 -> 5 -> 7 -> 11 -> null

        LinkedList[] splitLists = list.frontBackSplit();
        System.out.print("Front ");
        splitLists[0].display();  // Expected: 2 -> 3 -> 5 -> null

        System.out.print("Back ");
        splitLists[1].display();  // Expected: 7 -> 11 -> null

        // Test merge and sort
        System.out.println("\nTesting merge and sort:");
        LinkedList list1 = new LinkedList();
        list1.insertAtEnd(9);
        list1.insertAtEnd(3);
        list1.insertAtEnd(7);
        System.out.print("List 1 (unsorted): ");
        list1.display();

        LinkedList list2 = new LinkedList();
        list2.insertAtEnd(2);
        list2.insertAtEnd(8);
        list2.insertAtEnd(5);
        System.out.print("List 2 (unsorted): ");
        list2.display();

        LinkedList merged = LinkedList.mergeAndSort(list1, list2);
        System.out.print("Merged and sorted: ");
        merged.display();  // Expected: 2 -> 3 -> 5 -> 7 -> 8 -> 9 -> null
    }
}