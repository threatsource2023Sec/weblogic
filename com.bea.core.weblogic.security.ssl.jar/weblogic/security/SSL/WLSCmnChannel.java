package weblogic.security.SSL;

import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SocketChannel;
import java.security.InvalidParameterException;
import javax.net.ssl.SSLSocket;

public class WLSCmnChannel {
   boolean isNio;
   private SocketChannel sockChan;
   private WLSSSLNioSocket nioSock;
   private Socket OrgSock;

   public WLSCmnChannel(Socket sock) {
      if (sock == null) {
         throw new InvalidParameterException("The passed in SocketChannel instance is null");
      } else {
         if (sock instanceof WLSSSLNioSocket) {
            this.isNio = true;
            this.nioSock = (WLSSSLNioSocket)sock;
         } else if (sock instanceof SSLSocket) {
            this.isNio = false;
            this.sockChan = sock.getChannel();
         } else {
            this.isNio = false;
            this.sockChan = sock.getChannel();
         }

         this.OrgSock = sock;
      }
   }

   public WLSCmnChannel(WLSSSLNioSocket sock) {
      this((Socket)sock);
   }

   public SelectableChannel getSelectableChannel() {
      return (SelectableChannel)(this.isNio ? this.nioSock.getSelectableChannel() : this.sockChan);
   }

   public int write(ByteBuffer src) throws IOException {
      return this.isNio ? this.nioSock.getWritableByteChannel().write(src) : this.sockChan.write(src);
   }

   public long write(ByteBuffer[] srcs) throws IOException {
      if (this.isNio) {
         throw new UnsupportedOperationException("Not supported for the non-blocking SSL mode, use the other write method, write(ByteBuffer src)");
      } else {
         return this.sockChan.write(srcs);
      }
   }

   public long write(ByteBuffer[] srcs, int offset, int length) throws IOException {
      if (this.isNio) {
         throw new UnsupportedOperationException("Not supported for the non-blocking SSL mode, use the other write method, write(ByteBuffer src)");
      } else {
         return this.sockChan.write(srcs, offset, length);
      }
   }

   public int read(ByteBuffer dst) throws IOException {
      return this.isNio ? this.nioSock.getReadableByteChannel().read(dst) : this.sockChan.read(dst);
   }

   public final long read(ByteBuffer[] dsts) throws IOException {
      if (this.isNio) {
         throw new UnsupportedOperationException("Not supported for the non-blocking SSL mode, use the alternative read method, read(ByteBuffer dst)");
      } else {
         return this.sockChan.read(dsts, 0, dsts.length);
      }
   }

   public long read(ByteBuffer[] dsts, int offset, int length) throws IOException {
      if (this.isNio) {
         throw new UnsupportedOperationException("Not supported for the non-blocking SSL mode, use the alternative read method, read(ByteBuffer dst)");
      } else {
         return this.sockChan.read(dsts, offset, length);
      }
   }

   public void close() throws IOException {
      if (this.isNio) {
         this.OrgSock.close();
      } else {
         this.sockChan.close();
      }

   }
}
