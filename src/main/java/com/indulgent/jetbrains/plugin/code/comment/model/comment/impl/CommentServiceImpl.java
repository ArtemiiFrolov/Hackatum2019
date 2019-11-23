package com.indulgent.jetbrains.plugin.code.comment.model.comment.impl;

import com.indulgent.jetbrains.plugin.code.comment.exception.EmptyFileCanonicalPathException;
import com.indulgent.jetbrains.plugin.code.comment.exception.EmptyProjectPathException;
import com.indulgent.jetbrains.plugin.code.comment.exception.FileNotInProjectException;
import com.indulgent.jetbrains.plugin.code.comment.model.comment.Comment;
import com.indulgent.jetbrains.plugin.code.comment.model.comment.CommentService;
import com.indulgent.jetbrains.plugin.code.comment.model.comment.FileComments;
import com.indulgent.jetbrains.plugin.code.comment.util.FileUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Service for work with comments
 *
 * @author Indulgent
 *         05.06.2016.
 */
class CommentServiceImpl implements CommentService {
	private final Project project;

	private final Map<String, FileComments> fileCommentsMap = new HashMap<>();

	/**
	 * Constructor
	 *
	 * @param project current project
	 */
	CommentServiceImpl(@NotNull Project project) {
		this.project = project;
	}


	@NotNull
	@Override
	public synchronized Collection<FileComments> getAll() {
		return fileCommentsMap.values();
	}

	@Override
	public synchronized FileComments getForFile(@NotNull VirtualFile file) {
		try {
			String filePath = FileUtil.getFilePath(project, file);
			return fileCommentsMap.get(filePath);
		} catch (EmptyFileCanonicalPathException | EmptyProjectPathException | FileNotInProjectException e) {
			return null;
		}
	}

	@Override
	public synchronized void save(@NotNull Comment comment) {
		String filePath = comment.getFileInformation().getPath();
		FileComments fileComments = fileCommentsMap.get(filePath);
		if (fileComments == null) {
			fileComments = new FileCommentsImpl(comment.getFileInformation());
			fileCommentsMap.put(filePath, fileComments);
		}
		fileComments.save(comment);
	}

	@Override
	public void remove(@NotNull Collection<Comment> comments) {
		comments.forEach(this::remove);
	}

	private synchronized void remove(@NotNull Comment comment) {
		String filePath = comment.getFileInformation().getPath();
		FileComments fileComments = fileCommentsMap.get(filePath);
		if (fileComments == null) {
			return;
		}
		fileComments.remove(comment);
		if (fileComments.isEmpty()) {
			fileCommentsMap.remove(filePath);
		}
	}

	@NotNull
	protected final Project getProject() {
		return project;
	}

	protected synchronized void setData(CommentsDataBuilder dataBuilder) {
		fileCommentsMap.clear();
		dataBuilder.build().forEach(fileComments -> fileCommentsMap.put(fileComments.getFileInformation().getPath(), fileComments));
	}
}
