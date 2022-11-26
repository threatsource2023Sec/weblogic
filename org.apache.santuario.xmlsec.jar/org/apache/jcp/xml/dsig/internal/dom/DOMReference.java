package org.apache.jcp.xml.dsig.internal.dom;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.AccessController;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.xml.crypto.Data;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.NodeSetData;
import javax.xml.crypto.OctetStreamData;
import javax.xml.crypto.URIDereferencer;
import javax.xml.crypto.URIReferenceException;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.dom.DOMCryptoContext;
import javax.xml.crypto.dom.DOMURIReference;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.TransformException;
import javax.xml.crypto.dsig.TransformService;
import javax.xml.crypto.dsig.XMLSignContext;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLValidateContext;
import org.apache.jcp.xml.dsig.internal.DigesterOutputStream;
import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.apache.xml.security.utils.UnsyncBufferedOutputStream;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public final class DOMReference extends DOMStructure implements Reference, DOMURIReference {
   public static final int MAXIMUM_TRANSFORM_COUNT = 5;
   private static boolean useC14N11 = (Boolean)AccessController.doPrivileged(() -> {
      return Boolean.getBoolean("com.sun.org.apache.xml.internal.security.useC14N11");
   });
   private static final Logger LOG = LoggerFactory.getLogger(DOMReference.class);
   private final DigestMethod digestMethod;
   private final String id;
   private final List transforms;
   private List allTransforms;
   private final Data appliedTransformData;
   private Attr here;
   private final String uri;
   private final String type;
   private byte[] digestValue;
   private byte[] calcDigestValue;
   private Element refElem;
   private boolean digested;
   private boolean validated;
   private boolean validationStatus;
   private Data derefData;
   private InputStream dis;
   private MessageDigest md;
   private Provider provider;

   public DOMReference(String uri, String type, DigestMethod dm, List transforms, String id, Provider provider) {
      this(uri, type, dm, (List)null, (Data)null, transforms, id, (byte[])null, provider);
   }

   public DOMReference(String uri, String type, DigestMethod dm, List appliedTransforms, Data result, List transforms, String id, Provider provider) {
      this(uri, type, dm, appliedTransforms, result, transforms, id, (byte[])null, provider);
   }

   public DOMReference(String uri, String type, DigestMethod dm, List appliedTransforms, Data result, List transforms, String id, byte[] digestValue, Provider provider) {
      this.digested = false;
      this.validated = false;
      if (dm == null) {
         throw new NullPointerException("DigestMethod must be non-null");
      } else {
         int i;
         int size;
         if (appliedTransforms == null) {
            this.allTransforms = new ArrayList();
         } else {
            this.allTransforms = new ArrayList(appliedTransforms);
            i = 0;

            for(size = this.allTransforms.size(); i < size; ++i) {
               if (!(this.allTransforms.get(i) instanceof Transform)) {
                  throw new ClassCastException("appliedTransforms[" + i + "] is not a valid type");
               }
            }
         }

         if (transforms == null) {
            this.transforms = Collections.emptyList();
         } else {
            this.transforms = new ArrayList(transforms);
            i = 0;

            for(size = this.transforms.size(); i < size; ++i) {
               if (!(this.transforms.get(i) instanceof Transform)) {
                  throw new ClassCastException("transforms[" + i + "] is not a valid type");
               }
            }

            this.allTransforms.addAll(this.transforms);
         }

         this.digestMethod = dm;
         this.uri = uri;
         if (uri != null && !uri.equals("")) {
            try {
               new URI(uri);
            } catch (URISyntaxException var12) {
               throw new IllegalArgumentException(var12.getMessage());
            }
         }

         this.type = type;
         this.id = id;
         if (digestValue != null) {
            this.digestValue = (byte[])digestValue.clone();
            this.digested = true;
         }

         this.appliedTransformData = result;
         this.provider = provider;
      }
   }

   public DOMReference(Element refElem, XMLCryptoContext context, Provider provider) throws MarshalException {
      this.digested = false;
      this.validated = false;
      boolean secVal = Utils.secureValidation(context);
      Element nextSibling = DOMUtils.getFirstChildElement(refElem);
      List newTransforms = new ArrayList(5);
      String localName;
      String error;
      if (nextSibling.getLocalName().equals("Transforms") && "http://www.w3.org/2000/09/xmldsig#".equals(nextSibling.getNamespaceURI())) {
         Element transformElem = DOMUtils.getFirstChildElement(nextSibling, "Transform", "http://www.w3.org/2000/09/xmldsig#");
         newTransforms.add(new DOMTransform(transformElem, context, provider));
         transformElem = DOMUtils.getNextSiblingElement(transformElem);

         while(true) {
            if (transformElem == null) {
               nextSibling = DOMUtils.getNextSiblingElement(nextSibling);
               break;
            }

            localName = transformElem.getLocalName();
            String namespace = transformElem.getNamespaceURI();
            if (!"Transform".equals(localName) || !"http://www.w3.org/2000/09/xmldsig#".equals(namespace)) {
               throw new MarshalException("Invalid element name: " + localName + ", expected Transform");
            }

            newTransforms.add(new DOMTransform(transformElem, context, provider));
            if (secVal && newTransforms.size() > 5) {
               error = "A maxiumum of 5 transforms per Reference are allowed with secure validation";
               throw new MarshalException(error);
            }

            transformElem = DOMUtils.getNextSiblingElement(transformElem);
         }
      }

      if (!nextSibling.getLocalName().equals("DigestMethod") && "http://www.w3.org/2000/09/xmldsig#".equals(nextSibling.getNamespaceURI())) {
         throw new MarshalException("Invalid element name: " + nextSibling.getLocalName() + ", expected DigestMethod");
      } else {
         this.digestMethod = DOMDigestMethod.unmarshal(nextSibling);
         localName = this.digestMethod.getAlgorithm();
         if (secVal && "http://www.w3.org/2001/04/xmldsig-more#md5".equals(localName)) {
            throw new MarshalException("It is forbidden to use algorithm " + this.digestMethod + " when secure validation is enabled");
         } else {
            Element dvElem = DOMUtils.getNextSiblingElement(nextSibling, "DigestValue", "http://www.w3.org/2000/09/xmldsig#");
            error = XMLUtils.getFullTextChildrenFromNode(dvElem);
            this.digestValue = XMLUtils.decode(error);
            if (DOMUtils.getNextSiblingElement(dvElem) != null) {
               throw new MarshalException("Unexpected element after DigestValue element");
            } else {
               this.uri = DOMUtils.getAttributeValue(refElem, "URI");
               Attr attr = refElem.getAttributeNodeNS((String)null, "Id");
               if (attr != null) {
                  this.id = attr.getValue();
                  refElem.setIdAttributeNode(attr, true);
               } else {
                  this.id = null;
               }

               this.type = DOMUtils.getAttributeValue(refElem, "Type");
               this.here = refElem.getAttributeNodeNS((String)null, "URI");
               this.refElem = refElem;
               this.transforms = newTransforms;
               this.allTransforms = this.transforms;
               this.appliedTransformData = null;
               this.provider = provider;
            }
         }
      }
   }

   public DigestMethod getDigestMethod() {
      return this.digestMethod;
   }

   public String getId() {
      return this.id;
   }

   public String getURI() {
      return this.uri;
   }

   public String getType() {
      return this.type;
   }

   public List getTransforms() {
      return Collections.unmodifiableList(this.allTransforms);
   }

   public byte[] getDigestValue() {
      return this.digestValue == null ? null : (byte[])this.digestValue.clone();
   }

   public byte[] getCalculatedDigestValue() {
      return this.calcDigestValue == null ? null : (byte[])this.calcDigestValue.clone();
   }

   public void marshal(Node parent, String dsPrefix, DOMCryptoContext context) throws MarshalException {
      LOG.debug("Marshalling Reference");
      Document ownerDoc = DOMUtils.getOwnerDocument(parent);
      this.refElem = DOMUtils.createElement(ownerDoc, "Reference", "http://www.w3.org/2000/09/xmldsig#", dsPrefix);
      DOMUtils.setAttributeID(this.refElem, "Id", this.id);
      DOMUtils.setAttribute(this.refElem, "URI", this.uri);
      DOMUtils.setAttribute(this.refElem, "Type", this.type);
      Element digestValueElem;
      if (!this.allTransforms.isEmpty()) {
         digestValueElem = DOMUtils.createElement(ownerDoc, "Transforms", "http://www.w3.org/2000/09/xmldsig#", dsPrefix);
         this.refElem.appendChild(digestValueElem);
         Iterator var6 = this.allTransforms.iterator();

         while(var6.hasNext()) {
            Transform transform = (Transform)var6.next();
            ((DOMStructure)transform).marshal(digestValueElem, dsPrefix, context);
         }
      }

      ((DOMDigestMethod)this.digestMethod).marshal(this.refElem, dsPrefix, context);
      LOG.debug("Adding digestValueElem");
      digestValueElem = DOMUtils.createElement(ownerDoc, "DigestValue", "http://www.w3.org/2000/09/xmldsig#", dsPrefix);
      if (this.digestValue != null) {
         digestValueElem.appendChild(ownerDoc.createTextNode(XMLUtils.encodeToString(this.digestValue)));
      }

      this.refElem.appendChild(digestValueElem);
      parent.appendChild(this.refElem);
      this.here = this.refElem.getAttributeNodeNS((String)null, "URI");
   }

   public void digest(XMLSignContext signContext) throws XMLSignatureException {
      Data data = null;
      if (this.appliedTransformData == null) {
         data = this.dereference(signContext);
      } else {
         data = this.appliedTransformData;
      }

      this.digestValue = this.transform(data, signContext);
      String encodedDV = XMLUtils.encodeToString(this.digestValue);
      LOG.debug("Reference object uri = {}", this.uri);
      Element digestElem = DOMUtils.getLastChildElement(this.refElem);
      if (digestElem == null) {
         throw new XMLSignatureException("DigestValue element expected");
      } else {
         DOMUtils.removeAllChildren(digestElem);
         digestElem.appendChild(this.refElem.getOwnerDocument().createTextNode(encodedDV));
         this.digested = true;
         LOG.debug("Reference digesting completed");
      }
   }

   public boolean validate(XMLValidateContext validateContext) throws XMLSignatureException {
      if (validateContext == null) {
         throw new NullPointerException("validateContext cannot be null");
      } else if (this.validated) {
         return this.validationStatus;
      } else {
         Data data = this.dereference(validateContext);
         this.calcDigestValue = this.transform(data, validateContext);
         if (LOG.isDebugEnabled()) {
            LOG.debug("Expected digest: " + XMLUtils.encodeToString(this.digestValue));
            LOG.debug("Actual digest: " + XMLUtils.encodeToString(this.calcDigestValue));
         }

         this.validationStatus = Arrays.equals(this.digestValue, this.calcDigestValue);
         this.validated = true;
         return this.validationStatus;
      }
   }

   public Data getDereferencedData() {
      return this.derefData;
   }

   public InputStream getDigestInputStream() {
      return this.dis;
   }

   private Data dereference(XMLCryptoContext context) throws XMLSignatureException {
      Data data = null;
      URIDereferencer deref = context.getURIDereferencer();
      if (deref == null) {
         deref = DOMURIDereferencer.INSTANCE;
      }

      try {
         data = deref.dereference(this, context);
         LOG.debug("URIDereferencer class name: {}", deref.getClass().getName());
         LOG.debug("Data class name: {}", data.getClass().getName());
         return data;
      } catch (URIReferenceException var5) {
         throw new XMLSignatureException(var5);
      }
   }

   private byte[] transform(Data dereferencedData, XMLCryptoContext context) throws XMLSignatureException {
      if (this.md == null) {
         try {
            this.md = MessageDigest.getInstance(((DOMDigestMethod)this.digestMethod).getMessageDigestAlgorithm());
         } catch (NoSuchAlgorithmException var53) {
            throw new XMLSignatureException(var53);
         }
      }

      this.md.reset();
      Boolean cache = (Boolean)context.getProperty("javax.xml.crypto.dsig.cacheReference");
      DigesterOutputStream dos;
      if (cache != null && cache) {
         this.derefData = copyDerefData(dereferencedData);
         dos = new DigesterOutputStream(this.md, true);
      } else {
         dos = new DigesterOutputStream(this.md);
      }

      Data data = dereferencedData;

      Object i;
      try {
         OutputStream os = new UnsyncBufferedOutputStream(dos);
         Throwable var7 = null;

         try {
            i = false;

            for(int size = this.transforms.size(); i < size; ++i) {
               DOMTransform transform = (DOMTransform)this.transforms.get((int)i);
               if (i < size - 1) {
                  data = transform.transform(data, context);
               } else {
                  data = transform.transform(data, context, (OutputStream)os);
               }
            }

            if (data != null) {
               boolean c14n11 = useC14N11;
               String c14nalg = "http://www.w3.org/TR/2001/REC-xml-c14n-20010315";
               Boolean spi;
               if (context instanceof XMLSignContext) {
                  if (c14n11) {
                     c14nalg = "http://www.w3.org/2006/12/xml-c14n11";
                  } else {
                     spi = (Boolean)context.getProperty("org.apache.xml.security.useC14N11");
                     c14n11 = spi != null && spi;
                     if (c14n11) {
                        c14nalg = "http://www.w3.org/2006/12/xml-c14n11";
                     }
                  }
               }

               XMLSignatureInput xi;
               if (data instanceof ApacheData) {
                  xi = ((ApacheData)data).getXMLSignatureInput();
               } else if (data instanceof OctetStreamData) {
                  xi = new XMLSignatureInput(((OctetStreamData)data).getOctetStream());
               } else {
                  if (!(data instanceof NodeSetData)) {
                     throw new XMLSignatureException("unrecognized Data type");
                  }

                  spi = null;
                  TransformService spi;
                  if (this.provider == null) {
                     spi = TransformService.getInstance(c14nalg, "DOM");
                  } else {
                     try {
                        spi = TransformService.getInstance(c14nalg, "DOM", this.provider);
                     } catch (NoSuchAlgorithmException var52) {
                        spi = TransformService.getInstance(c14nalg, "DOM");
                     }
                  }

                  data = spi.transform(data, context);
                  xi = new XMLSignatureInput(((OctetStreamData)data).getOctetStream());
               }

               boolean secVal = Utils.secureValidation(context);
               xi.setSecureValidation(secVal);
               if (context instanceof XMLSignContext && c14n11 && !xi.isOctetStream() && !xi.isOutputStreamSet()) {
                  TransformService spi = null;
                  if (this.provider == null) {
                     spi = TransformService.getInstance(c14nalg, "DOM");
                  } else {
                     try {
                        spi = TransformService.getInstance(c14nalg, "DOM", this.provider);
                     } catch (NoSuchAlgorithmException var51) {
                        spi = TransformService.getInstance(c14nalg, "DOM");
                     }
                  }

                  DOMTransform t = new DOMTransform(spi);
                  Element transformsElem = null;
                  String dsPrefix = DOMUtils.getSignaturePrefix(context);
                  if (this.allTransforms.isEmpty()) {
                     transformsElem = DOMUtils.createElement(this.refElem.getOwnerDocument(), "Transforms", "http://www.w3.org/2000/09/xmldsig#", dsPrefix);
                     this.refElem.insertBefore(transformsElem, DOMUtils.getFirstChildElement(this.refElem));
                  } else {
                     transformsElem = DOMUtils.getFirstChildElement(this.refElem);
                  }

                  t.marshal(transformsElem, dsPrefix, (DOMCryptoContext)context);
                  this.allTransforms.add(t);
                  xi.updateOutputStream(os, true);
               } else {
                  xi.updateOutputStream(os);
               }
            }

            os.flush();
            if (cache != null && cache) {
               this.dis = dos.getInputStream();
            }

            i = dos.getDigestValue();
         } catch (Throwable var54) {
            i = var54;
            var7 = var54;
            throw var54;
         } finally {
            if (var7 != null) {
               try {
                  os.close();
               } catch (Throwable var50) {
                  var7.addSuppressed(var50);
               }
            } else {
               os.close();
            }

         }
      } catch (NoSuchAlgorithmException var56) {
         throw new XMLSignatureException(var56);
      } catch (TransformException var57) {
         throw new XMLSignatureException(var57);
      } catch (MarshalException var58) {
         throw new XMLSignatureException(var58);
      } catch (IOException var59) {
         throw new XMLSignatureException(var59);
      } catch (CanonicalizationException var60) {
         throw new XMLSignatureException(var60);
      } finally {
         if (dos != null) {
            try {
               dos.close();
            } catch (IOException var49) {
               throw new XMLSignatureException(var49);
            }
         }

      }

      return (byte[])i;
   }

   public Node getHere() {
      return this.here;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof Reference)) {
         return false;
      } else {
         Reference oref = (Reference)o;
         boolean idsEqual = this.id == null ? oref.getId() == null : this.id.equals(oref.getId());
         boolean urisEqual = this.uri == null ? oref.getURI() == null : this.uri.equals(oref.getURI());
         boolean typesEqual = this.type == null ? oref.getType() == null : this.type.equals(oref.getType());
         boolean digestValuesEqual = Arrays.equals(this.digestValue, oref.getDigestValue());
         return this.digestMethod.equals(oref.getDigestMethod()) && idsEqual && urisEqual && typesEqual && this.allTransforms.equals(oref.getTransforms()) && digestValuesEqual;
      }
   }

   public int hashCode() {
      int result = 17;
      if (this.id != null) {
         result = 31 * result + this.id.hashCode();
      }

      if (this.uri != null) {
         result = 31 * result + this.uri.hashCode();
      }

      if (this.type != null) {
         result = 31 * result + this.type.hashCode();
      }

      if (this.digestValue != null) {
         result = 31 * result + Arrays.hashCode(this.digestValue);
      }

      result = 31 * result + this.digestMethod.hashCode();
      result = 31 * result + this.allTransforms.hashCode();
      return result;
   }

   boolean isDigested() {
      return this.digested;
   }

   private static Data copyDerefData(Data dereferencedData) {
      if (dereferencedData instanceof ApacheData) {
         ApacheData ad = (ApacheData)dereferencedData;
         XMLSignatureInput xsi = ad.getXMLSignatureInput();
         if (xsi.isNodeSet()) {
            try {
               final Set s = xsi.getNodeSet();
               return new NodeSetData() {
                  public Iterator iterator() {
                     return s.iterator();
                  }
               };
            } catch (Exception var4) {
               LOG.warn("cannot cache dereferenced data: " + var4);
               return null;
            }
         }

         if (xsi.isElement()) {
            return new DOMSubTreeData(xsi.getSubNode(), xsi.isExcludeComments());
         }

         if (xsi.isOctetStream() || xsi.isByteArray()) {
            try {
               return new OctetStreamData(xsi.getOctetStream(), xsi.getSourceURI(), xsi.getMIMEType());
            } catch (IOException var5) {
               LOG.warn("cannot cache dereferenced data: " + var5);
               return null;
            }
         }
      }

      return dereferencedData;
   }
}
