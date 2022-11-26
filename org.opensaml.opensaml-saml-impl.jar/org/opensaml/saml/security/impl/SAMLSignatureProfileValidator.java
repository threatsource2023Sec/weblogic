package org.opensaml.saml.security.impl;

import com.google.common.base.Strings;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.signature.Reference;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.transforms.Transform;
import org.apache.xml.security.transforms.TransformationException;
import org.apache.xml.security.transforms.Transforms;
import org.apache.xml.security.utils.IdResolver;
import org.opensaml.saml.common.SignableSAMLObject;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.impl.SignatureImpl;
import org.opensaml.xmlsec.signature.support.SignatureException;
import org.opensaml.xmlsec.signature.support.SignaturePrevalidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SAMLSignatureProfileValidator implements SignaturePrevalidator {
   private final Logger log = LoggerFactory.getLogger(SAMLSignatureProfileValidator.class);

   public void validate(@Nonnull Signature signature) throws SignatureException {
      Constraint.isNotNull(signature, "Signature was null");
      if (!(signature instanceof SignatureImpl)) {
         this.log.info("Signature was not an instance of SignatureImpl, was {} validation not supported", signature.getClass().getName());
      } else {
         this.validateSignatureImpl((SignatureImpl)signature);
      }
   }

   protected void validateSignatureImpl(SignatureImpl sigImpl) throws SignatureException {
      if (sigImpl.getXMLSignature() == null) {
         this.log.error("SignatureImpl did not contain the an Apache XMLSignature child");
         throw new SignatureException("Apache XMLSignature does not exist on SignatureImpl");
      } else {
         XMLSignature apacheSig = sigImpl.getXMLSignature();
         if (!(sigImpl.getParent() instanceof SignableSAMLObject)) {
            this.log.error("Signature is not an immedidate child of a SignableSAMLObject");
            throw new SignatureException("Signature is not an immediate child of a SignableSAMLObject.");
         } else {
            SignableSAMLObject signableObject = (SignableSAMLObject)sigImpl.getParent();
            Reference ref = this.validateReference(apacheSig);
            this.validateReferenceURI(ref.getURI(), signableObject);
            this.validateTransforms(ref);
            this.validateObjectChildren(apacheSig);
         }
      }
   }

   protected Reference validateReference(XMLSignature apacheSig) throws SignatureException {
      int numReferences = apacheSig.getSignedInfo().getLength();
      if (numReferences != 1) {
         this.log.error("Signature SignedInfo had invalid number of References: " + numReferences);
         throw new SignatureException("Signature SignedInfo must have exactly 1 Reference element");
      } else {
         Reference ref = null;

         try {
            ref = apacheSig.getSignedInfo().item(0);
         } catch (XMLSecurityException var5) {
            this.log.error("Apache XML Security exception obtaining Reference", var5);
            throw new SignatureException("Could not obtain Reference from Signature/SignedInfo", var5);
         }

         if (ref == null) {
            this.log.error("Signature Reference was null");
            throw new SignatureException("Signature Reference was null");
         } else {
            return ref;
         }
      }
   }

   protected void validateReferenceURI(String uri, SignableSAMLObject signableObject) throws SignatureException {
      String id = signableObject.getSignatureReferenceID();
      this.validateReferenceURI(uri, id);
      if (!Strings.isNullOrEmpty(uri)) {
         String uriID = uri.substring(1);
         Element expected = signableObject.getDOM();
         if (expected == null) {
            this.log.error("SignableSAMLObject does not have a cached DOM Element.");
            throw new SignatureException("SignableSAMLObject does not have a cached DOM Element.");
         } else {
            Document doc = expected.getOwnerDocument();
            Element resolved = IdResolver.getElementById(doc, uriID);
            if (resolved == null) {
               this.log.error("Apache xmlsec IdResolver could not resolve the Element for id reference: {}", uriID);
               throw new SignatureException("Apache xmlsec IdResolver could not resolve the Element for id reference: " + uriID);
            } else if (!expected.isSameNode(resolved)) {
               this.log.error("Signature Reference URI '{}' did not resolve to the expected parent Element", uri);
               throw new SignatureException("Signature Reference URI did not resolve to the expected parent Element");
            }
         }
      }
   }

   protected void validateReferenceURI(String uri, String id) throws SignatureException {
      if (!Strings.isNullOrEmpty(uri)) {
         if (!uri.startsWith("#")) {
            this.log.error("Signature Reference URI was not a document fragment reference: " + uri);
            throw new SignatureException("Signature Reference URI was not a document fragment reference");
         }

         if (Strings.isNullOrEmpty(id)) {
            this.log.error("SignableSAMLObject did not contain an ID attribute");
            throw new SignatureException("SignableSAMLObject did not contain an ID attribute");
         }

         if (uri.length() < 2 || !id.equals(uri.substring(1))) {
            this.log.error("Reference URI '{}' did not point to SignableSAMLObject with ID '{}'", uri, id);
            throw new SignatureException("Reference URI did not point to parent ID");
         }
      }

   }

   protected void validateTransforms(Reference reference) throws SignatureException {
      Transforms transforms = null;

      try {
         transforms = reference.getTransforms();
      } catch (XMLSecurityException var9) {
         this.log.error("Apache XML Security error obtaining Transforms instance", var9);
         throw new SignatureException("Apache XML Security error obtaining Transforms instance", var9);
      }

      if (transforms == null) {
         this.log.error("Error obtaining Transforms instance, null was returned");
         throw new SignatureException("Transforms instance was null");
      } else {
         int numTransforms = transforms.getLength();
         if (numTransforms > 2) {
            this.log.error("Invalid number of Transforms was present: " + numTransforms);
            throw new SignatureException("Invalid number of transforms");
         } else {
            boolean sawEnveloped = false;

            for(int i = 0; i < numTransforms; ++i) {
               Transform transform = null;

               try {
                  transform = transforms.item(i);
               } catch (TransformationException var8) {
                  this.log.error("Error obtaining transform instance", var8);
                  throw new SignatureException("Error obtaining transform instance", var8);
               }

               String uri = transform.getURI();
               if ("http://www.w3.org/2000/09/xmldsig#enveloped-signature".equals(uri)) {
                  this.log.debug("Saw Enveloped signature transform");
                  sawEnveloped = true;
               } else {
                  if (!"http://www.w3.org/2001/10/xml-exc-c14n#".equals(uri) && !"http://www.w3.org/2001/10/xml-exc-c14n#WithComments".equals(uri)) {
                     this.log.error("Saw invalid signature transform: " + uri);
                     throw new SignatureException("Signature contained an invalid transform");
                  }

                  this.log.debug("Saw Exclusive C14N signature transform");
               }
            }

            if (!sawEnveloped) {
               this.log.error("Signature was missing the required Enveloped signature transform");
               throw new SignatureException("Transforms did not contain the required enveloped transform");
            }
         }
      }
   }

   protected void validateObjectChildren(XMLSignature apacheSig) throws SignatureException {
      if (apacheSig.getObjectLength() > 0) {
         this.log.error("Signature contained {} ds:Object child element(s)", apacheSig.getObjectLength());
         throw new SignatureException("Signature contained illegal ds:Object children");
      }
   }
}
