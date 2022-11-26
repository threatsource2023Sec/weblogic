package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface DefaultOrphanedKeyActionType extends LogOrphanedKeyActionType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DefaultOrphanedKeyActionType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("defaultorphanedkeyactiontype68eetype");

   public static final class Factory {
      public static DefaultOrphanedKeyActionType newInstance() {
         return (DefaultOrphanedKeyActionType)XmlBeans.getContextTypeLoader().newInstance(DefaultOrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static DefaultOrphanedKeyActionType newInstance(XmlOptions options) {
         return (DefaultOrphanedKeyActionType)XmlBeans.getContextTypeLoader().newInstance(DefaultOrphanedKeyActionType.type, options);
      }

      public static DefaultOrphanedKeyActionType parse(String xmlAsString) throws XmlException {
         return (DefaultOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultOrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static DefaultOrphanedKeyActionType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DefaultOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultOrphanedKeyActionType.type, options);
      }

      public static DefaultOrphanedKeyActionType parse(File file) throws XmlException, IOException {
         return (DefaultOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(file, DefaultOrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static DefaultOrphanedKeyActionType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DefaultOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(file, DefaultOrphanedKeyActionType.type, options);
      }

      public static DefaultOrphanedKeyActionType parse(URL u) throws XmlException, IOException {
         return (DefaultOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(u, DefaultOrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static DefaultOrphanedKeyActionType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DefaultOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(u, DefaultOrphanedKeyActionType.type, options);
      }

      public static DefaultOrphanedKeyActionType parse(InputStream is) throws XmlException, IOException {
         return (DefaultOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(is, DefaultOrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static DefaultOrphanedKeyActionType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DefaultOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(is, DefaultOrphanedKeyActionType.type, options);
      }

      public static DefaultOrphanedKeyActionType parse(Reader r) throws XmlException, IOException {
         return (DefaultOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(r, DefaultOrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static DefaultOrphanedKeyActionType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DefaultOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(r, DefaultOrphanedKeyActionType.type, options);
      }

      public static DefaultOrphanedKeyActionType parse(XMLStreamReader sr) throws XmlException {
         return (DefaultOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(sr, DefaultOrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static DefaultOrphanedKeyActionType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DefaultOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(sr, DefaultOrphanedKeyActionType.type, options);
      }

      public static DefaultOrphanedKeyActionType parse(Node node) throws XmlException {
         return (DefaultOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(node, DefaultOrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static DefaultOrphanedKeyActionType parse(Node node, XmlOptions options) throws XmlException {
         return (DefaultOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(node, DefaultOrphanedKeyActionType.type, options);
      }

      /** @deprecated */
      public static DefaultOrphanedKeyActionType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DefaultOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(xis, DefaultOrphanedKeyActionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DefaultOrphanedKeyActionType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DefaultOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(xis, DefaultOrphanedKeyActionType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultOrphanedKeyActionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultOrphanedKeyActionType.type, options);
      }

      private Factory() {
      }
   }
}
