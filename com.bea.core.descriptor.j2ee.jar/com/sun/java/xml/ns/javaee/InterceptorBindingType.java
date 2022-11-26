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

public interface InterceptorBindingType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(InterceptorBindingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("interceptorbindingtypeaad8type");

   DescriptionType[] getDescriptionArray();

   DescriptionType getDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(DescriptionType[] var1);

   void setDescriptionArray(int var1, DescriptionType var2);

   DescriptionType insertNewDescription(int var1);

   DescriptionType addNewDescription();

   void removeDescription(int var1);

   String getEjbName();

   void setEjbName(String var1);

   String addNewEjbName();

   FullyQualifiedClassType[] getInterceptorClassArray();

   FullyQualifiedClassType getInterceptorClassArray(int var1);

   int sizeOfInterceptorClassArray();

   void setInterceptorClassArray(FullyQualifiedClassType[] var1);

   void setInterceptorClassArray(int var1, FullyQualifiedClassType var2);

   FullyQualifiedClassType insertNewInterceptorClass(int var1);

   FullyQualifiedClassType addNewInterceptorClass();

   void removeInterceptorClass(int var1);

   InterceptorOrderType getInterceptorOrder();

   boolean isSetInterceptorOrder();

   void setInterceptorOrder(InterceptorOrderType var1);

   InterceptorOrderType addNewInterceptorOrder();

   void unsetInterceptorOrder();

   TrueFalseType getExcludeDefaultInterceptors();

   boolean isSetExcludeDefaultInterceptors();

   void setExcludeDefaultInterceptors(TrueFalseType var1);

   TrueFalseType addNewExcludeDefaultInterceptors();

   void unsetExcludeDefaultInterceptors();

   TrueFalseType getExcludeClassInterceptors();

   boolean isSetExcludeClassInterceptors();

   void setExcludeClassInterceptors(TrueFalseType var1);

   TrueFalseType addNewExcludeClassInterceptors();

   void unsetExcludeClassInterceptors();

   NamedMethodType getMethod();

   boolean isSetMethod();

   void setMethod(NamedMethodType var1);

   NamedMethodType addNewMethod();

   void unsetMethod();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static InterceptorBindingType newInstance() {
         return (InterceptorBindingType)XmlBeans.getContextTypeLoader().newInstance(InterceptorBindingType.type, (XmlOptions)null);
      }

      public static InterceptorBindingType newInstance(XmlOptions options) {
         return (InterceptorBindingType)XmlBeans.getContextTypeLoader().newInstance(InterceptorBindingType.type, options);
      }

      public static InterceptorBindingType parse(java.lang.String xmlAsString) throws XmlException {
         return (InterceptorBindingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InterceptorBindingType.type, (XmlOptions)null);
      }

      public static InterceptorBindingType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (InterceptorBindingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InterceptorBindingType.type, options);
      }

      public static InterceptorBindingType parse(File file) throws XmlException, IOException {
         return (InterceptorBindingType)XmlBeans.getContextTypeLoader().parse(file, InterceptorBindingType.type, (XmlOptions)null);
      }

      public static InterceptorBindingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (InterceptorBindingType)XmlBeans.getContextTypeLoader().parse(file, InterceptorBindingType.type, options);
      }

      public static InterceptorBindingType parse(URL u) throws XmlException, IOException {
         return (InterceptorBindingType)XmlBeans.getContextTypeLoader().parse(u, InterceptorBindingType.type, (XmlOptions)null);
      }

      public static InterceptorBindingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (InterceptorBindingType)XmlBeans.getContextTypeLoader().parse(u, InterceptorBindingType.type, options);
      }

      public static InterceptorBindingType parse(InputStream is) throws XmlException, IOException {
         return (InterceptorBindingType)XmlBeans.getContextTypeLoader().parse(is, InterceptorBindingType.type, (XmlOptions)null);
      }

      public static InterceptorBindingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (InterceptorBindingType)XmlBeans.getContextTypeLoader().parse(is, InterceptorBindingType.type, options);
      }

      public static InterceptorBindingType parse(Reader r) throws XmlException, IOException {
         return (InterceptorBindingType)XmlBeans.getContextTypeLoader().parse(r, InterceptorBindingType.type, (XmlOptions)null);
      }

      public static InterceptorBindingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (InterceptorBindingType)XmlBeans.getContextTypeLoader().parse(r, InterceptorBindingType.type, options);
      }

      public static InterceptorBindingType parse(XMLStreamReader sr) throws XmlException {
         return (InterceptorBindingType)XmlBeans.getContextTypeLoader().parse(sr, InterceptorBindingType.type, (XmlOptions)null);
      }

      public static InterceptorBindingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (InterceptorBindingType)XmlBeans.getContextTypeLoader().parse(sr, InterceptorBindingType.type, options);
      }

      public static InterceptorBindingType parse(Node node) throws XmlException {
         return (InterceptorBindingType)XmlBeans.getContextTypeLoader().parse(node, InterceptorBindingType.type, (XmlOptions)null);
      }

      public static InterceptorBindingType parse(Node node, XmlOptions options) throws XmlException {
         return (InterceptorBindingType)XmlBeans.getContextTypeLoader().parse(node, InterceptorBindingType.type, options);
      }

      /** @deprecated */
      public static InterceptorBindingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (InterceptorBindingType)XmlBeans.getContextTypeLoader().parse(xis, InterceptorBindingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static InterceptorBindingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (InterceptorBindingType)XmlBeans.getContextTypeLoader().parse(xis, InterceptorBindingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InterceptorBindingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InterceptorBindingType.type, options);
      }

      private Factory() {
      }
   }
}
