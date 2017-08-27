package com.rest.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
@Configuration
public class CommonProperties {

	@Value("${upload-file-max-size}")
	private int publishedReportMaxSize;

	@Value("${dest-file-path}")
	private String destFilePath;
	
	/**
	 * @return the publishedReportMaxSize
	 */
	public int getPublishedReportMaxSize() {
		return publishedReportMaxSize;
	}

	/**
	 * @param publishedReportMaxSize the publishedReportMaxSize to set
	 */
	public void setPublishedReportMaxSize(int publishedReportMaxSize) {
		this.publishedReportMaxSize = publishedReportMaxSize;
	}

	/**
	 * @return the destFilePath
	 */
	public String getDestFilePath() {
		return destFilePath;
	}

	/**
	 * @param destFilePath the destFilePath to set
	 */
	public void setDestFilePath(String destFilePath) {
		this.destFilePath = destFilePath;
	}
	
	
}
