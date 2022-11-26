package org.opensaml.soap.wssecurity;

import javax.xml.namespace.QName;

public final class WSSecurityConstants {
   public static final String WS_SECURITY_NS = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0";
   public static final String WS_SECURITY11_NS = "http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1";
   public static final String WSU_NS = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd";
   public static final String WSU_PREFIX = "wsu";
   public static final String WSSE_NS = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
   public static final String WSSE_PREFIX = "wsse";
   public static final String WSSE11_NS = "http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd";
   public static final String WSSE11_PREFIX = "wsse11";
   public static final String WSSE_SAML_TOKEN_PROFILE_NS = "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0";
   public static final String WSSE11_SAML_TOKEN_PROFILE_NS = "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1";
   public static final String WSSE_USERNAME_TOKEN_PROFILE_NS = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0";
   public static final String WSSE_X509_TOKEN_PROFILE_NS = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0";
   public static final String WSSE_KERBEROS_TOKEN_PROFILE_NS = "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1";
   public static final QName SOAP_FAULT_UNSUPPORTED_SECURITY_TOKEN = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "UnsupportedSecurityToken", "wsse");
   public static final QName SOAP_FAULT_UNSUPPORTED_ALGORITHM = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "UnsupportedAlgorithm", "wsse");
   public static final QName SOAP_FAULT_INVALID_SECURITY = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "InvalidSecurity", "wsse");
   public static final QName SOAP_FAULT_INVALID_SECURITY_TOKEN = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "InvalidSecurityToken", "wsse");
   public static final QName SOAP_FAULT_FAILED_AUTHENTICATION = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "FailedAuthentication", "wsse");
   public static final QName SOAP_FAULT_FAILED_CHECK = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "FailedCheck", "wsse");
   public static final QName SOAP_FAULT_SECURITY_TOKEN_UNAVAILABLE = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "SecurityTokenUnavailable", "wsse");
   public static final QName SOAP_FAULT_MESSAGE_EXPIRED = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "MessageExpired", "wsu");
   public static final String USERNAME_TOKEN = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#UsernameToken";
   public static final String X509_V3 = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3";
   public static final String X509_V1 = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v1";
   public static final String X509_PKI_PATH_V1 = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509PKIPathv1";
   public static final String X509_PKCS7 = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#PKCS7";
   public static final String X509_SUBJECT_KEY_IDENTIFIER = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509SubjectKeyIdentifier";
   public static final String KERBEROS_AP_REQ = "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#Kerberosv5_AP_REQ";
   public static final String GSS_KERBEROS_AP_REQ = "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ";
   public static final String KERBEROS_AP_REQ_1510 = "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#Kerberosv5_AP_REQ1510";
   public static final String GSS_KERBEROS_AP_REQ_1510 = "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ1510";
   public static final String THUMB_PRINT_SHA1 = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#ThumbPrintSHA1";
   public static final String ENCRYPTED_KEY_SHA1 = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#EncryptedKeySHA1";

   private WSSecurityConstants() {
   }
}
