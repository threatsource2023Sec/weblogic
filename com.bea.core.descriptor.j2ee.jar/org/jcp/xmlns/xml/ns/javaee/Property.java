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

public interface Property extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Property.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("propertye890type");

   java.lang.String getName();

   XmlString xgetName();

   void setName(java.lang.String var1);

   void xsetName(XmlString var1);

   java.lang.String getValue();

   XmlString xgetValue();

   void setValue(java.lang.String var1);

   void xsetValue(XmlString var1);

   public static final class Factory {
      public static Property newInstance() {
         return (Property)XmlBeans.getContextTypeLoader().newInstance(Property.type, (XmlOptions)null);
      }

      public static Property newInstance(XmlOptions options) {
         return (Property)XmlBeans.getContextTypeLoader().newInstance(Property.type, options);
      }

      public static Property parse(java.lang.String xmlAsString) throws XmlException {
         return (Property)XmlBeans.getContextTypeLoader().parse(xmlAsString, Property.type, (XmlOptions)null);
      }

      public static Property parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (Property)XmlBeans.getContextTypeLoader().parse(xmlAsString, Property.type, options);
      }

      public static Property parse(File file) throws XmlException, IOException {
         return (Property)XmlBeans.getContextTypeLoader().parse(file, Property.type, (XmlOptions)null);
      }

      public static Property parse(File file, XmlOptions options) throws XmlException, IOException {
         return (Property)XmlBeans.getContextTypeLoader().parse(file, Property.type, options);
      }

      public static Property parse(URL u) throws XmlException, IOException {
         return (Property)XmlBeans.getContextTypeLoader().parse(u, Property.type, (XmlOptions)null);
      }

      public static Property parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (Property)XmlBeans.getContextTypeLoader().parse(u, Property.type, options);
      }

      public static Property parse(InputStream is) throws XmlException, IOException {
         return (Property)XmlBeans.getContextTypeLoader().parse(is, Property.type, (XmlOptions)null);
      }

      public static Property parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (Property)XmlBeans.getContextTypeLoader().parse(is, Property.type, options);
      }

      public static Property parse(Reader r) throws XmlException, IOException {
         return (Property)XmlBeans.getContextTypeLoader().parse(r, Property.type, (XmlOptions)null);
      }

      public static Property parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (Property)XmlBeans.getContextTypeLoader().parse(r, Property.type, options);
      }

      public static Property parse(XMLStreamReader sr) throws XmlException {
         return (Property)XmlBeans.getContextTypeLoader().parse(sr, Property.type, (XmlOptions)null);
      }

      public static Property parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (Property)XmlBeans.getContextTypeLoader().parse(sr, Property.type, options);
      }

      public static Property parse(Node node) throws XmlException {
         return (Property)XmlBeans.getContextTypeLoader().parse(node, Property.type, (XmlOptions)null);
      }

      public static Property parse(Node node, XmlOptions options) throws XmlException {
         return (Property)XmlBeans.getContextTypeLoader().parse(node, Property.type, options);
      }

      /** @deprecated */
      public static Property parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (Property)XmlBeans.getContextTypeLoader().parse(xis, Property.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static Property parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (Property)XmlBeans.getContextTypeLoader().parse(xis, Property.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Property.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Property.type, options);
      }

      private Factory() {
      }
   }
}
