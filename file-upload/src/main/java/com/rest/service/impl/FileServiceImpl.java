package com.rest.service.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rest.bean.UserBean;
import com.rest.model.User;
import com.rest.repository.FileRepository;
import com.rest.service.FileService;

@Component
public class FileServiceImpl implements FileService{

	@Autowired
	FileRepository fileRepository;
	
	
	@Override
	public void saveUser(UserBean userBean) {
		User user =  new User();
		user.setUserName(userBean.getUserName());
		user.setPassword(userBean.getPassword());
		fileRepository.saveUser(user);
	}


	@Override
	public void saveFile(String fileId, String fileName, File destfile) {
		fileRepository.saveFile(fileId, fileName, destfile);
		
	}
	
}
