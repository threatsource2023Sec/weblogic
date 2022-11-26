package com.bea.httppubsub.internal;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.store.PersistentHandle;

public class PersistedClientRecord implements Externalizable {
   private static final short MAJOR_VERSION = 1;
   private static final short MINOR_VERSION = 0;
   private transient PersistentHandle handle = null;
   private String principalName = "";
   private long lastAccessTime = -1L;

   public String getPrincipalName() {
      return this.principalName;
   }

   public void setPrincipalName(String principalName) {
      this.principalName = principalName;
   }

   public long getLastAccessTime() {
      return this.lastAccessTime;
   }

   public void setLastAccessTime(long lastAccessTime) {
      this.lastAccessTime = lastAccessTime;
   }

   public PersistentHandle getHandle() {
      return this.handle;
   }

   public void setHandle(PersistentHandle handle) {
      this.handle = handle;
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      short major = in.readShort();
      short minor = in.readShort();
      if (major == 1 && minor == 0) {
         this.principalName = in.readUTF();
         this.lastAccessTime = in.readLong();
      }

   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeShort(1);
      out.writeShort(0);
      out.writeUTF(this.principalName);
      out.writeLong(this.lastAccessTime);
   }
}
