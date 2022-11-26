package org.omg.CosTransactions;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class VoteHolder implements Streamable {
   public Vote value = null;

   public VoteHolder() {
   }

   public VoteHolder(Vote initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = VoteHelper.read(i);
   }

   public void _write(OutputStream o) {
      VoteHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return VoteHelper.type();
   }
}
