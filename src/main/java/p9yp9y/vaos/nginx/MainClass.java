package p9yp9y.vaos.nginx;

import nginx.clojure.embed.NginxEmbedServer;

public class MainClass {

	public static void main(String[] args) {
		//Start it with ring handler and an options map
	//	NginxEmbedServer.getServer().start("my.HelloHandler", ArrayMap.create("port", "8081"));


		//Start it with with a nginx.conf file
		NginxEmbedServer.getServer().start("/home/andris/workspace3/vaos/src/main/java/p9yp9y/vaos/nginx/conf/nginx.conf");

		//Stop the server
	//	NginxEmbedServer.getServer().stop();
	}

}
