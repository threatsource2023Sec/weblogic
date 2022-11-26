package com.bea.xbean.xb.xsdschema;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlQName;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface ListDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ListDocument.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("listcde5doctype");

   List getList();

   void setList(List var1);

   List addNewList();

   public static final class Factory {
      public static ListDocument newInstance() {
         return (ListDocument)XmlBeans.getContextTypeLoader().newInstance(ListDocument.type, (XmlOptions)null);
      }

      public static ListDocument newInstance(XmlOptions options) {
         return (ListDocument)XmlBeans.getContextTypeLoader().newInstance(ListDocument.type, options);
      }

      public static ListDocument parse(String xmlAsString) throws XmlException {
         return (ListDocument)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, ListDocument.type, (XmlOptions)null);
      }

      public static ListDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ListDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ListDocument.type, options);
      }

      public static ListDocument parse(File file) throws XmlException, IOException {
         return (ListDocument)XmlBeans.getContextTypeLoader().parse((File)file, ListDocument.type, (XmlOptions)null);
      }

      public static ListDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ListDocument)XmlBeans.getContextTypeLoader().parse(file, ListDocument.type, options);
      }

      public static ListDocument parse(URL u) throws XmlException, IOException {
         return (ListDocument)XmlBeans.getContextTypeLoader().parse((URL)u, ListDocument.type, (XmlOptions)null);
      }

      public static ListDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ListDocument)XmlBeans.getContextTypeLoader().parse(u, ListDocument.type, options);
      }

      public static ListDocument parse(InputStream is) throws XmlException, IOException {
         return (ListDocument)XmlBeans.getContextTypeLoader().parse((InputStream)is, ListDocument.type, (XmlOptions)null);
      }

      public static ListDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ListDocument)XmlBeans.getContextTypeLoader().parse(is, ListDocument.type, options);
      }

      public static ListDocument parse(Reader r) throws XmlException, IOException {
         return (ListDocument)XmlBeans.getContextTypeLoader().parse((Reader)r, ListDocument.type, (XmlOptions)null);
      }

      public static ListDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ListDocument)XmlBeans.getContextTypeLoader().parse(r, ListDocument.type, options);
      }

      public static ListDocument parse(XMLStreamReader sr) throws XmlException {
         return (ListDocument)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, ListDocument.type, (XmlOptions)null);
      }

      public static ListDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ListDocument)XmlBeans.getContextTypeLoader().parse(sr, ListDocument.type, options);
      }

      public static ListDocument parse(Node node) throws XmlException {
         return (ListDocument)XmlBeans.getContextTypeLoader().parse((Node)node, ListDocument.type, (XmlOptions)null);
      }

      public static ListDocument parse(Node node, XmlOptions options) throws XmlException {
         return (ListDocument)XmlBeans.getContextTypeLoader().parse(node, ListDocument.type, options);
      }

      /** @deprecated */
      public static ListDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ListDocument)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, ListDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ListDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ListDocument)XmlBeans.getContextTypeLoader().parse(xis, ListDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ListDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ListDocument.type, options);
      }

      private Factory() {
      }
   }

   public interface List extends Annotated {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(List.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("list391felemtype");

      LocalSimpleType getSimpleType();

      boolean isSetSimpleType();

      void setSimpleType(LocalSimpleType var1);

      LocalSimpleType addNewSimpleType();

      void unsetSimpleType();

      QName getItemType();

      XmlQName xgetItemType();

      boolean isSetItemType();

      void setItemType(QName var1);

      void xsetItemType(XmlQName var1);

      void unsetItemType();

      public static final class Factory {
         public static List newInstance() {
            return (List)XmlBeans.getContextTypeLoader().newInstance(ListDocument.List.type, (XmlOptions)null);
         }

         public static List newInstance(XmlOptions options) {
            return (List)XmlBeans.getContextTypeLoader().newInstance(ListDocument.List.type, options);
         }

         private Factory() {
         }
      }
   }
}
