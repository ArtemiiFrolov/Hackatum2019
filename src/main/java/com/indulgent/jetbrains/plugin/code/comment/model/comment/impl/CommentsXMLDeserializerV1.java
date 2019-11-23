package com.indulgent.jetbrains.plugin.code.comment.model.comment.impl;

import com.indulgent.jetbrains.plugin.code.comment.logging.Log;
import com.indulgent.jetbrains.plugin.code.comment.model.comment.*;
import com.indulgent.jetbrains.plugin.code.comment.model.group.GroupInfo;
import com.indulgent.jetbrains.plugin.code.comment.model.group.GroupInfoServiceFactory;
import com.indulgent.jetbrains.plugin.code.comment.model.user.UserInfo;
import com.indulgent.jetbrains.plugin.code.comment.model.user.UserInfoServiceFactory;
import com.intellij.openapi.project.Project;
import org.jdom.Element;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

/**
 * Deserializer of comments data for data source version equals 1
 *
 * @author Indulgent
 *         08.06.2016.
 */
class CommentsXMLDeserializerV1 implements CommentsDataBuilder {
	private static final String COMMENTS_TAG_NAME = "comments";

	private static final String FILE_COMMENTS_TAG_NAME = "fileComments";

	private static final String FILE_INFORMATION_TAG_NAME = "fileInformation";
	private static final String FILE_PATH_TAG_NAME = "path";

	private static final String COMMENT_TAG_NAME = "comment";

	private static final String CODE_INFORMATION_TAG_NAME = "codeInformation";
	private static final String CODE_TEXT_TAG_NAME = "codeText";
	private static final String CODE_START_TAG_NAME = "start";
	private static final String CODE_END_TAG_NAME = "end";
	private static final String CODE_LINE_TAG_NAME = "line";
	private static final String CODE_COLUMN_TAG_NAME = "column";

	private static final String GROUP_INFO_TAG_NAME = "groupInfo";

	private static final String COMMENT_HISTORY_TAG_NAME = "commentHistory";
	private static final String COMMENT_RECORD_TAG_NAME = "record";
	private static final String COMMENT_DATE_TAG_NAME = "date";
	private static final String COMMENT_TEXT_TAG_NAME = "text";

	private static final String USER_INFO_TAG_NAME = "userInfo";

	private static final String DATE_FORMAT = "yyyy.MM.dd HH:mm:ss XXX";

	private final Project project;
	private final Element state;

	/**
	 * Constructor
	 *
	 * @param project current project
	 * @param state   root xml element
	 */
	public CommentsXMLDeserializerV1(Project project, Element state) {
		this.project = project;
		this.state = state;
	}

	@Override
	public Collection<FileComments> build() {
		List<FileComments> result = new ArrayList<>();
		Element commentsElement = state.getChild(COMMENTS_TAG_NAME);

		for (Element fileCommentsElement : commentsElement.getChildren(FILE_COMMENTS_TAG_NAME)) {
			result.add(parsFileComments(fileCommentsElement));
		}

		return result;
	}

	private FileComments parsFileComments(Element element) {
		FileInformation fileInformation = parseFileInformation(element.getChild(FILE_INFORMATION_TAG_NAME));
		FileCommentsImpl fileComments = new FileCommentsImpl(fileInformation);

		Element commentsElement = element.getChild(COMMENTS_TAG_NAME);
		for (Element commentElement : commentsElement.getChildren(COMMENT_TAG_NAME)) {
			Comment comment = parseComment(fileInformation, commentElement);
			fileComments.save(comment);
		}

		return fileComments;
	}

	private FileInformation parseFileInformation(Element element) {
		return FileInformationBuilderCache.build(getElementValue(element, FILE_PATH_TAG_NAME));
	}

	private Comment parseComment(FileInformation fileInformation, Element element) {
		CodeInformation codeInformation = parseCodeInformation(element.getChild(CODE_INFORMATION_TAG_NAME));
		GroupInfo groupInfo = parseGroupInfo(element.getChild(GROUP_INFO_TAG_NAME));
		Comment comment = new CommentImpl(fileInformation, codeInformation, groupInfo);
		addCommentHistoryInformation(comment.getCommentHistory(), element.getChild(COMMENT_HISTORY_TAG_NAME));
		return comment;
	}

	private CodeInformation parseCodeInformation(Element element) {
		CodeInformationImpl codeInformation = new CodeInformationImpl();
		codeInformation.setCodeText(getElementValue(element, CODE_TEXT_TAG_NAME));
		codeInformation.setStart(getElementIntValue(element, CODE_START_TAG_NAME));
		codeInformation.setEnd(getElementIntValue(element, CODE_END_TAG_NAME));
		codeInformation.setLine(getElementIntValue(element, CODE_LINE_TAG_NAME));
		codeInformation.setColumn(getElementIntValue(element, CODE_COLUMN_TAG_NAME));
		return codeInformation;
	}

	private GroupInfo parseGroupInfo(Element element) {
		return GroupInfoServiceFactory.getService(project).getAll().iterator().next();
	}

	private UserInfo parseUserInfo(Element element) {
		return UserInfoServiceFactory.getService(project).getCurrentUser();
	}

	private void addCommentHistoryInformation(CommentHistory destination, Element element) {
		for (Element historyRecordElement : element.getChildren(COMMENT_RECORD_TAG_NAME)) {
			destination.add(parseUserInfo(historyRecordElement.getChild(USER_INFO_TAG_NAME)), getElementValue(historyRecordElement, COMMENT_TEXT_TAG_NAME), getElementCalendarValue(historyRecordElement, COMMENT_DATE_TAG_NAME));
		}
	}

	private String getElementValue(Element root, String elementName) {
		return root.getChild(elementName).getText();
	}

	private int getElementIntValue(Element root, String elementName) {
		return Integer.valueOf(getElementValue(root, elementName));
	}

	private Calendar getElementCalendarValue(Element root, String elementName) {
		String elementValue = getElementValue(root, elementName);
		try {
			Calendar date = Calendar.getInstance();
			date.setTime(new SimpleDateFormat(DATE_FORMAT).parse(elementValue));
			return date;
		} catch (ParseException e) {
			Log.getInstance(project).error("Parse date exception (" + elementValue + ", expected format " + DATE_FORMAT + ").", e);
			return Calendar.getInstance();
		}
	}
}
