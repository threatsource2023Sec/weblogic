package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface CustomRemoteCommitProviderType extends RemoteCommitProviderType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CustomRemoteCommitProviderType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("customremotecommitprovidertype4d10type");

   String getClassname();

   XmlString xgetClassname();

   boolean isNilClassname();

   boolean isSetClassname();

   void setClassname(String var1);

   void xsetClassname(XmlString var1);

   void setNilClassname();

   void unsetClassname();

   PropertiesType getProperties();

   boolean isNilProperties();

   boolean isSetProperties();

   void setProperties(PropertiesType var1);

   PropertiesType addNewProperties();

   void setNilProperties();

   void unsetProperties();

   public static final class Factory {
      public static CustomRemoteCommitProviderType newInstance() {
         return (CustomRemoteCommitProviderType)XmlBeans.getContextTypeLoader().newInstance(CustomRemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static CustomRemoteCommitProviderType newInstance(XmlOptions options) {
         return (CustomRemoteCommitProviderType)XmlBeans.getContextTypeLoader().newInstance(CustomRemoteCommitProviderType.type, options);
      }

      public static CustomRemoteCommitProviderType parse(String xmlAsString) throws XmlException {
         return (CustomRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomRemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static CustomRemoteCommitProviderType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CustomRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomRemoteCommitProviderType.type, options);
      }

      public static CustomRemoteCommitProviderType parse(File file) throws XmlException, IOException {
         return (CustomRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(file, CustomRemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static CustomRemoteCommitProviderType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CustomRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(file, CustomRemoteCommitProviderType.type, options);
      }

      public static CustomRemoteCommitProviderType parse(URL u) throws XmlException, IOException {
         return (CustomRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(u, CustomRemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static CustomRemoteCommitProviderType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CustomRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(u, CustomRemoteCommitProviderType.type, options);
      }

      public static CustomRemoteCommitProviderType parse(InputStream is) throws XmlException, IOException {
         return (CustomRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(is, CustomRemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static CustomRemoteCommitProviderType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CustomRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(is, CustomRemoteCommitProviderType.type, options);
      }

      public static CustomRemoteCommitProviderType parse(Reader r) throws XmlException, IOException {
         return (CustomRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(r, CustomRemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static CustomRemoteCommitProviderType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CustomRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(r, CustomRemoteCommitProviderType.type, options);
      }

      public static CustomRemoteCommitProviderType parse(XMLStreamReader sr) throws XmlException {
         return (CustomRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(sr, CustomRemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static CustomRemoteCommitProviderType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CustomRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(sr, CustomRemoteCommitProviderType.type, options);
      }

      public static CustomRemoteCommitProviderType parse(Node node) throws XmlException {
         return (CustomRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(node, CustomRemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static CustomRemoteCommitProviderType parse(Node node, XmlOptions options) throws XmlException {
         return (CustomRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(node, CustomRemoteCommitProviderType.type, options);
      }

      /** @deprecated */
      public static CustomRemoteCommitProviderType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CustomRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(xis, CustomRemoteCommitProviderType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CustomRemoteCommitProviderType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CustomRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(xis, CustomRemoteCommitProviderType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomRemoteCommitProviderType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomRemoteCommitProviderType.type, options);
      }

      private Factory() {
      }
   }
}
