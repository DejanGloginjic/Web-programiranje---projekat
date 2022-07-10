package sysClock;

import dao.MembershipDAO;
import dao.UserDAO;

public class CheckMembership extends Thread{
	
	private static int sleep_time = 10 * 1000;	//10 sekundi
	
	 public void run(){
	       while(true) {
	    	   try {
				Thread.sleep(sleep_time);
				
				
				MembershipDAO.getInstance().checkMemberships();
				UserDAO.getInstance().setBuyerTypes();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	       }
	 }

}
