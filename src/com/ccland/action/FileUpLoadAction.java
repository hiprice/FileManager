package com.ccland.action;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;

public class FileUpLoadAction extends ActionSupport {

	private static final long serialVersionUID = -1232100382633301276L;

	private static final Logger log = LoggerFactory
			.getLogger(FileUpLoadAction.class);

	// 上传多个文件的集合文本
	private List<File> upload;

	// 多个上传文件的类型集合
	private List<String> uploadContextType;

	// 多个上传文件的文件名集合
	private List<String> uploadFileName;

	// 文件存放路径根目录
	private String fileUrl;

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public List<File> getUpload() {
		return upload;
	}

	public void setUpload(List<File> upload) {
		this.upload = upload;
	}

	public List<String> getUploadContextType() {
		return uploadContextType;
	}

	public void setUploadContextType(List<String> uploadContextType) {
		this.uploadContextType = uploadContextType;
	}

	public List<String> getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(List<String> uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	@Override
	public String execute() throws Exception {

		try {

			if (upload != null) {
				// 把得到的文件的集合通过循环的方式读取并放在指定的路径下
				for (int i = 0; i < upload.size(); i++) {
					try {

						String oldFileName = uploadFileName.get(i);// 上传文件原名
						String extName = "";
						// 获取拓展名
						if (oldFileName.lastIndexOf(".") >= 0) {
							extName = oldFileName.substring(oldFileName
									.lastIndexOf(".") + 1);
						}

						fileUrl = this.uploadFileToDFS(oldFileName, extName,
								upload.get(i).getPath());

					} catch (IOException e) {
						log.error(e.getMessage(), e);
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException();
		}

		return SUCCESS;
	}

	public String uploadFileToDFS(String uploadFileName, String fileExtName,
			String tmpFileName) throws IOException {

		String fileId = "";
		try {
			URL url = Thread.currentThread().getContextClassLoader()
					.getResource("fdfs_client.conf");

			ClientGlobal.init(url.getPath());

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new RuntimeException();
		}
		TrackerClient tracker = new TrackerClient();
		TrackerServer trackerServer = tracker.getConnection();
		StorageServer storageServer = null;
		StorageClient1 client = new StorageClient1(trackerServer, storageServer);

		NameValuePair[] metaList = new NameValuePair[2];
		metaList[0] = new NameValuePair("fileName", uploadFileName);
		metaList[1] = new NameValuePair("fileExtName", fileExtName);

		try {
			fileId = client.upload_file1(tmpFileName, fileExtName, metaList);
		} catch (Exception e) {
			log.error("Upload file \"" + uploadFileName + "\"fails");
		}

		trackerServer.close();

		return fileId;
	}

}
