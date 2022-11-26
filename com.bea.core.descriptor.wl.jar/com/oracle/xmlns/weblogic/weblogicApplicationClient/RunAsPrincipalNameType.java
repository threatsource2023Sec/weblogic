package com.oracle.xmlns.weblogic.weblogicApplicationClient;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface RunAsPrincipalNameType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(RunAsPrincipalNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("runasprincipalnametype7acctype");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static RunAsPrincipalNameType newInstance() {
         return (RunAsPrincipalNameType)XmlBeans.getContextTypeLoader().newInstance(RunAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static RunAsPrincipalNameType newInstance(XmlOptions options) {
         return (RunAsPrincipalNameType)XmlBeans.getContextTypeLoader().newInstance(RunAsPrincipalNameType.type, options);
      }

      public static RunAsPrincipalNameType parse(String xmlAsString) throws XmlException {
         return (RunAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, RunAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static RunAsPrincipalNameType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (RunAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, RunAsPrincipalNameType.type, options);
      }

      public static RunAsPrincipalNameType parse(File file) throws XmlException, IOException {
         return (RunAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(file, RunAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static RunAsPrincipalNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (RunAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(file, RunAsPrincipalNameType.type, options);
      }

      public static RunAsPrincipalNameType parse(URL u) throws XmlException, IOException {
         return (RunAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(u, RunAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static RunAsPrincipalNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (RunAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(u, RunAsPrincipalNameType.type, options);
      }

      public static RunAsPrincipalNameType parse(InputStream is) throws XmlException, IOException {
         return (RunAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(is, RunAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static RunAsPrincipalNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (RunAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(is, RunAsPrincipalNameType.type, options);
      }

      public static RunAsPrincipalNameType parse(Reader r) throws XmlException, IOException {
         return (RunAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(r, RunAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static RunAsPrincipalNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (RunAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(r, RunAsPrincipalNameType.type, options);
      }

      public static RunAsPrincipalNameType parse(XMLStreamReader sr) throws XmlException {
         return (RunAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(sr, RunAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static RunAsPrincipalNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (RunAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(sr, RunAsPrincipalNameType.type, options);
      }

      public static RunAsPrincipalNameType parse(Node node) throws XmlException {
         return (RunAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(node, RunAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static RunAsPrincipalNameType parse(Node node, XmlOptions options) throws XmlException {
         return (RunAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(node, RunAsPrincipalNameType.type, options);
      }

      /** @deprecated */
      public static RunAsPrincipalNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (RunAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(xis, RunAsPrincipalNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static RunAsPrincipalNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (RunAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(xis, RunAsPrincipalNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RunAsPrincipalNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RunAsPrincipalNameType.type, options);
      }

      private Factory() {
      }
   }
}
