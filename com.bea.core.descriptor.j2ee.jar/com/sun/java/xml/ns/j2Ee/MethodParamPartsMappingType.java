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

public interface MethodParamPartsMappingType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MethodParamPartsMappingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("methodparampartsmappingtype0ac8type");

   XsdNonNegativeIntegerType getParamPosition();

   void setParamPosition(XsdNonNegativeIntegerType var1);

   XsdNonNegativeIntegerType addNewParamPosition();

   JavaTypeType getParamType();

   void setParamType(JavaTypeType var1);

   JavaTypeType addNewParamType();

   WsdlMessageMappingType getWsdlMessageMapping();

   void setWsdlMessageMapping(WsdlMessageMappingType var1);

   WsdlMessageMappingType addNewWsdlMessageMapping();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static MethodParamPartsMappingType newInstance() {
         return (MethodParamPartsMappingType)XmlBeans.getContextTypeLoader().newInstance(MethodParamPartsMappingType.type, (XmlOptions)null);
      }

      public static MethodParamPartsMappingType newInstance(XmlOptions options) {
         return (MethodParamPartsMappingType)XmlBeans.getContextTypeLoader().newInstance(MethodParamPartsMappingType.type, options);
      }

      public static MethodParamPartsMappingType parse(java.lang.String xmlAsString) throws XmlException {
         return (MethodParamPartsMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MethodParamPartsMappingType.type, (XmlOptions)null);
      }

      public static MethodParamPartsMappingType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (MethodParamPartsMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MethodParamPartsMappingType.type, options);
      }

      public static MethodParamPartsMappingType parse(File file) throws XmlException, IOException {
         return (MethodParamPartsMappingType)XmlBeans.getContextTypeLoader().parse(file, MethodParamPartsMappingType.type, (XmlOptions)null);
      }

      public static MethodParamPartsMappingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MethodParamPartsMappingType)XmlBeans.getContextTypeLoader().parse(file, MethodParamPartsMappingType.type, options);
      }

      public static MethodParamPartsMappingType parse(URL u) throws XmlException, IOException {
         return (MethodParamPartsMappingType)XmlBeans.getContextTypeLoader().parse(u, MethodParamPartsMappingType.type, (XmlOptions)null);
      }

      public static MethodParamPartsMappingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MethodParamPartsMappingType)XmlBeans.getContextTypeLoader().parse(u, MethodParamPartsMappingType.type, options);
      }

      public static MethodParamPartsMappingType parse(InputStream is) throws XmlException, IOException {
         return (MethodParamPartsMappingType)XmlBeans.getContextTypeLoader().parse(is, MethodParamPartsMappingType.type, (XmlOptions)null);
      }

      public static MethodParamPartsMappingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MethodParamPartsMappingType)XmlBeans.getContextTypeLoader().parse(is, MethodParamPartsMappingType.type, options);
      }

      public static MethodParamPartsMappingType parse(Reader r) throws XmlException, IOException {
         return (MethodParamPartsMappingType)XmlBeans.getContextTypeLoader().parse(r, MethodParamPartsMappingType.type, (XmlOptions)null);
      }

      public static MethodParamPartsMappingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MethodParamPartsMappingType)XmlBeans.getContextTypeLoader().parse(r, MethodParamPartsMappingType.type, options);
      }

      public static MethodParamPartsMappingType parse(XMLStreamReader sr) throws XmlException {
         return (MethodParamPartsMappingType)XmlBeans.getContextTypeLoader().parse(sr, MethodParamPartsMappingType.type, (XmlOptions)null);
      }

      public static MethodParamPartsMappingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MethodParamPartsMappingType)XmlBeans.getContextTypeLoader().parse(sr, MethodParamPartsMappingType.type, options);
      }

      public static MethodParamPartsMappingType parse(Node node) throws XmlException {
         return (MethodParamPartsMappingType)XmlBeans.getContextTypeLoader().parse(node, MethodParamPartsMappingType.type, (XmlOptions)null);
      }

      public static MethodParamPartsMappingType parse(Node node, XmlOptions options) throws XmlException {
         return (MethodParamPartsMappingType)XmlBeans.getContextTypeLoader().parse(node, MethodParamPartsMappingType.type, options);
      }

      /** @deprecated */
      public static MethodParamPartsMappingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MethodParamPartsMappingType)XmlBeans.getContextTypeLoader().parse(xis, MethodParamPartsMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MethodParamPartsMappingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MethodParamPartsMappingType)XmlBeans.getContextTypeLoader().parse(xis, MethodParamPartsMappingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MethodParamPartsMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MethodParamPartsMappingType.type, options);
      }

      private Factory() {
      }
   }
}
