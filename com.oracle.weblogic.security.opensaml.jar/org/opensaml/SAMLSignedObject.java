package org.opensaml;

import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import weblogic.xml.crypto.api.AlgorithmMethod;
import weblogic.xml.crypto.api.KeySelector;
import weblogic.xml.crypto.api.KeySelectorException;
import weblogic.xml.crypto.api.KeySelectorResult;
import weblogic.xml.crypto.api.MarshalException;
import weblogic.xml.crypto.api.NodeSetData;
import weblogic.xml.crypto.api.URIReferenceException;
import weblogic.xml.crypto.api.XMLCryptoContext;
import weblogic.xml.crypto.api.XMLStructure;
import weblogic.xml.crypto.dom.WLDOMSignContextImpl;
import weblogic.xml.crypto.dom.WLDOMValidateContextImpl;
import weblogic.xml.crypto.dsig.api.Reference;
import weblogic.xml.crypto.dsig.api.SignedInfo;
import weblogic.xml.crypto.dsig.api.Transform;
import weblogic.xml.crypto.dsig.api.XMLSignature;
import weblogic.xml.crypto.dsig.api.XMLSignatureException;
import weblogic.xml.crypto.dsig.api.XMLSignatureFactory;
import weblogic.xml.crypto.dsig.api.keyinfo.KeyInfo;
import weblogic.xml.crypto.dsig.api.keyinfo.KeyInfoFactory;
import weblogic.xml.crypto.dsig.api.keyinfo.X509Data;
import weblogic.xml.crypto.dsig.api.spec.C14NMethodParameterSpec;
import weblogic.xml.crypto.dsig.api.spec.DigestMethodParameterSpec;
import weblogic.xml.crypto.dsig.api.spec.ExcC14NParameterSpec;
import weblogic.xml.crypto.dsig.api.spec.SignatureMethodParameterSpec;
import weblogic.xml.crypto.dsig.api.spec.TransformParameterSpec;

public abstract class SAMLSignedObject extends SAMLObject implements Cloneable {
   private XMLSignature sig = null;
   private Element sigElement = null;

   public Object getNativeSignature() {
      return this.sig;
   }

   public abstract String getId();

   public void fromDOM(Element var1) throws SAMLException {
      super.fromDOM(var1);
      Element var2 = XML.getFirstChildElement(var1, "http://www.w3.org/2000/09/xmldsig#", "Signature");
      if (var2 != null) {
         this.sigElement = var2;
      }

   }

   public Node toDOM() throws SAMLException {
      if (this.root != null) {
         return this.root;
      } else {
         return this.sigElement != null ? this.toDOM(this.sigElement.getOwnerDocument()) : super.toDOM();
      }
   }

   protected abstract Node insertSignature() throws SAMLException;

   protected Element getSignatureElement() {
      return this.sigElement;
   }

   public void unsign() {
      if (this.sigElement != null && this.sigElement.getParentNode() != null) {
         this.sigElement.getParentNode().removeChild(this.sigElement);
      }

      this.sigElement = null;
      this.sig = null;
   }

   public void sign(String var1, Key var2, Collection var3) throws SAMLException {
      this.unsign();
      this.toDOM();
      this.plantRoot();

      try {
         logDebug("SAMLSignedObject.sign(): algorithm '" + var1 + "'");
         XMLSignatureFactory var4 = XMLSignatureFactory.getInstance("DOM", "weblogic.xml.crypto.dsig.XMLSignatureFactoryImpl");
         Node var5 = this.insertSignature();
         String var6;
         if (this.config.getBooleanProperty("org.opensaml.compatibility-mode")) {
            var6 = "";
         } else {
            var6 = "#" + this.getId();
         }

         logDebug("SAMLSignedObject.sign(): reference '" + var6 + "'");
         ExcC14NParameterSpec var7 = null;
         String var8 = this.config.getProperty("org.opensaml.inclusive-namespace-prefixes");
         if (var8 != null && var8.length() > 0) {
            List var9 = Arrays.asList(var8.trim().split(" "));
            var7 = new ExcC14NParameterSpec(var9);
            logDebug("SAMLSignedObject.sign(): InclusiveNamespaces '" + var8 + "'");
         }

         ArrayList var20 = new ArrayList();
         var20.add(var4.newTransform("http://www.w3.org/2000/09/xmldsig#enveloped-signature", (TransformParameterSpec)null));
         var20.add(var4.newTransform("http://www.w3.org/2001/10/xml-exc-c14n#", var7));
         Reference var10 = var4.newReference(var6, var4.newDigestMethod("http://www.w3.org/2000/09/xmldsig#sha1", (DigestMethodParameterSpec)null), var20, (String)null, (String)null);
         SignedInfo var11 = var4.newSignedInfo(var4.newCanonicalizationMethod("http://www.w3.org/2001/10/xml-exc-c14n#", (C14NMethodParameterSpec)null), var4.newSignatureMethod(var1, (SignatureMethodParameterSpec)null), Collections.singletonList(var10));
         ArrayList var12 = new ArrayList();
         if (var3 != null) {
            int var13 = 0;

            for(Iterator var14 = var3.iterator(); var14.hasNext(); ++var13) {
               Object var15 = var14.next();
               if (var15 instanceof X509Certificate) {
                  if (!var14.hasNext() && var13 > 0 && ((X509Certificate)var15).getSubjectDN().equals(((X509Certificate)var15).getIssuerDN())) {
                     break;
                  }

                  var12.add((X509Certificate)var15);
               }
            }
         }

         KeyInfo var21 = null;
         if (!var12.isEmpty()) {
            logDebug("SAMLSignedObject.sign(): adding certificates");
            KeyInfoFactory var22 = var4.getKeyInfoFactory();
            X509Data var24 = var22.newX509Data(var12);
            var21 = var22.newKeyInfo(Collections.singletonList(var24));
         }

         WLDOMSignContextImpl var23 = new WLDOMSignContextImpl(var2, this.root);
         if (var5 != null) {
            var23.setNextSibling(var5);
         }

         logDebug("SAMLSignedObject.sign(): signing object");
         this.sig = var4.newXMLSignature(var11, var21);
         this.sig.sign(var23);
         Element var25 = XML.getFirstChildElement(this.root, "http://www.w3.org/2000/09/xmldsig#", "Signature");
         if (var25 != null) {
            this.sigElement = var25;
         }

         logDebug("SAMLSignedObject.sign(): completed");
      } catch (XMLSignatureException var16) {
         this.unsign();
         logDebugException("SAMLSignedObject.sign():", var16);
         throw new InvalidCryptoException("SAMLSignedObject.sign() detected an XML signature exception: " + var16.getMessage(), var16);
      } catch (MarshalException var17) {
         this.unsign();
         logDebugException("SAMLSignedObject.sign():", var17);
         throw new InvalidCryptoException("SAMLSignedObject.sign() detected an MarshalException exception: " + var17.getMessage(), var17);
      } catch (NoSuchAlgorithmException var18) {
         logDebugException("SAMLSignedObject.sign():", var18);
         throw new InvalidCryptoException("SAMLSignedObject.sign() detected an NoSuchAlgorithmException exception: " + var18.getMessage(), var18);
      } catch (InvalidAlgorithmParameterException var19) {
         logDebugException("SAMLSignedObject.sign():", var19);
         throw new InvalidCryptoException("SAMLSignedObject.sign() detected an InvalidAlgorithmParameterException exception: " + var19.getMessage(), var19);
      }
   }

   public void verify() throws SAMLException {
      this.verify((Key)null);
   }

   public void verify(Certificate var1) throws SAMLException {
      this.verify((Key)var1.getPublicKey());
   }

   public void verify(Key var1) throws SAMLException {
      if (!this.isSigned()) {
         throw new InvalidCryptoException("SAMLSignedObject.verify() can't verify unsigned object");
      } else {
         try {
            WLDOMValidateContextImpl var2;
            if (var1 == null) {
               logDebug("SAMLSignedObject.verify(): X.509 key selector");
               var2 = new WLDOMValidateContextImpl(new X509KeySelector(), this.sigElement);
            } else {
               logDebug("SAMLSignedObject.verify(): key supplied");
               var2 = new WLDOMValidateContextImpl(var1, this.sigElement);
            }

            XMLSignatureFactory var3 = XMLSignatureFactory.getInstance();
            this.sig = var3.unmarshalXMLSignature(var2);
            logDebug("SAMLSignedObject.verify(): obtained signed info");
            Reference var4 = null;
            boolean var5 = false;
            List var6 = this.sig.getSignedInfo().getReferences();
            if (var6.size() == 1) {
               var4 = (Reference)var6.get(0);
               if (var4.getURI() == null || var4.getURI().equals("") || var4.getURI().equals("#" + this.getId())) {
                  Iterator var7 = var4.getTransforms().iterator();

                  while(var7.hasNext()) {
                     Transform var8 = (Transform)var7.next();
                     if (var8.getAlgorithm().equals("http://www.w3.org/2000/09/xmldsig#enveloped-signature")) {
                        var5 = true;
                     } else if (!var8.getAlgorithm().equals("http://www.w3.org/2001/10/xml-exc-c14n#")) {
                        var5 = false;
                        break;
                     }
                  }
               }
            }

            if (!var5) {
               throw new InvalidCryptoException("SAMLSignedObject.verify() detected an invalid signature profile");
            } else {
               logDebug("SAMLSignedObject.verify(): validating signature");
               if (!this.sig.validate(var2)) {
                  throw new InvalidCryptoException("SAMLSignedObject.verify() failed to validate signature value");
               } else {
                  logDebug("SAMLSignedObject.verify(): validating signature reference");
                  Node var14 = null;
                  NodeSetData var15 = (NodeSetData)var2.getURIDereferencer().dereference(var4, var2);
                  Iterator var9 = var15.iterator();
                  if (var9.hasNext()) {
                     var14 = (Node)var9.next();
                  }

                  Node var10 = this.toDOM();
                  if (var14 != null && var10 != null && var10.isSameNode(var14)) {
                     logDebug("SAMLSignedObject.verify(): completed");
                  } else {
                     throw new InvalidCryptoException("SAMLSignedObject.verify() failed to validate signature reference");
                  }
               }
            }
         } catch (XMLSignatureException var11) {
            logDebugException("SAMLSignedObject.verify():", var11);
            throw new InvalidCryptoException("SAMLSignedObject.verify() detected an XML signature exception: " + var11.getMessage(), var11);
         } catch (MarshalException var12) {
            logDebugException("SAMLSignedObject.verify():", var12);
            throw new InvalidCryptoException("SAMLSignedObject.verify() detected an MarshalException exception: " + var12.getMessage(), var12);
         } catch (URIReferenceException var13) {
            logDebugException("SAMLSignedObject.verify():", var13);
            throw new InvalidCryptoException("SAMLSignedObject.verify() detected a URI reference exception: " + var13.getMessage(), var13);
         }
      }
   }

   public Iterator getX509Certificates() throws SAMLException {
      if (this.isSigned()) {
         if (this.sig == null) {
            throw new InvalidCryptoException("SAMLSignedObject.getX509Certificates(): Use sign() or verify() before obtaining certificates");
         } else {
            KeyInfo var1 = this.sig.getKeyInfo();
            if (var1 != null) {
               ArrayList var2 = new ArrayList();
               Iterator var3 = var1.getContent().iterator();

               while(true) {
                  XMLStructure var4;
                  do {
                     if (!var3.hasNext()) {
                        if (!var2.isEmpty()) {
                           return var2.iterator();
                        }

                        throw new InvalidCryptoException("SAMLSignedObject.getX509Certificates() can't find any X.509 certificates in signature");
                     }

                     var4 = (XMLStructure)var3.next();
                  } while(!(var4 instanceof X509Data));

                  X509Data var5 = (X509Data)var4;
                  Iterator var6 = var5.getContent().iterator();

                  while(var6.hasNext()) {
                     Object var7 = var6.next();
                     if (var7 instanceof X509Certificate) {
                        var2.add((X509Certificate)var7);
                     }
                  }
               }
            } else {
               throw new InvalidCryptoException("SAMLSignedObject.getX509Certificates() can't find any X.509 certificates in signature");
            }
         }
      } else {
         throw new InvalidCryptoException("SAMLSignedObject.getX509Certificates() can't examine unsigned object");
      }
   }

   public String getSignatureAlgorithm() throws SAMLException {
      if (this.isSigned()) {
         if (this.sig == null) {
            throw new InvalidCryptoException("SAMLSignedObject.getSignatureAlgorithm(): Use sign() or verify() before obtaining signature algorithm");
         } else {
            return this.sig.getSignedInfo().getSignatureMethod().getAlgorithm();
         }
      } else {
         throw new InvalidCryptoException("SAMLSignedObject.getSignatureAlgorithm() can't examine unsigned object");
      }
   }

   public boolean isSigned() {
      return this.sigElement != null;
   }

   protected Object clone() throws CloneNotSupportedException {
      SAMLSignedObject var1 = (SAMLSignedObject)super.clone();
      var1.sig = null;
      var1.sigElement = null;
      return var1;
   }

   private class X509KeySelectorResult implements KeySelectorResult {
      private Key key;

      public Key getKey() {
         return this.key;
      }

      X509KeySelectorResult(Key var2) {
         this.key = var2;
      }
   }

   private class X509KeySelector extends KeySelector {
      private X509KeySelector() {
      }

      public KeySelectorResult select(KeyInfo var1, KeySelector.Purpose var2, AlgorithmMethod var3, XMLCryptoContext var4) throws KeySelectorException {
         PublicKey var5 = null;
         if (var1 != null) {
            Iterator var6 = var1.getContent().iterator();

            do {
               XMLStructure var7;
               do {
                  if (!var6.hasNext()) {
                     return SAMLSignedObject.this.new X509KeySelectorResult(var5);
                  }

                  var7 = (XMLStructure)var6.next();
               } while(!(var7 instanceof X509Data));

               X509Data var8 = (X509Data)var7;
               Iterator var9 = var8.getContent().iterator();

               while(var9.hasNext()) {
                  Object var10 = var9.next();
                  if (var10 instanceof X509Certificate) {
                     var5 = ((X509Certificate)var10).getPublicKey();
                     break;
                  }
               }
            } while(var5 == null);
         }

         return SAMLSignedObject.this.new X509KeySelectorResult(var5);
      }

      // $FF: synthetic method
      X509KeySelector(Object var2) {
         this();
      }
   }
}
