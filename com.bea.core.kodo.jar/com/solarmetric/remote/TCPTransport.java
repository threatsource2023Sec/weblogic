package com.solarmetric.remote;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPTransport implements Transport {
   private static final byte STATUS_OK = 0;
   private static final byte STATUS_CLOSED = 1;
   private String _host = "localhost";
   private int _port = 5637;
   private int _timeout = 0;

   public int getPort() {
      return this._port;
   }

   public void setPort(int port) {
      this._port = port;
   }

   public String getHost() {
      return this._host;
   }

   public void setHost(String host) {
      this._host = host;
   }

   public int getSoTimeout() {
      return this._timeout;
   }

   public void setSoTimeout(int timeout) {
      this._timeout = timeout;
   }

   public Transport.Server getServer() throws Exception {
      return new TCPServer();
   }

   public Transport.Channel getClientChannel() throws Exception {
      return new SocketChannel(new Socket(this._host, this._port), false);
   }

   public void close() {
   }

   private static class NoCloseOutputStream extends DelegatingOutputStream {
      public NoCloseOutputStream(OutputStream out) {
         super(out);
      }

      public void close() {
      }
   }

   private static class NoCloseInputStream extends DelegatingInputStream {
      public NoCloseInputStream(InputStream in) {
         super(in);
      }

      public void close() {
      }
   }

   private class SocketChannel implements Transport.Channel {
      private final Socket _socket;
      private final NoCloseInputStream _in;
      private final NoCloseOutputStream _out;
      private final boolean _server;
      private boolean _closed = false;

      public SocketChannel(Socket socket, boolean server) throws Exception {
         this._socket = socket;
         if (TCPTransport.this._timeout != 0) {
            this._socket.setSoTimeout(TCPTransport.this._timeout);
         }

         this._server = server;
         this._in = new NoCloseInputStream(socket.getInputStream());
         this._out = new NoCloseOutputStream(socket.getOutputStream());
      }

      public OutputStream getOutput() throws Exception {
         if (this._closed) {
            return null;
         } else {
            if (!this._server) {
               this._out.write(0);
            }

            return this._out;
         }
      }

      public InputStream getInput() throws Exception {
         if (this._server && !this._closed) {
            int b = this._in.read();
            if (b == 1) {
               this.close();
            }
         }

         return this._closed ? null : this._in;
      }

      public void error(IOException ioe) {
      }

      public void close() throws Exception {
         if (!this._closed) {
            this._closed = true;
            if (!this._server) {
               this._out.write(1);
               this._out.flush();
            }

            this._in.getDelegate().close();
            this._out.getDelegate().close();
            this._socket.close();
         }
      }
   }

   private class TCPServer implements Transport.Server {
      private final ServerSocket _serverSocket;
      private boolean _closed = false;

      public TCPServer() throws IOException {
         this._serverSocket = new ServerSocket(TCPTransport.this._port);
      }

      public Transport.Channel accept() throws Exception {
         return TCPTransport.this.new SocketChannel(this._serverSocket.accept(), true);
      }

      public boolean isClosed() {
         return this._closed;
      }

      public void close() throws Exception {
         this._closed = true;
         this._serverSocket.close();
      }
   }
}
