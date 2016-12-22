package carla.doe2_output;

/**
 * 
 * @author Aaron Powers
 * 
 */

import booker.lexical.AndParser;
import booker.lexical.CannotParseException;
import booker.lexical.CharacterParser;
import booker.lexical.InputSequence;
import booker.lexical.NumericParser;
import booker.lexical.OrParser;
import booker.lexical.Parser;
import booker.lexical.StringParser;
import booker.lexical.ToParser;
import booker.lexical.ZeroOrMoreParser;

public class DOE2Report {

	private String name;
	private String item;
	private InputSequence in;
	private ZeroOrMoreParser whiteSpaceParser;
	private Parser numericParser;

	public DOE2Report(String name, String item){
		this.name = name;
		this.item = item;
		in = new InputSequence(131);
		StringParser newLine = new StringParser("\r\n");
		CharacterParser blank = new CharacterParser(" ");
		CharacterParser tab = new CharacterParser("\t");
		whiteSpaceParser = new ZeroOrMoreParser(new OrParser(new Parser[]{newLine,blank,tab}));
		numericParser = new AndParser(new Parser[]{whiteSpaceParser, new NumericParser()});
	}

	public String name(){
		return name;
	}

	public String item(){
		return item;
	}

	public void setInputSequence(InputSequence in){
		this.in = in;
	}

	public void reset(){
		in.reset();
	}

	public void moveToString(String string) throws CannotParseException{
		ToParser parser = new ToParser(new StringParser(string));
		parser.parse(in);
	}
	
	public double nextNumeric()throws CannotParseException{
		return Double.parseDouble(numericParser.parse(in));
	}

}