package weblogic.jms.store;

import java.io.Externalizable;
import java.io.IOException;
import java.util.HashMap;
import weblogic.jms.common.BytesMessageImpl;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.HdrMessageImpl;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSMessageId;
import weblogic.jms.common.JMSServerId;
import weblogic.jms.common.MapMessageImpl;
import weblogic.jms.common.ObjectMessageImpl;
import weblogic.jms.common.StreamMessageImpl;
import weblogic.jms.common.TextMessageImpl;
import weblogic.jms.common.XMLMessageImpl;
import weblogic.jms.forwarder.dd.internal.DDLBTableImpl;
import weblogic.store.helper.StoreObjectHandler;

public final class JMSObjectHandler extends StoreObjectHandler {
   private static final String SEQUENCE_DATA_NAME = "weblogic.jms.server.SequenceData";
   private static final String SAFSEQUENCE_DATA_NAME = "weblogic.messaging.saf.internal.SAFSequenceData";
   private static final String[] STRING_CLASS = new String[]{null, null, null, null, null, BytesMessageImpl.class.getName(), HdrMessageImpl.class.getName(), MapMessageImpl.class.getName(), ObjectMessageImpl.class.getName(), StreamMessageImpl.class.getName(), TextMessageImpl.class.getName(), XMLMessageImpl.class.getName(), DestinationImpl.class.getName(), JMSID.class.getName(), JMSMessageId.class.getName(), JMSServerId.class.getName(), null, null, String.class.getName(), null, null, null, "weblogic.jms.server.SequenceData", DDLBTableImpl.class.getName(), "weblogic.messaging.saf.internal.SAFSequenceData"};
   private static final Class[] CLASS_CLASS;
   public static final short JMSSUBSCRIBER = 1;
   public static final short JMSBEXAXID = 3;
   public static final short JMSBEXATRANENTRYRECEIVE = 4;
   public static final short JMSBYTESMESSAGE = 5;
   public static final short JMSHDRMESSAGE = 6;
   public static final short JMSMAPMESSAGE = 7;
   public static final short JMSOBJECTMESSAGE = 8;
   public static final short JMSSTREAMMESSAGE = 9;
   public static final short JMSTEXTMESSAGE = 10;
   public static final short JMSXMLMESSAGE = 11;
   public static final short DURABLETOPICMESSAGEINFO = 17;
   public static final short JMSDISTSUBSCRIBER = 19;
   public static final short MOVEMESSAGINGPENDING = 20;
   public static final short DESTINATIONDELETERECORD = 21;
   public static final short SEQUENCEDATA = 22;
   public static final short DDLBTABLE = 23;
   public static final short SAFSEQUENCEDATA = 24;
   private static HashMap classToId;

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
      switch (s) {
         case 1:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 11:
         case 17:
         case 19:
         case 20:
         case 21:
         case 22:
         case 23:
         case 24:
            return true;
         case 2:
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         case 18:
         default:
            return false;
      }
   }

   protected Externalizable newExternal(short s) throws IOException {
      Class clazz;
      if (s >= 0 && s < CLASS_CLASS.length && (clazz = CLASS_CLASS[s]) != null) {
         try {
            return (Externalizable)clazz.newInstance();
         } catch (InstantiationException var4) {
            throw new IOException(var4.getMessage());
         } catch (IllegalAccessException var5) {
            throw new IOException(var5.getMessage());
         }
      } else {
         throw new IOException("Unrecognized class code " + s);
      }
   }

   static {
      CLASS_CLASS = new Class[STRING_CLASS.length];
      classToId = new HashMap();

      for(short i = 0; i < STRING_CLASS.length; ++i) {
         if (STRING_CLASS[i] != null) {
            try {
               CLASS_CLASS[i] = Class.forName(STRING_CLASS[i]);
            } catch (ClassNotFoundException var2) {
               CLASS_CLASS[i] = null;
            }

            if (CLASS_CLASS[i] != null) {
               classToId.put(CLASS_CLASS[i], new Short(i));
            }
         }
      }

   }
}
