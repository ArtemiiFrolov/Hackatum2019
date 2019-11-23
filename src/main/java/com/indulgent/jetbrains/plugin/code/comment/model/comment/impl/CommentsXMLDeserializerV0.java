package com.indulgent.jetbrains.plugin.code.comment.model.comment.impl;

import com.indulgent.jetbrains.plugin.code.comment.model.comment.FileComments;
import com.indulgent.jetbrains.plugin.code.comment.model.group.GroupInfo;
import com.indulgent.jetbrains.plugin.code.comment.model.group.GroupInfoServiceFactory;
import com.indulgent.jetbrains.plugin.code.comment.model.user.UserInfo;
import com.indulgent.jetbrains.plugin.code.comment.model.user.UserInfoServiceFactory;
import com.intellij.openapi.project.Project;
import org.jdom.Element;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Deserializer of comments data for data source version equals 0
 *
 * @author Indulgent
 *         08.06.2016.
 */
class CommentsXMLDeserializerV0 implements CommentsDataBuilder {
	private static final String COMMENTS_TAG_NAME = "comments";
	private static final String COMMENT_TAG_NAME = "comment";
	private static final String PLACE_TAG_NAME = "place";
	private static final String PATH_ATTRIBUTE_NAME = "path";
	private static final String FROM_ATTRIBUTE_NAME = "from";
	private static final String TO_ATTRIBUTE_NAME = "to";
	private static final String TEXT_TAG_NAME = "text";
	private static final String DATA_TAG_NAME = "data";

	private final Project project;
	private final Element state;

	/**
	 * Constructor
	 *
	 * @param project current project
	 * @param state   root xml element
	 */
	public CommentsXMLDeserializerV0(Project project, Element state) {
		this.project = project;
		this.state = state;
	}

	@Override
	public Collection<FileComments> build() {
		GroupInfo groupInfo = GroupInfoServiceFactory.getService(project).getAll().iterator().next();
		UserInfo userInfo = UserInfoServiceFactory.getService(project).getCurrentUser();
		Map<String, FileComments> result = new HashMap<>();
		Element commentsElement = state.getChild(COMMENTS_TAG_NAME);

		for (Element commentElement : commentsElement.getChildren(COMMENT_TAG_NAME)) {
			Element placeElement = commentElement.getChild(PLACE_TAG_NAME);
			String filePath = placeElement.getAttribute(PATH_ATTRIBUTE_NAME).getValue();

			FileComments fileComments = result.get(filePath);
			if (fileComments == null) {
				fileComments = new FileCommentsImpl(FileInformationBuilderCache.build(filePath));
				result.put(filePath, fileComments);
			}

			CodeInformationImpl codeInformation = new CodeInformationImpl();
			codeInformation.setCodeText(commentElement.getChild(TEXT_TAG_NAME).getText());
			codeInformation.setStart(Integer.valueOf(placeElement.getAttribute(FROM_ATTRIBUTE_NAME).getValue()));
			codeInformation.setEnd(Integer.valueOf(placeElement.getAttribute(TO_ATTRIBUTE_NAME).getValue()));

			CommentImpl comment = new CommentImpl(fileComments.getFileInformation(), codeInformation, groupInfo);
			comment.getCommentHistory().add(userInfo, commentElement.getChild(DATA_TAG_NAME).getText(), Calendar.getInstance());
			fileComments.save(comment);
		}
		return result.values();
	}
}
