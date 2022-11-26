package org.opensaml.core.xml;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.xml.DOMTypeSupport;
import org.w3c.dom.Element;

public abstract class AbstractXMLObjectBuilder implements XMLObjectBuilder {
   @Nonnull
   public XMLObject buildObject(@Nonnull QName objectName) {
      return this.buildObject(objectName.getNamespaceURI(), objectName.getLocalPart(), objectName.getPrefix());
   }

   @Nonnull
   public XMLObject buildObject(@Nonnull QName objectName, @Nullable QName schemaType) {
      return this.buildObject(objectName.getNamespaceURI(), objectName.getLocalPart(), objectName.getPrefix(), schemaType);
   }

   @Nonnull
   public abstract XMLObject buildObject(@Nullable String var1, @Nonnull @NotEmpty String var2, @Nullable String var3);

   @Nonnull
   public XMLObject buildObject(@Nullable String namespaceURI, @Nonnull String localName, @Nullable String namespacePrefix, @Nullable QName schemaType) {
      XMLObject xmlObject = this.buildObject(namespaceURI, localName, namespacePrefix);
      ((AbstractXMLObject)xmlObject).setSchemaType(schemaType);
      return xmlObject;
   }

   @Nonnull
   public XMLObject buildObject(@Nonnull Element element) {
      String localName = element.getLocalName();
      String nsURI = element.getNamespaceURI();
      String nsPrefix = element.getPrefix();
      QName schemaType = DOMTypeSupport.getXSIType(element);
      XMLObject xmlObject = this.buildObject(nsURI, localName, nsPrefix, schemaType);
      return xmlObject;
   }
}
