/*
 *
 *  Copyright (C) 2017 Aaron Powers
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package carla.doe2_output;

import otis.lexical.AndParser;
import otis.lexical.CannotParseException;
import otis.lexical.CharacterParser;
import otis.lexical.ConsecutiveColumnParser;
import otis.lexical.InputSequence;
import otis.lexical.NumericParser;
import otis.lexical.OrParser;
import otis.lexical.Parser;
import otis.lexical.StringParser;
import otis.lexical.ToParser;
import otis.lexical.ZeroOrMoreParser;

public class DOE2Report {

	private String name;
	private String item;
	private InputSequence in;
	private ZeroOrMoreParser whiteSpaceParser;
	private Parser numericParser;

	public DOE2Report(String name, String item) {
		this.name = name;
		this.item = item;
		in = new InputSequence(131);
		StringParser newLine = new StringParser("\r\n");
		CharacterParser blank = new CharacterParser(" ");
		CharacterParser tab = new CharacterParser("\t");
		whiteSpaceParser = new ZeroOrMoreParser(new OrParser(new Parser[] { newLine, blank, tab }));
		numericParser = new AndParser(new Parser[] { whiteSpaceParser, new NumericParser() });
	}

	public String name() {
		return name;
	}

	public String item() {
		return item;
	}

	public void setInputSequence(InputSequence in) {
		this.in = in;
	}

	public void reset() {
		in.reset();
	}

	public void moveToString(String string) throws CannotParseException {
		ToParser parser = new ToParser(new StringParser(string));
		parser.parse(in);
	}

	public double nextNumeric() throws CannotParseException {
		return Double.parseDouble(numericParser.parse(in));
	}
	
	public double readNumericFromColumns(int fromColumn, int toColumn)throws CannotParseException{
		ConsecutiveColumnParser ignoreParser = new ConsecutiveColumnParser(fromColumn);
		ignoreParser.parse(in);
		return Double.parseDouble(new ConsecutiveColumnParser(toColumn-fromColumn).parse(in));
	}

}
