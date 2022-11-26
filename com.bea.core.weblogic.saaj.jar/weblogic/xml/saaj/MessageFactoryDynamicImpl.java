package weblogic.xml.saaj;

import java.io.IOException;
import java.io.InputStream;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

public class MessageFactoryDynamicImpl extends MessageFactory {
   public SOAPMessage createMessage() throws SOAPException {
      throw new UnsupportedOperationException("createMessage() not supported for Dynamic Protocol");
   }

   public SOAPMessage createMessage(MimeHeaders mimeHeaders, InputStream inputStream) throws IOException, SOAPException {
      return MessageFactory.newInstance().createMessage(mimeHeaders, inputStream);
   }
}
