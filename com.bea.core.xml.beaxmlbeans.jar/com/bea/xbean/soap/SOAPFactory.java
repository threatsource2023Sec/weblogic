package com.bea.xbean.soap;

public abstract class SOAPFactory {
   private static final String SF_PROPERTY = "javax.xml.soap.SOAPFactory";
   private static final String DEFAULT_SF = "org.apache.axis.soap.SOAPFactoryImpl";

   public abstract SOAPElement createElement(Name var1) throws SOAPException;

   public abstract SOAPElement createElement(String var1) throws SOAPException;

   public abstract SOAPElement createElement(String var1, String var2, String var3) throws SOAPException;

   public abstract Detail createDetail() throws SOAPException;

   public abstract Name createName(String var1, String var2, String var3) throws SOAPException;

   public abstract Name createName(String var1) throws SOAPException;

   public static SOAPFactory newInstance() throws SOAPException {
      try {
         return (SOAPFactory)FactoryFinder.find("javax.xml.soap.SOAPFactory", "org.apache.axis.soap.SOAPFactoryImpl");
      } catch (Exception var1) {
         throw new SOAPException("Unable to create SOAP Factory: " + var1.getMessage());
      }
   }
}
