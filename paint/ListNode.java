package paint;

// The ListNode class is  a helper class for the LinkedList class
// It specifies the nodes that are used for the Linked List class
public class ListNode<T> {
	// data and pointer to next Node on list
    private T data;
    private ListNode next;
     
    // Constructor: No reference to next node provided so make it null 
    public ListNode( T nodeData ) {
        this( nodeData, null);
    }
     
    // Constructor: Set data and reference to next node.
    public ListNode( T nodeData, ListNode nodeNext ) {
        data = nodeData;
        next = nodeNext;
    }
     
    // Accessor: Return the data for current ListNode object
    public T getData() {
        return data;
    }
     
    // Accessor: Return reference to next ListNode object
    public ListNode getNext() {
        return next;
    }
     
    // Mutator: Set new data for current ListNode object
    public void setData( T newData ) {
        data = newData;
    }
     
     
    // Mutator: Set new reference to the next node object
    public void setNext( ListNode newNext ) {
        next = newNext;
    }
}