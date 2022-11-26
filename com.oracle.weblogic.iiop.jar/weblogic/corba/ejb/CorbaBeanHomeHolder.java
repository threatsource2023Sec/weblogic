package weblogic.corba.ejb;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class CorbaBeanHomeHolder implements Streamable {
   public CorbaBeanHome value = null;

   public CorbaBeanHomeHolder() {
   }

   public CorbaBeanHomeHolder(CorbaBeanHome initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = CorbaBeanHomeHelper.read(i);
   }

   public void _write(OutputStream o) {
      CorbaBeanHomeHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return CorbaBeanHomeHelper.type();
   }
}
