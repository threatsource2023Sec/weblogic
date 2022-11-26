package com.bea.httppubsub.internal;

import com.bea.httppubsub.PubSubConfigFactory;
import com.bea.httppubsub.PubSubLogger;
import com.bea.httppubsub.PubSubServerException;
import com.bea.httppubsub.descriptor.WeblogicPubsubBean;
import com.bea.httppubsub.util.IOUtils;
import com.bea.xml.XmlValidationError;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import weblogic.descriptor.DescriptorManager;
import weblogic.utils.ErrorCollectionException;
import weblogic.utils.XXEUtils;

public class PubSubConfigFactoryImpl implements PubSubConfigFactory {
   private static final String WEBLOGIC_PUBSUB_DESCRIPTOR = "/WEB-INF/weblogic-pubsub.xml";
   private static final String WEBLOGIC_DESCRIPTOR = "/WEB-INF/weblogic.xml";
   private static final String BEA_PUBSUB_SCHEMA = "http://www.bea.com/ns/weblogic/weblogic-pubsub";
   private static final String PUBSUB_SCHEMA = "http://xmlns.oracle.com/weblogic/weblogic-pubsub";

   public WeblogicPubsubBean getWeblogicPubsubBean(ServletContext context) throws PubSubServerException {
      InputStream is = null;

      WeblogicPubsubBean var7;
      try {
         ClassLoader cl = Thread.currentThread().getContextClassLoader();
         DescriptorManager descriptorManager = new DescriptorManager(cl);
         is = context.getResourceAsStream("/WEB-INF/weblogic-pubsub.xml");
         List errors = new ArrayList();
         WeblogicPubsubBean bean = (WeblogicPubsubBean)descriptorManager.createDescriptor(new NamespaceURIMunger(is, "http://xmlns.oracle.com/weblogic/weblogic-pubsub", new String[]{"http://www.bea.com/ns/weblogic/weblogic-pubsub"}), errors, true).getRootBean();
         this.handleSchemaValidationErrors(errors);
         var7 = bean;
      } catch (Exception var11) {
         PubSubLogger.logConfigurationValidationProblem(var11.getMessage(), var11);
         throw new PubSubServerException("Can't load descriptor file weblogic-pubsub.xml", var11);
      } finally {
         IOUtils.closeInputStreamIfNecessary(is);
      }

      return var7;
   }

   public boolean isReplicatedSessionEnable(ServletContext context) {
      InputStream is = context.getResourceAsStream("/WEB-INF/weblogic.xml");
      if (is == null) {
         return false;
      } else {
         try {
            DocumentBuilderFactory factory = XXEUtils.createDocumentBuilderFactoryInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document document = docBuilder.parse(is);
            Element root = document.getDocumentElement();
            String namespaceURI = root.getNamespaceURI();
            NodeList nl = root.getElementsByTagNameNS(namespaceURI, "session-descriptor");
            if (nl != null && nl.getLength() != 0) {
               Element sessionDescriptorElement = (Element)nl.item(0);
               nl = sessionDescriptorElement.getElementsByTagNameNS(namespaceURI, "persistent-store-type");
               if (nl != null && nl.getLength() != 0) {
                  Element ele = (Element)nl.item(0);
                  String content = ele.getTextContent();
                  return "replicated".equalsIgnoreCase(content) || "replicated_if_clustered".equalsIgnoreCase(content) || "sync-replication-across-cluster".equalsIgnoreCase(content) || "async-replication-across-cluster".equalsIgnoreCase(content);
               } else {
                  return false;
               }
            } else {
               return false;
            }
         } catch (Exception var12) {
            return false;
         }
      }
   }

   private void handleSchemaValidationErrors(List errors) throws ErrorCollectionException {
      ErrorCollectionException exceptions = new ErrorCollectionException();
      Iterator it = errors.iterator();

      while(it.hasNext()) {
         Object o = it.next();
         if (o instanceof XmlValidationError) {
            XmlValidationError ve = (XmlValidationError)o;
            exceptions.add(new SchemaValidationException(ve.getMessage()));
         } else {
            exceptions.add(new SchemaValidationException(o.toString()));
         }
      }

      if (!exceptions.isEmpty()) {
         throw exceptions;
      }
   }
}
