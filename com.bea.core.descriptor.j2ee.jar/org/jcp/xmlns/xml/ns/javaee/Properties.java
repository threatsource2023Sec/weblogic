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

public interface Properties extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Properties.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("properties72b2type");

   Property[] getPropertyArray();

   Property getPropertyArray(int var1);

   int sizeOfPropertyArray();

   void setPropertyArray(Property[] var1);

   void setPropertyArray(int var1, Property var2);

   Property insertNewProperty(int var1);

   Property addNewProperty();

   void removeProperty(int var1);

   java.lang.String getPartition();

   XmlString xgetPartition();

   boolean isSetPartition();

   void setPartition(java.lang.String var1);

   void xsetPartition(XmlString var1);

   void unsetPartition();

   public static final class Factory {
      public static Properties newInstance() {
         return (Properties)XmlBeans.getContextTypeLoader().newInstance(Properties.type, (XmlOptions)null);
      }

      public static Properties newInstance(XmlOptions options) {
         return (Properties)XmlBeans.getContextTypeLoader().newInstance(Properties.type, options);
      }

      public static Properties parse(java.lang.String xmlAsString) throws XmlException {
         return (Properties)XmlBeans.getContextTypeLoader().parse(xmlAsString, Properties.type, (XmlOptions)null);
      }

      public static Properties parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (Properties)XmlBeans.getContextTypeLoader().parse(xmlAsString, Properties.type, options);
      }

      public static Properties parse(File file) throws XmlException, IOException {
         return (Properties)XmlBeans.getContextTypeLoader().parse(file, Properties.type, (XmlOptions)null);
      }

      public static Properties parse(File file, XmlOptions options) throws XmlException, IOException {
         return (Properties)XmlBeans.getContextTypeLoader().parse(file, Properties.type, options);
      }

      public static Properties parse(URL u) throws XmlException, IOException {
         return (Properties)XmlBeans.getContextTypeLoader().parse(u, Properties.type, (XmlOptions)null);
      }

      public static Properties parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (Properties)XmlBeans.getContextTypeLoader().parse(u, Properties.type, options);
      }

      public static Properties parse(InputStream is) throws XmlException, IOException {
         return (Properties)XmlBeans.getContextTypeLoader().parse(is, Properties.type, (XmlOptions)null);
      }

      public static Properties parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (Properties)XmlBeans.getContextTypeLoader().parse(is, Properties.type, options);
      }

      public static Properties parse(Reader r) throws XmlException, IOException {
         return (Properties)XmlBeans.getContextTypeLoader().parse(r, Properties.type, (XmlOptions)null);
      }

      public static Properties parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (Properties)XmlBeans.getContextTypeLoader().parse(r, Properties.type, options);
      }

      public static Properties parse(XMLStreamReader sr) throws XmlException {
         return (Properties)XmlBeans.getContextTypeLoader().parse(sr, Properties.type, (XmlOptions)null);
      }

      public static Properties parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (Properties)XmlBeans.getContextTypeLoader().parse(sr, Properties.type, options);
      }

      public static Properties parse(Node node) throws XmlException {
         return (Properties)XmlBeans.getContextTypeLoader().parse(node, Properties.type, (XmlOptions)null);
      }

      public static Properties parse(Node node, XmlOptions options) throws XmlException {
         return (Properties)XmlBeans.getContextTypeLoader().parse(node, Properties.type, options);
      }

      /** @deprecated */
      public static Properties parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (Properties)XmlBeans.getContextTypeLoader().parse(xis, Properties.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static Properties parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (Properties)XmlBeans.getContextTypeLoader().parse(xis, Properties.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Properties.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Properties.type, options);
      }

      private Factory() {
      }
   }
}
