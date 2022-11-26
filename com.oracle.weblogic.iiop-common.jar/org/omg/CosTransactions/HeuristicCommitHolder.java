package org.omg.CosTransactions;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class HeuristicCommitHolder implements Streamable {
   public HeuristicCommit value = null;

   public HeuristicCommitHolder() {
   }

   public HeuristicCommitHolder(HeuristicCommit initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = HeuristicCommitHelper.read(i);
   }

   public void _write(OutputStream o) {
      HeuristicCommitHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return HeuristicCommitHelper.type();
   }
}
