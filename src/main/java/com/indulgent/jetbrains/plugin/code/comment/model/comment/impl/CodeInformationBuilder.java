package com.indulgent.jetbrains.plugin.code.comment.model.comment.impl;

import com.indulgent.jetbrains.plugin.code.comment.model.comment.CodeInformation;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.editor.VisualPosition;
import org.jetbrains.annotations.NotNull;

/**
 * Builder of code information
 *
 * @author Indulgent
 *         04.06.2016.
 */
class CodeInformationBuilder {
	private final SelectionModel selectionInformation;

	/**
	 * Constructor
	 *
	 * @param selectionInformation information about selection in file
	 */
	CodeInformationBuilder(@NotNull SelectionModel selectionInformation) {

		this.selectionInformation = selectionInformation;
	}

	/**
	 * Build information about code
	 *
	 * @return information
	 */
	@NotNull
	CodeInformation build() {
		CodeInformationImpl information = new CodeInformationImpl();
		information.setCodeText(selectionInformation.getSelectedText());
		information.setStart(selectionInformation.getSelectionStart());
		information.setEnd(selectionInformation.getSelectionEnd());
		VisualPosition selectionStartPosition = selectionInformation.getSelectionStartPosition();
		if (selectionStartPosition != null) {
			information.setLine(selectionStartPosition.getLine());
			information.setColumn(selectionStartPosition.getColumn());
		}
		return information;
	}
}
