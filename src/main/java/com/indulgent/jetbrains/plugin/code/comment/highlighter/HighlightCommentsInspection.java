package com.indulgent.jetbrains.plugin.code.comment.highlighter;

import com.indulgent.jetbrains.plugin.code.comment.model.comment.CodeInformation;
import com.indulgent.jetbrains.plugin.code.comment.model.comment.Comment;
import com.indulgent.jetbrains.plugin.code.comment.model.comment.CommentServiceFactory;
import com.indulgent.jetbrains.plugin.code.comment.model.comment.FileComments;
import com.indulgent.jetbrains.plugin.code.comment.util.CommentHistoryUtil;
import com.intellij.codeInspection.InspectionManager;
import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * Inspection for detect and show comments in file
 *
 * @author Indulgent
 *         13.05.2016.
 */
public class HighlightCommentsInspection extends LocalInspectionTool {

	private static final ProblemDescriptor[] EMPTY = new ProblemDescriptor[0];

	protected FileComments getCommentsForFile(@NotNull Project project, @NotNull VirtualFile file) {
		return CommentServiceFactory.getService(project).getForFile(file);
	}

	@Nullable
	@Override
	public final ProblemDescriptor[] checkFile(@NotNull PsiFile file, @NotNull InspectionManager manager, boolean isOnTheFly) {
		Project project = file.getProject();
		FileComments commentsForFile = getCommentsForFile(project, file.getVirtualFile());
		if (commentsForFile == null || commentsForFile.isEmpty()) {
			return EMPTY;
		}

		Collection<Comment> comments = commentsForFile.getComments();
		ProblemDescriptor[] result = new ProblemDescriptor[comments.size()];
		int i = 0;
		for (Comment comment : comments) {
			CodeInformation codeInformation = comment.getCodeInformation();
			result[i++] = manager.createProblemDescriptor(file, new TextRange(codeInformation.getStart(), codeInformation.getEnd()), getHighlighterMessage(comment), ProblemHighlightType.GENERIC_ERROR_OR_WARNING, true);
		}

		return result;
	}

	@NotNull
	private String getHighlighterMessage(Comment comment) {
		return CommentHistoryUtil.getComment(comment);
	}
}
