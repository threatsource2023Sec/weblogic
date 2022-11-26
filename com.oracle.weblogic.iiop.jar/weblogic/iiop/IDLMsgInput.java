package weblogic.iiop;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.rmi.Remote;
import javax.rmi.CORBA.Util;
import org.omg.CORBA.Any;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.IDLEntity;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.Streamable;
import weblogic.corba.utils.CorbaUtils;
import weblogic.utils.AssertionError;

public class IDLMsgInput extends AbstractMsgInput implements ObjectInput {
   public IDLMsgInput(IIOPInputStream delegate) {
      super(delegate);
   }

   public final String readLine() throws IOException {
      try {
         return this.delegate.read_string();
      } catch (SystemException var2) {
         throw CorbaUtils.mapSystemException(var2);
      }
   }

   public final String readUTF() throws IOException {
      try {
         return this.delegate.read_string();
      } catch (SystemException var2) {
         throw CorbaUtils.mapSystemException(var2);
      }
   }

   public final char readChar() throws IOException {
      try {
         return this.delegate.read_char();
      } catch (SystemException var2) {
         throw CorbaUtils.mapSystemException(var2);
      }
   }

   private final Object readArray(Class c) throws ClassNotFoundException, IOException {
      int len = this.delegate.read_long();
      Class comp = c.getComponentType();
      Object o = Array.newInstance(comp, len);
      if (!comp.isPrimitive()) {
         for(int i = 0; i < len; ++i) {
            ((Object[])((Object[])o))[i] = this.readObject(comp);
         }
      } else if (comp == Byte.TYPE) {
         this.delegate.read_octet_array((byte[])((byte[])o), 0, len);
      } else if (comp == Character.TYPE) {
         this.delegate.read_wchar_array((char[])((char[])o), 0, len);
      } else if (comp == Float.TYPE) {
         this.delegate.read_float_array((float[])((float[])o), 0, len);
      } else if (comp == Double.TYPE) {
         this.delegate.read_double_array((double[])((double[])o), 0, len);
      } else if (comp == Integer.TYPE) {
         this.delegate.read_long_array((int[])((int[])o), 0, len);
      } else if (comp == Long.TYPE) {
         this.delegate.read_longlong_array((long[])((long[])o), 0, len);
      } else if (comp == Short.TYPE) {
         this.delegate.read_short_array((short[])((short[])o), 0, len);
      } else {
         if (comp != Boolean.TYPE) {
            throw new AssertionError("Unknown component type " + comp);
         }

         this.delegate.read_boolean_array((boolean[])((boolean[])((boolean[])o)), 0, len);
      }

      return o;
   }

   public final Object readObject() throws ClassNotFoundException, IOException {
      try {
         return this.readObject((Class)null);
      } catch (SystemException var2) {
         throw CorbaUtils.mapSystemException(var2);
      }
   }

   public final Object readObject(Class c) throws ClassNotFoundException, IOException {
      try {
         if (c.isArray()) {
            return this.readArray(c);
         } else if (Remote.class.isAssignableFrom(c)) {
            return this.readRemote(c);
         } else if (ObjectImpl.class.isAssignableFrom(c)) {
            return this.delegate.read_Object(c);
         } else if (Any.class.isAssignableFrom(c)) {
            return this.delegate.read_any();
         } else if (IDLEntity.class.isAssignableFrom(c)) {
            return this.delegate.read_IDLEntity(c);
         } else if (!c.equals(Object.class) && !c.equals(Serializable.class) && !c.equals(Externalizable.class)) {
            return c.equals(String.class) ? this.delegate.read_string() : this.delegate.read_value(c);
         } else {
            return Util.readAny(this.delegate);
         }
      } catch (SystemException var3) {
         throw CorbaUtils.mapSystemException(var3);
      }
   }

   AbstractMsgOutput createMsgOutput(IIOPOutputStream out) {
      return new IDLMsgOutput(out);
   }

   void readStreamable(Streamable s) {
      s._read(this.delegate);
   }
}
