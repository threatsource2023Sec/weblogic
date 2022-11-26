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

public interface ExceptionOrphanedKeyActionType extends OrphanedKeyActionType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ExceptionOrphanedKeyActionType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("exceptionorphanedkeyactiontypeb1e0type");

   public static final class Factory {
      public static ExceptionOrphanedKeyActionType newInstance() {
         return (ExceptionOrphanedKeyActionType)XmlBeans.getContextTypeLoader().newInstance(ExceptionOrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static ExceptionOrphanedKeyActionType newInstance(XmlOptions options) {
         return (ExceptionOrphanedKeyActionType)XmlBeans.getContextTypeLoader().newInstance(ExceptionOrphanedKeyActionType.type, options);
      }

      public static ExceptionOrphanedKeyActionType parse(String xmlAsString) throws XmlException {
         return (ExceptionOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ExceptionOrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static ExceptionOrphanedKeyActionType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ExceptionOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ExceptionOrphanedKeyActionType.type, options);
      }

      public static ExceptionOrphanedKeyActionType parse(File file) throws XmlException, IOException {
         return (ExceptionOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(file, ExceptionOrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static ExceptionOrphanedKeyActionType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ExceptionOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(file, ExceptionOrphanedKeyActionType.type, options);
      }

      public static ExceptionOrphanedKeyActionType parse(URL u) throws XmlException, IOException {
         return (ExceptionOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(u, ExceptionOrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static ExceptionOrphanedKeyActionType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ExceptionOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(u, ExceptionOrphanedKeyActionType.type, options);
      }

      public static ExceptionOrphanedKeyActionType parse(InputStream is) throws XmlException, IOException {
         return (ExceptionOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(is, ExceptionOrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static ExceptionOrphanedKeyActionType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ExceptionOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(is, ExceptionOrphanedKeyActionType.type, options);
      }

      public static ExceptionOrphanedKeyActionType parse(Reader r) throws XmlException, IOException {
         return (ExceptionOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(r, ExceptionOrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static ExceptionOrphanedKeyActionType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ExceptionOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(r, ExceptionOrphanedKeyActionType.type, options);
      }

      public static ExceptionOrphanedKeyActionType parse(XMLStreamReader sr) throws XmlException {
         return (ExceptionOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(sr, ExceptionOrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static ExceptionOrphanedKeyActionType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ExceptionOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(sr, ExceptionOrphanedKeyActionType.type, options);
      }

      public static ExceptionOrphanedKeyActionType parse(Node node) throws XmlException {
         return (ExceptionOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(node, ExceptionOrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static ExceptionOrphanedKeyActionType parse(Node node, XmlOptions options) throws XmlException {
         return (ExceptionOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(node, ExceptionOrphanedKeyActionType.type, options);
      }

      /** @deprecated */
      public static ExceptionOrphanedKeyActionType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ExceptionOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(xis, ExceptionOrphanedKeyActionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ExceptionOrphanedKeyActionType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ExceptionOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(xis, ExceptionOrphanedKeyActionType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ExceptionOrphanedKeyActionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ExceptionOrphanedKeyActionType.type, options);
      }

      private Factory() {
      }
   }
}
