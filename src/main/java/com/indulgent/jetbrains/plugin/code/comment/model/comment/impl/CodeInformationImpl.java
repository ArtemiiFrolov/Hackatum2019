package com.indulgent.jetbrains.plugin.code.comment.model.comment.impl;

import com.indulgent.jetbrains.plugin.code.comment.model.comment.CodeInformation;

/**
 * Information about code
 *
 * @author Indulgent
 *         04.06.2016.
 */
class CodeInformationImpl implements CodeInformation {

	private String codeText;
	private int start;
	private int end;
	private int line = UNDEFINED;
	private int column = UNDEFINED;

	@Override
	public String getCodeText() {
		return codeText;
	}

	/**
	 * Set text of commented code
	 *
	 * @param codeText text
	 */
	void setCodeText(String codeText) {
		this.codeText = codeText;
	}

	@Override
	public int getStart() {
		return start;
	}

	/**
	 * Set start position in file
	 *
	 * @param start position
	 */
	void setStart(int start) {
		this.start = start;
	}

	@Override
	public int getEnd() {
		return end;
	}

	/**
	 * Set end position in file
	 *
	 * @param end position
	 */
	void setEnd(int end) {
		this.end = end;
	}

	@Override
	public int getLine() {
		return line;
	}

	/**
	 * Set line number of start commented code
	 *
	 * @param line line number
	 */
	void setLine(int line) {
		this.line = line;
	}

	@Override
	public int getColumn() {
		return column;
	}

	/**
	 * Set column number of start commented code
	 *
	 * @param column column number
	 */
	void setColumn(int column) {
		this.column = column;
	}
}
