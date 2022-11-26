package com.bea.staxb.runtime.internal;

import com.bea.xbean.util.XsTypeConverter;
import com.bea.xml.XmlException;
import javax.xml.namespace.QName;

abstract class NamedXmlTypeVisitor extends XmlTypeVisitor {
   private final String prefix;
   private final RuntimeBindingType actualRuntimeBindingType;
   protected static final String TRUE_LEX = XsTypeConverter.printBoolean(true).intern();

   NamedXmlTypeVisitor(Object parentObject, RuntimeBindingProperty property, PullMarshalResult result) throws XmlException {
      super(parentObject, property, result);
      this.actualRuntimeBindingType = property.getActualRuntimeType(parentObject, result);
      String uri = this.getBindingProperty().getName().getNamespaceURI();
      if (uri.length() > 0) {
         this.prefix = this.marshalResult.ensureElementPrefix(uri);
      } else {
         this.prefix = null;
      }

   }

   protected QName getName() {
      QName name = this.getBindingProperty().getName();
      if (this.prefix == null) {
         return name;
      } else {
         String nameSpace = name.getNamespaceURI();
         String localPart = name.getLocalPart();
         return new QName(nameSpace != null ? nameSpace.intern() : null, localPart != null ? localPart.intern() : null, this.prefix.intern());
      }
   }

   protected String getLocalPart() {
      return this.getBindingProperty().getName().getLocalPart();
   }

   protected String getNamespaceURI() {
      return this.getBindingProperty().getName().getNamespaceURI();
   }

   protected String getPrefix() {
      return this.prefix;
   }

   protected final RuntimeBindingType getActualRuntimeBindingType() {
      return this.actualRuntimeBindingType;
   }

   protected final boolean needsXsiType() {
      return this.actualRuntimeBindingType.needsXsiType(this.getBindingProperty().getRuntimeBindingType(), this.marshalResult);
   }

   final void predefineNamespaces() throws XmlException {
      Object parentObject = this.getParentObject();
      if (parentObject != null) {
         this.actualRuntimeBindingType.predefineNamespaces(parentObject, this.marshalResult);
      }

   }
}
