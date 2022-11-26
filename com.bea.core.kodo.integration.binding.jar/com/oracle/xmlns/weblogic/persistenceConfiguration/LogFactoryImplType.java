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

public interface LogFactoryImplType extends LogType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(LogFactoryImplType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("logfactoryimpltype2dcetype");

   String getDiagnosticContext();

   XmlString xgetDiagnosticContext();

   boolean isNilDiagnosticContext();

   boolean isSetDiagnosticContext();

   void setDiagnosticContext(String var1);

   void xsetDiagnosticContext(XmlString var1);

   void setNilDiagnosticContext();

   void unsetDiagnosticContext();

   String getDefaultLevel();

   XmlString xgetDefaultLevel();

   boolean isNilDefaultLevel();

   boolean isSetDefaultLevel();

   void setDefaultLevel(String var1);

   void xsetDefaultLevel(XmlString var1);

   void setNilDefaultLevel();

   void unsetDefaultLevel();

   String getFile();

   XmlString xgetFile();

   boolean isNilFile();

   boolean isSetFile();

   void setFile(String var1);

   void xsetFile(XmlString var1);

   void setNilFile();

   void unsetFile();

   public static final class Factory {
      public static LogFactoryImplType newInstance() {
         return (LogFactoryImplType)XmlBeans.getContextTypeLoader().newInstance(LogFactoryImplType.type, (XmlOptions)null);
      }

      public static LogFactoryImplType newInstance(XmlOptions options) {
         return (LogFactoryImplType)XmlBeans.getContextTypeLoader().newInstance(LogFactoryImplType.type, options);
      }

      public static LogFactoryImplType parse(String xmlAsString) throws XmlException {
         return (LogFactoryImplType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LogFactoryImplType.type, (XmlOptions)null);
      }

      public static LogFactoryImplType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (LogFactoryImplType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LogFactoryImplType.type, options);
      }

      public static LogFactoryImplType parse(File file) throws XmlException, IOException {
         return (LogFactoryImplType)XmlBeans.getContextTypeLoader().parse(file, LogFactoryImplType.type, (XmlOptions)null);
      }

      public static LogFactoryImplType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (LogFactoryImplType)XmlBeans.getContextTypeLoader().parse(file, LogFactoryImplType.type, options);
      }

      public static LogFactoryImplType parse(URL u) throws XmlException, IOException {
         return (LogFactoryImplType)XmlBeans.getContextTypeLoader().parse(u, LogFactoryImplType.type, (XmlOptions)null);
      }

      public static LogFactoryImplType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (LogFactoryImplType)XmlBeans.getContextTypeLoader().parse(u, LogFactoryImplType.type, options);
      }

      public static LogFactoryImplType parse(InputStream is) throws XmlException, IOException {
         return (LogFactoryImplType)XmlBeans.getContextTypeLoader().parse(is, LogFactoryImplType.type, (XmlOptions)null);
      }

      public static LogFactoryImplType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (LogFactoryImplType)XmlBeans.getContextTypeLoader().parse(is, LogFactoryImplType.type, options);
      }

      public static LogFactoryImplType parse(Reader r) throws XmlException, IOException {
         return (LogFactoryImplType)XmlBeans.getContextTypeLoader().parse(r, LogFactoryImplType.type, (XmlOptions)null);
      }

      public static LogFactoryImplType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (LogFactoryImplType)XmlBeans.getContextTypeLoader().parse(r, LogFactoryImplType.type, options);
      }

      public static LogFactoryImplType parse(XMLStreamReader sr) throws XmlException {
         return (LogFactoryImplType)XmlBeans.getContextTypeLoader().parse(sr, LogFactoryImplType.type, (XmlOptions)null);
      }

      public static LogFactoryImplType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (LogFactoryImplType)XmlBeans.getContextTypeLoader().parse(sr, LogFactoryImplType.type, options);
      }

      public static LogFactoryImplType parse(Node node) throws XmlException {
         return (LogFactoryImplType)XmlBeans.getContextTypeLoader().parse(node, LogFactoryImplType.type, (XmlOptions)null);
      }

      public static LogFactoryImplType parse(Node node, XmlOptions options) throws XmlException {
         return (LogFactoryImplType)XmlBeans.getContextTypeLoader().parse(node, LogFactoryImplType.type, options);
      }

      /** @deprecated */
      public static LogFactoryImplType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (LogFactoryImplType)XmlBeans.getContextTypeLoader().parse(xis, LogFactoryImplType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static LogFactoryImplType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (LogFactoryImplType)XmlBeans.getContextTypeLoader().parse(xis, LogFactoryImplType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LogFactoryImplType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LogFactoryImplType.type, options);
      }

      private Factory() {
      }
   }
}
