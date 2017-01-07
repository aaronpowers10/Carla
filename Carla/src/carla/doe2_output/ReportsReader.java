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

import booker.lexical.CannotParseException;
import booker.lexical.EndOfSequenceException;
import booker.lexical.InputSequence;
import booker.lexical.OptionalParser;
import booker.lexical.StringParser;
import booker.lexical.ToParser;

public class ReportsReader {

	public DOE2Reports read(String fileName) {
		DOE2Reports reports = new DOE2Reports();
		InputSequence in = new InputSequence(fileName, 131);

		StringParser reportFlag = new StringParser("REPORT-");

		StringParser newLine = new StringParser("\r\n");
		OptionalParser lineParser = new OptionalParser(new ToParser(newLine));

		boolean continueParsing = true;
		while (continueParsing) {
			try {
				reportFlag.parse(in);
			} catch (EndOfSequenceException e1) {
				continueParsing = false;
			} catch (CannotParseException e2) {
				lineParser.parse(in);
			}
		}

		return reports;

	}

}
