public class StackMethod {
    private int top;
    private Object[] stacks;

    public StackMethod(int capacity) {
        top = -1;
        stacks = new Object[capacity];
    }

    public void push(Object data) {
        top++;
        stacks[top] = data;
    }

    public Object pop() {
        Object data = stacks[top];
        stacks[top] = null;
        top--;
        return data;
    }

    boolean isEmpty() {
        return top==-1;
    }

    boolean isFull() {
        return top + 1 == stacks.length;
    }

    Object peek() {
        if (isEmpty()) {
            System.out.println("stack is empty");
            return null;
        } else
            return stacks[top];
    }

    int size()
    {
        return top+1;
    }
}
