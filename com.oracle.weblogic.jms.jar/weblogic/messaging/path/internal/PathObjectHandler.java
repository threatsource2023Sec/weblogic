package weblogic.messaging.path.internal;

import java.io.Externalizable;
import java.io.IOException;
import weblogic.messaging.path.Key;
import weblogic.messaging.path.helper.KeySerializable;
import weblogic.messaging.path.helper.KeyString;
import weblogic.messaging.path.helper.MemberString;
import weblogic.store.ObjectHandler;
import weblogic.store.helper.StoreObjectHandler;

public final class PathObjectHandler extends StoreObjectHandler {
   public static final short KEYSTRING = 1;
   public static final short MEMBERSTRING = 2;
   public static final short KEYSERIALIZABLE = 3;
   private static Object keyStringClass = (new KeyString()).getClass();
   private static Object memberStringClass = (new MemberString()).getClass();
   private static Object keySerializableClass = (new KeySerializable()).getClass();
   private static final Short shortKey = new Short((short)1);
   private static final Short shortMember = new Short((short)2);
   private static final Short shortSerializable = new Short((short)3);
   private static final ObjectHandler[] registeredHandlers;

   public static void setObjectHandler(byte index, ObjectHandler oh) {
      registeredHandlers[index] = oh;
   }

   public static ObjectHandler getObjectHandler(byte index) {
      return registeredHandlers[index];
   }

   public final Class getClassForId(short id) {
      return null;
   }

   public final Short getIdForClass(Object o) {
      Object o = o.getClass();
      if (o == keyStringClass) {
         return shortKey;
      } else if (o == memberStringClass) {
         return shortMember;
      } else {
         return o == keySerializableClass ? shortSerializable : null;
      }
   }

   public final void checkIfClassRecognized(short id) throws IOException {
      throw new IOException("Unrecognized class code " + id);
   }

   protected boolean haveExternal(short s) {
      switch (s) {
         case 1:
         case 2:
         case 3:
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
            return new MemberString();
         case 3:
            return new KeySerializable();
         default:
            assert false;

            throw new IOException("Unrecognized class code " + s);
      }
   }

   static {
      registeredHandlers = new ObjectHandler[Key.RESERVED_SUBSYSTEMS.size()];
      PathObjectHandler one = new PathObjectHandler();

      for(byte i = 0; i < registeredHandlers.length; ++i) {
         if (i == 1) {
            registeredHandlers[i] = null;
         } else {
            registeredHandlers[i] = one;
         }
      }

   }
}
