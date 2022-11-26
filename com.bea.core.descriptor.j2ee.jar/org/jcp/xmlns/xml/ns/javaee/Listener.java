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

public interface Listener extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Listener.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("listener5d51type");

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
      public static Listener newInstance() {
         return (Listener)XmlBeans.getContextTypeLoader().newInstance(Listener.type, (XmlOptions)null);
      }

      public static Listener newInstance(XmlOptions options) {
         return (Listener)XmlBeans.getContextTypeLoader().newInstance(Listener.type, options);
      }

      public static Listener parse(java.lang.String xmlAsString) throws XmlException {
         return (Listener)XmlBeans.getContextTypeLoader().parse(xmlAsString, Listener.type, (XmlOptions)null);
      }

      public static Listener parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (Listener)XmlBeans.getContextTypeLoader().parse(xmlAsString, Listener.type, options);
      }

      public static Listener parse(File file) throws XmlException, IOException {
         return (Listener)XmlBeans.getContextTypeLoader().parse(file, Listener.type, (XmlOptions)null);
      }

      public static Listener parse(File file, XmlOptions options) throws XmlException, IOException {
         return (Listener)XmlBeans.getContextTypeLoader().parse(file, Listener.type, options);
      }

      public static Listener parse(URL u) throws XmlException, IOException {
         return (Listener)XmlBeans.getContextTypeLoader().parse(u, Listener.type, (XmlOptions)null);
      }

      public static Listener parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (Listener)XmlBeans.getContextTypeLoader().parse(u, Listener.type, options);
      }

      public static Listener parse(InputStream is) throws XmlException, IOException {
         return (Listener)XmlBeans.getContextTypeLoader().parse(is, Listener.type, (XmlOptions)null);
      }

      public static Listener parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (Listener)XmlBeans.getContextTypeLoader().parse(is, Listener.type, options);
      }

      public static Listener parse(Reader r) throws XmlException, IOException {
         return (Listener)XmlBeans.getContextTypeLoader().parse(r, Listener.type, (XmlOptions)null);
      }

      public static Listener parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (Listener)XmlBeans.getContextTypeLoader().parse(r, Listener.type, options);
      }

      public static Listener parse(XMLStreamReader sr) throws XmlException {
         return (Listener)XmlBeans.getContextTypeLoader().parse(sr, Listener.type, (XmlOptions)null);
      }

      public static Listener parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (Listener)XmlBeans.getContextTypeLoader().parse(sr, Listener.type, options);
      }

      public static Listener parse(Node node) throws XmlException {
         return (Listener)XmlBeans.getContextTypeLoader().parse(node, Listener.type, (XmlOptions)null);
      }

      public static Listener parse(Node node, XmlOptions options) throws XmlException {
         return (Listener)XmlBeans.getContextTypeLoader().parse(node, Listener.type, options);
      }

      /** @deprecated */
      public static Listener parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (Listener)XmlBeans.getContextTypeLoader().parse(xis, Listener.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static Listener parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (Listener)XmlBeans.getContextTypeLoader().parse(xis, Listener.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Listener.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Listener.type, options);
      }

      private Factory() {
      }
   }
}
