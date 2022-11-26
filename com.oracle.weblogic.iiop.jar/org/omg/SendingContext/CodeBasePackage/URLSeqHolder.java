package org.omg.SendingContext.CodeBasePackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class URLSeqHolder implements Streamable {
   public String[] value = null;

   public URLSeqHolder() {
   }

   public URLSeqHolder(String[] initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = URLSeqHelper.read(i);
   }

   public void _write(OutputStream o) {
      URLSeqHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return URLSeqHelper.type();
   }
}
