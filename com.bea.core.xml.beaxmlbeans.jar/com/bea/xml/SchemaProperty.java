package com.bea.xml;

import java.math.BigInteger;
import javax.xml.namespace.QName;

public interface SchemaProperty {
   int NEVER = 0;
   int VARIABLE = 1;
   int CONSISTENTLY = 2;
   int XML_OBJECT = 0;
   int JAVA_FIRST_PRIMITIVE = 1;
   int JAVA_BOOLEAN = 1;
   int JAVA_FLOAT = 2;
   int JAVA_DOUBLE = 3;
   int JAVA_BYTE = 4;
   int JAVA_SHORT = 5;
   int JAVA_INT = 6;
   int JAVA_LONG = 7;
   int JAVA_LAST_PRIMITIVE = 7;
   int JAVA_BIG_DECIMAL = 8;
   int JAVA_BIG_INTEGER = 9;
   int JAVA_STRING = 10;
   int JAVA_BYTE_ARRAY = 11;
   int JAVA_GDATE = 12;
   int JAVA_GDURATION = 13;
   int JAVA_DATE = 14;
   int JAVA_QNAME = 15;
   int JAVA_LIST = 16;
   int JAVA_CALENDAR = 17;
   int JAVA_ENUM = 18;
   int JAVA_OBJECT = 19;
   int JAVA_USER = 20;

   SchemaType getContainerType();

   QName getName();

   QName[] acceptedNames();

   String getJavaPropertyName();

   boolean isReadOnly();

   boolean isAttribute();

   SchemaType getType();

   SchemaType javaBasedOnType();

   boolean extendsJavaSingleton();

   boolean extendsJavaOption();

   boolean extendsJavaArray();

   int getJavaTypeCode();

   QNameSet getJavaSetterDelimiter();

   BigInteger getMinOccurs();

   BigInteger getMaxOccurs();

   int hasNillable();

   int hasDefault();

   int hasFixed();

   String getDefaultText();

   XmlAnySimpleType getDefaultValue();
}
