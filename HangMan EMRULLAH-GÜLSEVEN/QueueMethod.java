public class QueueMethod {
    private int rear, front;
    private Object[] queues;


    public QueueMethod(int capacity) {
        rear = -1;
        front = 0;
        queues = new Object[capacity];
    }


    public void enqueue(Object data) {
        rear++;
        queues[rear] = data;
    }


    public Object dequeue() {
        Object data = queues[front];
        queues[front] = null;
        front++;
        return data;
    }


    boolean isEmpty() {
        return rear < front;
    }


    boolean isFull() {
        return rear + 1 == queues.length;
    }


    Object peek() {
        if (isEmpty()) {
            System.out.println("queues is empty....");
            return null;
        } else
            return queues[front];
    }

    int size()
    {
        return rear-front+1;
    }
}
