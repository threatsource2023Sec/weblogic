package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlObject;
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

public interface ExportProfilingType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ExportProfilingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("exportprofilingtypeb94dtype");

   int getIntervalMillis();

   XmlInt xgetIntervalMillis();

   boolean isSetIntervalMillis();

   void setIntervalMillis(int var1);

   void xsetIntervalMillis(XmlInt var1);

   void unsetIntervalMillis();

   String getBaseName();

   XmlString xgetBaseName();

   boolean isSetBaseName();

   void setBaseName(String var1);

   void xsetBaseName(XmlString var1);

   void unsetBaseName();

   public static final class Factory {
      public static ExportProfilingType newInstance() {
         return (ExportProfilingType)XmlBeans.getContextTypeLoader().newInstance(ExportProfilingType.type, (XmlOptions)null);
      }

      public static ExportProfilingType newInstance(XmlOptions options) {
         return (ExportProfilingType)XmlBeans.getContextTypeLoader().newInstance(ExportProfilingType.type, options);
      }

      public static ExportProfilingType parse(String xmlAsString) throws XmlException {
         return (ExportProfilingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ExportProfilingType.type, (XmlOptions)null);
      }

      public static ExportProfilingType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ExportProfilingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ExportProfilingType.type, options);
      }

      public static ExportProfilingType parse(File file) throws XmlException, IOException {
         return (ExportProfilingType)XmlBeans.getContextTypeLoader().parse(file, ExportProfilingType.type, (XmlOptions)null);
      }

      public static ExportProfilingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ExportProfilingType)XmlBeans.getContextTypeLoader().parse(file, ExportProfilingType.type, options);
      }

      public static ExportProfilingType parse(URL u) throws XmlException, IOException {
         return (ExportProfilingType)XmlBeans.getContextTypeLoader().parse(u, ExportProfilingType.type, (XmlOptions)null);
      }

      public static ExportProfilingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ExportProfilingType)XmlBeans.getContextTypeLoader().parse(u, ExportProfilingType.type, options);
      }

      public static ExportProfilingType parse(InputStream is) throws XmlException, IOException {
         return (ExportProfilingType)XmlBeans.getContextTypeLoader().parse(is, ExportProfilingType.type, (XmlOptions)null);
      }

      public static ExportProfilingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ExportProfilingType)XmlBeans.getContextTypeLoader().parse(is, ExportProfilingType.type, options);
      }

      public static ExportProfilingType parse(Reader r) throws XmlException, IOException {
         return (ExportProfilingType)XmlBeans.getContextTypeLoader().parse(r, ExportProfilingType.type, (XmlOptions)null);
      }

      public static ExportProfilingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ExportProfilingType)XmlBeans.getContextTypeLoader().parse(r, ExportProfilingType.type, options);
      }

      public static ExportProfilingType parse(XMLStreamReader sr) throws XmlException {
         return (ExportProfilingType)XmlBeans.getContextTypeLoader().parse(sr, ExportProfilingType.type, (XmlOptions)null);
      }

      public static ExportProfilingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ExportProfilingType)XmlBeans.getContextTypeLoader().parse(sr, ExportProfilingType.type, options);
      }

      public static ExportProfilingType parse(Node node) throws XmlException {
         return (ExportProfilingType)XmlBeans.getContextTypeLoader().parse(node, ExportProfilingType.type, (XmlOptions)null);
      }

      public static ExportProfilingType parse(Node node, XmlOptions options) throws XmlException {
         return (ExportProfilingType)XmlBeans.getContextTypeLoader().parse(node, ExportProfilingType.type, options);
      }

      /** @deprecated */
      public static ExportProfilingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ExportProfilingType)XmlBeans.getContextTypeLoader().parse(xis, ExportProfilingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ExportProfilingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ExportProfilingType)XmlBeans.getContextTypeLoader().parse(xis, ExportProfilingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ExportProfilingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ExportProfilingType.type, options);
      }

      private Factory() {
      }
   }
}
