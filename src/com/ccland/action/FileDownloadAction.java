package com.ccland.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;

public class FileDownloadAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2708157610584574779L;

	private static final Logger log = LoggerFactory
			.getLogger(FileDownloadAction.class);

	private InputStream downloadStream;

	// 下载文件存放根目录 绝对路径 通过action配置参数注入获得
	private String downloadDir;

	// 文件id
	private String fileId;

	private String fileName;

	private String fileFullPath;

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		try {
			return URLEncoder.encode(fileName, "UTF-8");// 解决下载文件名，中文乱码问题
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(), e);
		}
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getDownloadStream() {
		// 返回文件流
		return this.downloadStream;
	}

	public void setDownloadDir(String downloadDir) {
		this.downloadDir = downloadDir;
	}

	@Override
	public String execute() throws Exception {

		this.downloadFileFromDFS();
		if (!this.fileName.trim().equals("")) {
			File file = new File(this.fileFullPath);
			// 获取文件流
			this.downloadStream = new FileInputStream(file);
		}
		return SUCCESS;
	}

	public void downloadFileFromDFS() throws IOException {
		try {
			URL url = Thread.currentThread().getContextClassLoader()
					.getResource("fdfs_client.conf");

			ClientGlobal.init(url.getPath());

		} catch (Exception e1) {
			log.error(e1.getMessage(), e1);
			throw new RuntimeException();
		}
		TrackerClient tracker = new TrackerClient();
		TrackerServer trackerServer = tracker.getConnection();
		StorageServer storageServer = null;
		StorageClient1 client = new StorageClient1(trackerServer, storageServer);

		try {
			NameValuePair[] metaList = client.get_metadata1(this.fileId);

			for (int i = 0; i < metaList.length; i++) {
				if (metaList[i].getName().toLowerCase().equals("filename")) {
					fileName = metaList[i].getValue();
					break;
				}
			}

			this.fileFullPath = this.downloadDir + "\\" + this.fileName;

			int i = client.download_file1(this.fileId, fileFullPath);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}

		trackerServer.close();
	}
}
