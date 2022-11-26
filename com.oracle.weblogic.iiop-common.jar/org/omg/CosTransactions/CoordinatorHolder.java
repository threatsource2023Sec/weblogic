package org.omg.CosTransactions;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class CoordinatorHolder implements Streamable {
   public Coordinator value = null;

   public CoordinatorHolder() {
   }

   public CoordinatorHolder(Coordinator initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = CoordinatorHelper.read(i);
   }

   public void _write(OutputStream o) {
      CoordinatorHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return CoordinatorHelper.type();
   }
}
