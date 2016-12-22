package carla.doe2_output;

/**
 * 
 * @author Aaron Powers
 * 
 */

import java.util.ArrayList;

public class DOE2Reports {
	
	private ArrayList<DOE2Report> reports;
	
	public DOE2Reports(){
		reports = new ArrayList<DOE2Report>();
	}
	
	public void add(DOE2Report report){
		reports.add(report);
	}

}
