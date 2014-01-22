package engine.generics;

import java.util.ArrayList;
import java.util.Random;

public class ShuffleBag<T> {
    // TODO: Add actual bag functionality, not just arraylist
    ArrayList<T> bag = new ArrayList<T>();

    public void add(T item) {
        bag.add(item);
    }

    public void shuffle() {
        Random r = new Random();

        for(int i = bag.size()-1; i >= 0; i--) {
            int range = r.nextInt(i+1);
            T obj = bag.get(range);
            bag.set(range, bag.get(i));
            bag.set(i, obj);
        }
    }

    public void print() {
        for(T obj : bag) {
            System.out.println(obj);
        }
    }
}
