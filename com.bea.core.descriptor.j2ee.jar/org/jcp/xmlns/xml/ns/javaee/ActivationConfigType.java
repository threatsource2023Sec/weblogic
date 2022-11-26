package org.jcp.xmlns.xml.ns.javaee;

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

public interface ActivationConfigType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ActivationConfigType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("activationconfigtypeb884type");

   DescriptionType[] getDescriptionArray();

   DescriptionType getDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(DescriptionType[] var1);

   void setDescriptionArray(int var1, DescriptionType var2);

   DescriptionType insertNewDescription(int var1);

   DescriptionType addNewDescription();

   void removeDescription(int var1);

   ActivationConfigPropertyType[] getActivationConfigPropertyArray();

   ActivationConfigPropertyType getActivationConfigPropertyArray(int var1);

   int sizeOfActivationConfigPropertyArray();

   void setActivationConfigPropertyArray(ActivationConfigPropertyType[] var1);

   void setActivationConfigPropertyArray(int var1, ActivationConfigPropertyType var2);

   ActivationConfigPropertyType insertNewActivationConfigProperty(int var1);

   ActivationConfigPropertyType addNewActivationConfigProperty();

   void removeActivationConfigProperty(int var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ActivationConfigType newInstance() {
         return (ActivationConfigType)XmlBeans.getContextTypeLoader().newInstance(ActivationConfigType.type, (XmlOptions)null);
      }

      public static ActivationConfigType newInstance(XmlOptions options) {
         return (ActivationConfigType)XmlBeans.getContextTypeLoader().newInstance(ActivationConfigType.type, options);
      }

      public static ActivationConfigType parse(java.lang.String xmlAsString) throws XmlException {
         return (ActivationConfigType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ActivationConfigType.type, (XmlOptions)null);
      }

      public static ActivationConfigType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ActivationConfigType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ActivationConfigType.type, options);
      }

      public static ActivationConfigType parse(File file) throws XmlException, IOException {
         return (ActivationConfigType)XmlBeans.getContextTypeLoader().parse(file, ActivationConfigType.type, (XmlOptions)null);
      }

      public static ActivationConfigType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ActivationConfigType)XmlBeans.getContextTypeLoader().parse(file, ActivationConfigType.type, options);
      }

      public static ActivationConfigType parse(URL u) throws XmlException, IOException {
         return (ActivationConfigType)XmlBeans.getContextTypeLoader().parse(u, ActivationConfigType.type, (XmlOptions)null);
      }

      public static ActivationConfigType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ActivationConfigType)XmlBeans.getContextTypeLoader().parse(u, ActivationConfigType.type, options);
      }

      public static ActivationConfigType parse(InputStream is) throws XmlException, IOException {
         return (ActivationConfigType)XmlBeans.getContextTypeLoader().parse(is, ActivationConfigType.type, (XmlOptions)null);
      }

      public static ActivationConfigType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ActivationConfigType)XmlBeans.getContextTypeLoader().parse(is, ActivationConfigType.type, options);
      }

      public static ActivationConfigType parse(Reader r) throws XmlException, IOException {
         return (ActivationConfigType)XmlBeans.getContextTypeLoader().parse(r, ActivationConfigType.type, (XmlOptions)null);
      }

      public static ActivationConfigType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ActivationConfigType)XmlBeans.getContextTypeLoader().parse(r, ActivationConfigType.type, options);
      }

      public static ActivationConfigType parse(XMLStreamReader sr) throws XmlException {
         return (ActivationConfigType)XmlBeans.getContextTypeLoader().parse(sr, ActivationConfigType.type, (XmlOptions)null);
      }

      public static ActivationConfigType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ActivationConfigType)XmlBeans.getContextTypeLoader().parse(sr, ActivationConfigType.type, options);
      }

      public static ActivationConfigType parse(Node node) throws XmlException {
         return (ActivationConfigType)XmlBeans.getContextTypeLoader().parse(node, ActivationConfigType.type, (XmlOptions)null);
      }

      public static ActivationConfigType parse(Node node, XmlOptions options) throws XmlException {
         return (ActivationConfigType)XmlBeans.getContextTypeLoader().parse(node, ActivationConfigType.type, options);
      }

      /** @deprecated */
      public static ActivationConfigType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ActivationConfigType)XmlBeans.getContextTypeLoader().parse(xis, ActivationConfigType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ActivationConfigType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ActivationConfigType)XmlBeans.getContextTypeLoader().parse(xis, ActivationConfigType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ActivationConfigType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ActivationConfigType.type, options);
      }

      private Factory() {
      }
   }
}
