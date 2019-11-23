package com.indulgent.jetbrains.plugin.code.comment.model.comment.impl;

import com.indulgent.jetbrains.plugin.code.comment.model.comment.Comment;
import com.indulgent.jetbrains.plugin.code.comment.model.comment.FileComments;
import com.indulgent.jetbrains.plugin.code.comment.model.comment.FileInformation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Comments for file
 *
 * @author Indulgent
 *         04.06.2016.
 */
class FileCommentsImpl implements FileComments {
	private final FileInformation fileInformation;
	private final Collection<Comment> comments = new ArrayList<>();

	FileCommentsImpl(FileInformation fileInformation) {
		this.fileInformation = fileInformation;
	}

	@Override
	@NotNull
	public FileInformation getFileInformation() {
		return fileInformation;
	}

	@Override
	@NotNull
	public Collection<Comment> getComments() {
		return comments;
	}

	@Override
	public void save(Comment comment) {
		for (Comment existsComment : comments) {
			if (isSameComments(existsComment, comment))
				return;
		}
		comments.add(comment);
	}

	@Override
	public void remove(@NotNull Comment comment) {
		Iterator<Comment> iterator = comments.iterator();
		while (iterator.hasNext()) {
			if (isSameComments(comment, iterator.next())) {
				iterator.remove();
				return;
			}
		}
	}

	@Override
	public boolean isEmpty() {
		return comments.isEmpty();
	}

	private boolean isSameComments(Comment first, Comment second) {
		return first.equals(second);
	}
}
