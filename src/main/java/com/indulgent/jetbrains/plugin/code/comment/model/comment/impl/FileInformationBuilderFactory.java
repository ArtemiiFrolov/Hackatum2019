package com.indulgent.jetbrains.plugin.code.comment.model.comment.impl;

/**
 * Builder of file information factory
 *
 * @author Indulgent
 *         06.06.2016.
 */
final class FileInformationBuilderFactory {
	private static final FileInformationBuilderFactory INSTANCE = new FileInformationBuilderFactory();
	private final FileInformationBuilderCache builder = new FileInformationBuilderCache(new FileInformationBuilderImpl());

	private FileInformationBuilderFactory() {
	}

	public static FileInformationBuilderFactory getInstance() {
		return INSTANCE;
	}

	/**
	 * Get builder of file information
	 *
	 * @return builder
	 */
	FileInformationBuilder getBuilder() {
		return builder;
	}
}
