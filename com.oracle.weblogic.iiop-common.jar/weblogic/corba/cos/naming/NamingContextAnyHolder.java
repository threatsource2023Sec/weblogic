package weblogic.corba.cos.naming;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class NamingContextAnyHolder implements Streamable {
   public NamingContextAny value = null;

   public NamingContextAnyHolder() {
   }

   public NamingContextAnyHolder(NamingContextAny initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = NamingContextAnyHelper.read(i);
   }

   public void _write(OutputStream o) {
      NamingContextAnyHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return NamingContextAnyHelper.type();
   }
}
