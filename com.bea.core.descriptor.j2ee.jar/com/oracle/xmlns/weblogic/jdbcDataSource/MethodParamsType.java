package com.oracle.xmlns.weblogic.jdbcDataSource;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.javaee.JavaTypeType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface MethodParamsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MethodParamsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("methodparamstype515atype");

   JavaTypeType[] getMethodParamArray();

   JavaTypeType getMethodParamArray(int var1);

   int sizeOfMethodParamArray();

   void setMethodParamArray(JavaTypeType[] var1);

   void setMethodParamArray(int var1, JavaTypeType var2);

   JavaTypeType insertNewMethodParam(int var1);

   JavaTypeType addNewMethodParam();

   void removeMethodParam(int var1);

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static MethodParamsType newInstance() {
         return (MethodParamsType)XmlBeans.getContextTypeLoader().newInstance(MethodParamsType.type, (XmlOptions)null);
      }

      public static MethodParamsType newInstance(XmlOptions options) {
         return (MethodParamsType)XmlBeans.getContextTypeLoader().newInstance(MethodParamsType.type, options);
      }

      public static MethodParamsType parse(String xmlAsString) throws XmlException {
         return (MethodParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MethodParamsType.type, (XmlOptions)null);
      }

      public static MethodParamsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (MethodParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MethodParamsType.type, options);
      }

      public static MethodParamsType parse(File file) throws XmlException, IOException {
         return (MethodParamsType)XmlBeans.getContextTypeLoader().parse(file, MethodParamsType.type, (XmlOptions)null);
      }

      public static MethodParamsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MethodParamsType)XmlBeans.getContextTypeLoader().parse(file, MethodParamsType.type, options);
      }

      public static MethodParamsType parse(URL u) throws XmlException, IOException {
         return (MethodParamsType)XmlBeans.getContextTypeLoader().parse(u, MethodParamsType.type, (XmlOptions)null);
      }

      public static MethodParamsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MethodParamsType)XmlBeans.getContextTypeLoader().parse(u, MethodParamsType.type, options);
      }

      public static MethodParamsType parse(InputStream is) throws XmlException, IOException {
         return (MethodParamsType)XmlBeans.getContextTypeLoader().parse(is, MethodParamsType.type, (XmlOptions)null);
      }

      public static MethodParamsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MethodParamsType)XmlBeans.getContextTypeLoader().parse(is, MethodParamsType.type, options);
      }

      public static MethodParamsType parse(Reader r) throws XmlException, IOException {
         return (MethodParamsType)XmlBeans.getContextTypeLoader().parse(r, MethodParamsType.type, (XmlOptions)null);
      }

      public static MethodParamsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MethodParamsType)XmlBeans.getContextTypeLoader().parse(r, MethodParamsType.type, options);
      }

      public static MethodParamsType parse(XMLStreamReader sr) throws XmlException {
         return (MethodParamsType)XmlBeans.getContextTypeLoader().parse(sr, MethodParamsType.type, (XmlOptions)null);
      }

      public static MethodParamsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MethodParamsType)XmlBeans.getContextTypeLoader().parse(sr, MethodParamsType.type, options);
      }

      public static MethodParamsType parse(Node node) throws XmlException {
         return (MethodParamsType)XmlBeans.getContextTypeLoader().parse(node, MethodParamsType.type, (XmlOptions)null);
      }

      public static MethodParamsType parse(Node node, XmlOptions options) throws XmlException {
         return (MethodParamsType)XmlBeans.getContextTypeLoader().parse(node, MethodParamsType.type, options);
      }

      /** @deprecated */
      public static MethodParamsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MethodParamsType)XmlBeans.getContextTypeLoader().parse(xis, MethodParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MethodParamsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MethodParamsType)XmlBeans.getContextTypeLoader().parse(xis, MethodParamsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MethodParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MethodParamsType.type, options);
      }

      private Factory() {
      }
   }
}
