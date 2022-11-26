package weblogic.rjvm;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import weblogic.common.WLObjectOutput;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.kernel.KernelStatus;
import weblogic.protocol.AsyncOutgoingMessage;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerChannelStream;
import weblogic.rmi.LocalObject;
import weblogic.rmi.cluster.PiggybackResponse;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.RMIEnvironment;
import weblogic.rmi.provider.BasicServiceContextList;
import weblogic.rmi.provider.WorkServiceContext;
import weblogic.rmi.spi.EndPoint;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.Interceptor;
import weblogic.rmi.spi.InterceptorManager;
import weblogic.rmi.spi.MsgOutput;
import weblogic.rmi.spi.OutboundResponse;
import weblogic.rmi.spi.ServiceContext;
import weblogic.security.acl.internal.AuthenticatedUser;
import weblogic.utils.StackTraceUtilsClient;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.io.Chunk;
import weblogic.utils.io.ChunkedObjectOutputStream;
import weblogic.utils.io.Immutable;
import weblogic.workarea.WorkContext;
import weblogic.workarea.WorkContextHelper;
import weblogic.workarea.WorkContextOutput;
import weblogic.workarea.spi.WorkContextMapInterceptor;

final class MsgAbbrevOutputStream extends ChunkedObjectOutputStream implements AsyncOutgoingMessage, MsgOutput, OutboundResponse, PeerInfoable, ReplyStream, RequestStream, ServerChannelStream, WLObjectOutput, WorkContextOutput {
   private static final boolean DEBUG = false;
   private static final boolean tracingEnabled = RJVMEnvironment.getEnvironment().isTracingEnabled();
   private static final long EJBEXCEPTION_SVUID = 796770993296843510L;
   private static ObjectStreamClass OLD_EJBEXCEPTION_OSC;
   private final OutboundMsgAbbrev abbrevs;
   private final ConnectionManager connectionManager;
   private ServerChannel channel;
   private int replyID;
   private AuthenticatedUser user;
   final JVMMessage header;
   private boolean contextsMarshaled;
   private final BasicServiceContextList contexts;
   private int timeout;
   private ObjectOutputStream objectStream;
   private int immutableNum;
   private RuntimeMethodDescriptor md;
   private boolean returnToPoolOnClose;
   private PeerInfo peerInfo;
   private String partitionName;
   private String partitionURL;
   private MsgAbbrevJVMConnection phantomConnection;

   MsgAbbrevOutputStream(ConnectionManager cm, PeerInfo peerInfo, ServerChannel channel, String partitionName) throws IOException {
      this(cm, channel, true);
      this.peerInfo = peerInfo;
      this.partitionName = partitionName;
      this.partitionURL = null;
   }

   MsgAbbrevOutputStream(ConnectionManager cm, ServerChannel channel) throws IOException {
      this(cm, channel, false);
      this.partitionName = "DOMAIN";
      this.partitionURL = null;
   }

   private MsgAbbrevOutputStream(ConnectionManager cm, ServerChannel channel, boolean pool) throws IOException {
      this.abbrevs = new OutboundMsgAbbrev(this);
      this.channel = null;
      this.contexts = new BasicServiceContextList();
      this.returnToPoolOnClose = true;
      this.phantomConnection = null;
      this.objectStream = new NestedObjectOutputStream(this);
      this.connectionManager = cm;
      this.channel = channel;
      this.header = new JVMMessage();
      this.replyID = -1;
      this.returnToPoolOnClose = pool;
      this.skip(19);
      this.partitionURL = null;
   }

   public ObjectOutputStream getOutputStream() {
      return this.objectStream;
   }

   void setPhantomConnection(MsgAbbrevJVMConnection phantomConnection) {
      this.phantomConnection = phantomConnection;
   }

   MsgAbbrevJVMConnection getPhantomConnection() {
      return this.phantomConnection;
   }

   public void writeContext(WorkContext runtimeContext) throws IOException {
      this.writeASCII(runtimeContext.getClass().getName());
      runtimeContext.writeContext(this);
   }

   void setUser(AuthenticatedUser user) {
      this.user = user;
   }

   AuthenticatedUser getUser() {
      return this.user;
   }

   public void transferThreadLocalContext(InboundRequest request) throws IOException {
      if (!this.contextsMarshaled) {
         this.contextsMarshaled = true;
         Interceptor ti = InterceptorManager.getManager().getTransactionInterceptor();
         if (ti != null) {
            this.setTxContext(ti.sendResponse(request.getTxContext()));
         }

         this.marshalCustomCallData();
         WorkContextMapInterceptor interceptor = WorkContextHelper.getWorkContextHelper().getLocalInterceptor();
         if (interceptor != null) {
            this.setContext(new WorkServiceContext(false));
         }

         this.marshalUserCustomCallData();
      }
   }

   void setRuntimeMethodDescriptor(RuntimeMethodDescriptor md) {
      this.md = md;
   }

   public void setTxContext(Object ptx) throws RemoteException {
      this.contexts.setContextData(0, ptx);
   }

   public void setContext(int id, Object data) throws IOException {
      this.contexts.setContextData(id, data);
   }

   public void setContext(ServiceContext sc) {
      this.contexts.setContext(sc);
   }

   byte[] getTrace() {
      return (byte[])((byte[])this.contexts.getContextData(4));
   }

   Object getTxContext() {
      return this.contexts.getContextData(0);
   }

   Object getContext(int id) {
      return this.contexts.getContextData(id);
   }

   int getReplyID() {
      return this.replyID;
   }

   void setReplyID(int id) {
      this.replyID = id;
   }

   OutboundMsgAbbrev getAbbrevs() {
      return this.abbrevs;
   }

   JVMMessage getMessageHeader() {
      return this.header;
   }

   String getPartitionName() {
      return this.partitionName;
   }

   void setPartitionName(String partitionName) {
      this.partitionName = partitionName;
   }

   String getPartitionURL() {
      return this.partitionURL;
   }

   void setPartitionURL(String partitionURL) {
      this.partitionURL = partitionURL;
   }

   public void setTimeOut(int mSecs) {
      this.timeout = mSecs;
   }

   public void annotateProxyClass(Class c) throws IOException {
      PeerInfo info = this.getPeerInfo();
      if (info.getMajor() > 8 || info.getMajor() == 8 && info.getServicePack() >= 2 || info.getMajor() == 7 && info.getServicePack() >= 5 || info.getMajor() == 6 && info.getServicePack() >= 7) {
         Class[] intf = c.getInterfaces();
         String annotation = "";

         for(int i = 0; i < intf.length; ++i) {
            if (intf[i].getClassLoader() instanceof GenericClassLoader) {
               annotation = ((GenericClassLoader)intf[i].getClassLoader()).getAnnotation().getAnnotationString();
               break;
            }
         }

         if (annotation == null) {
            annotation = "";
         }

         this.writeUTF(annotation);
      }

   }

   protected final Object replaceNonSerializable(Object o) throws IOException {
      return KernelStatus.isApplet() ? this.replaceObject(o) : o;
   }

   protected void writeClassDescriptor(ObjectStreamClass descriptor) throws IOException {
      ClassLoader cl = descriptor.forClass().getClassLoader();
      String annotation = null;
      if (cl != null && cl instanceof GenericClassLoader) {
         GenericClassLoader gcl = (GenericClassLoader)cl;
         annotation = gcl.getAnnotation().getAnnotationString();
      }

      if (annotation == null) {
         annotation = "";
      }

      if (descriptor.getSerialVersionUID() == 796770993296843510L && this.getInteropMode()) {
         PeerInfo info = this.getPeerInfo();
         if (info.getMajor() == 6 && info.getMinor() == 1) {
            descriptor = get61EJBExceptionDescriptor();
         }
      }

      ClassTableEntry cte = new ClassTableEntry(descriptor, annotation);
      this.writeImmutable(cte);
   }

   private static ObjectStreamClass get61EJBExceptionDescriptor() throws IOException {
      if (OLD_EJBEXCEPTION_OSC == null) {
         try {
            ObjectStreamClass target = ObjectStreamClass.lookup(Class.forName("javax.ejb.EJBException"));
            ObjectStreamClass result = (ObjectStreamClass)RJVMEnvironment.getEnvironment().copyObject(target);
            Field f = ObjectStreamClass.class.getDeclaredField("suid");
            f.setAccessible(true);
            f.set(result, new Long(-9219910240172116449L));
            OLD_EJBEXCEPTION_OSC = result;
         } catch (Exception var3) {
            throw new IOException(var3);
         }
      }

      return OLD_EJBEXCEPTION_OSC;
   }

   public void reset() {
      super.reset();
      this.skip(19);
      this.abbrevs.reset();
      this.immutableNum = 0;
      this.user = null;
      this.replyID = -1;
      this.timeout = 0;
      this.md = null;
      this.header.reset();
      this.contextsMarshaled = false;
      this.contexts.reset();
      this.partitionName = null;
      this.phantomConnection = null;
   }

   public void marshalCustomCallData() throws IOException {
      if (this.getPeerInfo().compareTo(PeerInfo.VERSION_DIABLO) < 0) {
         this.write81Contexts();
      } else {
         int sz = this.contexts.size() - this.contexts.sizeUser();
         if (sz > 0) {
            this.header.setFlag(8);
            this.write(sz);
            Iterator i = this.contexts.iterator();

            while(i.hasNext()) {
               ServiceContext sc = (ServiceContext)i.next();
               if (!sc.isUser()) {
                  boolean abbreved = sc instanceof Immutable;
                  this.writeBoolean(abbreved);
                  if (abbreved) {
                     this.writeImmutable(sc);
                  } else {
                     this.writeObject(sc);
                  }
               }
            }
         }
      }

   }

   void marshalUserCustomCallData() throws IOException {
      int sz = this.contexts.sizeUser();
      if (sz > 0) {
         if (this.getPeerInfo().compareTo(PeerInfo.VERSION_DIABLO) < 0) {
            this.write81UserContexts();
         } else {
            this.header.setFlag(16);
            this.write(sz);
            Iterator i = this.contexts.iterator();

            while(i.hasNext()) {
               ServiceContext sc = (ServiceContext)i.next();
               if (sc.isUser()) {
                  boolean abbreved = sc instanceof Immutable;
                  this.writeBoolean(abbreved);
                  if (abbreved) {
                     this.writeImmutable(sc);
                  } else {
                     this.writeObject(sc);
                  }
               }
            }
         }
      }

   }

   private void write81Contexts() throws IOException {
      Object ctx;
      if ((ctx = this.contexts.getContextData(0)) != null) {
         this.writeObject(ctx);
         this.header.setFlag(2);
      }

      if (tracingEnabled && (ctx = this.contexts.getContextData(4)) != null && this.getPeerInfo().compareTo(PeerInfo.VERSION_70) >= 0) {
         this.writeObject(ctx);
         this.header.setFlag(4);
      }

      if (this.md != null) {
         this.writeImmutable(this.md);
      }

   }

   private void write81UserContexts() throws IOException {
      Object ctx;
      if ((ctx = this.contexts.getContextData(2)) != null) {
         this.writeObject(ctx);
      }

   }

   private Response flushAndSend(int id, JVMMessage.Command cmd, ResponseImpl res, byte QOS) {
      this.connectionManager.thisRJVM.send(id, cmd, res, this, QOS);
      return res;
   }

   public void sendOneWay(int id) throws RemoteException {
      this.flushAndSend(id, JVMMessage.Command.CMD_ONE_WAY, (ResponseImpl)null, (byte)101);
   }

   public Response sendRecv(int id) throws RemoteException {
      return this.flushAndSend(id, JVMMessage.Command.CMD_REQUEST, new ResponseImpl((RJVM)this.getEndPoint(), this.timeout, this.md), (byte)101);
   }

   public void sendAsync(int id, ResponseListener l) throws RemoteException {
      this.flushAndSend(id, JVMMessage.Command.CMD_REQUEST, new ResponseWithListener((RJVM)this.getEndPoint(), this.timeout, l, this.md), (byte)101);
   }

   public void sendOneWay(int id, byte QOS) throws RemoteException {
      this.flushAndSend(id, JVMMessage.Command.CMD_ONE_WAY, (ResponseImpl)null, QOS);
   }

   public Response sendRecv(int id, byte QOS) throws RemoteException {
      return this.flushAndSend(id, JVMMessage.Command.CMD_REQUEST, new ResponseImpl((RJVM)this.getEndPoint(), this.timeout, this.md), QOS);
   }

   public void sendAsync(int id, ResponseListener l, byte QOS) throws RemoteException {
      this.flushAndSend(id, JVMMessage.Command.CMD_REQUEST, new ResponseWithListener((RJVM)this.getEndPoint(), this.timeout, l, this.md), QOS);
   }

   public void send() throws RemoteException {
      this.flushAndSend(this.replyID, JVMMessage.Command.CMD_RESPONSE, (ResponseImpl)null, (byte)101);
   }

   public void sendThrowable(Throwable problem) {
      int tempReplyID = this.replyID;
      if (!RMIEnvironment.getEnvironment().printExceptionStackTrace()) {
         StackTraceUtilsClient.scrubExceptionStackTrace(problem);
      }

      try {
         try {
            this.writeObject(problem);
         } catch (IOException var8) {
            IOException ioe = var8;
            this.flushAndReset();

            try {
               this.writeObject(new UnmarshalException("Failed to marshal error response: '" + problem + "' because exception ", ioe));
            } catch (IOException var7) {
               this.flushAndReset();

               try {
                  this.writeObject(new UnmarshalException(problem.toString()));
               } catch (IOException var6) {
                  this.writeSomeErrorResponse();
               }
            }
         }
      } catch (RuntimeException var9) {
         this.writeSomeErrorResponse();
      }

      this.connectionManager.thisRJVM.send(tempReplyID, JVMMessage.Command.CMD_ERROR_RESPONSE, (ResponseImpl)null, this, (byte)101);
   }

   private void writeSomeErrorResponse() {
      this.flushAndReset();

      try {
         this.writeObject(new UnmarshalException("Total failure to marshal an error response"));
      } catch (IOException var2) {
         throw new AssertionError(var2);
      }
   }

   private void flushAndReset() {
      try {
         this.flush();
         this.reset();
      } catch (IOException var2) {
         throw new AssertionError(var2);
      }
   }

   public Chunk getChunks() {
      this.setPosition(0);
      this.writeInt(this.getSize());
      this.header.writeHeader(this);
      Chunk c = super.getChunks();
      this.close();
      return c;
   }

   public void enqueue() {
   }

   public void cleanup() {
      this.close();
   }

   public void writeTo(OutputStream out) throws IOException {
      this.setPosition(0);
      this.writeInt(this.getSize());
      this.header.writeHeader(this);
      super.writeTo(out);
      this.close();
   }

   public int getLength() throws IOException {
      return this.getSize();
   }

   public void close() {
      if (this.returnToPoolOnClose) {
         this.connectionManager.releaseOutputStream(this);
      }

   }

   public String toString() {
      return super.toString() + ", reply: '" + this.replyID + " to: '" + this.connectionManager + "', user: '" + this.user + "', tx: '" + this.getTxContext() + '\'';
   }

   public MsgOutput getMsgOutput() {
      return this;
   }

   public void setPiggybackResponse(PiggybackResponse piggybackResponse) throws IOException {
      this.writeObject(piggybackResponse, Object.class);
   }

   public PeerInfo getPeerInfo() {
      return this.peerInfo;
   }

   private boolean getInteropMode() {
      return this.connectionManager.thisRJVM.getInteropMode();
   }

   void setServerChannel(ServerChannel ch) {
      this.channel = ch;
   }

   public EndPoint getEndPoint() {
      return this.connectionManager.thisRJVM;
   }

   public ServerChannel getServerChannel() {
      return this.channel;
   }

   public final void writeObjectWL(Object o) throws IOException {
      if (!(o instanceof Serializable)) {
         this.writeObject(this.replaceObject(o));
      } else {
         this.writeObject(o);
      }

   }

   public final void writeObject(Object o) throws IOException {
      if (this.isPreDiabloPeer()) {
         this.writeObjectToPreDiabloPeer(o);
      } else {
         this.objectStream.writeObject(o);
      }

   }

   public final void writeString(String s) throws IOException {
      if (s == null) {
         this.writeByte(112);
      } else {
         this.writeByte(116);
         this.writeUTF(s);
      }

   }

   public final void writeDate(Date dateval) throws IOException {
      this.writeObject(dateval);
   }

   public final void writeArrayList(ArrayList lst) throws IOException {
      this.writeObject(lst);
   }

   public final void writeProperties(Properties propval) throws IOException {
      this.writeObject(propval);
   }

   public final void writeBytes(byte[] val) throws IOException {
      this.writeObject(val);
   }

   public final void writeArrayOfObjects(Object[] aoo) throws IOException {
      this.writeObject(aoo);
   }

   public final void writeImmutable(Object o) throws IOException {
      this.writeLength(this.immutableNum++);
      this.getAbbrevs().addAbbrev(o);
   }

   public void writeAbbrevString(String s) throws IOException {
      this.writeImmutable(s);
   }

   protected void initNestedStream() throws IOException {
      this.objectStream.reset();
      this.chunkPos = 0;
   }

   private boolean isPreDiabloPeer() {
      if (this.connectionManager != null && this.connectionManager.thisRJVM != null) {
         PeerInfo info = this.getPeerInfo();
         if (info != null && info.compareTo(PeerInfo.VERSION_DIABLO) >= 0) {
            return false;
         }
      }

      return true;
   }

   final class NestedObjectOutputStream extends ChunkedObjectOutputStream.NestedObjectOutputStream implements WLObjectOutput, ServerChannelStream, WorkContextOutput, PeerInfoable {
      private NestedObjectOutputStream(OutputStream out) throws IOException {
         super(MsgAbbrevOutputStream.this, out);
      }

      protected Object replaceObject(Object obj) throws IOException {
         return obj instanceof Remote && obj instanceof LocalObject ? obj : super.replaceObject(obj);
      }

      public ServerChannel getServerChannel() {
         return MsgAbbrevOutputStream.this.getServerChannel();
      }

      public void writeObjectWL(Object o) throws IOException {
         this.drain();
         MsgAbbrevOutputStream.this.writeObjectWL(o);
      }

      public void writeString(String s) throws IOException {
         this.drain();
         MsgAbbrevOutputStream.this.writeString(s);
      }

      public void writeDate(Date dateval) throws IOException {
         this.drain();
         MsgAbbrevOutputStream.this.writeDate(dateval);
      }

      public void writeArrayList(ArrayList lst) throws IOException {
         this.drain();
         MsgAbbrevOutputStream.this.writeArrayList(lst);
      }

      public void writeProperties(Properties propval) throws IOException {
         this.drain();
         MsgAbbrevOutputStream.this.writeProperties(propval);
      }

      public void writeBytes(byte[] val) throws IOException {
         this.drain();
         MsgAbbrevOutputStream.this.writeBytes(val);
      }

      public void writeArrayOfObjects(Object[] aoo) throws IOException {
         this.drain();
         MsgAbbrevOutputStream.this.writeArrayOfObjects(aoo);
      }

      public void writeImmutable(Object o) throws IOException {
         this.drain();
         MsgAbbrevOutputStream.this.writeImmutable(o);
      }

      public void writeAbbrevString(String s) throws IOException {
         this.drain();
         MsgAbbrevOutputStream.this.writeAbbrevString(s);
      }

      public void writeContext(WorkContext runtimeContext) throws IOException {
         this.drain();
         MsgAbbrevOutputStream.this.writeContext(runtimeContext);
      }

      public PeerInfo getPeerInfo() {
         return MsgAbbrevOutputStream.this.getPeerInfo();
      }

      // $FF: synthetic method
      NestedObjectOutputStream(OutputStream x1, Object x2) throws IOException {
         this(x1);
      }
   }
}
