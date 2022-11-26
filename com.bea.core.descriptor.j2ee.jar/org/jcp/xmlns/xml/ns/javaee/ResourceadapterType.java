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

public interface ResourceadapterType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ResourceadapterType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("resourceadaptertype861ctype");

   FullyQualifiedClassType getResourceadapterClass();

   boolean isSetResourceadapterClass();

   void setResourceadapterClass(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewResourceadapterClass();

   void unsetResourceadapterClass();

   ConfigPropertyType[] getConfigPropertyArray();

   ConfigPropertyType getConfigPropertyArray(int var1);

   int sizeOfConfigPropertyArray();

   void setConfigPropertyArray(ConfigPropertyType[] var1);

   void setConfigPropertyArray(int var1, ConfigPropertyType var2);

   ConfigPropertyType insertNewConfigProperty(int var1);

   ConfigPropertyType addNewConfigProperty();

   void removeConfigProperty(int var1);

   OutboundResourceadapterType getOutboundResourceadapter();

   boolean isSetOutboundResourceadapter();

   void setOutboundResourceadapter(OutboundResourceadapterType var1);

   OutboundResourceadapterType addNewOutboundResourceadapter();

   void unsetOutboundResourceadapter();

   InboundResourceadapterType getInboundResourceadapter();

   boolean isSetInboundResourceadapter();

   void setInboundResourceadapter(InboundResourceadapterType var1);

   InboundResourceadapterType addNewInboundResourceadapter();

   void unsetInboundResourceadapter();

   AdminobjectType[] getAdminobjectArray();

   AdminobjectType getAdminobjectArray(int var1);

   int sizeOfAdminobjectArray();

   void setAdminobjectArray(AdminobjectType[] var1);

   void setAdminobjectArray(int var1, AdminobjectType var2);

   AdminobjectType insertNewAdminobject(int var1);

   AdminobjectType addNewAdminobject();

   void removeAdminobject(int var1);

   SecurityPermissionType[] getSecurityPermissionArray();

   SecurityPermissionType getSecurityPermissionArray(int var1);

   int sizeOfSecurityPermissionArray();

   void setSecurityPermissionArray(SecurityPermissionType[] var1);

   void setSecurityPermissionArray(int var1, SecurityPermissionType var2);

   SecurityPermissionType insertNewSecurityPermission(int var1);

   SecurityPermissionType addNewSecurityPermission();

   void removeSecurityPermission(int var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ResourceadapterType newInstance() {
         return (ResourceadapterType)XmlBeans.getContextTypeLoader().newInstance(ResourceadapterType.type, (XmlOptions)null);
      }

      public static ResourceadapterType newInstance(XmlOptions options) {
         return (ResourceadapterType)XmlBeans.getContextTypeLoader().newInstance(ResourceadapterType.type, options);
      }

      public static ResourceadapterType parse(java.lang.String xmlAsString) throws XmlException {
         return (ResourceadapterType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ResourceadapterType.type, (XmlOptions)null);
      }

      public static ResourceadapterType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ResourceadapterType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ResourceadapterType.type, options);
      }

      public static ResourceadapterType parse(File file) throws XmlException, IOException {
         return (ResourceadapterType)XmlBeans.getContextTypeLoader().parse(file, ResourceadapterType.type, (XmlOptions)null);
      }

      public static ResourceadapterType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ResourceadapterType)XmlBeans.getContextTypeLoader().parse(file, ResourceadapterType.type, options);
      }

      public static ResourceadapterType parse(URL u) throws XmlException, IOException {
         return (ResourceadapterType)XmlBeans.getContextTypeLoader().parse(u, ResourceadapterType.type, (XmlOptions)null);
      }

      public static ResourceadapterType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ResourceadapterType)XmlBeans.getContextTypeLoader().parse(u, ResourceadapterType.type, options);
      }

      public static ResourceadapterType parse(InputStream is) throws XmlException, IOException {
         return (ResourceadapterType)XmlBeans.getContextTypeLoader().parse(is, ResourceadapterType.type, (XmlOptions)null);
      }

      public static ResourceadapterType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ResourceadapterType)XmlBeans.getContextTypeLoader().parse(is, ResourceadapterType.type, options);
      }

      public static ResourceadapterType parse(Reader r) throws XmlException, IOException {
         return (ResourceadapterType)XmlBeans.getContextTypeLoader().parse(r, ResourceadapterType.type, (XmlOptions)null);
      }

      public static ResourceadapterType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ResourceadapterType)XmlBeans.getContextTypeLoader().parse(r, ResourceadapterType.type, options);
      }

      public static ResourceadapterType parse(XMLStreamReader sr) throws XmlException {
         return (ResourceadapterType)XmlBeans.getContextTypeLoader().parse(sr, ResourceadapterType.type, (XmlOptions)null);
      }

      public static ResourceadapterType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ResourceadapterType)XmlBeans.getContextTypeLoader().parse(sr, ResourceadapterType.type, options);
      }

      public static ResourceadapterType parse(Node node) throws XmlException {
         return (ResourceadapterType)XmlBeans.getContextTypeLoader().parse(node, ResourceadapterType.type, (XmlOptions)null);
      }

      public static ResourceadapterType parse(Node node, XmlOptions options) throws XmlException {
         return (ResourceadapterType)XmlBeans.getContextTypeLoader().parse(node, ResourceadapterType.type, options);
      }

      /** @deprecated */
      public static ResourceadapterType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ResourceadapterType)XmlBeans.getContextTypeLoader().parse(xis, ResourceadapterType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ResourceadapterType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ResourceadapterType)XmlBeans.getContextTypeLoader().parse(xis, ResourceadapterType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ResourceadapterType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ResourceadapterType.type, options);
      }

      private Factory() {
      }
   }
}
