package org.apache.jcp.xml.dsig.internal.dom;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.DSAPublicKeySpec;
import java.security.spec.ECField;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.EllipticCurve;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Arrays;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dom.DOMCryptoContext;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class DOMKeyValue extends DOMStructure implements KeyValue {
   private static final String XMLDSIG_11_XMLNS = "http://www.w3.org/2009/xmldsig11#";
   private final PublicKey publicKey;

   public DOMKeyValue(PublicKey key) throws KeyException {
      if (key == null) {
         throw new NullPointerException("key cannot be null");
      } else {
         this.publicKey = key;
      }
   }

   public DOMKeyValue(Element kvtElem) throws MarshalException {
      this.publicKey = this.unmarshalKeyValue(kvtElem);
   }

   static KeyValue unmarshal(Element kvElem) throws MarshalException {
      Element kvtElem = DOMUtils.getFirstChildElement(kvElem);
      if (kvtElem == null) {
         throw new MarshalException("KeyValue must contain at least one type");
      } else {
         String namespace = kvtElem.getNamespaceURI();
         if (kvtElem.getLocalName().equals("DSAKeyValue") && "http://www.w3.org/2000/09/xmldsig#".equals(namespace)) {
            return new DSA(kvtElem);
         } else if (kvtElem.getLocalName().equals("RSAKeyValue") && "http://www.w3.org/2000/09/xmldsig#".equals(namespace)) {
            return new RSA(kvtElem);
         } else {
            return (KeyValue)(kvtElem.getLocalName().equals("ECKeyValue") && "http://www.w3.org/2009/xmldsig11#".equals(namespace) ? new EC(kvtElem) : new Unknown(kvtElem));
         }
      }
   }

   public PublicKey getPublicKey() throws KeyException {
      if (this.publicKey == null) {
         throw new KeyException("can't convert KeyValue to PublicKey");
      } else {
         return this.publicKey;
      }
   }

   public void marshal(Node parent, String dsPrefix, DOMCryptoContext context) throws MarshalException {
      Document ownerDoc = DOMUtils.getOwnerDocument(parent);
      Element kvElem = DOMUtils.createElement(ownerDoc, "KeyValue", "http://www.w3.org/2000/09/xmldsig#", dsPrefix);
      this.marshalPublicKey(kvElem, ownerDoc, dsPrefix, context);
      parent.appendChild(kvElem);
   }

   abstract void marshalPublicKey(Node var1, Document var2, String var3, DOMCryptoContext var4) throws MarshalException;

   abstract PublicKey unmarshalKeyValue(Element var1) throws MarshalException;

   private static PublicKey generatePublicKey(KeyFactory kf, KeySpec keyspec) {
      try {
         return kf.generatePublic(keyspec);
      } catch (InvalidKeySpecException var3) {
         return null;
      }
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof KeyValue)) {
         return false;
      } else {
         try {
            KeyValue kv = (KeyValue)obj;
            if (this.publicKey == null) {
               if (kv.getPublicKey() != null) {
                  return false;
               }
            } else if (!this.publicKey.equals(kv.getPublicKey())) {
               return false;
            }

            return true;
         } catch (KeyException var3) {
            return false;
         }
      }
   }

   public static BigInteger decode(Element elem) throws MarshalException {
      try {
         String base64str = elem.getFirstChild().getNodeValue();
         return new BigInteger(1, XMLUtils.decode(base64str));
      } catch (Exception var2) {
         throw new MarshalException(var2);
      }
   }

   public int hashCode() {
      int result = 17;
      if (this.publicKey != null) {
         result = 31 * result + this.publicKey.hashCode();
      }

      return result;
   }

   private static BigInteger bigInt(String s) {
      return new BigInteger(s, 16);
   }

   static final class Unknown extends DOMKeyValue {
      private javax.xml.crypto.dom.DOMStructure externalPublicKey;

      Unknown(Element elem) throws MarshalException {
         super(elem);
      }

      PublicKey unmarshalKeyValue(Element kvElem) throws MarshalException {
         this.externalPublicKey = new javax.xml.crypto.dom.DOMStructure(kvElem);
         return null;
      }

      void marshalPublicKey(Node parent, Document doc, String dsPrefix, DOMCryptoContext context) throws MarshalException {
         parent.appendChild(this.externalPublicKey.getNode());
      }
   }

   static final class EC extends DOMKeyValue {
      private byte[] ecPublicKey;
      private KeyFactory eckf;
      private ECParameterSpec ecParams;
      private static final Curve SECP256R1 = initializeCurve("secp256r1 [NIST P-256, X9.62 prime256v1]", "1.2.840.10045.3.1.7", "FFFFFFFF00000001000000000000000000000000FFFFFFFFFFFFFFFFFFFFFFFF", "FFFFFFFF00000001000000000000000000000000FFFFFFFFFFFFFFFFFFFFFFFC", "5AC635D8AA3A93E7B3EBBD55769886BC651D06B0CC53B0F63BCE3C3E27D2604B", "6B17D1F2E12C4247F8BCE6E563A440F277037D812DEB33A0F4A13945D898C296", "4FE342E2FE1A7F9B8EE7EB4A7C0F9E162BCE33576B315ECECBB6406837BF51F5", "FFFFFFFF00000000FFFFFFFFFFFFFFFFBCE6FAADA7179E84F3B9CAC2FC632551", 1);
      private static final Curve SECP384R1 = initializeCurve("secp384r1 [NIST P-384]", "1.3.132.0.34", "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFF0000000000000000FFFFFFFF", "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFF0000000000000000FFFFFFFC", "B3312FA7E23EE7E4988E056BE3F82D19181D9C6EFE8141120314088F5013875AC656398D8A2ED19D2A85C8EDD3EC2AEF", "AA87CA22BE8B05378EB1C71EF320AD746E1D3B628BA79B9859F741E082542A385502F25DBF55296C3A545E3872760AB7", "3617DE4A96262C6F5D9E98BF9292DC29F8F41DBD289A147CE9DA3113B5F0B8C00A60B1CE1D7E819D7A431D7C90EA0E5F", "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFC7634D81F4372DDF581A0DB248B0A77AECEC196ACCC52973", 1);
      private static final Curve SECP521R1 = initializeCurve("secp521r1 [NIST P-521]", "1.3.132.0.35", "01FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF", "01FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFC", "0051953EB9618E1C9A1F929A21A0B68540EEA2DA725B99B315F3B8B489918EF109E156193951EC7E937B1652C0BD3BB1BF073573DF883D2C34F1EF451FD46B503F00", "00C6858E06B70404E9CD9E3ECB662395B4429C648139053FB521F828AF606B4D3DBAA14B5E77EFE75928FE1DC127A2FFA8DE3348B3C1856A429BF97E7E31C2E5BD66", "011839296A789A3BC0045C8A5FB42C7D1BD998F54449579B446817AFBD17273E662C97EE72995EF42640C550B9013FAD0761353C7086A272C24088BE94769FD16650", "01FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFA51868783BF2F966B7FCC0148F709A5D03BB5C9B8899C47AEBB6FB71E91386409", 1);

      private static Curve initializeCurve(String name, String oid, String sfield, String a, String b, String x, String y, String n, int h) {
         BigInteger p = DOMKeyValue.bigInt(sfield);
         ECField field = new ECFieldFp(p);
         EllipticCurve curve = new EllipticCurve(field, DOMKeyValue.bigInt(a), DOMKeyValue.bigInt(b));
         ECPoint g = new ECPoint(DOMKeyValue.bigInt(x), DOMKeyValue.bigInt(y));
         return new Curve(name, oid, curve, g, DOMKeyValue.bigInt(n), h);
      }

      EC(ECPublicKey ecKey) throws KeyException {
         super((PublicKey)ecKey);
         ECPoint ecPoint = ecKey.getW();
         this.ecParams = ecKey.getParams();
         this.ecPublicKey = encodePoint(ecPoint, this.ecParams.getCurve());
      }

      EC(Element dmElem) throws MarshalException {
         super(dmElem);
      }

      private static ECPoint decodePoint(byte[] data, EllipticCurve curve) throws IOException {
         if (data.length != 0 && data[0] == 4) {
            int n = (data.length - 1) / 2;
            if (n != curve.getField().getFieldSize() + 7 >> 3) {
               throw new IOException("Point does not match field size");
            } else {
               byte[] xb = Arrays.copyOfRange(data, 1, 1 + n);
               byte[] yb = Arrays.copyOfRange(data, n + 1, n + 1 + n);
               return new ECPoint(new BigInteger(1, xb), new BigInteger(1, yb));
            }
         } else {
            throw new IOException("Only uncompressed point format supported");
         }
      }

      private static byte[] encodePoint(ECPoint point, EllipticCurve curve) {
         int n = curve.getField().getFieldSize() + 7 >> 3;
         byte[] xb = trimZeroes(point.getAffineX().toByteArray());
         byte[] yb = trimZeroes(point.getAffineY().toByteArray());
         if (xb.length <= n && yb.length <= n) {
            byte[] b = new byte[1 + (n << 1)];
            b[0] = 4;
            System.arraycopy(xb, 0, b, n - xb.length + 1, xb.length);
            System.arraycopy(yb, 0, b, b.length - yb.length, yb.length);
            return b;
         } else {
            throw new RuntimeException("Point coordinates do not match field size");
         }
      }

      private static byte[] trimZeroes(byte[] b) {
         int i;
         for(i = 0; i < b.length - 1 && b[i] == 0; ++i) {
         }

         return i == 0 ? b : Arrays.copyOfRange(b, i, b.length);
      }

      private static String getCurveOid(ECParameterSpec params) {
         Curve match;
         if (matchCurve(params, SECP256R1)) {
            match = SECP256R1;
         } else if (matchCurve(params, SECP384R1)) {
            match = SECP384R1;
         } else {
            if (!matchCurve(params, SECP521R1)) {
               return null;
            }

            match = SECP521R1;
         }

         return match.getObjectId();
      }

      private static boolean matchCurve(ECParameterSpec params, Curve curve) {
         int fieldSize = params.getCurve().getField().getFieldSize();
         return curve.getCurve().getField().getFieldSize() == fieldSize && curve.getCurve().equals(params.getCurve()) && curve.getGenerator().equals(params.getGenerator()) && curve.getOrder().equals(params.getOrder()) && curve.getCofactor() == params.getCofactor();
      }

      void marshalPublicKey(Node parent, Document doc, String dsPrefix, DOMCryptoContext context) throws MarshalException {
         String prefix = DOMUtils.getNSPrefix(context, "http://www.w3.org/2009/xmldsig11#");
         Element ecKeyValueElem = DOMUtils.createElement(doc, "ECKeyValue", "http://www.w3.org/2009/xmldsig11#", prefix);
         Element namedCurveElem = DOMUtils.createElement(doc, "NamedCurve", "http://www.w3.org/2009/xmldsig11#", prefix);
         Element publicKeyElem = DOMUtils.createElement(doc, "PublicKey", "http://www.w3.org/2009/xmldsig11#", prefix);
         String oid = getCurveOid(this.ecParams);
         if (oid == null) {
            throw new MarshalException("Invalid ECParameterSpec");
         } else {
            DOMUtils.setAttribute(namedCurveElem, "URI", "urn:oid:" + oid);
            String qname = prefix != null && prefix.length() != 0 ? "xmlns:" + prefix : "xmlns";
            namedCurveElem.setAttributeNS("http://www.w3.org/2000/xmlns/", qname, "http://www.w3.org/2009/xmldsig11#");
            ecKeyValueElem.appendChild(namedCurveElem);
            String encoded = XMLUtils.encodeToString(this.ecPublicKey);
            publicKeyElem.appendChild(DOMUtils.getOwnerDocument(publicKeyElem).createTextNode(encoded));
            ecKeyValueElem.appendChild(publicKeyElem);
            parent.appendChild(ecKeyValueElem);
         }
      }

      ECPublicKey unmarshalKeyValue(Element kvtElem) throws MarshalException {
         if (this.eckf == null) {
            try {
               this.eckf = KeyFactory.getInstance("EC");
            } catch (NoSuchAlgorithmException var7) {
               throw new RuntimeException("unable to create EC KeyFactory: " + var7.getMessage());
            }
         }

         ECParameterSpec ecParams = null;
         Element curElem = DOMUtils.getFirstChildElement(kvtElem);
         if (curElem == null) {
            throw new MarshalException("KeyValue must contain at least one type");
         } else if (curElem.getLocalName().equals("ECParameters") && "http://www.w3.org/2009/xmldsig11#".equals(curElem.getNamespaceURI())) {
            throw new UnsupportedOperationException("ECParameters not supported");
         } else if (curElem.getLocalName().equals("NamedCurve") && "http://www.w3.org/2009/xmldsig11#".equals(curElem.getNamespaceURI())) {
            String ecPoint = DOMUtils.getAttributeValue(curElem, "URI");
            if (ecPoint.startsWith("urn:oid:")) {
               String content = ecPoint.substring("urn:oid:".length());
               ecParams = getECParameterSpec(content);
               if (ecParams == null) {
                  throw new MarshalException("Invalid curve OID");
               } else {
                  curElem = DOMUtils.getNextSiblingElement(curElem, "PublicKey", "http://www.w3.org/2009/xmldsig11#");
                  ecPoint = null;

                  ECPoint ecPoint;
                  try {
                     content = XMLUtils.getFullTextChildrenFromNode(curElem);
                     ecPoint = decodePoint(XMLUtils.decode(content), ecParams.getCurve());
                  } catch (IOException var6) {
                     throw new MarshalException("Invalid EC Point", var6);
                  }

                  ECPublicKeySpec spec = new ECPublicKeySpec(ecPoint, ecParams);
                  return (ECPublicKey)DOMKeyValue.generatePublicKey(this.eckf, spec);
               }
            } else {
               throw new MarshalException("Invalid NamedCurve URI");
            }
         } else {
            throw new MarshalException("Invalid ECKeyValue");
         }
      }

      private static ECParameterSpec getECParameterSpec(String oid) {
         if (oid.equals(SECP256R1.getObjectId())) {
            return SECP256R1;
         } else if (oid.equals(SECP384R1.getObjectId())) {
            return SECP384R1;
         } else {
            return oid.equals(SECP521R1.getObjectId()) ? SECP521R1 : null;
         }
      }

      static final class Curve extends ECParameterSpec {
         private final String name;
         private final String oid;

         Curve(String name, String oid, EllipticCurve curve, ECPoint g, BigInteger n, int h) {
            super(curve, g, n, h);
            this.name = name;
            this.oid = oid;
         }

         private String getName() {
            return this.name;
         }

         private String getObjectId() {
            return this.oid;
         }
      }
   }

   static final class DSA extends DOMKeyValue {
      private DOMCryptoBinary p;
      private DOMCryptoBinary q;
      private DOMCryptoBinary g;
      private DOMCryptoBinary y;
      private KeyFactory dsakf;

      DSA(DSAPublicKey key) throws KeyException {
         super((PublicKey)key);
         DSAParams params = key.getParams();
         this.p = new DOMCryptoBinary(params.getP());
         this.q = new DOMCryptoBinary(params.getQ());
         this.g = new DOMCryptoBinary(params.getG());
         this.y = new DOMCryptoBinary(key.getY());
      }

      DSA(Element elem) throws MarshalException {
         super(elem);
      }

      void marshalPublicKey(Node parent, Document doc, String dsPrefix, DOMCryptoContext context) throws MarshalException {
         Element dsaElem = DOMUtils.createElement(doc, "DSAKeyValue", "http://www.w3.org/2000/09/xmldsig#", dsPrefix);
         Element pElem = DOMUtils.createElement(doc, "P", "http://www.w3.org/2000/09/xmldsig#", dsPrefix);
         Element qElem = DOMUtils.createElement(doc, "Q", "http://www.w3.org/2000/09/xmldsig#", dsPrefix);
         Element gElem = DOMUtils.createElement(doc, "G", "http://www.w3.org/2000/09/xmldsig#", dsPrefix);
         Element yElem = DOMUtils.createElement(doc, "Y", "http://www.w3.org/2000/09/xmldsig#", dsPrefix);
         this.p.marshal(pElem, dsPrefix, context);
         this.q.marshal(qElem, dsPrefix, context);
         this.g.marshal(gElem, dsPrefix, context);
         this.y.marshal(yElem, dsPrefix, context);
         dsaElem.appendChild(pElem);
         dsaElem.appendChild(qElem);
         dsaElem.appendChild(gElem);
         dsaElem.appendChild(yElem);
         parent.appendChild(dsaElem);
      }

      DSAPublicKey unmarshalKeyValue(Element kvtElem) throws MarshalException {
         if (this.dsakf == null) {
            try {
               this.dsakf = KeyFactory.getInstance("DSA");
            } catch (NoSuchAlgorithmException var8) {
               throw new RuntimeException("unable to create DSA KeyFactory: " + var8.getMessage());
            }
         }

         Element curElem = DOMUtils.getFirstChildElement(kvtElem);
         if (curElem == null) {
            throw new MarshalException("KeyValue must contain at least one type");
         } else {
            BigInteger p = null;
            BigInteger q = null;
            if (curElem.getLocalName().equals("P") && "http://www.w3.org/2000/09/xmldsig#".equals(curElem.getNamespaceURI())) {
               p = decode(curElem);
               curElem = DOMUtils.getNextSiblingElement(curElem, "Q", "http://www.w3.org/2000/09/xmldsig#");
               q = decode(curElem);
               curElem = DOMUtils.getNextSiblingElement(curElem);
            }

            BigInteger g = null;
            if (curElem != null && curElem.getLocalName().equals("G") && "http://www.w3.org/2000/09/xmldsig#".equals(curElem.getNamespaceURI())) {
               g = decode(curElem);
               curElem = DOMUtils.getNextSiblingElement(curElem, "Y", "http://www.w3.org/2000/09/xmldsig#");
            }

            BigInteger y = null;
            if (curElem != null) {
               y = decode(curElem);
               curElem = DOMUtils.getNextSiblingElement(curElem);
            }

            DSAPublicKeySpec spec = new DSAPublicKeySpec(y, p, q, g);
            return (DSAPublicKey)DOMKeyValue.generatePublicKey(this.dsakf, spec);
         }
      }
   }

   static final class RSA extends DOMKeyValue {
      private DOMCryptoBinary modulus;
      private DOMCryptoBinary exponent;
      private KeyFactory rsakf;

      RSA(RSAPublicKey key) throws KeyException {
         super((PublicKey)key);
         this.exponent = new DOMCryptoBinary(key.getPublicExponent());
         this.modulus = new DOMCryptoBinary(key.getModulus());
      }

      RSA(Element elem) throws MarshalException {
         super(elem);
      }

      void marshalPublicKey(Node parent, Document doc, String dsPrefix, DOMCryptoContext context) throws MarshalException {
         Element rsaElem = DOMUtils.createElement(doc, "RSAKeyValue", "http://www.w3.org/2000/09/xmldsig#", dsPrefix);
         Element modulusElem = DOMUtils.createElement(doc, "Modulus", "http://www.w3.org/2000/09/xmldsig#", dsPrefix);
         Element exponentElem = DOMUtils.createElement(doc, "Exponent", "http://www.w3.org/2000/09/xmldsig#", dsPrefix);
         this.modulus.marshal(modulusElem, dsPrefix, context);
         this.exponent.marshal(exponentElem, dsPrefix, context);
         rsaElem.appendChild(modulusElem);
         rsaElem.appendChild(exponentElem);
         parent.appendChild(rsaElem);
      }

      RSAPublicKey unmarshalKeyValue(Element kvtElem) throws MarshalException {
         if (this.rsakf == null) {
            try {
               this.rsakf = KeyFactory.getInstance("RSA");
            } catch (NoSuchAlgorithmException var7) {
               throw new RuntimeException("unable to create RSA KeyFactory: " + var7.getMessage());
            }
         }

         Element modulusElem = DOMUtils.getFirstChildElement(kvtElem, "Modulus", "http://www.w3.org/2000/09/xmldsig#");
         BigInteger modulus = decode(modulusElem);
         Element exponentElem = DOMUtils.getNextSiblingElement(modulusElem, "Exponent", "http://www.w3.org/2000/09/xmldsig#");
         BigInteger exponent = decode(exponentElem);
         RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, exponent);
         return (RSAPublicKey)DOMKeyValue.generatePublicKey(this.rsakf, spec);
      }
   }
}
