package weblogic.rjvm;

import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import org.glassfish.pfl.basic.reflection.Bridge;
import weblogic.common.WLObjectInput;
import weblogic.common.internal.CodeBaseInfo;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.kernel.KernelStatus;
import weblogic.management.interop.JMXInteropHelper;
import weblogic.protocol.Protocol;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerChannelStream;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.RuntimeDescriptor;
import weblogic.rmi.provider.BasicServiceContextList;
import weblogic.rmi.spi.EndPoint;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;
import weblogic.rmi.spi.ServiceContext;
import weblogic.rmi.utils.Utilities;
import weblogic.rmi.utils.io.RemoteObjectReplacer;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.acl.internal.AuthenticatedUser;
import weblogic.security.service.ContextHandler;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.subject.AbstractSubject;
import weblogic.utils.Debug;
import weblogic.utils.PropertyHelper;
import weblogic.utils.io.Chunk;
import weblogic.utils.io.ChunkedObjectInputStream;
import weblogic.utils.io.oif.WebLogicObjectInputFilter;
import weblogic.workarea.WorkContext;
import weblogic.workarea.WorkContextInput;

public final class MsgAbbrevInputStream extends ChunkedObjectInputStream implements RemoteRequest, WLObjectInput, InboundRequest, PeerInfoable, MsgInput, ClusterInfoable, ServerChannelStream, WorkContextInput {
   private static final boolean noaccess = !System.getProperty("java.version").startsWith("1.");
   private static final Bridge BRIDGE = getBridge();
   private static final boolean DEBUG = false;
   private static final boolean USE_DUPLICATE_CONNECTION_RESPONSE = !PropertyHelper.getBoolean("weblogic.rjvm.disableDuplicateConnectionResponse");
   private static final Method CLEAR_METHOD = getClearMethod();
   private static final Field DATA_END_FLAG = getField(ObjectInputStream.class, "defaultDataEnd");
   private static final Field BLOCK_DATA_IS = getField(ObjectInputStream.class, "bin");
   private static final Field BLOCK_DATA_IS_END;
   private static final Field BLOCK_DATA_IS_POS;
   private static final Field BLOCK_DATA_IS_UNREAD;
   private static final Field PEEK_IS;
   private static final Field PEEK_IS_PEEKB;
   private static final boolean OBJECT_INPUT_STREAM_ACCESSIBLE_USING_REFLECTION;
   private static final Object[] NULL_ARGS;
   public static final HashMap PRIMITIVE_MAP;
   private static final DebugLogger debugMessaging;
   static final long OLD_EJBEXCEPTION_SVUID = -9219910240172116449L;
   private static final AuthenticatedSubject kernelID;
   private ConnectionManager connectionManager;
   private final InboundMsgAbbrev abbrevs;
   private MsgAbbrevJVMConnection connection;
   private RuntimeMethodDescriptor methodDescriptor;
   final JVMMessage header;
   private int responseId;
   private AuthenticatedUser user;
   final BasicServiceContextList contexts = new BasicServiceContextList();
   private String codebase;
   private int immutableNum = 0;
   private ClassTableEntry lastCTE;
   private NestedObjectInputStream objectStream = new NestedObjectInputStream(this);
   private boolean methodInited = false;

   private static Bridge getBridge() {
      return (Bridge)AccessController.doPrivileged(new PrivilegedAction() {
         public Bridge run() {
            return Bridge.get();
         }
      });
   }

   private static Method getClearMethod() {
      if (noaccess) {
         return null;
      } else {
         try {
            return BRIDGE.toAccessibleMethod(ObjectInputStream.class.getDeclaredMethod("clear"), MsgAbbrevInputStream.class);
         } catch (Exception var1) {
            RJVMLogger.logDebug2("'clear()' not initialized", var1);
            return null;
         }
      }
   }

   private static Field getField(Class c, String fieldName) {
      if (noaccess) {
         return null;
      } else {
         try {
            return BRIDGE.toAccessibleField(c.getDeclaredField(fieldName), MsgAbbrevInputStream.class);
         } catch (Exception var3) {
            RJVMLogger.logDebug2('\'' + c.getName() + '.' + fieldName + "' field not initialized", var3);
            return null;
         }
      }
   }

   private static Field getField(Field obj, String fieldName) {
      if (noaccess) {
         return null;
      } else if (obj == null) {
         return null;
      } else {
         try {
            return BRIDGE.toAccessibleField(obj.getType().getDeclaredField(fieldName), MsgAbbrevInputStream.class);
         } catch (Exception var3) {
            RJVMLogger.logDebug2('\'' + obj.getName() + '.' + fieldName + "' field not initialized", var3);
            return null;
         }
      }
   }

   MsgAbbrevInputStream(ConnectionManager cm) throws IOException {
      this.setReplacer(RemoteObjectReplacer.getReplacer());
      this.connectionManager = cm;
      this.header = new JVMMessage();
      this.abbrevs = new InboundMsgAbbrev();
   }

   void init(Chunk data, MsgAbbrevJVMConnection connection) throws ClassNotFoundException, IOException {
      super.init(data, 4);
      this.connection = connection;
      this.responseId = -1;
      this.user = null;
      this.setValidatingClass(false);
      this.header.readHeader(this, connection.getRemoteHeaderLength());
      if (this.connectionManager.thisRJVM != null) {
         this.header.src = this.connectionManager.thisRJVM.getID();
      }

      this.header.dest = JVMID.localID();
      if (KernelStatus.DEBUG && debugMessaging.isDebugEnabled()) {
      }

      this.mark(this.header.abbrevOffset);
      this.skip((long)(this.header.abbrevOffset - this.pos()));
      connection.readMsgAbbrevs(this);
      this.reset();
      if (JVMID.localID().equals(this.header.dest)) {
         if (!this.header.getFlag(8)) {
            this.read81Contexts();
         } else {
            this.readExtendedContexts();
         }
      }

   }

   void setConnectionManager(ConnectionManager cm) {
      this.connectionManager = cm;
   }

   private void read81Contexts() throws IOException, ClassNotFoundException {
      if (this.header.getFlag(2)) {
         this.contexts.setContextData(0, this.readObject());
      }

      if (this.header.getFlag(4)) {
         this.contexts.setContextData(4, this.readObject());
      }

   }

   final void readExtendedContexts() throws IOException {
      int nc = this.read();

      for(int i = 0; i < nc; ++i) {
         try {
            boolean abbreved = this.readBoolean();
            ServiceContext sc;
            if (abbreved) {
               sc = (ServiceContext)this.readImmutable();
            } else {
               sc = (ServiceContext)this.readObject();
            }

            if (sc != null) {
               this.contexts.setContext(sc);
            }
         } catch (ClassNotFoundException var5) {
         }
      }

   }

   public WorkContext readContext() throws IOException, ClassNotFoundException {
      Class rcClass = Utilities.loadClass(this.readASCII(), this.getCodebase());

      try {
         WorkContext runtimeContext = (WorkContext)rcClass.newInstance();
         runtimeContext.readContext(this);
         return runtimeContext;
      } catch (InstantiationException var3) {
         throw (IOException)(new NotSerializableException("WorkContext must have a public no-arg constructor")).initCause(var3);
      } catch (IllegalAccessException var4) {
         throw (IOException)(new NotSerializableException("WorkContext must have a public no-arg constructor")).initCause(var4);
      }
   }

   public ReplyStream getResponseStream() throws IOException {
      return this.getMsgAbbrevOutputStream();
   }

   private MsgAbbrevOutputStream getMsgAbbrevOutputStream() throws IOException {
      if (this.header.cmd == JVMMessage.Command.CMD_ONE_WAY) {
         throw new IOException("Requesting a response stream for oneWay request");
      } else {
         MsgAbbrevOutputStream res;
         if (this.connectionManager.thisRJVM != null && this.connectionManager.thisRJVM.getID().equals(this.header.src)) {
            res = this.connectionManager.thisRJVM.getResponseStream(this.getServerChannel(), this.header.QOS, this.connection.getRemotePartitionName());
            if (USE_DUPLICATE_CONNECTION_RESPONSE && this.connection.isPhantom()) {
               res.setPhantomConnection(this.connection);
            }
         } else {
            RJVMImpl theRJVM = RJVMManager.getRJVMManager().findOrCreateRemote(this.header.src);
            res = theRJVM.getResponseStream(this.getServerChannel(), this.header.QOS, this.connection.getRemotePartitionName());
         }

         res.setReplyID(this.responseId);
         res.setUser(this.user);
         return res;
      }
   }

   JVMMessage getMessageHeader() {
      return this.header;
   }

   void setResponseId(int rid) {
      this.responseId = rid;
   }

   public RJVM getOrigin() {
      return this.connectionManager.thisRJVM != null && this.connectionManager.thisRJVM.getID().equals(this.header.src) ? this.connectionManager.thisRJVM : RJVMManager.getRJVMManager().findRemote(this.header.src);
   }

   public String getRequestUrl() {
      return this.connection.getPartitionUrl();
   }

   public Protocol getProtocol() {
      return this.connection.getProtocol();
   }

   public ServerChannel getServerChannel() {
      return this.connection.getChannel();
   }

   public MsgAbbrevJVMConnection getConnection() {
      return this.connection;
   }

   public AbstractSubject getSubject() {
      return SecurityServiceManager.getSealedSubjectFromWire(kernelID, this.user);
   }

   AuthenticatedUser getAuthenticatedUser() {
      return this.user;
   }

   void setAuthenticatedUser(AuthenticatedUser au) {
      this.user = au;
   }

   public Object getTxContext() {
      return this.contexts.getContextData(0);
   }

   InboundMsgAbbrev getAbbrevs() {
      return this.abbrevs;
   }

   public String getCodebase() {
      if (this.connectionManager.thisRJVM == null) {
         return null;
      } else {
         if (this.codebase == null) {
            RJVMImpl rjvm = this.connectionManager.thisRJVM;
            this.codebase = rjvm.getCodebase(this.getConnection().getProtocol());
         }

         return this.codebase;
      }
   }

   public ContextHandler getContextHandler() {
      return this.connection.getContextHandler();
   }

   public final Object getContext(int id) {
      return this.contexts.getContextData(id);
   }

   public final boolean hasContext(int id) {
      return this.contexts.getContext(id) != null;
   }

   public void retrieveThreadLocalContext() throws IOException {
      if (this.header.getFlag(16)) {
         this.readExtendedContexts();
      }

   }

   protected ObjectStreamClass readClassDescriptor() throws IOException {
      this.lastCTE = (ClassTableEntry)this.readImmutable();
      return this.lastCTE.getDescriptor();
   }

   protected Class resolveClass(ObjectStreamClass descriptor) throws InvalidClassException, ClassNotFoundException {
      synchronized(this.lastCTE) {
         try {
            WebLogicObjectInputFilter.checkLegacyBlacklistIfNeeded(descriptor.getName());
         } catch (InvalidClassException var6) {
            if (KernelStatus.DEBUG && debugMessaging.isDebugEnabled()) {
               RJVMLogger.logDebug("Unauthorized deserialization attempt");
            }

            throw var6;
         }

         ClassLoader ccl = RJVMEnvironment.getEnvironment().getContextClassLoader();
         if (this.lastCTE.getClazz() == null || this.lastCTE.getClazzLoader() != ccl) {
            String classname = this.lastCTE.getDescriptor().getName();
            if (this.isPreDiabloPeer()) {
               classname = JMXInteropHelper.getJMXInteropClassName(classname);
            }

            this.lastCTE.setClazz((Class)PRIMITIVE_MAP.get(classname));
            if (this.lastCTE.getClazz() == null) {
               this.lastCTE.setClazz(Utilities.loadClass(classname, this.lastCTE.getAnnotation(), this.getCodebase(), ccl));
            }

            this.lastCTE.setClazzLoader(ccl);
         }

         this.lastClass = this.lastCTE.getClazz();
      }

      this.getInputStream().validateReturnType(this.lastClass);
      return this.lastClass;
   }

   protected final Class loadClass(String classname, String annotation) throws ClassNotFoundException {
      return Utilities.loadClass(classname, annotation, this.getCodebase());
   }

   protected Class resolveProxyClass(String[] interfaces) throws IOException, ClassNotFoundException {
      PeerInfo info = this.getPeerInfo();
      String annotation = null;
      if (info.getMajor() > 8 || info.getMajor() == 8 && info.getServicePack() >= 2 || info.getMajor() == 7 && info.getServicePack() >= 5 || info.getMajor() == 6 && info.getServicePack() >= 7) {
         annotation = this.readUTF();
      }

      return RJVMEnvironment.getEnvironment().resolveProxyClass(interfaces, annotation, this.getCodebase());
   }

   public void close() {
      super.close();
      this.methodInited = false;
      this.abbrevs.reset();
      this.lastClass = null;
      this.connection = null;
      this.responseId = -1;
      this.user = null;
      this.immutableNum = 0;
      this.contexts.reset();

      try {
         this.initNestedStream();
      } catch (IOException var2) {
         throw new AssertionError(var2);
      }

      this.methodDescriptor = null;
      this.lastCTE = null;
      this.connectionManager.releaseInputStream(this);
   }

   public String toString() {
      return "weblogic.rjvm.MsgAbbrevInputStream - " + this.responseId + " from: '" + this.connectionManager.thisRJVM + "', user: '" + this.user + "', tx: '" + this.getTxContext() + '\'';
   }

   public MsgInput getMsgInput() {
      return this;
   }

   public boolean isCollocated() {
      return JVMID.localID().equals(this.header.src);
   }

   public EndPoint getEndPoint() {
      return this.getOrigin();
   }

   public RuntimeMethodDescriptor getRuntimeMethodDescriptor(RuntimeDescriptor rd) throws IOException {
      if (!this.methodInited) {
         this.methodDescriptor = (RuntimeMethodDescriptor)this.contexts.getContextData(6);
         if (this.methodDescriptor == null) {
            this.methodDescriptor = (RuntimeMethodDescriptor)this.readImmutable();
         }

         this.methodDescriptor = this.methodDescriptor.getCanonical(rd);
         this.methodInited = true;
      }

      return this.methodDescriptor;
   }

   public Object getReplicaInfo() throws IOException {
      try {
         if (this.contexts.getContextData(3) == null) {
            this.contexts.setContextData(3, this.readObject());
         }

         return this.contexts.getContextData(3);
      } catch (ClassNotFoundException var2) {
         throw new AssertionError(var2);
      }
   }

   public Object getActivationID() throws IOException {
      try {
         if (this.contexts.getContextData(2) == null) {
            this.contexts.setContextData(2, this.readObject());
         }

         return this.contexts.getContextData(2);
      } catch (ClassNotFoundException var2) {
         throw new AssertionError(var2);
      }
   }

   public PeerInfo getPeerInfo() {
      return this.connectionManager.thisRJVM.getPeerInfo();
   }

   public String getPartitionName() {
      return this.connection.getRemotePartitionName();
   }

   public ClusterInfo getClusterInfo() {
      return this.connection.getClusterInfo();
   }

   public OutboundResponse getOutboundResponse() throws IOException {
      return this.getMsgAbbrevOutputStream();
   }

   public X509Certificate[] getCertificateChain() {
      return this.connection.getJavaCertChain();
   }

   public final Object readObjectWL() throws IOException, ClassNotFoundException {
      return this.readObject();
   }

   public final Object readObjectWLValidated(Class returnType) throws IOException, ClassNotFoundException {
      return this.readObjectValidated(returnType);
   }

   public final Object readObjectWL(Class clazz) throws IOException, ClassNotFoundException {
      return this.readObject(clazz);
   }

   public final Object readObject() throws IOException, ClassNotFoundException {
      return this.isPreDiabloPeer() ? this.readObjectFromPreDiabloPeer() : super.readObject();
   }

   private boolean isPreDiabloPeer() {
      RJVMImpl rjvm = this.connectionManager.thisRJVM;
      return rjvm == null || rjvm.isPreDiablo();
   }

   public final String readString() throws IOException {
      byte b = this.readByte();
      return b == 112 ? null : this.readUTF();
   }

   public final Date readDate() throws IOException {
      try {
         return (Date)this.readObject();
      } catch (ClassNotFoundException var2) {
         throw (Error)(new AssertionError("Couldn't find class")).initCause(var2);
      }
   }

   public final ArrayList readArrayList() throws IOException, ClassNotFoundException {
      return (ArrayList)this.readObject();
   }

   public final Properties readProperties() throws IOException {
      try {
         return (Properties)this.readObject();
      } catch (ClassNotFoundException var2) {
         throw (Error)(new AssertionError("Couldn't find class")).initCause(var2);
      }
   }

   public final byte[] readBytes() throws IOException {
      try {
         return (byte[])((byte[])this.readObject());
      } catch (ClassNotFoundException var2) {
         throw (Error)(new AssertionError("Couldn't find class")).initCause(var2);
      }
   }

   public final Object[] readArrayOfObjects() throws IOException, ClassNotFoundException {
      return (Object[])((Object[])this.readObject());
   }

   public Object readImmutable() throws IOException {
      int i = this.readLength();

      Object o;
      for(o = this.abbrevs.getAbbrev(); this.immutableNum++ < i; o = this.abbrevs.getAbbrev()) {
      }

      return o;
   }

   public String readAbbrevString() throws IOException {
      return (String)this.readImmutable();
   }

   protected final void initNestedStream() throws IOException {
      if (!OBJECT_INPUT_STREAM_ACCESSIBLE_USING_REFLECTION) {
         this.objectStream = new NestedObjectInputStream(this);
      } else {
         try {
            CLEAR_METHOD.invoke(this.objectStream, NULL_ARGS);
            DATA_END_FLAG.setBoolean(this.objectStream, false);
            Object bin = BLOCK_DATA_IS.get(this.objectStream);
            Object in = PEEK_IS.get(bin);
            BLOCK_DATA_IS_END.setInt(bin, 0);
            BLOCK_DATA_IS_POS.setInt(bin, 0);
            BLOCK_DATA_IS_UNREAD.setInt(bin, 0);
            PEEK_IS_PEEKB.setInt(in, -1);
         } catch (Exception var3) {
            throw new IOException(var3);
         }
      }

   }

   public NestedObjectInputStream getInputStream() {
      Debug.assertion(this.objectStream != null);
      return this.objectStream;
   }

   static {
      BLOCK_DATA_IS_END = getField(BLOCK_DATA_IS, "end");
      BLOCK_DATA_IS_POS = getField(BLOCK_DATA_IS, "pos");
      BLOCK_DATA_IS_UNREAD = getField(BLOCK_DATA_IS, "unread");
      PEEK_IS = getField(BLOCK_DATA_IS, "in");
      PEEK_IS_PEEKB = getField(PEEK_IS, "peekb");
      OBJECT_INPUT_STREAM_ACCESSIBLE_USING_REFLECTION = CLEAR_METHOD != null && DATA_END_FLAG != null && BLOCK_DATA_IS != null && BLOCK_DATA_IS_END != null && BLOCK_DATA_IS_POS != null && PEEK_IS != null && PEEK_IS_PEEKB != null;
      NULL_ARGS = new Object[0];
      PRIMITIVE_MAP = new HashMap(31);
      PRIMITIVE_MAP.put(Integer.TYPE.getName(), Integer.TYPE);
      PRIMITIVE_MAP.put(Byte.TYPE.getName(), Byte.TYPE);
      PRIMITIVE_MAP.put(Long.TYPE.getName(), Long.TYPE);
      PRIMITIVE_MAP.put(Float.TYPE.getName(), Float.TYPE);
      PRIMITIVE_MAP.put(Double.TYPE.getName(), Double.TYPE);
      PRIMITIVE_MAP.put(Short.TYPE.getName(), Short.TYPE);
      PRIMITIVE_MAP.put(Character.TYPE.getName(), Character.TYPE);
      PRIMITIVE_MAP.put(Boolean.TYPE.getName(), Boolean.TYPE);
      PRIMITIVE_MAP.put(Void.TYPE.getName(), Void.TYPE);
      debugMessaging = DebugLogger.getDebugLogger("DebugMessaging");
      kernelID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }

   private final class NestedObjectInputStream extends ChunkedObjectInputStream.NestedObjectInputStream implements WLObjectInput, CodeBaseInfo, ClusterInfoable, ServerChannelStream, WorkContextInput, PeerInfoable {
      private NestedObjectInputStream(InputStream in) throws IOException {
         super(MsgAbbrevInputStream.this, in);

         try {
            this.enableResolveObject(true);
         } catch (SecurityException var4) {
         }

      }

      protected Class resolveProxyClass(String[] interfaces) throws IOException, ClassNotFoundException {
         return MsgAbbrevInputStream.this.resolveProxyClass(interfaces);
      }

      public Object readObjectWL() throws IOException, ClassNotFoundException {
         return MsgAbbrevInputStream.this.readObjectWL();
      }

      public Object readObjectWLValidated(Class returnType) throws IOException, ClassNotFoundException {
         return MsgAbbrevInputStream.this.readObjectWLValidated(returnType);
      }

      public String readString() throws IOException {
         return MsgAbbrevInputStream.this.readString();
      }

      public Date readDate() throws IOException {
         return MsgAbbrevInputStream.this.readDate();
      }

      public ArrayList readArrayList() throws IOException, ClassNotFoundException {
         return MsgAbbrevInputStream.this.readArrayList();
      }

      public Properties readProperties() throws IOException {
         return MsgAbbrevInputStream.this.readProperties();
      }

      public byte[] readBytes() throws IOException {
         return MsgAbbrevInputStream.this.readBytes();
      }

      public Object[] readArrayOfObjects() throws IOException, ClassNotFoundException {
         return MsgAbbrevInputStream.this.readArrayOfObjects();
      }

      public Object readImmutable() throws IOException {
         return MsgAbbrevInputStream.this.readImmutable();
      }

      public String readAbbrevString() throws IOException {
         return MsgAbbrevInputStream.this.readAbbrevString();
      }

      public String getCodebase() {
         return MsgAbbrevInputStream.this.getCodebase();
      }

      public ServerChannel getServerChannel() {
         return MsgAbbrevInputStream.this.getServerChannel();
      }

      public ClusterInfo getClusterInfo() {
         return MsgAbbrevInputStream.this.getClusterInfo();
      }

      public WorkContext readContext() throws IOException, ClassNotFoundException {
         return MsgAbbrevInputStream.this.readContext();
      }

      public PeerInfo getPeerInfo() {
         return MsgAbbrevInputStream.this.getPeerInfo();
      }

      // $FF: synthetic method
      NestedObjectInputStream(InputStream x1, Object x2) throws IOException {
         this(x1);
      }
   }
}
