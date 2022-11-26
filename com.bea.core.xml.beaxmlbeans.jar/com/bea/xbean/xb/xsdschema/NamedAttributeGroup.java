package com.bea.xbean.xb.xsdschema;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlNCName;
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

public interface NamedAttributeGroup extends AttributeGroup {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(NamedAttributeGroup.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("namedattributegroup2e29type");

   String getName();

   XmlNCName xgetName();

   boolean isSetName();

   void setName(String var1);

   void xsetName(XmlNCName var1);

   void unsetName();

   public static final class Factory {
      public static NamedAttributeGroup newInstance() {
         return (NamedAttributeGroup)XmlBeans.getContextTypeLoader().newInstance(NamedAttributeGroup.type, (XmlOptions)null);
      }

      public static NamedAttributeGroup newInstance(XmlOptions options) {
         return (NamedAttributeGroup)XmlBeans.getContextTypeLoader().newInstance(NamedAttributeGroup.type, options);
      }

      public static NamedAttributeGroup parse(String xmlAsString) throws XmlException {
         return (NamedAttributeGroup)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, NamedAttributeGroup.type, (XmlOptions)null);
      }

      public static NamedAttributeGroup parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (NamedAttributeGroup)XmlBeans.getContextTypeLoader().parse(xmlAsString, NamedAttributeGroup.type, options);
      }

      public static NamedAttributeGroup parse(File file) throws XmlException, IOException {
         return (NamedAttributeGroup)XmlBeans.getContextTypeLoader().parse((File)file, NamedAttributeGroup.type, (XmlOptions)null);
      }

      public static NamedAttributeGroup parse(File file, XmlOptions options) throws XmlException, IOException {
         return (NamedAttributeGroup)XmlBeans.getContextTypeLoader().parse(file, NamedAttributeGroup.type, options);
      }

      public static NamedAttributeGroup parse(URL u) throws XmlException, IOException {
         return (NamedAttributeGroup)XmlBeans.getContextTypeLoader().parse((URL)u, NamedAttributeGroup.type, (XmlOptions)null);
      }

      public static NamedAttributeGroup parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (NamedAttributeGroup)XmlBeans.getContextTypeLoader().parse(u, NamedAttributeGroup.type, options);
      }

      public static NamedAttributeGroup parse(InputStream is) throws XmlException, IOException {
         return (NamedAttributeGroup)XmlBeans.getContextTypeLoader().parse((InputStream)is, NamedAttributeGroup.type, (XmlOptions)null);
      }

      public static NamedAttributeGroup parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (NamedAttributeGroup)XmlBeans.getContextTypeLoader().parse(is, NamedAttributeGroup.type, options);
      }

      public static NamedAttributeGroup parse(Reader r) throws XmlException, IOException {
         return (NamedAttributeGroup)XmlBeans.getContextTypeLoader().parse((Reader)r, NamedAttributeGroup.type, (XmlOptions)null);
      }

      public static NamedAttributeGroup parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (NamedAttributeGroup)XmlBeans.getContextTypeLoader().parse(r, NamedAttributeGroup.type, options);
      }

      public static NamedAttributeGroup parse(XMLStreamReader sr) throws XmlException {
         return (NamedAttributeGroup)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, NamedAttributeGroup.type, (XmlOptions)null);
      }

      public static NamedAttributeGroup parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (NamedAttributeGroup)XmlBeans.getContextTypeLoader().parse(sr, NamedAttributeGroup.type, options);
      }

      public static NamedAttributeGroup parse(Node node) throws XmlException {
         return (NamedAttributeGroup)XmlBeans.getContextTypeLoader().parse((Node)node, NamedAttributeGroup.type, (XmlOptions)null);
      }

      public static NamedAttributeGroup parse(Node node, XmlOptions options) throws XmlException {
         return (NamedAttributeGroup)XmlBeans.getContextTypeLoader().parse(node, NamedAttributeGroup.type, options);
      }

      /** @deprecated */
      public static NamedAttributeGroup parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (NamedAttributeGroup)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, NamedAttributeGroup.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static NamedAttributeGroup parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (NamedAttributeGroup)XmlBeans.getContextTypeLoader().parse(xis, NamedAttributeGroup.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, NamedAttributeGroup.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, NamedAttributeGroup.type, options);
      }

      private Factory() {
      }
   }
}
