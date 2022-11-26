package weblogic.rmi.internal;

import java.io.IOException;
import weblogic.common.WLObjectOutput;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.MsgOutput;

public final class ObjectIO {
   public static final short OBJECT_TYPE = 0;
   public static final short INTEGER_TYPE = 1;
   public static final short BOOLEAN_TYPE = 2;
   public static final short BYTE_TYPE = 3;
   public static final short CHARACTER_TYPE = 4;
   public static final short SHORT_TYPE = 5;
   public static final short LONG_TYPE = 6;
   public static final short FLOAT_TYPE = 7;
   public static final short DOUBLE_TYPE = 8;
   public static final short VOID_TYPE = 9;

   private ObjectIO() {
   }

   public static void writeObject(MsgOutput out, Object object, Class type, short typeCode) throws IOException {
      switch (typeCode) {
         case 0:
            if (out instanceof WLObjectOutput) {
               WLObjectOutput wlout = (WLObjectOutput)out;
               wlout.writeObjectWL(object);
            } else {
               out.writeObject(object, type);
            }
            break;
         case 1:
            out.writeInt((Integer)object);
            break;
         case 2:
            out.writeBoolean((Boolean)object);
            break;
         case 3:
            out.writeByte((Byte)object);
            break;
         case 4:
            out.writeChar((Character)object);
            break;
         case 5:
            out.writeShort((Short)object);
            break;
         case 6:
            out.writeLong((Long)object);
            break;
         case 7:
            out.writeFloat((Float)object);
            break;
         case 8:
            out.writeDouble((Double)object);
            break;
         default:
            throw new AssertionError("Unexpected primitive type: " + type);
      }

   }

   public static Object readObject(MsgInput in, Class type, short typeCode) throws IOException, ClassNotFoundException {
      switch (typeCode) {
         case 0:
            return in.readObject(type);
         case 1:
            return new Integer(in.readInt());
         case 2:
            return in.readBoolean();
         case 3:
            return new Byte(in.readByte());
         case 4:
            return new Character(in.readChar());
         case 5:
            return new Short(in.readShort());
         case 6:
            return new Long(in.readLong());
         case 7:
            return new Float(in.readFloat());
         case 8:
            return new Double(in.readDouble());
         case 9:
            return null;
         default:
            throw new AssertionError("Unexpected primitive type: " + type);
      }
   }
}
