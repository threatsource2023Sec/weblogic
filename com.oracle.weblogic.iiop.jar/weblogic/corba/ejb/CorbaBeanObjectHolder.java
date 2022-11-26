package weblogic.corba.ejb;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class CorbaBeanObjectHolder implements Streamable {
   public CorbaBeanObject value = null;

   public CorbaBeanObjectHolder() {
   }

   public CorbaBeanObjectHolder(CorbaBeanObject initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = CorbaBeanObjectHelper.read(i);
   }

   public void _write(OutputStream o) {
      CorbaBeanObjectHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return CorbaBeanObjectHelper.type();
   }
}
