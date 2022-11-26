package org.apache.xml.security.encryption;

import java.io.IOException;
import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.apache.xml.security.transforms.TransformationException;
import org.apache.xml.security.utils.XMLUtils;
import org.apache.xml.security.utils.resolver.ResourceResolver;
import org.apache.xml.security.utils.resolver.ResourceResolverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;

public class XMLCipherInput {
   private static final Logger LOG = LoggerFactory.getLogger(XMLCipherInput.class);
   private CipherData cipherData;
   private int mode;
   private boolean secureValidation;

   public XMLCipherInput(CipherData data) throws XMLEncryptionException {
      this.cipherData = data;
      this.mode = 2;
      if (this.cipherData == null) {
         throw new XMLEncryptionException("CipherData is null");
      }
   }

   public XMLCipherInput(EncryptedType input) throws XMLEncryptionException {
      this.cipherData = input == null ? null : input.getCipherData();
      this.mode = 2;
      if (this.cipherData == null) {
         throw new XMLEncryptionException("CipherData is null");
      }
   }

   public void setSecureValidation(boolean secureValidation) {
      this.secureValidation = secureValidation;
   }

   public byte[] getBytes() throws XMLEncryptionException {
      return this.mode == 2 ? this.getDecryptBytes() : null;
   }

   private byte[] getDecryptBytes() throws XMLEncryptionException {
      String base64EncodedEncryptedOctets = null;
      if (this.cipherData.getDataType() == 2) {
         LOG.debug("Found a reference type CipherData");
         CipherReference cr = this.cipherData.getCipherReference();
         Attr uriAttr = cr.getURIAsAttr();
         XMLSignatureInput input = null;

         try {
            ResourceResolver resolver = ResourceResolver.getInstance(uriAttr, (String)null, this.secureValidation);
            input = resolver.resolve(uriAttr, (String)null, this.secureValidation);
         } catch (ResourceResolverException var10) {
            throw new XMLEncryptionException(var10);
         }

         if (input != null) {
            LOG.debug("Managed to resolve URI \"{}\"", cr.getURI());
         } else {
            LOG.debug("Failed to resolve URI \"{}\"", cr.getURI());
         }

         Transforms transforms = cr.getTransforms();
         if (transforms != null) {
            LOG.debug("Have transforms in cipher reference");

            try {
               org.apache.xml.security.transforms.Transforms dsTransforms = transforms.getDSTransforms();
               dsTransforms.setSecureValidation(this.secureValidation);
               input = dsTransforms.performTransforms(input);
            } catch (TransformationException var9) {
               throw new XMLEncryptionException(var9);
            }
         }

         try {
            return input.getBytes();
         } catch (IOException var7) {
            throw new XMLEncryptionException(var7);
         } catch (CanonicalizationException var8) {
            throw new XMLEncryptionException(var8);
         }
      } else if (this.cipherData.getDataType() == 1) {
         base64EncodedEncryptedOctets = this.cipherData.getCipherValue().getValue();
         LOG.debug("Encrypted octets:\n{}", base64EncodedEncryptedOctets);
         return XMLUtils.decode(base64EncodedEncryptedOctets);
      } else {
         throw new XMLEncryptionException("CipherData.getDataType() returned unexpected value");
      }
   }
}
