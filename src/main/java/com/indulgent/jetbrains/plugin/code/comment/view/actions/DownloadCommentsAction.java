package com.indulgent.jetbrains.plugin.code.comment.view.actions;

import com.indulgent.jetbrains.plugin.code.comment.logging.Log;
import com.indulgent.jetbrains.plugin.code.comment.model.comment.CommentServiceFactory;
import com.indulgent.jetbrains.plugin.code.comment.model.comment.FileComments;
import com.indulgent.jetbrains.plugin.code.comment.template.Template;
import com.indulgent.jetbrains.plugin.code.comment.template.processor.TemplateProcessor;
import com.indulgent.jetbrains.plugin.code.comment.template.processor.TemplateProcessorFactory;
import com.indulgent.jetbrains.plugin.code.comment.template.service.TemplateService;
import com.indulgent.jetbrains.plugin.code.comment.template.service.TemplateServiceFactory;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.InputValidator;
import com.intellij.openapi.ui.Messages;
import com.intellij.util.PathUtil;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

/**
 * Action for download comments to file
 *
 * @author Indulgent
 *         03.06.2016.
 */
public class DownloadCommentsAction extends AnAction {
	private static final String TEMPLATE_KEY = "TEMPLATE";
	private static final String DEFAULT =
			"==========\n" +
			"<#list fileComments as fileComment>\n" +
				"File: ${fileComment.fileInformation.path}\n" +
				"-----\n" +
				"<#list fileComment.comments as comment>\n" +
					"${comment.codeInformation.codeText}:\n" +
					"vvvvvv\n" +
					"<#list comment.commentHistory.records as record>\n" +
						"${record.text}\n" +
					"</#list>\n" +
					"-----\n" +
				"</#list>\n\n" +
				"==========\n" +
			"</#list>";

	@Override
	public void actionPerformed(AnActionEvent event) {
		Project project = event.getProject();
		if (project == null) {
			Messages.showInfoMessage("File not found.", "Attention");
			return;
		}

		Template template = selectTemplate(project);
		if (template == null) {
			return;
		}

		String filePath = getFilePath(project);
		if (filePath == null || filePath.length() == 0) {
			return;
		}

		try {
			FileWriter fileWriter = new FileWriter(filePath);

			Collection<FileComments> comments = CommentServiceFactory.getService(project).getAll();

			TemplateProcessor processor = TemplateProcessorFactory.getProcessor(template);
			processor.process(template, Collections.singletonMap("fileComments", comments), fileWriter);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			Log.getInstance(project).error("File not downloaded.", e);
		}
	}

	@Nullable
	private String getFilePath(Project project) {
		return Messages.showInputDialog(project, "Enter destination path", "Path", null, null, new InputValidator() {
			@Override
			public boolean checkInput(String inputString) {
				return new File(PathUtil.getParentPath(inputString)).exists() && PathUtil.isValidFileName(PathUtil.getFileName(inputString)) && !new File(inputString).isDirectory();
			}

			@Override
			public boolean canClose(String inputString) {
				return true;
			}
		});
	}

	@Nullable
	private Template selectTemplate(Project project) {
		TemplateService templateService = TemplateServiceFactory.getService(project);

		Template template = templateService.getByKey(TEMPLATE_KEY);

		String body = template == null ? DEFAULT : template.getBody();
		String templateBody = Messages.showMultilineInputDialog(project, null, "Template", body, AllIcons.General.Configure, null);
		if (templateBody == null || templateBody.length() == 0) {
			return null;
		}

		template = new Template(TEMPLATE_KEY, templateBody);
		templateService.save(template);
		return template;
	}
}
