package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.ArtifactResponse;
import org.opensaml.saml.saml2.core.Extensions;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.saml.saml2.core.Status;
import org.opensaml.xmlsec.signature.Signature;

public class ArtifactResponseUnmarshaller extends StatusResponseTypeUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      ArtifactResponse artifactResponse = (ArtifactResponse)parentSAMLObject;
      if (childSAMLObject instanceof Issuer) {
         artifactResponse.setIssuer((Issuer)childSAMLObject);
      } else if (childSAMLObject instanceof Signature) {
         artifactResponse.setSignature((Signature)childSAMLObject);
      } else if (childSAMLObject instanceof Extensions) {
         artifactResponse.setExtensions((Extensions)childSAMLObject);
      } else if (childSAMLObject instanceof Status) {
         artifactResponse.setStatus((Status)childSAMLObject);
      } else {
         artifactResponse.setMessage((SAMLObject)childSAMLObject);
      }

   }
}
