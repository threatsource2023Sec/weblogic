package org.jcp.xmlns.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface EmptyType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(EmptyType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("emptytypef610type");

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static EmptyType newInstance() {
         return (EmptyType)XmlBeans.getContextTypeLoader().newInstance(EmptyType.type, (XmlOptions)null);
      }

      public static EmptyType newInstance(XmlOptions options) {
         return (EmptyType)XmlBeans.getContextTypeLoader().newInstance(EmptyType.type, options);
      }

      public static EmptyType parse(java.lang.String xmlAsString) throws XmlException {
         return (EmptyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EmptyType.type, (XmlOptions)null);
      }

      public static EmptyType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (EmptyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EmptyType.type, options);
      }

      public static EmptyType parse(File file) throws XmlException, IOException {
         return (EmptyType)XmlBeans.getContextTypeLoader().parse(file, EmptyType.type, (XmlOptions)null);
      }

      public static EmptyType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (EmptyType)XmlBeans.getContextTypeLoader().parse(file, EmptyType.type, options);
      }

      public static EmptyType parse(URL u) throws XmlException, IOException {
         return (EmptyType)XmlBeans.getContextTypeLoader().parse(u, EmptyType.type, (XmlOptions)null);
      }

      public static EmptyType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (EmptyType)XmlBeans.getContextTypeLoader().parse(u, EmptyType.type, options);
      }

      public static EmptyType parse(InputStream is) throws XmlException, IOException {
         return (EmptyType)XmlBeans.getContextTypeLoader().parse(is, EmptyType.type, (XmlOptions)null);
      }

      public static EmptyType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (EmptyType)XmlBeans.getContextTypeLoader().parse(is, EmptyType.type, options);
      }

      public static EmptyType parse(Reader r) throws XmlException, IOException {
         return (EmptyType)XmlBeans.getContextTypeLoader().parse(r, EmptyType.type, (XmlOptions)null);
      }

      public static EmptyType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (EmptyType)XmlBeans.getContextTypeLoader().parse(r, EmptyType.type, options);
      }

      public static EmptyType parse(XMLStreamReader sr) throws XmlException {
         return (EmptyType)XmlBeans.getContextTypeLoader().parse(sr, EmptyType.type, (XmlOptions)null);
      }

      public static EmptyType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (EmptyType)XmlBeans.getContextTypeLoader().parse(sr, EmptyType.type, options);
      }

      public static EmptyType parse(Node node) throws XmlException {
         return (EmptyType)XmlBeans.getContextTypeLoader().parse(node, EmptyType.type, (XmlOptions)null);
      }

      public static EmptyType parse(Node node, XmlOptions options) throws XmlException {
         return (EmptyType)XmlBeans.getContextTypeLoader().parse(node, EmptyType.type, options);
      }

      /** @deprecated */
      public static EmptyType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (EmptyType)XmlBeans.getContextTypeLoader().parse(xis, EmptyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static EmptyType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (EmptyType)XmlBeans.getContextTypeLoader().parse(xis, EmptyType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EmptyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EmptyType.type, options);
      }

      private Factory() {
      }
   }
}
