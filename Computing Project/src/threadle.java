import javax.swing.JButton;


public class threadle implements Runnable{




	private JButton butt;

	public threadle(JButton button){

		butt = button;

	}

	@Override
	public void run() {
		try {

			butt.setText("Organising");

		} catch(Exception e){
		}
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
