package com.bea.xbean.xb.xsdschema;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlException;
import com.bea.xml.XmlNCName;
import com.bea.xml.XmlNonNegativeInteger;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlQName;
import com.bea.xml.XmlString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigInteger;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface Element extends Annotated {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Element.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("elementd189type");

   LocalSimpleType getSimpleType();

   boolean isSetSimpleType();

   void setSimpleType(LocalSimpleType var1);

   LocalSimpleType addNewSimpleType();

   void unsetSimpleType();

   LocalComplexType getComplexType();

   boolean isSetComplexType();

   void setComplexType(LocalComplexType var1);

   LocalComplexType addNewComplexType();

   void unsetComplexType();

   Keybase[] getUniqueArray();

   Keybase getUniqueArray(int var1);

   int sizeOfUniqueArray();

   void setUniqueArray(Keybase[] var1);

   void setUniqueArray(int var1, Keybase var2);

   Keybase insertNewUnique(int var1);

   Keybase addNewUnique();

   void removeUnique(int var1);

   Keybase[] getKeyArray();

   Keybase getKeyArray(int var1);

   int sizeOfKeyArray();

   void setKeyArray(Keybase[] var1);

   void setKeyArray(int var1, Keybase var2);

   Keybase insertNewKey(int var1);

   Keybase addNewKey();

   void removeKey(int var1);

   KeyrefDocument.Keyref[] getKeyrefArray();

   KeyrefDocument.Keyref getKeyrefArray(int var1);

   int sizeOfKeyrefArray();

   void setKeyrefArray(KeyrefDocument.Keyref[] var1);

   void setKeyrefArray(int var1, KeyrefDocument.Keyref var2);

   KeyrefDocument.Keyref insertNewKeyref(int var1);

   KeyrefDocument.Keyref addNewKeyref();

   void removeKeyref(int var1);

   String getName();

   XmlNCName xgetName();

   boolean isSetName();

   void setName(String var1);

   void xsetName(XmlNCName var1);

   void unsetName();

   QName getRef();

   XmlQName xgetRef();

   boolean isSetRef();

   void setRef(QName var1);

   void xsetRef(XmlQName var1);

   void unsetRef();

   QName getType();

   XmlQName xgetType();

   boolean isSetType();

   void setType(QName var1);

   void xsetType(XmlQName var1);

   void unsetType();

   QName getSubstitutionGroup();

   XmlQName xgetSubstitutionGroup();

   boolean isSetSubstitutionGroup();

   void setSubstitutionGroup(QName var1);

   void xsetSubstitutionGroup(XmlQName var1);

   void unsetSubstitutionGroup();

   BigInteger getMinOccurs();

   XmlNonNegativeInteger xgetMinOccurs();

   boolean isSetMinOccurs();

   void setMinOccurs(BigInteger var1);

   void xsetMinOccurs(XmlNonNegativeInteger var1);

   void unsetMinOccurs();

   Object getMaxOccurs();

   AllNNI xgetMaxOccurs();

   boolean isSetMaxOccurs();

   void setMaxOccurs(Object var1);

   void xsetMaxOccurs(AllNNI var1);

   void unsetMaxOccurs();

   String getDefault();

   XmlString xgetDefault();

   boolean isSetDefault();

   void setDefault(String var1);

   void xsetDefault(XmlString var1);

   void unsetDefault();

   String getFixed();

   XmlString xgetFixed();

   boolean isSetFixed();

   void setFixed(String var1);

   void xsetFixed(XmlString var1);

   void unsetFixed();

   boolean getNillable();

   XmlBoolean xgetNillable();

   boolean isSetNillable();

   void setNillable(boolean var1);

   void xsetNillable(XmlBoolean var1);

   void unsetNillable();

   boolean getAbstract();

   XmlBoolean xgetAbstract();

   boolean isSetAbstract();

   void setAbstract(boolean var1);

   void xsetAbstract(XmlBoolean var1);

   void unsetAbstract();

   Object getFinal();

   DerivationSet xgetFinal();

   boolean isSetFinal();

   void setFinal(Object var1);

   void xsetFinal(DerivationSet var1);

   void unsetFinal();

   Object getBlock();

   BlockSet xgetBlock();

   boolean isSetBlock();

   void setBlock(Object var1);

   void xsetBlock(BlockSet var1);

   void unsetBlock();

   FormChoice.Enum getForm();

   FormChoice xgetForm();

   boolean isSetForm();

   void setForm(FormChoice.Enum var1);

   void xsetForm(FormChoice var1);

   void unsetForm();

   public static final class Factory {
      /** @deprecated */
      public static Element newInstance() {
         return (Element)XmlBeans.getContextTypeLoader().newInstance(Element.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static Element newInstance(XmlOptions options) {
         return (Element)XmlBeans.getContextTypeLoader().newInstance(Element.type, options);
      }

      public static Element parse(String xmlAsString) throws XmlException {
         return (Element)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, Element.type, (XmlOptions)null);
      }

      public static Element parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (Element)XmlBeans.getContextTypeLoader().parse(xmlAsString, Element.type, options);
      }

      public static Element parse(File file) throws XmlException, IOException {
         return (Element)XmlBeans.getContextTypeLoader().parse((File)file, Element.type, (XmlOptions)null);
      }

      public static Element parse(File file, XmlOptions options) throws XmlException, IOException {
         return (Element)XmlBeans.getContextTypeLoader().parse(file, Element.type, options);
      }

      public static Element parse(URL u) throws XmlException, IOException {
         return (Element)XmlBeans.getContextTypeLoader().parse((URL)u, Element.type, (XmlOptions)null);
      }

      public static Element parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (Element)XmlBeans.getContextTypeLoader().parse(u, Element.type, options);
      }

      public static Element parse(InputStream is) throws XmlException, IOException {
         return (Element)XmlBeans.getContextTypeLoader().parse((InputStream)is, Element.type, (XmlOptions)null);
      }

      public static Element parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (Element)XmlBeans.getContextTypeLoader().parse(is, Element.type, options);
      }

      public static Element parse(Reader r) throws XmlException, IOException {
         return (Element)XmlBeans.getContextTypeLoader().parse((Reader)r, Element.type, (XmlOptions)null);
      }

      public static Element parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (Element)XmlBeans.getContextTypeLoader().parse(r, Element.type, options);
      }

      public static Element parse(XMLStreamReader sr) throws XmlException {
         return (Element)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, Element.type, (XmlOptions)null);
      }

      public static Element parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (Element)XmlBeans.getContextTypeLoader().parse(sr, Element.type, options);
      }

      public static Element parse(Node node) throws XmlException {
         return (Element)XmlBeans.getContextTypeLoader().parse((Node)node, Element.type, (XmlOptions)null);
      }

      public static Element parse(Node node, XmlOptions options) throws XmlException {
         return (Element)XmlBeans.getContextTypeLoader().parse(node, Element.type, options);
      }

      /** @deprecated */
      public static Element parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (Element)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, Element.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static Element parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (Element)XmlBeans.getContextTypeLoader().parse(xis, Element.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Element.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Element.type, options);
      }

      private Factory() {
      }
   }
}
