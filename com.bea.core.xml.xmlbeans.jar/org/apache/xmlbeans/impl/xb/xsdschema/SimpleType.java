package org.apache.xmlbeans.impl.xb.xsdschema;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlBeans;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlNCName;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.apache.xmlbeans.xml.stream.XMLStreamException;
import org.w3c.dom.Node;

public interface SimpleType extends Annotated {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SimpleType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("simpletype0707type");

   RestrictionDocument.Restriction getRestriction();

   boolean isSetRestriction();

   void setRestriction(RestrictionDocument.Restriction var1);

   RestrictionDocument.Restriction addNewRestriction();

   void unsetRestriction();

   ListDocument.List getList();

   boolean isSetList();

   void setList(ListDocument.List var1);

   ListDocument.List addNewList();

   void unsetList();

   UnionDocument.Union getUnion();

   boolean isSetUnion();

   void setUnion(UnionDocument.Union var1);

   UnionDocument.Union addNewUnion();

   void unsetUnion();

   Object getFinal();

   SimpleDerivationSet xgetFinal();

   boolean isSetFinal();

   void setFinal(Object var1);

   void xsetFinal(SimpleDerivationSet var1);

   void unsetFinal();

   String getName();

   XmlNCName xgetName();

   boolean isSetName();

   void setName(String var1);

   void xsetName(XmlNCName var1);

   void unsetName();

   public static final class Factory {
      /** @deprecated */
      public static SimpleType newInstance() {
         return (SimpleType)XmlBeans.getContextTypeLoader().newInstance(SimpleType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SimpleType newInstance(XmlOptions options) {
         return (SimpleType)XmlBeans.getContextTypeLoader().newInstance(SimpleType.type, options);
      }

      public static SimpleType parse(String xmlAsString) throws XmlException {
         return (SimpleType)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, SimpleType.type, (XmlOptions)null);
      }

      public static SimpleType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SimpleType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SimpleType.type, options);
      }

      public static SimpleType parse(File file) throws XmlException, IOException {
         return (SimpleType)XmlBeans.getContextTypeLoader().parse((File)file, SimpleType.type, (XmlOptions)null);
      }

      public static SimpleType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SimpleType)XmlBeans.getContextTypeLoader().parse(file, SimpleType.type, options);
      }

      public static SimpleType parse(URL u) throws XmlException, IOException {
         return (SimpleType)XmlBeans.getContextTypeLoader().parse((URL)u, SimpleType.type, (XmlOptions)null);
      }

      public static SimpleType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SimpleType)XmlBeans.getContextTypeLoader().parse(u, SimpleType.type, options);
      }

      public static SimpleType parse(InputStream is) throws XmlException, IOException {
         return (SimpleType)XmlBeans.getContextTypeLoader().parse((InputStream)is, SimpleType.type, (XmlOptions)null);
      }

      public static SimpleType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SimpleType)XmlBeans.getContextTypeLoader().parse(is, SimpleType.type, options);
      }

      public static SimpleType parse(Reader r) throws XmlException, IOException {
         return (SimpleType)XmlBeans.getContextTypeLoader().parse((Reader)r, SimpleType.type, (XmlOptions)null);
      }

      public static SimpleType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SimpleType)XmlBeans.getContextTypeLoader().parse(r, SimpleType.type, options);
      }

      public static SimpleType parse(XMLStreamReader sr) throws XmlException {
         return (SimpleType)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, SimpleType.type, (XmlOptions)null);
      }

      public static SimpleType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SimpleType)XmlBeans.getContextTypeLoader().parse(sr, SimpleType.type, options);
      }

      public static SimpleType parse(Node node) throws XmlException {
         return (SimpleType)XmlBeans.getContextTypeLoader().parse((Node)node, SimpleType.type, (XmlOptions)null);
      }

      public static SimpleType parse(Node node, XmlOptions options) throws XmlException {
         return (SimpleType)XmlBeans.getContextTypeLoader().parse(node, SimpleType.type, options);
      }

      /** @deprecated */
      public static SimpleType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SimpleType)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, SimpleType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SimpleType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SimpleType)XmlBeans.getContextTypeLoader().parse(xis, SimpleType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SimpleType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SimpleType.type, options);
      }

      private Factory() {
      }
   }
}
