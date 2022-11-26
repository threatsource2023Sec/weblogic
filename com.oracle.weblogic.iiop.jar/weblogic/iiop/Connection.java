package weblogic.iiop;

import java.net.InetSocketAddress;
import java.rmi.Remote;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.common.internal.PeerInfo;
import weblogic.iiop.ior.IOR;
import weblogic.iiop.protocol.CodeSet;
import weblogic.iiop.protocol.ListenPoint;
import weblogic.protocol.AsyncMessageSender;
import weblogic.protocol.ServerChannel;
import weblogic.rmi.spi.Channel;
import weblogic.security.acl.UserInfo;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.ContextHandler;

public abstract class Connection implements AsyncMessageSender {
   public static final String CONN_BIDIR_KEYS = "weblogic.iiop.BiDirKeys";
   protected static final int SENT_CODE_SET = 1;
   public static final int SENT_CODEBASE = 2;
   public static final int SENT_VERSION = 4;
   public static final int SENT_BIDIR = 8;
   public static final int CONN_NEGOTIATED = 16;
   public static final int RECV_CODE_SET = 32;
   public static final int RECV_BIDIR = 64;
   private static final InetSocketAddress NULL_SOCKET_ADDRESS = new InetSocketAddress(0);
   private int flags = 0;
   private Remote hbStub;
   private int minorVersion;
   private PeerInfo peerinfo = null;
   private int char_codeset = CodeSet.getDefaultCharCodeSet();
   private int wchar_codeset = CodeSet.getDefaultWcharCodeSet();
   private IOR remoteCodeBase = null;
   private ConcurrentHashMap properties = new ConcurrentHashMap();

   public abstract void close();

   public abstract boolean isClosed();

   public boolean isDead() {
      return false;
   }

   public abstract ListenPoint getListenPoint();

   public abstract void setListenPoint(ListenPoint var1);

   final void setHeartbeatStub(Remote stub) {
      this.hbStub = stub;
   }

   final Remote getHeartbeatStub() {
      return this.hbStub;
   }

   public abstract AuthenticatedSubject getUser();

   public abstract void authenticate(UserInfo var1);

   public abstract Object getTxContext();

   public abstract void setTxContext(Object var1);

   public abstract EndPoint createEndPoint();

   public abstract ServerChannel getChannel();

   public abstract Channel getRemoteChannel();

   public boolean isStateful() {
      return true;
   }

   public int getMinorVersion() {
      return this.minorVersion;
   }

   public void setMinorVersion(int v) {
      this.minorVersion = v;
   }

   public void setFlag(int f) {
      this.flags |= f;
   }

   public boolean getFlag(int f) {
      return (this.flags & f) != 0;
   }

   public int getWcharCodeSet() {
      return this.wchar_codeset;
   }

   public int getCharCodeSet() {
      return this.char_codeset;
   }

   public void setCodeSets(int cs, int ws) {
      this.char_codeset = cs;
      this.wchar_codeset = ws;
   }

   public IOR getRemoteCodeBase() {
      return this.remoteCodeBase;
   }

   public void setRemoteCodeBase(IOR ior) {
      this.remoteCodeBase = ior;
   }

   public PeerInfo getPeerInfo() {
      return this.peerinfo;
   }

   void setPeerInfo(PeerInfo peerinfo) {
      this.peerinfo = peerinfo;
   }

   public Object getProperty(String pname) {
      return this.properties.get(pname);
   }

   public void setProperty(String pname, Object prop) {
      this.properties.put(pname, prop);
   }

   protected abstract boolean isSecure();

   public ContextHandler getContextHandler() {
      return null;
   }
}
