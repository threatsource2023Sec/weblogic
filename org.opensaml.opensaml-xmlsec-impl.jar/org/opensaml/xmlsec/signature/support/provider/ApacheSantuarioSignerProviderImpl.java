package org.opensaml.xmlsec.signature.support.provider;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.apache.xml.security.Init;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.signature.XMLSignature;
import org.opensaml.security.credential.CredentialSupport;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.impl.SignatureImpl;
import org.opensaml.xmlsec.signature.support.SignatureException;
import org.opensaml.xmlsec.signature.support.SignerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApacheSantuarioSignerProviderImpl implements SignerProvider {
   private Logger log = LoggerFactory.getLogger(ApacheSantuarioSignerProviderImpl.class);

   public void signObject(@Nonnull Signature signature) throws SignatureException {
      Constraint.isNotNull(signature, "Signature cannot be null");
      Constraint.isTrue(Init.isInitialized(), "Apache XML security library is not initialized");

      try {
         XMLSignature xmlSignature = ((SignatureImpl)signature).getXMLSignature();
         if (xmlSignature == null) {
            this.log.error("Unable to compute signature, Signature XMLObject does not have the XMLSignature created during marshalling.");
            throw new SignatureException("XMLObject does not have XMLSignature instance, unable to compute signature");
         } else {
            this.log.debug("Computing signature over XMLSignature object");
            xmlSignature.sign(CredentialSupport.extractSigningKey(signature.getSigningCredential()));
         }
      } catch (XMLSecurityException var3) {
         this.log.error("An error occured computing the digital signature", var3);
         throw new SignatureException("Signature computation error", var3);
      }
   }
}
