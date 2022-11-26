package com.bea.connector.monitoring1Dot0;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface ResourceAdapterSecurityType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ResourceAdapterSecurityType.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("resourceadaptersecuritytype1cd9type");

   AnonPrincipalType getDefaultPrincipalName();

   boolean isSetDefaultPrincipalName();

   void setDefaultPrincipalName(AnonPrincipalType var1);

   AnonPrincipalType addNewDefaultPrincipalName();

   void unsetDefaultPrincipalName();

   AnonPrincipalType getManageAsPrincipalName();

   boolean isSetManageAsPrincipalName();

   void setManageAsPrincipalName(AnonPrincipalType var1);

   AnonPrincipalType addNewManageAsPrincipalName();

   void unsetManageAsPrincipalName();

   AnonPrincipalCallerType getRunAsPrincipalName();

   boolean isSetRunAsPrincipalName();

   void setRunAsPrincipalName(AnonPrincipalCallerType var1);

   AnonPrincipalCallerType addNewRunAsPrincipalName();

   void unsetRunAsPrincipalName();

   AnonPrincipalCallerType getRunWorkAsPrincipalName();

   boolean isSetRunWorkAsPrincipalName();

   void setRunWorkAsPrincipalName(AnonPrincipalCallerType var1);

   AnonPrincipalCallerType addNewRunWorkAsPrincipalName();

   void unsetRunWorkAsPrincipalName();

   SecurityWorkContextType getSecurityWorkContext();

   boolean isSetSecurityWorkContext();

   void setSecurityWorkContext(SecurityWorkContextType var1);

   SecurityWorkContextType addNewSecurityWorkContext();

   void unsetSecurityWorkContext();

   public static final class Factory {
      public static ResourceAdapterSecurityType newInstance() {
         return (ResourceAdapterSecurityType)XmlBeans.getContextTypeLoader().newInstance(ResourceAdapterSecurityType.type, (XmlOptions)null);
      }

      public static ResourceAdapterSecurityType newInstance(XmlOptions options) {
         return (ResourceAdapterSecurityType)XmlBeans.getContextTypeLoader().newInstance(ResourceAdapterSecurityType.type, options);
      }

      public static ResourceAdapterSecurityType parse(String xmlAsString) throws XmlException {
         return (ResourceAdapterSecurityType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ResourceAdapterSecurityType.type, (XmlOptions)null);
      }

      public static ResourceAdapterSecurityType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ResourceAdapterSecurityType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ResourceAdapterSecurityType.type, options);
      }

      public static ResourceAdapterSecurityType parse(File file) throws XmlException, IOException {
         return (ResourceAdapterSecurityType)XmlBeans.getContextTypeLoader().parse(file, ResourceAdapterSecurityType.type, (XmlOptions)null);
      }

      public static ResourceAdapterSecurityType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ResourceAdapterSecurityType)XmlBeans.getContextTypeLoader().parse(file, ResourceAdapterSecurityType.type, options);
      }

      public static ResourceAdapterSecurityType parse(URL u) throws XmlException, IOException {
         return (ResourceAdapterSecurityType)XmlBeans.getContextTypeLoader().parse(u, ResourceAdapterSecurityType.type, (XmlOptions)null);
      }

      public static ResourceAdapterSecurityType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ResourceAdapterSecurityType)XmlBeans.getContextTypeLoader().parse(u, ResourceAdapterSecurityType.type, options);
      }

      public static ResourceAdapterSecurityType parse(InputStream is) throws XmlException, IOException {
         return (ResourceAdapterSecurityType)XmlBeans.getContextTypeLoader().parse(is, ResourceAdapterSecurityType.type, (XmlOptions)null);
      }

      public static ResourceAdapterSecurityType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ResourceAdapterSecurityType)XmlBeans.getContextTypeLoader().parse(is, ResourceAdapterSecurityType.type, options);
      }

      public static ResourceAdapterSecurityType parse(Reader r) throws XmlException, IOException {
         return (ResourceAdapterSecurityType)XmlBeans.getContextTypeLoader().parse(r, ResourceAdapterSecurityType.type, (XmlOptions)null);
      }

      public static ResourceAdapterSecurityType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ResourceAdapterSecurityType)XmlBeans.getContextTypeLoader().parse(r, ResourceAdapterSecurityType.type, options);
      }

      public static ResourceAdapterSecurityType parse(XMLStreamReader sr) throws XmlException {
         return (ResourceAdapterSecurityType)XmlBeans.getContextTypeLoader().parse(sr, ResourceAdapterSecurityType.type, (XmlOptions)null);
      }

      public static ResourceAdapterSecurityType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ResourceAdapterSecurityType)XmlBeans.getContextTypeLoader().parse(sr, ResourceAdapterSecurityType.type, options);
      }

      public static ResourceAdapterSecurityType parse(Node node) throws XmlException {
         return (ResourceAdapterSecurityType)XmlBeans.getContextTypeLoader().parse(node, ResourceAdapterSecurityType.type, (XmlOptions)null);
      }

      public static ResourceAdapterSecurityType parse(Node node, XmlOptions options) throws XmlException {
         return (ResourceAdapterSecurityType)XmlBeans.getContextTypeLoader().parse(node, ResourceAdapterSecurityType.type, options);
      }

      /** @deprecated */
      public static ResourceAdapterSecurityType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ResourceAdapterSecurityType)XmlBeans.getContextTypeLoader().parse(xis, ResourceAdapterSecurityType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ResourceAdapterSecurityType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ResourceAdapterSecurityType)XmlBeans.getContextTypeLoader().parse(xis, ResourceAdapterSecurityType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ResourceAdapterSecurityType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ResourceAdapterSecurityType.type, options);
      }

      private Factory() {
      }
   }
}
