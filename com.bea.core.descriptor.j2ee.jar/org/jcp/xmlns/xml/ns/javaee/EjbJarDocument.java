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

public interface EjbJarDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(EjbJarDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("ejbjard15cdoctype");

   EjbJarType getEjbJar();

   void setEjbJar(EjbJarType var1);

   EjbJarType addNewEjbJar();

   public static final class Factory {
      public static EjbJarDocument newInstance() {
         return (EjbJarDocument)XmlBeans.getContextTypeLoader().newInstance(EjbJarDocument.type, (XmlOptions)null);
      }

      public static EjbJarDocument newInstance(XmlOptions options) {
         return (EjbJarDocument)XmlBeans.getContextTypeLoader().newInstance(EjbJarDocument.type, options);
      }

      public static EjbJarDocument parse(java.lang.String xmlAsString) throws XmlException {
         return (EjbJarDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, EjbJarDocument.type, (XmlOptions)null);
      }

      public static EjbJarDocument parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (EjbJarDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, EjbJarDocument.type, options);
      }

      public static EjbJarDocument parse(File file) throws XmlException, IOException {
         return (EjbJarDocument)XmlBeans.getContextTypeLoader().parse(file, EjbJarDocument.type, (XmlOptions)null);
      }

      public static EjbJarDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (EjbJarDocument)XmlBeans.getContextTypeLoader().parse(file, EjbJarDocument.type, options);
      }

      public static EjbJarDocument parse(URL u) throws XmlException, IOException {
         return (EjbJarDocument)XmlBeans.getContextTypeLoader().parse(u, EjbJarDocument.type, (XmlOptions)null);
      }

      public static EjbJarDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (EjbJarDocument)XmlBeans.getContextTypeLoader().parse(u, EjbJarDocument.type, options);
      }

      public static EjbJarDocument parse(InputStream is) throws XmlException, IOException {
         return (EjbJarDocument)XmlBeans.getContextTypeLoader().parse(is, EjbJarDocument.type, (XmlOptions)null);
      }

      public static EjbJarDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (EjbJarDocument)XmlBeans.getContextTypeLoader().parse(is, EjbJarDocument.type, options);
      }

      public static EjbJarDocument parse(Reader r) throws XmlException, IOException {
         return (EjbJarDocument)XmlBeans.getContextTypeLoader().parse(r, EjbJarDocument.type, (XmlOptions)null);
      }

      public static EjbJarDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (EjbJarDocument)XmlBeans.getContextTypeLoader().parse(r, EjbJarDocument.type, options);
      }

      public static EjbJarDocument parse(XMLStreamReader sr) throws XmlException {
         return (EjbJarDocument)XmlBeans.getContextTypeLoader().parse(sr, EjbJarDocument.type, (XmlOptions)null);
      }

      public static EjbJarDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (EjbJarDocument)XmlBeans.getContextTypeLoader().parse(sr, EjbJarDocument.type, options);
      }

      public static EjbJarDocument parse(Node node) throws XmlException {
         return (EjbJarDocument)XmlBeans.getContextTypeLoader().parse(node, EjbJarDocument.type, (XmlOptions)null);
      }

      public static EjbJarDocument parse(Node node, XmlOptions options) throws XmlException {
         return (EjbJarDocument)XmlBeans.getContextTypeLoader().parse(node, EjbJarDocument.type, options);
      }

      /** @deprecated */
      public static EjbJarDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (EjbJarDocument)XmlBeans.getContextTypeLoader().parse(xis, EjbJarDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static EjbJarDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (EjbJarDocument)XmlBeans.getContextTypeLoader().parse(xis, EjbJarDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EjbJarDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EjbJarDocument.type, options);
      }

      private Factory() {
      }
   }
}
