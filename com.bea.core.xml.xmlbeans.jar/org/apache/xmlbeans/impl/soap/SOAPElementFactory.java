package org.apache.xmlbeans.impl.soap;

/** @deprecated */
public class SOAPElementFactory {
   private SOAPFactory sf;

   private SOAPElementFactory(SOAPFactory soapfactory) {
      this.sf = soapfactory;
   }

   /** @deprecated */
   public SOAPElement create(Name name) throws SOAPException {
      return this.sf.createElement(name);
   }

   /** @deprecated */
   public SOAPElement create(String localName) throws SOAPException {
      return this.sf.createElement(localName);
   }

   /** @deprecated */
   public SOAPElement create(String localName, String prefix, String uri) throws SOAPException {
      return this.sf.createElement(localName, prefix, uri);
   }

   /** @deprecated */
   public static SOAPElementFactory newInstance() throws SOAPException {
      try {
         return new SOAPElementFactory(SOAPFactory.newInstance());
      } catch (Exception var1) {
         throw new SOAPException("Unable to create SOAP Element Factory: " + var1.getMessage());
      }
   }
}
