package com.bea.ns.staxb.bindingConfig.x90;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface MappingTable extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MappingTable.class.getClassLoader(), "schemacom_bea_xml.system.bea_staxb_runtime").resolveHandle("mappingtable703atype");

   Mapping[] getMappingArray();

   Mapping getMappingArray(int var1);

   int sizeOfMappingArray();

   void setMappingArray(Mapping[] var1);

   void setMappingArray(int var1, Mapping var2);

   Mapping insertNewMapping(int var1);

   Mapping addNewMapping();

   void removeMapping(int var1);

   public static final class Factory {
      public static MappingTable newInstance() {
         return (MappingTable)XmlBeans.getContextTypeLoader().newInstance(MappingTable.type, (XmlOptions)null);
      }

      public static MappingTable newInstance(XmlOptions options) {
         return (MappingTable)XmlBeans.getContextTypeLoader().newInstance(MappingTable.type, options);
      }

      public static MappingTable parse(String xmlAsString) throws XmlException {
         return (MappingTable)XmlBeans.getContextTypeLoader().parse(xmlAsString, MappingTable.type, (XmlOptions)null);
      }

      public static MappingTable parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (MappingTable)XmlBeans.getContextTypeLoader().parse(xmlAsString, MappingTable.type, options);
      }

      public static MappingTable parse(File file) throws XmlException, IOException {
         return (MappingTable)XmlBeans.getContextTypeLoader().parse(file, MappingTable.type, (XmlOptions)null);
      }

      public static MappingTable parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MappingTable)XmlBeans.getContextTypeLoader().parse(file, MappingTable.type, options);
      }

      public static MappingTable parse(URL u) throws XmlException, IOException {
         return (MappingTable)XmlBeans.getContextTypeLoader().parse(u, MappingTable.type, (XmlOptions)null);
      }

      public static MappingTable parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MappingTable)XmlBeans.getContextTypeLoader().parse(u, MappingTable.type, options);
      }

      public static MappingTable parse(InputStream is) throws XmlException, IOException {
         return (MappingTable)XmlBeans.getContextTypeLoader().parse(is, MappingTable.type, (XmlOptions)null);
      }

      public static MappingTable parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MappingTable)XmlBeans.getContextTypeLoader().parse(is, MappingTable.type, options);
      }

      public static MappingTable parse(Reader r) throws XmlException, IOException {
         return (MappingTable)XmlBeans.getContextTypeLoader().parse(r, MappingTable.type, (XmlOptions)null);
      }

      public static MappingTable parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MappingTable)XmlBeans.getContextTypeLoader().parse(r, MappingTable.type, options);
      }

      public static MappingTable parse(XMLStreamReader sr) throws XmlException {
         return (MappingTable)XmlBeans.getContextTypeLoader().parse(sr, MappingTable.type, (XmlOptions)null);
      }

      public static MappingTable parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MappingTable)XmlBeans.getContextTypeLoader().parse(sr, MappingTable.type, options);
      }

      public static MappingTable parse(Node node) throws XmlException {
         return (MappingTable)XmlBeans.getContextTypeLoader().parse(node, MappingTable.type, (XmlOptions)null);
      }

      public static MappingTable parse(Node node, XmlOptions options) throws XmlException {
         return (MappingTable)XmlBeans.getContextTypeLoader().parse(node, MappingTable.type, options);
      }

      /** @deprecated */
      public static MappingTable parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MappingTable)XmlBeans.getContextTypeLoader().parse(xis, MappingTable.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MappingTable parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MappingTable)XmlBeans.getContextTypeLoader().parse(xis, MappingTable.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MappingTable.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MappingTable.type, options);
      }

      private Factory() {
      }
   }
}
