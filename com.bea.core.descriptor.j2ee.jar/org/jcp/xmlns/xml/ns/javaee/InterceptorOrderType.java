package org.jcp.xmlns.xml.ns.javaee;

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

public interface InterceptorOrderType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(InterceptorOrderType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("interceptorordertypef217type");

   FullyQualifiedClassType[] getInterceptorClassArray();

   FullyQualifiedClassType getInterceptorClassArray(int var1);

   int sizeOfInterceptorClassArray();

   void setInterceptorClassArray(FullyQualifiedClassType[] var1);

   void setInterceptorClassArray(int var1, FullyQualifiedClassType var2);

   FullyQualifiedClassType insertNewInterceptorClass(int var1);

   FullyQualifiedClassType addNewInterceptorClass();

   void removeInterceptorClass(int var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static InterceptorOrderType newInstance() {
         return (InterceptorOrderType)XmlBeans.getContextTypeLoader().newInstance(InterceptorOrderType.type, (XmlOptions)null);
      }

      public static InterceptorOrderType newInstance(XmlOptions options) {
         return (InterceptorOrderType)XmlBeans.getContextTypeLoader().newInstance(InterceptorOrderType.type, options);
      }

      public static InterceptorOrderType parse(java.lang.String xmlAsString) throws XmlException {
         return (InterceptorOrderType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InterceptorOrderType.type, (XmlOptions)null);
      }

      public static InterceptorOrderType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (InterceptorOrderType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InterceptorOrderType.type, options);
      }

      public static InterceptorOrderType parse(File file) throws XmlException, IOException {
         return (InterceptorOrderType)XmlBeans.getContextTypeLoader().parse(file, InterceptorOrderType.type, (XmlOptions)null);
      }

      public static InterceptorOrderType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (InterceptorOrderType)XmlBeans.getContextTypeLoader().parse(file, InterceptorOrderType.type, options);
      }

      public static InterceptorOrderType parse(URL u) throws XmlException, IOException {
         return (InterceptorOrderType)XmlBeans.getContextTypeLoader().parse(u, InterceptorOrderType.type, (XmlOptions)null);
      }

      public static InterceptorOrderType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (InterceptorOrderType)XmlBeans.getContextTypeLoader().parse(u, InterceptorOrderType.type, options);
      }

      public static InterceptorOrderType parse(InputStream is) throws XmlException, IOException {
         return (InterceptorOrderType)XmlBeans.getContextTypeLoader().parse(is, InterceptorOrderType.type, (XmlOptions)null);
      }

      public static InterceptorOrderType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (InterceptorOrderType)XmlBeans.getContextTypeLoader().parse(is, InterceptorOrderType.type, options);
      }

      public static InterceptorOrderType parse(Reader r) throws XmlException, IOException {
         return (InterceptorOrderType)XmlBeans.getContextTypeLoader().parse(r, InterceptorOrderType.type, (XmlOptions)null);
      }

      public static InterceptorOrderType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (InterceptorOrderType)XmlBeans.getContextTypeLoader().parse(r, InterceptorOrderType.type, options);
      }

      public static InterceptorOrderType parse(XMLStreamReader sr) throws XmlException {
         return (InterceptorOrderType)XmlBeans.getContextTypeLoader().parse(sr, InterceptorOrderType.type, (XmlOptions)null);
      }

      public static InterceptorOrderType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (InterceptorOrderType)XmlBeans.getContextTypeLoader().parse(sr, InterceptorOrderType.type, options);
      }

      public static InterceptorOrderType parse(Node node) throws XmlException {
         return (InterceptorOrderType)XmlBeans.getContextTypeLoader().parse(node, InterceptorOrderType.type, (XmlOptions)null);
      }

      public static InterceptorOrderType parse(Node node, XmlOptions options) throws XmlException {
         return (InterceptorOrderType)XmlBeans.getContextTypeLoader().parse(node, InterceptorOrderType.type, options);
      }

      /** @deprecated */
      public static InterceptorOrderType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (InterceptorOrderType)XmlBeans.getContextTypeLoader().parse(xis, InterceptorOrderType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static InterceptorOrderType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (InterceptorOrderType)XmlBeans.getContextTypeLoader().parse(xis, InterceptorOrderType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InterceptorOrderType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InterceptorOrderType.type, options);
      }

      private Factory() {
      }
   }
}
