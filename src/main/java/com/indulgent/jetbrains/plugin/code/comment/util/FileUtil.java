package com.indulgent.jetbrains.plugin.code.comment.util;

import com.indulgent.jetbrains.plugin.code.comment.exception.EmptyFileCanonicalPathException;
import com.indulgent.jetbrains.plugin.code.comment.exception.EmptyProjectPathException;
import com.indulgent.jetbrains.plugin.code.comment.exception.FileNotInProjectException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

/**
 * Utility class for work with files
 *
 * @author Indulgent
 *         06.06.2016.
 */
public final class FileUtil {
	private FileUtil() {
	}

	/**
	 * Get relative path to file
	 *
	 * @param project file holder project
	 * @param file    destination file
	 * @return path
	 * @throws EmptyFileCanonicalPathException empty canonical path to file
	 * @throws EmptyProjectPathException       empty path to project
	 * @throws FileNotInProjectException       work with non-project file
	 */
	@NotNull
	public static String getFilePath(@NotNull Project project, @NotNull VirtualFile file) throws EmptyFileCanonicalPathException, EmptyProjectPathException, FileNotInProjectException {
		String projectPath = project.getBasePath();
		String canonicalPath = file.getCanonicalPath();
		if (canonicalPath == null) {
			throw new EmptyFileCanonicalPathException(file);
		}
		if (projectPath == null) {
			throw new EmptyProjectPathException(project);
		}
		if (!canonicalPath.startsWith(projectPath)) {
			throw new FileNotInProjectException(project, file);
		}
		return canonicalPath.substring(projectPath.length() + 1);
	}
}
