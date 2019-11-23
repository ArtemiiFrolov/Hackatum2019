package com.indulgent.jetbrains.plugin.code.comment.model.comment.impl;

import com.indulgent.jetbrains.plugin.code.comment.exception.EmptyFileCanonicalPathException;
import com.indulgent.jetbrains.plugin.code.comment.exception.EmptyProjectPathException;
import com.indulgent.jetbrains.plugin.code.comment.exception.FileNotInProjectException;
import com.indulgent.jetbrains.plugin.code.comment.model.comment.FileInformation;
import com.indulgent.jetbrains.plugin.code.comment.util.FileUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Builder of file information which add result in cache
 *
 * @author Indulgent
 *         06.06.2016.
 */
class FileInformationBuilderCache implements FileInformationBuilder {
	private static final Map<String, FileInformation> fileInformationMap = new HashMap<>();

	private final FileInformationBuilder builder;

	/**
	 * Constructor
	 *
	 * @param builder make information builder
	 */
	FileInformationBuilderCache(FileInformationBuilder builder) {
		this.builder = builder;
	}

	@NotNull
	static synchronized FileInformation build(String filePath) {
		FileInformation fileInformation = fileInformationMap.get(filePath);
		if (fileInformation == null) {
			fileInformation = new FileInformationImpl(filePath);
			fileInformationMap.put(filePath, fileInformation);
		}
		return fileInformation;
	}

	@NotNull
	@Override
	public synchronized FileInformation build(@NotNull Project project, @NotNull VirtualFile file) throws EmptyFileCanonicalPathException, EmptyProjectPathException, FileNotInProjectException {
		String filePath = FileUtil.getFilePath(project, file);

		FileInformation fileInformation = fileInformationMap.get(filePath);
		if (fileInformation == null) {
			fileInformation = builder.build(project, file);
			fileInformationMap.put(filePath, fileInformation);
		}
		return fileInformation;
	}
}
