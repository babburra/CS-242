/*   This file is part of lanSimulation.
 *
 *   lanSimulation is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   lanSimulation is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with lanSimulation; if not, write to the Free Software
 *   Foundation, Inc. 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *
 *   Copyright original Java version: 2004 Bart Du Bois, Serge Demeyer
 *   Copyright C++ version: 2006 Matthias Rieger, Bart Van Rompaey
 */
package lanSimulation.internals;

import lanSimulation.Network;

import java.io.IOException;
import java.io.Writer;

/**
 * A <em>Node</em> represents a single Node in a Local Area Network (LAN).
 * Several types of Nodes exist.
 */
public class Node {
	// enumeration constants specifying all legal node types
	/**
	 * A node with type NODE has only basic functionality.
	 */
	public static final byte NODE = 0;
	/**
	 * A node with type WORKSTATION may initiate requests on the LAN.
	 */
	public static final byte WORKSTATION = 1;
	/**
	 * A node with type PRINTER may accept packages to be printed.
	 */
	public static final byte PRINTER = 2;

	/**
	 * Holds the type of the Node.
	 */
	public byte type;
	/**
	 * Holds the name of the Node.
	 */
	public String name;
	/**
	 * Holds the next Node in the token ring architecture.
	 * 
	 * @see lanSimulation.internals.Node
	 */
	public Node nextNode;

	/**
	 * Construct a <em>Node</em> with given #type and #name.
	 * <p>
	 * <strong>Precondition:</strong> (type >= NODE) & (type <= PRINTER);
	 * </p>
	 */
	public Node(byte _type, String _name) {
		assert (_type >= NODE) & (_type <= PRINTER);
		type = _type;
		name = _name;
		nextNode = null;
	}

	/**
	 * Construct a <em>Node</em> with given #type and #name, and which is linked
	 * to #nextNode.
	 * <p>
	 * <strong>Precondition:</strong> (type >= NODE) & (type <= PRINTER);
	 * </p>
	 */
	public Node(byte _type, String _name, Node _nextNode) {
		assert (_type >= NODE) & (_type <= PRINTER);
		type = _type;
		name = _name;
		nextNode = _nextNode;
	}

	public boolean printDocument(Packet document, Writer report, Network network) {
		String author = "Unknown";
		String title = "Untitled";
		int startPos = 0, endPos = 0;

		if (type == PRINTER) {
			try {
				if (document.message.startsWith("!PS")) {
					startPos = document.message.indexOf("author:");
					if (startPos >= 0) {
						endPos = document.message.indexOf(".", startPos + 7);
						if (endPos < 0) {
							endPos = document.message.length();
						}

						author = document.message.substring(startPos + 7,
								endPos);
					}

					startPos = document.message.indexOf("title:");
					if (startPos >= 0) {
						endPos = document.message.indexOf(".", startPos + 6);
						if (endPos < 0) {
							endPos = document.message.length();
						}
						title = document.message
								.substring(startPos + 6, endPos);
					}

					String jobType = "Postscript";
					report.write("\tAccounting -- author = '");
					report.write(author);
					report.write("' -- title = '");
					report.write(title);
					report.write("'\n");
					report.write(">>> " + jobType + " job delivered.\n\n");
					report.flush();
				} else {
					title = "ASCII DOCUMENT";
					if (document.message.length() >= 16) {
						author = document.message.substring(8, 16);
					}

					String jobType = "ASCII Print";
					writeAccountingReport(report, author, title, jobType);
				}

			} catch (IOException exc) {
				// just ignore
			}
			return true;
		} else {
			try {
				report
						.write(">>> Destination is not a printer, print job canceled.\n\n");
				report.flush();
			} catch (IOException exc) {
				// just ignore
			}
			return false;
		}
	}

	private void writeAccountingReport(Writer report, String author, String title, String jobType) throws IOException {
		report.write("\tAccounting -- author = '");
		report.write(author);
		report.write("' -- title = '");
		report.write(title);
		report.write("'\n");
		report.write(">>> " + jobType + " job delivered.\n\n");
		report.flush();
	}

	public void printCurrentNode(Writer report, String str, String name, String str2) throws IOException {
		report.write(str);
		report.write(name);
		report.write(str2);
	}
}