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

public interface JaxrpcEnumType extends BindingType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JaxrpcEnumType.class.getClassLoader(), "schemacom_bea_xml.system.bea_staxb_runtime").resolveHandle("jaxrpcenumtype147atype");

   String getBaseXmlcomponent();

   XmlSignature xgetBaseXmlcomponent();

   void setBaseXmlcomponent(String var1);

   void xsetBaseXmlcomponent(XmlSignature var1);

   String getBaseJavatype();

   JavaClassName xgetBaseJavatype();

   void setBaseJavatype(String var1);

   void xsetBaseJavatype(JavaClassName var1);

   JavaMethodName getGetValueMethod();

   void setGetValueMethod(JavaMethodName var1);

   JavaMethodName addNewGetValueMethod();

   JavaMethodName getFromValueMethod();

   void setFromValueMethod(JavaMethodName var1);

   JavaMethodName addNewFromValueMethod();

   JavaMethodName getFromStringMethod();

   void setFromStringMethod(JavaMethodName var1);

   JavaMethodName addNewFromStringMethod();

   JavaMethodName getToXMLMethod();

   boolean isSetToXMLMethod();

   void setToXMLMethod(JavaMethodName var1);

   JavaMethodName addNewToXMLMethod();

   void unsetToXMLMethod();

   public static final class Factory {
      public static JaxrpcEnumType newInstance() {
         return (JaxrpcEnumType)XmlBeans.getContextTypeLoader().newInstance(JaxrpcEnumType.type, (XmlOptions)null);
      }

      public static JaxrpcEnumType newInstance(XmlOptions options) {
         return (JaxrpcEnumType)XmlBeans.getContextTypeLoader().newInstance(JaxrpcEnumType.type, options);
      }

      public static JaxrpcEnumType parse(String xmlAsString) throws XmlException {
         return (JaxrpcEnumType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JaxrpcEnumType.type, (XmlOptions)null);
      }

      public static JaxrpcEnumType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (JaxrpcEnumType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JaxrpcEnumType.type, options);
      }

      public static JaxrpcEnumType parse(File file) throws XmlException, IOException {
         return (JaxrpcEnumType)XmlBeans.getContextTypeLoader().parse(file, JaxrpcEnumType.type, (XmlOptions)null);
      }

      public static JaxrpcEnumType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (JaxrpcEnumType)XmlBeans.getContextTypeLoader().parse(file, JaxrpcEnumType.type, options);
      }

      public static JaxrpcEnumType parse(URL u) throws XmlException, IOException {
         return (JaxrpcEnumType)XmlBeans.getContextTypeLoader().parse(u, JaxrpcEnumType.type, (XmlOptions)null);
      }

      public static JaxrpcEnumType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (JaxrpcEnumType)XmlBeans.getContextTypeLoader().parse(u, JaxrpcEnumType.type, options);
      }

      public static JaxrpcEnumType parse(InputStream is) throws XmlException, IOException {
         return (JaxrpcEnumType)XmlBeans.getContextTypeLoader().parse(is, JaxrpcEnumType.type, (XmlOptions)null);
      }

      public static JaxrpcEnumType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (JaxrpcEnumType)XmlBeans.getContextTypeLoader().parse(is, JaxrpcEnumType.type, options);
      }

      public static JaxrpcEnumType parse(Reader r) throws XmlException, IOException {
         return (JaxrpcEnumType)XmlBeans.getContextTypeLoader().parse(r, JaxrpcEnumType.type, (XmlOptions)null);
      }

      public static JaxrpcEnumType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (JaxrpcEnumType)XmlBeans.getContextTypeLoader().parse(r, JaxrpcEnumType.type, options);
      }

      public static JaxrpcEnumType parse(XMLStreamReader sr) throws XmlException {
         return (JaxrpcEnumType)XmlBeans.getContextTypeLoader().parse(sr, JaxrpcEnumType.type, (XmlOptions)null);
      }

      public static JaxrpcEnumType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (JaxrpcEnumType)XmlBeans.getContextTypeLoader().parse(sr, JaxrpcEnumType.type, options);
      }

      public static JaxrpcEnumType parse(Node node) throws XmlException {
         return (JaxrpcEnumType)XmlBeans.getContextTypeLoader().parse(node, JaxrpcEnumType.type, (XmlOptions)null);
      }

      public static JaxrpcEnumType parse(Node node, XmlOptions options) throws XmlException {
         return (JaxrpcEnumType)XmlBeans.getContextTypeLoader().parse(node, JaxrpcEnumType.type, options);
      }

      /** @deprecated */
      public static JaxrpcEnumType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (JaxrpcEnumType)XmlBeans.getContextTypeLoader().parse(xis, JaxrpcEnumType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static JaxrpcEnumType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (JaxrpcEnumType)XmlBeans.getContextTypeLoader().parse(xis, JaxrpcEnumType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JaxrpcEnumType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JaxrpcEnumType.type, options);
      }

      private Factory() {
      }
   }
}
