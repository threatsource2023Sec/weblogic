package weblogic.rmi.cluster;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.rmi.spi.HostID;

public final class Version implements Externalizable {
   private static final long serialVersionUID = -7944784153786101427L;
   private long version;

   public Version(long version) {
      this.version = version;
   }

   public int hashCode() {
      return (new Long(this.version)).hashCode();
   }

   public boolean equals(Object other) {
      if (other instanceof Version) {
         return this.version == ((Version)other).version;
      } else {
         return false;
      }
   }

   public long getVersion() {
      return this.version;
   }

   public String toString() {
      return "version " + this.version;
   }

   public void removeServer(HostID id) {
      this.version -= (long)id.hashCode();
   }

   public void addServer(HostID id) {
      this.version += (long)id.hashCode();
   }

   public Version() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeLong(this.version);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.version = in.readLong();
   }
}
