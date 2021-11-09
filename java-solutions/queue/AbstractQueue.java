package queue;

public abstract class AbstractQueue implements Queue{
    protected int size = 0;

    public void enqueue(Object element){
        assert element != null;
        enqueueImpl(element);
        size++;
    }

    protected abstract void enqueueImpl(Object element);

    public Object element(){
        assert size() > 0;
        return elementImpl();
    }

    public  int size(){
        return size;
    };
    protected abstract Object elementImpl();

    public Object dequeue(){
        Object result = element();
        dequeueImpl();
        size--;
        return result;
    }

    protected abstract void dequeueImpl();

    public boolean isEmpty(){
        return size() == 0;
    }

    public Object[] toArray(){
        Object[] result = new Object[size()];
        for (int i = 0; i < size(); i++){
            Object element = dequeue();
            result[i] = element;
            enqueue(element);
        }
        return result;
    }

    public void clear(){
        size = 0;
        clearImpl();
    }

    protected abstract void clearImpl();

}
