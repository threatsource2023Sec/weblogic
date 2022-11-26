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

public interface ByNameBean extends BindingType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ByNameBean.class.getClassLoader(), "schemacom_bea_xml.system.bea_staxb_runtime").resolveHandle("bynamebean2059type");

   GenericXmlProperty getAnyProperty();

   boolean isSetAnyProperty();

   void setAnyProperty(GenericXmlProperty var1);

   GenericXmlProperty addNewAnyProperty();

   void unsetAnyProperty();

   GenericXmlProperty getAnyAttributeProperty();

   boolean isSetAnyAttributeProperty();

   void setAnyAttributeProperty(GenericXmlProperty var1);

   GenericXmlProperty addNewAnyAttributeProperty();

   void unsetAnyAttributeProperty();

   QnameProperty[] getQnamePropertyArray();

   QnameProperty getQnamePropertyArray(int var1);

   int sizeOfQnamePropertyArray();

   void setQnamePropertyArray(QnameProperty[] var1);

   void setQnamePropertyArray(int var1, QnameProperty var2);

   QnameProperty insertNewQnameProperty(int var1);

   QnameProperty addNewQnameProperty();

   void removeQnameProperty(int var1);

   public static final class Factory {
      public static ByNameBean newInstance() {
         return (ByNameBean)XmlBeans.getContextTypeLoader().newInstance(ByNameBean.type, (XmlOptions)null);
      }

      public static ByNameBean newInstance(XmlOptions options) {
         return (ByNameBean)XmlBeans.getContextTypeLoader().newInstance(ByNameBean.type, options);
      }

      public static ByNameBean parse(String xmlAsString) throws XmlException {
         return (ByNameBean)XmlBeans.getContextTypeLoader().parse(xmlAsString, ByNameBean.type, (XmlOptions)null);
      }

      public static ByNameBean parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ByNameBean)XmlBeans.getContextTypeLoader().parse(xmlAsString, ByNameBean.type, options);
      }

      public static ByNameBean parse(File file) throws XmlException, IOException {
         return (ByNameBean)XmlBeans.getContextTypeLoader().parse(file, ByNameBean.type, (XmlOptions)null);
      }

      public static ByNameBean parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ByNameBean)XmlBeans.getContextTypeLoader().parse(file, ByNameBean.type, options);
      }

      public static ByNameBean parse(URL u) throws XmlException, IOException {
         return (ByNameBean)XmlBeans.getContextTypeLoader().parse(u, ByNameBean.type, (XmlOptions)null);
      }

      public static ByNameBean parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ByNameBean)XmlBeans.getContextTypeLoader().parse(u, ByNameBean.type, options);
      }

      public static ByNameBean parse(InputStream is) throws XmlException, IOException {
         return (ByNameBean)XmlBeans.getContextTypeLoader().parse(is, ByNameBean.type, (XmlOptions)null);
      }

      public static ByNameBean parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ByNameBean)XmlBeans.getContextTypeLoader().parse(is, ByNameBean.type, options);
      }

      public static ByNameBean parse(Reader r) throws XmlException, IOException {
         return (ByNameBean)XmlBeans.getContextTypeLoader().parse(r, ByNameBean.type, (XmlOptions)null);
      }

      public static ByNameBean parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ByNameBean)XmlBeans.getContextTypeLoader().parse(r, ByNameBean.type, options);
      }

      public static ByNameBean parse(XMLStreamReader sr) throws XmlException {
         return (ByNameBean)XmlBeans.getContextTypeLoader().parse(sr, ByNameBean.type, (XmlOptions)null);
      }

      public static ByNameBean parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ByNameBean)XmlBeans.getContextTypeLoader().parse(sr, ByNameBean.type, options);
      }

      public static ByNameBean parse(Node node) throws XmlException {
         return (ByNameBean)XmlBeans.getContextTypeLoader().parse(node, ByNameBean.type, (XmlOptions)null);
      }

      public static ByNameBean parse(Node node, XmlOptions options) throws XmlException {
         return (ByNameBean)XmlBeans.getContextTypeLoader().parse(node, ByNameBean.type, options);
      }

      /** @deprecated */
      public static ByNameBean parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ByNameBean)XmlBeans.getContextTypeLoader().parse(xis, ByNameBean.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ByNameBean parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ByNameBean)XmlBeans.getContextTypeLoader().parse(xis, ByNameBean.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ByNameBean.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ByNameBean.type, options);
      }

      private Factory() {
      }
   }
}
