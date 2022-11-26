package com.sun.java.xml.ns.javaee;

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

public interface SecurityIdentityType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SecurityIdentityType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("securityidentitytypee3e6type");

   DescriptionType[] getDescriptionArray();

   DescriptionType getDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(DescriptionType[] var1);

   void setDescriptionArray(int var1, DescriptionType var2);

   DescriptionType insertNewDescription(int var1);

   DescriptionType addNewDescription();

   void removeDescription(int var1);

   EmptyType getUseCallerIdentity();

   boolean isSetUseCallerIdentity();

   void setUseCallerIdentity(EmptyType var1);

   EmptyType addNewUseCallerIdentity();

   void unsetUseCallerIdentity();

   RunAsType getRunAs();

   boolean isSetRunAs();

   void setRunAs(RunAsType var1);

   RunAsType addNewRunAs();

   void unsetRunAs();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static SecurityIdentityType newInstance() {
         return (SecurityIdentityType)XmlBeans.getContextTypeLoader().newInstance(SecurityIdentityType.type, (XmlOptions)null);
      }

      public static SecurityIdentityType newInstance(XmlOptions options) {
         return (SecurityIdentityType)XmlBeans.getContextTypeLoader().newInstance(SecurityIdentityType.type, options);
      }

      public static SecurityIdentityType parse(java.lang.String xmlAsString) throws XmlException {
         return (SecurityIdentityType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SecurityIdentityType.type, (XmlOptions)null);
      }

      public static SecurityIdentityType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (SecurityIdentityType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SecurityIdentityType.type, options);
      }

      public static SecurityIdentityType parse(File file) throws XmlException, IOException {
         return (SecurityIdentityType)XmlBeans.getContextTypeLoader().parse(file, SecurityIdentityType.type, (XmlOptions)null);
      }

      public static SecurityIdentityType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SecurityIdentityType)XmlBeans.getContextTypeLoader().parse(file, SecurityIdentityType.type, options);
      }

      public static SecurityIdentityType parse(URL u) throws XmlException, IOException {
         return (SecurityIdentityType)XmlBeans.getContextTypeLoader().parse(u, SecurityIdentityType.type, (XmlOptions)null);
      }

      public static SecurityIdentityType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SecurityIdentityType)XmlBeans.getContextTypeLoader().parse(u, SecurityIdentityType.type, options);
      }

      public static SecurityIdentityType parse(InputStream is) throws XmlException, IOException {
         return (SecurityIdentityType)XmlBeans.getContextTypeLoader().parse(is, SecurityIdentityType.type, (XmlOptions)null);
      }

      public static SecurityIdentityType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SecurityIdentityType)XmlBeans.getContextTypeLoader().parse(is, SecurityIdentityType.type, options);
      }

      public static SecurityIdentityType parse(Reader r) throws XmlException, IOException {
         return (SecurityIdentityType)XmlBeans.getContextTypeLoader().parse(r, SecurityIdentityType.type, (XmlOptions)null);
      }

      public static SecurityIdentityType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SecurityIdentityType)XmlBeans.getContextTypeLoader().parse(r, SecurityIdentityType.type, options);
      }

      public static SecurityIdentityType parse(XMLStreamReader sr) throws XmlException {
         return (SecurityIdentityType)XmlBeans.getContextTypeLoader().parse(sr, SecurityIdentityType.type, (XmlOptions)null);
      }

      public static SecurityIdentityType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SecurityIdentityType)XmlBeans.getContextTypeLoader().parse(sr, SecurityIdentityType.type, options);
      }

      public static SecurityIdentityType parse(Node node) throws XmlException {
         return (SecurityIdentityType)XmlBeans.getContextTypeLoader().parse(node, SecurityIdentityType.type, (XmlOptions)null);
      }

      public static SecurityIdentityType parse(Node node, XmlOptions options) throws XmlException {
         return (SecurityIdentityType)XmlBeans.getContextTypeLoader().parse(node, SecurityIdentityType.type, options);
      }

      /** @deprecated */
      public static SecurityIdentityType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SecurityIdentityType)XmlBeans.getContextTypeLoader().parse(xis, SecurityIdentityType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SecurityIdentityType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SecurityIdentityType)XmlBeans.getContextTypeLoader().parse(xis, SecurityIdentityType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SecurityIdentityType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SecurityIdentityType.type, options);
      }

      private Factory() {
      }
   }
}
