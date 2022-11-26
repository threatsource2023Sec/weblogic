package org.jcp.xmlns.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface AroundInvokeType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AroundInvokeType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("aroundinvoketype5f85type");

   FullyQualifiedClassType getClass1();

   boolean isSetClass1();

   void setClass1(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewClass1();

   void unsetClass1();

   JavaIdentifierType getMethodName();

   void setMethodName(JavaIdentifierType var1);

   JavaIdentifierType addNewMethodName();

   public static final class Factory {
      public static AroundInvokeType newInstance() {
         return (AroundInvokeType)XmlBeans.getContextTypeLoader().newInstance(AroundInvokeType.type, (XmlOptions)null);
      }

      public static AroundInvokeType newInstance(XmlOptions options) {
         return (AroundInvokeType)XmlBeans.getContextTypeLoader().newInstance(AroundInvokeType.type, options);
      }

      public static AroundInvokeType parse(java.lang.String xmlAsString) throws XmlException {
         return (AroundInvokeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AroundInvokeType.type, (XmlOptions)null);
      }

      public static AroundInvokeType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (AroundInvokeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AroundInvokeType.type, options);
      }

      public static AroundInvokeType parse(File file) throws XmlException, IOException {
         return (AroundInvokeType)XmlBeans.getContextTypeLoader().parse(file, AroundInvokeType.type, (XmlOptions)null);
      }

      public static AroundInvokeType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AroundInvokeType)XmlBeans.getContextTypeLoader().parse(file, AroundInvokeType.type, options);
      }

      public static AroundInvokeType parse(URL u) throws XmlException, IOException {
         return (AroundInvokeType)XmlBeans.getContextTypeLoader().parse(u, AroundInvokeType.type, (XmlOptions)null);
      }

      public static AroundInvokeType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AroundInvokeType)XmlBeans.getContextTypeLoader().parse(u, AroundInvokeType.type, options);
      }

      public static AroundInvokeType parse(InputStream is) throws XmlException, IOException {
         return (AroundInvokeType)XmlBeans.getContextTypeLoader().parse(is, AroundInvokeType.type, (XmlOptions)null);
      }

      public static AroundInvokeType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AroundInvokeType)XmlBeans.getContextTypeLoader().parse(is, AroundInvokeType.type, options);
      }

      public static AroundInvokeType parse(Reader r) throws XmlException, IOException {
         return (AroundInvokeType)XmlBeans.getContextTypeLoader().parse(r, AroundInvokeType.type, (XmlOptions)null);
      }

      public static AroundInvokeType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AroundInvokeType)XmlBeans.getContextTypeLoader().parse(r, AroundInvokeType.type, options);
      }

      public static AroundInvokeType parse(XMLStreamReader sr) throws XmlException {
         return (AroundInvokeType)XmlBeans.getContextTypeLoader().parse(sr, AroundInvokeType.type, (XmlOptions)null);
      }

      public static AroundInvokeType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AroundInvokeType)XmlBeans.getContextTypeLoader().parse(sr, AroundInvokeType.type, options);
      }

      public static AroundInvokeType parse(Node node) throws XmlException {
         return (AroundInvokeType)XmlBeans.getContextTypeLoader().parse(node, AroundInvokeType.type, (XmlOptions)null);
      }

      public static AroundInvokeType parse(Node node, XmlOptions options) throws XmlException {
         return (AroundInvokeType)XmlBeans.getContextTypeLoader().parse(node, AroundInvokeType.type, options);
      }

      /** @deprecated */
      public static AroundInvokeType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AroundInvokeType)XmlBeans.getContextTypeLoader().parse(xis, AroundInvokeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AroundInvokeType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AroundInvokeType)XmlBeans.getContextTypeLoader().parse(xis, AroundInvokeType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AroundInvokeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AroundInvokeType.type, options);
      }

      private Factory() {
      }
   }
}
