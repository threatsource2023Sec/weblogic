package com.sun.java.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface InterceptorsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(InterceptorsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("interceptorstype3e9btype");

   DescriptionType[] getDescriptionArray();

   DescriptionType getDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(DescriptionType[] var1);

   void setDescriptionArray(int var1, DescriptionType var2);

   DescriptionType insertNewDescription(int var1);

   DescriptionType addNewDescription();

   void removeDescription(int var1);

   InterceptorType[] getInterceptorArray();

   InterceptorType getInterceptorArray(int var1);

   int sizeOfInterceptorArray();

   void setInterceptorArray(InterceptorType[] var1);

   void setInterceptorArray(int var1, InterceptorType var2);

   InterceptorType insertNewInterceptor(int var1);

   InterceptorType addNewInterceptor();

   void removeInterceptor(int var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static InterceptorsType newInstance() {
         return (InterceptorsType)XmlBeans.getContextTypeLoader().newInstance(InterceptorsType.type, (XmlOptions)null);
      }

      public static InterceptorsType newInstance(XmlOptions options) {
         return (InterceptorsType)XmlBeans.getContextTypeLoader().newInstance(InterceptorsType.type, options);
      }

      public static InterceptorsType parse(java.lang.String xmlAsString) throws XmlException {
         return (InterceptorsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InterceptorsType.type, (XmlOptions)null);
      }

      public static InterceptorsType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (InterceptorsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InterceptorsType.type, options);
      }

      public static InterceptorsType parse(File file) throws XmlException, IOException {
         return (InterceptorsType)XmlBeans.getContextTypeLoader().parse(file, InterceptorsType.type, (XmlOptions)null);
      }

      public static InterceptorsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (InterceptorsType)XmlBeans.getContextTypeLoader().parse(file, InterceptorsType.type, options);
      }

      public static InterceptorsType parse(URL u) throws XmlException, IOException {
         return (InterceptorsType)XmlBeans.getContextTypeLoader().parse(u, InterceptorsType.type, (XmlOptions)null);
      }

      public static InterceptorsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (InterceptorsType)XmlBeans.getContextTypeLoader().parse(u, InterceptorsType.type, options);
      }

      public static InterceptorsType parse(InputStream is) throws XmlException, IOException {
         return (InterceptorsType)XmlBeans.getContextTypeLoader().parse(is, InterceptorsType.type, (XmlOptions)null);
      }

      public static InterceptorsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (InterceptorsType)XmlBeans.getContextTypeLoader().parse(is, InterceptorsType.type, options);
      }

      public static InterceptorsType parse(Reader r) throws XmlException, IOException {
         return (InterceptorsType)XmlBeans.getContextTypeLoader().parse(r, InterceptorsType.type, (XmlOptions)null);
      }

      public static InterceptorsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (InterceptorsType)XmlBeans.getContextTypeLoader().parse(r, InterceptorsType.type, options);
      }

      public static InterceptorsType parse(XMLStreamReader sr) throws XmlException {
         return (InterceptorsType)XmlBeans.getContextTypeLoader().parse(sr, InterceptorsType.type, (XmlOptions)null);
      }

      public static InterceptorsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (InterceptorsType)XmlBeans.getContextTypeLoader().parse(sr, InterceptorsType.type, options);
      }

      public static InterceptorsType parse(Node node) throws XmlException {
         return (InterceptorsType)XmlBeans.getContextTypeLoader().parse(node, InterceptorsType.type, (XmlOptions)null);
      }

      public static InterceptorsType parse(Node node, XmlOptions options) throws XmlException {
         return (InterceptorsType)XmlBeans.getContextTypeLoader().parse(node, InterceptorsType.type, options);
      }

      /** @deprecated */
      public static InterceptorsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (InterceptorsType)XmlBeans.getContextTypeLoader().parse(xis, InterceptorsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static InterceptorsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (InterceptorsType)XmlBeans.getContextTypeLoader().parse(xis, InterceptorsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InterceptorsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InterceptorsType.type, options);
      }

      private Factory() {
      }
   }
}
