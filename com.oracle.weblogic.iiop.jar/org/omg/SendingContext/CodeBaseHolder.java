package org.omg.SendingContext;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class CodeBaseHolder implements Streamable {
   public CodeBase value = null;

   public CodeBaseHolder() {
   }

   public CodeBaseHolder(CodeBase initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = CodeBaseHelper.read(i);
   }

   public void _write(OutputStream o) {
      CodeBaseHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return CodeBaseHelper.type();
   }
}
