package weblogic.jms.common;

import java.io.ByteArrayOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import javax.jms.ObjectMessage;
import weblogic.common.WLObjectInput;
import weblogic.common.WLObjectOutput;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.jms.JMSClientExceptionLogger;
import weblogic.kernel.KernelStatus;
import weblogic.store.PersistentStoreException;
import weblogic.store.TestStoreException;
import weblogic.utils.io.FilteringObjectInputStream;
import weblogic.utils.io.Replacer;

public final class ObjectMessageImpl extends MessageImpl implements ObjectMessage, Externalizable, TestStoreException {
   private static final byte EXTVERSION = 1;
   private static final byte EXTVERSION2 = 2;
   private static final byte VERSIONMASK = 127;
   static final long serialVersionUID = -1035306457762201546L;
   private transient Serializable object;
   PayloadStream payload;
   private static final Replacer REPLACER = getReplacer();
   private static final String REMOTE_OBJECT_REPLACER_CLASS_NAME = "weblogic.rmi.utils.io.RemoteObjectReplacer";
   private static boolean testStoreExceptionEnabled;
   public static final String DEBUG_STORE_PROPERTY = "JMS_BEA_DEBUG_STORE_EXCEPTION";

   public ObjectMessageImpl() {
   }

   public ObjectMessageImpl(ObjectMessage message) throws javax.jms.JMSException {
      this(message, (javax.jms.Destination)null, (javax.jms.Destination)null);
   }

   public ObjectMessageImpl(ObjectMessage message, javax.jms.Destination destination, javax.jms.Destination replyDestination) throws javax.jms.JMSException {
      super(message, destination, replyDestination);
      this.setObject(message.getObject());
   }

   public byte getType() {
      return 4;
   }

   public static boolean isTestStoreExceptionEnabled() {
      return testStoreExceptionEnabled;
   }

   public void setObject(Serializable object) throws javax.jms.JMSException {
      this.setObject(object, (PeerInfo)null);
   }

   public void setObject(Serializable object, PeerInfo peerInfo) throws javax.jms.JMSException {
      this.writeMode();

      try {
         BufferOutputStream bos = PayloadFactoryImpl.createOutputStream();
         ObjectOutputStream2 oos = null;
         if (peerInfo == null) {
            oos = new ObjectOutputStream2(bos);
         } else {
            oos = new ObjectOutputStreamPeerInfoable(bos, peerInfo);
         }

         if (!((ObjectOutputStream2)oos).canReplace()) {
            object = (Serializable)((ObjectOutputStream2)oos).replaceObject(object);
         }

         ((ObjectOutputStream2)oos).writeObject(object);
         ((ObjectOutputStream2)oos).flush();
         this.payload = (PayloadStream)bos.moveToPayload();
         this.object = null;
      } catch (Exception var5) {
         throw new JMSException(JMSClientExceptionLogger.logSerializationErrorLoggable().getMessage(), var5);
      }
   }

   public Serializable getObject() throws javax.jms.JMSException {
      this.decompressMessageBody();
      if (this.object == null && this.payload != null) {
         try {
            ObjectInputStream2 ois = new ObjectInputStream2(this.payload.getInputStream());
            this.object = (Serializable)ois.readObject();
            if (!ois.canResolve()) {
               this.object = (Serializable)ois.resolveObject(this.object);
            }
         } catch (IOException var2) {
            throw new JMSException(JMSClientExceptionLogger.logDeserializeIOLoggable().getMessage(), var2);
         } catch (ClassNotFoundException var3) {
            throw new JMSException(JMSClientExceptionLogger.logDeserializeCNFELoggable().getMessage(), var3);
         }
      }

      return this.object;
   }

   public void nullBody() {
      this.object = null;
      this.payload = null;
   }

   public String toString() {
      try {
         return "ObjectMessage[" + this.getJMSMessageID() + "," + this.getObject() + "]";
      } catch (javax.jms.JMSException var2) {
         return "ObjectMessage[" + this.getJMSMessageID() + "]";
      }
   }

   private static Replacer getReplacer() {
      try {
         Class ror = Class.forName("weblogic.rmi.utils.io.RemoteObjectReplacer");
         Method getReplacerMethod = ror.getMethod("getReplacer");
         return (Replacer)getReplacerMethod.invoke((Object)null);
      } catch (ClassNotFoundException var2) {
         return null;
      } catch (NoSuchMethodException var3) {
         throw new AssertionError(var3);
      } catch (IllegalAccessException var4) {
         throw new AssertionError(var4);
      } catch (InvocationTargetException var5) {
         throw new AssertionError(var5);
      }
   }

   public PersistentStoreException getTestException() {
      try {
         if (isTestStoreExceptionEnabled() && this.propertyExists("JMS_BEA_DEBUG_STORE_EXCEPTION")) {
            Serializable value = this.getObject();
            if (value instanceof PersistentStoreException) {
               return (PersistentStoreException)value;
            }
         }
      } catch (javax.jms.JMSException var2) {
      }

      return null;
   }

   public void writeExternal(ObjectOutput tOut) throws IOException {
      super.writeExternal(tOut);
      int compressionThreshold = Integer.MAX_VALUE;
      ObjectOutput out;
      if (tOut instanceof MessageImpl.JMSObjectOutputWrapper) {
         compressionThreshold = ((MessageImpl.JMSObjectOutputWrapper)tOut).getCompressionThreshold();
         out = ((MessageImpl.JMSObjectOutputWrapper)tOut).getInnerObjectOutput();
      } else {
         out = tOut;
      }

      if (!this.isCompressed() && this.payload == null) {
         BufferOutputStream bos = PayloadFactoryImpl.createOutputStream();
         ObjectOutputStream os = new ObjectOutputStream2(bos);
         os.writeObject(this.object);
         os.flush();
         this.payload = (PayloadStream)bos.moveToPayload();
      }

      byte flag;
      if (this.getVersion(out) >= 30) {
         if (this.needToDecompressDueToInterop(out)) {
            flag = 2;
         } else {
            flag = (byte)(2 | (this.shouldCompress(out, compressionThreshold) ? -128 : 0));
         }
      } else {
         flag = 1;
      }

      out.writeByte(flag);
      if (this.isCompressed()) {
         PayloadStream tmp;
         if (flag == 1) {
            tmp = (PayloadStream)this.decompress();
            tmp.writeLengthAndData(out);
         } else if (this.needToDecompressDueToInterop(out)) {
            tmp = (PayloadStream)this.decompress();
            tmp.writeLengthAndData(out);
         } else {
            this.flushCompressedMessageBody(out);
         }
      } else if ((flag & -128) != 0) {
         this.writeExternalCompressPayload(out, this.payload);
      } else {
         this.payload.writeLengthAndData(out);
      }

   }

   public final void decompressMessageBody() throws javax.jms.JMSException {
      if (this.isCompressed()) {
         try {
            this.payload = (PayloadStream)this.decompress();
         } catch (IOException var5) {
            throw new JMSException(JMSClientExceptionLogger.logErrorDecompressMessageBodyLoggable().getMessage(), var5);
         } finally {
            this.cleanupCompressedMessageBody();
         }

      }
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      super.readExternal(in);
      byte unmaskedVersion = in.readByte();
      byte vrsn = (byte)(unmaskedVersion & 127);
      if (vrsn >= 1 && vrsn <= 2) {
         if ((unmaskedVersion & -128) != 0) {
            this.readExternalCompressedMessageBody(in);
         } else {
            this.payload = (PayloadStream)PayloadFactoryImpl.createPayload((InputStream)in);
         }

      } else {
         throw JMSUtilities.versionIOException(vrsn, 1, 2);
      }
   }

   public MessageImpl copy() throws javax.jms.JMSException {
      ObjectMessageImpl omi = new ObjectMessageImpl();
      this.copy(omi);

      try {
         if (this.payload == null && this.object != null) {
            BufferOutputStream bos = PayloadFactoryImpl.createOutputStream();
            ObjectOutputStream os = new ObjectOutputStream2(bos);
            os.writeObject(this.object);
            os.flush();
            omi.payload = (PayloadStream)bos.moveToPayload();
         } else if (this.payload != null) {
            omi.payload = this.payload.copyPayloadWithoutSharedStream();
            omi.payloadCopyOnWrite = this.payloadCopyOnWrite = true;
         } else {
            omi.payload = null;
         }

         omi.object = null;
      } catch (IOException var4) {
      }

      omi.setBodyWritable(false);
      omi.setPropertiesWritable(false);
      return omi;
   }

   public long getPayloadSize() {
      if (this.isCompressed()) {
         return (long)this.getCompressedMessageBodySize();
      } else if (super.bodySize != -1L) {
         return super.bodySize;
      } else {
         return this.payload != null ? (super.bodySize = (long)this.payload.getLength()) : (super.bodySize = 0L);
      }
   }

   public PayloadStream getPayload() {
      return this.payload;
   }

   public byte[] getBodyBytes() throws javax.jms.JMSException {
      if (this.payload != null) {
         Payload localPayload = this.payload;

         try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            localPayload.writeTo(baos);
            baos.flush();
            return baos.toByteArray();
         } catch (IOException var3) {
            throw new JMSException(var3);
         }
      } else {
         return new byte[0];
      }
   }

   public void setBodyBytes(PayloadStream value) {
      this.payload = value;
      this.object = null;
   }

   public PayloadStream getMessageBody() throws javax.jms.JMSException {
      if (!this.isCompressed()) {
         return this.payload;
      } else {
         try {
            return (PayloadStream)this.decompress();
         } catch (IOException var2) {
            throw new JMSException(JMSClientExceptionLogger.logErrorDecompressMessageBodyLoggable().getMessage(), var2);
         }
      }
   }

   static {
      try {
         String property = System.getProperty("weblogic.store.qa.StoreTest");
         if (property != null) {
            String trueValue = "true";
            testStoreExceptionEnabled = trueValue.equalsIgnoreCase(property);
         }
      } catch (Throwable var2) {
         if (!(var2 instanceof AccessControlException)) {
            var2.printStackTrace();
         }
      }

   }

   final class ObjectOutputStreamPeerInfoable extends ObjectOutputStream2 implements PeerInfoable {
      private PeerInfo peerInfo;

      ObjectOutputStreamPeerInfoable(OutputStream os, PeerInfo peerInfo) throws IOException, StreamCorruptedException {
         super(os);
         this.peerInfo = peerInfo;
      }

      public PeerInfo getPeerInfo() {
         return this.peerInfo;
      }
   }

   class ObjectOutputStream2 extends ObjectOutputStream implements WLObjectOutput {
      private boolean canReplace = true;

      ObjectOutputStream2(OutputStream os) throws IOException, StreamCorruptedException {
         super(os);
         if (KernelStatus.isApplet()) {
            this.canReplace = false;
         } else {
            this.enableReplaceObject(true);
         }

      }

      boolean canReplace() {
         return this.canReplace;
      }

      protected Object replaceObject(Object o) throws IOException {
         return ObjectMessageImpl.REPLACER != null ? ObjectMessageImpl.REPLACER.replaceObject(o) : o;
      }

      public final void writeObjectWL(Object o) throws IOException {
         this.writeObject(o);
      }

      public final void writeString(String s) throws IOException {
         this.writeObject(s);
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

      public final void writeBytes(byte[] val, int off, int len) throws IOException {
         byte[] b = new byte[len];
         System.arraycopy(val, off, b, 0, len);
         this.writeObject(b);
      }

      public final void writeArrayOfObjects(Object[] aoo) throws IOException {
         this.writeObject(aoo);
      }

      public final void writeAbbrevString(String s) throws IOException {
         this.writeObject(s);
      }

      public final void writeImmutable(Object o) throws IOException {
         this.writeObject(o);
      }
   }

   final class ObjectInputStream2 extends FilteringObjectInputStream implements WLObjectInput {
      private boolean canResolve = true;

      ObjectInputStream2(InputStream is) throws IOException, StreamCorruptedException {
         super(is);
         if (KernelStatus.isApplet()) {
            this.canResolve = false;
         } else {
            this.enableResolveObject(true);
         }

      }

      boolean canResolve() {
         return this.canResolve;
      }

      protected Class resolveClass(ObjectStreamClass v) throws IOException, ClassNotFoundException {
         this.checkLegacyBlacklistIfNeeded(v.getName());
         Class cls = null;
         if (KernelStatus.isApplet()) {
            cls = Class.forName(v.getName());
         } else {
            ClassLoader ccl = Thread.currentThread().getContextClassLoader();

            try {
               cls = Class.forName(v.getName(), true, ccl);
            } catch (ClassNotFoundException var5) {
               cls = super.resolveClass(v);
            }
         }

         this.validateReturnType(cls);
         return cls;
      }

      protected Class resolveProxyClass(String[] interfaces) throws IOException, ClassNotFoundException {
         if (KernelStatus.isApplet()) {
            return super.resolveProxyClass(interfaces);
         } else {
            ClassLoader ccl = Thread.currentThread().getContextClassLoader();
            ClassLoader nonPublicLoader = null;
            boolean hasNonPublicInterface = false;
            Class[] classObjs = new Class[interfaces.length];

            try {
               for(int i = 0; i < interfaces.length; ++i) {
                  Class cl = Class.forName(interfaces[i], false, ccl);
                  if ((cl.getModifiers() & 1) == 0) {
                     if (hasNonPublicInterface) {
                        if (nonPublicLoader != cl.getClassLoader()) {
                           throw new IllegalAccessError("conflicting non-public interface class loaders");
                        }
                     } else {
                        nonPublicLoader = cl.getClassLoader();
                        hasNonPublicInterface = true;
                     }
                  }

                  classObjs[i] = cl;
               }

               return Proxy.getProxyClass(hasNonPublicInterface ? nonPublicLoader : ccl, classObjs);
            } catch (IllegalArgumentException | ClassNotFoundException var8) {
               return super.resolveProxyClass(interfaces);
            }
         }
      }

      protected Object resolveObject(Object o) throws IOException {
         return ObjectMessageImpl.REPLACER != null ? ObjectMessageImpl.REPLACER.resolveObject(o) : o;
      }

      public final Object readObjectWL() throws IOException, ClassNotFoundException {
         return this.readObject();
      }

      public final Object readObjectWLValidated(Class returnType) throws IOException, ClassNotFoundException {
         return this.readObjectValidated(returnType);
      }

      public final String readString() throws IOException {
         try {
            return (String)this.readObject();
         } catch (ClassNotFoundException var2) {
            throw new IOException(var2.toString());
         }
      }

      public final Date readDate() throws IOException {
         try {
            return (Date)this.readObject();
         } catch (ClassNotFoundException var2) {
            throw new IOException(var2.toString());
         }
      }

      public final ArrayList readArrayList() throws IOException, ClassNotFoundException {
         return (ArrayList)this.readObject();
      }

      public final Properties readProperties() throws IOException {
         try {
            return (Properties)this.readObject();
         } catch (ClassNotFoundException var2) {
            throw new IOException(var2.toString());
         }
      }

      public final byte[] readBytes() throws IOException {
         try {
            return (byte[])((byte[])this.readObject());
         } catch (ClassNotFoundException var2) {
            throw new IOException(var2.toString());
         }
      }

      public final Object[] readArrayOfObjects() throws IOException, ClassNotFoundException {
         return (Object[])((Object[])this.readObject());
      }

      public final String readAbbrevString() throws IOException {
         try {
            return (String)this.readObject();
         } catch (ClassNotFoundException var2) {
            throw new IOException(var2.toString());
         }
      }

      public final Object readImmutable() throws IOException, ClassNotFoundException {
         return this.readObject();
      }
   }
}
