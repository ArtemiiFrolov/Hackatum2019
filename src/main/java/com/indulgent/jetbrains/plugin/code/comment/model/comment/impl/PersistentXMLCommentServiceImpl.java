package com.indulgent.jetbrains.plugin.code.comment.model.comment.impl;

import com.indulgent.jetbrains.plugin.code.comment.logging.Log;
import com.indulgent.jetbrains.plugin.code.comment.model.comment.*;
import com.indulgent.jetbrains.plugin.code.comment.model.group.GroupInfo;
import com.indulgent.jetbrains.plugin.code.comment.model.user.UserInfo;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import org.jdom.Attribute;
import org.jdom.DataConversionException;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;

/**
 * Service for work with comments
 * Save state in project settings file
 *
 * @author Indulgent
 *         07.06.2016.
 */
@State(
		name = "CodeComments", storages = {
		@Storage(
				file = "code-comments.xml")
})
class PersistentXMLCommentServiceImpl extends CommentServiceImpl implements PersistentStateComponent<Element> {
	private static final String CURRENT_VERSION = "1";

	private static final String ROOT_TAG_NAME = "root";
	private static final String VERSION_ATTRIBUTE_NAME = "version";

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

	/**
	 * Constructor
	 *
	 * @param project current project
	 */
	PersistentXMLCommentServiceImpl(@NotNull Project project) {
		super(project);
	}

	@Nullable
	@Override
	public Element getState() {
		Element root = getElement(ROOT_TAG_NAME);
		root.setAttribute(VERSION_ATTRIBUTE_NAME, CURRENT_VERSION);
		addFileCommentsElement(root, COMMENTS_TAG_NAME, getAll());
		return root;
	}

	private boolean addElement(@NotNull Element destination, @NotNull String name, @NotNull ElementBuilder elementBuilder) {
		Element element = elementBuilder.build(getElement(name));
		if (element == null) {
			return false;
		}
		destination.addContent(element);
		return true;
	}

	private boolean addFileCommentsElement(@NotNull Element destination, @NotNull String name, @NotNull Collection<FileComments> fileCommentsCollection) {
		return addElement(destination, name, (element) -> {
			for (FileComments fileComments : fileCommentsCollection) {
				addElement(element, FILE_COMMENTS_TAG_NAME, fileComments);
			}
			return element;
		});
	}

	private boolean addElement(@NotNull Element destination, @NotNull String name, @NotNull FileComments fileComments) {
		return addElement(destination, name, (element) -> {
			addElement(element, FILE_INFORMATION_TAG_NAME, fileComments.getFileInformation());
			addElement(element, COMMENTS_TAG_NAME, fileComments.getComments());
			return element;
		});
	}

	private boolean addElement(@NotNull Element destination, @NotNull String name, @NotNull FileInformation fileInformation) {
		return addElement(destination, name, (element) -> {
			addElement(element, FILE_PATH_TAG_NAME, fileInformation.getPath());
			return element;
		});
	}

	private boolean addElement(@NotNull Element destination, @NotNull String name, @NotNull Collection<Comment> comments) {
		return addElement(destination, name, (element) -> {
			for (Comment comment : comments) {
				addElement(element, COMMENT_TAG_NAME, comment);
			}
			return element;
		});
	}

	private boolean addElement(@NotNull Element destination, @NotNull String name, @NotNull Comment comment) {
		return addElement(destination, name, (element) -> {
			addElement(element, CODE_INFORMATION_TAG_NAME, comment.getCodeInformation());
			addElement(element, GROUP_INFO_TAG_NAME, comment.getGroupInfo());
			addElement(element, COMMENT_HISTORY_TAG_NAME, comment.getCommentHistory());
			return element;
		});
	}

	private boolean addElement(@NotNull Element destination, @NotNull String name, @NotNull CodeInformation codeInformation) {
		return addElement(destination, name, (element) -> {
			addElement(element, CODE_TEXT_TAG_NAME, codeInformation.getCodeText());
			addElement(element, CODE_START_TAG_NAME, codeInformation.getStart());
			addElement(element, CODE_END_TAG_NAME, codeInformation.getEnd());
			addElement(element, CODE_LINE_TAG_NAME, codeInformation.getLine());
			addElement(element, CODE_COLUMN_TAG_NAME, codeInformation.getColumn());
			return element;
		});
	}

	private boolean addElement(@NotNull Element destination, @NotNull String name, @NotNull GroupInfo groupInfo) {
		return addElement(destination, name, (element) -> {
			return element;
		});
	}

	private boolean addElement(@NotNull Element destination, @NotNull String name, @NotNull CommentHistory commentHistory) {
		return addElement(destination, name, (element) -> {
			for (CommentHistoryRecord historyRecord : commentHistory.getRecords()) {
				addElement(element, COMMENT_RECORD_TAG_NAME, historyRecord);
			}
			return element;
		});
	}

	private boolean addElement(@NotNull Element destination, @NotNull String name, @NotNull CommentHistoryRecord commentHistoryRecord) {
		return addElement(destination, name, (element) -> {
			addElement(element, COMMENT_DATE_TAG_NAME, commentHistoryRecord.getDate());
			addElement(element, COMMENT_TEXT_TAG_NAME, commentHistoryRecord.getText());
			addElement(element, USER_INFO_TAG_NAME, commentHistoryRecord.getUserInfo());
			return element;
		});
	}

	private boolean addElement(@NotNull Element destination, @NotNull String name, @NotNull UserInfo userInfo) {
		return addElement(destination, name, (element) -> {
			return element;
		});
	}

	@NotNull
	private Element getElement(@NotNull String name) {
		return new Element(name);
	}

	private boolean addElement(@NotNull Element destination, @NotNull String name, @NotNull String value) {
		return addElement(destination, name, (element) -> {
			element.setText(value);
			return element;
		});
	}

	private boolean addElement(@NotNull Element destination, @NotNull String name, int value) {
		return addElement(destination, name, String.valueOf(value));
	}

	private boolean addElement(@NotNull Element destination, @NotNull String name, @NotNull Calendar value) {
		return addElement(destination, name, new SimpleDateFormat(DATE_FORMAT).format(value.getTime()));
	}

	@Override
	public void loadState(Element state) {
		CommentsDataBuilder deserializer = getDeserializer(state);
		if (deserializer == null) {
			return;
		}
		setData(deserializer);
	}

	private CommentsDataBuilder getDeserializer(Element state) {
		Attribute versionAttribute = state.getAttribute(VERSION_ATTRIBUTE_NAME);
		try {
			int version = getIntAttributeValue(versionAttribute, 0);

			switch (version) {
				case 0:
					return new CommentsXMLDeserializerV0(getProject(), state);
				case 1:
					return new CommentsXMLDeserializerV1(getProject(), state);
			}
		} catch (DataConversionException ignore) {
		}

		Log.getInstance(getProject()).error("Version " + versionAttribute.getValue() + " of data source is not supported.");
		return null;
	}

	private int getIntAttributeValue(Attribute attribute, int defaultValue) throws DataConversionException {
		return attribute == null ? defaultValue : attribute.getIntValue();
	}

	private interface ElementBuilder {
		Element build(@NotNull Element target);
	}
}
