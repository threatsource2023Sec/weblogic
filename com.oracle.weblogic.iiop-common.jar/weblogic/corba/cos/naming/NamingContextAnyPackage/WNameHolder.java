package weblogic.corba.cos.naming.NamingContextAnyPackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class WNameHolder implements Streamable {
   public WNameComponent[] value = null;

   public WNameHolder() {
   }

   public WNameHolder(WNameComponent[] initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = WNameHelper.read(i);
   }

   public void _write(OutputStream o) {
      WNameHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return WNameHelper.type();
   }
}
