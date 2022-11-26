package com.bea.xbeanmarshal.runtime.internal;

import com.bea.xml.XmlException;
import javax.xml.namespace.QName;
import org.w3c.dom.Document;

final class RuntimeGlobalProperty extends RuntimeBindingProperty {
   private final boolean isWrapped;
   private final Document document;
   private final QName rootElement;
   private final RuntimeBindingType runtimeBindingType;

   public RuntimeGlobalProperty(boolean isWrapped, Document document, QName rootElement, RuntimeBindingType runtimeBindingType) {
      super((RuntimeBindingType)null);
      this.isWrapped = isWrapped;
      this.document = document;
      this.rootElement = rootElement;
      this.runtimeBindingType = runtimeBindingType;
   }

   public boolean isWrapped() {
      return this.isWrapped;
   }

   public Document getDocument() {
      return this.document;
   }

   public RuntimeBindingType getRuntimeBindingType() {
      return this.runtimeBindingType;
   }

   public QName getName() {
      return this.rootElement;
   }

   RuntimeBindingType getContainingType() {
      throw new AssertionError("unused");
   }

   void setValue(Object target, Object prop_obj) throws XmlException {
      throw new AssertionError("not used");
   }

   public void fill(Object inter, Object prop_obj) throws XmlException {
      throw new UnsupportedOperationException();
   }

   public Object getValue(Object parent_obj) throws XmlException {
      throw new AssertionError("UNIMP: " + this);
   }

   public boolean isSet(Object parentObject) throws XmlException {
      return true;
   }

   public boolean isMultiple() {
      return false;
   }

   public boolean isNillable() {
      return true;
   }

   public String getLexicalDefault() {
      throw new AssertionError("UNIMP");
   }
}
