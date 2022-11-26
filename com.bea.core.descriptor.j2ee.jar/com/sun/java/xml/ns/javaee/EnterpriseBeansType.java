package com.sun.java.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface EnterpriseBeansType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(EnterpriseBeansType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("enterprisebeanstype09c4type");

   SessionBeanType[] getSessionArray();

   SessionBeanType getSessionArray(int var1);

   int sizeOfSessionArray();

   void setSessionArray(SessionBeanType[] var1);

   void setSessionArray(int var1, SessionBeanType var2);

   SessionBeanType insertNewSession(int var1);

   SessionBeanType addNewSession();

   void removeSession(int var1);

   EntityBeanType[] getEntityArray();

   EntityBeanType getEntityArray(int var1);

   int sizeOfEntityArray();

   void setEntityArray(EntityBeanType[] var1);

   void setEntityArray(int var1, EntityBeanType var2);

   EntityBeanType insertNewEntity(int var1);

   EntityBeanType addNewEntity();

   void removeEntity(int var1);

   MessageDrivenBeanType[] getMessageDrivenArray();

   MessageDrivenBeanType getMessageDrivenArray(int var1);

   int sizeOfMessageDrivenArray();

   void setMessageDrivenArray(MessageDrivenBeanType[] var1);

   void setMessageDrivenArray(int var1, MessageDrivenBeanType var2);

   MessageDrivenBeanType insertNewMessageDriven(int var1);

   MessageDrivenBeanType addNewMessageDriven();

   void removeMessageDriven(int var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static EnterpriseBeansType newInstance() {
         return (EnterpriseBeansType)XmlBeans.getContextTypeLoader().newInstance(EnterpriseBeansType.type, (XmlOptions)null);
      }

      public static EnterpriseBeansType newInstance(XmlOptions options) {
         return (EnterpriseBeansType)XmlBeans.getContextTypeLoader().newInstance(EnterpriseBeansType.type, options);
      }

      public static EnterpriseBeansType parse(java.lang.String xmlAsString) throws XmlException {
         return (EnterpriseBeansType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EnterpriseBeansType.type, (XmlOptions)null);
      }

      public static EnterpriseBeansType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (EnterpriseBeansType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EnterpriseBeansType.type, options);
      }

      public static EnterpriseBeansType parse(File file) throws XmlException, IOException {
         return (EnterpriseBeansType)XmlBeans.getContextTypeLoader().parse(file, EnterpriseBeansType.type, (XmlOptions)null);
      }

      public static EnterpriseBeansType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (EnterpriseBeansType)XmlBeans.getContextTypeLoader().parse(file, EnterpriseBeansType.type, options);
      }

      public static EnterpriseBeansType parse(URL u) throws XmlException, IOException {
         return (EnterpriseBeansType)XmlBeans.getContextTypeLoader().parse(u, EnterpriseBeansType.type, (XmlOptions)null);
      }

      public static EnterpriseBeansType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (EnterpriseBeansType)XmlBeans.getContextTypeLoader().parse(u, EnterpriseBeansType.type, options);
      }

      public static EnterpriseBeansType parse(InputStream is) throws XmlException, IOException {
         return (EnterpriseBeansType)XmlBeans.getContextTypeLoader().parse(is, EnterpriseBeansType.type, (XmlOptions)null);
      }

      public static EnterpriseBeansType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (EnterpriseBeansType)XmlBeans.getContextTypeLoader().parse(is, EnterpriseBeansType.type, options);
      }

      public static EnterpriseBeansType parse(Reader r) throws XmlException, IOException {
         return (EnterpriseBeansType)XmlBeans.getContextTypeLoader().parse(r, EnterpriseBeansType.type, (XmlOptions)null);
      }

      public static EnterpriseBeansType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (EnterpriseBeansType)XmlBeans.getContextTypeLoader().parse(r, EnterpriseBeansType.type, options);
      }

      public static EnterpriseBeansType parse(XMLStreamReader sr) throws XmlException {
         return (EnterpriseBeansType)XmlBeans.getContextTypeLoader().parse(sr, EnterpriseBeansType.type, (XmlOptions)null);
      }

      public static EnterpriseBeansType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (EnterpriseBeansType)XmlBeans.getContextTypeLoader().parse(sr, EnterpriseBeansType.type, options);
      }

      public static EnterpriseBeansType parse(Node node) throws XmlException {
         return (EnterpriseBeansType)XmlBeans.getContextTypeLoader().parse(node, EnterpriseBeansType.type, (XmlOptions)null);
      }

      public static EnterpriseBeansType parse(Node node, XmlOptions options) throws XmlException {
         return (EnterpriseBeansType)XmlBeans.getContextTypeLoader().parse(node, EnterpriseBeansType.type, options);
      }

      /** @deprecated */
      public static EnterpriseBeansType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (EnterpriseBeansType)XmlBeans.getContextTypeLoader().parse(xis, EnterpriseBeansType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static EnterpriseBeansType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (EnterpriseBeansType)XmlBeans.getContextTypeLoader().parse(xis, EnterpriseBeansType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EnterpriseBeansType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EnterpriseBeansType.type, options);
      }

      private Factory() {
      }
   }
}
