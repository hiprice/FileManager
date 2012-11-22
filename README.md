说明

1、本项目需要预先安装并配置好FastDFS文件系统。

2、代码是对FastDFS文件系统的简单存取访问。

3、本工程需要引用jar包：fastdfs_client_v1.20.jar

4、修改配置文件fdfs_client.conf以连接FastDFS服务器：

connect_timeout = 2
network_timeout = 30
charset = UTF-8
http.tracker_http_port = 80
http.anti_steal_token = false
http.secret_key = null
tracker_server=tracker1:22122
tracker_server=tracker2:22122


把tracker_server设为与你的实际相符服务器名或IP地址，及端口。

