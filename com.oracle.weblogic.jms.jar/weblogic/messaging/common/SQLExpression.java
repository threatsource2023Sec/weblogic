package weblogic.messaging.common;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class SQLExpression implements Externalizable {
   public static final long serialVersionUID = 7574805445195313601L;
   private static final int EXTERNAL_VERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int HAS_SELECTOR_FLAG = 256;
   protected String selector;

   public SQLExpression() {
   }

   public SQLExpression(String selector) {
      this.setSelector(selector);
   }

   public boolean isNull() {
      return this.selector == null;
   }

   public String getSelector() {
      return this.selector;
   }

   public void setSelector(String selector) {
      if (selector != null) {
         selector = selector.trim();
         if (selector.length() == 0) {
            selector = null;
         }
      }

      this.selector = selector;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int flags = 1;
      if (this.selector != null) {
         flags |= 256;
      }

      out.writeInt(flags);
      if (this.selector != null) {
         out.writeUTF(this.selector);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int flags = in.readInt();
      if ((flags & 255) != 1) {
         throw new IOException("External version mismatch");
      } else {
         if ((flags & 256) != 0) {
            this.selector = in.readUTF();
         }

      }
   }
}
