package com.bea.staxb.runtime.internal;

import javax.xml.namespace.QName;

final class Soap11Constants {
   static final QName ID_NAME = new QName("id");
   static final QName REF_NAME = new QName("href");
   static final String SOAPENC_URI = "http://schemas.xmlsoap.org/soap/encoding/";
   static final String SOAP12ENC_URI = "http://www.w3.org/2003/05/soap-encoding";
   static final QName ARRAY_TYPE_NAME = new QName("http://schemas.xmlsoap.org/soap/encoding/", "arrayType");
   static final QName SOAP12_ARRAY_TYPE_NAME = new QName("http://www.w3.org/2003/05/soap-encoding", "itemType");
   static final QName ARRAY_NAME = new QName("http://schemas.xmlsoap.org/soap/encoding/", "Array");
   static final QName SOAP12_ARRAYSIZE = new QName("http://www.w3.org/2003/05/soap-encoding", "arraySize");
   private static final char REF_PREFIX_CHAR = '#';
   private static final String ID_PREFIX = "i";
   private static final String REF_PREFIX = "#i";

   private Soap11Constants() {
   }

   static String constructRefValueFromId(int id) {
      return "#i" + Integer.toString(id);
   }

   static String constructIdValueFromId(int id) {
      return "i" + Integer.toString(id);
   }

   static String extractIdFromRef(String attval) {
      char firstchar = attval.charAt(0);
      return '#' == firstchar ? attval.substring(1) : null;
   }
}
