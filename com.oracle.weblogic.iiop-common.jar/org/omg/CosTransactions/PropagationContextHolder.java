package org.omg.CosTransactions;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class PropagationContextHolder implements Streamable {
   public PropagationContext value = null;

   public PropagationContextHolder() {
   }

   public PropagationContextHolder(PropagationContext initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = PropagationContextHelper.read(i);
   }

   public void _write(OutputStream o) {
      PropagationContextHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return PropagationContextHelper.type();
   }
}
