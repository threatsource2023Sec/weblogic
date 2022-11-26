package org.opensaml.saml.ext.saml2mdui.impl;

import java.util.Iterator;
import net.shibboleth.utilities.java.support.xml.AttributeSupport;
import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.ext.saml2mdui.Keywords;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

public class KeywordsMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
      Keywords words = (Keywords)samlObject;
      if (words.getXMLLang() != null) {
         Attr attribute = AttributeSupport.constructAttribute(domElement.getOwnerDocument(), "http://www.w3.org/XML/1998/namespace", "lang", "xml");
         attribute.setValue(words.getXMLLang());
         domElement.setAttributeNodeNS(attribute);
      }

   }

   protected void marshallElementContent(XMLObject samlObject, Element domElement) throws MarshallingException {
      Keywords words = (Keywords)samlObject;
      if (words.getKeywords() != null) {
         StringBuilder sb = new StringBuilder();
         Iterator var5 = words.getKeywords().iterator();

         while(var5.hasNext()) {
            String s = (String)var5.next();
            sb.append(s);
            sb.append(' ');
         }

         ElementSupport.appendTextContent(domElement, sb.toString());
      }

   }
}
