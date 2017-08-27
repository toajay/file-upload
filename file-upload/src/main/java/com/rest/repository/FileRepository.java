package com.rest.repository;

import java.io.File;

import com.rest.model.User;

public interface FileRepository {
	void saveUser(User user);
	void saveFile(String fileId, String fileName, File file);
}
