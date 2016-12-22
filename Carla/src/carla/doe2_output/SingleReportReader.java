package carla.doe2_output;

/**
 * 
 * @author Aaron Powers
 * 
 */

import java.util.ArrayList;

import booker.building_data.UpdateListener;
import booker.lexical.CannotParseException;
import booker.lexical.EndOfSequenceException;
import booker.lexical.InputSequence;
import booker.lexical.OptionalParser;
import booker.lexical.StringParser;
import booker.lexical.ToParser;

public class SingleReportReader {
	
	private ArrayList<UpdateListener> listeners;
	
	public SingleReportReader(){
		listeners = new ArrayList<UpdateListener>();
	}
	
	public void addListener(UpdateListener listener){
		listeners.add(listener);
	}
	
	public DOE2Report read(String fileName, String reportName){
		InputSequence in = new InputSequence(fileName,131);
		
		StringParser newLine = new StringParser("\r\n");
		OptionalParser lineParser = new OptionalParser(new ToParser(newLine));
		
		ReportReader reportReader = new ReportReader(listeners);


		boolean continueParsing = true;
		while(continueParsing){
			try{
				DOE2Report report = reportReader.read(in);
				if(report.name().equals(reportName)){
					return report;
				}
			} catch (CannotParseException e1){
				lineParser.parse(in);
			} catch (EndOfSequenceException e2){
				continueParsing = false;
			}
		}
		return null;
		
	}

}
