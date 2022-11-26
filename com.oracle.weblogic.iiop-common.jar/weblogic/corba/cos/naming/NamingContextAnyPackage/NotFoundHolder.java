package weblogic.corba.cos.naming.NamingContextAnyPackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class NotFoundHolder implements Streamable {
   public NotFound value = null;

   public NotFoundHolder() {
   }

   public NotFoundHolder(NotFound initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = NotFoundHelper.read(i);
   }

   public void _write(OutputStream o) {
      NotFoundHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return NotFoundHelper.type();
   }
}
