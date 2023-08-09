package com.example.demo01;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;

public class demoProxy {
    public static void main(String[] args) throws IOException {
        // Set the http proxy to webcache.mydomain.com:8080
//Set the http proxy to webcache.mydomain.com:8080

        URL weburl = new URL("http://java.sun.com/");
        Proxy webProxy
                = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 3128));
        HttpURLConnection webProxyConnection
                = (HttpURLConnection) weburl.openConnection(webProxy);

    }
}
