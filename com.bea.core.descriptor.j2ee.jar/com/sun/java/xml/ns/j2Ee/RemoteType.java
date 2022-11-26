package com.sun.java.xml.ns.j2Ee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface RemoteType extends FullyQualifiedClassType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(RemoteType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("remotetypef259type");

   public static final class Factory {
      public static RemoteType newInstance() {
         return (RemoteType)XmlBeans.getContextTypeLoader().newInstance(RemoteType.type, (XmlOptions)null);
      }

      public static RemoteType newInstance(XmlOptions options) {
         return (RemoteType)XmlBeans.getContextTypeLoader().newInstance(RemoteType.type, options);
      }

      public static RemoteType parse(java.lang.String xmlAsString) throws XmlException {
         return (RemoteType)XmlBeans.getContextTypeLoader().parse(xmlAsString, RemoteType.type, (XmlOptions)null);
      }

      public static RemoteType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (RemoteType)XmlBeans.getContextTypeLoader().parse(xmlAsString, RemoteType.type, options);
      }

      public static RemoteType parse(File file) throws XmlException, IOException {
         return (RemoteType)XmlBeans.getContextTypeLoader().parse(file, RemoteType.type, (XmlOptions)null);
      }

      public static RemoteType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (RemoteType)XmlBeans.getContextTypeLoader().parse(file, RemoteType.type, options);
      }

      public static RemoteType parse(URL u) throws XmlException, IOException {
         return (RemoteType)XmlBeans.getContextTypeLoader().parse(u, RemoteType.type, (XmlOptions)null);
      }

      public static RemoteType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (RemoteType)XmlBeans.getContextTypeLoader().parse(u, RemoteType.type, options);
      }

      public static RemoteType parse(InputStream is) throws XmlException, IOException {
         return (RemoteType)XmlBeans.getContextTypeLoader().parse(is, RemoteType.type, (XmlOptions)null);
      }

      public static RemoteType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (RemoteType)XmlBeans.getContextTypeLoader().parse(is, RemoteType.type, options);
      }

      public static RemoteType parse(Reader r) throws XmlException, IOException {
         return (RemoteType)XmlBeans.getContextTypeLoader().parse(r, RemoteType.type, (XmlOptions)null);
      }

      public static RemoteType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (RemoteType)XmlBeans.getContextTypeLoader().parse(r, RemoteType.type, options);
      }

      public static RemoteType parse(XMLStreamReader sr) throws XmlException {
         return (RemoteType)XmlBeans.getContextTypeLoader().parse(sr, RemoteType.type, (XmlOptions)null);
      }

      public static RemoteType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (RemoteType)XmlBeans.getContextTypeLoader().parse(sr, RemoteType.type, options);
      }

      public static RemoteType parse(Node node) throws XmlException {
         return (RemoteType)XmlBeans.getContextTypeLoader().parse(node, RemoteType.type, (XmlOptions)null);
      }

      public static RemoteType parse(Node node, XmlOptions options) throws XmlException {
         return (RemoteType)XmlBeans.getContextTypeLoader().parse(node, RemoteType.type, options);
      }

      /** @deprecated */
      public static RemoteType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (RemoteType)XmlBeans.getContextTypeLoader().parse(xis, RemoteType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static RemoteType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (RemoteType)XmlBeans.getContextTypeLoader().parse(xis, RemoteType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RemoteType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RemoteType.type, options);
      }

      private Factory() {
      }
   }
}
