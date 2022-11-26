package org.jcp.xmlns.xml.ns.javaee;

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

public interface AdminobjectType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AdminobjectType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("adminobjecttype6a2ftype");

   FullyQualifiedClassType getAdminobjectInterface();

   void setAdminobjectInterface(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewAdminobjectInterface();

   FullyQualifiedClassType getAdminobjectClass();

   void setAdminobjectClass(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewAdminobjectClass();

   ConfigPropertyType[] getConfigPropertyArray();

   ConfigPropertyType getConfigPropertyArray(int var1);

   int sizeOfConfigPropertyArray();

   void setConfigPropertyArray(ConfigPropertyType[] var1);

   void setConfigPropertyArray(int var1, ConfigPropertyType var2);

   ConfigPropertyType insertNewConfigProperty(int var1);

   ConfigPropertyType addNewConfigProperty();

   void removeConfigProperty(int var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static AdminobjectType newInstance() {
         return (AdminobjectType)XmlBeans.getContextTypeLoader().newInstance(AdminobjectType.type, (XmlOptions)null);
      }

      public static AdminobjectType newInstance(XmlOptions options) {
         return (AdminobjectType)XmlBeans.getContextTypeLoader().newInstance(AdminobjectType.type, options);
      }

      public static AdminobjectType parse(java.lang.String xmlAsString) throws XmlException {
         return (AdminobjectType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AdminobjectType.type, (XmlOptions)null);
      }

      public static AdminobjectType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (AdminobjectType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AdminobjectType.type, options);
      }

      public static AdminobjectType parse(File file) throws XmlException, IOException {
         return (AdminobjectType)XmlBeans.getContextTypeLoader().parse(file, AdminobjectType.type, (XmlOptions)null);
      }

      public static AdminobjectType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AdminobjectType)XmlBeans.getContextTypeLoader().parse(file, AdminobjectType.type, options);
      }

      public static AdminobjectType parse(URL u) throws XmlException, IOException {
         return (AdminobjectType)XmlBeans.getContextTypeLoader().parse(u, AdminobjectType.type, (XmlOptions)null);
      }

      public static AdminobjectType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AdminobjectType)XmlBeans.getContextTypeLoader().parse(u, AdminobjectType.type, options);
      }

      public static AdminobjectType parse(InputStream is) throws XmlException, IOException {
         return (AdminobjectType)XmlBeans.getContextTypeLoader().parse(is, AdminobjectType.type, (XmlOptions)null);
      }

      public static AdminobjectType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AdminobjectType)XmlBeans.getContextTypeLoader().parse(is, AdminobjectType.type, options);
      }

      public static AdminobjectType parse(Reader r) throws XmlException, IOException {
         return (AdminobjectType)XmlBeans.getContextTypeLoader().parse(r, AdminobjectType.type, (XmlOptions)null);
      }

      public static AdminobjectType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AdminobjectType)XmlBeans.getContextTypeLoader().parse(r, AdminobjectType.type, options);
      }

      public static AdminobjectType parse(XMLStreamReader sr) throws XmlException {
         return (AdminobjectType)XmlBeans.getContextTypeLoader().parse(sr, AdminobjectType.type, (XmlOptions)null);
      }

      public static AdminobjectType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AdminobjectType)XmlBeans.getContextTypeLoader().parse(sr, AdminobjectType.type, options);
      }

      public static AdminobjectType parse(Node node) throws XmlException {
         return (AdminobjectType)XmlBeans.getContextTypeLoader().parse(node, AdminobjectType.type, (XmlOptions)null);
      }

      public static AdminobjectType parse(Node node, XmlOptions options) throws XmlException {
         return (AdminobjectType)XmlBeans.getContextTypeLoader().parse(node, AdminobjectType.type, options);
      }

      /** @deprecated */
      public static AdminobjectType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AdminobjectType)XmlBeans.getContextTypeLoader().parse(xis, AdminobjectType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AdminobjectType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AdminobjectType)XmlBeans.getContextTypeLoader().parse(xis, AdminobjectType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AdminobjectType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AdminobjectType.type, options);
      }

      private Factory() {
      }
   }
}
