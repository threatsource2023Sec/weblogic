package org.opensaml.soap.wsaddressing;

import com.google.common.collect.Sets;
import java.util.Set;
import javax.xml.namespace.QName;

public final class WSAddressingConstants {
   public static final String WSA_NS = "http://www.w3.org/2005/08/addressing";
   public static final String WSA_PREFIX = "wsa";
   public static final QName SOAP_FAULT_INVALID_ADDRESSING_HEADER = new QName("http://www.w3.org/2005/08/addressing", "InvalidAddressingHeader", "wsa");
   public static final QName SOAP_FAULT_INVALID_ADDRESS = new QName("http://www.w3.org/2005/08/addressing", "InvalidAddress", "wsa");
   public static final QName SOAP_FAULT_INVALID_EPR = new QName("http://www.w3.org/2005/08/addressing", "InvalidEPR", "wsa");
   public static final QName SOAP_FAULT_INVALID_CARDINALITY = new QName("http://www.w3.org/2005/08/addressing", "InvalidCardinality", "wsa");
   public static final QName SOAP_FAULT_MISSING_ADDRESS_IN_EPR = new QName("http://www.w3.org/2005/08/addressing", "MissingAddressInEPR", "wsa");
   public static final QName SOAP_FAULT_DUPLICATE_MESSAGE_ID = new QName("http://www.w3.org/2005/08/addressing", "DuplicateMessageID", "wsa");
   public static final QName SOAP_FAULT_ACTION_MISMATCH = new QName("http://www.w3.org/2005/08/addressing", "ActionMismatch", "wsa");
   public static final QName SOAP_FAULT_MESSAGE_ADDRESSING_HEADER_REQUIRED = new QName("http://www.w3.org/2005/08/addressing", "MessageAddressingHeaderRequired", "wsa");
   public static final QName SOAP_FAULT_DESTINATION_UNREACHABLE = new QName("http://www.w3.org/2005/08/addressing", "DestinationUnreachable", "wsa");
   public static final QName SOAP_FAULT_ACTION_NOT_SUPPORTED = new QName("http://www.w3.org/2005/08/addressing", "ActionNotSupported", "wsa");
   public static final QName SOAP_FAULT_ENDPOINT_UNAVAILABLE = new QName("http://www.w3.org/2005/08/addressing", "EndpointUnavailable", "wsa");
   public static final Set WS_ADDRESSING_FAULTS;
   public static final String ACTION_URI_FAULT = "http://www.w3.org/2005/08/addressing/fault";
   public static final String ACTION_URI_SOAP_FAULT = "http://www.w3.org/2005/08/addressing/soap/fault";

   private WSAddressingConstants() {
   }

   static {
      WS_ADDRESSING_FAULTS = Sets.newHashSet(new QName[]{SOAP_FAULT_INVALID_ADDRESSING_HEADER, SOAP_FAULT_INVALID_ADDRESS, SOAP_FAULT_INVALID_EPR, SOAP_FAULT_INVALID_CARDINALITY, SOAP_FAULT_MISSING_ADDRESS_IN_EPR, SOAP_FAULT_DUPLICATE_MESSAGE_ID, SOAP_FAULT_ACTION_MISMATCH, SOAP_FAULT_MESSAGE_ADDRESSING_HEADER_REQUIRED, SOAP_FAULT_DESTINATION_UNREACHABLE, SOAP_FAULT_ACTION_NOT_SUPPORTED, SOAP_FAULT_ENDPOINT_UNAVAILABLE});
   }
}
