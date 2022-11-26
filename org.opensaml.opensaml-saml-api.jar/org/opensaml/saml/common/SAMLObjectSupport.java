package org.opensaml.saml.common;

import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import org.opensaml.core.xml.Namespace;
import org.opensaml.xmlsec.signature.support.ContentReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SAMLObjectSupport {
   private SAMLObjectSupport() {
   }

   public static void declareNonVisibleNamespaces(@Nonnull SignableSAMLObject signableObject) {
      Logger log = getLogger();
      if (signableObject.getDOM() == null && signableObject.getSignature() != null) {
         log.debug("Examining signed object for content references with exclusive canonicalization transform");
         boolean sawExclusive = false;
         Iterator var3 = signableObject.getSignature().getContentReferences().iterator();

         label36: {
            List transforms;
            do {
               ContentReference cr;
               do {
                  if (!var3.hasNext()) {
                     break label36;
                  }

                  cr = (ContentReference)var3.next();
               } while(!(cr instanceof SAMLObjectContentReference));

               transforms = ((SAMLObjectContentReference)cr).getTransforms();
            } while(!transforms.contains("http://www.w3.org/2001/10/xml-exc-c14n#WithComments") && !transforms.contains("http://www.w3.org/2001/10/xml-exc-c14n#"));

            sawExclusive = true;
         }

         if (sawExclusive) {
            log.debug("Saw exclusive transform, declaring non-visible namespaces on signed object");
            var3 = signableObject.getNamespaceManager().getNonVisibleNamespaces().iterator();

            while(var3.hasNext()) {
               Namespace ns = (Namespace)var3.next();
               signableObject.getNamespaceManager().registerNamespaceDeclaration(ns);
            }
         }
      }

   }

   @Nonnull
   private static Logger getLogger() {
      return LoggerFactory.getLogger(SAMLObjectSupport.class);
   }
}
