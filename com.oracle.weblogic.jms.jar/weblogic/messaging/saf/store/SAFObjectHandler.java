package weblogic.messaging.saf.store;

import java.io.Externalizable;
import java.io.IOException;
import java.util.HashMap;
import weblogic.messaging.saf.common.SAFConversationInfoImpl;
import weblogic.messaging.saf.internal.ReceivingAgentImpl;
import weblogic.messaging.saf.internal.SendingAgentImpl;
import weblogic.store.helper.StoreObjectHandler;

public final class SAFObjectHandler extends StoreObjectHandler {
   private static final Class[] CLASS_CLASS = new Class[]{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, SendingAgentImpl.class, ReceivingAgentImpl.class, SAFConversationInfoImpl.class, String.class};
   public static final short SAFHISTORYRECORD = 12;
   public static final short SAFCONVERSATIONID = 13;
   public static final short SAFID = 14;
   public static final short SENDINGAGENT = 15;
   public static final short RECEIVINGAGENT = 16;
   public static final short SAFCONVERSATION = 17;
   private static HashMap classToId = new HashMap();

   public final Class getClassForId(short id) {
      return CLASS_CLASS[id];
   }

   public final Short getIdForClass(Object o) {
      return (Short)classToId.get(o.getClass());
   }

   public final void checkIfClassRecognized(short id) throws IOException {
      if (id < 0 || id >= CLASS_CLASS.length || CLASS_CLASS[id] == null) {
         throw new IOException("Unrecognized class code " + id);
      }
   }

   protected boolean haveExternal(short s) {
      return 12 <= s;
   }

   protected Externalizable newExternal(short s) throws IOException {
      if (s < 16) {
         switch (s) {
            case 15:
               return new SendingAgentImpl();
         }
      }

      switch (s) {
         case 16:
            return new ReceivingAgentImpl();
         case 17:
            return new SAFConversationInfoImpl();
         default:
            assert false;

            throw new IOException("Unrecognized class code " + s);
      }
   }

   static {
      for(short i = 0; i < CLASS_CLASS.length; ++i) {
         classToId.put(CLASS_CLASS[i], new Short(i));
      }

   }
}
