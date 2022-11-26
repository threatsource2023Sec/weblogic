package weblogic.corba.cos.naming.NamingContextAnyPackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class WNameComponentHolder implements Streamable {
   public WNameComponent value = null;

   public WNameComponentHolder() {
   }

   public WNameComponentHolder(WNameComponent initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = WNameComponentHelper.read(i);
   }

   public void _write(OutputStream o) {
      WNameComponentHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return WNameComponentHelper.type();
   }
}
