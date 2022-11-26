package org.apache.xml.security.binding.xmldsig11;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {
   private static final QName _ECKeyValue_QNAME = new QName("http://www.w3.org/2009/xmldsig11#", "ECKeyValue");
   private static final QName _Prime_QNAME = new QName("http://www.w3.org/2009/xmldsig11#", "Prime");
   private static final QName _GnB_QNAME = new QName("http://www.w3.org/2009/xmldsig11#", "GnB");
   private static final QName _TnB_QNAME = new QName("http://www.w3.org/2009/xmldsig11#", "TnB");
   private static final QName _PnB_QNAME = new QName("http://www.w3.org/2009/xmldsig11#", "PnB");
   private static final QName _OCSPResponse_QNAME = new QName("http://www.w3.org/2009/xmldsig11#", "OCSPResponse");
   private static final QName _DEREncodedKeyValue_QNAME = new QName("http://www.w3.org/2009/xmldsig11#", "DEREncodedKeyValue");
   private static final QName _KeyInfoReference_QNAME = new QName("http://www.w3.org/2009/xmldsig11#", "KeyInfoReference");
   private static final QName _X509Digest_QNAME = new QName("http://www.w3.org/2009/xmldsig11#", "X509Digest");

   public ECKeyValueType createECKeyValueType() {
      return new ECKeyValueType();
   }

   public PrimeFieldParamsType createPrimeFieldParamsType() {
      return new PrimeFieldParamsType();
   }

   public CharTwoFieldParamsType createCharTwoFieldParamsType() {
      return new CharTwoFieldParamsType();
   }

   public TnBFieldParamsType createTnBFieldParamsType() {
      return new TnBFieldParamsType();
   }

   public PnBFieldParamsType createPnBFieldParamsType() {
      return new PnBFieldParamsType();
   }

   public DEREncodedKeyValueType createDEREncodedKeyValueType() {
      return new DEREncodedKeyValueType();
   }

   public KeyInfoReferenceType createKeyInfoReferenceType() {
      return new KeyInfoReferenceType();
   }

   public X509DigestType createX509DigestType() {
      return new X509DigestType();
   }

   public NamedCurveType createNamedCurveType() {
      return new NamedCurveType();
   }

   public ECParametersType createECParametersType() {
      return new ECParametersType();
   }

   public FieldIDType createFieldIDType() {
      return new FieldIDType();
   }

   public CurveType createCurveType() {
      return new CurveType();
   }

   public ECValidationDataType createECValidationDataType() {
      return new ECValidationDataType();
   }

   @XmlElementDecl(
      namespace = "http://www.w3.org/2009/xmldsig11#",
      name = "ECKeyValue"
   )
   public JAXBElement createECKeyValue(ECKeyValueType value) {
      return new JAXBElement(_ECKeyValue_QNAME, ECKeyValueType.class, (Class)null, value);
   }

   @XmlElementDecl(
      namespace = "http://www.w3.org/2009/xmldsig11#",
      name = "Prime"
   )
   public JAXBElement createPrime(PrimeFieldParamsType value) {
      return new JAXBElement(_Prime_QNAME, PrimeFieldParamsType.class, (Class)null, value);
   }

   @XmlElementDecl(
      namespace = "http://www.w3.org/2009/xmldsig11#",
      name = "GnB"
   )
   public JAXBElement createGnB(CharTwoFieldParamsType value) {
      return new JAXBElement(_GnB_QNAME, CharTwoFieldParamsType.class, (Class)null, value);
   }

   @XmlElementDecl(
      namespace = "http://www.w3.org/2009/xmldsig11#",
      name = "TnB"
   )
   public JAXBElement createTnB(TnBFieldParamsType value) {
      return new JAXBElement(_TnB_QNAME, TnBFieldParamsType.class, (Class)null, value);
   }

   @XmlElementDecl(
      namespace = "http://www.w3.org/2009/xmldsig11#",
      name = "PnB"
   )
   public JAXBElement createPnB(PnBFieldParamsType value) {
      return new JAXBElement(_PnB_QNAME, PnBFieldParamsType.class, (Class)null, value);
   }

   @XmlElementDecl(
      namespace = "http://www.w3.org/2009/xmldsig11#",
      name = "OCSPResponse"
   )
   public JAXBElement createOCSPResponse(byte[] value) {
      return new JAXBElement(_OCSPResponse_QNAME, byte[].class, (Class)null, value);
   }

   @XmlElementDecl(
      namespace = "http://www.w3.org/2009/xmldsig11#",
      name = "DEREncodedKeyValue"
   )
   public JAXBElement createDEREncodedKeyValue(DEREncodedKeyValueType value) {
      return new JAXBElement(_DEREncodedKeyValue_QNAME, DEREncodedKeyValueType.class, (Class)null, value);
   }

   @XmlElementDecl(
      namespace = "http://www.w3.org/2009/xmldsig11#",
      name = "KeyInfoReference"
   )
   public JAXBElement createKeyInfoReference(KeyInfoReferenceType value) {
      return new JAXBElement(_KeyInfoReference_QNAME, KeyInfoReferenceType.class, (Class)null, value);
   }

   @XmlElementDecl(
      namespace = "http://www.w3.org/2009/xmldsig11#",
      name = "X509Digest"
   )
   public JAXBElement createX509Digest(X509DigestType value) {
      return new JAXBElement(_X509Digest_QNAME, X509DigestType.class, (Class)null, value);
   }
}
