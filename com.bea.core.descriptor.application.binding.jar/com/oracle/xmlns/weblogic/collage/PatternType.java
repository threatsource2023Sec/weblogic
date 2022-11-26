package com.oracle.xmlns.weblogic.collage;

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

public interface PatternType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PatternType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("patterntype1127type");

   String getRefid();

   XmlString xgetRefid();

   void setRefid(String var1);

   void xsetRefid(XmlString var1);

   public static final class Factory {
      public static PatternType newInstance() {
         return (PatternType)XmlBeans.getContextTypeLoader().newInstance(PatternType.type, (XmlOptions)null);
      }

      public static PatternType newInstance(XmlOptions options) {
         return (PatternType)XmlBeans.getContextTypeLoader().newInstance(PatternType.type, options);
      }

      public static PatternType parse(String xmlAsString) throws XmlException {
         return (PatternType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PatternType.type, (XmlOptions)null);
      }

      public static PatternType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (PatternType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PatternType.type, options);
      }

      public static PatternType parse(File file) throws XmlException, IOException {
         return (PatternType)XmlBeans.getContextTypeLoader().parse(file, PatternType.type, (XmlOptions)null);
      }

      public static PatternType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PatternType)XmlBeans.getContextTypeLoader().parse(file, PatternType.type, options);
      }

      public static PatternType parse(URL u) throws XmlException, IOException {
         return (PatternType)XmlBeans.getContextTypeLoader().parse(u, PatternType.type, (XmlOptions)null);
      }

      public static PatternType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PatternType)XmlBeans.getContextTypeLoader().parse(u, PatternType.type, options);
      }

      public static PatternType parse(InputStream is) throws XmlException, IOException {
         return (PatternType)XmlBeans.getContextTypeLoader().parse(is, PatternType.type, (XmlOptions)null);
      }

      public static PatternType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PatternType)XmlBeans.getContextTypeLoader().parse(is, PatternType.type, options);
      }

      public static PatternType parse(Reader r) throws XmlException, IOException {
         return (PatternType)XmlBeans.getContextTypeLoader().parse(r, PatternType.type, (XmlOptions)null);
      }

      public static PatternType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PatternType)XmlBeans.getContextTypeLoader().parse(r, PatternType.type, options);
      }

      public static PatternType parse(XMLStreamReader sr) throws XmlException {
         return (PatternType)XmlBeans.getContextTypeLoader().parse(sr, PatternType.type, (XmlOptions)null);
      }

      public static PatternType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PatternType)XmlBeans.getContextTypeLoader().parse(sr, PatternType.type, options);
      }

      public static PatternType parse(Node node) throws XmlException {
         return (PatternType)XmlBeans.getContextTypeLoader().parse(node, PatternType.type, (XmlOptions)null);
      }

      public static PatternType parse(Node node, XmlOptions options) throws XmlException {
         return (PatternType)XmlBeans.getContextTypeLoader().parse(node, PatternType.type, options);
      }

      /** @deprecated */
      public static PatternType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PatternType)XmlBeans.getContextTypeLoader().parse(xis, PatternType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PatternType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PatternType)XmlBeans.getContextTypeLoader().parse(xis, PatternType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PatternType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PatternType.type, options);
      }

      private Factory() {
      }
   }
}
