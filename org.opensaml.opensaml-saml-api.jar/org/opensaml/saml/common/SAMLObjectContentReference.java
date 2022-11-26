package org.opensaml.saml.common;

import com.google.common.base.Strings;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.Live;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.collection.LazyList;
import net.shibboleth.utilities.java.support.collection.LazySet;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.signature.XMLSignatureException;
import org.apache.xml.security.transforms.Transform;
import org.apache.xml.security.transforms.TransformationException;
import org.apache.xml.security.transforms.Transforms;
import org.apache.xml.security.transforms.params.InclusiveNamespaces;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.signature.support.ConfigurableContentReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SAMLObjectContentReference implements ConfigurableContentReference {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(SAMLObjectContentReference.class);
   @Nonnull
   private final SignableSAMLObject signableObject;
   @Nonnull
   @NotEmpty
   private String digestAlgorithm;
   @Nonnull
   @NonnullElements
   private List transforms;

   public SAMLObjectContentReference(@Nonnull SignableSAMLObject newSignableObject) {
      this.signableObject = newSignableObject;
      this.transforms = new LazyList();
      this.digestAlgorithm = "http://www.w3.org/2001/04/xmlenc#sha256";
      this.transforms.add("http://www.w3.org/2000/09/xmldsig#enveloped-signature");
      this.transforms.add("http://www.w3.org/2001/10/xml-exc-c14n#");
   }

   @Nonnull
   @NonnullElements
   @Live
   public List getTransforms() {
      return this.transforms;
   }

   @Nonnull
   @NotEmpty
   public String getDigestAlgorithm() {
      return this.digestAlgorithm;
   }

   public void setDigestAlgorithm(@Nonnull @NotEmpty String newAlgorithm) {
      this.digestAlgorithm = (String)Constraint.isNotNull(StringSupport.trimOrNull(newAlgorithm), "Digest algorithm cannot be empty or null");
   }

   public void createReference(@Nonnull XMLSignature signature) {
      try {
         Transforms dsigTransforms = new Transforms(signature.getDocument());

         for(int i = 0; i < this.transforms.size(); ++i) {
            String transform = (String)this.transforms.get(i);
            dsigTransforms.addTransform(transform);
            if (transform.equals("http://www.w3.org/2001/10/xml-exc-c14n#WithComments") || transform.equals("http://www.w3.org/2001/10/xml-exc-c14n#")) {
               this.processExclusiveTransform(signature, dsigTransforms.item(i));
            }
         }

         if (!Strings.isNullOrEmpty(this.signableObject.getSignatureReferenceID())) {
            signature.addDocument("#" + this.signableObject.getSignatureReferenceID(), dsigTransforms, this.digestAlgorithm);
         } else {
            this.log.debug("SignableSAMLObject had no reference ID, signing using whole document Reference URI");
            signature.addDocument("", dsigTransforms, this.digestAlgorithm);
         }
      } catch (TransformationException var5) {
         this.log.error("Unsupported signature transformation", var5);
      } catch (XMLSignatureException var6) {
         this.log.error("Error adding content reference to signature", var6);
      }

   }

   private void processExclusiveTransform(@Nonnull XMLSignature signature, @Nonnull Transform transform) {
      this.log.debug("Adding list of inclusive namespaces for signature exclusive canonicalization transform");
      LazySet inclusiveNamespacePrefixes = new LazySet();
      this.populateNamespacePrefixes(inclusiveNamespacePrefixes, this.signableObject);
      if (inclusiveNamespacePrefixes != null && inclusiveNamespacePrefixes.size() > 0) {
         InclusiveNamespaces inclusiveNamespaces = new InclusiveNamespaces(signature.getDocument(), inclusiveNamespacePrefixes);
         transform.getElement().appendChild(inclusiveNamespaces.getElement());
      }

   }

   private void populateNamespacePrefixes(@Nonnull @NonnullElements Set namespacePrefixes, @Nonnull XMLObject signatureContent) {
      Iterator var3 = signatureContent.getNamespaceManager().getNonVisibleNamespacePrefixes().iterator();

      while(var3.hasNext()) {
         String prefix = (String)var3.next();
         if (prefix != null) {
            if ("#default".equals(prefix)) {
               namespacePrefixes.add("xmlns");
            } else {
               namespacePrefixes.add(prefix);
            }
         }
      }

   }
}
