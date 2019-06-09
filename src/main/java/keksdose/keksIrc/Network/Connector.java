package keksdose.keksIrc.Network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

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
        this.out = socket.getOutputStream();
        this.in = socket.getInputStream();

    }

    private void createNonSSLSocket(String host, int port) {
        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void createSSLSocket(String host, int port) {
        try {
            socket = SSLSocketFactory.getDefault().createSocket(host, port);
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    public OutputStream getOut() {
        return this.out;
    }

    public InputStream getIn() {
        return this.in;
    }

}
