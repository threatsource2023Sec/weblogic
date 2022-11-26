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

public interface TimerImplementationType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TimerImplementationType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("timerimplementationtype1f70type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static TimerImplementationType newInstance() {
         return (TimerImplementationType)XmlBeans.getContextTypeLoader().newInstance(TimerImplementationType.type, (XmlOptions)null);
      }

      public static TimerImplementationType newInstance(XmlOptions options) {
         return (TimerImplementationType)XmlBeans.getContextTypeLoader().newInstance(TimerImplementationType.type, options);
      }

      public static TimerImplementationType parse(String xmlAsString) throws XmlException {
         return (TimerImplementationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TimerImplementationType.type, (XmlOptions)null);
      }

      public static TimerImplementationType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TimerImplementationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TimerImplementationType.type, options);
      }

      public static TimerImplementationType parse(File file) throws XmlException, IOException {
         return (TimerImplementationType)XmlBeans.getContextTypeLoader().parse(file, TimerImplementationType.type, (XmlOptions)null);
      }

      public static TimerImplementationType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TimerImplementationType)XmlBeans.getContextTypeLoader().parse(file, TimerImplementationType.type, options);
      }

      public static TimerImplementationType parse(URL u) throws XmlException, IOException {
         return (TimerImplementationType)XmlBeans.getContextTypeLoader().parse(u, TimerImplementationType.type, (XmlOptions)null);
      }

      public static TimerImplementationType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TimerImplementationType)XmlBeans.getContextTypeLoader().parse(u, TimerImplementationType.type, options);
      }

      public static TimerImplementationType parse(InputStream is) throws XmlException, IOException {
         return (TimerImplementationType)XmlBeans.getContextTypeLoader().parse(is, TimerImplementationType.type, (XmlOptions)null);
      }

      public static TimerImplementationType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TimerImplementationType)XmlBeans.getContextTypeLoader().parse(is, TimerImplementationType.type, options);
      }

      public static TimerImplementationType parse(Reader r) throws XmlException, IOException {
         return (TimerImplementationType)XmlBeans.getContextTypeLoader().parse(r, TimerImplementationType.type, (XmlOptions)null);
      }

      public static TimerImplementationType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TimerImplementationType)XmlBeans.getContextTypeLoader().parse(r, TimerImplementationType.type, options);
      }

      public static TimerImplementationType parse(XMLStreamReader sr) throws XmlException {
         return (TimerImplementationType)XmlBeans.getContextTypeLoader().parse(sr, TimerImplementationType.type, (XmlOptions)null);
      }

      public static TimerImplementationType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TimerImplementationType)XmlBeans.getContextTypeLoader().parse(sr, TimerImplementationType.type, options);
      }

      public static TimerImplementationType parse(Node node) throws XmlException {
         return (TimerImplementationType)XmlBeans.getContextTypeLoader().parse(node, TimerImplementationType.type, (XmlOptions)null);
      }

      public static TimerImplementationType parse(Node node, XmlOptions options) throws XmlException {
         return (TimerImplementationType)XmlBeans.getContextTypeLoader().parse(node, TimerImplementationType.type, options);
      }

      /** @deprecated */
      public static TimerImplementationType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TimerImplementationType)XmlBeans.getContextTypeLoader().parse(xis, TimerImplementationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TimerImplementationType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TimerImplementationType)XmlBeans.getContextTypeLoader().parse(xis, TimerImplementationType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TimerImplementationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TimerImplementationType.type, options);
      }

      private Factory() {
      }
   }
}
