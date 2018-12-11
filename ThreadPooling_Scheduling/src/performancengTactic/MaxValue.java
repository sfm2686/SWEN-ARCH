package performancengTactic;
import java.util.ArrayList;
import java.util.Random;

class MaxValue implements Runnable {
    int size, priority;
    ArrayList<Integer> intArray ;

    MaxValue(int size, int priority) {
        this.priority = priority;
        intArray = new ArrayList();
        this.size = size;
    }

    private void populate(int size){
        Random rand = new Random();
        for(int j = 1; j<=size; j++){
            int random = rand.nextInt(10000);
            this.intArray.add(random);
        }
    }

    @Override
    public void run() {
        populate(size);
        try {

            System.out.println("Start Thread ID: "+ size + " with priority " + priority);
            System.out.println();
            Thread.sleep(size*80);
            System.out.println("End  thread ID: "+ size + " The Max number is " + FindMax() );
            System.out.println();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int FindMax() {
        int MAX = intArray.get(0);
        for(int n: intArray) {
            if (n > MAX) {
                MAX = n;
            }
        }
        return MAX;
    }
}