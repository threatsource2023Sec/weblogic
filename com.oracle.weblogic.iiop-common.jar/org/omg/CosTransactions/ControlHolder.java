package org.omg.CosTransactions;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ControlHolder implements Streamable {
   public Control value = null;

   public ControlHolder() {
   }

   public ControlHolder(Control initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = ControlHelper.read(i);
   }

   public void _write(OutputStream o) {
      ControlHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return ControlHelper.type();
   }
}
