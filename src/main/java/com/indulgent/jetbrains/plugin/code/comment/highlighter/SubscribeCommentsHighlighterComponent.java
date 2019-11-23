package com.indulgent.jetbrains.plugin.code.comment.highlighter;

import com.indulgent.jetbrains.plugin.code.comment.messaging.MessagingProvider;
import com.intellij.codeInsight.daemon.DaemonCodeAnalyzer;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * Component of highlighter subscriber
 *
 * @author Indulgent
 *         29.05.2016.
 */
public class SubscribeCommentsHighlighterComponent implements ProjectComponent {

	private final Project project;

	/**
	 * Constructor
	 *
	 * @param project current project
	 */
	public SubscribeCommentsHighlighterComponent(Project project) {
		this.project = project;
	}

	@Override
	public void initComponent() {
		MessagingProvider.getInstance(project).subscribeAddComments(comments -> DaemonCodeAnalyzer.getInstance(project).restart());
		MessagingProvider.getInstance(project).subscribeRemoveComments(comments -> DaemonCodeAnalyzer.getInstance(project).restart());
		MessagingProvider.getInstance(project).subscribeRefreshComments(() -> DaemonCodeAnalyzer.getInstance(project).restart());
	}

	@Override
	public void disposeComponent() {
	}

	@Override
	@NotNull
	public String getComponentName() {
		return "CodeComments.SubscribeCommentsHighlighterComponent";
	}

	@Override
	public void projectOpened() {
	}

	@Override
	public void projectClosed() {
	}
}
