package org.apache.jcp.xml.dsig.internal.dom;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.Provider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.crypto.KeySelector;
import javax.xml.crypto.KeySelectorException;
import javax.xml.crypto.KeySelectorResult;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.KeySelector.Purpose;
import javax.xml.crypto.dom.DOMCryptoContext;
import javax.xml.crypto.dsig.Manifest;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLObject;
import javax.xml.crypto.dsig.XMLSignContext;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLValidateContext;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import org.apache.xml.security.Init;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public final class DOMXMLSignature extends DOMStructure implements XMLSignature {
   private static final Logger LOG = LoggerFactory.getLogger(DOMXMLSignature.class);
   private String id;
   private XMLSignature.SignatureValue sv;
   private KeyInfo ki;
   private List objects;
   private SignedInfo si;
   private Document ownerDoc = null;
   private Element localSigElem = null;
   private Element sigElem = null;
   private boolean validationStatus;
   private boolean validated = false;
   private KeySelectorResult ksr;
   private Map signatureIdMap;

   public DOMXMLSignature(SignedInfo si, KeyInfo ki, List objs, String id, String signatureValueId) {
      if (si == null) {
         throw new NullPointerException("signedInfo cannot be null");
      } else {
         this.si = si;
         this.id = id;
         this.sv = new DOMSignatureValue(signatureValueId);
         if (objs == null) {
            this.objects = Collections.emptyList();
         } else {
            this.objects = Collections.unmodifiableList(new ArrayList(objs));
            int i = 0;

            for(int size = this.objects.size(); i < size; ++i) {
               if (!(this.objects.get(i) instanceof XMLObject)) {
                  throw new ClassCastException("objs[" + i + "] is not an XMLObject");
               }
            }
         }

         this.ki = ki;
      }
   }

   public DOMXMLSignature(Element sigElem, XMLCryptoContext context, Provider provider) throws MarshalException {
      this.localSigElem = sigElem;
      this.ownerDoc = this.localSigElem.getOwnerDocument();
      this.id = DOMUtils.getAttributeValue(this.localSigElem, "Id");
      Element siElem = DOMUtils.getFirstChildElement(this.localSigElem, "SignedInfo", "http://www.w3.org/2000/09/xmldsig#");
      this.si = new DOMSignedInfo(siElem, context, provider);
      Element sigValElem = DOMUtils.getNextSiblingElement(siElem, "SignatureValue", "http://www.w3.org/2000/09/xmldsig#");
      this.sv = new DOMSignatureValue(sigValElem);
      Element nextSibling = DOMUtils.getNextSiblingElement(sigValElem);
      if (nextSibling != null && nextSibling.getLocalName().equals("KeyInfo") && "http://www.w3.org/2000/09/xmldsig#".equals(nextSibling.getNamespaceURI())) {
         this.ki = new DOMKeyInfo(nextSibling, context, provider);
         nextSibling = DOMUtils.getNextSiblingElement(nextSibling);
      }

      if (nextSibling == null) {
         this.objects = Collections.emptyList();
      } else {
         List tempObjects = new ArrayList();

         while(true) {
            if (nextSibling == null) {
               this.objects = Collections.unmodifiableList(tempObjects);
               break;
            }

            String name = nextSibling.getLocalName();
            String namespace = nextSibling.getNamespaceURI();
            if (!"Object".equals(name) || !"http://www.w3.org/2000/09/xmldsig#".equals(namespace)) {
               throw new MarshalException("Invalid element name: " + namespace + ":" + name + ", expected KeyInfo or Object");
            }

            tempObjects.add(new DOMXMLObject(nextSibling, context, provider));
            nextSibling = DOMUtils.getNextSiblingElement(nextSibling);
         }
      }

   }

   public String getId() {
      return this.id;
   }

   public KeyInfo getKeyInfo() {
      return this.ki;
   }

   public SignedInfo getSignedInfo() {
      return this.si;
   }

   public List getObjects() {
      return this.objects;
   }

   public XMLSignature.SignatureValue getSignatureValue() {
      return this.sv;
   }

   public KeySelectorResult getKeySelectorResult() {
      return this.ksr;
   }

   public void marshal(Node parent, String dsPrefix, DOMCryptoContext context) throws MarshalException {
      this.marshal(parent, (Node)null, dsPrefix, context);
   }

   public void marshal(Node parent, Node nextSibling, String dsPrefix, DOMCryptoContext context) throws MarshalException {
      this.ownerDoc = DOMUtils.getOwnerDocument(parent);
      this.sigElem = DOMUtils.createElement(this.ownerDoc, "Signature", "http://www.w3.org/2000/09/xmldsig#", dsPrefix);
      if (dsPrefix != null && dsPrefix.length() != 0) {
         this.sigElem.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + dsPrefix, "http://www.w3.org/2000/09/xmldsig#");
      } else {
         this.sigElem.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "http://www.w3.org/2000/09/xmldsig#");
      }

      ((DOMSignedInfo)this.si).marshal(this.sigElem, dsPrefix, context);
      ((DOMSignatureValue)this.sv).marshal(this.sigElem, dsPrefix, context);
      if (this.ki != null) {
         ((DOMKeyInfo)this.ki).marshal(this.sigElem, (Node)null, dsPrefix, context);
      }

      int i = 0;

      for(int size = this.objects.size(); i < size; ++i) {
         ((DOMXMLObject)this.objects.get(i)).marshal(this.sigElem, dsPrefix, context);
      }

      DOMUtils.setAttributeID(this.sigElem, "Id", this.id);
      parent.insertBefore(this.sigElem, nextSibling);
   }

   public boolean validate(XMLValidateContext vc) throws XMLSignatureException {
      if (vc == null) {
         throw new NullPointerException("validateContext is null");
      } else if (!(vc instanceof DOMValidateContext)) {
         throw new ClassCastException("validateContext must be of type DOMValidateContext");
      } else if (this.validated) {
         return this.validationStatus;
      } else {
         boolean sigValidity = this.sv.validate(vc);
         if (!sigValidity) {
            this.validationStatus = false;
            this.validated = true;
            return this.validationStatus;
         } else {
            List refs = this.si.getReferences();
            boolean validateRefs = true;
            int i = 0;

            int i;
            for(i = refs.size(); validateRefs && i < i; ++i) {
               Reference ref = (Reference)refs.get(i);
               boolean refValid = ref.validate(vc);
               LOG.debug("Reference [{}] is valid: {}", ref.getURI(), refValid);
               validateRefs &= refValid;
            }

            if (!validateRefs) {
               LOG.debug("Couldn't validate the References");
               this.validationStatus = false;
               this.validated = true;
               return this.validationStatus;
            } else {
               boolean validateMans = true;
               if (Boolean.TRUE.equals(vc.getProperty("org.jcp.xml.dsig.validateManifests"))) {
                  i = 0;

                  for(int size = this.objects.size(); validateMans && i < size; ++i) {
                     XMLObject xo = (XMLObject)this.objects.get(i);
                     List content = xo.getContent();
                     int csize = content.size();

                     for(int j = 0; validateMans && j < csize; ++j) {
                        XMLStructure xs = (XMLStructure)content.get(j);
                        if (xs instanceof Manifest) {
                           LOG.debug("validating manifest");
                           Manifest man = (Manifest)xs;
                           List manRefs = man.getReferences();
                           int rsize = manRefs.size();

                           for(int k = 0; validateMans && k < rsize; ++k) {
                              Reference ref = (Reference)manRefs.get(k);
                              boolean refValid = ref.validate(vc);
                              LOG.debug("Manifest ref [{}] is valid: {}", ref.getURI(), refValid);
                              validateMans &= refValid;
                           }
                        }
                     }
                  }
               }

               this.validationStatus = validateMans;
               this.validated = true;
               return this.validationStatus;
            }
         }
      }
   }

   public void sign(XMLSignContext signContext) throws MarshalException, XMLSignatureException {
      if (signContext == null) {
         throw new NullPointerException("signContext cannot be null");
      } else {
         DOMSignContext context = (DOMSignContext)signContext;
         this.marshal(context.getParent(), context.getNextSibling(), DOMUtils.getSignaturePrefix(context), context);
         List allReferences = new ArrayList();
         this.signatureIdMap = new HashMap();
         this.signatureIdMap.put(this.id, this);
         this.signatureIdMap.put(this.si.getId(), this.si);
         List refs = this.si.getReferences();
         Iterator signingKey = refs.iterator();

         Reference ref;
         while(signingKey.hasNext()) {
            ref = (Reference)signingKey.next();
            this.signatureIdMap.put(ref.getId(), ref);
         }

         signingKey = this.objects.iterator();

         label80:
         while(signingKey.hasNext()) {
            XMLObject obj = (XMLObject)signingKey.next();
            this.signatureIdMap.put(obj.getId(), obj);
            List content = obj.getContent();
            Iterator var8 = content.iterator();

            while(true) {
               XMLStructure xs;
               do {
                  if (!var8.hasNext()) {
                     continue label80;
                  }

                  xs = (XMLStructure)var8.next();
               } while(!(xs instanceof Manifest));

               Manifest man = (Manifest)xs;
               this.signatureIdMap.put(man.getId(), man);
               List manRefs = man.getReferences();
               Iterator var12 = manRefs.iterator();

               while(var12.hasNext()) {
                  Reference ref = (Reference)var12.next();
                  allReferences.add(ref);
                  this.signatureIdMap.put(ref.getId(), ref);
               }
            }
         }

         allReferences.addAll(refs);
         signingKey = allReferences.iterator();

         while(signingKey.hasNext()) {
            ref = (Reference)signingKey.next();
            this.digestReference((DOMReference)ref, signContext);
         }

         signingKey = allReferences.iterator();

         while(signingKey.hasNext()) {
            ref = (Reference)signingKey.next();
            if (!((DOMReference)ref).isDigested()) {
               ((DOMReference)ref).digest(signContext);
            }
         }

         signingKey = null;

         Key signingKey;
         try {
            KeySelectorResult keySelectorResult = signContext.getKeySelector().select(this.ki, Purpose.SIGN, this.si.getSignatureMethod(), signContext);
            signingKey = keySelectorResult.getKey();
            if (signingKey == null) {
               throw new XMLSignatureException("the keySelector did not find a signing key");
            }

            this.ksr = keySelectorResult;
         } catch (KeySelectorException var15) {
            throw new XMLSignatureException("cannot find signing key", var15);
         }

         try {
            byte[] val = ((AbstractDOMSignatureMethod)this.si.getSignatureMethod()).sign(signingKey, this.si, signContext);
            ((DOMSignatureValue)this.sv).setValue(val);
         } catch (InvalidKeyException var14) {
            throw new XMLSignatureException(var14);
         }

         this.localSigElem = this.sigElem;
      }
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof XMLSignature)) {
         return false;
      } else {
         XMLSignature osig = (XMLSignature)o;
         boolean idEqual = this.id == null ? osig.getId() == null : this.id.equals(osig.getId());
         boolean keyInfoEqual = this.ki == null ? osig.getKeyInfo() == null : this.ki.equals(osig.getKeyInfo());
         return idEqual && keyInfoEqual && this.sv.equals(osig.getSignatureValue()) && this.si.equals(osig.getSignedInfo()) && this.objects.equals(osig.getObjects());
      }
   }

   public int hashCode() {
      int result = 17;
      if (this.id != null) {
         result = 31 * result + this.id.hashCode();
      }

      if (this.ki != null) {
         result = 31 * result + this.ki.hashCode();
      }

      result = 31 * result + this.sv.hashCode();
      result = 31 * result + this.si.hashCode();
      result = 31 * result + this.objects.hashCode();
      return result;
   }

   private void digestReference(DOMReference ref, XMLSignContext signContext) throws XMLSignatureException {
      if (!ref.isDigested()) {
         String uri = ref.getURI();
         if (Utils.sameDocumentURI(uri)) {
            String parsedId = Utils.parseIdFromSameDocumentURI(uri);
            if (parsedId != null && this.signatureIdMap.containsKey(parsedId)) {
               XMLStructure xs = (XMLStructure)this.signatureIdMap.get(parsedId);
               if (xs instanceof DOMReference) {
                  this.digestReference((DOMReference)xs, signContext);
               } else if (xs instanceof Manifest) {
                  Manifest man = (Manifest)xs;
                  List manRefs = DOMManifest.getManifestReferences(man);
                  int i = 0;

                  for(int size = manRefs.size(); i < size; ++i) {
                     this.digestReference((DOMReference)manRefs.get(i), signContext);
                  }
               }
            }

            if (uri.length() == 0) {
               List transforms = ref.getTransforms();
               Iterator var11 = transforms.iterator();

               while(var11.hasNext()) {
                  Transform transform = (Transform)var11.next();
                  String transformAlg = transform.getAlgorithm();
                  if (transformAlg.equals("http://www.w3.org/TR/1999/REC-xpath-19991116") || transformAlg.equals("http://www.w3.org/2002/06/xmldsig-filter2")) {
                     return;
                  }
               }
            }
         }

         ref.digest(signContext);
      }
   }

   static {
      Init.init();
   }

   public class DOMSignatureValue extends DOMStructure implements XMLSignature.SignatureValue {
      private String id;
      private byte[] value;
      private String valueBase64;
      private Element sigValueElem;
      private boolean validated = false;
      private boolean validationStatus;

      DOMSignatureValue(String id) {
         this.id = id;
      }

      DOMSignatureValue(Element sigValueElem) throws MarshalException {
         String content = XMLUtils.getFullTextChildrenFromNode(sigValueElem);
         this.value = XMLUtils.decode(content);
         Attr attr = sigValueElem.getAttributeNodeNS((String)null, "Id");
         if (attr != null) {
            this.id = attr.getValue();
            sigValueElem.setIdAttributeNode(attr, true);
         } else {
            this.id = null;
         }

         this.sigValueElem = sigValueElem;
      }

      public String getId() {
         return this.id;
      }

      public byte[] getValue() {
         return this.value == null ? null : (byte[])this.value.clone();
      }

      public String getEncodedValue() {
         return this.valueBase64;
      }

      public boolean validate(XMLValidateContext validateContext) throws XMLSignatureException {
         if (validateContext == null) {
            throw new NullPointerException("context cannot be null");
         } else if (this.validated) {
            return this.validationStatus;
         } else {
            SignatureMethod sm = DOMXMLSignature.this.si.getSignatureMethod();
            Key validationKey = null;
            KeySelectorResult ksResult = null;

            try {
               KeySelector keySelector = validateContext.getKeySelector();
               if (keySelector != null) {
                  ksResult = keySelector.select(DOMXMLSignature.this.ki, Purpose.VERIFY, sm, validateContext);
                  if (ksResult != null) {
                     validationKey = ksResult.getKey();
                  }
               }

               if (validationKey == null) {
                  throw new XMLSignatureException("the keyselector did not find a validation key");
               }
            } catch (KeySelectorException var7) {
               throw new XMLSignatureException("cannot find validation key", var7);
            }

            try {
               this.validationStatus = ((AbstractDOMSignatureMethod)sm).verify(validationKey, DOMXMLSignature.this.si, this.value, validateContext);
            } catch (Exception var6) {
               throw new XMLSignatureException(var6);
            }

            this.validated = true;
            DOMXMLSignature.this.ksr = ksResult;
            return this.validationStatus;
         }
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (!(o instanceof XMLSignature.SignatureValue)) {
            return false;
         } else {
            XMLSignature.SignatureValue osv = (XMLSignature.SignatureValue)o;
            boolean idEqual = this.id == null ? osv.getId() == null : this.id.equals(osv.getId());
            return idEqual;
         }
      }

      public int hashCode() {
         int result = 17;
         if (this.id != null) {
            result = 31 * result + this.id.hashCode();
         }

         return result;
      }

      public void marshal(Node parent, String dsPrefix, DOMCryptoContext context) throws MarshalException {
         this.sigValueElem = DOMUtils.createElement(DOMXMLSignature.this.ownerDoc, "SignatureValue", "http://www.w3.org/2000/09/xmldsig#", dsPrefix);
         if (this.valueBase64 != null) {
            this.sigValueElem.appendChild(DOMXMLSignature.this.ownerDoc.createTextNode(this.valueBase64));
         }

         DOMUtils.setAttributeID(this.sigValueElem, "Id", this.id);
         parent.appendChild(this.sigValueElem);
      }

      void setValue(byte[] value) {
         this.value = value;
         this.valueBase64 = XMLUtils.encodeToString(value);
         this.sigValueElem.appendChild(DOMXMLSignature.this.ownerDoc.createTextNode(this.valueBase64));
      }
   }
}
