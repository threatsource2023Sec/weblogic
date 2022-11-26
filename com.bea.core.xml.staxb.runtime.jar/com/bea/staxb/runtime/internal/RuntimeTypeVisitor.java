package com.bea.staxb.runtime.internal;

import com.bea.xml.XmlException;

public interface RuntimeTypeVisitor {
   void visit(BuiltinRuntimeBindingType var1) throws XmlException;

   void visit(AnyTypeRuntimeBindingType var1) throws XmlException;

   void visit(ByNameRuntimeBindingType var1) throws XmlException;

   void visit(SimpleContentRuntimeBindingType var1) throws XmlException;

   void visit(SimpleRuntimeBindingType var1) throws XmlException;

   void visit(JaxrpcEnumRuntimeBindingType var1) throws XmlException;

   void visit(WrappedArrayRuntimeBindingType var1) throws XmlException;

   void visit(SoapArrayRuntimeBindingType var1) throws XmlException;

   void visit(ListArrayRuntimeBindingType var1) throws XmlException;
}
