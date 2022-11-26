package weblogic.security.SSL;

import java.security.cert.X509Certificate;
import javax.security.auth.Subject;

public class CertificateCallbackInfo {
   private String srcServerName;
   private String srcDomainName;
   private String srcAddress;
   private int srcPort;
   private String dstAddress;
   private int dstPort;
   private Subject subject;
   private X509Certificate[] certificate;

   public CertificateCallbackInfo(String srcServerName, String srcDomainName, String srcAddress, int srcPort, String dstAddress, int dstPort, Subject subject, X509Certificate[] certificate) {
      this.srcServerName = srcServerName;
      this.srcDomainName = srcDomainName;
      this.srcAddress = srcAddress;
      this.srcPort = srcPort;
      this.dstAddress = dstAddress;
      this.dstPort = dstPort;
      this.subject = subject;
      this.certificate = certificate;
   }

   public Subject getSubject() {
      return this.subject;
   }

   public X509Certificate[] getCertificate() {
      return this.certificate;
   }

   public String getSrcAddress() {
      return this.srcAddress;
   }

   public String getDomainName() {
      return this.srcDomainName;
   }

   public int getSrcPort() {
      return this.srcPort;
   }

   public String getServerName() {
      return this.srcServerName;
   }

   public String getDstAddress() {
      return this.dstAddress;
   }

   public int getDstPort() {
      return this.dstPort;
   }
}
