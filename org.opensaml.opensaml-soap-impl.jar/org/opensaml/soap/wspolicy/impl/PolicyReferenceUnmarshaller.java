package org.opensaml.soap.wspolicy.impl;

import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wspolicy.PolicyReference;
import org.w3c.dom.Attr;

public class PolicyReferenceUnmarshaller extends AbstractWSPolicyObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      PolicyReference pr = (PolicyReference)xmlObject;
      QName uriName = new QName("URI");
      QName digestName = new QName("Digest");
      QName digestAlgorithmName = new QName("DigestAlgorithm");
      QName attribQName = QNameSupport.constructQName(attribute.getNamespaceURI(), attribute.getLocalName(), attribute.getPrefix());
      if (uriName.equals(attribQName)) {
         pr.setURI(attribute.getValue());
      } else if (digestName.equals(attribQName)) {
         pr.setDigest(attribute.getValue());
      } else if (digestAlgorithmName.equals(attribQName)) {
         pr.setDigestAlgorithm(attribute.getValue());
      } else {
         XMLObjectSupport.unmarshallToAttributeMap(pr.getUnknownAttributes(), attribute);
      }

   }
}
