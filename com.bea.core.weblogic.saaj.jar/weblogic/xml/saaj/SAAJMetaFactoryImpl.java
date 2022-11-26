package weblogic.xml.saaj;

import java.util.HashMap;
import java.util.Map;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SAAJMetaFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;

public class SAAJMetaFactoryImpl extends SAAJMetaFactory {
   private final Map messageFactories = new HashMap();
   private final Map soapFactories = new HashMap();

   public SAAJMetaFactoryImpl() {
      this.messageFactories.put("SOAP 1.1 Protocol", new MessageFactoryImpl("http://schemas.xmlsoap.org/soap/envelope/"));
      this.messageFactories.put("SOAP 1.2 Protocol", new MessageFactoryImpl("http://www.w3.org/2003/05/soap-envelope"));
      this.messageFactories.put("Dynamic Protocol", new MessageFactoryDynamicImpl());
      this.soapFactories.put("SOAP 1.1 Protocol", new SOAPFactoryImpl());
      this.soapFactories.put("SOAP 1.2 Protocol", new SOAP12FactoryImpl());
   }

   public MessageFactory newMessageFactory(String protocol) throws SOAPException {
      MessageFactory messageFactory = (MessageFactory)this.messageFactories.get(protocol);
      if (messageFactory == null) {
         throw new SOAPException("Unknown Protocol: " + protocol + "  specified for creating MessageFactory");
      } else {
         return messageFactory;
      }
   }

   public SOAPFactory newSOAPFactory(String protocol) throws SOAPException {
      SOAPFactory soapFactory = (SOAPFactory)this.soapFactories.get(protocol);
      if (soapFactory == null) {
         throw new SOAPException("Unknown Protocol: " + protocol + "  specified for creating SOAPFactory");
      } else {
         return soapFactory;
      }
   }
}
