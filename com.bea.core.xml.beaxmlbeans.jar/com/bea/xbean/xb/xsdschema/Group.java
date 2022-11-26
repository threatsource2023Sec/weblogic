package com.bea.xbean.xb.xsdschema;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlNCName;
import com.bea.xml.XmlNonNegativeInteger;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlQName;
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

public interface Group extends Annotated {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Group.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("group7ca6type");

   LocalElement[] getElementArray();

   LocalElement getElementArray(int var1);

   int sizeOfElementArray();

   void setElementArray(LocalElement[] var1);

   void setElementArray(int var1, LocalElement var2);

   LocalElement insertNewElement(int var1);

   LocalElement addNewElement();

   void removeElement(int var1);

   GroupRef[] getGroupArray();

   GroupRef getGroupArray(int var1);

   int sizeOfGroupArray();

   void setGroupArray(GroupRef[] var1);

   void setGroupArray(int var1, GroupRef var2);

   GroupRef insertNewGroup(int var1);

   GroupRef addNewGroup();

   void removeGroup(int var1);

   All[] getAllArray();

   All getAllArray(int var1);

   int sizeOfAllArray();

   void setAllArray(All[] var1);

   void setAllArray(int var1, All var2);

   All insertNewAll(int var1);

   All addNewAll();

   void removeAll(int var1);

   ExplicitGroup[] getChoiceArray();

   ExplicitGroup getChoiceArray(int var1);

   int sizeOfChoiceArray();

   void setChoiceArray(ExplicitGroup[] var1);

   void setChoiceArray(int var1, ExplicitGroup var2);

   ExplicitGroup insertNewChoice(int var1);

   ExplicitGroup addNewChoice();

   void removeChoice(int var1);

   ExplicitGroup[] getSequenceArray();

   ExplicitGroup getSequenceArray(int var1);

   int sizeOfSequenceArray();

   void setSequenceArray(ExplicitGroup[] var1);

   void setSequenceArray(int var1, ExplicitGroup var2);

   ExplicitGroup insertNewSequence(int var1);

   ExplicitGroup addNewSequence();

   void removeSequence(int var1);

   AnyDocument.Any[] getAnyArray();

   AnyDocument.Any getAnyArray(int var1);

   int sizeOfAnyArray();

   void setAnyArray(AnyDocument.Any[] var1);

   void setAnyArray(int var1, AnyDocument.Any var2);

   AnyDocument.Any insertNewAny(int var1);

   AnyDocument.Any addNewAny();

   void removeAny(int var1);

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

   public static final class Factory {
      /** @deprecated */
      public static Group newInstance() {
         return (Group)XmlBeans.getContextTypeLoader().newInstance(Group.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static Group newInstance(XmlOptions options) {
         return (Group)XmlBeans.getContextTypeLoader().newInstance(Group.type, options);
      }

      public static Group parse(String xmlAsString) throws XmlException {
         return (Group)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, Group.type, (XmlOptions)null);
      }

      public static Group parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (Group)XmlBeans.getContextTypeLoader().parse(xmlAsString, Group.type, options);
      }

      public static Group parse(File file) throws XmlException, IOException {
         return (Group)XmlBeans.getContextTypeLoader().parse((File)file, Group.type, (XmlOptions)null);
      }

      public static Group parse(File file, XmlOptions options) throws XmlException, IOException {
         return (Group)XmlBeans.getContextTypeLoader().parse(file, Group.type, options);
      }

      public static Group parse(URL u) throws XmlException, IOException {
         return (Group)XmlBeans.getContextTypeLoader().parse((URL)u, Group.type, (XmlOptions)null);
      }

      public static Group parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (Group)XmlBeans.getContextTypeLoader().parse(u, Group.type, options);
      }

      public static Group parse(InputStream is) throws XmlException, IOException {
         return (Group)XmlBeans.getContextTypeLoader().parse((InputStream)is, Group.type, (XmlOptions)null);
      }

      public static Group parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (Group)XmlBeans.getContextTypeLoader().parse(is, Group.type, options);
      }

      public static Group parse(Reader r) throws XmlException, IOException {
         return (Group)XmlBeans.getContextTypeLoader().parse((Reader)r, Group.type, (XmlOptions)null);
      }

      public static Group parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (Group)XmlBeans.getContextTypeLoader().parse(r, Group.type, options);
      }

      public static Group parse(XMLStreamReader sr) throws XmlException {
         return (Group)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, Group.type, (XmlOptions)null);
      }

      public static Group parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (Group)XmlBeans.getContextTypeLoader().parse(sr, Group.type, options);
      }

      public static Group parse(Node node) throws XmlException {
         return (Group)XmlBeans.getContextTypeLoader().parse((Node)node, Group.type, (XmlOptions)null);
      }

      public static Group parse(Node node, XmlOptions options) throws XmlException {
         return (Group)XmlBeans.getContextTypeLoader().parse(node, Group.type, options);
      }

      /** @deprecated */
      public static Group parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (Group)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, Group.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static Group parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (Group)XmlBeans.getContextTypeLoader().parse(xis, Group.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Group.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Group.type, options);
      }

      private Factory() {
      }
   }
}
