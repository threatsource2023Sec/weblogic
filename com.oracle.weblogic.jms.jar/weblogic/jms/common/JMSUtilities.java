package weblogic.jms.common;

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.io.UTFDataFormatException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.jms.Message;
import weblogic.common.internal.PeerInfo;
import weblogic.jms.JMSClientExceptionLogger;
import weblogic.messaging.MessagingLogger;
import weblogic.utils.StringUtils;
import weblogic.utils.io.DataIO;

public final class JMSUtilities {
   public static final String RESERVED_ROLLBACK_ONLY = "ReservedRollbackOnly";
   private static final short TYPENULL = 99;
   private static final short TYPESTRING = 102;
   private static final short TYPEBOOLEAN = 103;
   private static final short TYPEINTEGER = 104;
   private static final short TYPELONG = 105;
   private static final short TYPEBYTE = 106;
   private static final short TYPESHORT = 107;
   private static final short TYPEFLOAT = 108;
   private static final short TYPEDOUBLE = 109;
   private static final short TYPECHARACTER = 110;
   private static final short TYPEBYTEARRAY = 111;
   private static final short TYPEBIGSTRING = 112;

   private JMSUtilities() {
   }

   public static javax.jms.JMSException jmsException(String reason, Exception exe) {
      return jmsExceptionThrowable(reason, exe);
   }

   public static javax.jms.JMSException jmsExceptionThrowable(String reason, Throwable t) {
      if (JMSDebug.JMSCommon.isDebugEnabled()) {
         JMSDebug.JMSCommon.debug(reason, t);
      }

      if (t instanceof javax.jms.JMSException) {
         return (javax.jms.JMSException)t;
      } else {
         javax.jms.JMSException jmse = new JMSException(reason, t);
         return jmse;
      }
   }

   public static javax.jms.JMSException throwJMSOrRuntimeException(Throwable t) throws javax.jms.JMSException {
      if (t instanceof javax.jms.JMSException) {
         throw (javax.jms.JMSException)t;
      } else if (t instanceof RuntimeException) {
         throw (RuntimeException)t;
      } else if (t instanceof Error) {
         throw (Error)t;
      } else {
         throw new JMSException(t.getMessage(), t);
      }
   }

   public static StreamCorruptedException versionIOException(int version, int minExpectedVersion, int maxExpectedVersion) {
      return new StreamCorruptedException(MessagingLogger.logUnsupportedClassVersionLoggable(version, minExpectedVersion, maxExpectedVersion).getMessage());
   }

   public static boolean getTracing(Message m) {
      return true;
   }

   public static void setTracing(Message m, boolean b) {
      try {
         m.setBooleanProperty("tracing", b);
      } catch (javax.jms.JMSException var3) {
      }

   }

   private static boolean getDropNulls(PeerInfo peerInfo) {
      if (peerInfo == null) {
         return true;
      } else {
         return peerInfo.compareTo(PeerInfo.VERSION_612) <= 0;
      }
   }

   private static int getEntryCount(Map map, boolean dropNulls) {
      if (dropNulls) {
         int count = 0;
         Iterator iterator = map.entrySet().iterator();

         while(iterator.hasNext()) {
            Map.Entry entry = (Map.Entry)iterator.next();
            if (entry.getValue() != null) {
               ++count;
            }
         }

         return count;
      } else {
         return map.size();
      }
   }

   static final int writeBasicMap(DataOutput out, Map map, PeerInfo peerInfo) throws IOException {
      boolean dropNulls = getDropNulls(peerInfo);
      out.writeInt(getEntryCount(map, dropNulls));
      int size = 0;
      Iterator iterator = map.entrySet().iterator();

      while(true) {
         Map.Entry entry;
         do {
            if (!iterator.hasNext()) {
               return size;
            }

            entry = (Map.Entry)iterator.next();
         } while(dropNulls && entry.getValue() == null);

         size += writeBasicType(out, entry.getKey());
         size += writeBasicType(out, entry.getValue());
      }
   }

   static final int writeBigStringBasicMap(ObjectOutput out, Map map, PeerInfo peerInfo, boolean allowLargeStrings) throws IOException {
      boolean dropNulls = getDropNulls(peerInfo);
      boolean bigStringOK = false;
      if (peerInfo != null) {
         bigStringOK = allowLargeStrings && peerInfo.compareTo(PeerInfo.VERSION_701) >= 0;
      }

      out.writeInt(getEntryCount(map, dropNulls));
      int size = 0;
      Iterator iterator = map.entrySet().iterator();

      while(true) {
         Map.Entry entry;
         do {
            if (!iterator.hasNext()) {
               return size;
            }

            entry = (Map.Entry)iterator.next();
         } while(dropNulls && entry.getValue() == null);

         if (bigStringOK) {
            size += writeBigStringBasicType(out, entry.getKey());
            size += writeBigStringBasicType(out, entry.getValue());
         } else {
            size += writeBasicType(out, entry.getKey());
            size += writeBasicType(out, entry.getValue());
         }
      }
   }

   static final HashMap readBasicMap(DataInput in) throws IOException {
      HashMap hashMap = new HashMap();
      int size = in.readInt();

      while(size-- > 0) {
         Object key = readBasicType(in);
         Object value = readBasicType(in);
         hashMap.put(key, value);
      }

      return hashMap;
   }

   static final HashMap readBigStringBasicMap(ObjectInput in) throws IOException {
      HashMap hashMap = new HashMap();
      int size = in.readInt();

      while(size-- > 0) {
         Object key = readBigStringBasicType(in);
         Object value = readBigStringBasicType(in);
         hashMap.put(key, value);
      }

      return hashMap;
   }

   private static int writeBigStringBasicType(ObjectOutput oo, Object obj) throws IOException {
      if (obj != null && obj instanceof String) {
         String s = (String)obj;
         int utflen = StringUtils.getUTFLength(s);
         if (utflen <= 32767) {
            oo.writeShort(102);
            writeUTF(oo, s, utflen);
         } else {
            oo.writeShort(112);
            oo.writeObject(s);
         }

         return 4 + (s.length() << 2);
      } else {
         return writeBasicType(oo, obj);
      }
   }

   private static int writeBasicType(DataOutput oo, Object obj) throws IOException {
      if (obj == null) {
         oo.writeShort(99);
         return 2;
      } else if (obj instanceof String) {
         String s = (String)obj;
         int utflen = StringUtils.getUTFLength(s);
         if (utflen <= 32767) {
            oo.writeShort(102);
            writeUTF(oo, s, utflen);
            return 4 + (s.length() << 2);
         } else {
            ByteArrayOutputStream baos = new ByteArrayOutputStream((s.length() << 2) + 50);
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(s);
            oos.close();
            return writeBasicType(oo, baos.toByteArray());
         }
      } else if (obj instanceof Integer) {
         oo.writeShort(104);
         oo.writeInt((Integer)obj);
         return 6;
      } else if (obj instanceof Long) {
         oo.writeShort(105);
         oo.writeLong((Long)obj);
         return 10;
      } else if (obj instanceof byte[]) {
         oo.writeShort(111);
         oo.writeInt(((byte[])((byte[])obj)).length);
         oo.write((byte[])((byte[])obj));
         return 6 + ((byte[])((byte[])obj)).length;
      } else if (obj instanceof Boolean) {
         oo.writeShort(103);
         oo.writeBoolean((Boolean)obj);
         return 3;
      } else if (obj instanceof Byte) {
         oo.writeShort(106);
         oo.writeByte((Byte)obj);
         return 3;
      } else if (obj instanceof Short) {
         oo.writeShort(107);
         oo.writeShort((Short)obj);
         return 4;
      } else if (obj instanceof Float) {
         oo.writeShort(108);
         oo.writeFloat((Float)obj);
         return 6;
      } else if (obj instanceof Double) {
         oo.writeShort(109);
         oo.writeDouble((Double)obj);
         return 10;
      } else if (obj instanceof Character) {
         oo.writeShort(110);
         oo.writeChar((Character)obj);
         return 4;
      } else {
         throw new StreamCorruptedException(JMSClientExceptionLogger.logSimpleObjectLoggable(obj.getClass().getName()).getMessage());
      }
   }

   private static Object readBigStringBasicType(ObjectInput oi) throws IOException {
      short code = oi.readShort();
      if (code == 112) {
         try {
            return (String)oi.readObject();
         } catch (ClassNotFoundException var3) {
            throw new IOException(var3.toString());
         }
      } else {
         return readBasicType(oi, code);
      }
   }

   private static Object readBasicType(DataInput di) throws IOException {
      short code = di.readShort();
      return readBasicType(di, code);
   }

   private static Object readBasicType(DataInput oi, short code) throws IOException {
      switch (code) {
         case 99:
            return null;
         case 100:
         case 101:
         default:
            throw new StreamCorruptedException(JMSClientExceptionLogger.logUnrecognizedClassCodeLoggable(code).getMessage());
         case 102:
            return DataInputStream.readUTF(oi);
         case 103:
            return oi.readBoolean();
         case 104:
            return new Integer(oi.readInt());
         case 105:
            return new Long(oi.readLong());
         case 106:
            return new Byte(oi.readByte());
         case 107:
            return new Short(oi.readShort());
         case 108:
            return new Float(oi.readFloat());
         case 109:
            return new Double(oi.readDouble());
         case 110:
            return new Character(oi.readChar());
         case 111:
            byte[] b = new byte[oi.readInt()];
            oi.readFully(b);
            return b;
      }
   }

   private static void writeUTF(DataOutput out, String str, int utflen) throws IOException {
      if (utflen > 32767) {
         throw new UTFDataFormatException();
      } else {
         out.writeShort(utflen);
         int strlen = str.length();

         for(int i = 0; i < strlen; ++i) {
            DataIO.writeUTFChar(out, str.charAt(i));
         }

      }
   }
}
