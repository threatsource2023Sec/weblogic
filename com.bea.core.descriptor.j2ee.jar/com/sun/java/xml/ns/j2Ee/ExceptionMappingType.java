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

public interface ExceptionMappingType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ExceptionMappingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("exceptionmappingtypef871type");

   FullyQualifiedClassType getExceptionType();

   void setExceptionType(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewExceptionType();

   WsdlMessageType getWsdlMessage();

   void setWsdlMessage(WsdlMessageType var1);

   WsdlMessageType addNewWsdlMessage();

   WsdlMessagePartNameType getWsdlMessagePartName();

   boolean isSetWsdlMessagePartName();

   void setWsdlMessagePartName(WsdlMessagePartNameType var1);

   WsdlMessagePartNameType addNewWsdlMessagePartName();

   void unsetWsdlMessagePartName();

   ConstructorParameterOrderType getConstructorParameterOrder();

   boolean isSetConstructorParameterOrder();

   void setConstructorParameterOrder(ConstructorParameterOrderType var1);

   ConstructorParameterOrderType addNewConstructorParameterOrder();

   void unsetConstructorParameterOrder();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ExceptionMappingType newInstance() {
         return (ExceptionMappingType)XmlBeans.getContextTypeLoader().newInstance(ExceptionMappingType.type, (XmlOptions)null);
      }

      public static ExceptionMappingType newInstance(XmlOptions options) {
         return (ExceptionMappingType)XmlBeans.getContextTypeLoader().newInstance(ExceptionMappingType.type, options);
      }

      public static ExceptionMappingType parse(java.lang.String xmlAsString) throws XmlException {
         return (ExceptionMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ExceptionMappingType.type, (XmlOptions)null);
      }

      public static ExceptionMappingType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ExceptionMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ExceptionMappingType.type, options);
      }

      public static ExceptionMappingType parse(File file) throws XmlException, IOException {
         return (ExceptionMappingType)XmlBeans.getContextTypeLoader().parse(file, ExceptionMappingType.type, (XmlOptions)null);
      }

      public static ExceptionMappingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ExceptionMappingType)XmlBeans.getContextTypeLoader().parse(file, ExceptionMappingType.type, options);
      }

      public static ExceptionMappingType parse(URL u) throws XmlException, IOException {
         return (ExceptionMappingType)XmlBeans.getContextTypeLoader().parse(u, ExceptionMappingType.type, (XmlOptions)null);
      }

      public static ExceptionMappingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ExceptionMappingType)XmlBeans.getContextTypeLoader().parse(u, ExceptionMappingType.type, options);
      }

      public static ExceptionMappingType parse(InputStream is) throws XmlException, IOException {
         return (ExceptionMappingType)XmlBeans.getContextTypeLoader().parse(is, ExceptionMappingType.type, (XmlOptions)null);
      }

      public static ExceptionMappingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ExceptionMappingType)XmlBeans.getContextTypeLoader().parse(is, ExceptionMappingType.type, options);
      }

      public static ExceptionMappingType parse(Reader r) throws XmlException, IOException {
         return (ExceptionMappingType)XmlBeans.getContextTypeLoader().parse(r, ExceptionMappingType.type, (XmlOptions)null);
      }

      public static ExceptionMappingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ExceptionMappingType)XmlBeans.getContextTypeLoader().parse(r, ExceptionMappingType.type, options);
      }

      public static ExceptionMappingType parse(XMLStreamReader sr) throws XmlException {
         return (ExceptionMappingType)XmlBeans.getContextTypeLoader().parse(sr, ExceptionMappingType.type, (XmlOptions)null);
      }

      public static ExceptionMappingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ExceptionMappingType)XmlBeans.getContextTypeLoader().parse(sr, ExceptionMappingType.type, options);
      }

      public static ExceptionMappingType parse(Node node) throws XmlException {
         return (ExceptionMappingType)XmlBeans.getContextTypeLoader().parse(node, ExceptionMappingType.type, (XmlOptions)null);
      }

      public static ExceptionMappingType parse(Node node, XmlOptions options) throws XmlException {
         return (ExceptionMappingType)XmlBeans.getContextTypeLoader().parse(node, ExceptionMappingType.type, options);
      }

      /** @deprecated */
      public static ExceptionMappingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ExceptionMappingType)XmlBeans.getContextTypeLoader().parse(xis, ExceptionMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ExceptionMappingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ExceptionMappingType)XmlBeans.getContextTypeLoader().parse(xis, ExceptionMappingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ExceptionMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ExceptionMappingType.type, options);
      }

      private Factory() {
      }
   }
}
