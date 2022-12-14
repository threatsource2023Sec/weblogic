package org.python.apache.xerces.xs.datatypes;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface XSDecimal {
   BigDecimal getBigDecimal();

   BigInteger getBigInteger() throws NumberFormatException;

   long getLong() throws NumberFormatException;

   int getInt() throws NumberFormatException;

   short getShort() throws NumberFormatException;

   byte getByte() throws NumberFormatException;
}
