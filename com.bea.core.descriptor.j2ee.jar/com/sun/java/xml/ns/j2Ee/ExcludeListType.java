package com.sun.java.xml.ns.j2Ee;

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

public interface ExcludeListType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ExcludeListType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("excludelisttype2a84type");

   DescriptionType[] getDescriptionArray();

   DescriptionType getDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(DescriptionType[] var1);

   void setDescriptionArray(int var1, DescriptionType var2);

   DescriptionType insertNewDescription(int var1);

   DescriptionType addNewDescription();

   void removeDescription(int var1);

   MethodType[] getMethodArray();

   MethodType getMethodArray(int var1);

   int sizeOfMethodArray();

   void setMethodArray(MethodType[] var1);

   void setMethodArray(int var1, MethodType var2);

   MethodType insertNewMethod(int var1);

   MethodType addNewMethod();

   void removeMethod(int var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ExcludeListType newInstance() {
         return (ExcludeListType)XmlBeans.getContextTypeLoader().newInstance(ExcludeListType.type, (XmlOptions)null);
      }

      public static ExcludeListType newInstance(XmlOptions options) {
         return (ExcludeListType)XmlBeans.getContextTypeLoader().newInstance(ExcludeListType.type, options);
      }

      public static ExcludeListType parse(java.lang.String xmlAsString) throws XmlException {
         return (ExcludeListType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ExcludeListType.type, (XmlOptions)null);
      }

      public static ExcludeListType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ExcludeListType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ExcludeListType.type, options);
      }

      public static ExcludeListType parse(File file) throws XmlException, IOException {
         return (ExcludeListType)XmlBeans.getContextTypeLoader().parse(file, ExcludeListType.type, (XmlOptions)null);
      }

      public static ExcludeListType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ExcludeListType)XmlBeans.getContextTypeLoader().parse(file, ExcludeListType.type, options);
      }

      public static ExcludeListType parse(URL u) throws XmlException, IOException {
         return (ExcludeListType)XmlBeans.getContextTypeLoader().parse(u, ExcludeListType.type, (XmlOptions)null);
      }

      public static ExcludeListType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ExcludeListType)XmlBeans.getContextTypeLoader().parse(u, ExcludeListType.type, options);
      }

      public static ExcludeListType parse(InputStream is) throws XmlException, IOException {
         return (ExcludeListType)XmlBeans.getContextTypeLoader().parse(is, ExcludeListType.type, (XmlOptions)null);
      }

      public static ExcludeListType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ExcludeListType)XmlBeans.getContextTypeLoader().parse(is, ExcludeListType.type, options);
      }

      public static ExcludeListType parse(Reader r) throws XmlException, IOException {
         return (ExcludeListType)XmlBeans.getContextTypeLoader().parse(r, ExcludeListType.type, (XmlOptions)null);
      }

      public static ExcludeListType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ExcludeListType)XmlBeans.getContextTypeLoader().parse(r, ExcludeListType.type, options);
      }

      public static ExcludeListType parse(XMLStreamReader sr) throws XmlException {
         return (ExcludeListType)XmlBeans.getContextTypeLoader().parse(sr, ExcludeListType.type, (XmlOptions)null);
      }

      public static ExcludeListType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ExcludeListType)XmlBeans.getContextTypeLoader().parse(sr, ExcludeListType.type, options);
      }

      public static ExcludeListType parse(Node node) throws XmlException {
         return (ExcludeListType)XmlBeans.getContextTypeLoader().parse(node, ExcludeListType.type, (XmlOptions)null);
      }

      public static ExcludeListType parse(Node node, XmlOptions options) throws XmlException {
         return (ExcludeListType)XmlBeans.getContextTypeLoader().parse(node, ExcludeListType.type, options);
      }

      /** @deprecated */
      public static ExcludeListType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ExcludeListType)XmlBeans.getContextTypeLoader().parse(xis, ExcludeListType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ExcludeListType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ExcludeListType)XmlBeans.getContextTypeLoader().parse(xis, ExcludeListType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ExcludeListType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ExcludeListType.type, options);
      }

      private Factory() {
      }
   }
}
