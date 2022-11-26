package weblogic.cluster.replication;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.common.internal.ProxyClassResolvableChunkedObjectInputStream;
import weblogic.rmi.utils.io.RemoteObjectReplacer;
import weblogic.utils.io.Chunk;
import weblogic.utils.io.ChunkInputStreamAccess;
import weblogic.utils.io.ChunkOutput;
import weblogic.utils.io.ChunkedObjectInputStream;
import weblogic.utils.io.ChunkedObjectOutputStream;
import weblogic.utils.io.StringInput;
import weblogic.utils.io.StringOutput;

public final class ROObject implements Externalizable {
   private static final long serialVersionUID = -5018057544806950295L;
   private Map ros;
   private Map roVersions;
   private ResourceGroupKey resourceGroupKey;

   public ROObject() {
   }

   ROObject(Map ros, Map versionsMap, ResourceGroupKey resourceGroupKey) {
      this.ros = ros;
      this.roVersions = versionsMap;
      this.resourceGroupKey = resourceGroupKey;
   }

   final int getVersion(Object key) {
      Integer version = (Integer)this.roVersions.get(key);
      return version != null ? version : -1;
   }

   final Map getVersions() {
      return this.roVersions;
   }

   final Map getROS() {
      return this.ros;
   }

   final ResourceGroupKey getResourceGroupKey() {
      return this.resourceGroupKey;
   }

   public final void writeExternal(ObjectOutput out) throws IOException {
      int size = this.ros.size();
      out.writeInt(size);
      if (size > 0) {
         Iterator i = this.ros.keySet().iterator();

         while(i.hasNext()) {
            ChunkedObjectOutputStream costrm = new ChunkedObjectOutputStream();
            costrm.setReplacer(RemoteObjectReplacer.getReplacer());

            try {
               String key = (String)i.next();
               this.writeKey(out, key);
               Object val = this.ros.get(key);
               Chunk toWrite = null;
               costrm.writeObject(val);
               toWrite = costrm.getChunks();
               ((ChunkOutput)out).writeChunks(toWrite);
            } finally {
               costrm.reset();
            }
         }
      }

      out.writeObject(this.roVersions);
      ResourceGroupKeyImpl.writeKeyToStream(this.resourceGroupKey, out);
   }

   private void writeKey(ObjectOutput out, String str) throws IOException {
      if (out instanceof StringOutput) {
         StringOutput strout = (StringOutput)out;
         strout.writeUTF8(str);
      } else {
         out.writeUTF(str);
      }

   }

   public final void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int size = in.readInt();
      this.ros = new HashMap(size);

      for(int i = 0; i < size; ++i) {
         String key = this.readKey(in);
         int len = ((ChunkInputStreamAccess)in).readChunkLength();
         byte[] val = new byte[len];
         ((ChunkInputStreamAccess)in).readByteArray(val, 0, len);

         try {
            Replicatable value = this.deserializeReplicatable(val);
            this.ros.put(key, value);
         } catch (Exception var8) {
            if (ReplicationDebugLogger.isDebugEnabled()) {
               ReplicationDebugLogger.debug((String)("Unable to deserialize replicatable object associated with key: " + key + " when fetching."), (Throwable)var8);
            }
         }
      }

      this.roVersions = (Map)in.readObject();
      this.resourceGroupKey = ResourceGroupKeyImpl.readKeyFromStream(in);
   }

   private String readKey(ObjectInput in) throws IOException {
      if (in instanceof StringInput) {
         StringInput strin = (StringInput)in;
         return strin.readUTF8();
      } else {
         return in.readUTF();
      }
   }

   private Replicatable deserializeReplicatable(byte[] buf) throws IOException, ClassNotFoundException {
      Chunk head = null;
      ChunkedObjectInputStream cistrm = null;

      Replicatable var4;
      try {
         head = Chunk.createSharedChunk(buf, buf.length);
         cistrm = new ProxyClassResolvableChunkedObjectInputStream(head, 0);
         cistrm.setReplacer(RemoteObjectReplacer.getReplacer());
         var4 = (Replicatable)cistrm.readObject();
      } finally {
         if (cistrm != null) {
            cistrm.close();
         } else if (head != null) {
            Chunk.releaseChunks(head);
         }

      }

      return var4;
   }
}
