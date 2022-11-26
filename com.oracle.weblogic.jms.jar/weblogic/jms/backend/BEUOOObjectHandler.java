package weblogic.jms.backend;

import java.io.Externalizable;
import java.io.IOException;
import weblogic.messaging.path.helper.KeyString;
import weblogic.store.helper.StoreObjectHandler;

public final class BEUOOObjectHandler extends StoreObjectHandler {
   private static Object KEY_STRING_CLASS = (new KeyString()).getClass();
   private static Object MEMBER_CLASS = (new BEUOOMember()).getClass();
   public static final short KEYSTRING = 1;
   public static final short UOOMEMBER = 2;
   private static final Short SHORT_KEY = new Short((short)1);
   private static final Short SHORT_MEMBER = new Short((short)2);

   public final Class getClassForId(short id) {
      if (id == SHORT_KEY) {
         return KeyString.class;
      } else {
         return id == SHORT_MEMBER ? BEUOOMember.class : null;
      }
   }

   public final Short getIdForClass(Object o) {
      Object o = o.getClass();
      if (o == KEY_STRING_CLASS) {
         return SHORT_KEY;
      } else {
         return o == MEMBER_CLASS ? SHORT_MEMBER : null;
      }
   }

   public final void checkIfClassRecognized(short id) throws IOException {
      if (id < 1 || id > 2) {
         throw new IOException("Unrecognized class code " + id);
      }
   }

   protected boolean haveExternal(short s) {
      switch (s) {
         case 1:
         case 2:
            return true;
         default:
            return false;
      }
   }

   protected Externalizable newExternal(short s) throws IOException {
      switch (s) {
         case 1:
            return new KeyString();
         case 2:
            return new BEUOOMember();
         default:
            assert false;

            throw new IOException("Unrecognized class code " + s);
      }
   }
}
