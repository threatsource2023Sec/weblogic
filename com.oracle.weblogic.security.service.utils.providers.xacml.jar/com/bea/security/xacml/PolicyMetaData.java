package com.bea.security.xacml;

public interface PolicyMetaData {
   String getValue();

   String getElementName();

   String[] getIndexKeys();

   String getIndexValue(String var1);
}
