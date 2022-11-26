package weblogic.xml.saaj;

import java.util.Arrays;
import java.util.List;
import javax.xml.namespace.QName;

public final class SOAPConstants {
   public static final String ENV_PREFIX = "env";
   public static final String ENVELOPE = "Envelope";
   public static final String BODY = "Body";
   public static final String HEADER = "Header";
   public static final String FAULT = "Fault";
   public static final String FAULT_CODE = "faultcode";
   public static final String DEFAULT_FAULT_CODE = "Server";
   public static final String DEFAULT_FAULT12_CODE = "Sender";
   public static final String DEFAULT_FAULT_REASON_TEXT = "Server Error";
   public static final String FAULT_STRING = "faultstring";
   public static final List VALID_FAULT_CODES_LIST = Arrays.asList("VersionMismatch", "MustUnderstand", "Client", "Server");
   public static final String FAULT_ACTOR = "faultactor";
   public static final String LOCALE = "locale";
   public static final String DETAIL = "detail";
   public static final String DETAIL_SOAP12 = "Detail";
   public static final String MUST_UNDERSTAND = "mustUnderstand";
   public static final String ACTOR = "actor";
   public static final String NO_NAMESPACE = "";
   public static final List XML_TRUE_VALUES = Arrays.asList("1", "true", "TRUE");
   public static final List XML_FALSE_VALUES = Arrays.asList("0", "false", "FALSE");
   public static final String XML_TRUE = "1";
   public static final String ENCODING_US_ASCII = "us-ascii";
   public static final String MIME_CONTENT_TYPE = "Content-Type";
   public static final String MIME_CHARSET = "charset";
   public static final String MIME_TEXT = "text";
   public static final String MIME_XML = "xml";
   public static final String MIME_TEXT_XML = "text/xml";
   public static final String MIME_SOAP_XML = "application/soap+xml";
   public static final String MIME_XOP_XML = "application/xop+xml";
   public static final String MIME_MULTIPART_RELATED = "Multipart/Related";
   public static final String MIME_BOUNDARY = "boundary=MIME_boundary";
   public static final String MIME_TYPE_TEXT_XML = "type=text/xml";
   public static final String MIME_CONTENT_ID = "Content-ID";
   public static final String MIME_CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";
   public static final String MIME_BINARY_TRANSER_ENCODING = "binary";
   public static final String STATE_SOAP_PART_MIME_HEADERS = "soapPartMimeHeaders";
   public static final String STATE_MIME_HEADERS = "mimeHeaders";
   public static final String SOAP11_NAMESPACE = "http://schemas.xmlsoap.org/soap/envelope/";
   public static final String SOAP12_NAMESPACE = "http://www.w3.org/2003/05/soap-envelope";
   public static final String SCHEMA_PREFIX = "xsd";
   public static final String SCHEMA_NAMESPACE = "http://www.w3.org/2001/XMLSchema";
   public static final String SCHEMA_INSTANCE_PREFIX = "xsi";
   public static final String SCHEMA_INSTANCE_NAMESPACE = "http://www.w3.org/2001/XMLSchema-instance";
   public static final String SOAP_ENCODING_PREFIX = "soapenc";
   public static final String SOAP11_ENCODING_NAMESPACE = "http://schemas.xmlsoap.org/soap/encoding/";
   public static final String ENCODING_STYLE = "encodingStyle";
   public static final String HTTP_TRANSPORT = "http";
   public static final String SOAPPART_CONTENT_ID = "soapPart";
   public static final String SOAP12_ACTION_PARAMETER = "weblogic.xml.saaj.action-parameter";
   public static final QName FAULT12_CODE = new QName("http://www.w3.org/2003/05/soap-envelope", "Code");
   public static final QName FAULT12_VALUE = new QName("http://www.w3.org/2003/05/soap-envelope", "Value");
   public static final QName FAULT12_SUBCODE = new QName("http://www.w3.org/2003/05/soap-envelope", "Subcode");
   public static final QName FAULT12_Reason = new QName("http://www.w3.org/2003/05/soap-envelope", "Reason");
   public static final QName FAULT12_TEXT = new QName("http://www.w3.org/2003/05/soap-envelope", "Text");
   public static final QName FAULT12_NODE = new QName("http://www.w3.org/2003/05/soap-envelope", "Node");
   public static final QName FAULT12_ROLE = new QName("http://www.w3.org/2003/05/soap-envelope", "Role");
   public static final QName FAULT12_DETAIL = new QName("http://www.w3.org/2003/05/soap-envelope", "Detail");
   public static final QName HEADER12_NOT_UNDERSTOOD = new QName("http://www.w3.org/2003/05/soap-envelope", "NotUnderstood");
   public static final QName HEADER12_UPGRADE = new QName("http://www.w3.org/2003/05/soap-envelope", "Upgrade");
   public static final QName HEADER12_SUPPORTED_ENVELOPE = new QName("http://www.w3.org/2003/05/soap-envelope", "SupportedEnvelope");
   public static final QName HEADER12_ROLE = new QName("http://www.w3.org/2003/05/soap-envelope", "role");
   public static final QName HEADER12_RELAY = new QName("http://www.w3.org/2003/05/soap-envelope", "relay");

   private SOAPConstants() {
   }

   static String getMimeType(String soapNS) {
      return "http://www.w3.org/2003/05/soap-envelope".equals(soapNS) ? "application/soap+xml" : "text/xml";
   }

   static String getMtomMimeType(String soapNS) {
      return "application/xop+xml";
   }

   static boolean isSoap(String soapNS, String mimeType) {
      return "application/soap+xml".equals(mimeType) || "text/xml".equals(mimeType) || "application/xop+xml".equals(mimeType);
   }
}
