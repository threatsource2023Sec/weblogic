package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;

public final class WSTrustConstants {
   public static final String WST_VERSION = "1.3";
   public static final String WST_PREFIX = "wst";
   public static final String WST_NS = "http://docs.oasis-open.org/ws-sx/ws-trust/200512";
   public static final String WSA_ACTION_RST_ISSUE = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/RST/Issue";
   public static final String WSA_ACTION_RST_CANCEL = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/RST/Cancel";
   public static final String WSA_ACTION_RST_STSCANCEL = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/RST/STSCancel";
   public static final String WSA_ACTION_RST_VALIDATE = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/RST/Validate";
   public static final String WSA_ACTION_RST_RENEW = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/RST/Renew";
   public static final String WSA_ACTION_RST_KET = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/RST/KET";
   public static final String WSA_ACTION_RSTR_ISSUE = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/RSTR/Issue";
   public static final String WSA_ACTION_RSTR_CANCEL = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/RSTR/Cancel";
   public static final String WSA_ACTION_RSTR_CANCEL_FINAL = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/RSTR/CancelFinal";
   public static final String WSA_ACTION_RSTR_VALIDATE = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/RSTR/Validate";
   public static final String WSA_ACTION_RSTR_VALIDATE_FINAL = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/RSTR/ValidateFinal";
   public static final String WSA_ACTION_RSTR_RENEW = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/RSTR/Renew";
   public static final String WSA_ACTION_RSTR_RENEW_FINAL = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/RSTR/RenewFinal";
   public static final String WSA_ACTION_RSTR_KET = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/RSTR/KET";
   public static final String WSA_ACTION_RSTR_KET_FINAL = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/RSTR/KETFinal";
   public static final String WSA_ACTION_RSTRC_ISSUE_FINAL = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/RSTRC/IssueFinal";
   public static final QName SOAP_FAULT_INVALID_REQUEST = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "InvalidRequest", "wst");
   public static final QName SOAP_FAULT_FAILED_AUTHENTICATION = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "FailedAuthentication", "wst");
   public static final QName SOAP_FAULT_REQUEST_FAILED = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestFailed", "wst");
   public static final QName SOAP_FAULT_INVALID_SECURITY_TOKEN = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "InvalidSecurityToken", "wst");
   public static final QName SOAP_FAULT_AUTHENTICATION_BAD_ELEMENTS = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "AuthenticationBadElements", "wst");
   public static final QName SOAP_FAULT_BAD_REQUEST = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "BadRequest", "wst");
   public static final QName SOAP_FAULT_EXPIRED_DATA = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "ExpiredData", "wst");
   public static final QName SOAP_FAULT_INVALID_TIME_RANGE = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "InvalidTimeRange", "wst");
   public static final QName SOAP_FAULT_INVALID_SCOPE = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "InvalidScope", "wst");
   public static final QName SOAP_FAULT_RENEW_NEEDED = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RenewNeeded", "wst");
   public static final QName SOAP_FAULT_UNABLE_TO_RENEW = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "UnableToRenew", "wst");

   private WSTrustConstants() {
   }
}
