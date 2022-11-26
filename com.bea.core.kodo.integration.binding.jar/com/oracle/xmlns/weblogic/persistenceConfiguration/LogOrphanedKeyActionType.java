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

public interface LogOrphanedKeyActionType extends OrphanedKeyActionType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(LogOrphanedKeyActionType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("logorphanedkeyactiontype938btype");

   String getChannel();

   XmlString xgetChannel();

   boolean isNilChannel();

   boolean isSetChannel();

   void setChannel(String var1);

   void xsetChannel(XmlString var1);

   void setNilChannel();

   void unsetChannel();

   String getLevel();

   XmlString xgetLevel();

   boolean isNilLevel();

   boolean isSetLevel();

   void setLevel(String var1);

   void xsetLevel(XmlString var1);

   void setNilLevel();

   void unsetLevel();

   public static final class Factory {
      public static LogOrphanedKeyActionType newInstance() {
         return (LogOrphanedKeyActionType)XmlBeans.getContextTypeLoader().newInstance(LogOrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static LogOrphanedKeyActionType newInstance(XmlOptions options) {
         return (LogOrphanedKeyActionType)XmlBeans.getContextTypeLoader().newInstance(LogOrphanedKeyActionType.type, options);
      }

      public static LogOrphanedKeyActionType parse(String xmlAsString) throws XmlException {
         return (LogOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LogOrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static LogOrphanedKeyActionType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (LogOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LogOrphanedKeyActionType.type, options);
      }

      public static LogOrphanedKeyActionType parse(File file) throws XmlException, IOException {
         return (LogOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(file, LogOrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static LogOrphanedKeyActionType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (LogOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(file, LogOrphanedKeyActionType.type, options);
      }

      public static LogOrphanedKeyActionType parse(URL u) throws XmlException, IOException {
         return (LogOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(u, LogOrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static LogOrphanedKeyActionType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (LogOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(u, LogOrphanedKeyActionType.type, options);
      }

      public static LogOrphanedKeyActionType parse(InputStream is) throws XmlException, IOException {
         return (LogOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(is, LogOrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static LogOrphanedKeyActionType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (LogOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(is, LogOrphanedKeyActionType.type, options);
      }

      public static LogOrphanedKeyActionType parse(Reader r) throws XmlException, IOException {
         return (LogOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(r, LogOrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static LogOrphanedKeyActionType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (LogOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(r, LogOrphanedKeyActionType.type, options);
      }

      public static LogOrphanedKeyActionType parse(XMLStreamReader sr) throws XmlException {
         return (LogOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(sr, LogOrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static LogOrphanedKeyActionType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (LogOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(sr, LogOrphanedKeyActionType.type, options);
      }

      public static LogOrphanedKeyActionType parse(Node node) throws XmlException {
         return (LogOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(node, LogOrphanedKeyActionType.type, (XmlOptions)null);
      }

      public static LogOrphanedKeyActionType parse(Node node, XmlOptions options) throws XmlException {
         return (LogOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(node, LogOrphanedKeyActionType.type, options);
      }

      /** @deprecated */
      public static LogOrphanedKeyActionType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (LogOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(xis, LogOrphanedKeyActionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static LogOrphanedKeyActionType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (LogOrphanedKeyActionType)XmlBeans.getContextTypeLoader().parse(xis, LogOrphanedKeyActionType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LogOrphanedKeyActionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LogOrphanedKeyActionType.type, options);
      }

      private Factory() {
      }
   }
}
