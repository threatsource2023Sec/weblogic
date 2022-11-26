package com.bea.ns.staxb.bindingConfig.x90;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlException;
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

public interface WrappedArray extends BindingType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WrappedArray.class.getClassLoader(), "schemacom_bea_xml.system.bea_staxb_runtime").resolveHandle("wrappedarrayb9bctype");

   QName getItemName();

   XmlQName xgetItemName();

   void setItemName(QName var1);

   void xsetItemName(XmlQName var1);

   Mapping getItemType();

   void setItemType(Mapping var1);

   Mapping addNewItemType();

   boolean getItemNillable();

   XmlBoolean xgetItemNillable();

   boolean isSetItemNillable();

   void setItemNillable(boolean var1);

   void xsetItemNillable(XmlBoolean var1);

   void unsetItemNillable();

   public static final class Factory {
      public static WrappedArray newInstance() {
         return (WrappedArray)XmlBeans.getContextTypeLoader().newInstance(WrappedArray.type, (XmlOptions)null);
      }

      public static WrappedArray newInstance(XmlOptions options) {
         return (WrappedArray)XmlBeans.getContextTypeLoader().newInstance(WrappedArray.type, options);
      }

      public static WrappedArray parse(String xmlAsString) throws XmlException {
         return (WrappedArray)XmlBeans.getContextTypeLoader().parse(xmlAsString, WrappedArray.type, (XmlOptions)null);
      }

      public static WrappedArray parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WrappedArray)XmlBeans.getContextTypeLoader().parse(xmlAsString, WrappedArray.type, options);
      }

      public static WrappedArray parse(File file) throws XmlException, IOException {
         return (WrappedArray)XmlBeans.getContextTypeLoader().parse(file, WrappedArray.type, (XmlOptions)null);
      }

      public static WrappedArray parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WrappedArray)XmlBeans.getContextTypeLoader().parse(file, WrappedArray.type, options);
      }

      public static WrappedArray parse(URL u) throws XmlException, IOException {
         return (WrappedArray)XmlBeans.getContextTypeLoader().parse(u, WrappedArray.type, (XmlOptions)null);
      }

      public static WrappedArray parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WrappedArray)XmlBeans.getContextTypeLoader().parse(u, WrappedArray.type, options);
      }

      public static WrappedArray parse(InputStream is) throws XmlException, IOException {
         return (WrappedArray)XmlBeans.getContextTypeLoader().parse(is, WrappedArray.type, (XmlOptions)null);
      }

      public static WrappedArray parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WrappedArray)XmlBeans.getContextTypeLoader().parse(is, WrappedArray.type, options);
      }

      public static WrappedArray parse(Reader r) throws XmlException, IOException {
         return (WrappedArray)XmlBeans.getContextTypeLoader().parse(r, WrappedArray.type, (XmlOptions)null);
      }

      public static WrappedArray parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WrappedArray)XmlBeans.getContextTypeLoader().parse(r, WrappedArray.type, options);
      }

      public static WrappedArray parse(XMLStreamReader sr) throws XmlException {
         return (WrappedArray)XmlBeans.getContextTypeLoader().parse(sr, WrappedArray.type, (XmlOptions)null);
      }

      public static WrappedArray parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WrappedArray)XmlBeans.getContextTypeLoader().parse(sr, WrappedArray.type, options);
      }

      public static WrappedArray parse(Node node) throws XmlException {
         return (WrappedArray)XmlBeans.getContextTypeLoader().parse(node, WrappedArray.type, (XmlOptions)null);
      }

      public static WrappedArray parse(Node node, XmlOptions options) throws XmlException {
         return (WrappedArray)XmlBeans.getContextTypeLoader().parse(node, WrappedArray.type, options);
      }

      /** @deprecated */
      public static WrappedArray parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WrappedArray)XmlBeans.getContextTypeLoader().parse(xis, WrappedArray.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WrappedArray parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WrappedArray)XmlBeans.getContextTypeLoader().parse(xis, WrappedArray.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WrappedArray.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WrappedArray.type, options);
      }

      private Factory() {
      }
   }
}
