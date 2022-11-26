package weblogic.corba.cos.naming.NamingContextAnyPackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class TypeNotSupportedHolder implements Streamable {
   public TypeNotSupported value = null;

   public TypeNotSupportedHolder() {
   }

   public TypeNotSupportedHolder(TypeNotSupported initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = TypeNotSupportedHelper.read(i);
   }

   public void _write(OutputStream o) {
      TypeNotSupportedHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return TypeNotSupportedHelper.type();
   }
}
