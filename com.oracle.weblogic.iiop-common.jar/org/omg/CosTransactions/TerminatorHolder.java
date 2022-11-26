package org.omg.CosTransactions;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class TerminatorHolder implements Streamable {
   public Terminator value = null;

   public TerminatorHolder() {
   }

   public TerminatorHolder(Terminator initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = TerminatorHelper.read(i);
   }

   public void _write(OutputStream o) {
      TerminatorHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return TerminatorHelper.type();
   }
}
