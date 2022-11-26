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

public interface Next extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Next.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("next3a32type");

   java.lang.String getOn();

   XmlString xgetOn();

   void setOn(java.lang.String var1);

   void xsetOn(XmlString var1);

   java.lang.String getTo();

   XmlString xgetTo();

   void setTo(java.lang.String var1);

   void xsetTo(XmlString var1);

   public static final class Factory {
      public static Next newInstance() {
         return (Next)XmlBeans.getContextTypeLoader().newInstance(Next.type, (XmlOptions)null);
      }

      public static Next newInstance(XmlOptions options) {
         return (Next)XmlBeans.getContextTypeLoader().newInstance(Next.type, options);
      }

      public static Next parse(java.lang.String xmlAsString) throws XmlException {
         return (Next)XmlBeans.getContextTypeLoader().parse(xmlAsString, Next.type, (XmlOptions)null);
      }

      public static Next parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (Next)XmlBeans.getContextTypeLoader().parse(xmlAsString, Next.type, options);
      }

      public static Next parse(File file) throws XmlException, IOException {
         return (Next)XmlBeans.getContextTypeLoader().parse(file, Next.type, (XmlOptions)null);
      }

      public static Next parse(File file, XmlOptions options) throws XmlException, IOException {
         return (Next)XmlBeans.getContextTypeLoader().parse(file, Next.type, options);
      }

      public static Next parse(URL u) throws XmlException, IOException {
         return (Next)XmlBeans.getContextTypeLoader().parse(u, Next.type, (XmlOptions)null);
      }

      public static Next parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (Next)XmlBeans.getContextTypeLoader().parse(u, Next.type, options);
      }

      public static Next parse(InputStream is) throws XmlException, IOException {
         return (Next)XmlBeans.getContextTypeLoader().parse(is, Next.type, (XmlOptions)null);
      }

      public static Next parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (Next)XmlBeans.getContextTypeLoader().parse(is, Next.type, options);
      }

      public static Next parse(Reader r) throws XmlException, IOException {
         return (Next)XmlBeans.getContextTypeLoader().parse(r, Next.type, (XmlOptions)null);
      }

      public static Next parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (Next)XmlBeans.getContextTypeLoader().parse(r, Next.type, options);
      }

      public static Next parse(XMLStreamReader sr) throws XmlException {
         return (Next)XmlBeans.getContextTypeLoader().parse(sr, Next.type, (XmlOptions)null);
      }

      public static Next parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (Next)XmlBeans.getContextTypeLoader().parse(sr, Next.type, options);
      }

      public static Next parse(Node node) throws XmlException {
         return (Next)XmlBeans.getContextTypeLoader().parse(node, Next.type, (XmlOptions)null);
      }

      public static Next parse(Node node, XmlOptions options) throws XmlException {
         return (Next)XmlBeans.getContextTypeLoader().parse(node, Next.type, options);
      }

      /** @deprecated */
      public static Next parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (Next)XmlBeans.getContextTypeLoader().parse(xis, Next.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static Next parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (Next)XmlBeans.getContextTypeLoader().parse(xis, Next.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Next.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Next.type, options);
      }

      private Factory() {
      }
   }
}
