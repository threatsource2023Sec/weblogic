package com.bea.xbean.xb.xsdschema;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
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

public interface ComplexType extends Annotated {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ComplexType.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("complextype5dbbtype");

   SimpleContentDocument.SimpleContent getSimpleContent();

   boolean isSetSimpleContent();

   void setSimpleContent(SimpleContentDocument.SimpleContent var1);

   SimpleContentDocument.SimpleContent addNewSimpleContent();

   void unsetSimpleContent();

   ComplexContentDocument.ComplexContent getComplexContent();

   boolean isSetComplexContent();

   void setComplexContent(ComplexContentDocument.ComplexContent var1);

   ComplexContentDocument.ComplexContent addNewComplexContent();

   void unsetComplexContent();

   GroupRef getGroup();

   boolean isSetGroup();

   void setGroup(GroupRef var1);

   GroupRef addNewGroup();

   void unsetGroup();

   All getAll();

   boolean isSetAll();

   void setAll(All var1);

   All addNewAll();

   void unsetAll();

   ExplicitGroup getChoice();

   boolean isSetChoice();

   void setChoice(ExplicitGroup var1);

   ExplicitGroup addNewChoice();

   void unsetChoice();

   ExplicitGroup getSequence();

   boolean isSetSequence();

   void setSequence(ExplicitGroup var1);

   ExplicitGroup addNewSequence();

   void unsetSequence();

   Attribute[] getAttributeArray();

   Attribute getAttributeArray(int var1);

   int sizeOfAttributeArray();

   void setAttributeArray(Attribute[] var1);

   void setAttributeArray(int var1, Attribute var2);

   Attribute insertNewAttribute(int var1);

   Attribute addNewAttribute();

   void removeAttribute(int var1);

   AttributeGroupRef[] getAttributeGroupArray();

   AttributeGroupRef getAttributeGroupArray(int var1);

   int sizeOfAttributeGroupArray();

   void setAttributeGroupArray(AttributeGroupRef[] var1);

   void setAttributeGroupArray(int var1, AttributeGroupRef var2);

   AttributeGroupRef insertNewAttributeGroup(int var1);

   AttributeGroupRef addNewAttributeGroup();

   void removeAttributeGroup(int var1);

   Wildcard getAnyAttribute();

   boolean isSetAnyAttribute();

   void setAnyAttribute(Wildcard var1);

   Wildcard addNewAnyAttribute();

   void unsetAnyAttribute();

   String getName();

   XmlNCName xgetName();

   boolean isSetName();

   void setName(String var1);

   void xsetName(XmlNCName var1);

   void unsetName();

   boolean getMixed();

   XmlBoolean xgetMixed();

   boolean isSetMixed();

   void setMixed(boolean var1);

   void xsetMixed(XmlBoolean var1);

   void unsetMixed();

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

   DerivationSet xgetBlock();

   boolean isSetBlock();

   void setBlock(Object var1);

   void xsetBlock(DerivationSet var1);

   void unsetBlock();

   public static final class Factory {
      /** @deprecated */
      public static ComplexType newInstance() {
         return (ComplexType)XmlBeans.getContextTypeLoader().newInstance(ComplexType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ComplexType newInstance(XmlOptions options) {
         return (ComplexType)XmlBeans.getContextTypeLoader().newInstance(ComplexType.type, options);
      }

      public static ComplexType parse(String xmlAsString) throws XmlException {
         return (ComplexType)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, ComplexType.type, (XmlOptions)null);
      }

      public static ComplexType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ComplexType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ComplexType.type, options);
      }

      public static ComplexType parse(File file) throws XmlException, IOException {
         return (ComplexType)XmlBeans.getContextTypeLoader().parse((File)file, ComplexType.type, (XmlOptions)null);
      }

      public static ComplexType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ComplexType)XmlBeans.getContextTypeLoader().parse(file, ComplexType.type, options);
      }

      public static ComplexType parse(URL u) throws XmlException, IOException {
         return (ComplexType)XmlBeans.getContextTypeLoader().parse((URL)u, ComplexType.type, (XmlOptions)null);
      }

      public static ComplexType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ComplexType)XmlBeans.getContextTypeLoader().parse(u, ComplexType.type, options);
      }

      public static ComplexType parse(InputStream is) throws XmlException, IOException {
         return (ComplexType)XmlBeans.getContextTypeLoader().parse((InputStream)is, ComplexType.type, (XmlOptions)null);
      }

      public static ComplexType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ComplexType)XmlBeans.getContextTypeLoader().parse(is, ComplexType.type, options);
      }

      public static ComplexType parse(Reader r) throws XmlException, IOException {
         return (ComplexType)XmlBeans.getContextTypeLoader().parse((Reader)r, ComplexType.type, (XmlOptions)null);
      }

      public static ComplexType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ComplexType)XmlBeans.getContextTypeLoader().parse(r, ComplexType.type, options);
      }

      public static ComplexType parse(XMLStreamReader sr) throws XmlException {
         return (ComplexType)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, ComplexType.type, (XmlOptions)null);
      }

      public static ComplexType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ComplexType)XmlBeans.getContextTypeLoader().parse(sr, ComplexType.type, options);
      }

      public static ComplexType parse(Node node) throws XmlException {
         return (ComplexType)XmlBeans.getContextTypeLoader().parse((Node)node, ComplexType.type, (XmlOptions)null);
      }

      public static ComplexType parse(Node node, XmlOptions options) throws XmlException {
         return (ComplexType)XmlBeans.getContextTypeLoader().parse(node, ComplexType.type, options);
      }

      /** @deprecated */
      public static ComplexType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ComplexType)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, ComplexType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ComplexType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ComplexType)XmlBeans.getContextTypeLoader().parse(xis, ComplexType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ComplexType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ComplexType.type, options);
      }

      private Factory() {
      }
   }
}
