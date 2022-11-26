package com.bea.xbeanmarshal.runtime.internal;

import com.bea.xbeanmarshal.buildtime.internal.bts.BindingLoader;
import com.bea.xbeanmarshal.buildtime.internal.bts.XmlBeanType;
import com.bea.xml.XmlException;

final class XmlBeanTypeRuntimeBindingType extends RuntimeBindingType {
   private final XmlBeanType xmlBeanType;

   XmlBeanTypeRuntimeBindingType(XmlBeanType binding_type) throws XmlException {
      super(binding_type);
      this.xmlBeanType = binding_type;
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
