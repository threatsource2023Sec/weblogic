package org.omg.CosTransactions;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ResourceHolder implements Streamable {
   public Resource value = null;

   public ResourceHolder() {
   }

   public ResourceHolder(Resource initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = ResourceHelper.read(i);
   }

   public void _write(OutputStream o) {
      ResourceHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return ResourceHelper.type();
   }
}
