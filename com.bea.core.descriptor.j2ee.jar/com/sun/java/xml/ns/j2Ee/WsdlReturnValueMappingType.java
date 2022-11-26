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

public interface WsdlReturnValueMappingType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WsdlReturnValueMappingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("wsdlreturnvaluemappingtypef57ftype");

   FullyQualifiedClassType getMethodReturnValue();

   void setMethodReturnValue(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewMethodReturnValue();

   WsdlMessageType getWsdlMessage();

   void setWsdlMessage(WsdlMessageType var1);

   WsdlMessageType addNewWsdlMessage();

   WsdlMessagePartNameType getWsdlMessagePartName();

   boolean isSetWsdlMessagePartName();

   void setWsdlMessagePartName(WsdlMessagePartNameType var1);

   WsdlMessagePartNameType addNewWsdlMessagePartName();

   void unsetWsdlMessagePartName();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static WsdlReturnValueMappingType newInstance() {
         return (WsdlReturnValueMappingType)XmlBeans.getContextTypeLoader().newInstance(WsdlReturnValueMappingType.type, (XmlOptions)null);
      }

      public static WsdlReturnValueMappingType newInstance(XmlOptions options) {
         return (WsdlReturnValueMappingType)XmlBeans.getContextTypeLoader().newInstance(WsdlReturnValueMappingType.type, options);
      }

      public static WsdlReturnValueMappingType parse(java.lang.String xmlAsString) throws XmlException {
         return (WsdlReturnValueMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WsdlReturnValueMappingType.type, (XmlOptions)null);
      }

      public static WsdlReturnValueMappingType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (WsdlReturnValueMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WsdlReturnValueMappingType.type, options);
      }

      public static WsdlReturnValueMappingType parse(File file) throws XmlException, IOException {
         return (WsdlReturnValueMappingType)XmlBeans.getContextTypeLoader().parse(file, WsdlReturnValueMappingType.type, (XmlOptions)null);
      }

      public static WsdlReturnValueMappingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WsdlReturnValueMappingType)XmlBeans.getContextTypeLoader().parse(file, WsdlReturnValueMappingType.type, options);
      }

      public static WsdlReturnValueMappingType parse(URL u) throws XmlException, IOException {
         return (WsdlReturnValueMappingType)XmlBeans.getContextTypeLoader().parse(u, WsdlReturnValueMappingType.type, (XmlOptions)null);
      }

      public static WsdlReturnValueMappingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WsdlReturnValueMappingType)XmlBeans.getContextTypeLoader().parse(u, WsdlReturnValueMappingType.type, options);
      }

      public static WsdlReturnValueMappingType parse(InputStream is) throws XmlException, IOException {
         return (WsdlReturnValueMappingType)XmlBeans.getContextTypeLoader().parse(is, WsdlReturnValueMappingType.type, (XmlOptions)null);
      }

      public static WsdlReturnValueMappingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WsdlReturnValueMappingType)XmlBeans.getContextTypeLoader().parse(is, WsdlReturnValueMappingType.type, options);
      }

      public static WsdlReturnValueMappingType parse(Reader r) throws XmlException, IOException {
         return (WsdlReturnValueMappingType)XmlBeans.getContextTypeLoader().parse(r, WsdlReturnValueMappingType.type, (XmlOptions)null);
      }

      public static WsdlReturnValueMappingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WsdlReturnValueMappingType)XmlBeans.getContextTypeLoader().parse(r, WsdlReturnValueMappingType.type, options);
      }

      public static WsdlReturnValueMappingType parse(XMLStreamReader sr) throws XmlException {
         return (WsdlReturnValueMappingType)XmlBeans.getContextTypeLoader().parse(sr, WsdlReturnValueMappingType.type, (XmlOptions)null);
      }

      public static WsdlReturnValueMappingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WsdlReturnValueMappingType)XmlBeans.getContextTypeLoader().parse(sr, WsdlReturnValueMappingType.type, options);
      }

      public static WsdlReturnValueMappingType parse(Node node) throws XmlException {
         return (WsdlReturnValueMappingType)XmlBeans.getContextTypeLoader().parse(node, WsdlReturnValueMappingType.type, (XmlOptions)null);
      }

      public static WsdlReturnValueMappingType parse(Node node, XmlOptions options) throws XmlException {
         return (WsdlReturnValueMappingType)XmlBeans.getContextTypeLoader().parse(node, WsdlReturnValueMappingType.type, options);
      }

      /** @deprecated */
      public static WsdlReturnValueMappingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WsdlReturnValueMappingType)XmlBeans.getContextTypeLoader().parse(xis, WsdlReturnValueMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WsdlReturnValueMappingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WsdlReturnValueMappingType)XmlBeans.getContextTypeLoader().parse(xis, WsdlReturnValueMappingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WsdlReturnValueMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WsdlReturnValueMappingType.type, options);
      }

      private Factory() {
      }
   }
}
