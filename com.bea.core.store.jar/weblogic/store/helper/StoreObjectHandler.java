package weblogic.store.helper;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import weblogic.store.ObjectHandler;

public abstract class StoreObjectHandler implements ObjectHandler {
   private static final short CODE_NULL = -1;
   protected short CODE_SERIALIZABLE = -2;
   private static final short CODE_NOTFOUND = -3;
   protected boolean ALLOW_SERIALIZABLES = false;
   static final short CODE_STRING = 18;

   public abstract Short getIdForClass(Object var1);

   public abstract Class getClassForId(short var1);

   public abstract void checkIfClassRecognized(short var1) throws IOException;

   protected Externalizable newExternal(short s) throws IOException {
      throw new IOException("subclass not defined " + s);
   }

   protected abstract boolean haveExternal(short var1);

   public final void writeObject(ObjectOutput out, Object obj) throws IOException {
      if (obj == null) {
         out.writeShort(-1);
      } else {
         Short s = this.getIdForClass(obj);
         if (s == null) {
            if (this.ALLOW_SERIALIZABLES && obj instanceof Serializable) {
               throw new IOException("Can't serialize class, type=" + obj.getClass().getName());
            } else {
               out.writeShort(this.CODE_SERIALIZABLE);
               out.writeObject((Serializable)obj);
            }
         } else {
            short v = s;
            out.writeShort(v);
            if (v == 18) {
               writeUTF32((String)obj, out);
            } else {
               ((Externalizable)obj).writeExternal(out);
            }

         }
      }
   }

   public final short getCode(Object obj) {
      if (obj == null) {
         return -1;
      } else {
         Short s = this.getIdForClass(obj);
         return s == null ? -3 : s;
      }
   }

   public final Object readObject(ObjectInput in) throws ClassNotFoundException, IOException {
      short typeNum = in.readShort();
      if (typeNum == -1) {
         return null;
      } else if (typeNum == 18) {
         return readUTF32(in);
      } else if (typeNum == this.CODE_SERIALIZABLE) {
         return in.readObject();
      } else {
         Externalizable ext;
         if (this.haveExternal(typeNum)) {
            ext = this.newExternal(typeNum);
         } else {
            this.checkIfClassRecognized(typeNum);
            Class c = this.getClassForId(typeNum);

            try {
               ext = (Externalizable)((Externalizable)c.newInstance());
            } catch (InstantiationException var6) {
               throw new ClassNotFoundException(var6.toString() + ", " + c.getName());
            } catch (IllegalAccessException var7) {
               throw new ClassNotFoundException(var7.toString() + ", " + c.getName());
            } catch (SecurityException var8) {
               throw new ClassNotFoundException(var8.toString() + ", " + c.getName());
            }
         }

         ext.readExternal(in);
         return ext;
      }
   }

   private static final void writeUTF32(String str, ObjectOutput out) throws IOException {
      int strlen = str.length();
      long utflen = 0L;

      int i;
      char c;
      for(i = 0; i < strlen; ++i) {
         c = str.charAt(i);
         if (c >= 1 && c <= 127) {
            ++utflen;
         } else if (c > 2047) {
            utflen += 3L;
         } else {
            utflen += 2L;
         }
      }

      if (utflen > 2147483647L) {
         throw new IOException("String to large to encode, encodes to " + utflen + " bytes but maximum is " + Integer.MAX_VALUE);
      } else {
         out.writeInt((int)utflen);

         for(i = 0; i < strlen; ++i) {
            c = str.charAt(i);
            if (c >= 1 && c <= 127) {
               out.write(c);
            } else if (c > 2047) {
               out.write(224 | c >> 12 & 15);
               out.write(128 | c >> 6 & 63);
               out.write(128 | c >> 0 & 63);
            } else {
               out.write(192 | c >> 6 & 31);
               out.write(128 | c >> 0 & 63);
            }
         }

      }
   }

   private static final String readUTF32(ObjectInput in) throws IOException {
      int utflen = in.readInt();
      char[] str = new char[utflen];
      int count = 0;
      int strlen = 0;

      while(count < utflen) {
         int c = in.readUnsignedByte();
         int char2;
         switch (c >> 4) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
               ++count;
               str[strlen++] = (char)c;
               break;
            case 8:
            case 9:
            case 10:
            case 11:
            default:
               throw new IOException("Error decoding String.");
            case 12:
            case 13:
               count += 2;
               if (count > utflen) {
                  throw new IOException("Error decoding String.");
               }

               char2 = in.readUnsignedByte();
               if ((char2 & 192) != 128) {
                  throw new IOException("Error decoding String.");
               }

               str[strlen++] = (char)((c & 31) << 6 | char2 & 63);
               break;
            case 14:
               count += 3;
               if (count > utflen) {
                  throw new IOException("Error decoding String.");
               }

               char2 = in.readUnsignedByte();
               int char3 = in.readUnsignedByte();
               if ((char2 & 192) != 128 || (char3 & 192) != 128) {
                  throw new IOException("Error decoding String.");
               }

               str[strlen++] = (char)((c & 15) << 12 | (char2 & 63) << 6 | (char3 & 63) << 0);
         }
      }

      return new String(str, 0, strlen);
   }
}
