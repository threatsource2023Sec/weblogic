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

public interface NoneOrphanedKeyActionType extends OrphanedKeyActionType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(NoneOrphanedKeyActionType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("noneorphanedkeyactiontype14e5type");

   public static final class Factory {
      public static NoneOrphanedKeyActionType newInstance() {
         return (NoneOrphanedKeyActionType)XmlBeans.getContextTypeLoader().newInstance(NoneOrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static NoneOrphanedKeyActionType newInstance(XmlOptions options) {
         return (NoneOrphanedKeyActionType)XmlBeans.getContextTypeLoader().newInstance(NoneOrphanedKeyActionType.type, options);
      }

      public static NoneOrphanedKeyActionType parse(String xmlAsString) throws XmlException {
         return (NoneOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, NoneOrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static NoneOrphanedKeyActionType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (NoneOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, NoneOrphanedKeyActionType.type, options);
      }

      public static NoneOrphanedKeyActionType parse(File file) throws XmlException, IOException {
         return (NoneOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(file, NoneOrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static NoneOrphanedKeyActionType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (NoneOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(file, NoneOrphanedKeyActionType.type, options);
      }

      public static NoneOrphanedKeyActionType parse(URL u) throws XmlException, IOException {
         return (NoneOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(u, NoneOrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static NoneOrphanedKeyActionType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (NoneOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(u, NoneOrphanedKeyActionType.type, options);
      }

      public static NoneOrphanedKeyActionType parse(InputStream is) throws XmlException, IOException {
         return (NoneOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(is, NoneOrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static NoneOrphanedKeyActionType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (NoneOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(is, NoneOrphanedKeyActionType.type, options);
      }

      public static NoneOrphanedKeyActionType parse(Reader r) throws XmlException, IOException {
         return (NoneOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(r, NoneOrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static NoneOrphanedKeyActionType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (NoneOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(r, NoneOrphanedKeyActionType.type, options);
      }

      public static NoneOrphanedKeyActionType parse(XMLStreamReader sr) throws XmlException {
         return (NoneOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(sr, NoneOrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static NoneOrphanedKeyActionType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (NoneOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(sr, NoneOrphanedKeyActionType.type, options);
      }

      public static NoneOrphanedKeyActionType parse(Node node) throws XmlException {
         return (NoneOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(node, NoneOrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static NoneOrphanedKeyActionType parse(Node node, XmlOptions options) throws XmlException {
         return (NoneOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(node, NoneOrphanedKeyActionType.type, options);
      }

      /** @deprecated */
      public static NoneOrphanedKeyActionType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (NoneOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(xis, NoneOrphanedKeyActionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static NoneOrphanedKeyActionType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (NoneOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(xis, NoneOrphanedKeyActionType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, NoneOrphanedKeyActionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, NoneOrphanedKeyActionType.type, options);
      }

      private Factory() {
      }
   }
}
