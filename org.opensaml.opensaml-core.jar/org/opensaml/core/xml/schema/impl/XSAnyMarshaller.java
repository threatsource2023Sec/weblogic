package org.opensaml.core.xml.schema.impl;

import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.xml.AttributeSupport;
import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.AbstractXMLObjectMarshaller;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.schema.XSAny;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

public class XSAnyMarshaller extends AbstractXMLObjectMarshaller {
   protected void marshallAttributes(@Nonnull XMLObject xmlObject, @Nonnull Element domElement) throws MarshallingException {
      XSAny xsAny = (XSAny)xmlObject;
      Iterator var5 = xsAny.getUnknownAttributes().entrySet().iterator();

      while(true) {
         Attr attribute;
         Map.Entry entry;
         do {
            if (!var5.hasNext()) {
               return;
            }

            entry = (Map.Entry)var5.next();
            attribute = AttributeSupport.constructAttribute(domElement.getOwnerDocument(), (QName)entry.getKey());
            attribute.setValue((String)entry.getValue());
            domElement.setAttributeNodeNS(attribute);
         } while(!XMLObjectProviderRegistrySupport.isIDAttribute((QName)entry.getKey()) && !xsAny.getUnknownAttributes().isIDAttribute((QName)entry.getKey()));

         attribute.getOwnerElement().setIdAttributeNode(attribute, true);
      }
   }

   protected void marshallElementContent(@Nonnull XMLObject xmlObject, @Nonnull Element domElement) throws MarshallingException {
      XSAny xsAny = (XSAny)xmlObject;
      if (xsAny.getTextContent() != null) {
         ElementSupport.appendTextContent(domElement, xsAny.getTextContent());
      }

   }
}
