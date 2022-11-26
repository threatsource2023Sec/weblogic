package weblogic.apache.xerces.dom3.as;

/** @deprecated */
public interface ASDataType {
   short STRING_DATATYPE = 1;
   short NOTATION_DATATYPE = 10;
   short ID_DATATYPE = 11;
   short IDREF_DATATYPE = 12;
   short IDREFS_DATATYPE = 13;
   short ENTITY_DATATYPE = 14;
   short ENTITIES_DATATYPE = 15;
   short NMTOKEN_DATATYPE = 16;
   short NMTOKENS_DATATYPE = 17;
   short BOOLEAN_DATATYPE = 100;
   short FLOAT_DATATYPE = 101;
   short DOUBLE_DATATYPE = 102;
   short DECIMAL_DATATYPE = 103;
   short HEXBINARY_DATATYPE = 104;
   short BASE64BINARY_DATATYPE = 105;
   short ANYURI_DATATYPE = 106;
   short QNAME_DATATYPE = 107;
   short DURATION_DATATYPE = 108;
   short DATETIME_DATATYPE = 109;
   short DATE_DATATYPE = 110;
   short TIME_DATATYPE = 111;
   short GYEARMONTH_DATATYPE = 112;
   short GYEAR_DATATYPE = 113;
   short GMONTHDAY_DATATYPE = 114;
   short GDAY_DATATYPE = 115;
   short GMONTH_DATATYPE = 116;
   short INTEGER = 117;
   short NAME_DATATYPE = 200;
   short NCNAME_DATATYPE = 201;
   short NORMALIZEDSTRING_DATATYPE = 202;
   short TOKEN_DATATYPE = 203;
   short LANGUAGE_DATATYPE = 204;
   short NONPOSITIVEINTEGER_DATATYPE = 205;
   short NEGATIVEINTEGER_DATATYPE = 206;
   short LONG_DATATYPE = 207;
   short INT_DATATYPE = 208;
   short SHORT_DATATYPE = 209;
   short BYTE_DATATYPE = 210;
   short NONNEGATIVEINTEGER_DATATYPE = 211;
   short UNSIGNEDLONG_DATATYPE = 212;
   short UNSIGNEDINT_DATATYPE = 213;
   short UNSIGNEDSHORT_DATATYPE = 214;
   short UNSIGNEDBYTE_DATATYPE = 215;
   short POSITIVEINTEGER_DATATYPE = 216;
   short OTHER_SIMPLE_DATATYPE = 1000;
   short COMPLEX_DATATYPE = 1001;

   short getDataType();
}