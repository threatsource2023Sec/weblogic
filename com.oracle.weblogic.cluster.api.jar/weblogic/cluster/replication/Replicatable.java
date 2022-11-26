package weblogic.cluster.replication;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.io.Serializable;

public interface Replicatable extends Serializable {
   Object DEFAULT_KEY = new NullKey();

   void becomePrimary(ROID var1);

   Object becomeSecondary(ROID var1) throws ApplicationUnavailableException;

   void becomeUnregistered(ROID var1);

   void update(ROID var1, Serializable var2);

   Object getKey();

   public static class NullKey implements Externalizable {
      private static final long serialVersionUID = 8517229973086899026L;
      private static final int hashCode = "NULL".hashCode();

      public int hashCode() {
         return hashCode;
      }

      public boolean equals(Object object) {
         if (this == object) {
            return true;
         } else {
            return object instanceof NullKey;
         }
      }

      public void writeExternal(ObjectOutput out) throws IOException {
      }

      public void readExternal(ObjectInput in) throws IOException {
      }

      public Object readResolve() throws ObjectStreamException, IOException {
         return Replicatable.DEFAULT_KEY;
      }
   }
}
