package org.apache.jcp.xml.dsig.internal.dom;

import javax.xml.crypto.Data;
import javax.xml.crypto.URIDereferencer;
import javax.xml.crypto.URIReference;
import javax.xml.crypto.URIReferenceException;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.dom.DOMCryptoContext;
import javax.xml.crypto.dom.DOMURIReference;
import org.apache.xml.security.Init;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.apache.xml.security.utils.XMLUtils;
import org.apache.xml.security.utils.resolver.ResourceResolver;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public final class DOMURIDereferencer implements URIDereferencer {
   static final URIDereferencer INSTANCE = new DOMURIDereferencer();

   private DOMURIDereferencer() {
      Init.init();
   }

   public Data dereference(URIReference uriRef, XMLCryptoContext context) throws URIReferenceException {
      if (uriRef == null) {
         throw new NullPointerException("uriRef cannot be null");
      } else if (context == null) {
         throw new NullPointerException("context cannot be null");
      } else {
         DOMURIReference domRef = (DOMURIReference)uriRef;
         Attr uriAttr = (Attr)domRef.getHere();
         String uri = uriRef.getURI();
         DOMCryptoContext dcc = (DOMCryptoContext)context;
         String baseURI = context.getBaseURI();
         boolean secVal = Utils.secureValidation(context);
         if (uri != null && uri.length() != 0 && uri.charAt(0) == '#') {
            String id = uri.substring(1);
            if (id.startsWith("xpointer(id(")) {
               int i1 = id.indexOf(39);
               int i2 = id.indexOf(39, i1 + 1);
               id = id.substring(i1 + 1, i2);
            }

            Node referencedElem = dcc.getElementById(id);
            if (referencedElem != null) {
               if (secVal) {
                  Element start = referencedElem.getOwnerDocument().getDocumentElement();
                  if (!XMLUtils.protectAgainstWrappingAttack(start, (Element)referencedElem, id)) {
                     String error = "Multiple Elements with the same ID " + id + " were detected";
                     throw new URIReferenceException(error);
                  }
               }

               XMLSignatureInput result = new XMLSignatureInput(referencedElem);
               result.setSecureValidation(secVal);
               if (!uri.substring(1).startsWith("xpointer(id(")) {
                  result.setExcludeComments(true);
               }

               result.setMIMEType("text/xml");
               if (baseURI != null && baseURI.length() > 0) {
                  result.setSourceURI(baseURI.concat(uriAttr.getNodeValue()));
               } else {
                  result.setSourceURI(uriAttr.getNodeValue());
               }

               return new ApacheNodeSetData(result);
            }
         }

         try {
            ResourceResolver apacheResolver = ResourceResolver.getInstance(uriAttr, baseURI, secVal);
            XMLSignatureInput in = apacheResolver.resolve(uriAttr, baseURI, secVal);
            return (Data)(in.isOctetStream() ? new ApacheOctetStreamData(in) : new ApacheNodeSetData(in));
         } catch (Exception var13) {
            throw new URIReferenceException(var13);
         }
      }
   }
}
