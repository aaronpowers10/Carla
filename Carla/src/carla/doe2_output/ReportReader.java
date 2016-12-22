package carla.doe2_output;

import java.util.ArrayList;

import booker.building_data.UpdateListener;
import booker.lexical.CannotParseException;
import booker.lexical.ConsecutiveColumnParser;
import booker.lexical.InputSequence;
import booker.lexical.OptionalParser;
import booker.lexical.Parser;
import booker.lexical.StringParser;
import booker.lexical.ToParser;
import booker.lexical.UpToParser;

/**
 * 
 * @author Aaron Powers
 *
 */

public class ReportReader {

	private Parser reportFlag;
	private Parser reportNameParser;
	private Parser reportItemParser;
	private OptionalParser lineParser;
	private Parser reportTextParser;
	private ArrayList<UpdateListener> listeners;
	private DOE2Report report;

	public ReportReader(ArrayList<UpdateListener> listeners){
		this.listeners = listeners;
		reportFlag = new StringParser("REPORT-");
		reportNameParser = new ConsecutiveColumnParser(36);
		reportItemParser = new ConsecutiveColumnParser(48);
		StringParser newLine = new StringParser("\r\n");
		lineParser = new OptionalParser(new ToParser(newLine));
		reportTextParser = new UpToParser(reportFlag);
	}

	public DOE2Report read(InputSequence in)throws CannotParseException{

		reportFlag.parse(in);
		report = new DOE2Report(reportNameParser.parse(in).trim(),reportItemParser.parse(in).trim());
		lineParser.parse(in);
		report.setInputSequence(reportTextParser.parseAsInputSequence(in));
		updateListeners();
		return report;

	}

	private void updateListeners(){
		for(int i=0; i<listeners.size(); i++){
			listeners.get(i).update("Reading report " + report.name() + " " + report.item());
		}
	}

}
