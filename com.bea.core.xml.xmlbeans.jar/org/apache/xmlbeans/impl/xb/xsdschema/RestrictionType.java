package org.apache.xmlbeans.impl.xb.xsdschema;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlBeans;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.XmlQName;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.apache.xmlbeans.xml.stream.XMLStreamException;
import org.w3c.dom.Node;

public interface RestrictionType extends Annotated {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(RestrictionType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("restrictiontype939ftype");

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

   LocalSimpleType getSimpleType();

   boolean isSetSimpleType();

   void setSimpleType(LocalSimpleType var1);

   LocalSimpleType addNewSimpleType();

   void unsetSimpleType();

   Facet[] getMinExclusiveArray();

   Facet getMinExclusiveArray(int var1);

   int sizeOfMinExclusiveArray();

   void setMinExclusiveArray(Facet[] var1);

   void setMinExclusiveArray(int var1, Facet var2);

   Facet insertNewMinExclusive(int var1);

   Facet addNewMinExclusive();

   void removeMinExclusive(int var1);

   Facet[] getMinInclusiveArray();

   Facet getMinInclusiveArray(int var1);

   int sizeOfMinInclusiveArray();

   void setMinInclusiveArray(Facet[] var1);

   void setMinInclusiveArray(int var1, Facet var2);

   Facet insertNewMinInclusive(int var1);

   Facet addNewMinInclusive();

   void removeMinInclusive(int var1);

   Facet[] getMaxExclusiveArray();

   Facet getMaxExclusiveArray(int var1);

   int sizeOfMaxExclusiveArray();

   void setMaxExclusiveArray(Facet[] var1);

   void setMaxExclusiveArray(int var1, Facet var2);

   Facet insertNewMaxExclusive(int var1);

   Facet addNewMaxExclusive();

   void removeMaxExclusive(int var1);

   Facet[] getMaxInclusiveArray();

   Facet getMaxInclusiveArray(int var1);

   int sizeOfMaxInclusiveArray();

   void setMaxInclusiveArray(Facet[] var1);

   void setMaxInclusiveArray(int var1, Facet var2);

   Facet insertNewMaxInclusive(int var1);

   Facet addNewMaxInclusive();

   void removeMaxInclusive(int var1);

   TotalDigitsDocument.TotalDigits[] getTotalDigitsArray();

   TotalDigitsDocument.TotalDigits getTotalDigitsArray(int var1);

   int sizeOfTotalDigitsArray();

   void setTotalDigitsArray(TotalDigitsDocument.TotalDigits[] var1);

   void setTotalDigitsArray(int var1, TotalDigitsDocument.TotalDigits var2);

   TotalDigitsDocument.TotalDigits insertNewTotalDigits(int var1);

   TotalDigitsDocument.TotalDigits addNewTotalDigits();

   void removeTotalDigits(int var1);

   NumFacet[] getFractionDigitsArray();

   NumFacet getFractionDigitsArray(int var1);

   int sizeOfFractionDigitsArray();

   void setFractionDigitsArray(NumFacet[] var1);

   void setFractionDigitsArray(int var1, NumFacet var2);

   NumFacet insertNewFractionDigits(int var1);

   NumFacet addNewFractionDigits();

   void removeFractionDigits(int var1);

   NumFacet[] getLengthArray();

   NumFacet getLengthArray(int var1);

   int sizeOfLengthArray();

   void setLengthArray(NumFacet[] var1);

   void setLengthArray(int var1, NumFacet var2);

   NumFacet insertNewLength(int var1);

   NumFacet addNewLength();

   void removeLength(int var1);

   NumFacet[] getMinLengthArray();

   NumFacet getMinLengthArray(int var1);

   int sizeOfMinLengthArray();

   void setMinLengthArray(NumFacet[] var1);

   void setMinLengthArray(int var1, NumFacet var2);

   NumFacet insertNewMinLength(int var1);

   NumFacet addNewMinLength();

   void removeMinLength(int var1);

   NumFacet[] getMaxLengthArray();

   NumFacet getMaxLengthArray(int var1);

   int sizeOfMaxLengthArray();

   void setMaxLengthArray(NumFacet[] var1);

   void setMaxLengthArray(int var1, NumFacet var2);

   NumFacet insertNewMaxLength(int var1);

   NumFacet addNewMaxLength();

   void removeMaxLength(int var1);

   NoFixedFacet[] getEnumerationArray();

   NoFixedFacet getEnumerationArray(int var1);

   int sizeOfEnumerationArray();

   void setEnumerationArray(NoFixedFacet[] var1);

   void setEnumerationArray(int var1, NoFixedFacet var2);

   NoFixedFacet insertNewEnumeration(int var1);

   NoFixedFacet addNewEnumeration();

   void removeEnumeration(int var1);

   WhiteSpaceDocument.WhiteSpace[] getWhiteSpaceArray();

   WhiteSpaceDocument.WhiteSpace getWhiteSpaceArray(int var1);

   int sizeOfWhiteSpaceArray();

   void setWhiteSpaceArray(WhiteSpaceDocument.WhiteSpace[] var1);

   void setWhiteSpaceArray(int var1, WhiteSpaceDocument.WhiteSpace var2);

   WhiteSpaceDocument.WhiteSpace insertNewWhiteSpace(int var1);

   WhiteSpaceDocument.WhiteSpace addNewWhiteSpace();

   void removeWhiteSpace(int var1);

   PatternDocument.Pattern[] getPatternArray();

   PatternDocument.Pattern getPatternArray(int var1);

   int sizeOfPatternArray();

   void setPatternArray(PatternDocument.Pattern[] var1);

   void setPatternArray(int var1, PatternDocument.Pattern var2);

   PatternDocument.Pattern insertNewPattern(int var1);

   PatternDocument.Pattern addNewPattern();

   void removePattern(int var1);

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

   QName getBase();

   XmlQName xgetBase();

   void setBase(QName var1);

   void xsetBase(XmlQName var1);

   public static final class Factory {
      public static RestrictionType newInstance() {
         return (RestrictionType)XmlBeans.getContextTypeLoader().newInstance(RestrictionType.type, (XmlOptions)null);
      }

      public static RestrictionType newInstance(XmlOptions options) {
         return (RestrictionType)XmlBeans.getContextTypeLoader().newInstance(RestrictionType.type, options);
      }

      public static RestrictionType parse(String xmlAsString) throws XmlException {
         return (RestrictionType)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, RestrictionType.type, (XmlOptions)null);
      }

      public static RestrictionType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (RestrictionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, RestrictionType.type, options);
      }

      public static RestrictionType parse(File file) throws XmlException, IOException {
         return (RestrictionType)XmlBeans.getContextTypeLoader().parse((File)file, RestrictionType.type, (XmlOptions)null);
      }

      public static RestrictionType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (RestrictionType)XmlBeans.getContextTypeLoader().parse(file, RestrictionType.type, options);
      }

      public static RestrictionType parse(URL u) throws XmlException, IOException {
         return (RestrictionType)XmlBeans.getContextTypeLoader().parse((URL)u, RestrictionType.type, (XmlOptions)null);
      }

      public static RestrictionType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (RestrictionType)XmlBeans.getContextTypeLoader().parse(u, RestrictionType.type, options);
      }

      public static RestrictionType parse(InputStream is) throws XmlException, IOException {
         return (RestrictionType)XmlBeans.getContextTypeLoader().parse((InputStream)is, RestrictionType.type, (XmlOptions)null);
      }

      public static RestrictionType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (RestrictionType)XmlBeans.getContextTypeLoader().parse(is, RestrictionType.type, options);
      }

      public static RestrictionType parse(Reader r) throws XmlException, IOException {
         return (RestrictionType)XmlBeans.getContextTypeLoader().parse((Reader)r, RestrictionType.type, (XmlOptions)null);
      }

      public static RestrictionType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (RestrictionType)XmlBeans.getContextTypeLoader().parse(r, RestrictionType.type, options);
      }

      public static RestrictionType parse(XMLStreamReader sr) throws XmlException {
         return (RestrictionType)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, RestrictionType.type, (XmlOptions)null);
      }

      public static RestrictionType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (RestrictionType)XmlBeans.getContextTypeLoader().parse(sr, RestrictionType.type, options);
      }

      public static RestrictionType parse(Node node) throws XmlException {
         return (RestrictionType)XmlBeans.getContextTypeLoader().parse((Node)node, RestrictionType.type, (XmlOptions)null);
      }

      public static RestrictionType parse(Node node, XmlOptions options) throws XmlException {
         return (RestrictionType)XmlBeans.getContextTypeLoader().parse(node, RestrictionType.type, options);
      }

      /** @deprecated */
      public static RestrictionType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (RestrictionType)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, RestrictionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static RestrictionType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (RestrictionType)XmlBeans.getContextTypeLoader().parse(xis, RestrictionType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RestrictionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RestrictionType.type, options);
      }

      private Factory() {
      }
   }
}
