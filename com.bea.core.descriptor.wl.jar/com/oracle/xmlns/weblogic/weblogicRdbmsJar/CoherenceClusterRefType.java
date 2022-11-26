package com.oracle.xmlns.weblogic.weblogicRdbmsJar;

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

public interface CoherenceClusterRefType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CoherenceClusterRefType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("coherenceclusterreftype23e4type");

   String getCoherenceClusterName();

   XmlString xgetCoherenceClusterName();

   boolean isSetCoherenceClusterName();

   void setCoherenceClusterName(String var1);

   void xsetCoherenceClusterName(XmlString var1);

   void unsetCoherenceClusterName();

   public static final class Factory {
      public static CoherenceClusterRefType newInstance() {
         return (CoherenceClusterRefType)XmlBeans.getContextTypeLoader().newInstance(CoherenceClusterRefType.type, (XmlOptions)null);
      }

      public static CoherenceClusterRefType newInstance(XmlOptions options) {
         return (CoherenceClusterRefType)XmlBeans.getContextTypeLoader().newInstance(CoherenceClusterRefType.type, options);
      }

      public static CoherenceClusterRefType parse(String xmlAsString) throws XmlException {
         return (CoherenceClusterRefType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CoherenceClusterRefType.type, (XmlOptions)null);
      }

      public static CoherenceClusterRefType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CoherenceClusterRefType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CoherenceClusterRefType.type, options);
      }

      public static CoherenceClusterRefType parse(File file) throws XmlException, IOException {
         return (CoherenceClusterRefType)XmlBeans.getContextTypeLoader().parse(file, CoherenceClusterRefType.type, (XmlOptions)null);
      }

      public static CoherenceClusterRefType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CoherenceClusterRefType)XmlBeans.getContextTypeLoader().parse(file, CoherenceClusterRefType.type, options);
      }

      public static CoherenceClusterRefType parse(URL u) throws XmlException, IOException {
         return (CoherenceClusterRefType)XmlBeans.getContextTypeLoader().parse(u, CoherenceClusterRefType.type, (XmlOptions)null);
      }

      public static CoherenceClusterRefType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CoherenceClusterRefType)XmlBeans.getContextTypeLoader().parse(u, CoherenceClusterRefType.type, options);
      }

      public static CoherenceClusterRefType parse(InputStream is) throws XmlException, IOException {
         return (CoherenceClusterRefType)XmlBeans.getContextTypeLoader().parse(is, CoherenceClusterRefType.type, (XmlOptions)null);
      }

      public static CoherenceClusterRefType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CoherenceClusterRefType)XmlBeans.getContextTypeLoader().parse(is, CoherenceClusterRefType.type, options);
      }

      public static CoherenceClusterRefType parse(Reader r) throws XmlException, IOException {
         return (CoherenceClusterRefType)XmlBeans.getContextTypeLoader().parse(r, CoherenceClusterRefType.type, (XmlOptions)null);
      }

      public static CoherenceClusterRefType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CoherenceClusterRefType)XmlBeans.getContextTypeLoader().parse(r, CoherenceClusterRefType.type, options);
      }

      public static CoherenceClusterRefType parse(XMLStreamReader sr) throws XmlException {
         return (CoherenceClusterRefType)XmlBeans.getContextTypeLoader().parse(sr, CoherenceClusterRefType.type, (XmlOptions)null);
      }

      public static CoherenceClusterRefType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CoherenceClusterRefType)XmlBeans.getContextTypeLoader().parse(sr, CoherenceClusterRefType.type, options);
      }

      public static CoherenceClusterRefType parse(Node node) throws XmlException {
         return (CoherenceClusterRefType)XmlBeans.getContextTypeLoader().parse(node, CoherenceClusterRefType.type, (XmlOptions)null);
      }

      public static CoherenceClusterRefType parse(Node node, XmlOptions options) throws XmlException {
         return (CoherenceClusterRefType)XmlBeans.getContextTypeLoader().parse(node, CoherenceClusterRefType.type, options);
      }

      /** @deprecated */
      public static CoherenceClusterRefType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CoherenceClusterRefType)XmlBeans.getContextTypeLoader().parse(xis, CoherenceClusterRefType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CoherenceClusterRefType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CoherenceClusterRefType)XmlBeans.getContextTypeLoader().parse(xis, CoherenceClusterRefType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CoherenceClusterRefType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CoherenceClusterRefType.type, options);
      }

      private Factory() {
      }
   }
}
