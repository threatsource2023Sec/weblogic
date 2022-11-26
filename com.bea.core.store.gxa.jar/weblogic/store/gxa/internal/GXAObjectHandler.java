package weblogic.store.gxa.internal;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.StreamCorruptedException;
import weblogic.store.ObjectHandler;

public final class GXAObjectHandler implements ObjectHandler {
   private static final int PREPARERECORD = 555;

   public void writeObject(ObjectOutput out, Object object) throws IOException {
      if (object instanceof GXATwoPhaseRecord) {
         out.writeInt(555);
         ((Externalizable)object).writeExternal(out);
      } else {
         throw new IOException("unknown class " + object.getClass().getName());
      }
   }

   public Object readObject(ObjectInput in) throws IOException, ClassNotFoundException {
      int code = in.readInt();
      switch (code) {
         case 555:
            Externalizable ext = new GXATwoPhaseRecord();
            ext.readExternal(in);
            return ext;
         default:
            throw new StreamCorruptedException("code " + code);
      }
   }
}
