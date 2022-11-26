package com.bea.ns.staxb.bindingConfig.x90;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
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

public interface SoapArray extends BindingType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SoapArray.class.getClassLoader(), "schemacom_bea_xml.system.bea_staxb_runtime").resolveHandle("soaparray6d34type");

   QName getItemName();

   XmlQName xgetItemName();

   boolean isSetItemName();

   void setItemName(QName var1);

   void xsetItemName(XmlQName var1);

   void unsetItemName();

   Mapping getItemType();

   boolean isSetItemType();

   void setItemType(Mapping var1);

   Mapping addNewItemType();

   void unsetItemType();

   boolean getItemNillable();

   XmlBoolean xgetItemNillable();

   boolean isSetItemNillable();

   void setItemNillable(boolean var1);

   void xsetItemNillable(XmlBoolean var1);

   void unsetItemNillable();

   int getRanks();

   XmlInt xgetRanks();

   boolean isSetRanks();

   void setRanks(int var1);

   void xsetRanks(XmlInt var1);

   void unsetRanks();

   public static final class Factory {
      public static SoapArray newInstance() {
         return (SoapArray)XmlBeans.getContextTypeLoader().newInstance(SoapArray.type, (XmlOptions)null);
      }

      public static SoapArray newInstance(XmlOptions options) {
         return (SoapArray)XmlBeans.getContextTypeLoader().newInstance(SoapArray.type, options);
      }

      public static SoapArray parse(String xmlAsString) throws XmlException {
         return (SoapArray)XmlBeans.getContextTypeLoader().parse(xmlAsString, SoapArray.type, (XmlOptions)null);
      }

      public static SoapArray parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SoapArray)XmlBeans.getContextTypeLoader().parse(xmlAsString, SoapArray.type, options);
      }

      public static SoapArray parse(File file) throws XmlException, IOException {
         return (SoapArray)XmlBeans.getContextTypeLoader().parse(file, SoapArray.type, (XmlOptions)null);
      }

      public static SoapArray parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SoapArray)XmlBeans.getContextTypeLoader().parse(file, SoapArray.type, options);
      }

      public static SoapArray parse(URL u) throws XmlException, IOException {
         return (SoapArray)XmlBeans.getContextTypeLoader().parse(u, SoapArray.type, (XmlOptions)null);
      }

      public static SoapArray parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SoapArray)XmlBeans.getContextTypeLoader().parse(u, SoapArray.type, options);
      }

      public static SoapArray parse(InputStream is) throws XmlException, IOException {
         return (SoapArray)XmlBeans.getContextTypeLoader().parse(is, SoapArray.type, (XmlOptions)null);
      }

      public static SoapArray parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SoapArray)XmlBeans.getContextTypeLoader().parse(is, SoapArray.type, options);
      }

      public static SoapArray parse(Reader r) throws XmlException, IOException {
         return (SoapArray)XmlBeans.getContextTypeLoader().parse(r, SoapArray.type, (XmlOptions)null);
      }

      public static SoapArray parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SoapArray)XmlBeans.getContextTypeLoader().parse(r, SoapArray.type, options);
      }

      public static SoapArray parse(XMLStreamReader sr) throws XmlException {
         return (SoapArray)XmlBeans.getContextTypeLoader().parse(sr, SoapArray.type, (XmlOptions)null);
      }

      public static SoapArray parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SoapArray)XmlBeans.getContextTypeLoader().parse(sr, SoapArray.type, options);
      }

      public static SoapArray parse(Node node) throws XmlException {
         return (SoapArray)XmlBeans.getContextTypeLoader().parse(node, SoapArray.type, (XmlOptions)null);
      }

      public static SoapArray parse(Node node, XmlOptions options) throws XmlException {
         return (SoapArray)XmlBeans.getContextTypeLoader().parse(node, SoapArray.type, options);
      }

      /** @deprecated */
      public static SoapArray parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SoapArray)XmlBeans.getContextTypeLoader().parse(xis, SoapArray.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SoapArray parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SoapArray)XmlBeans.getContextTypeLoader().parse(xis, SoapArray.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SoapArray.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SoapArray.type, options);
      }

      private Factory() {
      }
   }
}
