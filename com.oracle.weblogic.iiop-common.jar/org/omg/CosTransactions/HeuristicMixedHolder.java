package org.omg.CosTransactions;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class HeuristicMixedHolder implements Streamable {
   public HeuristicMixed value = null;

   public HeuristicMixedHolder() {
   }

   public HeuristicMixedHolder(HeuristicMixed initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = HeuristicMixedHelper.read(i);
   }

   public void _write(OutputStream o) {
      HeuristicMixedHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return HeuristicMixedHelper.type();
   }
}
