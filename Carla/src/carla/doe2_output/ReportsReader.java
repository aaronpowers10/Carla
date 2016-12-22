package carla.doe2_output;

/**
 * @author Aaron Powers
 */

import booker.lexical.CannotParseException;
import booker.lexical.EndOfSequenceException;
import booker.lexical.InputSequence;
import booker.lexical.OptionalParser;
import booker.lexical.StringParser;
import booker.lexical.ToParser;

public class ReportsReader {
	
	public DOE2Reports read(String fileName){
		DOE2Reports reports = new DOE2Reports();
		InputSequence in = new InputSequence(fileName,131);
		
		StringParser reportFlag = new StringParser("REPORT-");
		
		StringParser newLine = new StringParser("\r\n");
		OptionalParser lineParser = new OptionalParser(new ToParser(newLine));
		
		boolean continueParsing = true;
		while(continueParsing){
			try{
				reportFlag.parse(in);
			} catch (EndOfSequenceException e1){
				continueParsing = false;
			} catch (CannotParseException e2){
				lineParser.parse(in);
			}
		}
		
		return reports;
		
	}

}
