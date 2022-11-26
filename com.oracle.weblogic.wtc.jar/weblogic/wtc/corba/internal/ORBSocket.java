package weblogic.wtc.corba.internal;

import com.bea.core.jatmi.common.ntrace;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import weblogic.wtc.gwt.TuxedoCorbaConnection;
import weblogic.wtc.gwt.TuxedoCorbaConnectionFactory;
import weblogic.wtc.jatmi.BEAObjectKey;
import weblogic.wtc.jatmi.CallDescriptor;
import weblogic.wtc.jatmi.TPException;

public final class ORBSocket extends Socket {
   private boolean dummyTcpNoDelay;
   private boolean dummyKeepAlive;
   private int dummyRecvBufSize;
   private int dummySendBufSize;
   private boolean dummyLingerEnabled;
   private int dummyLingerValue;
   private int dummyPort;
   private InetAddress dummyInetAddress;
   private int dummyTimeout;
   private ORBSocketInputStream inputStream;
   private ORBSocketOutputStream outputStream;
   private TuxedoCorbaConnection tuxConnection;
   private HashMap outstandingReqMap;

   public ORBSocket(String host, int port) throws UnknownHostException, IOException {
      this(InetAddress.getByName(host), port, true);
   }

   public ORBSocket(InetAddress address, int port) throws IOException {
      this(address, port, true);
   }

   private ORBSocket(InetAddress address, int port, boolean stream) throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(8);
      if (traceEnabled) {
         ntrace.doTrace("[/ORBSocket//" + address + "/" + port + "/" + stream);
      }

      this.dummyTcpNoDelay = false;
      this.dummyKeepAlive = false;
      this.dummyRecvBufSize = 2048;
      this.dummySendBufSize = 2048;
      this.dummyLingerEnabled = false;
      this.dummyLingerValue = 0;
      this.dummyInetAddress = address;
      this.dummyPort = port;
      this.dummyTimeout = 0;

      TuxedoCorbaConnectionFactory tcf;
      try {
         Context ctx = new InitialContext();
         tcf = (TuxedoCorbaConnectionFactory)ctx.lookup("tuxedo.services.TuxedoCorbaConnection");
      } catch (NamingException var10) {
         throw new IOException("Could not get TuxedoCorbaConnectionFactory : " + var10);
      } catch (Exception var11) {
         throw new IOException("Could not get TuxedoCorbaConnectionFactory : " + var11);
      }

      try {
         this.tuxConnection = tcf.getTuxedoCorbaConnection();
      } catch (TPException var8) {
         throw new IOException("Could not get TuxedoCorbaConnection : " + var8);
      } catch (Exception var9) {
         throw new IOException("Could not get TuxedoCorbaConnection : " + var9);
      }

      this.inputStream = new ORBSocketInputStream(this, this.tuxConnection);
      this.outputStream = new ORBSocketOutputStream(this, this.tuxConnection);
      this.outstandingReqMap = new HashMap();
      if (traceEnabled) {
         ntrace.doTrace("[/ORBSocket//50");
      }

   }

   public InetAddress getInetAddress() {
      return this.dummyInetAddress;
   }

   public InetAddress getLocalAddress() {
      try {
         return InetAddress.getLocalHost();
      } catch (UnknownHostException var2) {
         return null;
      }
   }

   public int getPort() {
      return this.dummyPort;
   }

   public int getLocalPort() {
      return 0;
   }

   public InputStream getInputStream() throws IOException {
      return this.inputStream;
   }

   public OutputStream getOutputStream() throws IOException {
      return this.outputStream;
   }

   public void setTcpNoDelay(boolean on) throws SocketException {
      this.dummyTcpNoDelay = on;
   }

   public boolean getTcpNoDelay() throws SocketException {
      return this.dummyTcpNoDelay;
   }

   public void setSoLinger(boolean on, int linger) throws SocketException {
      this.dummyLingerEnabled = on;
      this.dummyLingerValue = linger;
   }

   public int getSoLinger() throws SocketException {
      return this.dummyLingerEnabled ? this.dummyLingerValue : -1;
   }

   public synchronized void setSoTimeout(int timeout) throws SocketException {
      this.dummyTimeout = timeout;
   }

   public synchronized int getSoTimeout() throws SocketException {
      return this.dummyTimeout;
   }

   public synchronized void setSendBufferSize(int size) throws SocketException {
   }

   public synchronized int getSendBufferSize() throws SocketException {
      return this.dummySendBufSize;
   }

   public synchronized void setReceiveBufferSize(int size) throws SocketException {
      this.dummyRecvBufSize = size;
   }

   public synchronized int getReceiveBufferSize() throws SocketException {
      return this.dummyRecvBufSize;
   }

   public void setKeepAlive(boolean on) throws SocketException {
      this.dummyKeepAlive = on;
   }

   public boolean getKeepAlive() throws SocketException {
      return this.dummyKeepAlive;
   }

   public synchronized void close() throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(8);
      if (traceEnabled) {
         ntrace.doTrace("[/ORBSocket/close/");
      }

      try {
         this.inputStream.close();
         this.outputStream.close();
         this.tuxConnection.tpterm();
      } catch (Exception var3) {
         if (traceEnabled) {
            ntrace.doTrace("*]/ORBSocket/close/10/IOException" + var3);
            var3.printStackTrace();
         }

         throw new IOException("Could not close ORBSocket : " + var3);
      }

      if (traceEnabled) {
         ntrace.doTrace("[/ORBSocket/close/50");
      }

   }

   public void shutdownInput() throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(8);
      if (traceEnabled) {
         ntrace.doTrace("[/ORBSocket/shutdownInput/");
      }

      try {
         this.inputStream.close();
      } catch (Exception var3) {
         if (traceEnabled) {
            ntrace.doTrace("*]/ORBSocket/shutdownInput/10/IOException" + var3);
         }

         throw new IOException("Could not close ORBSocketInputStream : " + var3);
      }

      if (traceEnabled) {
         ntrace.doTrace("[/ORBSocket/shutdownInput/50");
      }

   }

   public void shutdownOutput() throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(8);
      if (traceEnabled) {
         ntrace.doTrace("[/ORBSocket/shutdownOutput/");
      }

      try {
         this.outputStream.close();
      } catch (Exception var3) {
         if (traceEnabled) {
            ntrace.doTrace("*]/ORBSocket/shutdownOutput/10/IOException" + var3);
         }

         throw new IOException("Could not close ORBSocketOutputStream : " + var3);
      }

      if (traceEnabled) {
         ntrace.doTrace("[/ORBSocket/shutdownOutput/50");
      }

   }

   public void addOutstandingRequest(CallDescriptor cd, BEAObjectKey key) {
      boolean traceEnabled = ntrace.isTraceEnabled(8);
      if (traceEnabled) {
         ntrace.doTrace("[/ORBSocket/addOutstandingRequest/" + cd + "/" + key);
      }

      synchronized(this.outstandingReqMap) {
         this.outstandingReqMap.put(cd, key);
      }

      if (traceEnabled) {
         ntrace.doTrace("[/ORBSocket/addOutstandingRequest/50");
      }

   }

   public BEAObjectKey removeOutstandingRequest(CallDescriptor cd) {
      boolean traceEnabled = ntrace.isTraceEnabled(8);
      if (traceEnabled) {
         ntrace.doTrace("[/ORBSocket/removeOutstandingRequest/" + cd);
      }

      BEAObjectKey retKey = null;
      synchronized(this.outstandingReqMap) {
         retKey = (BEAObjectKey)this.outstandingReqMap.remove(cd);
         if (retKey == null && cd == null && !this.outstandingReqMap.isEmpty()) {
            Set keySet = this.outstandingReqMap.keySet();
            Iterator iter = keySet.iterator();
            retKey = (BEAObjectKey)this.outstandingReqMap.remove((CallDescriptor)iter.next());
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("[/ORBSocket/removeOutstandingRequest/50/" + retKey);
      }

      return retKey;
   }

   public CallDescriptor getOriginalCallDescriptor(CallDescriptor cd) {
      boolean traceEnabled = ntrace.isTraceEnabled(8);
      if (traceEnabled) {
         ntrace.doTrace("[/ORBSocket/getOriginalCallDescriptor/" + cd);
      }

      CallDescriptor ret = null;
      synchronized(this.outstandingReqMap) {
         if (this.outstandingReqMap.containsKey(cd)) {
            Set entrySet = this.outstandingReqMap.entrySet();
            Iterator iter = entrySet.iterator();

            while(iter.hasNext()) {
               Map.Entry entry = (Map.Entry)iter.next();
               CallDescriptor entryCD = (CallDescriptor)entry.getKey();
               if (entryCD.equals(cd)) {
                  ret = entryCD;
                  break;
               }
            }
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("[/ORBSocket/getOriginalCallDescriptor/50/" + ret);
      }

      return ret;
   }

   public String toString() {
      return "Socket[addr=" + this.dummyInetAddress + ",port=" + this.dummyPort + "]";
   }
}
