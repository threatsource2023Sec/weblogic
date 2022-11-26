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

public interface VariableMappingType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(VariableMappingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("variablemappingtypeddd0type");

   String getJavaVariableName();

   void setJavaVariableName(String var1);

   String addNewJavaVariableName();

   EmptyType getDataMember();

   boolean isSetDataMember();

   void setDataMember(EmptyType var1);

   EmptyType addNewDataMember();

   void unsetDataMember();

   String getXmlAttributeName();

   boolean isSetXmlAttributeName();

   void setXmlAttributeName(String var1);

   String addNewXmlAttributeName();

   void unsetXmlAttributeName();

   String getXmlElementName();

   boolean isSetXmlElementName();

   void setXmlElementName(String var1);

   String addNewXmlElementName();

   void unsetXmlElementName();

   EmptyType getXmlWildcard();

   boolean isSetXmlWildcard();

   void setXmlWildcard(EmptyType var1);

   EmptyType addNewXmlWildcard();

   void unsetXmlWildcard();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static VariableMappingType newInstance() {
         return (VariableMappingType)XmlBeans.getContextTypeLoader().newInstance(VariableMappingType.type, (XmlOptions)null);
      }

      public static VariableMappingType newInstance(XmlOptions options) {
         return (VariableMappingType)XmlBeans.getContextTypeLoader().newInstance(VariableMappingType.type, options);
      }

      public static VariableMappingType parse(java.lang.String xmlAsString) throws XmlException {
         return (VariableMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, VariableMappingType.type, (XmlOptions)null);
      }

      public static VariableMappingType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (VariableMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, VariableMappingType.type, options);
      }

      public static VariableMappingType parse(File file) throws XmlException, IOException {
         return (VariableMappingType)XmlBeans.getContextTypeLoader().parse(file, VariableMappingType.type, (XmlOptions)null);
      }

      public static VariableMappingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (VariableMappingType)XmlBeans.getContextTypeLoader().parse(file, VariableMappingType.type, options);
      }

      public static VariableMappingType parse(URL u) throws XmlException, IOException {
         return (VariableMappingType)XmlBeans.getContextTypeLoader().parse(u, VariableMappingType.type, (XmlOptions)null);
      }

      public static VariableMappingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (VariableMappingType)XmlBeans.getContextTypeLoader().parse(u, VariableMappingType.type, options);
      }

      public static VariableMappingType parse(InputStream is) throws XmlException, IOException {
         return (VariableMappingType)XmlBeans.getContextTypeLoader().parse(is, VariableMappingType.type, (XmlOptions)null);
      }

      public static VariableMappingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (VariableMappingType)XmlBeans.getContextTypeLoader().parse(is, VariableMappingType.type, options);
      }

      public static VariableMappingType parse(Reader r) throws XmlException, IOException {
         return (VariableMappingType)XmlBeans.getContextTypeLoader().parse(r, VariableMappingType.type, (XmlOptions)null);
      }

      public static VariableMappingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (VariableMappingType)XmlBeans.getContextTypeLoader().parse(r, VariableMappingType.type, options);
      }

      public static VariableMappingType parse(XMLStreamReader sr) throws XmlException {
         return (VariableMappingType)XmlBeans.getContextTypeLoader().parse(sr, VariableMappingType.type, (XmlOptions)null);
      }

      public static VariableMappingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (VariableMappingType)XmlBeans.getContextTypeLoader().parse(sr, VariableMappingType.type, options);
      }

      public static VariableMappingType parse(Node node) throws XmlException {
         return (VariableMappingType)XmlBeans.getContextTypeLoader().parse(node, VariableMappingType.type, (XmlOptions)null);
      }

      public static VariableMappingType parse(Node node, XmlOptions options) throws XmlException {
         return (VariableMappingType)XmlBeans.getContextTypeLoader().parse(node, VariableMappingType.type, options);
      }

      /** @deprecated */
      public static VariableMappingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (VariableMappingType)XmlBeans.getContextTypeLoader().parse(xis, VariableMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static VariableMappingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (VariableMappingType)XmlBeans.getContextTypeLoader().parse(xis, VariableMappingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, VariableMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, VariableMappingType.type, options);
      }

      private Factory() {
      }
   }
}
