package com.bridgelabz.bookstore.utils;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class Template {

	private Template() {
	}

	private static final String PATH = "C:\\Users\\admin\\Desktop\\Project Data\\Book-Store-Application\\src\\main\\resources\\templates\\Registration-template.html";

	public static String readContentFromTemplet() throws IOException {
		Path filePath = FileSystems.getDefault().getPath(PATH).normalize();
		byte[] fileBytes = Files.readAllBytes(filePath);
		return new String(fileBytes);
	}
}