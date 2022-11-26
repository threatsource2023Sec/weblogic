package com.oracle.xmlns.weblogic.weblogicJms;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface DistributedDestinationMemberType extends NamedEntityType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DistributedDestinationMemberType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("distributeddestinationmembertype0273type");

   int getWeight();

   XmlInt xgetWeight();

   boolean isSetWeight();

   void setWeight(int var1);

   void xsetWeight(XmlInt var1);

   void unsetWeight();

   String getPhysicalDestinationName();

   XmlString xgetPhysicalDestinationName();

   boolean isNilPhysicalDestinationName();

   boolean isSetPhysicalDestinationName();

   void setPhysicalDestinationName(String var1);

   void xsetPhysicalDestinationName(XmlString var1);

   void setNilPhysicalDestinationName();

   void unsetPhysicalDestinationName();

   public static final class Factory {
      public static DistributedDestinationMemberType newInstance() {
         return (DistributedDestinationMemberType)XmlBeans.getContextTypeLoader().newInstance(DistributedDestinationMemberType.type, (XmlOptions)null);
      }

      public static DistributedDestinationMemberType newInstance(XmlOptions options) {
         return (DistributedDestinationMemberType)XmlBeans.getContextTypeLoader().newInstance(DistributedDestinationMemberType.type, options);
      }

      public static DistributedDestinationMemberType parse(String xmlAsString) throws XmlException {
         return (DistributedDestinationMemberType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DistributedDestinationMemberType.type, (XmlOptions)null);
      }

      public static DistributedDestinationMemberType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DistributedDestinationMemberType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DistributedDestinationMemberType.type, options);
      }

      public static DistributedDestinationMemberType parse(File file) throws XmlException, IOException {
         return (DistributedDestinationMemberType)XmlBeans.getContextTypeLoader().parse(file, DistributedDestinationMemberType.type, (XmlOptions)null);
      }

      public static DistributedDestinationMemberType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DistributedDestinationMemberType)XmlBeans.getContextTypeLoader().parse(file, DistributedDestinationMemberType.type, options);
      }

      public static DistributedDestinationMemberType parse(URL u) throws XmlException, IOException {
         return (DistributedDestinationMemberType)XmlBeans.getContextTypeLoader().parse(u, DistributedDestinationMemberType.type, (XmlOptions)null);
      }

      public static DistributedDestinationMemberType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DistributedDestinationMemberType)XmlBeans.getContextTypeLoader().parse(u, DistributedDestinationMemberType.type, options);
      }

      public static DistributedDestinationMemberType parse(InputStream is) throws XmlException, IOException {
         return (DistributedDestinationMemberType)XmlBeans.getContextTypeLoader().parse(is, DistributedDestinationMemberType.type, (XmlOptions)null);
      }

      public static DistributedDestinationMemberType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DistributedDestinationMemberType)XmlBeans.getContextTypeLoader().parse(is, DistributedDestinationMemberType.type, options);
      }

      public static DistributedDestinationMemberType parse(Reader r) throws XmlException, IOException {
         return (DistributedDestinationMemberType)XmlBeans.getContextTypeLoader().parse(r, DistributedDestinationMemberType.type, (XmlOptions)null);
      }

      public static DistributedDestinationMemberType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DistributedDestinationMemberType)XmlBeans.getContextTypeLoader().parse(r, DistributedDestinationMemberType.type, options);
      }

      public static DistributedDestinationMemberType parse(XMLStreamReader sr) throws XmlException {
         return (DistributedDestinationMemberType)XmlBeans.getContextTypeLoader().parse(sr, DistributedDestinationMemberType.type, (XmlOptions)null);
      }

      public static DistributedDestinationMemberType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DistributedDestinationMemberType)XmlBeans.getContextTypeLoader().parse(sr, DistributedDestinationMemberType.type, options);
      }

      public static DistributedDestinationMemberType parse(Node node) throws XmlException {
         return (DistributedDestinationMemberType)XmlBeans.getContextTypeLoader().parse(node, DistributedDestinationMemberType.type, (XmlOptions)null);
      }

      public static DistributedDestinationMemberType parse(Node node, XmlOptions options) throws XmlException {
         return (DistributedDestinationMemberType)XmlBeans.getContextTypeLoader().parse(node, DistributedDestinationMemberType.type, options);
      }

      /** @deprecated */
      public static DistributedDestinationMemberType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DistributedDestinationMemberType)XmlBeans.getContextTypeLoader().parse(xis, DistributedDestinationMemberType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DistributedDestinationMemberType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DistributedDestinationMemberType)XmlBeans.getContextTypeLoader().parse(xis, DistributedDestinationMemberType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DistributedDestinationMemberType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DistributedDestinationMemberType.type, options);
      }

      private Factory() {
      }
   }
}
