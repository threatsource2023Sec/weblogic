package com.bea.xbeanmarshal.runtime.internal;

import com.bea.xml.XmlException;

public interface RuntimeTypeVisitor {
   void visit(WrappedArrayRuntimeBindingType var1) throws XmlException;

   void visit(XmlBeanTypeRuntimeBindingType var1) throws XmlException;

   void visit(XmlBeanDocumentRuntimeBindingType var1) throws XmlException;

   void visit(XmlBeanBuiltinRuntimeBindingType var1) throws XmlException;

   void visit(XmlBeanAnyRuntimeBindingType var1) throws XmlException;
}
