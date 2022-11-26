package com.bea.xml;

import java.math.BigInteger;
import javax.xml.namespace.QName;

public interface SchemaField {
   QName getName();

   boolean isAttribute();

   boolean isNillable();

   SchemaType getType();

   BigInteger getMinOccurs();

   BigInteger getMaxOccurs();

   String getDefaultText();

   XmlAnySimpleType getDefaultValue();

   boolean isDefault();

   boolean isFixed();

   Object getUserData();
}
