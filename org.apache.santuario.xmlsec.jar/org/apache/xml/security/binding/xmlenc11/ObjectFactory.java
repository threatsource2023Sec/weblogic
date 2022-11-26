package org.apache.xml.security.binding.xmlenc11;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {
   private static final QName _ConcatKDFParams_QNAME = new QName("http://www.w3.org/2009/xmlenc11#", "ConcatKDFParams");
   private static final QName _DerivedKey_QNAME = new QName("http://www.w3.org/2009/xmlenc11#", "DerivedKey");
   private static final QName _KeyDerivationMethod_QNAME = new QName("http://www.w3.org/2009/xmlenc11#", "KeyDerivationMethod");
   private static final QName _PBKDF2Params_QNAME = new QName("http://www.w3.org/2009/xmlenc11#", "PBKDF2-params");
   private static final QName _MGF_QNAME = new QName("http://www.w3.org/2009/xmlenc11#", "MGF");

   public PBKDF2ParameterType createPBKDF2ParameterType() {
      return new PBKDF2ParameterType();
   }

   public ConcatKDFParamsType createConcatKDFParamsType() {
      return new ConcatKDFParamsType();
   }

   public DerivedKeyType createDerivedKeyType() {
      return new DerivedKeyType();
   }

   public KeyDerivationMethodType createKeyDerivationMethodType() {
      return new KeyDerivationMethodType();
   }

   public MGFType createMGFType() {
      return new MGFType();
   }

   public AlgorithmIdentifierType createAlgorithmIdentifierType() {
      return new AlgorithmIdentifierType();
   }

   public PRFAlgorithmIdentifierType createPRFAlgorithmIdentifierType() {
      return new PRFAlgorithmIdentifierType();
   }

   public PBKDF2ParameterType.Salt createPBKDF2ParameterTypeSalt() {
      return new PBKDF2ParameterType.Salt();
   }

   @XmlElementDecl(
      namespace = "http://www.w3.org/2009/xmlenc11#",
      name = "ConcatKDFParams"
   )
   public JAXBElement createConcatKDFParams(ConcatKDFParamsType value) {
      return new JAXBElement(_ConcatKDFParams_QNAME, ConcatKDFParamsType.class, (Class)null, value);
   }

   @XmlElementDecl(
      namespace = "http://www.w3.org/2009/xmlenc11#",
      name = "DerivedKey"
   )
   public JAXBElement createDerivedKey(DerivedKeyType value) {
      return new JAXBElement(_DerivedKey_QNAME, DerivedKeyType.class, (Class)null, value);
   }

   @XmlElementDecl(
      namespace = "http://www.w3.org/2009/xmlenc11#",
      name = "KeyDerivationMethod"
   )
   public JAXBElement createKeyDerivationMethod(KeyDerivationMethodType value) {
      return new JAXBElement(_KeyDerivationMethod_QNAME, KeyDerivationMethodType.class, (Class)null, value);
   }

   @XmlElementDecl(
      namespace = "http://www.w3.org/2009/xmlenc11#",
      name = "PBKDF2-params"
   )
   public JAXBElement createPBKDF2Params(PBKDF2ParameterType value) {
      return new JAXBElement(_PBKDF2Params_QNAME, PBKDF2ParameterType.class, (Class)null, value);
   }

   @XmlElementDecl(
      namespace = "http://www.w3.org/2009/xmlenc11#",
      name = "MGF"
   )
   public JAXBElement createMGF(MGFType value) {
      return new JAXBElement(_MGF_QNAME, MGFType.class, (Class)null, value);
   }
}
