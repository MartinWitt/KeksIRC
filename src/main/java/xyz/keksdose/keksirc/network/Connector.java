package xyz.keksdose.keksirc.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.net.ssl.SSLSocketFactory;

public class Connector {

    private boolean ssl;
    private Socket socket;

    private OutputStream out;
    private InputStream in;

    public static Connector connectorWithSSL() {
        return new Connector(true);
    }

    public static Connector connectorWithoutSSL() {
        return new Connector(false);
    }

    private Connector(boolean ssl) {
        this.ssl = ssl;
    }

    public void connect(String host, int port) throws IOException {
        if (ssl) {
            createSSLSocket(host, port);
        } else {
            createNonSSLSocket(host, port);
        }
        if (socket == null) {
            throw new IOException("cant connect SSL status: " + ssl);
        }
        this.out = socket.getOutputStream();
        this.in = socket.getInputStream();

    }

    private void createNonSSLSocket(String host, int port)
            throws UnknownHostException, IOException {
        socket = new Socket(host, port);
    }

    private void createSSLSocket(String host, int port) throws UnknownHostException, IOException {
        socket = SSLSocketFactory.getDefault().createSocket(host, port);
    }

    OutputStream getOut() {
        return this.out;
    }

    InputStream getIn() {
        return this.in;
    }

}
