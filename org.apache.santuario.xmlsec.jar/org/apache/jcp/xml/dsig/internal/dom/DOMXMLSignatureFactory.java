package org.apache.jcp.xml.dsig.internal.dom;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.xml.crypto.Data;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.URIDereferencer;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dom.DOMCryptoContext;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Manifest;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignatureProperties;
import javax.xml.crypto.dsig.SignatureProperty;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.TransformService;
import javax.xml.crypto.dsig.XMLObject;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.XMLValidateContext;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.DigestMethodParameterSpec;
import javax.xml.crypto.dsig.spec.SignatureMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public final class DOMXMLSignatureFactory extends XMLSignatureFactory {
   public XMLSignature newXMLSignature(SignedInfo si, KeyInfo ki) {
      return new DOMXMLSignature(si, ki, (List)null, (String)null, (String)null);
   }

   public XMLSignature newXMLSignature(SignedInfo si, KeyInfo ki, List objects, String id, String signatureValueId) {
      return new DOMXMLSignature(si, ki, objects, id, signatureValueId);
   }

   public Reference newReference(String uri, DigestMethod dm) {
      return this.newReference(uri, dm, (List)null, (String)null, (String)null);
   }

   public Reference newReference(String uri, DigestMethod dm, List transforms, String type, String id) {
      return new DOMReference(uri, type, dm, transforms, id, this.getProvider());
   }

   public Reference newReference(String uri, DigestMethod dm, List appliedTransforms, Data result, List transforms, String type, String id) {
      if (appliedTransforms == null) {
         throw new NullPointerException("appliedTransforms cannot be null");
      } else if (appliedTransforms.isEmpty()) {
         throw new NullPointerException("appliedTransforms cannot be empty");
      } else if (result == null) {
         throw new NullPointerException("result cannot be null");
      } else {
         return new DOMReference(uri, type, dm, appliedTransforms, result, transforms, id, this.getProvider());
      }
   }

   public Reference newReference(String uri, DigestMethod dm, List transforms, String type, String id, byte[] digestValue) {
      if (digestValue == null) {
         throw new NullPointerException("digestValue cannot be null");
      } else {
         return new DOMReference(uri, type, dm, (List)null, (Data)null, transforms, id, digestValue, this.getProvider());
      }
   }

   public SignedInfo newSignedInfo(CanonicalizationMethod cm, SignatureMethod sm, List references) {
      return this.newSignedInfo(cm, sm, references, (String)null);
   }

   public SignedInfo newSignedInfo(CanonicalizationMethod cm, SignatureMethod sm, List references, String id) {
      return new DOMSignedInfo(cm, sm, references, id);
   }

   public XMLObject newXMLObject(List content, String id, String mimeType, String encoding) {
      return new DOMXMLObject(content, id, mimeType, encoding);
   }

   public Manifest newManifest(List references) {
      return this.newManifest(references, (String)null);
   }

   public Manifest newManifest(List references, String id) {
      return new DOMManifest(references, id);
   }

   public SignatureProperties newSignatureProperties(List props, String id) {
      return new DOMSignatureProperties(props, id);
   }

   public SignatureProperty newSignatureProperty(List info, String target, String id) {
      return new DOMSignatureProperty(info, target, id);
   }

   public XMLSignature unmarshalXMLSignature(XMLValidateContext context) throws MarshalException {
      if (context == null) {
         throw new NullPointerException("context cannot be null");
      } else {
         return this.unmarshal(((DOMValidateContext)context).getNode(), context);
      }
   }

   public XMLSignature unmarshalXMLSignature(XMLStructure xmlStructure) throws MarshalException {
      if (xmlStructure == null) {
         throw new NullPointerException("xmlStructure cannot be null");
      } else if (!(xmlStructure instanceof javax.xml.crypto.dom.DOMStructure)) {
         throw new ClassCastException("xmlStructure must be of type DOMStructure");
      } else {
         return this.unmarshal(((javax.xml.crypto.dom.DOMStructure)xmlStructure).getNode(), new UnmarshalContext());
      }
   }

   private XMLSignature unmarshal(Node node, XMLCryptoContext context) throws MarshalException {
      node.normalize();
      Element element = null;
      if (node.getNodeType() == 9) {
         element = ((Document)node).getDocumentElement();
      } else {
         if (node.getNodeType() != 1) {
            throw new MarshalException("Signature element is not a proper Node");
         }

         element = (Element)node;
      }

      String tag = element.getLocalName();
      String namespace = element.getNamespaceURI();
      if (tag != null && namespace != null) {
         if ("Signature".equals(tag) && "http://www.w3.org/2000/09/xmldsig#".equals(namespace)) {
            return new DOMXMLSignature(element, context, this.getProvider());
         } else {
            throw new MarshalException("invalid Signature tag: " + namespace + ":" + tag);
         }
      } else {
         throw new MarshalException("Document implementation must support DOM Level 2 and be namespace aware");
      }
   }

   public boolean isFeatureSupported(String feature) {
      if (feature == null) {
         throw new NullPointerException();
      } else {
         return false;
      }
   }

   public DigestMethod newDigestMethod(String algorithm, DigestMethodParameterSpec params) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
      if (algorithm == null) {
         throw new NullPointerException();
      } else if (algorithm.equals("http://www.w3.org/2000/09/xmldsig#sha1")) {
         return new DOMDigestMethod.SHA1(params);
      } else if (algorithm.equals("http://www.w3.org/2001/04/xmldsig-more#sha224")) {
         return new DOMDigestMethod.SHA224(params);
      } else if (algorithm.equals("http://www.w3.org/2001/04/xmlenc#sha256")) {
         return new DOMDigestMethod.SHA256(params);
      } else if (algorithm.equals("http://www.w3.org/2001/04/xmldsig-more#sha384")) {
         return new DOMDigestMethod.SHA384(params);
      } else if (algorithm.equals("http://www.w3.org/2001/04/xmlenc#sha512")) {
         return new DOMDigestMethod.SHA512(params);
      } else if (algorithm.equals("http://www.w3.org/2001/04/xmlenc#ripemd160")) {
         return new DOMDigestMethod.RIPEMD160(params);
      } else if (algorithm.equals("http://www.w3.org/2007/05/xmldsig-more#whirlpool")) {
         return new DOMDigestMethod.WHIRLPOOL(params);
      } else if (algorithm.equals("http://www.w3.org/2007/05/xmldsig-more#sha3-224")) {
         return new DOMDigestMethod.SHA3_224(params);
      } else if (algorithm.equals("http://www.w3.org/2007/05/xmldsig-more#sha3-256")) {
         return new DOMDigestMethod.SHA3_256(params);
      } else if (algorithm.equals("http://www.w3.org/2007/05/xmldsig-more#sha3-384")) {
         return new DOMDigestMethod.SHA3_384(params);
      } else if (algorithm.equals("http://www.w3.org/2007/05/xmldsig-more#sha3-512")) {
         return new DOMDigestMethod.SHA3_512(params);
      } else {
         throw new NoSuchAlgorithmException("unsupported algorithm");
      }
   }

   public SignatureMethod newSignatureMethod(String algorithm, SignatureMethodParameterSpec params) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
      if (algorithm == null) {
         throw new NullPointerException();
      } else if (algorithm.equals("http://www.w3.org/2000/09/xmldsig#rsa-sha1")) {
         return new DOMSignatureMethod.SHA1withRSA(params);
      } else if (algorithm.equals("http://www.w3.org/2001/04/xmldsig-more#rsa-sha224")) {
         return new DOMSignatureMethod.SHA224withRSA(params);
      } else if (algorithm.equals("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256")) {
         return new DOMSignatureMethod.SHA256withRSA(params);
      } else if (algorithm.equals("http://www.w3.org/2001/04/xmldsig-more#rsa-sha384")) {
         return new DOMSignatureMethod.SHA384withRSA(params);
      } else if (algorithm.equals("http://www.w3.org/2001/04/xmldsig-more#rsa-sha512")) {
         return new DOMSignatureMethod.SHA512withRSA(params);
      } else if (algorithm.equals("http://www.w3.org/2001/04/xmldsig-more#rsa-ripemd160")) {
         return new DOMSignatureMethod.RIPEMD160withRSA(params);
      } else if (algorithm.equals("http://www.w3.org/2007/05/xmldsig-more#sha1-rsa-MGF1")) {
         return new DOMSignatureMethod.SHA1withRSAandMGF1(params);
      } else if (algorithm.equals("http://www.w3.org/2007/05/xmldsig-more#sha224-rsa-MGF1")) {
         return new DOMSignatureMethod.SHA224withRSAandMGF1(params);
      } else if (algorithm.equals("http://www.w3.org/2007/05/xmldsig-more#sha256-rsa-MGF1")) {
         return new DOMSignatureMethod.SHA256withRSAandMGF1(params);
      } else if (algorithm.equals("http://www.w3.org/2007/05/xmldsig-more#sha384-rsa-MGF1")) {
         return new DOMSignatureMethod.SHA384withRSAandMGF1(params);
      } else if (algorithm.equals("http://www.w3.org/2007/05/xmldsig-more#sha512-rsa-MGF1")) {
         return new DOMSignatureMethod.SHA512withRSAandMGF1(params);
      } else if (algorithm.equals("http://www.w3.org/2007/05/xmldsig-more#ripemd160-rsa-MGF1")) {
         return new DOMSignatureMethod.RIPEMD160withRSAandMGF1(params);
      } else if (algorithm.equals("http://www.w3.org/2000/09/xmldsig#dsa-sha1")) {
         return new DOMSignatureMethod.SHA1withDSA(params);
      } else if (algorithm.equals("http://www.w3.org/2009/xmldsig11#dsa-sha256")) {
         return new DOMSignatureMethod.SHA256withDSA(params);
      } else if (algorithm.equals("http://www.w3.org/2000/09/xmldsig#hmac-sha1")) {
         return new DOMHMACSignatureMethod.SHA1(params);
      } else if (algorithm.equals("http://www.w3.org/2001/04/xmldsig-more#hmac-sha224")) {
         return new DOMHMACSignatureMethod.SHA224(params);
      } else if (algorithm.equals("http://www.w3.org/2001/04/xmldsig-more#hmac-sha256")) {
         return new DOMHMACSignatureMethod.SHA256(params);
      } else if (algorithm.equals("http://www.w3.org/2001/04/xmldsig-more#hmac-sha384")) {
         return new DOMHMACSignatureMethod.SHA384(params);
      } else if (algorithm.equals("http://www.w3.org/2001/04/xmldsig-more#hmac-sha512")) {
         return new DOMHMACSignatureMethod.SHA512(params);
      } else if (algorithm.equals("http://www.w3.org/2001/04/xmldsig-more#hmac-ripemd160")) {
         return new DOMHMACSignatureMethod.RIPEMD160(params);
      } else if (algorithm.equals("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha1")) {
         return new DOMSignatureMethod.SHA1withECDSA(params);
      } else if (algorithm.equals("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha224")) {
         return new DOMSignatureMethod.SHA224withECDSA(params);
      } else if (algorithm.equals("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha256")) {
         return new DOMSignatureMethod.SHA256withECDSA(params);
      } else if (algorithm.equals("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha384")) {
         return new DOMSignatureMethod.SHA384withECDSA(params);
      } else if (algorithm.equals("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha512")) {
         return new DOMSignatureMethod.SHA512withECDSA(params);
      } else if (algorithm.equals("http://www.w3.org/2007/05/xmldsig-more#ecdsa-ripemd160")) {
         return new DOMSignatureMethod.RIPEMD160withECDSA(params);
      } else {
         throw new NoSuchAlgorithmException("unsupported algorithm");
      }
   }

   public Transform newTransform(String algorithm, TransformParameterSpec params) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
      TransformService spi;
      if (this.getProvider() == null) {
         spi = TransformService.getInstance(algorithm, "DOM");
      } else {
         try {
            spi = TransformService.getInstance(algorithm, "DOM", this.getProvider());
         } catch (NoSuchAlgorithmException var5) {
            spi = TransformService.getInstance(algorithm, "DOM");
         }
      }

      spi.init(params);
      return new DOMTransform(spi);
   }

   public Transform newTransform(String algorithm, XMLStructure params) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
      TransformService spi;
      if (this.getProvider() == null) {
         spi = TransformService.getInstance(algorithm, "DOM");
      } else {
         try {
            spi = TransformService.getInstance(algorithm, "DOM", this.getProvider());
         } catch (NoSuchAlgorithmException var5) {
            spi = TransformService.getInstance(algorithm, "DOM");
         }
      }

      if (params == null) {
         spi.init((TransformParameterSpec)null);
      } else {
         spi.init(params, (XMLCryptoContext)null);
      }

      return new DOMTransform(spi);
   }

   public CanonicalizationMethod newCanonicalizationMethod(String algorithm, C14NMethodParameterSpec params) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
      TransformService spi;
      if (this.getProvider() == null) {
         spi = TransformService.getInstance(algorithm, "DOM");
      } else {
         try {
            spi = TransformService.getInstance(algorithm, "DOM", this.getProvider());
         } catch (NoSuchAlgorithmException var5) {
            spi = TransformService.getInstance(algorithm, "DOM");
         }
      }

      spi.init(params);
      return new DOMCanonicalizationMethod(spi);
   }

   public CanonicalizationMethod newCanonicalizationMethod(String algorithm, XMLStructure params) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
      TransformService spi;
      if (this.getProvider() == null) {
         spi = TransformService.getInstance(algorithm, "DOM");
      } else {
         try {
            spi = TransformService.getInstance(algorithm, "DOM", this.getProvider());
         } catch (NoSuchAlgorithmException var5) {
            spi = TransformService.getInstance(algorithm, "DOM");
         }
      }

      if (params == null) {
         spi.init((TransformParameterSpec)null);
      } else {
         spi.init(params, (XMLCryptoContext)null);
      }

      return new DOMCanonicalizationMethod(spi);
   }

   public URIDereferencer getURIDereferencer() {
      return DOMURIDereferencer.INSTANCE;
   }

   private static class UnmarshalContext extends DOMCryptoContext {
      UnmarshalContext() {
      }
   }
}
