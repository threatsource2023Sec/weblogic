package weblogic.servlet.internal.session;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.rmi.ConnectException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.application.AppClassLoaderManager;
import weblogic.cluster.ClusterServices;
import weblogic.cluster.replication.ReplicationSizeDebugLogger;
import weblogic.common.internal.PassivationUtils;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.common.internal.ProxyClassResolvableChunkedObjectInputStream;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.management.configuration.ClusterMBean;
import weblogic.rmi.utils.io.RemoteObjectReplacer;
import weblogic.servlet.spi.ClusterProvider;
import weblogic.servlet.spi.WebServerRegistry;
import weblogic.utils.PlatformConstants;
import weblogic.utils.classloaders.Annotation;
import weblogic.utils.io.Chunk;
import weblogic.utils.io.ChunkInputStreamAccess;
import weblogic.utils.io.ChunkOutput;
import weblogic.utils.io.ChunkedObjectInputStream;
import weblogic.utils.io.ChunkedObjectOutputStream;
import weblogic.utils.io.StringInput;
import weblogic.utils.io.StringOutput;

public class ReplicatedSessionChange implements Externalizable {
   private static final long serialVersionUID = -2864760712171499570L;
   protected final Map attributeChanges = new HashMap();
   protected final Map internalChanges = new HashMap();
   protected int maxInactiveInterval;
   protected long lastAccessedTime;
   protected long timeOnPrimaryAtUpdate;
   protected boolean modified = false;
   protected String annotation;
   protected static final DebugLogger DEBUG_SESSIONS = DebugLogger.getDebugLogger("DebugHttpSessions");
   private transient boolean useLazyDeserialization = false;
   private static final ClusterMBean clusterMbean = WebServerRegistry.getInstance().getClusterMBean();
   private static final ClusterProvider clusterProvider = WebServerRegistry.getInstance().getClusterProvider();

   public ReplicatedSessionChange() {
      String classloaderAnnotation = ReplicatedSessionData.getAnnotation();
      this.annotation = classloaderAnnotation == null ? "" : classloaderAnnotation;
   }

   synchronized void init(int mii, long lat) {
      this.maxInactiveInterval = mii;
      this.lastAccessedTime = lat;
      this.modified = true;
      String classloaderAnnotation = ReplicatedSessionData.getAnnotation();
      this.annotation = classloaderAnnotation == null ? "" : classloaderAnnotation;
   }

   void clear() {
      this.attributeChanges.clear();
      this.internalChanges.clear();
      this.modified = false;
   }

   boolean isModified() {
      return this.modified;
   }

   void setTimeOnPrimaryAtUpdate(long timeOnPrimary) {
      this.timeOnPrimaryAtUpdate = timeOnPrimary;
   }

   long getTimeOnPrimaryAtUpdate() {
      return this.timeOnPrimaryAtUpdate;
   }

   long getLastAccessTime() {
      return this.lastAccessedTime;
   }

   int getMaxInActiveInterval() {
      return this.maxInactiveInterval;
   }

   Map getAttributeChanges() {
      return this.attributeChanges;
   }

   Map getInternalAttributeChanges() {
      return this.internalChanges;
   }

   void addAttributeChange(String name, Object value, int maxInactiveInterval, long lastAccessedTime) {
      value = AttributeWrapperUtils.resolveEJBHandleIfNecessary(name, value);
      synchronized(this) {
         this.attributeChanges.put(name, value);
         this.init(maxInactiveInterval, lastAccessedTime);
      }
   }

   public void addInternalAttributeChange(String name, Object value, int maxInactiveInterval, long lastAccessedTime) {
      value = AttributeWrapperUtils.resolveEJBHandleIfNecessary(name, value);
      synchronized(this) {
         this.internalChanges.put(name, value);
         this.init(maxInactiveInterval, lastAccessedTime);
      }
   }

   public String toString() {
      return !ReplicationSizeDebugLogger.isDebugEnabled() ? "[Changes:" + this.attributeChanges.size() + " internalChanges:" + this.internalChanges.size() + " annotation: " + this.annotation + " maxInactiveInterval: " + this.maxInactiveInterval + " lastAccessedTime: " + this.lastAccessedTime + "]" : this.appendSizeDetails(super.toString());
   }

   private void writeString(ObjectOutput out, String str) throws IOException {
      if (out instanceof StringOutput) {
         StringOutput strout = (StringOutput)out;
         strout.writeUTF8(str);
      } else {
         out.writeUTF(str);
      }

   }

   private String readString(ObjectInput in) throws IOException {
      if (in instanceof StringInput) {
         StringInput strin = (StringInput)in;
         return strin.readUTF8();
      } else {
         return in.readUTF();
      }
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      HashMap attributeChangesCopy;
      HashMap internalChangesCopy;
      int maxInactiveIntervalCopy;
      long lastAccessedTimeCopy;
      boolean modifiedCopy;
      synchronized(this) {
         attributeChangesCopy = new HashMap(this.attributeChanges);
         internalChangesCopy = new HashMap(this.internalChanges);
         maxInactiveIntervalCopy = this.maxInactiveInterval;
         lastAccessedTimeCopy = this.lastAccessedTime;
         modifiedCopy = this.modified;
         this.clear();
      }

      this.writeString(out, this.annotation);
      int size = attributeChangesCopy.size();
      out.writeInt(size);
      if (size > 0) {
         boolean useBackwardCompatibilityMode = false;
         ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
         String partitionName = null;
         if (cic != null) {
            partitionName = cic.getPartitionName();
         }

         ClusterServices clusterServices = clusterProvider.getClusterService();
         boolean lazyDeserializationEnabled = partitionName != null ? clusterServices.isSessionLazyDeserializationEnabled(partitionName) : clusterServices.isSessionLazyDeserializationEnabled();
         if (out instanceof PeerInfoable) {
            PeerInfo peerInfo = ((PeerInfoable)out).getPeerInfo();
            useBackwardCompatibilityMode = this.checkCompatibility(peerInfo, lazyDeserializationEnabled);
         }

         if (!useBackwardCompatibilityMode && out instanceof ChunkOutput) {
            out.writeBoolean(lazyDeserializationEnabled);
            if (lazyDeserializationEnabled) {
               this.useLazyDeserialization = true;
               this.writeMapAsChunksToStream(attributeChangesCopy, out);
            } else {
               this.writeMapKeysAndValuesToStream(attributeChangesCopy, out);
            }
         } else {
            this.writeMapKeysAndValuesToStream(attributeChangesCopy, out);
         }
      }

      size = internalChangesCopy.size();
      out.writeInt(size);
      if (size > 0) {
         this.writeMapKeysAndValuesToStream(internalChangesCopy, out);
      }

      out.writeInt(maxInactiveIntervalCopy);
      out.writeLong(lastAccessedTimeCopy);
      out.writeLong(this.timeOnPrimaryAtUpdate);
      out.writeBoolean(modifiedCopy);
   }

   private void writeMapAsChunksToStream(Map map, ObjectOutput out) throws IOException {
      ChunkedObjectOutputStream costrm = new ChunkedObjectOutputStream();
      costrm.setReplacer(RemoteObjectReplacer.getReplacer());
      Iterator var4 = map.keySet().iterator();

      while(var4.hasNext()) {
         String key = (String)var4.next();

         try {
            this.writeString(out, key);
            costrm.writeObject(map.get(key));
            ((ChunkOutput)out).writeChunks(costrm.getChunks());
         } finally {
            costrm.reset();
         }
      }

   }

   private void writeMapKeysAndValuesToStream(Map map, ObjectOutput out) throws IOException {
      Iterator var3 = map.keySet().iterator();

      while(var3.hasNext()) {
         String key = (String)var3.next();
         this.writeString(out, key);
         out.writeObject(map.get(key));
      }

   }

   private void readMapKeysAndValuesFromStream(Map map, int size, ObjectInput in) throws IOException, ClassNotFoundException {
      for(int i = 0; i < size; ++i) {
         String key = this.readString(in);
         Object val = in.readObject();
         map.put(key, val);
      }

   }

   private void readMapFromChunksInInputStream(Map map, int size, ObjectInput in) throws IOException, ClassNotFoundException {
      for(int i = 0; i < size; ++i) {
         String key = this.readString(in);
         int len = ((ChunkInputStreamAccess)in).readChunkLength();
         byte[] val = new byte[len];
         ((ChunkInputStreamAccess)in).readByteArray(val, 0, len);
         if (val.length == 1 && val[0] == 112) {
            map.put(key, (Object)null);
         } else if (!this.useLazyDeserialization) {
            deserializeAttribute(map, key, val);
         } else {
            map.put(key, val);
         }
      }

   }

   boolean checkCompatibility(PeerInfo peerInfo, boolean useLazyDeserialization) {
      boolean useCompatibilityMode;
      if (peerInfo.compareTo(PeerInfo.VERSION_1221) >= 0) {
         useCompatibilityMode = false;
      } else if (peerInfo.compareTo(PeerInfo.VERSION_1033) > 0) {
         useCompatibilityMode = !useLazyDeserialization;
      } else {
         useCompatibilityMode = true;
      }

      return useCompatibilityMode;
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      ClassLoader loader = null;
      this.annotation = this.readString(in);
      AppClassLoaderManager manager = clusterProvider.getApplicationClassLoaderManager();
      Annotation ann = new Annotation(this.annotation);
      loader = manager.findLoader(ann);
      Thread thread = Thread.currentThread();
      ClassLoader origCL = thread.getContextClassLoader();
      if (loader != null) {
         thread.setContextClassLoader(loader);
      }

      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
      String partitionName = null;
      if (cic != null) {
         partitionName = cic.getPartitionName();
      }

      try {
         ManagedInvocationContext mic = clusterProvider.getClusterServicesInvocationContext().setInvocationContext(ann.getApplicationName(), ann.getModuleName());
         Throwable var10 = null;

         try {
            this.clear();
            int size = in.readInt();
            if (size > 0) {
               boolean useBackwardCompatibilityMode = false;
               if (in instanceof PeerInfoable) {
                  PeerInfo peerInfo = ((PeerInfoable)in).getPeerInfo();
                  if (peerInfo == null) {
                     throw new ConnectException("Couldn't read Replicated Session Change for " + partitionName + " - it is likely that the connection has already been shut down");
                  }

                  ClusterServices clusterServices = clusterProvider.getClusterService();
                  boolean isSessionLazyDeserialization = partitionName != null ? clusterServices.isSessionLazyDeserializationEnabled(partitionName) : clusterServices.isSessionLazyDeserializationEnabled();
                  useBackwardCompatibilityMode = this.checkCompatibility(peerInfo, isSessionLazyDeserialization);
               }

               if (!useBackwardCompatibilityMode && in instanceof ChunkInputStreamAccess) {
                  this.useLazyDeserialization = in.readBoolean();
                  if (!this.useLazyDeserialization) {
                     this.readMapKeysAndValuesFromStream(this.attributeChanges, size, in);
                  } else {
                     this.readMapFromChunksInInputStream(this.attributeChanges, size, in);
                  }
               } else {
                  this.readMapKeysAndValuesFromStream(this.attributeChanges, size, in);
               }
            }

            size = in.readInt();
            if (size > 0) {
               this.readMapKeysAndValuesFromStream(this.internalChanges, size, in);
            }

            this.maxInactiveInterval = in.readInt();
            this.lastAccessedTime = in.readLong();
            this.timeOnPrimaryAtUpdate = in.readLong();
            this.modified = in.readBoolean();
         } catch (Throwable var30) {
            var10 = var30;
            throw var30;
         } finally {
            if (mic != null) {
               if (var10 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var29) {
                     var10.addSuppressed(var29);
                  }
               } else {
                  mic.close();
               }
            }

         }
      } finally {
         if (loader != null) {
            thread.setContextClassLoader(origCL);
         }

      }

   }

   private static void deserializeAttribute(Map map, String name, byte[] buf) {
      Chunk head = null;
      ChunkedObjectInputStream cistrm = null;

      try {
         head = Chunk.createSharedChunk(buf, buf.length);
         cistrm = new ProxyClassResolvableChunkedObjectInputStream(head, 0);
         cistrm.setReplacer(RemoteObjectReplacer.getReplacer());
         Object value = cistrm.readObject();
         map.put(name, value);
      } catch (Exception var9) {
         if (DEBUG_SESSIONS.isDebugEnabled()) {
            DEBUG_SESSIONS.debug("Unable to deserialize attribute value for key: " + name, var9);
         }
      } finally {
         if (cistrm != null) {
            cistrm.close();
         } else if (head != null) {
            Chunk.releaseChunks(head);
         }

      }

   }

   public boolean isUseLazyDeserialization() {
      return this.useLazyDeserialization;
   }

   private String appendSizeDetails(String data) {
      StringBuilder sb = (new StringBuilder(data)).append(PlatformConstants.EOL);
      sb.append("  Size of ReplicatedSessionChange is  : ");

      try {
         sb.append(PassivationUtils.toByteArray(this).length);
      } catch (Exception var6) {
      }

      sb.append(" Attributes are as below ").append(PlatformConstants.EOL);
      Iterator iterator = this.attributeChanges.keySet().iterator();

      while(iterator.hasNext()) {
         try {
            Object obj = iterator.next();
            sb.append(" Attribute Name  : ").append(obj).append(" Attribute Size : ").append(PassivationUtils.toByteArray(this.attributeChanges.get(obj)).length).append(PlatformConstants.EOL);
         } catch (Exception var5) {
         }
      }

      return sb.toString();
   }
}
