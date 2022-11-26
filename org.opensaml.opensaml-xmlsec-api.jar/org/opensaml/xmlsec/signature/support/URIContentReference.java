package org.opensaml.xmlsec.signature.support;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.signature.XMLSignatureException;
import org.apache.xml.security.transforms.TransformationException;
import org.apache.xml.security.transforms.Transforms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class URIContentReference implements ConfigurableContentReference {
   private final Logger log = LoggerFactory.getLogger(URIContentReference.class);
   private final String referenceID;
   private String digestAlgorithm;
   private final List transforms;

   public URIContentReference(@Nullable String refID) {
      this.referenceID = refID;
      this.transforms = new LinkedList();
      this.digestAlgorithm = "http://www.w3.org/2001/04/xmlenc#sha256";
   }

   @Nonnull
   public List getTransforms() {
      return this.transforms;
   }

   @Nullable
   public String getDigestAlgorithm() {
      return this.digestAlgorithm;
   }

   public void setDigestAlgorithm(@Nonnull String newAlgorithm) {
      this.digestAlgorithm = (String)Constraint.isNotNull(StringSupport.trimOrNull(newAlgorithm), "Digest algorithm cannot be empty or null");
   }

   public void createReference(@Nonnull XMLSignature signature) {
      try {
         Transforms dsigTransforms = new Transforms(signature.getDocument());
         Iterator var3 = this.getTransforms().iterator();

         while(var3.hasNext()) {
            String transform = (String)var3.next();
            dsigTransforms.addTransform(transform);
         }

         signature.addDocument(this.referenceID, dsigTransforms, this.digestAlgorithm);
      } catch (TransformationException var5) {
         this.log.error("Error while creating transforms", var5);
      } catch (XMLSignatureException var6) {
         this.log.error("Error while adding content reference", var6);
      }

   }
}
