package com.bea.xbeanmarshal.runtime.internal;

import com.bea.xbeanmarshal.buildtime.internal.bts.BindingLoader;
import com.bea.xbeanmarshal.buildtime.internal.bts.BindingType;
import com.bea.xml.XmlException;

final class XmlBeanAnyRuntimeBindingType extends RuntimeBindingType {
   XmlBeanAnyRuntimeBindingType(BindingType binding_type) throws XmlException {
      super(binding_type);
   }

   void accept(RuntimeTypeVisitor visitor) throws XmlException {
      visitor.visit(this);
   }

   protected void initialize(RuntimeBindingTypeTable typeTable, BindingLoader bindingLoader) throws XmlException {
   }

   boolean isJavaCollection() {
      return false;
   }

   protected boolean needsXsiType(RuntimeBindingType expected) {
      return false;
   }

   boolean hasElementChildren() {
      return false;
   }
}
