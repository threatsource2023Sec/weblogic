package org.omg.CosTransactions;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class InvocationPolicyHolder implements Streamable {
   public InvocationPolicy value = null;

   public InvocationPolicyHolder() {
   }

   public InvocationPolicyHolder(InvocationPolicy initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = InvocationPolicyHelper.read(i);
   }

   public void _write(OutputStream o) {
      InvocationPolicyHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return InvocationPolicyHelper.type();
   }
}
