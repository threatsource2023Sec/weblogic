package com.bea.security.saml2.artifact;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import net.shibboleth.utilities.java.support.xml.ParserPool;
import net.shibboleth.utilities.java.support.xml.XMLParserException;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.Marshaller;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.io.Unmarshaller;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.SAMLObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ArtifactDataObject implements Serializable {
   private static final long serialVersionUID = -8426302889982196943L;
   private SAMLObject data = null;
   private String partnerName = null;

   public ArtifactDataObject(SAMLObject samlObj, String partnerId) {
      this.data = samlObj;
      this.partnerName = partnerId;
   }

   public SAMLObject getData() {
      return this.data;
   }

   public String getPartnerName() {
      return this.partnerName;
   }

   private void writeObject(ObjectOutputStream out) throws IOException {
      out.writeObject(this.partnerName);
      Marshaller marshaller = XMLObjectProviderRegistrySupport.getMarshallerFactory().getMarshaller(this.data);

      try {
         Element e = marshaller.marshall(this.data);
         TransformerFactory factory = TransformerFactory.newInstance();
         Transformer trans = factory.newTransformer();
         ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
         trans.transform(new DOMSource(e), new StreamResult(baos));
         out.writeObject(baos.toByteArray());
      } catch (MarshallingException var7) {
         throw new IOException(var7.getMessage());
      } catch (TransformerException var8) {
         throw new IOException(var8.getMessage());
      }
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      this.partnerName = (String)in.readObject();
      byte[] xml = (byte[])((byte[])in.readObject());
      ParserPool ppMgr = XMLObjectProviderRegistrySupport.getParserPool();

      try {
         Document doc = ppMgr.parse(new ByteArrayInputStream(xml));
         Unmarshaller unmarshaller = XMLObjectProviderRegistrySupport.getUnmarshallerFactory().getUnmarshaller(doc.getDocumentElement());
         if (unmarshaller == null) {
            throw new IOException("Can't get unmarshaller for the document. rootelement.name=" + doc.getDocumentElement().getLocalName());
         } else {
            this.data = (SAMLObject)unmarshaller.unmarshall(doc.getDocumentElement());
         }
      } catch (XMLParserException var6) {
         throw new IOException(var6.getMessage());
      } catch (UnmarshallingException var7) {
         throw new IOException(var7.getMessage());
      }
   }
}
