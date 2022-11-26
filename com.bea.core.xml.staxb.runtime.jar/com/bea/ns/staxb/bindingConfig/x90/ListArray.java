package com.bea.ns.staxb.bindingConfig.x90;

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

public interface ListArray extends BindingType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ListArray.class.getClassLoader(), "schemacom_bea_xml.system.bea_staxb_runtime").resolveHandle("listarrayd6c7type");

   Mapping getItemType();

   void setItemType(Mapping var1);

   Mapping addNewItemType();

   public static final class Factory {
      public static ListArray newInstance() {
         return (ListArray)XmlBeans.getContextTypeLoader().newInstance(ListArray.type, (XmlOptions)null);
      }

      public static ListArray newInstance(XmlOptions options) {
         return (ListArray)XmlBeans.getContextTypeLoader().newInstance(ListArray.type, options);
      }

      public static ListArray parse(String xmlAsString) throws XmlException {
         return (ListArray)XmlBeans.getContextTypeLoader().parse(xmlAsString, ListArray.type, (XmlOptions)null);
      }

      public static ListArray parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ListArray)XmlBeans.getContextTypeLoader().parse(xmlAsString, ListArray.type, options);
      }

      public static ListArray parse(File file) throws XmlException, IOException {
         return (ListArray)XmlBeans.getContextTypeLoader().parse(file, ListArray.type, (XmlOptions)null);
      }

      public static ListArray parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ListArray)XmlBeans.getContextTypeLoader().parse(file, ListArray.type, options);
      }

      public static ListArray parse(URL u) throws XmlException, IOException {
         return (ListArray)XmlBeans.getContextTypeLoader().parse(u, ListArray.type, (XmlOptions)null);
      }

      public static ListArray parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ListArray)XmlBeans.getContextTypeLoader().parse(u, ListArray.type, options);
      }

      public static ListArray parse(InputStream is) throws XmlException, IOException {
         return (ListArray)XmlBeans.getContextTypeLoader().parse(is, ListArray.type, (XmlOptions)null);
      }

      public static ListArray parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ListArray)XmlBeans.getContextTypeLoader().parse(is, ListArray.type, options);
      }

      public static ListArray parse(Reader r) throws XmlException, IOException {
         return (ListArray)XmlBeans.getContextTypeLoader().parse(r, ListArray.type, (XmlOptions)null);
      }

      public static ListArray parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ListArray)XmlBeans.getContextTypeLoader().parse(r, ListArray.type, options);
      }

      public static ListArray parse(XMLStreamReader sr) throws XmlException {
         return (ListArray)XmlBeans.getContextTypeLoader().parse(sr, ListArray.type, (XmlOptions)null);
      }

      public static ListArray parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ListArray)XmlBeans.getContextTypeLoader().parse(sr, ListArray.type, options);
      }

      public static ListArray parse(Node node) throws XmlException {
         return (ListArray)XmlBeans.getContextTypeLoader().parse(node, ListArray.type, (XmlOptions)null);
      }

      public static ListArray parse(Node node, XmlOptions options) throws XmlException {
         return (ListArray)XmlBeans.getContextTypeLoader().parse(node, ListArray.type, options);
      }

      /** @deprecated */
      public static ListArray parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ListArray)XmlBeans.getContextTypeLoader().parse(xis, ListArray.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ListArray parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ListArray)XmlBeans.getContextTypeLoader().parse(xis, ListArray.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ListArray.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ListArray.type, options);
      }

      private Factory() {
      }
   }
}
