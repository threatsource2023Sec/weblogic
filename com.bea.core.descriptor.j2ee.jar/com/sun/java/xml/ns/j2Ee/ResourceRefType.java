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

public interface ResourceRefType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ResourceRefType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("resourcereftype02a7type");

   DescriptionType[] getDescriptionArray();

   DescriptionType getDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(DescriptionType[] var1);

   void setDescriptionArray(int var1, DescriptionType var2);

   DescriptionType insertNewDescription(int var1);

   DescriptionType addNewDescription();

   void removeDescription(int var1);

   JndiNameType getResRefName();

   void setResRefName(JndiNameType var1);

   JndiNameType addNewResRefName();

   FullyQualifiedClassType getResType();

   void setResType(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewResType();

   ResAuthType getResAuth();

   void setResAuth(ResAuthType var1);

   ResAuthType addNewResAuth();

   ResSharingScopeType getResSharingScope();

   boolean isSetResSharingScope();

   void setResSharingScope(ResSharingScopeType var1);

   ResSharingScopeType addNewResSharingScope();

   void unsetResSharingScope();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ResourceRefType newInstance() {
         return (ResourceRefType)XmlBeans.getContextTypeLoader().newInstance(ResourceRefType.type, (XmlOptions)null);
      }

      public static ResourceRefType newInstance(XmlOptions options) {
         return (ResourceRefType)XmlBeans.getContextTypeLoader().newInstance(ResourceRefType.type, options);
      }

      public static ResourceRefType parse(java.lang.String xmlAsString) throws XmlException {
         return (ResourceRefType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ResourceRefType.type, (XmlOptions)null);
      }

      public static ResourceRefType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ResourceRefType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ResourceRefType.type, options);
      }

      public static ResourceRefType parse(File file) throws XmlException, IOException {
         return (ResourceRefType)XmlBeans.getContextTypeLoader().parse(file, ResourceRefType.type, (XmlOptions)null);
      }

      public static ResourceRefType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ResourceRefType)XmlBeans.getContextTypeLoader().parse(file, ResourceRefType.type, options);
      }

      public static ResourceRefType parse(URL u) throws XmlException, IOException {
         return (ResourceRefType)XmlBeans.getContextTypeLoader().parse(u, ResourceRefType.type, (XmlOptions)null);
      }

      public static ResourceRefType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ResourceRefType)XmlBeans.getContextTypeLoader().parse(u, ResourceRefType.type, options);
      }

      public static ResourceRefType parse(InputStream is) throws XmlException, IOException {
         return (ResourceRefType)XmlBeans.getContextTypeLoader().parse(is, ResourceRefType.type, (XmlOptions)null);
      }

      public static ResourceRefType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ResourceRefType)XmlBeans.getContextTypeLoader().parse(is, ResourceRefType.type, options);
      }

      public static ResourceRefType parse(Reader r) throws XmlException, IOException {
         return (ResourceRefType)XmlBeans.getContextTypeLoader().parse(r, ResourceRefType.type, (XmlOptions)null);
      }

      public static ResourceRefType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ResourceRefType)XmlBeans.getContextTypeLoader().parse(r, ResourceRefType.type, options);
      }

      public static ResourceRefType parse(XMLStreamReader sr) throws XmlException {
         return (ResourceRefType)XmlBeans.getContextTypeLoader().parse(sr, ResourceRefType.type, (XmlOptions)null);
      }

      public static ResourceRefType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ResourceRefType)XmlBeans.getContextTypeLoader().parse(sr, ResourceRefType.type, options);
      }

      public static ResourceRefType parse(Node node) throws XmlException {
         return (ResourceRefType)XmlBeans.getContextTypeLoader().parse(node, ResourceRefType.type, (XmlOptions)null);
      }

      public static ResourceRefType parse(Node node, XmlOptions options) throws XmlException {
         return (ResourceRefType)XmlBeans.getContextTypeLoader().parse(node, ResourceRefType.type, options);
      }

      /** @deprecated */
      public static ResourceRefType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ResourceRefType)XmlBeans.getContextTypeLoader().parse(xis, ResourceRefType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ResourceRefType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ResourceRefType)XmlBeans.getContextTypeLoader().parse(xis, ResourceRefType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ResourceRefType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ResourceRefType.type, options);
      }

      private Factory() {
      }
   }
}
