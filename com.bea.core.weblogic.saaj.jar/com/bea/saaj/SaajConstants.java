package com.bea.saaj;

import java.util.Arrays;
import java.util.List;

public class SaajConstants {
   public static final String ENV_PREFIX = "soapenv";
   public static final String DEFAULT_ENV_PREFIX = "soapenv";
   public static final String ENVELOPE = "Envelope";
   public static final String BODY = "Body";
   public static final String HEADER = "Header";
   public static final String FAULT = "Fault";
   public static final String FAULT_CODE = "faultcode";
   public static final String DEFAULT_FAULT_CODE = "Server";
   public static final String DEFAULT_FAULT_STRING = "Server Error";
   public static final String FAULT_STRING = "faultstring";
   private static final String[] validFaultCodes = new String[]{"VersionMismatch", "MustUnderstand", "Client", "Server"};
   public static final List VALID_FAULT_CODES_LIST;
   public static final String FAULT_ACTOR = "faultactor";
   public static final String LOCALE = "locale";
   public static final String DETAIL = "detail";
   public static final String MUST_UNDERSTAND = "mustUnderstand";
   public static final String ACTOR = "actor";
   public static final String NO_NAMESPACE = "";
   private static final String[] xmlTrueValues;
   private static final String[] xmlFalseValues;
   public static final List XML_TRUE_VALUES;
   public static final List XML_FALSE_VALUES;
   public static final String XML_TRUE = "1";
   public static final String ENCODING_US_ASCII = "US-ASCII";
   public static final int NEW_DOC = 0;
   public static final int PARSED_DOC = 1;
   public static final String MIME_CONTENT_TYPE = "Content-Type";
   public static final String MIME_CHARSET = "charset";
   public static final String MIME_TEXT_XML = "text/xml";
   public static final String MIME_MULTIPART_RELATED = "Multipart/Related";
   public static final String MIME_BOUNDARY = "boundary=MIME_boundary";
   public static final String MIME_TYPE_TEXT_XML = "type=text/xml";
   public static final String STATE_SOAP_PART_MIME_HEADERS = "soapPartMimeHeaders";
   public static final String STATE_MIME_HEADERS = "mimeHeaders";
   public static final String SOAP11_NAMESPACE = "http://schemas.xmlsoap.org/soap/envelope/";
   public static final String SCHEMA_PREFIX = "xsd";
   public static final String SCHEMA_NAMESPACE = "http://www.w3.org/2001/XMLSchema";
   public static final String SCHEMA_INSTANCE_PREFIX = "xsi";
   public static final String SCHEMA_INSTANCE_NAMESPACE = "http://www.w3.org/2001/XMLSchema-instance";
   public static final String SOAP_ENCODING_PREFIX = "soapenc";
   public static final String SOAP11_ENCODING_NAMESPACE = "http://schemas.xmlsoap.org/soap/encoding/";
   public static String ENCODING_STYLE;
   public static final String HTTP_TRANSPORT = "http";
   public static final String SOAPPART_CONTENT_ID = "soapPart";

   static {
      VALID_FAULT_CODES_LIST = Arrays.asList((Object[])validFaultCodes);
      xmlTrueValues = new String[]{"1", "true", "TRUE"};
      xmlFalseValues = new String[]{"0", "false", "FALSE"};
      XML_TRUE_VALUES = Arrays.asList((Object[])xmlTrueValues);
      XML_FALSE_VALUES = Arrays.asList((Object[])xmlFalseValues);
      ENCODING_STYLE = "encodingStyle";
   }
}
