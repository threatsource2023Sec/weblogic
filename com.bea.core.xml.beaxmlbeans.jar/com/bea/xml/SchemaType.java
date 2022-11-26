package com.bea.xml;

import javax.xml.namespace.QName;

public interface SchemaType extends SchemaComponent, SchemaAnnotated {
   int DT_NOT_DERIVED = 0;
   int DT_RESTRICTION = 1;
   int DT_EXTENSION = 2;
   int BTC_NOT_BUILTIN = 0;
   int BTC_ANY_TYPE = 1;
   int BTC_FIRST_PRIMITIVE = 2;
   int BTC_ANY_SIMPLE = 2;
   int BTC_BOOLEAN = 3;
   int BTC_BASE_64_BINARY = 4;
   int BTC_HEX_BINARY = 5;
   int BTC_ANY_URI = 6;
   int BTC_QNAME = 7;
   int BTC_NOTATION = 8;
   int BTC_FLOAT = 9;
   int BTC_DOUBLE = 10;
   int BTC_DECIMAL = 11;
   int BTC_STRING = 12;
   int BTC_DURATION = 13;
   int BTC_DATE_TIME = 14;
   int BTC_TIME = 15;
   int BTC_DATE = 16;
   int BTC_G_YEAR_MONTH = 17;
   int BTC_G_YEAR = 18;
   int BTC_G_MONTH_DAY = 19;
   int BTC_G_DAY = 20;
   int BTC_G_MONTH = 21;
   int BTC_LAST_PRIMITIVE = 21;
   int BTC_INTEGER = 22;
   int BTC_LONG = 23;
   int BTC_INT = 24;
   int BTC_SHORT = 25;
   int BTC_BYTE = 26;
   int BTC_NON_POSITIVE_INTEGER = 27;
   int BTC_NEGATIVE_INTEGER = 28;
   int BTC_NON_NEGATIVE_INTEGER = 29;
   int BTC_POSITIVE_INTEGER = 30;
   int BTC_UNSIGNED_LONG = 31;
   int BTC_UNSIGNED_INT = 32;
   int BTC_UNSIGNED_SHORT = 33;
   int BTC_UNSIGNED_BYTE = 34;
   int BTC_NORMALIZED_STRING = 35;
   int BTC_TOKEN = 36;
   int BTC_NAME = 37;
   int BTC_NCNAME = 38;
   int BTC_LANGUAGE = 39;
   int BTC_ID = 40;
   int BTC_IDREF = 41;
   int BTC_IDREFS = 42;
   int BTC_ENTITY = 43;
   int BTC_ENTITIES = 44;
   int BTC_NMTOKEN = 45;
   int BTC_NMTOKENS = 46;
   int BTC_LAST_BUILTIN = 46;
   int NOT_COMPLEX_TYPE = 0;
   int EMPTY_CONTENT = 1;
   int SIMPLE_CONTENT = 2;
   int ELEMENT_CONTENT = 3;
   int MIXED_CONTENT = 4;
   int FACET_LENGTH = 0;
   int FACET_MIN_LENGTH = 1;
   int FACET_MAX_LENGTH = 2;
   int FACET_MIN_EXCLUSIVE = 3;
   int FACET_MIN_INCLUSIVE = 4;
   int FACET_MAX_INCLUSIVE = 5;
   int FACET_MAX_EXCLUSIVE = 6;
   int FACET_TOTAL_DIGITS = 7;
   int FACET_FRACTION_DIGITS = 8;
   int LAST_BASIC_FACET = 8;
   int FACET_WHITE_SPACE = 9;
   int FACET_PATTERN = 10;
   int FACET_ENUMERATION = 11;
   int LAST_FACET = 11;
   int PROPERTY_ORDERED = 12;
   int PROPERTY_BOUNDED = 13;
   int PROPERTY_CARDINALITY = 14;
   int PROPERTY_NUMERIC = 15;
   int LAST_PROPERTY = 15;
   int UNORDERED = 0;
   int PARTIAL_ORDER = 1;
   int TOTAL_ORDER = 2;
   int NOT_SIMPLE = 0;
   int ATOMIC = 1;
   int UNION = 2;
   int LIST = 3;
   int NOT_DECIMAL = 0;
   int SIZE_BYTE = 8;
   int SIZE_SHORT = 16;
   int SIZE_INT = 32;
   int SIZE_LONG = 64;
   int SIZE_BIG_INTEGER = 1000000;
   int SIZE_BIG_DECIMAL = 1000001;
   int WS_UNSPECIFIED = 0;
   int WS_PRESERVE = 1;
   int WS_REPLACE = 2;
   int WS_COLLAPSE = 3;

   QName getName();

   SchemaField getContainerField();

   boolean isDocumentType();

   boolean isAttributeType();

   QName getDocumentElementName();

   QName getAttributeTypeAttributeName();

   SchemaType getOuterType();

   boolean isSkippedAnonymousType();

   boolean isCompiled();

   String getFullJavaName();

   String getShortJavaName();

   String getFullJavaImplName();

   String getShortJavaImplName();

   Class getJavaClass();

   Class getEnumJavaClass();

   Object getUserData();

   boolean isAnonymousType();

   boolean isBuiltinType();

   boolean isSimpleType();

   SchemaType getBaseType();

   SchemaType getCommonBaseType(SchemaType var1);

   boolean isAssignableFrom(SchemaType var1);

   int getDerivationType();

   int getBuiltinTypeCode();

   boolean isURType();

   boolean isNoType();

   SchemaTypeSystem getTypeSystem();

   boolean isAbstract();

   boolean finalExtension();

   boolean finalRestriction();

   boolean finalList();

   boolean finalUnion();

   boolean blockExtension();

   boolean blockRestriction();

   int getContentType();

   SchemaType getContentBasedOnType();

   SchemaTypeElementSequencer getElementSequencer();

   SchemaType[] getAnonymousTypes();

   SchemaProperty getElementProperty(QName var1);

   SchemaProperty[] getElementProperties();

   SchemaProperty getAttributeProperty(QName var1);

   SchemaProperty[] getAttributeProperties();

   SchemaProperty[] getProperties();

   SchemaProperty[] getDerivedProperties();

   SchemaAttributeModel getAttributeModel();

   boolean hasAttributeWildcards();

   SchemaParticle getContentModel();

   boolean hasElementWildcards();

   boolean isValidSubstitution(QName var1);

   boolean hasAllContent();

   boolean isOrderSensitive();

   SchemaType getElementType(QName var1, QName var2, SchemaTypeLoader var3);

   SchemaType getAttributeType(QName var1, SchemaTypeLoader var2);

   XmlAnySimpleType getFacet(int var1);

   boolean isFacetFixed(int var1);

   int ordered();

   boolean isBounded();

   boolean isFinite();

   boolean isNumeric();

   boolean hasPatternFacet();

   String[] getPatterns();

   boolean matchPatternFacet(String var1);

   XmlAnySimpleType[] getEnumerationValues();

   boolean hasStringEnumValues();

   SchemaType getBaseEnumType();

   SchemaStringEnumEntry[] getStringEnumEntries();

   SchemaStringEnumEntry enumEntryForString(String var1);

   StringEnumAbstractBase enumForString(String var1);

   StringEnumAbstractBase enumForInt(int var1);

   boolean isPrimitiveType();

   int getSimpleVariety();

   SchemaType getPrimitiveType();

   int getDecimalSize();

   SchemaType[] getUnionMemberTypes();

   SchemaType[] getUnionSubTypes();

   SchemaType[] getUnionConstituentTypes();

   SchemaType getUnionCommonBaseType();

   int getAnonymousUnionMemberOrdinal();

   SchemaType getListItemType();

   int getWhiteSpaceRule();

   XmlAnySimpleType newValue(Object var1);

   Ref getRef();

   QNameSet qnameSetForWildcardElements();

   QNameSet qnameSetForWildcardAttributes();

   public static final class Ref extends SchemaComponent.Ref {
      public Ref() {
      }

      public Ref(SchemaType type) {
         super(type);
      }

      public Ref(SchemaTypeSystem system, String handle) {
         super(system, handle);
      }

      public final int getComponentType() {
         return 0;
      }

      public final SchemaType get() {
         return (SchemaType)this.getComponent();
      }
   }
}
