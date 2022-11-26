package com.bea.ns.staxb.bindingConfig.x90;

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

public interface BindingTable extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(BindingTable.class.getClassLoader(), "schemacom_bea_xml.system.bea_staxb_runtime").resolveHandle("bindingtable6871type");

   BindingType[] getBindingTypeArray();

   BindingType getBindingTypeArray(int var1);

   int sizeOfBindingTypeArray();

   void setBindingTypeArray(BindingType[] var1);

   void setBindingTypeArray(int var1, BindingType var2);

   BindingType insertNewBindingType(int var1);

   BindingType addNewBindingType();

   void removeBindingType(int var1);

   public static final class Factory {
      public static BindingTable newInstance() {
         return (BindingTable)XmlBeans.getContextTypeLoader().newInstance(BindingTable.type, (XmlOptions)null);
      }

      public static BindingTable newInstance(XmlOptions options) {
         return (BindingTable)XmlBeans.getContextTypeLoader().newInstance(BindingTable.type, options);
      }

      public static BindingTable parse(String xmlAsString) throws XmlException {
         return (BindingTable)XmlBeans.getContextTypeLoader().parse(xmlAsString, BindingTable.type, (XmlOptions)null);
      }

      public static BindingTable parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (BindingTable)XmlBeans.getContextTypeLoader().parse(xmlAsString, BindingTable.type, options);
      }

      public static BindingTable parse(File file) throws XmlException, IOException {
         return (BindingTable)XmlBeans.getContextTypeLoader().parse(file, BindingTable.type, (XmlOptions)null);
      }

      public static BindingTable parse(File file, XmlOptions options) throws XmlException, IOException {
         return (BindingTable)XmlBeans.getContextTypeLoader().parse(file, BindingTable.type, options);
      }

      public static BindingTable parse(URL u) throws XmlException, IOException {
         return (BindingTable)XmlBeans.getContextTypeLoader().parse(u, BindingTable.type, (XmlOptions)null);
      }

      public static BindingTable parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (BindingTable)XmlBeans.getContextTypeLoader().parse(u, BindingTable.type, options);
      }

      public static BindingTable parse(InputStream is) throws XmlException, IOException {
         return (BindingTable)XmlBeans.getContextTypeLoader().parse(is, BindingTable.type, (XmlOptions)null);
      }

      public static BindingTable parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (BindingTable)XmlBeans.getContextTypeLoader().parse(is, BindingTable.type, options);
      }

      public static BindingTable parse(Reader r) throws XmlException, IOException {
         return (BindingTable)XmlBeans.getContextTypeLoader().parse(r, BindingTable.type, (XmlOptions)null);
      }

      public static BindingTable parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (BindingTable)XmlBeans.getContextTypeLoader().parse(r, BindingTable.type, options);
      }

      public static BindingTable parse(XMLStreamReader sr) throws XmlException {
         return (BindingTable)XmlBeans.getContextTypeLoader().parse(sr, BindingTable.type, (XmlOptions)null);
      }

      public static BindingTable parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (BindingTable)XmlBeans.getContextTypeLoader().parse(sr, BindingTable.type, options);
      }

      public static BindingTable parse(Node node) throws XmlException {
         return (BindingTable)XmlBeans.getContextTypeLoader().parse(node, BindingTable.type, (XmlOptions)null);
      }

      public static BindingTable parse(Node node, XmlOptions options) throws XmlException {
         return (BindingTable)XmlBeans.getContextTypeLoader().parse(node, BindingTable.type, options);
      }

      /** @deprecated */
      public static BindingTable parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (BindingTable)XmlBeans.getContextTypeLoader().parse(xis, BindingTable.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static BindingTable parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (BindingTable)XmlBeans.getContextTypeLoader().parse(xis, BindingTable.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, BindingTable.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, BindingTable.type, options);
      }

      private Factory() {
      }
   }
}
