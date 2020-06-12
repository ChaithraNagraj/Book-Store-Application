package com.bridgelabz.bookstore.utils;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import com.bridgelabz.bookstore.constants.Constant;

public class Template {

	private Template() {
	}

	public static String readContentFromTemplet() throws IOException {
		Path filePath = FileSystems.getDefault().getPath(Constant.PATH).normalize();
		byte[] fileBytes = Files.readAllBytes(filePath);
		return new String(fileBytes);
	}
}