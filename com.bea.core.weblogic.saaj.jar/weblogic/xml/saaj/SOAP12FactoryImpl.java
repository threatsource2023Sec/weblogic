package weblogic.xml.saaj;

import javax.xml.namespace.QName;
import javax.xml.soap.Detail;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import weblogic.xml.domimpl.DocumentImpl;

public class SOAP12FactoryImpl extends SOAPFactoryImpl {
   DocumentImpl createDocument() {
      return new SaajDocument(true, "http://www.w3.org/2003/05/soap-envelope");
   }

   public Detail createDetail() throws SOAPException {
      return new DetailImpl(this.createDocument(), "http://www.w3.org/2003/05/soap-envelope");
   }

   public SOAPFault createFault(String reasonText, QName faultCode) throws SOAPException {
      SOAP12FaultImpl fault = new SOAP12FaultImpl(this.createDocument());
      fault.setFaultString(reasonText);
      fault.setFaultCode(faultCode);
      return fault;
   }

   public SOAPFault createFault() throws SOAPException {
      SOAP12FaultImpl fault = new SOAP12FaultImpl(this.createDocument());
      fault.setFaultCode(fault.getDefaultFaultCode());
      fault.setFaultString("Server Error");
      return fault;
   }
}
