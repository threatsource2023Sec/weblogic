package com.bea.staxb.runtime.internal;

import com.bea.xml.XmlException;
import javax.xml.namespace.QName;

abstract class XmlTypeVisitor {
   private final Object parentObject;
   private final RuntimeBindingProperty bindingProperty;
   protected final PullMarshalResult marshalResult;
   static final int START = 1;
   static final int CONTENT = 2;
   static final int CHARS = 3;
   static final int END = 4;

   XmlTypeVisitor(Object obj, RuntimeBindingProperty property, PullMarshalResult result) throws XmlException {
      this.parentObject = obj;
      this.bindingProperty = property;
      this.marshalResult = result;
   }

   protected final Object getParentObject() {
      return this.parentObject;
   }

   protected final RuntimeBindingProperty getBindingProperty() {
      return this.bindingProperty;
   }

   protected abstract int getState();

   protected abstract int advance() throws XmlException;

   public abstract XmlTypeVisitor getCurrentChild() throws XmlException;

   protected abstract QName getName();

   protected abstract String getLocalPart();

   protected abstract String getNamespaceURI();

   protected abstract String getPrefix();

   protected void initAttributes() throws XmlException {
   }

   abstract void predefineNamespaces() throws XmlException;

   protected abstract CharSequence getCharData();

   public String toString() {
      return this.getClass().getName() + " prop=" + this.bindingProperty.getName() + " type=" + this.bindingProperty.getRuntimeBindingType().getBindingType();
   }
}
