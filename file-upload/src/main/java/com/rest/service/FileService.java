package com.rest.service;

import java.io.File;

import com.rest.bean.UserBean;

public interface FileService {
	void saveUser(UserBean useBean);
	void saveFile(String fileId, String fileName, File file);
}
