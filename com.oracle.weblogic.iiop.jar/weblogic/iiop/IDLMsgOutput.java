package weblogic.iiop;

import java.io.Externalizable;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.rmi.Remote;
import javax.rmi.CORBA.Util;
import org.omg.CORBA.Any;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.IDLEntity;
import org.omg.CORBA.portable.InvokeHandler;
import org.omg.CORBA.portable.Streamable;
import weblogic.corba.utils.CorbaUtils;
import weblogic.utils.AssertionError;

public class IDLMsgOutput extends AbstractMsgOutput {
   public IDLMsgOutput(IIOPOutputStream delegate) {
      super(delegate);
   }

   public final void writeUTF(String s) throws IOException {
      try {
         this.delegate.write_string(s);
      } catch (SystemException var3) {
         throw CorbaUtils.mapSystemException(var3);
      }
   }

   public final void writeChar(int c) throws IOException {
      try {
         this.delegate.write_char((char)c);
      } catch (SystemException var3) {
         throw CorbaUtils.mapSystemException(var3);
      }
   }

   public final void writeBytes(String s) throws IOException {
      try {
         this.delegate.write_string(s);
      } catch (SystemException var3) {
         throw CorbaUtils.mapSystemException(var3);
      }
   }

   public final void writeChars(String s) throws IOException {
      try {
         this.delegate.write_string(s);
      } catch (SystemException var3) {
         throw CorbaUtils.mapSystemException(var3);
      }
   }

   private final void writeArray(Object o, Class c) throws IOException {
      int length = Array.getLength(o);
      this.delegate.write_long(length);
      Class comp = c.getComponentType();
      if (!comp.isPrimitive()) {
         for(int i = 0; i < length; ++i) {
            this.writeObject(((Object[])((Object[])o))[i], comp);
         }
      } else if (comp == Byte.TYPE) {
         this.delegate.write_octet_array((byte[])((byte[])o), 0, length);
      } else if (comp == Character.TYPE) {
         this.delegate.write_wchar_array((char[])((char[])o), 0, length);
      } else if (comp == Float.TYPE) {
         this.delegate.write_float_array((float[])((float[])o), 0, length);
      } else if (comp == Double.TYPE) {
         this.delegate.write_double_array((double[])((double[])o), 0, length);
      } else if (comp == Integer.TYPE) {
         this.delegate.write_long_array((int[])((int[])o), 0, length);
      } else if (comp == Long.TYPE) {
         this.delegate.write_longlong_array((long[])((long[])o), 0, length);
      } else if (comp == Short.TYPE) {
         this.delegate.write_short_array((short[])((short[])o), 0, length);
      } else {
         if (comp != Boolean.TYPE) {
            throw new AssertionError("Unknown component type " + comp);
         }

         this.delegate.write_boolean_array((boolean[])((boolean[])o), 0, length);
      }

   }

   public final void writeObject(Object obj) throws IOException {
      this.writeObject(obj, obj.getClass());
   }

   public final void writeObject(Object o, Class c) throws IOException {
      try {
         if (c.isArray()) {
            this.writeArray(o, c);
         } else if (!Remote.class.isAssignableFrom(c) && !InvokeHandler.class.isAssignableFrom(c) && !org.omg.CORBA.Object.class.isAssignableFrom(c)) {
            if (Any.class.isAssignableFrom(c)) {
               this.delegate.write_any((Any)o);
            } else if (IDLEntity.class.isAssignableFrom(c)) {
               this.delegate.write_IDLEntity(o, c);
            } else if (!c.equals(Object.class) && !c.equals(Serializable.class) && !c.equals(Externalizable.class)) {
               if (c.equals(String.class)) {
                  this.delegate.write_string((String)o);
               } else if (Streamable.class.isAssignableFrom(c)) {
                  this.writeStreamable((Streamable)o);
               } else {
                  this.delegate.write_value((Serializable)o, c);
               }
            } else {
               Util.writeAny(this.delegate, o);
            }
         } else {
            this.delegate.writeRemote(o);
         }

      } catch (SystemException var4) {
         throw CorbaUtils.mapSystemException(var4);
      }
   }

   AbstractMsgInput createMsgInput(IIOPInputStream in) {
      return new IDLMsgInput(in);
   }

   void writeStreamable(Streamable s) {
      s._write(this.delegate);
   }
}
