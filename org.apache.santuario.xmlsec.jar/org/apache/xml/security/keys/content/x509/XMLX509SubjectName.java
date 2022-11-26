package org.apache.xml.security.keys.content.x509;

import java.security.cert.X509Certificate;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.utils.RFC2253Parser;
import org.apache.xml.security.utils.SignatureElementProxy;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLX509SubjectName extends SignatureElementProxy implements XMLX509DataContent {
   public XMLX509SubjectName(Element element, String baseURI) throws XMLSecurityException {
      super(element, baseURI);
   }

   public XMLX509SubjectName(Document doc, String X509SubjectNameString) {
      super(doc);
      this.addText(X509SubjectNameString);
   }

   public XMLX509SubjectName(Document doc, X509Certificate x509certificate) {
      this(doc, x509certificate.getSubjectX500Principal().getName());
   }

   public String getSubjectName() {
      return RFC2253Parser.normalize(this.getTextFromTextChild());
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof XMLX509SubjectName)) {
         return false;
      } else {
         XMLX509SubjectName other = (XMLX509SubjectName)obj;
         String otherSubject = other.getSubjectName();
         String thisSubject = this.getSubjectName();
         return thisSubject.equals(otherSubject);
      }
   }

   public int hashCode() {
      int result = 17;
      result = 31 * result + this.getSubjectName().hashCode();
      return result;
   }

   public String getBaseLocalName() {
      return "X509SubjectName";
   }
}
