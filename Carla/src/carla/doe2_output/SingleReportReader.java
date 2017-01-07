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

	public SingleReportReader() {
		listeners = new ArrayList<UpdateListener>();
	}

	public void addListener(UpdateListener listener) {
		listeners.add(listener);
	}

	public DOE2Report read(String fileName, String reportName) {
		InputSequence in = new InputSequence(fileName, 131);

		StringParser newLine = new StringParser("\r\n");
		OptionalParser lineParser = new OptionalParser(new ToParser(newLine));

		ReportReader reportReader = new ReportReader(listeners);

		boolean continueParsing = true;
		while (continueParsing) {
			try {
				DOE2Report report = reportReader.read(in);
				if (report.name().equals(reportName)) {
					return report;
				}
			} catch (CannotParseException e1) {
				lineParser.parse(in);
			} catch (EndOfSequenceException e2) {
				continueParsing = false;
			}
		}
		return null;

	}

}
