package org.apache.jcp.xml.dsig.internal.dom;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Provider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.dom.DOMCryptoContext;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.TransformException;
import javax.xml.crypto.dsig.XMLSignatureException;
import org.apache.xml.security.utils.UnsyncBufferedOutputStream;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public final class DOMSignedInfo extends DOMStructure implements SignedInfo {
   public static final int MAXIMUM_REFERENCE_COUNT = 30;
   private static final Logger LOG = LoggerFactory.getLogger(DOMSignedInfo.class);
   private static final String ALGO_ID_SIGNATURE_NOT_RECOMMENDED_RSA_MD5 = "http://www.w3.org/2001/04/xmldsig-more#rsa-md5";
   private static final String ALGO_ID_MAC_HMAC_NOT_RECOMMENDED_MD5 = "http://www.w3.org/2001/04/xmldsig-more#hmac-md5";
   private List references;
   private CanonicalizationMethod canonicalizationMethod;
   private SignatureMethod signatureMethod;
   private String id;
   private Document ownerDoc;
   private Element localSiElem;
   private InputStream canonData;

   public DOMSignedInfo(CanonicalizationMethod cm, SignatureMethod sm, List references) {
      if (cm != null && sm != null && references != null) {
         this.canonicalizationMethod = cm;
         this.signatureMethod = sm;
         this.references = Collections.unmodifiableList(new ArrayList(references));
         if (this.references.isEmpty()) {
            throw new IllegalArgumentException("list of references must contain at least one entry");
         } else {
            int i = 0;

            for(int size = this.references.size(); i < size; ++i) {
               Object obj = this.references.get(i);
               if (!(obj instanceof Reference)) {
                  throw new ClassCastException("list of references contains an illegal type");
               }
            }

         }
      } else {
         throw new NullPointerException();
      }
   }

   public DOMSignedInfo(CanonicalizationMethod cm, SignatureMethod sm, List references, String id) {
      this(cm, sm, references);
      this.id = id;
   }

   public DOMSignedInfo(Element siElem, XMLCryptoContext context, Provider provider) throws MarshalException {
      this.localSiElem = siElem;
      this.ownerDoc = siElem.getOwnerDocument();
      this.id = DOMUtils.getAttributeValue(siElem, "Id");
      Element cmElem = DOMUtils.getFirstChildElement(siElem, "CanonicalizationMethod", "http://www.w3.org/2000/09/xmldsig#");
      this.canonicalizationMethod = new DOMCanonicalizationMethod(cmElem, context, provider);
      Element smElem = DOMUtils.getNextSiblingElement(cmElem, "SignatureMethod", "http://www.w3.org/2000/09/xmldsig#");
      this.signatureMethod = DOMSignatureMethod.unmarshal(smElem);
      boolean secVal = Utils.secureValidation(context);
      String signatureMethodAlgorithm = this.signatureMethod.getAlgorithm();
      if (!secVal || !"http://www.w3.org/2001/04/xmldsig-more#hmac-md5".equals(signatureMethodAlgorithm) && !"http://www.w3.org/2001/04/xmldsig-more#rsa-md5".equals(signatureMethodAlgorithm)) {
         ArrayList refList = new ArrayList(5);
         Element refElem = DOMUtils.getNextSiblingElement(smElem, "Reference", "http://www.w3.org/2000/09/xmldsig#");
         refList.add(new DOMReference(refElem, context, provider));

         for(refElem = DOMUtils.getNextSiblingElement(refElem); refElem != null; refElem = DOMUtils.getNextSiblingElement(refElem)) {
            String name = refElem.getLocalName();
            String namespace = refElem.getNamespaceURI();
            if (!"Reference".equals(name) || !"http://www.w3.org/2000/09/xmldsig#".equals(namespace)) {
               throw new MarshalException("Invalid element name: " + namespace + ":" + name + ", expected Reference");
            }

            refList.add(new DOMReference(refElem, context, provider));
            if (secVal && refList.size() > 30) {
               String error = "A maxiumum of 30 references per Manifest are allowed with secure validation";
               throw new MarshalException(error);
            }
         }

         this.references = Collections.unmodifiableList(refList);
      } else {
         throw new MarshalException("It is forbidden to use algorithm " + this.signatureMethod + " when secure validation is enabled");
      }
   }

   public CanonicalizationMethod getCanonicalizationMethod() {
      return this.canonicalizationMethod;
   }

   public SignatureMethod getSignatureMethod() {
      return this.signatureMethod;
   }

   public String getId() {
      return this.id;
   }

   public List getReferences() {
      return this.references;
   }

   public InputStream getCanonicalizedData() {
      return this.canonData;
   }

   public void canonicalize(XMLCryptoContext context, ByteArrayOutputStream bos) throws XMLSignatureException {
      if (context == null) {
         throw new NullPointerException("context cannot be null");
      } else {
         DOMSubTreeData subTree = new DOMSubTreeData(this.localSiElem, true);

         try {
            OutputStream os = new UnsyncBufferedOutputStream(bos);
            Throwable var5 = null;

            try {
               ((DOMCanonicalizationMethod)this.canonicalizationMethod).canonicalize(subTree, context, os);
               os.flush();
               byte[] signedInfoBytes = bos.toByteArray();
               if (LOG.isDebugEnabled()) {
                  LOG.debug("Canonicalized SignedInfo:");
                  StringBuilder sb = new StringBuilder(signedInfoBytes.length);

                  for(int i = 0; i < signedInfoBytes.length; ++i) {
                     sb.append((char)signedInfoBytes[i]);
                  }

                  LOG.debug(sb.toString());
                  LOG.debug("Data to be signed/verified:" + XMLUtils.encodeToString(signedInfoBytes));
               }

               this.canonData = new ByteArrayInputStream(signedInfoBytes);
            } catch (Throwable var18) {
               var5 = var18;
               throw var18;
            } finally {
               if (var5 != null) {
                  try {
                     os.close();
                  } catch (Throwable var17) {
                     var5.addSuppressed(var17);
                  }
               } else {
                  os.close();
               }

            }
         } catch (TransformException var20) {
            throw new XMLSignatureException(var20);
         } catch (IOException var21) {
            LOG.debug(var21.getMessage(), var21);
         }

      }
   }

   public void marshal(Node parent, String dsPrefix, DOMCryptoContext context) throws MarshalException {
      this.ownerDoc = DOMUtils.getOwnerDocument(parent);
      Element siElem = DOMUtils.createElement(this.ownerDoc, "SignedInfo", "http://www.w3.org/2000/09/xmldsig#", dsPrefix);
      DOMCanonicalizationMethod dcm = (DOMCanonicalizationMethod)this.canonicalizationMethod;
      dcm.marshal(siElem, dsPrefix, context);
      ((DOMStructure)this.signatureMethod).marshal(siElem, dsPrefix, context);
      Iterator var6 = this.references.iterator();

      while(var6.hasNext()) {
         Reference reference = (Reference)var6.next();
         ((DOMReference)reference).marshal(siElem, dsPrefix, context);
      }

      DOMUtils.setAttributeID(siElem, "Id", this.id);
      parent.appendChild(siElem);
      this.localSiElem = siElem;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof SignedInfo)) {
         return false;
      } else {
         SignedInfo osi = (SignedInfo)o;
         boolean idEqual = this.id == null ? osi.getId() == null : this.id.equals(osi.getId());
         return this.canonicalizationMethod.equals(osi.getCanonicalizationMethod()) && this.signatureMethod.equals(osi.getSignatureMethod()) && this.references.equals(osi.getReferences()) && idEqual;
      }
   }

   public static List getSignedInfoReferences(SignedInfo si) {
      return si.getReferences();
   }

   public int hashCode() {
      int result = 17;
      if (this.id != null) {
         result = 31 * result + this.id.hashCode();
      }

      result = 31 * result + this.canonicalizationMethod.hashCode();
      result = 31 * result + this.signatureMethod.hashCode();
      result = 31 * result + this.references.hashCode();
      return result;
   }
}
