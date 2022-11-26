package weblogic.store.xa.map;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.StreamCorruptedException;
import weblogic.store.DefaultObjectHandler;
import weblogic.store.ObjectHandler;

class ObjectHandlerImpl implements ObjectHandler {
   private final ObjectHandler delegate;
   private static final int ENTRYRECORD = 555;

   ObjectHandlerImpl() {
      this.delegate = DefaultObjectHandler.THE_ONE;
   }

   ObjectHandlerImpl(ObjectHandler delegate) {
      this.delegate = delegate;
   }

   public void writeObject(ObjectOutput out, Object object) throws IOException {
      if (object instanceof Entry) {
         Entry e = (Entry)object;
         out.writeInt(555);
         e.write(out, this.delegate);
      } else {
         throw new IOException("unknown class " + object.getClass().getName());
      }
   }

   public Object readObject(ObjectInput in) throws IOException, ClassNotFoundException {
      int code = in.readInt();
      if (code != 555) {
         throw new StreamCorruptedException("code " + code);
      } else {
         Entry e = new Entry();
         e.read(in, this.delegate);
         return e;
      }
   }
}
