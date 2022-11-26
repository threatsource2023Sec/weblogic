package com.oracle.xmlns.weblogic.weblogicEjbJar;

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

public interface ConcurrencyStrategyType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConcurrencyStrategyType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("concurrencystrategytype4863type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ConcurrencyStrategyType newInstance() {
         return (ConcurrencyStrategyType)XmlBeans.getContextTypeLoader().newInstance(ConcurrencyStrategyType.type, (XmlOptions)null);
      }

      public static ConcurrencyStrategyType newInstance(XmlOptions options) {
         return (ConcurrencyStrategyType)XmlBeans.getContextTypeLoader().newInstance(ConcurrencyStrategyType.type, options);
      }

      public static ConcurrencyStrategyType parse(String xmlAsString) throws XmlException {
         return (ConcurrencyStrategyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConcurrencyStrategyType.type, (XmlOptions)null);
      }

      public static ConcurrencyStrategyType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ConcurrencyStrategyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConcurrencyStrategyType.type, options);
      }

      public static ConcurrencyStrategyType parse(File file) throws XmlException, IOException {
         return (ConcurrencyStrategyType)XmlBeans.getContextTypeLoader().parse(file, ConcurrencyStrategyType.type, (XmlOptions)null);
      }

      public static ConcurrencyStrategyType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConcurrencyStrategyType)XmlBeans.getContextTypeLoader().parse(file, ConcurrencyStrategyType.type, options);
      }

      public static ConcurrencyStrategyType parse(URL u) throws XmlException, IOException {
         return (ConcurrencyStrategyType)XmlBeans.getContextTypeLoader().parse(u, ConcurrencyStrategyType.type, (XmlOptions)null);
      }

      public static ConcurrencyStrategyType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConcurrencyStrategyType)XmlBeans.getContextTypeLoader().parse(u, ConcurrencyStrategyType.type, options);
      }

      public static ConcurrencyStrategyType parse(InputStream is) throws XmlException, IOException {
         return (ConcurrencyStrategyType)XmlBeans.getContextTypeLoader().parse(is, ConcurrencyStrategyType.type, (XmlOptions)null);
      }

      public static ConcurrencyStrategyType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConcurrencyStrategyType)XmlBeans.getContextTypeLoader().parse(is, ConcurrencyStrategyType.type, options);
      }

      public static ConcurrencyStrategyType parse(Reader r) throws XmlException, IOException {
         return (ConcurrencyStrategyType)XmlBeans.getContextTypeLoader().parse(r, ConcurrencyStrategyType.type, (XmlOptions)null);
      }

      public static ConcurrencyStrategyType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConcurrencyStrategyType)XmlBeans.getContextTypeLoader().parse(r, ConcurrencyStrategyType.type, options);
      }

      public static ConcurrencyStrategyType parse(XMLStreamReader sr) throws XmlException {
         return (ConcurrencyStrategyType)XmlBeans.getContextTypeLoader().parse(sr, ConcurrencyStrategyType.type, (XmlOptions)null);
      }

      public static ConcurrencyStrategyType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConcurrencyStrategyType)XmlBeans.getContextTypeLoader().parse(sr, ConcurrencyStrategyType.type, options);
      }

      public static ConcurrencyStrategyType parse(Node node) throws XmlException {
         return (ConcurrencyStrategyType)XmlBeans.getContextTypeLoader().parse(node, ConcurrencyStrategyType.type, (XmlOptions)null);
      }

      public static ConcurrencyStrategyType parse(Node node, XmlOptions options) throws XmlException {
         return (ConcurrencyStrategyType)XmlBeans.getContextTypeLoader().parse(node, ConcurrencyStrategyType.type, options);
      }

      /** @deprecated */
      public static ConcurrencyStrategyType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConcurrencyStrategyType)XmlBeans.getContextTypeLoader().parse(xis, ConcurrencyStrategyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConcurrencyStrategyType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConcurrencyStrategyType)XmlBeans.getContextTypeLoader().parse(xis, ConcurrencyStrategyType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConcurrencyStrategyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConcurrencyStrategyType.type, options);
      }

      private Factory() {
      }
   }
}
