package org.omg.CosTransactions;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class OTSPolicyHolder implements Streamable {
   public OTSPolicy value = null;

   public OTSPolicyHolder() {
   }

   public OTSPolicyHolder(OTSPolicy initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = OTSPolicyHelper.read(i);
   }

   public void _write(OutputStream o) {
      OTSPolicyHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return OTSPolicyHelper.type();
   }
}
