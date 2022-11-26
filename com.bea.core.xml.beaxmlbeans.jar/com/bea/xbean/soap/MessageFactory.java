package com.bea.xbean.soap;

import java.io.IOException;
import java.io.InputStream;

public abstract class MessageFactory {
   private static final String DEFAULT_MESSAGE_FACTORY = "org.apache.axis.soap.MessageFactoryImpl";
   private static final String MESSAGE_FACTORY_PROPERTY = "javax.xml.soap.MessageFactory";

   public static MessageFactory newInstance() throws SOAPException {
      try {
         return (MessageFactory)FactoryFinder.find("javax.xml.soap.MessageFactory", "org.apache.axis.soap.MessageFactoryImpl");
      } catch (Exception var1) {
         throw new SOAPException("Unable to create message factory for SOAP: " + var1.getMessage());
      }
   }

   public abstract SOAPMessage createMessage() throws SOAPException;

   public abstract SOAPMessage createMessage(MimeHeaders var1, InputStream var2) throws IOException, SOAPException;
}
