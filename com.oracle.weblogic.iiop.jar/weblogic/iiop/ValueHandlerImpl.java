package weblogic.iiop;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.lang.reflect.Array;
import javax.rmi.CORBA.ValueHandler;
import javax.rmi.CORBA.ValueHandlerMultiFormat;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.SendingContext.RunTime;
import weblogic.corba.utils.ClassInfo;
import weblogic.utils.io.ObjectStreamClass;

public class ValueHandlerImpl implements ValueHandlerMultiFormat {
   private static final boolean DEBUG = false;

   public void writeValue(OutputStream out, Serializable value) {
      this.writeValue(out, value, (byte)1);
   }

   public Serializable readValue(InputStream in, int indirection, Class type, String repid, RunTime codebase) {
      try {
         ObjectStreamClass osc = ObjectStreamClass.lookup(type);
         Object value = allocateValue(in, osc);
         return (Serializable)readValue((IIOPInputStream)in, osc, value);
      } catch (ClassNotFoundException | IOException var8) {
         throw (MARSHAL)(new MARSHAL(var8.getMessage())).initCause(var8);
      }
   }

   public static Object allocateValue(InputStream in, ObjectStreamClass osc) throws IOException {
      Class type = osc.forClass();
      return osc.isArray() ? Array.newInstance(type.getComponentType(), in.read_ulong()) : osc.newInstance();
   }

   public static Object readValue(IIOPInputStream in, ObjectStreamClass osc, Object value) throws IOException, ClassNotFoundException {
      if (osc.isArray()) {
         return readArray(in, osc, value);
      } else {
         if (osc.isExternalizable()) {
            discardFormatId(in);
            ((Externalizable)value).readExternal(in);
         } else {
            readValueData(in, value, osc);
         }

         return osc.readResolve(value);
      }
   }

   private static void discardFormatId(IIOPInputStream in) {
      in.read_octet();
   }

   public String getRMIRepositoryID(Class cl) {
      return getRepositoryID(cl);
   }

   public static String getRepositoryID(Class cl) {
      ClassInfo osc = ClassInfo.findClassInfo(cl);
      return osc == null ? null : osc.getRepositoryId().toString();
   }

   public boolean isCustomMarshaled(Class cl) {
      ObjectStreamClass osc = ObjectStreamClass.lookup(cl);
      return osc != null && osc.isCustomMarshaled();
   }

   public RunTime getRunTimeCodeBase() {
      return null;
   }

   public Serializable writeReplace(Serializable value) {
      try {
         return (Serializable)this.writeReplace((Object)value);
      } catch (IOException var3) {
         throw (MARSHAL)(new MARSHAL("writeReplace()")).initCause(var3);
      }
   }

   public Object writeReplace(Object obj) throws IOException {
      Class c = obj.getClass();

      for(ObjectStreamClass osc = ObjectStreamClass.lookup(c); osc != null && osc.hasWriteReplace() && (obj = osc.writeReplace(obj)) != null && obj.getClass() != c; osc = ObjectStreamClass.lookup(c)) {
         c = obj.getClass();
      }

      return obj;
   }

   public byte getMaximumStreamFormatVersion() {
      return 2;
   }

   public void writeValue(OutputStream out, Serializable value, byte streamFormatVersion) {
      ClassInfo cinfo = ClassInfo.findClassInfo(value.getClass());

      try {
         writeValue((IIOPOutputStream)out, value, streamFormatVersion, cinfo);
      } catch (IOException var6) {
         throw (MARSHAL)(new MARSHAL(var6.getMessage() + " at " + ((IIOPOutputStream)out).pos())).initCause(var6);
      }
   }

   public static void writeValue(IIOPOutputStream out, Object value, byte streamFormatVersion, ClassInfo cinfo) throws IOException {
      ObjectStreamClass osc = cinfo.getDescriptor();
      if (osc.isExternalizable()) {
         out.write_octet(streamFormatVersion);
         ((Externalizable)value).writeExternal(out);
      } else if (!ObjectStreamClass.supportsUnsafeSerialization()) {
         ValueHandler vh = ValueHandlerHolder.getValueHandler();
         if (vh instanceof ValueHandlerMultiFormat) {
            ((ValueHandlerMultiFormat)vh).writeValue(out, (Serializable)value, streamFormatVersion);
         } else {
            vh.writeValue(out, (Serializable)value);
         }
      } else if (osc.isArray()) {
         writeArray(out, value, osc, cinfo.forClass());
      } else {
         writeValueData(out, value, osc, streamFormatVersion);
      }

   }

   private static void writeValueData(IIOPOutputStream out, Object value, ObjectStreamClass osc, byte streamFormatVersion) throws IOException {
      if (osc.getSuperclass() != null) {
         writeValueData(out, value, osc.getSuperclass(), streamFormatVersion);
      }

      if (osc.hasWriteObject()) {
         out.write_octet(streamFormatVersion);
         ObjectOutputStream oo = out.getObjectOutputStream(value, osc, streamFormatVersion);
         osc.writeObject(value, oo);
         oo.close();
      } else {
         osc.writeFields(value, out);
      }

   }

   private static void readValueData(IIOPInputStream in, Object value, ObjectStreamClass osc) throws IOException, ClassNotFoundException {
      if (osc.getSuperclass() != null) {
         readValueData(in, value, osc.getSuperclass());
      }

      boolean dfwo = true;
      byte sfv = 1;
      if (osc.hasWriteObject()) {
         sfv = in.read_octet();
         dfwo = in.read_boolean();
      }

      if (osc.hasReadObject()) {
         boolean endValue = false;
         if (!dfwo && sfv > 1) {
            endValue = in.startValue();
         }

         ObjectInputStream ois = in.getObjectInputStream(value, osc, dfwo, sfv);
         osc.readObject(value, ois);
         ois.close();
         if (endValue) {
            in.end_value();
         }
      } else {
         if (!dfwo) {
            throw new StreamCorruptedException("defaultWriteObject() was not called, but " + osc.forClass().getName() + " has no readObject() method");
         }

         osc.readFields(value, in);
      }

   }

   private static void writeArray(IIOPOutputStream out, Object value, ObjectStreamClass osc, Class c) throws IOException {
      Class comp = c.getComponentType();
      if (comp.isPrimitive()) {
         if (comp == Integer.TYPE) {
            int[] arr = (int[])((int[])value);
            out.write_ulong(arr.length);
            out.write_long_array(arr, 0, arr.length);
         } else if (comp == Byte.TYPE) {
            byte[] arr = (byte[])((byte[])value);
            out.write_ulong(arr.length);
            out.write_octet_array(arr, 0, arr.length);
         } else if (comp == Long.TYPE) {
            long[] arr = (long[])((long[])value);
            out.write_ulong(arr.length);
            out.write_longlong_array(arr, 0, arr.length);
         } else if (comp == Float.TYPE) {
            float[] arr = (float[])((float[])value);
            out.write_ulong(arr.length);
            out.write_float_array(arr, 0, arr.length);
         } else if (comp == Double.TYPE) {
            double[] arr = (double[])((double[])value);
            out.write_ulong(arr.length);
            out.write_double_array(arr, 0, arr.length);
         } else if (comp == Short.TYPE) {
            short[] arr = (short[])((short[])value);
            out.write_ulong(arr.length);
            out.write_short_array(arr, 0, arr.length);
         } else if (comp == Character.TYPE) {
            char[] arr = (char[])((char[])value);
            out.write_ulong(arr.length);
            out.write_wchar_array(arr, 0, arr.length);
         } else {
            if (comp != Boolean.TYPE) {
               throw new StreamCorruptedException("Invalid component type");
            }

            boolean[] arr = (boolean[])((boolean[])value);
            out.write_ulong(arr.length);
            out.write_boolean_array(arr, 0, arr.length);
         }
      } else {
         Object[] objs = (Object[])((Object[])value);
         out.write_ulong(objs.length);
         Object[] var6 = objs;
         int var7 = objs.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            Object obj = var6[var8];
            out.writeObject(obj, comp);
         }
      }

   }

   private static Object readArray(IIOPInputStream in, ObjectStreamClass osc, Object value) throws IOException, ClassNotFoundException {
      Class comp = osc.forClass().getComponentType();
      if (comp.isPrimitive()) {
         if (comp == Integer.TYPE) {
            int[] arr = (int[])((int[])value);
            in.read_long_array(arr, 0, arr.length);
            return arr;
         } else if (comp == Byte.TYPE) {
            byte[] arr = (byte[])((byte[])value);
            in.read_octet_array(arr, 0, arr.length);
            return arr;
         } else if (comp == Long.TYPE) {
            long[] arr = (long[])((long[])value);
            in.read_longlong_array(arr, 0, arr.length);
            return arr;
         } else if (comp == Float.TYPE) {
            float[] arr = (float[])((float[])value);
            in.read_float_array(arr, 0, arr.length);
            return arr;
         } else if (comp == Double.TYPE) {
            double[] arr = (double[])((double[])value);
            in.read_double_array(arr, 0, arr.length);
            return arr;
         } else if (comp == Short.TYPE) {
            short[] arr = (short[])((short[])value);
            in.read_short_array(arr, 0, arr.length);
            return arr;
         } else if (comp == Character.TYPE) {
            char[] arr = (char[])((char[])value);
            in.read_wchar_array(arr, 0, arr.length);
            return arr;
         } else if (comp == Boolean.TYPE) {
            boolean[] arr = (boolean[])((boolean[])value);
            in.read_boolean_array((boolean[])arr, 0, arr.length);
            return arr;
         } else {
            throw new StreamCorruptedException("Invalid component type");
         }
      } else {
         Object[] objs = (Object[])((Object[])value);

         for(int i = 0; i < objs.length; ++i) {
            objs[i] = in.readObject(comp);
         }

         return objs;
      }
   }

   private static void p(String msg) {
      System.err.println("<ValueHandlerImpl>: " + msg);
   }
}
