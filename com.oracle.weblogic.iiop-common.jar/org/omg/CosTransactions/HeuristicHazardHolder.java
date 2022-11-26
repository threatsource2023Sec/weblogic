package org.omg.CosTransactions;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class HeuristicHazardHolder implements Streamable {
   public HeuristicHazard value = null;

   public HeuristicHazardHolder() {
   }

   public HeuristicHazardHolder(HeuristicHazard initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = HeuristicHazardHelper.read(i);
   }

   public void _write(OutputStream o) {
      HeuristicHazardHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return HeuristicHazardHelper.type();
   }
}
