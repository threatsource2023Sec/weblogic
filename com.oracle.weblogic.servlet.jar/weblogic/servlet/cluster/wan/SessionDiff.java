package weblogic.servlet.cluster.wan;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;
import weblogic.servlet.internal.session.AttributeWrapperUtils;
import weblogic.servlet.spi.WebServerRegistry;
import weblogic.utils.io.UnsyncByteArrayOutputStream;

public final class SessionDiff implements Externalizable {
   static final long serialVersionUID = 8310551219025541051L;
   private HashMap newAttributes = new HashMap(11);
   private HashMap updateAttributes = new HashMap(11);
   private HashMap newInternalAttributes = new HashMap(3);
   private HashMap updateInternalAttributes = new HashMap(3);
   private int versionCounter;
   private boolean serialized;

   public final synchronized SessionDiff cloneAndClear() {
      SessionDiff clone = new SessionDiff();
      clone.updateAttributes = (HashMap)this.updateAttributes.clone();
      clone.updateInternalAttributes = (HashMap)this.updateInternalAttributes.clone();
      clone.newAttributes = (HashMap)this.newAttributes.clone();
      clone.newInternalAttributes = (HashMap)this.newInternalAttributes.clone();
      ++this.versionCounter;
      clone.versionCounter = this.versionCounter;
      this.updateAttributes.clear();
      this.updateInternalAttributes.clear();
      this.newAttributes.clear();
      this.newInternalAttributes.clear();
      return clone;
   }

   public final int getVersionCount() {
      return this.versionCounter;
   }

   public final synchronized void setAttribute(String key, Object val, boolean newAttribute, boolean internal) {
      val = AttributeWrapperUtils.resolveEJBHandleIfNecessary(key, val);
      HashMap inserts;
      if (!newAttribute && !this.newAttributes.containsKey(key)) {
         inserts = internal ? this.updateInternalAttributes : this.updateAttributes;
         inserts.put(key, val);
      } else {
         inserts = internal ? this.newInternalAttributes : this.newAttributes;
         inserts.put(key, val);
      }
   }

   HashMap getNewAttributes() {
      return this.newAttributes;
   }

   HashMap getUpdateAttributes() {
      return this.updateAttributes;
   }

   HashMap getNewInternalAttributes() {
      return this.newInternalAttributes;
   }

   HashMap getUpdateInternalAttributes() {
      return this.updateInternalAttributes;
   }

   public HashMap getAttributes() {
      HashMap attributes = new HashMap();
      HashMap newAttributesClone = null;
      HashMap updatedAttributesClone = null;
      synchronized(this) {
         if (this.newAttributes.size() > 0) {
            newAttributesClone = (HashMap)this.newAttributes.clone();
         }

         if (this.updateAttributes.size() > 0) {
            updatedAttributesClone = (HashMap)this.updateAttributes.clone();
         }
      }

      Iterator i;
      Object key;
      if (newAttributesClone != null) {
         i = newAttributesClone.keySet().iterator();

         while(i.hasNext()) {
            key = i.next();
            attributes.put(key, newAttributesClone.get(key));
         }
      }

      if (updatedAttributesClone != null) {
         i = updatedAttributesClone.keySet().iterator();

         while(i.hasNext()) {
            key = i.next();
            attributes.put(key, updatedAttributesClone.get(key));
         }
      }

      return attributes;
   }

   public HashMap getInternalAttributes() {
      HashMap internalAttributes = new HashMap();
      HashMap newInternalAttributesClone = null;
      HashMap updateInternalAttributesClone = null;
      synchronized(this) {
         if (this.newInternalAttributes.size() > 0) {
            newInternalAttributesClone = (HashMap)this.newInternalAttributes.clone();
         }

         if (this.updateInternalAttributes.size() > 0) {
            updateInternalAttributesClone = (HashMap)this.updateInternalAttributes.clone();
         }
      }

      Iterator i;
      Object key;
      if (newInternalAttributesClone != null) {
         i = newInternalAttributesClone.keySet().iterator();

         while(i.hasNext()) {
            key = i.next();
            internalAttributes.put(key, newInternalAttributesClone.get(key));
         }
      }

      if (updateInternalAttributesClone != null) {
         i = updateInternalAttributesClone.keySet().iterator();

         while(i.hasNext()) {
            key = i.next();
            internalAttributes.put(key, updateInternalAttributesClone.get(key));
         }
      }

      return internalAttributes;
   }

   public void setVersionCounter(int versionCounter) {
      this.versionCounter = versionCounter;
   }

   byte[] getBytesForDB(Object object) throws IOException {
      byte[] b;
      if (this.serialized) {
         ByteBuffer buf = (ByteBuffer)object;
         buf.rewind();
         b = new byte[buf.remaining()];
         buf.get(b);
      } else {
         b = (byte[])passivateObject(object);
      }

      return b;
   }

   static byte[] passivateObject(Object object) throws IOException {
      UnsyncByteArrayOutputStream baos = new UnsyncByteArrayOutputStream();
      ObjectOutputStream out = WebServerRegistry.getInstance().getContainerSupportProvider().getObjectOutputStream(baos);
      out.writeObject(object);
      out.flush();
      return baos.toByteArray();
   }

   private synchronized void passivateMap(ObjectOutput oo, HashMap map) throws IOException {
      int size = map.size();
      oo.writeInt(size);
      if (size > 0) {
         Iterator i = map.keySet().iterator();

         while(i.hasNext()) {
            String key = (String)i.next();
            byte[] temp = passivateObject(map.get(key));
            oo.writeUTF(key);
            oo.writeInt(temp.length);
            oo.write(temp);
         }
      }

   }

   private void readMap(ObjectInput in, HashMap map) throws IOException {
      int mapSize = in.readInt();
      if (mapSize > 0) {
         int i = 0;

         while(i++ < mapSize) {
            String key = in.readUTF();
            int size = in.readInt();
            byte[] tmp = new byte[size];
            ByteBuffer buf = ByteBuffer.allocateDirect(size);

            int n;
            for(int readCount = 0; readCount < size; readCount += n) {
               n = in.read(tmp, readCount, size - readCount);
               if (n == -1) {
                  throw new IOException("Encountered EOF during deserialization");
               }
            }

            buf.put(tmp, 0, size);
            map.put(key, buf);
         }
      }

   }

   public void writeExternal(ObjectOutput oo) throws IOException {
      oo.writeInt(this.versionCounter);
      this.passivateMap(oo, this.newAttributes);
      this.passivateMap(oo, this.updateAttributes);
      this.passivateMap(oo, this.newInternalAttributes);
      this.passivateMap(oo, this.updateInternalAttributes);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.versionCounter = in.readInt();
      this.readMap(in, this.newAttributes);
      this.readMap(in, this.updateAttributes);
      this.readMap(in, this.newInternalAttributes);
      this.readMap(in, this.updateInternalAttributes);
      this.serialized = true;
   }
}
