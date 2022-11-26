package org.omg.CosTransactions;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class InvalidControlHolder implements Streamable {
   public InvalidControl value = null;

   public InvalidControlHolder() {
   }

   public InvalidControlHolder(InvalidControl initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = InvalidControlHelper.read(i);
   }

   public void _write(OutputStream o) {
      InvalidControlHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return InvalidControlHelper.type();
   }
}
