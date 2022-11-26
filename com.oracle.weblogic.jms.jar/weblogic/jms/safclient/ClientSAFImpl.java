package weblogic.jms.safclient;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.jms.JMSException;
import javax.naming.Context;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import weblogic.jms.extensions.ClientSAF;
import weblogic.jms.extensions.ClientSAFDuplicateException;
import weblogic.utils.XXEUtils;

public final class ClientSAFImpl implements ClientSAF {
   private File rootDirectory;
   private Document document;
   private ClientSAFDelegate delegate;

   public ClientSAFImpl(File paramRootDirectory, InputStream configuration) throws ClientSAFDuplicateException, JMSException {
      this.rootDirectory = paramRootDirectory;
      this.delegate = ClientSAFManager.getManager().getDelegate(this.rootDirectory);
      if (this.delegate != null) {
         throw new ClientSAFDuplicateException(this.delegate.getUserObj());
      } else {
         DocumentBuilderFactory documentBuilderFactory = XXEUtils.createDocumentBuilderFactoryInstance();

         DocumentBuilder documentBuilder;
         try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
         } catch (ParserConfigurationException var8) {
            throw new weblogic.jms.common.JMSException(var8);
         }

         try {
            this.document = documentBuilder.parse(configuration);
         } catch (SAXException var6) {
            throw new weblogic.jms.common.JMSException(var6);
         } catch (IOException var7) {
            throw new weblogic.jms.common.JMSException(var7);
         }

         this.delegate = ClientSAFManager.getManager().createDelegate(this.rootDirectory, this);
      }
   }

   public void open(char[] password) throws JMSException {
      this.delegate.open(this.document, this.rootDirectory, password);
   }

   public void discover(String discoveryFilePath, long cutoffTime) throws JMSException {
      this.delegate.discover(this.document, this.rootDirectory, discoveryFilePath, cutoffTime);
   }

   public void close() {
      if (this.delegate.close()) {
         ClientSAFManager.getManager().removeDelegate(this.rootDirectory);
      }

   }

   public boolean isOpen() {
      return this.delegate.isOpened();
   }

   public Context getContext() {
      return this.delegate.getContext();
   }
}
