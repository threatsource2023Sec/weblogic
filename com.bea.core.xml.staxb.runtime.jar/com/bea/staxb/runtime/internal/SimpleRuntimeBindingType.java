package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.buildtime.internal.bts.BindingType;
import com.bea.staxb.buildtime.internal.bts.SimpleBindingType;
import com.bea.xml.XmlException;
import javax.xml.namespace.QName;

final class SimpleRuntimeBindingType extends RuntimeBindingType {
   private final SimpleBindingType simpleBindingType;
   private boolean isDerivedFromQName;

   SimpleRuntimeBindingType(SimpleBindingType type) throws XmlException {
      super(type);
      this.simpleBindingType = type;
   }

   void accept(RuntimeTypeVisitor visitor) throws XmlException {
      visitor.visit(this);
   }

   public void initialize(RuntimeBindingTypeTable typeTable, BindingLoader bindingLoader) throws XmlException {
      TypeMarshaller marshaller = typeTable.createMarshaller((BindingType)this.simpleBindingType, bindingLoader);
      this.isDerivedFromQName = marshaller instanceof QNameTypeConverter;
   }

   boolean hasElementChildren() {
      return false;
   }

   protected boolean canUseDefaultNamespace(Object obj) throws XmlException {
      if (!this.isDerivedFromQName) {
         return true;
      } else if (obj instanceof QName) {
         QName qn = (QName)obj;
         return !"".equals(qn.getNamespaceURI());
      } else {
         return true;
      }
   }
}
