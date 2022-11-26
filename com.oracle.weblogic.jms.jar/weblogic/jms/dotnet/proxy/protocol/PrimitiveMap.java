package weblogic.jms.dotnet.proxy.protocol;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.jms.dotnet.proxy.util.ProxyUtil;
import weblogic.jms.dotnet.transport.MarshalReadable;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWritable;
import weblogic.jms.dotnet.transport.MarshalWriter;

public class PrimitiveMap implements Map, MarshalWritable, MarshalReadable {
   private static final int EXTVERSION = 1;
   private MarshalBitMask versionFlags;
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
   private Map map = new HashMap();

   public void clear() {
      this.map.clear();
   }

   public boolean containsKey(Object key) {
      return this.map.containsKey(key);
   }

   public boolean containsValue(Object value) {
      return this.map.containsValue(value);
   }

   public Set entrySet() {
      return this.map.entrySet();
   }

   public Object get(Object key) {
      return this.map.get(key);
   }

   public boolean isEmpty() {
      return this.map.isEmpty();
   }

   public Set keySet() {
      return this.map.keySet();
   }

   public Object put(String key, Object value) {
      this.checkValidType(value);
      return this.map.put(key, value);
   }

   private void checkValidType(Object value) {
      if (value != null && !(value instanceof byte[]) && !(value instanceof String) && !(value instanceof Boolean) && !(value instanceof Character) && !(value instanceof Byte) && !(value instanceof Short) && !(value instanceof Integer) && !(value instanceof Long) && !(value instanceof Float) && !(value instanceof Double)) {
         throw new IllegalArgumentException("Passed value object (Class = " + value.getClass().getName() + ") is not allowed primitive type");
      }
   }

   public void putAll(Map primitiveMap) {
      if (primitiveMap == null || !primitiveMap.isEmpty()) {
         Iterator var2 = primitiveMap.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry entry = (Map.Entry)var2.next();
            this.put((String)entry.getKey(), entry.getValue());
         }
      }

   }

   public Object remove(Object key) {
      return this.map.remove(key);
   }

   public int size() {
      return this.map.size();
   }

   public Collection values() {
      return this.map.values();
   }

   public int getMarshalTypeCode() {
      return 0;
   }

   public void marshal(MarshalWriter mw) {
      this.versionFlags = new MarshalBitMask(1);
      this.versionFlags.marshal(mw);
      mw.writeInt(this.map.size());
      Iterator var2 = this.map.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         mw.writeShort((short)102);
         mw.writeString((String)entry.getKey());
         if (entry.getValue() == null) {
            mw.writeShort((short)99);
         } else if (entry.getValue() instanceof String) {
            mw.writeShort((short)102);
            mw.writeString((String)entry.getValue());
         } else if (entry.getValue() instanceof Integer) {
            mw.writeShort((short)104);
            mw.writeInt((Integer)entry.getValue());
         } else if (entry.getValue() instanceof Long) {
            mw.writeShort((short)105);
            mw.writeLong((Long)entry.getValue());
         } else if (entry.getValue() instanceof byte[]) {
            mw.writeShort((short)111);
            byte[] signedBytes = (byte[])((byte[])entry.getValue());
            mw.writeInt(signedBytes.length);
            mw.write(signedBytes, 0, signedBytes.length);
         } else if (entry.getValue() instanceof Boolean) {
            mw.writeShort((short)103);
            mw.writeBoolean((Boolean)entry.getValue());
         } else if (entry.getValue() instanceof Byte) {
            mw.writeShort((short)106);
            mw.writeByte((Byte)entry.getValue());
         } else if (entry.getValue() instanceof Short) {
            mw.writeShort((short)107);
            mw.writeShort((Short)entry.getValue());
         } else if (entry.getValue() instanceof Float) {
            mw.writeShort((short)108);
            mw.writeFloat((Float)entry.getValue());
         } else if (entry.getValue() instanceof Double) {
            mw.writeShort((short)109);
            mw.writeDouble((Double)entry.getValue());
         } else {
            if (!(entry.getValue() instanceof Character)) {
               throw new IllegalArgumentException("Invalid value type " + entry.getValue().getClass().getName() + " found");
            }

            mw.writeShort((short)110);
            mw.writeChar((Character)entry.getValue());
         }
      }

   }

   public void unmarshal(MarshalReader mr) {
      this.versionFlags = new MarshalBitMask();
      this.versionFlags.unmarshal(mr);
      ProxyUtil.checkVersion(this.versionFlags.getVersion(), 1, 1);

      for(int entryCount = mr.readInt(); entryCount != 0; --entryCount) {
         String key = (String)this.readPrimitiveType(mr);
         Object value = this.readPrimitiveType(mr);
         this.map.put(key, value);
      }

   }

   private Object readPrimitiveType(MarshalReader mr) {
      short code = mr.readShort();
      switch (code) {
         case 99:
            return null;
         case 100:
         case 101:
         default:
            throw new IllegalArgumentException("Invalid value type code " + code + " found");
         case 102:
            return mr.readString();
         case 103:
            return mr.readBoolean();
         case 104:
            return mr.readInt();
         case 105:
            return mr.readLong();
         case 106:
            return mr.readByte();
         case 107:
            return mr.readShort();
         case 108:
            return mr.readFloat();
         case 109:
            return mr.readDouble();
         case 110:
            return mr.readChar();
         case 111:
            int arrayLength = mr.readInt();
            byte[] result = new byte[arrayLength];
            mr.read(result, 0, arrayLength);
            return result;
      }
   }
}
