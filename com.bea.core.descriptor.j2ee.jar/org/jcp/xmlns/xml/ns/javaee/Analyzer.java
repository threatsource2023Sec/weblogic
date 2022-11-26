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

public interface Analyzer extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Analyzer.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("analyzerc01ftype");

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
      public static Analyzer newInstance() {
         return (Analyzer)XmlBeans.getContextTypeLoader().newInstance(Analyzer.type, (XmlOptions)null);
      }

      public static Analyzer newInstance(XmlOptions options) {
         return (Analyzer)XmlBeans.getContextTypeLoader().newInstance(Analyzer.type, options);
      }

      public static Analyzer parse(java.lang.String xmlAsString) throws XmlException {
         return (Analyzer)XmlBeans.getContextTypeLoader().parse(xmlAsString, Analyzer.type, (XmlOptions)null);
      }

      public static Analyzer parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (Analyzer)XmlBeans.getContextTypeLoader().parse(xmlAsString, Analyzer.type, options);
      }

      public static Analyzer parse(File file) throws XmlException, IOException {
         return (Analyzer)XmlBeans.getContextTypeLoader().parse(file, Analyzer.type, (XmlOptions)null);
      }

      public static Analyzer parse(File file, XmlOptions options) throws XmlException, IOException {
         return (Analyzer)XmlBeans.getContextTypeLoader().parse(file, Analyzer.type, options);
      }

      public static Analyzer parse(URL u) throws XmlException, IOException {
         return (Analyzer)XmlBeans.getContextTypeLoader().parse(u, Analyzer.type, (XmlOptions)null);
      }

      public static Analyzer parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (Analyzer)XmlBeans.getContextTypeLoader().parse(u, Analyzer.type, options);
      }

      public static Analyzer parse(InputStream is) throws XmlException, IOException {
         return (Analyzer)XmlBeans.getContextTypeLoader().parse(is, Analyzer.type, (XmlOptions)null);
      }

      public static Analyzer parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (Analyzer)XmlBeans.getContextTypeLoader().parse(is, Analyzer.type, options);
      }

      public static Analyzer parse(Reader r) throws XmlException, IOException {
         return (Analyzer)XmlBeans.getContextTypeLoader().parse(r, Analyzer.type, (XmlOptions)null);
      }

      public static Analyzer parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (Analyzer)XmlBeans.getContextTypeLoader().parse(r, Analyzer.type, options);
      }

      public static Analyzer parse(XMLStreamReader sr) throws XmlException {
         return (Analyzer)XmlBeans.getContextTypeLoader().parse(sr, Analyzer.type, (XmlOptions)null);
      }

      public static Analyzer parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (Analyzer)XmlBeans.getContextTypeLoader().parse(sr, Analyzer.type, options);
      }

      public static Analyzer parse(Node node) throws XmlException {
         return (Analyzer)XmlBeans.getContextTypeLoader().parse(node, Analyzer.type, (XmlOptions)null);
      }

      public static Analyzer parse(Node node, XmlOptions options) throws XmlException {
         return (Analyzer)XmlBeans.getContextTypeLoader().parse(node, Analyzer.type, options);
      }

      /** @deprecated */
      public static Analyzer parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (Analyzer)XmlBeans.getContextTypeLoader().parse(xis, Analyzer.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static Analyzer parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (Analyzer)XmlBeans.getContextTypeLoader().parse(xis, Analyzer.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Analyzer.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Analyzer.type, options);
      }

      private Factory() {
      }
   }
}
