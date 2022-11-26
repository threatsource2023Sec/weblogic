package org.opensaml.soap.soap11.impl;

import java.util.Iterator;
import java.util.Map;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.xml.AttributeSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.AbstractXMLObjectMarshaller;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.soap.soap11.Detail;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

public class DetailMarshaller extends AbstractXMLObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      Detail detail = (Detail)xmlObject;
      Iterator var5 = detail.getUnknownAttributes().entrySet().iterator();

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
         } while(!XMLObjectProviderRegistrySupport.isIDAttribute((QName)entry.getKey()) && !detail.getUnknownAttributes().isIDAttribute((QName)entry.getKey()));

         attribute.getOwnerElement().setIdAttributeNode(attribute, true);
      }
   }

   protected void marshallElementContent(XMLObject xmlObject, Element domElement) throws MarshallingException {
   }
}
