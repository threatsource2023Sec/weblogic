package org.apache.xml.security.keys.content;

import java.math.BigInteger;
import java.security.cert.X509Certificate;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.keys.content.x509.XMLX509CRL;
import org.apache.xml.security.keys.content.x509.XMLX509Certificate;
import org.apache.xml.security.keys.content.x509.XMLX509Digest;
import org.apache.xml.security.keys.content.x509.XMLX509IssuerSerial;
import org.apache.xml.security.keys.content.x509.XMLX509SKI;
import org.apache.xml.security.keys.content.x509.XMLX509SubjectName;
import org.apache.xml.security.utils.SignatureElementProxy;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class X509Data extends SignatureElementProxy implements KeyInfoContent {
   private static final Logger LOG = LoggerFactory.getLogger(X509Data.class);

   public X509Data(Document doc) {
      super(doc);
      this.addReturnToSelf();
   }

   public X509Data(Element element, String baseURI) throws XMLSecurityException {
      super(element, baseURI);

      Node sibling;
      for(sibling = this.getFirstChild(); sibling != null && sibling.getNodeType() != 1; sibling = sibling.getNextSibling()) {
      }

      if (sibling == null || sibling.getNodeType() != 1) {
         Object[] exArgs = new Object[]{"Elements", "X509Data"};
         throw new XMLSecurityException("xml.WrongContent", exArgs);
      }
   }

   public void addIssuerSerial(String X509IssuerName, BigInteger X509SerialNumber) {
      this.add(new XMLX509IssuerSerial(this.getDocument(), X509IssuerName, X509SerialNumber));
   }

   public void addIssuerSerial(String X509IssuerName, String X509SerialNumber) {
      this.add(new XMLX509IssuerSerial(this.getDocument(), X509IssuerName, X509SerialNumber));
   }

   public void addIssuerSerial(String X509IssuerName, int X509SerialNumber) {
      this.add(new XMLX509IssuerSerial(this.getDocument(), X509IssuerName, X509SerialNumber));
   }

   public void add(XMLX509IssuerSerial xmlX509IssuerSerial) {
      this.appendSelf(xmlX509IssuerSerial);
      this.addReturnToSelf();
   }

   public void addSKI(byte[] skiBytes) {
      this.add(new XMLX509SKI(this.getDocument(), skiBytes));
   }

   public void addSKI(X509Certificate x509certificate) throws XMLSecurityException {
      this.add(new XMLX509SKI(this.getDocument(), x509certificate));
   }

   public void add(XMLX509SKI xmlX509SKI) {
      this.appendSelf(xmlX509SKI);
      this.addReturnToSelf();
   }

   public void addSubjectName(String subjectName) {
      this.add(new XMLX509SubjectName(this.getDocument(), subjectName));
   }

   public void addSubjectName(X509Certificate x509certificate) {
      this.add(new XMLX509SubjectName(this.getDocument(), x509certificate));
   }

   public void add(XMLX509SubjectName xmlX509SubjectName) {
      this.appendSelf(xmlX509SubjectName);
      this.addReturnToSelf();
   }

   public void addCertificate(X509Certificate x509certificate) throws XMLSecurityException {
      this.add(new XMLX509Certificate(this.getDocument(), x509certificate));
   }

   public void addCertificate(byte[] x509certificateBytes) {
      this.add(new XMLX509Certificate(this.getDocument(), x509certificateBytes));
   }

   public void add(XMLX509Certificate xmlX509Certificate) {
      this.appendSelf(xmlX509Certificate);
      this.addReturnToSelf();
   }

   public void addCRL(byte[] crlBytes) {
      this.add(new XMLX509CRL(this.getDocument(), crlBytes));
   }

   public void add(XMLX509CRL xmlX509CRL) {
      this.appendSelf(xmlX509CRL);
      this.addReturnToSelf();
   }

   public void addDigest(X509Certificate x509certificate, String algorithmURI) throws XMLSecurityException {
      this.add(new XMLX509Digest(this.getDocument(), x509certificate, algorithmURI));
   }

   public void addDigest(byte[] x509CertificateDigestBytes, String algorithmURI) {
      this.add(new XMLX509Digest(this.getDocument(), x509CertificateDigestBytes, algorithmURI));
   }

   public void add(XMLX509Digest xmlX509Digest) {
      this.appendSelf(xmlX509Digest);
      this.addReturnToSelf();
   }

   public void addUnknownElement(Element element) {
      this.appendSelf(element);
      this.addReturnToSelf();
   }

   public int lengthIssuerSerial() {
      return this.length("http://www.w3.org/2000/09/xmldsig#", "X509IssuerSerial");
   }

   public int lengthSKI() {
      return this.length("http://www.w3.org/2000/09/xmldsig#", "X509SKI");
   }

   public int lengthSubjectName() {
      return this.length("http://www.w3.org/2000/09/xmldsig#", "X509SubjectName");
   }

   public int lengthCertificate() {
      return this.length("http://www.w3.org/2000/09/xmldsig#", "X509Certificate");
   }

   public int lengthCRL() {
      return this.length("http://www.w3.org/2000/09/xmldsig#", "X509CRL");
   }

   public int lengthDigest() {
      return this.length("http://www.w3.org/2009/xmldsig11#", "X509Digest");
   }

   public int lengthUnknownElement() {
      int result = 0;

      for(Node n = this.getFirstChild(); n != null; n = n.getNextSibling()) {
         if (n.getNodeType() == 1 && !n.getNamespaceURI().equals("http://www.w3.org/2000/09/xmldsig#")) {
            ++result;
         }
      }

      return result;
   }

   public XMLX509IssuerSerial itemIssuerSerial(int i) throws XMLSecurityException {
      Element e = XMLUtils.selectDsNode(this.getFirstChild(), "X509IssuerSerial", i);
      return e != null ? new XMLX509IssuerSerial(e, this.baseURI) : null;
   }

   public XMLX509SKI itemSKI(int i) throws XMLSecurityException {
      Element e = XMLUtils.selectDsNode(this.getFirstChild(), "X509SKI", i);
      return e != null ? new XMLX509SKI(e, this.baseURI) : null;
   }

   public XMLX509SubjectName itemSubjectName(int i) throws XMLSecurityException {
      Element e = XMLUtils.selectDsNode(this.getFirstChild(), "X509SubjectName", i);
      return e != null ? new XMLX509SubjectName(e, this.baseURI) : null;
   }

   public XMLX509Certificate itemCertificate(int i) throws XMLSecurityException {
      Element e = XMLUtils.selectDsNode(this.getFirstChild(), "X509Certificate", i);
      return e != null ? new XMLX509Certificate(e, this.baseURI) : null;
   }

   public XMLX509CRL itemCRL(int i) throws XMLSecurityException {
      Element e = XMLUtils.selectDsNode(this.getFirstChild(), "X509CRL", i);
      return e != null ? new XMLX509CRL(e, this.baseURI) : null;
   }

   public XMLX509Digest itemDigest(int i) throws XMLSecurityException {
      Element e = XMLUtils.selectDs11Node(this.getFirstChild(), "X509Digest", i);
      return e != null ? new XMLX509Digest(e, this.baseURI) : null;
   }

   public Element itemUnknownElement(int i) {
      LOG.debug("itemUnknownElement not implemented: {}", i);
      return null;
   }

   public boolean containsIssuerSerial() {
      return this.lengthIssuerSerial() > 0;
   }

   public boolean containsSKI() {
      return this.lengthSKI() > 0;
   }

   public boolean containsSubjectName() {
      return this.lengthSubjectName() > 0;
   }

   public boolean containsCertificate() {
      return this.lengthCertificate() > 0;
   }

   public boolean containsDigest() {
      return this.lengthDigest() > 0;
   }

   public boolean containsCRL() {
      return this.lengthCRL() > 0;
   }

   public boolean containsUnknownElement() {
      return this.lengthUnknownElement() > 0;
   }

   public String getBaseLocalName() {
      return "X509Data";
   }
}
