package weblogic.xml.saaj;

import java.io.IOException;
import java.io.InputStream;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

public class MessageFactoryImpl extends MessageFactory {
   private final String soapNS;

   /** @deprecated */
   @Deprecated
   public MessageFactoryImpl() {
      this("http://schemas.xmlsoap.org/soap/envelope/");
   }

   MessageFactoryImpl(String soapNS) {
      this.soapNS = soapNS;
   }

   public SOAPMessage createMessage() throws SOAPException {
      try {
         return this.createMessage(false);
      } catch (IOException var2) {
         throw new SOAPException("error creating SOAPMesssage " + var2.getMessage());
      }
   }

   public SOAPMessage createMessage(boolean useStreamingAttachments) throws IOException, SOAPException {
      return this.createMessage(useStreamingAttachments, false);
   }

   public SOAPMessage createMessage(boolean useStreamingAttachments, boolean mtomEnabled) throws IOException, SOAPException {
      return new SOAPMessageImpl(useStreamingAttachments, mtomEnabled, this.soapNS);
   }

   public SOAPMessage createMessage(MimeHeaders mimeHeaders, InputStream inputStream) throws IOException, SOAPException {
      return this.createMessage(mimeHeaders, inputStream, false);
   }

   public SOAPMessage createMessage(MimeHeaders mimeHeaders, InputStream inputStream, boolean useStreamingAttachments) throws IOException, SOAPException {
      return this.createMessage(mimeHeaders, inputStream, useStreamingAttachments, false);
   }

   public SOAPMessage createMessage(MimeHeaders mimeHeaders, InputStream inputStream, boolean useStreamingAttachments, boolean isMTOMmessage) throws IOException, SOAPException {
      if (inputStream == null) {
         throw new SOAPException("Error creating Message.  Inputstream cannot be null.");
      } else {
         if (mimeHeaders == null) {
            mimeHeaders = new MimeHeaders();
         }

         return new SOAPMessageImpl(mimeHeaders, inputStream, useStreamingAttachments, isMTOMmessage, this.soapNS);
      }
   }
}
