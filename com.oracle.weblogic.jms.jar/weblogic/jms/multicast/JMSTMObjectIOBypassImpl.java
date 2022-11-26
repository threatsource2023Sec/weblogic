package weblogic.jms.multicast;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.HashMap;
import weblogic.jms.common.BufferDataInputStream;
import weblogic.jms.common.BufferDataOutputStream;
import weblogic.jms.common.BytesMessageImpl;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.HdrMessageImpl;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSMessageId;
import weblogic.jms.common.JMSServerId;
import weblogic.jms.common.MapMessageImpl;
import weblogic.jms.common.ObjectIOBypass;
import weblogic.jms.common.ObjectMessageImpl;
import weblogic.jms.common.StreamMessageImpl;
import weblogic.jms.common.TextMessageImpl;
import weblogic.jms.common.XMLMessageImpl;

public final class JMSTMObjectIOBypassImpl implements ObjectIOBypass {
   private static final Class[] CLASS_ARRAY = new Class[]{null, null, null, null, null, BytesMessageImpl.class, HdrMessageImpl.class, MapMessageImpl.class, ObjectMessageImpl.class, StreamMessageImpl.class, TextMessageImpl.class, XMLMessageImpl.class, DestinationImpl.class, JMSID.class, JMSMessageId.class, JMSServerId.class, null, null, String.class, null};
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
   private static final short STRING = 18;
   public static final short JMSDISTSUBSCRIBER = 19;
   private static HashMap classToId;
   private static final short CODE_NULL = -1;
   private static final short CODE_NOTFOUND = -3;

   JMSTMObjectIOBypassImpl() {
      synchronized(CLASS_ARRAY) {
         if (classToId == null) {
            classToId = new HashMap();

            for(short i = 0; i < CLASS_ARRAY.length; ++i) {
               if (CLASS_ARRAY[i] != null) {
                  classToId.put(CLASS_ARRAY[i], new Short(i));
               }
            }

         }
      }
   }

   public final short getCode(Object obj) {
      if (obj == null) {
         return -1;
      } else {
         Short s = (Short)classToId.get(obj.getClass());
         return s == null ? -3 : s;
      }
   }

   public final void writeObject(ObjectOutput out, Object obj) throws IOException {
      if (obj == null) {
         out.writeShort(-1);
      } else {
         Short s = (Short)classToId.get(obj.getClass());
         if (s == null) {
            throw new IOException("Can't serialize class, type=" + obj.getClass().getName());
         } else {
            short v;
            out.writeShort(v = s);
            if (v == 18) {
               BufferDataOutputStream.writeUTF32(out, (String)obj);
            } else {
               ((Externalizable)obj).writeExternal(out);
            }

         }
      }
   }

   public final Object readObject(ObjectInput in) throws ClassNotFoundException, IOException {
      short typeNum = in.readShort();
      if (typeNum == -1) {
         return null;
      } else if (typeNum == 18) {
         return BufferDataInputStream.readUTF32(in);
      } else if (typeNum >= 0 && typeNum < CLASS_ARRAY.length && CLASS_ARRAY[typeNum] != null) {
         Class c = CLASS_ARRAY[typeNum];

         Externalizable ext;
         try {
            ext = (Externalizable)((Externalizable)c.newInstance());
         } catch (InstantiationException var6) {
            throw new ClassNotFoundException(var6.toString() + ", " + c.getName());
         } catch (IllegalAccessException var7) {
            throw new ClassNotFoundException(var7.toString() + ", " + c.getName());
         } catch (SecurityException var8) {
            throw new ClassNotFoundException(var8.toString() + ", " + c.getName());
         }

         ext.readExternal(in);
         return ext;
      } else {
         throw new IOException("Unrecognized class code " + typeNum);
      }
   }
}
