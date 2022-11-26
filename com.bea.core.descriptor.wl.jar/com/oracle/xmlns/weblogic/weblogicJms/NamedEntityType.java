package com.oracle.xmlns.weblogic.weblogicJms;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlLong;
import com.bea.xml.XmlObject;
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

public interface NamedEntityType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(NamedEntityType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("namedentitytypea15ftype");

   String getNotes();

   XmlString xgetNotes();

   boolean isNilNotes();

   boolean isSetNotes();

   void setNotes(String var1);

   void xsetNotes(XmlString var1);

   void setNilNotes();

   void unsetNotes();

   long getId();

   XmlLong xgetId();

   boolean isSetId();

   void setId(long var1);

   void xsetId(XmlLong var1);

   void unsetId();

   String getName();

   XmlString xgetName();

   void setName(String var1);

   void xsetName(XmlString var1);

   public static final class Factory {
      public static NamedEntityType newInstance() {
         return (NamedEntityType)XmlBeans.getContextTypeLoader().newInstance(NamedEntityType.type, (XmlOptions)null);
      }

      public static NamedEntityType newInstance(XmlOptions options) {
         return (NamedEntityType)XmlBeans.getContextTypeLoader().newInstance(NamedEntityType.type, options);
      }

      public static NamedEntityType parse(String xmlAsString) throws XmlException {
         return (NamedEntityType)XmlBeans.getContextTypeLoader().parse(xmlAsString, NamedEntityType.type, (XmlOptions)null);
      }

      public static NamedEntityType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (NamedEntityType)XmlBeans.getContextTypeLoader().parse(xmlAsString, NamedEntityType.type, options);
      }

      public static NamedEntityType parse(File file) throws XmlException, IOException {
         return (NamedEntityType)XmlBeans.getContextTypeLoader().parse(file, NamedEntityType.type, (XmlOptions)null);
      }

      public static NamedEntityType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (NamedEntityType)XmlBeans.getContextTypeLoader().parse(file, NamedEntityType.type, options);
      }

      public static NamedEntityType parse(URL u) throws XmlException, IOException {
         return (NamedEntityType)XmlBeans.getContextTypeLoader().parse(u, NamedEntityType.type, (XmlOptions)null);
      }

      public static NamedEntityType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (NamedEntityType)XmlBeans.getContextTypeLoader().parse(u, NamedEntityType.type, options);
      }

      public static NamedEntityType parse(InputStream is) throws XmlException, IOException {
         return (NamedEntityType)XmlBeans.getContextTypeLoader().parse(is, NamedEntityType.type, (XmlOptions)null);
      }

      public static NamedEntityType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (NamedEntityType)XmlBeans.getContextTypeLoader().parse(is, NamedEntityType.type, options);
      }

      public static NamedEntityType parse(Reader r) throws XmlException, IOException {
         return (NamedEntityType)XmlBeans.getContextTypeLoader().parse(r, NamedEntityType.type, (XmlOptions)null);
      }

      public static NamedEntityType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (NamedEntityType)XmlBeans.getContextTypeLoader().parse(r, NamedEntityType.type, options);
      }

      public static NamedEntityType parse(XMLStreamReader sr) throws XmlException {
         return (NamedEntityType)XmlBeans.getContextTypeLoader().parse(sr, NamedEntityType.type, (XmlOptions)null);
      }

      public static NamedEntityType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (NamedEntityType)XmlBeans.getContextTypeLoader().parse(sr, NamedEntityType.type, options);
      }

      public static NamedEntityType parse(Node node) throws XmlException {
         return (NamedEntityType)XmlBeans.getContextTypeLoader().parse(node, NamedEntityType.type, (XmlOptions)null);
      }

      public static NamedEntityType parse(Node node, XmlOptions options) throws XmlException {
         return (NamedEntityType)XmlBeans.getContextTypeLoader().parse(node, NamedEntityType.type, options);
      }

      /** @deprecated */
      public static NamedEntityType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (NamedEntityType)XmlBeans.getContextTypeLoader().parse(xis, NamedEntityType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static NamedEntityType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (NamedEntityType)XmlBeans.getContextTypeLoader().parse(xis, NamedEntityType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, NamedEntityType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, NamedEntityType.type, options);
      }

      private Factory() {
      }
   }
}
