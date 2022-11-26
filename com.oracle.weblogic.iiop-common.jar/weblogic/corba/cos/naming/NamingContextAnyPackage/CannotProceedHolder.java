package weblogic.corba.cos.naming.NamingContextAnyPackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class CannotProceedHolder implements Streamable {
   public CannotProceed value = null;

   public CannotProceedHolder() {
   }

   public CannotProceedHolder(CannotProceed initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = CannotProceedHelper.read(i);
   }

   public void _write(OutputStream o) {
      CannotProceedHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return CannotProceedHelper.type();
   }
}
