package components;

import java.util.ArrayList;

public class Queue<E> extends ArrayList<E> {

	private static final long serialVersionUID = -3088158201946497866L;
	
	public E pop(){
		E top = this.get(0);
		this.remove(0);
		return top;
	}
	
	public void push(E value){
		this.add(value);
	}
	
	public void updateValue(E value, int index){
		this.set(index,value);
	}
}
