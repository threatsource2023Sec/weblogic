package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.buildtime.internal.bts.BuiltinBindingType;
import com.bea.xml.XmlException;

class BuiltinRuntimeBindingType extends RuntimeBindingType {
   BuiltinRuntimeBindingType(BuiltinBindingType type, TypeConverter converter) throws XmlException {
      super(type, converter, converter);

      assert converter != null;

   }

   final void accept(RuntimeTypeVisitor visitor) throws XmlException {
      visitor.visit(this);
   }

   public void initialize(RuntimeBindingTypeTable typeTable, BindingLoader bindingLoader) throws XmlException {
   }

   boolean canHaveSubtype() {
      return false;
   }

   final boolean hasElementChildren() {
      return false;
   }
}
