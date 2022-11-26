package com.oracle.xmlns.weblogic.weblogicApplication;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface StartupType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(StartupType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("startuptypea726type");

   String getStartupClass();

   XmlString xgetStartupClass();

   void setStartupClass(String var1);

   void xsetStartupClass(XmlString var1);

   String getStartupUri();

   XmlString xgetStartupUri();

   boolean isSetStartupUri();

   void setStartupUri(String var1);

   void xsetStartupUri(XmlString var1);

   void unsetStartupUri();

   public static final class Factory {
      public static StartupType newInstance() {
         return (StartupType)XmlBeans.getContextTypeLoader().newInstance(StartupType.type, (XmlOptions)null);
      }

      public static StartupType newInstance(XmlOptions options) {
         return (StartupType)XmlBeans.getContextTypeLoader().newInstance(StartupType.type, options);
      }

      public static StartupType parse(String xmlAsString) throws XmlException {
         return (StartupType)XmlBeans.getContextTypeLoader().parse(xmlAsString, StartupType.type, (XmlOptions)null);
      }

      public static StartupType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (StartupType)XmlBeans.getContextTypeLoader().parse(xmlAsString, StartupType.type, options);
      }

      public static StartupType parse(File file) throws XmlException, IOException {
         return (StartupType)XmlBeans.getContextTypeLoader().parse(file, StartupType.type, (XmlOptions)null);
      }

      public static StartupType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (StartupType)XmlBeans.getContextTypeLoader().parse(file, StartupType.type, options);
      }

      public static StartupType parse(URL u) throws XmlException, IOException {
         return (StartupType)XmlBeans.getContextTypeLoader().parse(u, StartupType.type, (XmlOptions)null);
      }

      public static StartupType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (StartupType)XmlBeans.getContextTypeLoader().parse(u, StartupType.type, options);
      }

      public static StartupType parse(InputStream is) throws XmlException, IOException {
         return (StartupType)XmlBeans.getContextTypeLoader().parse(is, StartupType.type, (XmlOptions)null);
      }

      public static StartupType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (StartupType)XmlBeans.getContextTypeLoader().parse(is, StartupType.type, options);
      }

      public static StartupType parse(Reader r) throws XmlException, IOException {
         return (StartupType)XmlBeans.getContextTypeLoader().parse(r, StartupType.type, (XmlOptions)null);
      }

      public static StartupType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (StartupType)XmlBeans.getContextTypeLoader().parse(r, StartupType.type, options);
      }

      public static StartupType parse(XMLStreamReader sr) throws XmlException {
         return (StartupType)XmlBeans.getContextTypeLoader().parse(sr, StartupType.type, (XmlOptions)null);
      }

      public static StartupType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (StartupType)XmlBeans.getContextTypeLoader().parse(sr, StartupType.type, options);
      }

      public static StartupType parse(Node node) throws XmlException {
         return (StartupType)XmlBeans.getContextTypeLoader().parse(node, StartupType.type, (XmlOptions)null);
      }

      public static StartupType parse(Node node, XmlOptions options) throws XmlException {
         return (StartupType)XmlBeans.getContextTypeLoader().parse(node, StartupType.type, options);
      }

      /** @deprecated */
      public static StartupType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (StartupType)XmlBeans.getContextTypeLoader().parse(xis, StartupType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static StartupType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (StartupType)XmlBeans.getContextTypeLoader().parse(xis, StartupType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, StartupType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, StartupType.type, options);
      }

      private Factory() {
      }
   }
}
