package org.omg.SendingContext;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class RunTimeHolder implements Streamable {
   public RunTime value = null;

   public RunTimeHolder() {
   }

   public RunTimeHolder(RunTime initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = RunTimeHelper.read(i);
   }

   public void _write(OutputStream o) {
      RunTimeHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return RunTimeHelper.type();
   }
}
