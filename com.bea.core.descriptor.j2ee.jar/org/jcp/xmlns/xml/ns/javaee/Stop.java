package org.jcp.xmlns.xml.ns.javaee;

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

public interface Stop extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Stop.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("stopeac3type");

   java.lang.String getOn();

   XmlString xgetOn();

   void setOn(java.lang.String var1);

   void xsetOn(XmlString var1);

   java.lang.String getExitStatus();

   XmlString xgetExitStatus();

   boolean isSetExitStatus();

   void setExitStatus(java.lang.String var1);

   void xsetExitStatus(XmlString var1);

   void unsetExitStatus();

   java.lang.String getRestart();

   XmlString xgetRestart();

   boolean isSetRestart();

   void setRestart(java.lang.String var1);

   void xsetRestart(XmlString var1);

   void unsetRestart();

   public static final class Factory {
      public static Stop newInstance() {
         return (Stop)XmlBeans.getContextTypeLoader().newInstance(Stop.type, (XmlOptions)null);
      }

      public static Stop newInstance(XmlOptions options) {
         return (Stop)XmlBeans.getContextTypeLoader().newInstance(Stop.type, options);
      }

      public static Stop parse(java.lang.String xmlAsString) throws XmlException {
         return (Stop)XmlBeans.getContextTypeLoader().parse(xmlAsString, Stop.type, (XmlOptions)null);
      }

      public static Stop parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (Stop)XmlBeans.getContextTypeLoader().parse(xmlAsString, Stop.type, options);
      }

      public static Stop parse(File file) throws XmlException, IOException {
         return (Stop)XmlBeans.getContextTypeLoader().parse(file, Stop.type, (XmlOptions)null);
      }

      public static Stop parse(File file, XmlOptions options) throws XmlException, IOException {
         return (Stop)XmlBeans.getContextTypeLoader().parse(file, Stop.type, options);
      }

      public static Stop parse(URL u) throws XmlException, IOException {
         return (Stop)XmlBeans.getContextTypeLoader().parse(u, Stop.type, (XmlOptions)null);
      }

      public static Stop parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (Stop)XmlBeans.getContextTypeLoader().parse(u, Stop.type, options);
      }

      public static Stop parse(InputStream is) throws XmlException, IOException {
         return (Stop)XmlBeans.getContextTypeLoader().parse(is, Stop.type, (XmlOptions)null);
      }

      public static Stop parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (Stop)XmlBeans.getContextTypeLoader().parse(is, Stop.type, options);
      }

      public static Stop parse(Reader r) throws XmlException, IOException {
         return (Stop)XmlBeans.getContextTypeLoader().parse(r, Stop.type, (XmlOptions)null);
      }

      public static Stop parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (Stop)XmlBeans.getContextTypeLoader().parse(r, Stop.type, options);
      }

      public static Stop parse(XMLStreamReader sr) throws XmlException {
         return (Stop)XmlBeans.getContextTypeLoader().parse(sr, Stop.type, (XmlOptions)null);
      }

      public static Stop parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (Stop)XmlBeans.getContextTypeLoader().parse(sr, Stop.type, options);
      }

      public static Stop parse(Node node) throws XmlException {
         return (Stop)XmlBeans.getContextTypeLoader().parse(node, Stop.type, (XmlOptions)null);
      }

      public static Stop parse(Node node, XmlOptions options) throws XmlException {
         return (Stop)XmlBeans.getContextTypeLoader().parse(node, Stop.type, options);
      }

      /** @deprecated */
      public static Stop parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (Stop)XmlBeans.getContextTypeLoader().parse(xis, Stop.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static Stop parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (Stop)XmlBeans.getContextTypeLoader().parse(xis, Stop.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Stop.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Stop.type, options);
      }

      private Factory() {
      }
   }
}
