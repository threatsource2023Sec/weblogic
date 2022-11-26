package org.apache.xmlbeans.impl.piccolo.xml;

public final class AttributeDefinition {
   public static final int IMPLIED = 1;
   public static final int REQUIRED = 2;
   public static final int FIXED = 3;
   public static final int ENUMERATION = 1;
   public static final int NOTATION = 2;
   public static final int CDATA = 3;
   public static final int ID = 4;
   public static final int IDREF = 5;
   public static final int IDREFS = 6;
   public static final int ENTITY = 7;
   public static final int ENTITIES = 8;
   public static final int NMTOKEN = 9;
   public static final int NMTOKENS = 10;
   private static final String[] valueTypeStrings = new String[]{null, "NMTOKEN", "NOTATION", "CDATA", "ID", "IDREF", "IDREFS", "ENTITY", "ENTITIES", "NMTOKEN", "NMTOKENS"};
   private static final String[] defaultTypeStrings = new String[]{null, "#IMPLIED", "#REQUIRED", "#FIXED"};
   String prefix;
   String localName;
   String qName;
   int valueType;
   int defaultType;
   String defaultValue;
   String[] possibleValues;

   public AttributeDefinition(String prefix, String localName, String qName, int valueType, String[] possibleValues, int defaultType, String defaultValue) {
      this.prefix = prefix;
      this.localName = localName;
      this.qName = qName;
      this.valueType = valueType;
      this.possibleValues = possibleValues;
      this.defaultType = defaultType;
      this.defaultValue = defaultValue;
   }

   public String getPrefix() {
      return this.prefix;
   }

   public String getLocalName() {
      return this.localName;
   }

   public String getQName() {
      return this.qName;
   }

   public int getValueType() {
      return this.valueType;
   }

   public String getValueTypeString() {
      return getValueTypeString(this.valueType);
   }

   public static String getValueTypeString(int valueType) {
      return valueTypeStrings[valueType];
   }

   public int getDefaultType() {
      return this.defaultType;
   }

   public String getDefaultTypeString() {
      return getDefaultTypeString(this.defaultType);
   }

   public static String getDefaultTypeString(int defaultType) {
      return defaultTypeStrings[defaultType];
   }

   public String getDefaultValue() {
      return this.defaultValue;
   }

   public String[] getPossibleValues() {
      return this.possibleValues;
   }
}
