package com.bea.xbeanmarshal.buildtime.internal.bts;

import com.bea.xml.XmlException;

public interface BindingTypeVisitor {
   void visit(XmlBeanType var1) throws XmlException;

   void visit(XmlBeanDocumentType var1) throws XmlException;

   void visit(XmlBeanBuiltinType var1) throws XmlException;

   void visit(WrappedArrayType var1) throws XmlException;

   void visit(XmlBeanAnyType var1) throws XmlException;
}
