package weblogic.corba.cos.naming;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class BindingIteratorHolder implements Streamable {
   public BindingIterator value = null;

   public BindingIteratorHolder() {
   }

   public BindingIteratorHolder(BindingIterator initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = BindingIteratorHelper.read(i);
   }

   public void _write(OutputStream o) {
      BindingIteratorHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return BindingIteratorHelper.type();
   }
}
