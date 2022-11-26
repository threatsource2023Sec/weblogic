package com.oracle.xmlns.weblogic.persistenceConfiguration;

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

public interface OrphanedKeyActionType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(OrphanedKeyActionType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("orphanedkeyactiontypefae2type");

   public static final class Factory {
      public static OrphanedKeyActionType newInstance() {
         return (OrphanedKeyActionType)XmlBeans.getContextTypeLoader().newInstance(OrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static OrphanedKeyActionType newInstance(XmlOptions options) {
         return (OrphanedKeyActionType)XmlBeans.getContextTypeLoader().newInstance(OrphanedKeyActionType.type, options);
      }

      public static OrphanedKeyActionType parse(String xmlAsString) throws XmlException {
         return (OrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, OrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static OrphanedKeyActionType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (OrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, OrphanedKeyActionType.type, options);
      }

      public static OrphanedKeyActionType parse(File file) throws XmlException, IOException {
         return (OrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(file, OrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static OrphanedKeyActionType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (OrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(file, OrphanedKeyActionType.type, options);
      }

      public static OrphanedKeyActionType parse(URL u) throws XmlException, IOException {
         return (OrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(u, OrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static OrphanedKeyActionType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (OrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(u, OrphanedKeyActionType.type, options);
      }

      public static OrphanedKeyActionType parse(InputStream is) throws XmlException, IOException {
         return (OrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(is, OrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static OrphanedKeyActionType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (OrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(is, OrphanedKeyActionType.type, options);
      }

      public static OrphanedKeyActionType parse(Reader r) throws XmlException, IOException {
         return (OrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(r, OrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static OrphanedKeyActionType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (OrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(r, OrphanedKeyActionType.type, options);
      }

      public static OrphanedKeyActionType parse(XMLStreamReader sr) throws XmlException {
         return (OrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(sr, OrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static OrphanedKeyActionType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (OrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(sr, OrphanedKeyActionType.type, options);
      }

      public static OrphanedKeyActionType parse(Node node) throws XmlException {
         return (OrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(node, OrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static OrphanedKeyActionType parse(Node node, XmlOptions options) throws XmlException {
         return (OrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(node, OrphanedKeyActionType.type, options);
      }

      /** @deprecated */
      public static OrphanedKeyActionType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (OrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(xis, OrphanedKeyActionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static OrphanedKeyActionType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (OrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(xis, OrphanedKeyActionType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OrphanedKeyActionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OrphanedKeyActionType.type, options);
      }

      private Factory() {
      }
   }
}
