package org.jcp.xmlns.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface Collector extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Collector.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("collector4e0atype");

   Properties getProperties();

   boolean isSetProperties();

   void setProperties(Properties var1);

   Properties addNewProperties();

   void unsetProperties();

   java.lang.String getRef();

   ArtifactRef xgetRef();

   void setRef(java.lang.String var1);

   void xsetRef(ArtifactRef var1);

   public static final class Factory {
      public static Collector newInstance() {
         return (Collector)XmlBeans.getContextTypeLoader().newInstance(Collector.type, (XmlOptions)null);
      }

      public static Collector newInstance(XmlOptions options) {
         return (Collector)XmlBeans.getContextTypeLoader().newInstance(Collector.type, options);
      }

      public static Collector parse(java.lang.String xmlAsString) throws XmlException {
         return (Collector)XmlBeans.getContextTypeLoader().parse(xmlAsString, Collector.type, (XmlOptions)null);
      }

      public static Collector parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (Collector)XmlBeans.getContextTypeLoader().parse(xmlAsString, Collector.type, options);
      }

      public static Collector parse(File file) throws XmlException, IOException {
         return (Collector)XmlBeans.getContextTypeLoader().parse(file, Collector.type, (XmlOptions)null);
      }

      public static Collector parse(File file, XmlOptions options) throws XmlException, IOException {
         return (Collector)XmlBeans.getContextTypeLoader().parse(file, Collector.type, options);
      }

      public static Collector parse(URL u) throws XmlException, IOException {
         return (Collector)XmlBeans.getContextTypeLoader().parse(u, Collector.type, (XmlOptions)null);
      }

      public static Collector parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (Collector)XmlBeans.getContextTypeLoader().parse(u, Collector.type, options);
      }

      public static Collector parse(InputStream is) throws XmlException, IOException {
         return (Collector)XmlBeans.getContextTypeLoader().parse(is, Collector.type, (XmlOptions)null);
      }

      public static Collector parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (Collector)XmlBeans.getContextTypeLoader().parse(is, Collector.type, options);
      }

      public static Collector parse(Reader r) throws XmlException, IOException {
         return (Collector)XmlBeans.getContextTypeLoader().parse(r, Collector.type, (XmlOptions)null);
      }

      public static Collector parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (Collector)XmlBeans.getContextTypeLoader().parse(r, Collector.type, options);
      }

      public static Collector parse(XMLStreamReader sr) throws XmlException {
         return (Collector)XmlBeans.getContextTypeLoader().parse(sr, Collector.type, (XmlOptions)null);
      }

      public static Collector parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (Collector)XmlBeans.getContextTypeLoader().parse(sr, Collector.type, options);
      }

      public static Collector parse(Node node) throws XmlException {
         return (Collector)XmlBeans.getContextTypeLoader().parse(node, Collector.type, (XmlOptions)null);
      }

      public static Collector parse(Node node, XmlOptions options) throws XmlException {
         return (Collector)XmlBeans.getContextTypeLoader().parse(node, Collector.type, options);
      }

      /** @deprecated */
      public static Collector parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (Collector)XmlBeans.getContextTypeLoader().parse(xis, Collector.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static Collector parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (Collector)XmlBeans.getContextTypeLoader().parse(xis, Collector.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Collector.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Collector.type, options);
      }

      private Factory() {
      }
   }
}
