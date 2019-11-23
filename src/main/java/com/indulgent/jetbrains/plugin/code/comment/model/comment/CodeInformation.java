package com.indulgent.jetbrains.plugin.code.comment.model.comment;

/**
 * Information about code
 *
 * @author Indulgent
 *         05.06.2016.
 */
public interface CodeInformation {
	int UNDEFINED = -1;

	/**
	 * Get text of commented code
	 *
	 * @return text
	 */
	String getCodeText();

	/**
	 * Get start position in file
	 *
	 * @return position
	 */
	int getStart();

	/**
	 * Get end position in file
	 *
	 * @return position
	 */
	int getEnd();

	/**
	 * Get line number of start commented code
	 *
	 * @return line number
	 */
	int getLine();

	/**
	 * Get column number of start commented code
	 *
	 * @return column number
	 */
	int getColumn();
}
