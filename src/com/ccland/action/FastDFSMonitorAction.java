package com.ccland.action;

import java.net.URL;
import java.text.SimpleDateFormat;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.ProtoCommon;
import org.csource.fastdfs.StructGroupStat;
import org.csource.fastdfs.StructStorageStat;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;

public class FastDFSMonitorAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5878995368899335410L;

	private static Logger log = LoggerFactory
			.getLogger(FastDFSMonitorAction.class);

	private String mointorInfo;

	public String getMointorInfo() {
		return mointorInfo;
	}

	public void setMointorInfo(String mointorInfo) {
		this.mointorInfo = mointorInfo;
	}

	public String getMonitorInfo() {
		StringBuilder sb = new StringBuilder("");

		try {

			URL url = Thread.currentThread().getContextClassLoader()
					.getResource("fdfs_client.conf");

			ClientGlobal.init(url.getPath());
			sb.append("<h3>客户端连接参数配置</h3>");
			sb.append("tracker_group=" + ClientGlobal.g_tracker_group);
			sb.append("<br>tracker_http_port="
					+ ClientGlobal.g_tracker_http_port);
			sb.append("<br>g_connect_timeout=" + ClientGlobal.g_connect_timeout
					+ "ms");
			sb.append("<br>network_timeout=" + ClientGlobal.g_network_timeout
					+ "ms");
			sb.append("<br>charset=" + ClientGlobal.g_charset);

			TrackerClient tracker = new TrackerClient();

			TrackerServer trackerServer = tracker.getConnection();
			if (trackerServer == null) {
				return sb.toString();
			}

			int count;
			StructGroupStat[] groupStats = tracker.listGroups(trackerServer);
			if (groupStats == null) {
				sb.append("");
				sb.append("<br>ERROR! list groups error, error no: "
						+ tracker.getErrorCode());
				sb.append("");
				return sb.toString();
			}
			sb.append("<br><h3>服务分组信息</h3>");
			sb.append("group count: " + groupStats.length);
			count = 0;
			for (StructGroupStat groupStat : groupStats) {
				count++;
				sb.append("<br>Group " + count + ":");
				sb.append("<br>group name = " + groupStat.getGroupName());
				sb.append("<br>disk free space = "
						+ (groupStat.getFreeMB() / 1024) + " GB");
				sb.append("<br>trunk free space = "
						+ (groupStat.getTrunkFreeMB() / 1024) + " GB");
				sb.append("<br>storage server count = "
						+ groupStat.getStorageCount());
				sb.append("<br>active server count = "
						+ groupStat.getActiveCount());
				sb.append("<br>storage server port = "
						+ groupStat.getStoragePort());
				sb.append("<br>storage HTTP port = "
						+ groupStat.getStorageHttpPort());
				sb.append("<br>store path count = "
						+ groupStat.getStorePathCount());
				sb.append("<br>subdir count per path = "
						+ groupStat.getSubdirCountPerPath());
				sb.append("<br>current write server index = "
						+ groupStat.getCurrentWriteServer());
				sb.append("<br>current trunk file id = "
						+ groupStat.getCurrentTrunkFileId());

				StructStorageStat[] storageStats = tracker.listStorages(
						trackerServer, groupStat.getGroupName());
				if (storageStats == null) {
					sb.append("");
					sb.append("<br>ERROR! list storage error, error no: "
							+ tracker.getErrorCode());
					sb.append("");
					break;
				}

				SimpleDateFormat df = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				int stroageCount = 0;
				for (StructStorageStat storageStat : storageStats) {
					stroageCount++;
					sb.append("<br>Storage " + stroageCount + ":");
					sb.append("<br>ip_addr = "
							+ storageStat.getIpAddr()
							+ "  "
							+ ProtoCommon.getStorageStatusCaption(storageStat
									.getStatus()));
					sb.append("<br>http domain = "
							+ storageStat.getDomainName());
					sb.append("<br>version = " + storageStat.getVersion());
					sb.append("<br>join time = "
							+ df.format(storageStat.getJoinTime()));
					sb.append("<br>up time = "
							+ (storageStat.getUpTime().getTime() == 0 ? "" : df
									.format(storageStat.getUpTime())));
					sb.append("<br>total storage = "
							+ (storageStat.getTotalMB() / 1024) + "GB");
					sb.append("<br>free storage = "
							+ (storageStat.getFreeMB() / 1024) + "GB");
					sb.append("<br>upload priority = "
							+ storageStat.getUploadPriority());
					sb.append("<br>store_path_count = "
							+ storageStat.getStorePathCount());
					sb.append("<br>subdir_count_per_path = "
							+ storageStat.getSubdirCountPerPath());
					sb.append("<br>storage_port = "
							+ storageStat.getStoragePort());
					sb.append("<br>storage_http_port = "
							+ storageStat.getStorageHttpPort());
					sb.append("<br>current_write_path = "
							+ storageStat.getCurrentWritePath());
					sb.append("<br>source ip_addr = "
							+ storageStat.getSrcIpAddr());
					sb.append("<br>if_trunk_server = "
							+ storageStat.isTrunkServer());
					sb.append("<br>total_upload_count = "
							+ storageStat.getTotalUploadCount());
					sb.append("<br>success_upload_count = "
							+ storageStat.getSuccessUploadCount());
					sb.append("<br>total_append_count = "
							+ storageStat.getTotalAppendCount());
					sb.append("<br>success_append_count = "
							+ storageStat.getSuccessAppendCount());
					sb.append("<br>total_modify_count = "
							+ storageStat.getTotalModifyCount());
					sb.append("<br>success_modify_count = "
							+ storageStat.getSuccessModifyCount());
					sb.append("<br>total_truncate_count = "
							+ storageStat.getTotalTruncateCount());
					sb.append("<br>success_truncate_count = "
							+ storageStat.getSuccessTruncateCount());
					sb.append("<br>total_set_meta_count = "
							+ storageStat.getTotalSetMetaCount());
					sb.append("<br>success_set_meta_count = "
							+ storageStat.getSuccessSetMetaCount());
					sb.append("<br>total_delete_count = "
							+ storageStat.getTotalDeleteCount());
					sb.append("<br>success_delete_count = "
							+ storageStat.getSuccessDeleteCount());
					sb.append("<br>total_download_count = "
							+ storageStat.getTotalDownloadCount());
					sb.append("<br>success_download_count = "
							+ storageStat.getSuccessDownloadCount());
					sb.append("<br>total_get_meta_count = "
							+ storageStat.getTotalGetMetaCount());
					sb.append("<br>success_get_meta_count = "
							+ storageStat.getSuccessGetMetaCount());
					sb.append("<br>total_create_link_count = "
							+ storageStat.getTotalCreateLinkCount());
					sb.append("<br>success_create_link_count = "
							+ storageStat.getSuccessCreateLinkCount());
					sb.append("<br>total_delete_link_count = "
							+ storageStat.getTotalDeleteLinkCount());
					sb.append("<br>success_delete_link_count = "
							+ storageStat.getSuccessDeleteLinkCount());
					sb.append("<br>total_upload_bytes = "
							+ storageStat.getTotalUploadBytes());
					sb.append("<br>success_upload_bytes = "
							+ storageStat.getSuccessUploadBytes());
					sb.append("<br>total_append_bytes = "
							+ storageStat.getTotalAppendBytes());
					sb.append("<br>success_append_bytes = "
							+ storageStat.getSuccessAppendBytes());
					sb.append("<br>total_modify_bytes = "
							+ storageStat.getTotalModifyBytes());
					sb.append("<br>success_modify_bytes = "
							+ storageStat.getSuccessModifyBytes());
					sb.append("<br>total_download_bytes = "
							+ storageStat.getTotalDownloadloadBytes());
					sb.append("<br>success_download_bytes = "
							+ storageStat.getSuccessDownloadloadBytes());
					sb.append("<br>total_sync_in_bytes = "
							+ storageStat.getTotalSyncInBytes());
					sb.append("<br>success_sync_in_bytes = "
							+ storageStat.getSuccessSyncInBytes());
					sb.append("<br>total_sync_out_bytes = "
							+ storageStat.getTotalSyncOutBytes());
					sb.append("<br>success_sync_out_bytes = "
							+ storageStat.getSuccessSyncOutBytes());
					sb.append("<br>total_file_open_count = "
							+ storageStat.getTotalFileOpenCount());
					sb.append("<br>success_file_open_count = "
							+ storageStat.getSuccessFileOpenCount());
					sb.append("<br>total_file_read_count = "
							+ storageStat.getTotalFileReadCount());
					sb.append("<br>success_file_read_count = "
							+ storageStat.getSuccessFileReadCount());
					sb.append("<br>total_file_write_count = "
							+ storageStat.getTotalFileWriteCount());
					sb.append("<br>success_file_write_count = "
							+ storageStat.getSuccessFileWriteCount());
					sb.append("<br>last_heart_beat_time = "
							+ df.format(storageStat.getLastHeartBeatTime()));
					sb.append("<br>last_source_update = "
							+ df.format(storageStat.getLastSourceUpdate()));
					sb.append("<br>last_sync_update = "
							+ df.format(storageStat.getLastSyncUpdate()));
					sb.append("<br>last_synced_timestamp = "
							+ df.format(storageStat.getLastSyncedTimestamp())
							+ getSyncedDelayString(storageStats, storageStat));
				}
			}

			trackerServer.close();
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
		return sb.toString();
	}

	protected String getSyncedDelayString(StructStorageStat[] storageStats,
			StructStorageStat currentStorageStat) {
		long maxLastSourceUpdate = 0;
		for (StructStorageStat storageStat : storageStats) {
			if (storageStat != currentStorageStat
					&& storageStat.getLastSourceUpdate().getTime() > maxLastSourceUpdate) {
				maxLastSourceUpdate = storageStat.getLastSourceUpdate()
						.getTime();
			}
		}

		if (maxLastSourceUpdate == 0) {
			return "";
		}

		if (currentStorageStat.getLastSyncedTimestamp().getTime() == 0) {
			return " (never synced)";
		}

		int delaySeconds = (int) ((maxLastSourceUpdate - currentStorageStat
				.getLastSyncedTimestamp().getTime()) / 1000);
		int day = delaySeconds / (24 * 3600);
		int remainSeconds = delaySeconds % (24 * 3600);
		int hour = remainSeconds / 3600;
		remainSeconds %= 3600;
		int minute = remainSeconds / 60;
		int second = remainSeconds % 60;
		String delayTimeStr;
		if (day != 0) {
			delayTimeStr = String.format("%1$d days %2$02dh:%3$02dm:%4$02ds",
					day, hour, minute, second);
		} else if (hour != 0) {
			delayTimeStr = String.format("%1$02dh:%2$02dm:%3$02ds", hour,
					minute, second);
		} else if (minute != 0) {
			delayTimeStr = String.format("%1$02dm:%2$02ds", minute, second);
		} else {
			delayTimeStr = String.format("%1$ds", second);
		}

		return " (" + delayTimeStr + " delay)";
	}

	@Override
	public String execute() throws Exception {
		this.mointorInfo = this.getMointorInfo();
		return SUCCESS;
	}

}
