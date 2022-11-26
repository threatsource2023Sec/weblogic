package com.bea.common.security.utils;

public interface ContextElementDictionary {
   String SERVLET_HTTPSERVLETREQUEST = "com.bea.contextelement.servlet.HttpServletRequest";
   String SERVLET_HTTPSERVLETRESPONSE = "com.bea.contextelement.servlet.HttpServletResponse";
   String CHANNEL_PORT = "com.bea.contextelement.channel.Port";
   String CHANNEL_PUBLICPORT = "com.bea.contextelement.channel.PublicPort";
   String CHANNEL_REMOTEPORT = "com.bea.contextelement.channel.RemotePort";
   String CHANNEL_PROTOCOL = "com.bea.contextelement.channel.Protocol";
   String CHANNEL_ADDRESS = "com.bea.contextelement.channel.Address";
   String CHANNEL_PUBLICADDRESS = "com.bea.contextelement.channel.PublicAddress";
   String CHANNEL_REMOTEADDRESS = "com.bea.contextelement.channel.RemoteAddress";
   String CHANNEL_CHANNELNAME = "com.bea.contextelement.channel.ChannelName";
   String CHANNEL_SECURE = "com.bea.contextelement.channel.Secure";
   String EJB20_PARAMETER = "com.bea.contextelement.ejb20.Parameter";
   String WSEE_SOAPMESSAGE = "com.bea.contextelement.wsee.SOAPMessage";
   String WSEE_SOAPMESSAGE_JAXWS = "com.bea.contextelement.wsee.SOAPMessage.jaxws";
   String ENTITLEMENT_EAUXID = "com.bea.contextelement.entitlement.EAuxiliaryID";
   String SECURITY_CHAINPREVAILIDATEDBYSSL = "com.bea.contextelement.security.ChainPrevailidatedBySSL";
   String XML_SECURITY_TOKEN = "com.bea.contextelement.xml.SecurityToken";
   String XML_SECURITY_INFO = "com.bea.contextelement.xml.SecurityInfo";
   String XML_KEY_IDENTIFIER = "com.bea.contextelement.xml.KeyIdentifier";
   String XML_ISSUER_SERIAL = "com.bea.contextelement.xml.IssuerSerial";
   String XML_ENDPOINT_URL = "com.bea.contextelement.xml.EndpointURL";
   String XML_SECURITY_TOKEN_ASSERTION = "com.bea.contextelement.xml.SecurityTokenAssertion";
   String WEBSERVICE_INTEGRITY = "com.bea.contextelement.webservice.Integrity";
   String SAML_TARGET_RESOURCE = "com.bea.contextelement.saml.TargetResource";
   String SAML_PARTNER_ID = "com.bea.contextelement.saml.PartnerId";
   String SAML_CONSUMER_URL = "com.bea.contextelement.saml.profile.sso.ConsumerURL";
   String SAML_ASSERTION_ID = "com.bea.contextelement.saml.AssertionID";
   String SAML_ASSERTION_EXPIRE_TIME = "com.bea.contextelement.saml.AssertionExpireTime";
   String SAML_POST_FORM_TEMPLATE = "com.bea.contextelement.saml.profile.PostFormTemplate";
   String SAML_SSL_CLIENT_CERTIFICATE_CHAIN = "com.bea.contextelement.saml.SSLClientCertificateChain";
   String SAML_MESSAGE_SIGNER_CERTIFICATE = "com.bea.contextelement.saml.MessageSignerCertificate";
   String SAML_SUBJECT_CONFIRMATION = "com.bea.contextelement.saml.subject.ConfirmationMethod";
   String SAML_SUBJECT_KEYINFO_DOM = "com.bea.contextelement.saml.subject.dom.KeyInfo";
   String SAML_CACHING_REQUESTED = "com.bea.contextelement.saml.CachingRequested";
   String SAML_ATTRIBUTE_PRINCIPALS = "com.bea.contextelement.saml.AttributePrincipals";
   String WLI_MESSAGE = "com.bea.contextelement.wli.Message";
   String PKI_CREDENTIAL_ACTION = "com.bea.contextelement.pki.credAction";
   String PKI_CREDENTIAL_ALGORITHM = "com.bea.contextelement.pki.credAlgorithm";
   String WSS_SERVICE_URI = "com.bea.contextelement.wsee.serviceUri";
   String WSS_CREDENTIAL_PROVIDERS = "com.bea.contextelement.wsee.credentialProviders";
   String WSS_TOKEN_HANDLERS = "com.bea.contextelement.wsee.tokenHandlers";
   String CONTROL_PARAMETER = "com.bea.contextelement.control.Parameter";
   String JMX_OBJECT_NAME = "com.bea.contextelement.jmx.ObjectName";
   String JMX_SHORT_NAME = "com.bea.contextelement.jmx.ShortName";
   String JMX_PARAMETERS = "com.bea.contextelement.jmx.Parameters";
   String JMX_SIGNATURE = "com.bea.contextelement.jmx.Signature";
   String JMX_AUDIT_PROTECTED = "com.bea.contextelement.jmx.AuditProtectedArgInfo";
   String JMX_OLD_ATTRIBUTE_VALUE = "com.bea.contextelement.jmx.OldAttributeValue";
   String SAML2_TRANSPORT_BINDING = "com.bea.contextelement.saml2.TransportBinding";
   String SAML2_RECIPIENT_ENDPOINT = "com.bea.contextelement.saml2.RecipientEndpoint";
   String SAML2_PARTNER_NAME = "com.bea.contextelement.saml2.PartnerName";
   String SAML2_ONE_USE_POLICY_APPLIES = "com.bea.contextelement.saml2.OneUsePolicyApplies";
   String SAML2_IN_RESPONSE_TO = "com.bea.contextelement.saml2.InResponseTo";
   String SAML2_WANT_ASSERTION_SIGNED = "com.bea.contextelement.saml2.WantAssertionSigned";
   String SAML2_ENTITY_ID = "com.bea.contextelement.saml2.EntityID";
   String SAML2_WEBSSO_DATA = "com.bea.contextelement.saml2.websso.data";
   String TRUST_GROUPS = "com.bea.contextelement.security.CertificateGroup";
   String SAML_ATTRIBUTES = "com.bea.contextelement.saml.Attributes";
   String SAML2_ATTRIBUTES = "com.bea.contextelement.saml2.Attributes";
   String SAML_ATTRIBUTE_ONLY = "com.bea.contextelement.saml.AttributeOnly";
   String ADMIN_IDENTITY_DOMAIN = "com.oracle.contextelement.security.AdminIdentityDomain";
   String RESOURCE_IDENTITY_DOMAIN = "com.oracle.contextelement.security.ResourceIdentityDomain";
   String RESOURCE_SERVICE_INSTANCE_IDENTITY_APPNAME = "com.oracle.contextelement.security.ResourceServiceInstanceIdentityAppName";
}
