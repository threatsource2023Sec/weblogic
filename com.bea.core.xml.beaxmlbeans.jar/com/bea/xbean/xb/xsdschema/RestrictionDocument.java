package com.bea.xbean.xb.xsdschema;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlQName;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface RestrictionDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(RestrictionDocument.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("restriction0049doctype");

   Restriction getRestriction();

   void setRestriction(Restriction var1);

   Restriction addNewRestriction();

   public static final class Factory {
      public static RestrictionDocument newInstance() {
         return (RestrictionDocument)XmlBeans.getContextTypeLoader().newInstance(RestrictionDocument.type, (XmlOptions)null);
      }

      public static RestrictionDocument newInstance(XmlOptions options) {
         return (RestrictionDocument)XmlBeans.getContextTypeLoader().newInstance(RestrictionDocument.type, options);
      }

      public static RestrictionDocument parse(String xmlAsString) throws XmlException {
         return (RestrictionDocument)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, RestrictionDocument.type, (XmlOptions)null);
      }

      public static RestrictionDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (RestrictionDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, RestrictionDocument.type, options);
      }

      public static RestrictionDocument parse(File file) throws XmlException, IOException {
         return (RestrictionDocument)XmlBeans.getContextTypeLoader().parse((File)file, RestrictionDocument.type, (XmlOptions)null);
      }

      public static RestrictionDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (RestrictionDocument)XmlBeans.getContextTypeLoader().parse(file, RestrictionDocument.type, options);
      }

      public static RestrictionDocument parse(URL u) throws XmlException, IOException {
         return (RestrictionDocument)XmlBeans.getContextTypeLoader().parse((URL)u, RestrictionDocument.type, (XmlOptions)null);
      }

      public static RestrictionDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (RestrictionDocument)XmlBeans.getContextTypeLoader().parse(u, RestrictionDocument.type, options);
      }

      public static RestrictionDocument parse(InputStream is) throws XmlException, IOException {
         return (RestrictionDocument)XmlBeans.getContextTypeLoader().parse((InputStream)is, RestrictionDocument.type, (XmlOptions)null);
      }

      public static RestrictionDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (RestrictionDocument)XmlBeans.getContextTypeLoader().parse(is, RestrictionDocument.type, options);
      }

      public static RestrictionDocument parse(Reader r) throws XmlException, IOException {
         return (RestrictionDocument)XmlBeans.getContextTypeLoader().parse((Reader)r, RestrictionDocument.type, (XmlOptions)null);
      }

      public static RestrictionDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (RestrictionDocument)XmlBeans.getContextTypeLoader().parse(r, RestrictionDocument.type, options);
      }

      public static RestrictionDocument parse(XMLStreamReader sr) throws XmlException {
         return (RestrictionDocument)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, RestrictionDocument.type, (XmlOptions)null);
      }

      public static RestrictionDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (RestrictionDocument)XmlBeans.getContextTypeLoader().parse(sr, RestrictionDocument.type, options);
      }

      public static RestrictionDocument parse(Node node) throws XmlException {
         return (RestrictionDocument)XmlBeans.getContextTypeLoader().parse((Node)node, RestrictionDocument.type, (XmlOptions)null);
      }

      public static RestrictionDocument parse(Node node, XmlOptions options) throws XmlException {
         return (RestrictionDocument)XmlBeans.getContextTypeLoader().parse(node, RestrictionDocument.type, options);
      }

      /** @deprecated */
      public static RestrictionDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (RestrictionDocument)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, RestrictionDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static RestrictionDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (RestrictionDocument)XmlBeans.getContextTypeLoader().parse(xis, RestrictionDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RestrictionDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RestrictionDocument.type, options);
      }

      private Factory() {
      }
   }

   public interface Restriction extends Annotated {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Restriction.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("restrictionad11elemtype");

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

      QName getBase();

      XmlQName xgetBase();

      boolean isSetBase();

      void setBase(QName var1);

      void xsetBase(XmlQName var1);

      void unsetBase();

      public static final class Factory {
         public static Restriction newInstance() {
            return (Restriction)XmlBeans.getContextTypeLoader().newInstance(RestrictionDocument.Restriction.type, (XmlOptions)null);
         }

         public static Restriction newInstance(XmlOptions options) {
            return (Restriction)XmlBeans.getContextTypeLoader().newInstance(RestrictionDocument.Restriction.type, options);
         }

         private Factory() {
         }
      }
   }
}
