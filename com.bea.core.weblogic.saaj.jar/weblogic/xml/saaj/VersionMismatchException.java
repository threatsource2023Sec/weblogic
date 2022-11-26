package weblogic.xml.saaj;

import javax.xml.soap.SOAPException;

public class VersionMismatchException extends SOAPException {
   private final String soapNS;
   private final String mismatchSoapNS;
   private final String mismatchLocalName;

   public VersionMismatchException(String soapNS, String mismatchSoapNS, String mismatchLocalName) {
      this.soapNS = soapNS;
      this.mismatchSoapNS = mismatchSoapNS;
      this.mismatchLocalName = mismatchLocalName;
   }

   public String getSoapNS() {
      return this.soapNS;
   }

   public String getMessage() {
      String msg = "unable to locate Envelope in namespace " + this.soapNS + ".  Root element is \"" + this.mismatchLocalName + "\" in namespace \"" + this.mismatchSoapNS + "\"";
      return msg;
   }
}
