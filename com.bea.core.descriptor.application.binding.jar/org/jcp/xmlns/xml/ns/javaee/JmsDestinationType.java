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

public interface JmsDestinationType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JmsDestinationType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("jmsdestinationtype564ctype");

   DescriptionType getDescription();

   boolean isSetDescription();

   void setDescription(DescriptionType var1);

   DescriptionType addNewDescription();

   void unsetDescription();

   JndiNameType getName();

   void setName(JndiNameType var1);

   JndiNameType addNewName();

   FullyQualifiedClassType getInterfaceName();

   void setInterfaceName(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewInterfaceName();

   FullyQualifiedClassType getClassName();

   boolean isSetClassName();

   void setClassName(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewClassName();

   void unsetClassName();

   String getResourceAdapter();

   boolean isSetResourceAdapter();

   void setResourceAdapter(String var1);

   String addNewResourceAdapter();

   void unsetResourceAdapter();

   String getDestinationName();

   boolean isSetDestinationName();

   void setDestinationName(String var1);

   String addNewDestinationName();

   void unsetDestinationName();

   PropertyType[] getPropertyArray();

   PropertyType getPropertyArray(int var1);

   int sizeOfPropertyArray();

   void setPropertyArray(PropertyType[] var1);

   void setPropertyArray(int var1, PropertyType var2);

   PropertyType insertNewProperty(int var1);

   PropertyType addNewProperty();

   void removeProperty(int var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static JmsDestinationType newInstance() {
         return (JmsDestinationType)XmlBeans.getContextTypeLoader().newInstance(JmsDestinationType.type, (XmlOptions)null);
      }

      public static JmsDestinationType newInstance(XmlOptions options) {
         return (JmsDestinationType)XmlBeans.getContextTypeLoader().newInstance(JmsDestinationType.type, options);
      }

      public static JmsDestinationType parse(java.lang.String xmlAsString) throws XmlException {
         return (JmsDestinationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JmsDestinationType.type, (XmlOptions)null);
      }

      public static JmsDestinationType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (JmsDestinationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JmsDestinationType.type, options);
      }

      public static JmsDestinationType parse(File file) throws XmlException, IOException {
         return (JmsDestinationType)XmlBeans.getContextTypeLoader().parse(file, JmsDestinationType.type, (XmlOptions)null);
      }

      public static JmsDestinationType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (JmsDestinationType)XmlBeans.getContextTypeLoader().parse(file, JmsDestinationType.type, options);
      }

      public static JmsDestinationType parse(URL u) throws XmlException, IOException {
         return (JmsDestinationType)XmlBeans.getContextTypeLoader().parse(u, JmsDestinationType.type, (XmlOptions)null);
      }

      public static JmsDestinationType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (JmsDestinationType)XmlBeans.getContextTypeLoader().parse(u, JmsDestinationType.type, options);
      }

      public static JmsDestinationType parse(InputStream is) throws XmlException, IOException {
         return (JmsDestinationType)XmlBeans.getContextTypeLoader().parse(is, JmsDestinationType.type, (XmlOptions)null);
      }

      public static JmsDestinationType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (JmsDestinationType)XmlBeans.getContextTypeLoader().parse(is, JmsDestinationType.type, options);
      }

      public static JmsDestinationType parse(Reader r) throws XmlException, IOException {
         return (JmsDestinationType)XmlBeans.getContextTypeLoader().parse(r, JmsDestinationType.type, (XmlOptions)null);
      }

      public static JmsDestinationType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (JmsDestinationType)XmlBeans.getContextTypeLoader().parse(r, JmsDestinationType.type, options);
      }

      public static JmsDestinationType parse(XMLStreamReader sr) throws XmlException {
         return (JmsDestinationType)XmlBeans.getContextTypeLoader().parse(sr, JmsDestinationType.type, (XmlOptions)null);
      }

      public static JmsDestinationType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (JmsDestinationType)XmlBeans.getContextTypeLoader().parse(sr, JmsDestinationType.type, options);
      }

      public static JmsDestinationType parse(Node node) throws XmlException {
         return (JmsDestinationType)XmlBeans.getContextTypeLoader().parse(node, JmsDestinationType.type, (XmlOptions)null);
      }

      public static JmsDestinationType parse(Node node, XmlOptions options) throws XmlException {
         return (JmsDestinationType)XmlBeans.getContextTypeLoader().parse(node, JmsDestinationType.type, options);
      }

      /** @deprecated */
      public static JmsDestinationType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (JmsDestinationType)XmlBeans.getContextTypeLoader().parse(xis, JmsDestinationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static JmsDestinationType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (JmsDestinationType)XmlBeans.getContextTypeLoader().parse(xis, JmsDestinationType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JmsDestinationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JmsDestinationType.type, options);
      }

      private Factory() {
      }
   }
}
