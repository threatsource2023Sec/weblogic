package org.omg.SendingContext.CodeBasePackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.ValueDefPackage.FullValueDescription;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ValueDescSeqHolder implements Streamable {
   public FullValueDescription[] value = null;

   public ValueDescSeqHolder() {
   }

   public ValueDescSeqHolder(FullValueDescription[] initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = ValueDescSeqHelper.read(i);
   }

   public void _write(OutputStream o) {
      ValueDescSeqHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return ValueDescSeqHelper.type();
   }
}
