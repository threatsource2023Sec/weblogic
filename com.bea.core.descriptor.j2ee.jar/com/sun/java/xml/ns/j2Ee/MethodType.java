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

public interface MethodType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MethodType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("methodtyped974type");

   DescriptionType[] getDescriptionArray();

   DescriptionType getDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(DescriptionType[] var1);

   void setDescriptionArray(int var1, DescriptionType var2);

   DescriptionType insertNewDescription(int var1);

   DescriptionType addNewDescription();

   void removeDescription(int var1);

   EjbNameType getEjbName();

   void setEjbName(EjbNameType var1);

   EjbNameType addNewEjbName();

   MethodIntfType getMethodIntf();

   boolean isSetMethodIntf();

   void setMethodIntf(MethodIntfType var1);

   MethodIntfType addNewMethodIntf();

   void unsetMethodIntf();

   MethodNameType getMethodName();

   void setMethodName(MethodNameType var1);

   MethodNameType addNewMethodName();

   MethodParamsType getMethodParams();

   boolean isSetMethodParams();

   void setMethodParams(MethodParamsType var1);

   MethodParamsType addNewMethodParams();

   void unsetMethodParams();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static MethodType newInstance() {
         return (MethodType)XmlBeans.getContextTypeLoader().newInstance(MethodType.type, (XmlOptions)null);
      }

      public static MethodType newInstance(XmlOptions options) {
         return (MethodType)XmlBeans.getContextTypeLoader().newInstance(MethodType.type, options);
      }

      public static MethodType parse(java.lang.String xmlAsString) throws XmlException {
         return (MethodType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MethodType.type, (XmlOptions)null);
      }

      public static MethodType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (MethodType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MethodType.type, options);
      }

      public static MethodType parse(File file) throws XmlException, IOException {
         return (MethodType)XmlBeans.getContextTypeLoader().parse(file, MethodType.type, (XmlOptions)null);
      }

      public static MethodType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MethodType)XmlBeans.getContextTypeLoader().parse(file, MethodType.type, options);
      }

      public static MethodType parse(URL u) throws XmlException, IOException {
         return (MethodType)XmlBeans.getContextTypeLoader().parse(u, MethodType.type, (XmlOptions)null);
      }

      public static MethodType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MethodType)XmlBeans.getContextTypeLoader().parse(u, MethodType.type, options);
      }

      public static MethodType parse(InputStream is) throws XmlException, IOException {
         return (MethodType)XmlBeans.getContextTypeLoader().parse(is, MethodType.type, (XmlOptions)null);
      }

      public static MethodType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MethodType)XmlBeans.getContextTypeLoader().parse(is, MethodType.type, options);
      }

      public static MethodType parse(Reader r) throws XmlException, IOException {
         return (MethodType)XmlBeans.getContextTypeLoader().parse(r, MethodType.type, (XmlOptions)null);
      }

      public static MethodType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MethodType)XmlBeans.getContextTypeLoader().parse(r, MethodType.type, options);
      }

      public static MethodType parse(XMLStreamReader sr) throws XmlException {
         return (MethodType)XmlBeans.getContextTypeLoader().parse(sr, MethodType.type, (XmlOptions)null);
      }

      public static MethodType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MethodType)XmlBeans.getContextTypeLoader().parse(sr, MethodType.type, options);
      }

      public static MethodType parse(Node node) throws XmlException {
         return (MethodType)XmlBeans.getContextTypeLoader().parse(node, MethodType.type, (XmlOptions)null);
      }

      public static MethodType parse(Node node, XmlOptions options) throws XmlException {
         return (MethodType)XmlBeans.getContextTypeLoader().parse(node, MethodType.type, options);
      }

      /** @deprecated */
      public static MethodType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MethodType)XmlBeans.getContextTypeLoader().parse(xis, MethodType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MethodType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MethodType)XmlBeans.getContextTypeLoader().parse(xis, MethodType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MethodType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MethodType.type, options);
      }

      private Factory() {
      }
   }
}
