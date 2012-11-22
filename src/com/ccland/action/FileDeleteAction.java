package com.ccland.action;

import java.net.URL;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;

public class FileDeleteAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8451746139125744088L;

	private static Logger log = LoggerFactory.getLogger(FileDeleteAction.class);

	// 文件id
	private String fileId;

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String deleteFile() throws Exception {

		try {
			URL url = Thread.currentThread().getContextClassLoader()
					.getResource("fdfs_client.conf");

			ClientGlobal.init(url.getPath());

		} catch (Exception e1) {
			log.error(e1.getMessage(), e1);
		}
		TrackerClient tracker = new TrackerClient();
		TrackerServer trackerServer = tracker.getConnection();
		StorageServer storageServer = null;
		StorageClient1 client = new StorageClient1(trackerServer, storageServer);

		try {

			int i = client.delete_file1(fileId);
			if (i == 0) {
				msg = "文件删除成功！";
			} else {
				msg = "删除文件失败！";
			}
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);

		}

		trackerServer.close();

		return "deleteFile";
	}

}
