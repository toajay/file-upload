package com.rest.repository.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;

import com.rest.model.User;
import com.rest.repository.FileRepository;

@Repository
public class FileRepositoryImpl implements FileRepository{

	private static final Logger logger = LoggerFactory
			.getLogger(FileRepositoryImpl.class);
	
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public FileRepositoryImpl(JdbcTemplate jdbc) {
		jdbcTemplate = jdbc;
	}
	
	
	@Override
	public void saveUser(User user) {
		List<Object> params = new ArrayList<Object>();
		String insertUserQuery = "INSERT INTO USERS(USER_NAME, PASSWORD) values (?,?)";
		try {
			params.add(user.getUserName());
			params.add(user.getPassword());
			int result = jdbcTemplate.update(insertUserQuery, params.toArray());
			System.out.println("Inserted Result:"+result);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}


	@Override
	public void saveFile(String fileId, String fileName, File destFile) {
		try {
			final InputStream files = new FileInputStream(destFile);   
			LobHandler lobHandler = new DefaultLobHandler(); 
			jdbcTemplate.update(
			         "INSERT INTO file_store (file_id, file_name, file) VALUES (?, ?, ?)",
			         new Object[] {fileId, fileName, new SqlLobValue(files, (int)destFile.length(), lobHandler),
			         },
			         new int[] {Types.VARCHAR,Types.VARCHAR, Types.BLOB});   
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
