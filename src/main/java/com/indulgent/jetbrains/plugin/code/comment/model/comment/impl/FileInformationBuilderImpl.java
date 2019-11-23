package com.indulgent.jetbrains.plugin.code.comment.model.comment.impl;

import com.indulgent.jetbrains.plugin.code.comment.exception.EmptyFileCanonicalPathException;
import com.indulgent.jetbrains.plugin.code.comment.exception.EmptyProjectPathException;
import com.indulgent.jetbrains.plugin.code.comment.exception.FileNotInProjectException;
import com.indulgent.jetbrains.plugin.code.comment.model.comment.FileInformation;
import com.indulgent.jetbrains.plugin.code.comment.util.FileUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

/**
 * Builder of file information
 *
 * @author Indulgent
 *         04.06.2016.
 */
class FileInformationBuilderImpl implements FileInformationBuilder {
	/**
	 * Build file information
	 *
	 * @param project current project
	 * @param file    current file
	 * @return file information
	 */
	@NotNull
	public FileInformation build(@NotNull Project project, @NotNull VirtualFile file) throws EmptyFileCanonicalPathException, EmptyProjectPathException, FileNotInProjectException {
		String filePath = FileUtil.getFilePath(project, file);
		return new FileInformationImpl(filePath);
	}
}
