package org.opensaml.core.xml.schema.impl;

import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.xml.ElementSupport;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.AbstractXMLObjectUnmarshaller;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.XSQName;
import org.w3c.dom.Attr;
import org.w3c.dom.Text;

public class XSQNameUnmarshaller extends AbstractXMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
   }

   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
   }

   protected void processElementContent(XMLObject xmlObject, String elementContent) {
   }

   protected void unmarshallTextContent(XMLObject xmlObject, Text content) throws UnmarshallingException {
      String textContent = StringSupport.trimOrNull(content.getWholeText());
      if (textContent != null) {
         XSQName qname = (XSQName)xmlObject;
         qname.setValue(QNameSupport.constructQName(ElementSupport.getElementAncestor(content), textContent));
      }

   }
}
