package com.oracle.xmlns.weblogic.weblogicEjbJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.javaee.FullyQualifiedClassType;
import com.sun.java.xml.ns.javaee.JndiNameType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface JndiBindingType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JndiBindingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("jndibindingtypecf77type");

   FullyQualifiedClassType getClassName();

   boolean isSetClassName();

   void setClassName(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewClassName();

   void unsetClassName();

   JndiNameType getJndiName();

   boolean isSetJndiName();

   void setJndiName(JndiNameType var1);

   JndiNameType addNewJndiName();

   void unsetJndiName();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static JndiBindingType newInstance() {
         return (JndiBindingType)XmlBeans.getContextTypeLoader().newInstance(JndiBindingType.type, (XmlOptions)null);
      }

      public static JndiBindingType newInstance(XmlOptions options) {
         return (JndiBindingType)XmlBeans.getContextTypeLoader().newInstance(JndiBindingType.type, options);
      }

      public static JndiBindingType parse(String xmlAsString) throws XmlException {
         return (JndiBindingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JndiBindingType.type, (XmlOptions)null);
      }

      public static JndiBindingType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (JndiBindingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JndiBindingType.type, options);
      }

      public static JndiBindingType parse(File file) throws XmlException, IOException {
         return (JndiBindingType)XmlBeans.getContextTypeLoader().parse(file, JndiBindingType.type, (XmlOptions)null);
      }

      public static JndiBindingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (JndiBindingType)XmlBeans.getContextTypeLoader().parse(file, JndiBindingType.type, options);
      }

      public static JndiBindingType parse(URL u) throws XmlException, IOException {
         return (JndiBindingType)XmlBeans.getContextTypeLoader().parse(u, JndiBindingType.type, (XmlOptions)null);
      }

      public static JndiBindingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (JndiBindingType)XmlBeans.getContextTypeLoader().parse(u, JndiBindingType.type, options);
      }

      public static JndiBindingType parse(InputStream is) throws XmlException, IOException {
         return (JndiBindingType)XmlBeans.getContextTypeLoader().parse(is, JndiBindingType.type, (XmlOptions)null);
      }

      public static JndiBindingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (JndiBindingType)XmlBeans.getContextTypeLoader().parse(is, JndiBindingType.type, options);
      }

      public static JndiBindingType parse(Reader r) throws XmlException, IOException {
         return (JndiBindingType)XmlBeans.getContextTypeLoader().parse(r, JndiBindingType.type, (XmlOptions)null);
      }

      public static JndiBindingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (JndiBindingType)XmlBeans.getContextTypeLoader().parse(r, JndiBindingType.type, options);
      }

      public static JndiBindingType parse(XMLStreamReader sr) throws XmlException {
         return (JndiBindingType)XmlBeans.getContextTypeLoader().parse(sr, JndiBindingType.type, (XmlOptions)null);
      }

      public static JndiBindingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (JndiBindingType)XmlBeans.getContextTypeLoader().parse(sr, JndiBindingType.type, options);
      }

      public static JndiBindingType parse(Node node) throws XmlException {
         return (JndiBindingType)XmlBeans.getContextTypeLoader().parse(node, JndiBindingType.type, (XmlOptions)null);
      }

      public static JndiBindingType parse(Node node, XmlOptions options) throws XmlException {
         return (JndiBindingType)XmlBeans.getContextTypeLoader().parse(node, JndiBindingType.type, options);
      }

      /** @deprecated */
      public static JndiBindingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (JndiBindingType)XmlBeans.getContextTypeLoader().parse(xis, JndiBindingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static JndiBindingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (JndiBindingType)XmlBeans.getContextTypeLoader().parse(xis, JndiBindingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JndiBindingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JndiBindingType.type, options);
      }

      private Factory() {
      }
   }
}
