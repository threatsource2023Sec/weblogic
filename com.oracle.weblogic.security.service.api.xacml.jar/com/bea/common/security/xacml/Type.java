package com.bea.common.security.xacml;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Type implements Serializable {
   public static final Type STRING = new Type("http://www.w3.org/2001/XMLSchema#string", "string", false, false);
   public static final Type BOOLEAN = new Type("http://www.w3.org/2001/XMLSchema#boolean", "boolean", false, false);
   public static final Type INTEGER = new Type("http://www.w3.org/2001/XMLSchema#integer", "integer", false, false);
   public static final Type DOUBLE = new Type("http://www.w3.org/2001/XMLSchema#double", "double", false, false);
   public static final Type TIME = new Type("http://www.w3.org/2001/XMLSchema#time", "time", false, false);
   public static final Type DATE = new Type("http://www.w3.org/2001/XMLSchema#date", "date", false, false);
   public static final Type DATE_TIME = new Type("http://www.w3.org/2001/XMLSchema#dateTime", "dateTime", false, false);
   public static final Type DAY_TIME_DURATION = new Type("http://www.w3.org/TR/2002/WD-xquery-operators-20020816#dayTimeDuration", "dayTimeDuration", false, false);
   public static final Type YEAR_MONTH_DURATION = new Type("http://www.w3.org/TR/2002/WD-xquery-operators-20020816#yearMonthDuration", "yearMonthDuration", false, false);
   public static final Type ANY_URI = new Type("http://www.w3.org/2001/XMLSchema#anyURI", "anyURI", false, false);
   public static final Type HEX_BINARY = new Type("http://www.w3.org/2001/XMLSchema#hexBinary", "hexBinary", false, false);
   public static final Type BASE64_BINARY = new Type("http://www.w3.org/2001/XMLSchema#base64Binary", "base64Binary", false, false);
   public static final Type RFC822_NAME = new Type("urn:oasis:names:tc:xacml:1.0:data-type:rfc822Name", "rfc822Name", false, false);
   public static final Type X500_NAME = new Type("urn:oasis:names:tc:xacml:1.0:data-type:x500Name", "x500Name", false, false);
   public static final Type IP_ADDRESS = new Type("urn:oasis:names:tc:xacml:2.0:data-type:ipAddress", "ipAddress", false, false);
   public static final Type DNS_ADDRESS = new Type("urn:oasis:names:tc:xacml:2.0:data-type:dnsName", "dnsName", false, false);
   public static final Type CHARACTER = new Type("urn:bea:xacml:2.0:data-type:character", "character");
   public static final Type LONG = new Type("http://www.w3.org/2001/XMLSchema#long", "long");
   public static final Type FLOAT = new Type("http://www.w3.org/2001/XMLSchema#float", "float");
   public static final Type DECIMAL = new Type("http://www.w3.org/2001/XMLSchema#decimal", "decimal");
   public static final Type OBJECT = new Type("urn:bea:xacml:2.0:data-type:object", "object");
   public static final Type CLASS = new Type("urn:bea:xacml:2.0:data-type:class", "class");
   public static final Type STRING_BAG;
   public static final Type BOOLEAN_BAG;
   public static final Type INTEGER_BAG;
   public static final Type DOUBLE_BAG;
   public static final Type TIME_BAG;
   public static final Type DATE_BAG;
   public static final Type DATE_TIME_BAG;
   public static final Type DAY_TIME_DURATION_BAG;
   public static final Type YEAR_MONTH_DURATION_BAG;
   public static final Type ANY_URI_BAG;
   public static final Type HEX_BINARY_BAG;
   public static final Type BASE64_BINARY_BAG;
   public static final Type RFC822_NAME_BAG;
   public static final Type X500_NAME_BAG;
   public static final Type IP_ADDRESS_BAG;
   public static final Type DNS_ADDRESS_BAG;
   public static final Type FUNCTION;
   public static final Type FUNCTION_BAG;
   public static final Type CHARACTER_BAG;
   public static final Type LONG_BAG;
   public static final Type FLOAT_BAG;
   public static final Type DECIMAL_BAG;
   public static final Type OBJECT_BAG;
   public static final Type CLASS_BAG;
   private static final Map scalarTypes;
   private URI type;
   private String type_as_string;
   private boolean isBag;
   private boolean isCustom;
   private String shortName;

   public static Collection getScalarTypes() {
      return scalarTypes.values();
   }

   public static Type findType(String uri) {
      return (Type)scalarTypes.get(uri);
   }

   protected Type(Type other) {
      this(other, other.isBag);
   }

   public Type(Type other, boolean isBag) {
      this.type = other.type;
      this.type_as_string = other.type_as_string;
      this.isBag = isBag;
      this.isCustom = other.isCustom;
      this.shortName = other.shortName;
   }

   public Type(String type, String shortName) {
      this(type, shortName, false, true);
   }

   public Type(String type, String shortName, boolean isBag) {
      this(type, shortName, isBag, true);
   }

   public Type(String type, String shortName, boolean isBag, boolean isCustom) {
      if (type == null) {
         throw new IllegalArgumentException();
      } else {
         this.type_as_string = type;
         this.shortName = shortName;
         this.isBag = isBag;
         this.isCustom = isCustom;
      }
   }

   public Type(URI type, String shortName) {
      this(type, shortName, false);
   }

   public Type(URI type, boolean isBag) {
      this((URI)type, (String)null, isBag);
   }

   public Type(URI type, String shortName, boolean isBag) {
      if (type == null) {
         throw new IllegalArgumentException();
      } else {
         this.type = type;
         this.shortName = shortName;
         this.isBag = isBag;
      }
   }

   public String getShortName() {
      return this.shortName;
   }

   public URI getDataType() throws URISyntaxException {
      if (this.type == null && this.type_as_string != null) {
         try {
            this.type = new URI(this.type_as_string);
         } catch (java.net.URISyntaxException var2) {
            throw new URISyntaxException(var2);
         }
      }

      return this.type;
   }

   public boolean isBag() {
      return this.isBag;
   }

   public boolean isCustom() {
      return this.isCustom;
   }

   public String getType() {
      if (this.type_as_string == null) {
         this.type_as_string = this.type.toString();
      }

      return this.type_as_string;
   }

   public int hashCode() {
      int retVal = this.isBag ? 0 : -1;
      String type = this.getType();
      return type == null ? retVal : retVal ^ type.hashCode();
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof Type)) {
         return false;
      } else {
         Type other = (Type)o;
         return this.isBag == other.isBag && (this.getType() == other.getType() || this.getType().equals(other.getType()));
      }
   }

   static {
      STRING_BAG = new Type(STRING, true);
      BOOLEAN_BAG = new Type(BOOLEAN, true);
      INTEGER_BAG = new Type(INTEGER, true);
      DOUBLE_BAG = new Type(DOUBLE, true);
      TIME_BAG = new Type(TIME, true);
      DATE_BAG = new Type(DATE, true);
      DATE_TIME_BAG = new Type(DATE_TIME, true);
      DAY_TIME_DURATION_BAG = new Type(DAY_TIME_DURATION, true);
      YEAR_MONTH_DURATION_BAG = new Type(YEAR_MONTH_DURATION, true);
      ANY_URI_BAG = new Type(ANY_URI, true);
      HEX_BINARY_BAG = new Type(HEX_BINARY, true);
      BASE64_BINARY_BAG = new Type(BASE64_BINARY, true);
      RFC822_NAME_BAG = new Type(RFC822_NAME, true);
      X500_NAME_BAG = new Type(X500_NAME, true);
      IP_ADDRESS_BAG = new Type(IP_ADDRESS, true);
      DNS_ADDRESS_BAG = new Type(DNS_ADDRESS, true);
      FUNCTION = new Type("urn:oasis:names:tc:xacml:1.0:data-type:function", "function");
      FUNCTION_BAG = new Type(FUNCTION, true);
      CHARACTER_BAG = new Type(CHARACTER, true);
      LONG_BAG = new Type(LONG, true);
      FLOAT_BAG = new Type(FLOAT, true);
      DECIMAL_BAG = new Type(DECIMAL, true);
      OBJECT_BAG = new Type(OBJECT, true);
      CLASS_BAG = new Type(CLASS, true);
      scalarTypes = new HashMap();
      scalarTypes.put(STRING.getType(), STRING);
      scalarTypes.put(INTEGER.getType(), INTEGER);
      scalarTypes.put(BOOLEAN.getType(), BOOLEAN);
      scalarTypes.put(DOUBLE.getType(), DOUBLE);
      scalarTypes.put(DATE.getType(), DATE);
      scalarTypes.put(TIME.getType(), TIME);
      scalarTypes.put(DATE_TIME.getType(), DATE_TIME);
      scalarTypes.put(ANY_URI.getType(), ANY_URI);
      scalarTypes.put(BASE64_BINARY.getType(), BASE64_BINARY);
      scalarTypes.put(HEX_BINARY.getType(), HEX_BINARY);
      scalarTypes.put(DAY_TIME_DURATION.getType(), DAY_TIME_DURATION);
      scalarTypes.put(YEAR_MONTH_DURATION.getType(), YEAR_MONTH_DURATION);
      scalarTypes.put(RFC822_NAME.getType(), RFC822_NAME);
      scalarTypes.put(X500_NAME.getType(), X500_NAME);
      scalarTypes.put(IP_ADDRESS.getType(), IP_ADDRESS);
      scalarTypes.put(DNS_ADDRESS.getType(), DNS_ADDRESS);
      scalarTypes.put(CHARACTER.getType(), CHARACTER);
      scalarTypes.put(FLOAT.getType(), FLOAT);
      scalarTypes.put(LONG.getType(), LONG);
      scalarTypes.put(DECIMAL.getType(), DECIMAL);
      scalarTypes.put(OBJECT.getType(), OBJECT);
      scalarTypes.put(CLASS.getType(), CLASS);
   }
}
