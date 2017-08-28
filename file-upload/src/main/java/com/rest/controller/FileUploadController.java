package com.rest.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rest.bean.UserBean;
import com.rest.constants.CommonConstants;
import com.rest.properties.CommonProperties;
import com.rest.service.FileService;


@RestController
@RequestMapping("file")
public class FileUploadController {
	private static final Logger logger = LoggerFactory
			.getLogger(FileUploadController.class);
	
	@Autowired
	CommonProperties commonProperties;
	
	@Autowired
	FileService fileService;
	
	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	public ResponseEntity<String> fileUpload(@RequestPart("file") MultipartFile file,HttpServletResponse response) {
		logger.debug("FileUploadController - fileUpload - START");
		String msg = null;
		if(file.getSize() > ( commonProperties.getPublishedReportMaxSize() * 1024 *1024 )){
				try {
					response.sendError(700);
				} catch (IOException e) {
					logger.error("FileUploadController - fileUpload - Exception", e);
				}
				return new ResponseEntity<String>(CommonConstants.FAIL_MSG,HttpStatus.BAD_REQUEST);
		}
		Path path = Paths.get(commonProperties.getDestFilePath());
		//if directory exists?
		 if (!Files.exists(path)) {
	            try {
	                Files.createDirectories(path);
	            } catch (IOException e) {
	                //fail to create directory
	                e.printStackTrace();
	            }
	        }
		String name = commonProperties.getDestFilePath()+ File.separator + file.getOriginalFilename();
		File destFile = new File(name);
		try {
			file.transferTo(destFile);
			fileService.saveFile(file.getOriginalFilename(), file.getOriginalFilename(), destFile);
			msg = CommonConstants.SUCCESS_MSG;
		} catch (IllegalStateException e) {
			try {
				response.sendError(600);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				logger.error("FileUploadController - fileUpload - Exception", e1);
				msg = CommonConstants.FAIL_MSG;
			}
			logger.error("FileUploadController - fileUpload - Exception", e);
		} catch (IOException e) {
			try {
					response.sendError(600);
					msg = CommonConstants.FAIL_MSG;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					logger.error("FileUploadController - fileUpload - Exception", e1);
				}
				logger.error("FileUploadController - fileUpload - Exception", e);
			}
			logger.debug("FileUploadController - fileUpload - END");
			return new ResponseEntity<String>(msg,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "saveUser", method = RequestMethod.POST)
	public ResponseEntity<Boolean> saveUser(@RequestBody UserBean userBean){
		logger.info(this.getClass().getName() + " - saveTicket - START");
		fileService.saveUser(userBean);
		logger.info(this.getClass().getName() + " - saveTicket - END");
		return new ResponseEntity<Boolean>(true,HttpStatus.OK);
	}
}
