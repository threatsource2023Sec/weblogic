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

public interface SimpleContentBean extends BindingType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SimpleContentBean.class.getClassLoader(), "schemacom_bea_xml.system.bea_staxb_runtime").resolveHandle("simplecontentbeanb9aatype");

   GenericXmlProperty getAnyAttributeProperty();

   boolean isSetAnyAttributeProperty();

   void setAnyAttributeProperty(GenericXmlProperty var1);

   GenericXmlProperty addNewAnyAttributeProperty();

   void unsetAnyAttributeProperty();

   SimpleContentProperty getSimpleContentProperty();

   void setSimpleContentProperty(SimpleContentProperty var1);

   SimpleContentProperty addNewSimpleContentProperty();

   QnameProperty[] getAttributePropertyArray();

   QnameProperty getAttributePropertyArray(int var1);

   int sizeOfAttributePropertyArray();

   void setAttributePropertyArray(QnameProperty[] var1);

   void setAttributePropertyArray(int var1, QnameProperty var2);

   QnameProperty insertNewAttributeProperty(int var1);

   QnameProperty addNewAttributeProperty();

   void removeAttributeProperty(int var1);

   public static final class Factory {
      public static SimpleContentBean newInstance() {
         return (SimpleContentBean)XmlBeans.getContextTypeLoader().newInstance(SimpleContentBean.type, (XmlOptions)null);
      }

      public static SimpleContentBean newInstance(XmlOptions options) {
         return (SimpleContentBean)XmlBeans.getContextTypeLoader().newInstance(SimpleContentBean.type, options);
      }

      public static SimpleContentBean parse(String xmlAsString) throws XmlException {
         return (SimpleContentBean)XmlBeans.getContextTypeLoader().parse(xmlAsString, SimpleContentBean.type, (XmlOptions)null);
      }

      public static SimpleContentBean parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SimpleContentBean)XmlBeans.getContextTypeLoader().parse(xmlAsString, SimpleContentBean.type, options);
      }

      public static SimpleContentBean parse(File file) throws XmlException, IOException {
         return (SimpleContentBean)XmlBeans.getContextTypeLoader().parse(file, SimpleContentBean.type, (XmlOptions)null);
      }

      public static SimpleContentBean parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SimpleContentBean)XmlBeans.getContextTypeLoader().parse(file, SimpleContentBean.type, options);
      }

      public static SimpleContentBean parse(URL u) throws XmlException, IOException {
         return (SimpleContentBean)XmlBeans.getContextTypeLoader().parse(u, SimpleContentBean.type, (XmlOptions)null);
      }

      public static SimpleContentBean parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SimpleContentBean)XmlBeans.getContextTypeLoader().parse(u, SimpleContentBean.type, options);
      }

      public static SimpleContentBean parse(InputStream is) throws XmlException, IOException {
         return (SimpleContentBean)XmlBeans.getContextTypeLoader().parse(is, SimpleContentBean.type, (XmlOptions)null);
      }

      public static SimpleContentBean parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SimpleContentBean)XmlBeans.getContextTypeLoader().parse(is, SimpleContentBean.type, options);
      }

      public static SimpleContentBean parse(Reader r) throws XmlException, IOException {
         return (SimpleContentBean)XmlBeans.getContextTypeLoader().parse(r, SimpleContentBean.type, (XmlOptions)null);
      }

      public static SimpleContentBean parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SimpleContentBean)XmlBeans.getContextTypeLoader().parse(r, SimpleContentBean.type, options);
      }

      public static SimpleContentBean parse(XMLStreamReader sr) throws XmlException {
         return (SimpleContentBean)XmlBeans.getContextTypeLoader().parse(sr, SimpleContentBean.type, (XmlOptions)null);
      }

      public static SimpleContentBean parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SimpleContentBean)XmlBeans.getContextTypeLoader().parse(sr, SimpleContentBean.type, options);
      }

      public static SimpleContentBean parse(Node node) throws XmlException {
         return (SimpleContentBean)XmlBeans.getContextTypeLoader().parse(node, SimpleContentBean.type, (XmlOptions)null);
      }

      public static SimpleContentBean parse(Node node, XmlOptions options) throws XmlException {
         return (SimpleContentBean)XmlBeans.getContextTypeLoader().parse(node, SimpleContentBean.type, options);
      }

      /** @deprecated */
      public static SimpleContentBean parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SimpleContentBean)XmlBeans.getContextTypeLoader().parse(xis, SimpleContentBean.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SimpleContentBean parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SimpleContentBean)XmlBeans.getContextTypeLoader().parse(xis, SimpleContentBean.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SimpleContentBean.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SimpleContentBean.type, options);
      }

      private Factory() {
      }
   }
}
