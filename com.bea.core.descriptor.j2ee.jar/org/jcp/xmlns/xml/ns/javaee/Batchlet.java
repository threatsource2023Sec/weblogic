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

public interface Batchlet extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Batchlet.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("batchletaa44type");

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
      public static Batchlet newInstance() {
         return (Batchlet)XmlBeans.getContextTypeLoader().newInstance(Batchlet.type, (XmlOptions)null);
      }

      public static Batchlet newInstance(XmlOptions options) {
         return (Batchlet)XmlBeans.getContextTypeLoader().newInstance(Batchlet.type, options);
      }

      public static Batchlet parse(java.lang.String xmlAsString) throws XmlException {
         return (Batchlet)XmlBeans.getContextTypeLoader().parse(xmlAsString, Batchlet.type, (XmlOptions)null);
      }

      public static Batchlet parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (Batchlet)XmlBeans.getContextTypeLoader().parse(xmlAsString, Batchlet.type, options);
      }

      public static Batchlet parse(File file) throws XmlException, IOException {
         return (Batchlet)XmlBeans.getContextTypeLoader().parse(file, Batchlet.type, (XmlOptions)null);
      }

      public static Batchlet parse(File file, XmlOptions options) throws XmlException, IOException {
         return (Batchlet)XmlBeans.getContextTypeLoader().parse(file, Batchlet.type, options);
      }

      public static Batchlet parse(URL u) throws XmlException, IOException {
         return (Batchlet)XmlBeans.getContextTypeLoader().parse(u, Batchlet.type, (XmlOptions)null);
      }

      public static Batchlet parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (Batchlet)XmlBeans.getContextTypeLoader().parse(u, Batchlet.type, options);
      }

      public static Batchlet parse(InputStream is) throws XmlException, IOException {
         return (Batchlet)XmlBeans.getContextTypeLoader().parse(is, Batchlet.type, (XmlOptions)null);
      }

      public static Batchlet parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (Batchlet)XmlBeans.getContextTypeLoader().parse(is, Batchlet.type, options);
      }

      public static Batchlet parse(Reader r) throws XmlException, IOException {
         return (Batchlet)XmlBeans.getContextTypeLoader().parse(r, Batchlet.type, (XmlOptions)null);
      }

      public static Batchlet parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (Batchlet)XmlBeans.getContextTypeLoader().parse(r, Batchlet.type, options);
      }

      public static Batchlet parse(XMLStreamReader sr) throws XmlException {
         return (Batchlet)XmlBeans.getContextTypeLoader().parse(sr, Batchlet.type, (XmlOptions)null);
      }

      public static Batchlet parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (Batchlet)XmlBeans.getContextTypeLoader().parse(sr, Batchlet.type, options);
      }

      public static Batchlet parse(Node node) throws XmlException {
         return (Batchlet)XmlBeans.getContextTypeLoader().parse(node, Batchlet.type, (XmlOptions)null);
      }

      public static Batchlet parse(Node node, XmlOptions options) throws XmlException {
         return (Batchlet)XmlBeans.getContextTypeLoader().parse(node, Batchlet.type, options);
      }

      /** @deprecated */
      public static Batchlet parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (Batchlet)XmlBeans.getContextTypeLoader().parse(xis, Batchlet.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static Batchlet parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (Batchlet)XmlBeans.getContextTypeLoader().parse(xis, Batchlet.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Batchlet.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Batchlet.type, options);
      }

      private Factory() {
      }
   }
}
