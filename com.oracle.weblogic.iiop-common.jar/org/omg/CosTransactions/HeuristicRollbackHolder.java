package org.omg.CosTransactions;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class HeuristicRollbackHolder implements Streamable {
   public HeuristicRollback value = null;

   public HeuristicRollbackHolder() {
   }

   public HeuristicRollbackHolder(HeuristicRollback initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = HeuristicRollbackHelper.read(i);
   }

   public void _write(OutputStream o) {
      HeuristicRollbackHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return HeuristicRollbackHelper.type();
   }
}
