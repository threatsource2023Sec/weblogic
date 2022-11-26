package weblogic.cluster;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public final class ServiceRetract implements Externalizable {
   private static final long serialVersionUID = 1117899013903986756L;
   private int id;
   private boolean ignoreRetract;

   public ServiceRetract(int id) {
      this.id = id;
   }

   public ServiceRetract(int id, boolean ignoreRetract) {
      this(id);
      this.ignoreRetract = ignoreRetract;
   }

   public int id() {
      return this.id;
   }

   public boolean ignoreRetract() {
      return this.ignoreRetract;
   }

   public void writeExternal(ObjectOutput oo) throws IOException {
      oo.writeInt(this.id);
      oo.writeBoolean(this.ignoreRetract);
   }

   public void readExternal(ObjectInput oi) throws IOException {
      this.id = oi.readInt();
      this.ignoreRetract = oi.readBoolean();
   }

   public ServiceRetract() {
   }
}
