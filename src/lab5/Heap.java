package lab5;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * TODO class declaration
 * We want to accept any type which is comparable to itself
 *
 * @param <T>
 */
public class Heap<T extends Comparable<T>> {
    private ArrayList<T> container;

    public Heap() {
        container = new ArrayList<>();
    }

    /**
     * @return If size is 0, throw {@link IllegalStateException}.
     * Otherwise, return the first element of {@link this#container}
     */
    public T peek() {
        //TODO
    	//System.out.print("hii ");
    	if(this.size() == 0) {
    		//System.out.print("this? ");
    		throw new IllegalStateException("@link IllegalStateException");
    	}
    	else {
    		/*
    		System.out.println();
    		System.out.print("Peak:[ ");
    		System.out.print(this.container.get(0)+" ]");
    		*/
    		return this.container.get(0);
    	}
    }

    /**
     *
     * @return If size is 0, throw {@link IllegalStateException}. Otherwise, temporarily save the first element.
     * Afterwards, set the first position to the last element, and remove the last element.
     * Call {@link this#heapifyDown()}, then return the original first element
     */
    public T poll() {
        //TODO
    	if(this.size() == 0) {
    		throw new IllegalStateException("@link IllegalStateException");
    	}
    	else {
    		T temp = this.container.get(0);
    		
    		//System.out.print("Polled: "+temp+ " && ");
    		
    		this.swap(this.container.size()-1, 0);
    		this.container.remove(this.container.size()-1);
    		heapifyDown();
    		/*
        	for(int i = 0; i < this.container.size();i++) {
        		System.out.print(i+": "+this.container.get(i) + "| " );
        	}
        	System.out.println();
        	*/
    		return temp;
    	}
    }

    private void heapifyDown() {
        int pos = 0;
        while (hasLeft(pos)) {
            int smallerChildIndex = getLeftIndex(pos);
            if (hasRight(pos) && container.get(getRightIndex(pos)).compareTo(container.get(getLeftIndex(pos))) < 0) {
                smallerChildIndex = getRightIndex(pos);
            }
            if (container.get(pos).compareTo(container.get(smallerChildIndex)) < 0) {
                break;
            } else {
                swap(pos, smallerChildIndex);
            }
            pos = smallerChildIndex;
        }
    }

    /**
     * Add the object into {@link this#container}, then call {@link this#heapifyUp()}
     *
     * @param obj the object to add
     */
    public void add(T obj) {
        //TODO
    	this.container.add(obj);
    	/*
    	for(int i = 0; i < this.container.size(); i++) {	
    		System.out.print(this.container.get(i)+ " ");}
    	System.out.println();
    	*/
    	this.heapifyUp();
    }

    public void addAll(Collection<T> list) {
        list.forEach(this::add);
    }

    /**
     * While the last element has a parent and is smaller than its parent, swap the two elements. Then, check again
     * with the new parent until there's either no parent or we're larger than our parent.
     */
    private void heapifyUp() {
        // TODO
    	
    	int indexOfTarget = this.container.size()-1;
    	
    	while(hasParent(indexOfTarget) && this.container.get(indexOfTarget).compareTo(this.container.get(getParentIndex(indexOfTarget) ))<0 ) {
    		//System.out.print(" || " + this.container.get(indexOfTarget) + " " + this.container.get(getParentIndex(indexOfTarget)));
    		swap(indexOfTarget,getParentIndex(indexOfTarget));
    		//System.out.print(" " + this.container.get(indexOfTarget) + " " + this.container.get(getParentIndex(indexOfTarget)) + " | ");
    		indexOfTarget = getParentIndex(indexOfTarget);
    		//break;
    	}
    	/*
    	while(hasParent(this.container.size()-1) && 
    		this.container.get(this.container.size()-1).compareTo(this.container.get(getParentIndex(this.container.size()-1)))>=1) {
    		System.out.print(" || " + this.container.get(this.container.size()-1) + " " + this.container.get(getParentIndex(this.container.size()-1)));
    		swap(this.container.size()-1,getParentIndex(this.container.size()-1));
    		System.out.print(" " + this.container.get(this.container.size()-1) + " " + this.container.get(getParentIndex(this.container.size()-1)) + " | ");
    	}*/
    	
    	/*
    	System.out.println();
    	for(int i = 0; i < this.container.size();i++) {
    		System.out.print(i+": "+this.container.get(i) + " " );
    	}System.out.println();
    	*/
    	
    }

    public int size() {
        return container.size();
    }

    private int getParentIndex(int i) {
        return (i - 1) / 2;
    }

    private int getLeftIndex(int i) {
        return 2 * i + 1;
    }

    private int getRightIndex(int i) {
        return 2 * i + 2;
    }

    private boolean hasParent(int i) {
        return getParentIndex(i) >= 0;
    }

    private boolean hasLeft(int i) {
        return getLeftIndex(i) < container.size();
    }

    private boolean hasRight(int i) {
        return getRightIndex(i) < container.size();
    }

    private void swap(int i1, int i2) {
        Collections.swap(container, i1, i2);
    }
}