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

public interface JavaXmlTypeMappingType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JavaXmlTypeMappingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("javaxmltypemappingtype991dtype");

   JavaTypeType getJavaType();

   void setJavaType(JavaTypeType var1);

   JavaTypeType addNewJavaType();

   XsdQNameType getRootTypeQname();

   boolean isSetRootTypeQname();

   void setRootTypeQname(XsdQNameType var1);

   XsdQNameType addNewRootTypeQname();

   void unsetRootTypeQname();

   String getAnonymousTypeQname();

   boolean isSetAnonymousTypeQname();

   void setAnonymousTypeQname(String var1);

   String addNewAnonymousTypeQname();

   void unsetAnonymousTypeQname();

   QnameScopeType getQnameScope();

   void setQnameScope(QnameScopeType var1);

   QnameScopeType addNewQnameScope();

   VariableMappingType[] getVariableMappingArray();

   VariableMappingType getVariableMappingArray(int var1);

   int sizeOfVariableMappingArray();

   void setVariableMappingArray(VariableMappingType[] var1);

   void setVariableMappingArray(int var1, VariableMappingType var2);

   VariableMappingType insertNewVariableMapping(int var1);

   VariableMappingType addNewVariableMapping();

   void removeVariableMapping(int var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static JavaXmlTypeMappingType newInstance() {
         return (JavaXmlTypeMappingType)XmlBeans.getContextTypeLoader().newInstance(JavaXmlTypeMappingType.type, (XmlOptions)null);
      }

      public static JavaXmlTypeMappingType newInstance(XmlOptions options) {
         return (JavaXmlTypeMappingType)XmlBeans.getContextTypeLoader().newInstance(JavaXmlTypeMappingType.type, options);
      }

      public static JavaXmlTypeMappingType parse(java.lang.String xmlAsString) throws XmlException {
         return (JavaXmlTypeMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JavaXmlTypeMappingType.type, (XmlOptions)null);
      }

      public static JavaXmlTypeMappingType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (JavaXmlTypeMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JavaXmlTypeMappingType.type, options);
      }

      public static JavaXmlTypeMappingType parse(File file) throws XmlException, IOException {
         return (JavaXmlTypeMappingType)XmlBeans.getContextTypeLoader().parse(file, JavaXmlTypeMappingType.type, (XmlOptions)null);
      }

      public static JavaXmlTypeMappingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (JavaXmlTypeMappingType)XmlBeans.getContextTypeLoader().parse(file, JavaXmlTypeMappingType.type, options);
      }

      public static JavaXmlTypeMappingType parse(URL u) throws XmlException, IOException {
         return (JavaXmlTypeMappingType)XmlBeans.getContextTypeLoader().parse(u, JavaXmlTypeMappingType.type, (XmlOptions)null);
      }

      public static JavaXmlTypeMappingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (JavaXmlTypeMappingType)XmlBeans.getContextTypeLoader().parse(u, JavaXmlTypeMappingType.type, options);
      }

      public static JavaXmlTypeMappingType parse(InputStream is) throws XmlException, IOException {
         return (JavaXmlTypeMappingType)XmlBeans.getContextTypeLoader().parse(is, JavaXmlTypeMappingType.type, (XmlOptions)null);
      }

      public static JavaXmlTypeMappingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (JavaXmlTypeMappingType)XmlBeans.getContextTypeLoader().parse(is, JavaXmlTypeMappingType.type, options);
      }

      public static JavaXmlTypeMappingType parse(Reader r) throws XmlException, IOException {
         return (JavaXmlTypeMappingType)XmlBeans.getContextTypeLoader().parse(r, JavaXmlTypeMappingType.type, (XmlOptions)null);
      }

      public static JavaXmlTypeMappingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (JavaXmlTypeMappingType)XmlBeans.getContextTypeLoader().parse(r, JavaXmlTypeMappingType.type, options);
      }

      public static JavaXmlTypeMappingType parse(XMLStreamReader sr) throws XmlException {
         return (JavaXmlTypeMappingType)XmlBeans.getContextTypeLoader().parse(sr, JavaXmlTypeMappingType.type, (XmlOptions)null);
      }

      public static JavaXmlTypeMappingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (JavaXmlTypeMappingType)XmlBeans.getContextTypeLoader().parse(sr, JavaXmlTypeMappingType.type, options);
      }

      public static JavaXmlTypeMappingType parse(Node node) throws XmlException {
         return (JavaXmlTypeMappingType)XmlBeans.getContextTypeLoader().parse(node, JavaXmlTypeMappingType.type, (XmlOptions)null);
      }

      public static JavaXmlTypeMappingType parse(Node node, XmlOptions options) throws XmlException {
         return (JavaXmlTypeMappingType)XmlBeans.getContextTypeLoader().parse(node, JavaXmlTypeMappingType.type, options);
      }

      /** @deprecated */
      public static JavaXmlTypeMappingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (JavaXmlTypeMappingType)XmlBeans.getContextTypeLoader().parse(xis, JavaXmlTypeMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static JavaXmlTypeMappingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (JavaXmlTypeMappingType)XmlBeans.getContextTypeLoader().parse(xis, JavaXmlTypeMappingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JavaXmlTypeMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JavaXmlTypeMappingType.type, options);
      }

      private Factory() {
      }
   }
}
