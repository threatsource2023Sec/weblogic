package com.bea.staxb.buildtime.internal.bts;

import com.bea.xml.XmlException;

public interface BindingTypeVisitor {
   void visit(BuiltinBindingType var1) throws XmlException;

   void visit(ByNameBean var1) throws XmlException;

   void visit(SimpleContentBean var1) throws XmlException;

   void visit(SimpleBindingType var1) throws XmlException;

   void visit(JaxrpcEnumType var1) throws XmlException;

   void visit(SimpleDocumentBinding var1) throws XmlException;

   void visit(WrappedArrayType var1) throws XmlException;

   void visit(SoapArrayType var1) throws XmlException;

   void visit(ListArrayType var1) throws XmlException;
}
