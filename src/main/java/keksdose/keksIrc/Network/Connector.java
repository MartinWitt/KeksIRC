package keksdose.keksIrc.Network;
// TODO für SSL and non SSL

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class Connector {

    private boolean ssl;
    private Socket socket;

    private OutputStream out;
    private InputStream in;

    public Connector(boolean ssl) {
        this.ssl = ssl;
    }

    public void connect(String host, int port) throws IOException {
        if (ssl) {
            createSSLSocket(host, port);
        } else {
            createNonSSLSocket(host, port);
        }
        if (socket == null) {
            throw new IOException("cant connect SSL status:" + ssl);
        }
    }

    private void createNonSSLSocket(String host, int port) {
        try {
            socket = new Socket(host, port);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    private void createSSLSocket(String host, int port) {
        try {
            SocketFactory sslsocketfactory = SSLSocketFactory.getDefault();
            socket = (SSLSocket) sslsocketfactory.createSocket(host, port);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();

        }

    }

}
