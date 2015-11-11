import java.applet.Applet;


public class count  implements Runnable {

	private String Name;
	private Thread t;
	private int h =0;
	
	public count(String n) {
		 t = new Thread(this, n);
	      System.out.println("Child thread: " + t);
	     // t.start(); // Start the thread
	}
	
	@Override
	public void run() {
		 try {
	         for(int i = 0; i < 600; i++) {
	            setH(i);
	            // Let the thread sleep for a while.
	            Thread.sleep(500);
	         }
	     } catch (InterruptedException e) {
	         System.out.println("Child interrupted.");
	     }
	     System.out.println("Exiting child thread.");
	   }
	

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

}
