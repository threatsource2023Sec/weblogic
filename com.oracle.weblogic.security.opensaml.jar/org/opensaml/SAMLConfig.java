package org.opensaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.w3c.dom.Element;
import org.xml.sax.EntityResolver;

public class SAMLConfig {
   private static SAMLConfig instance;
   protected Properties properties;
   private static final DebugLogger LOGGER = SAMLServicesHelper.getDebugLogger();

   private static final void logDebug(String var0) {
      if (LOGGER != null && LOGGER.isDebugEnabled()) {
         LOGGER.debug(var0);
      }

   }

   protected SAMLConfig() {
      this.verifyUsableXmlParser();
      this.properties = new Properties();

      try {
         this.loadProperties(this.getClass().getResourceAsStream("/opensaml/conf/opensaml.properties"));
      } catch (IOException var2) {
         logDebug("Unable to load default library properties.");
      }

      SAMLCondition.conditionTypeMap.put(new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AudienceRestrictionCondition"), "org.opensaml.SAMLAudienceRestrictionCondition");
      SAMLCondition.conditionTypeMap.put(new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AudienceRestrictionConditionType"), "org.opensaml.SAMLAudienceRestrictionCondition");
      SAMLCondition.conditionTypeMap.put(new QName("urn:oasis:names:tc:SAML:1.0:assertion", "DoNotCacheCondition"), "org.opensaml.SAMLDoNotCacheCondition");
      SAMLCondition.conditionTypeMap.put(new QName("urn:oasis:names:tc:SAML:1.0:assertion", "DoNotCacheConditionType"), "org.opensaml.SAMLDoNotCacheCondition");
      SAMLQuery.queryTypeMap.put(new QName("urn:oasis:names:tc:SAML:1.0:protocol", "AttributeQuery"), "org.opensaml.SAMLAttributeQuery");
      SAMLQuery.queryTypeMap.put(new QName("urn:oasis:names:tc:SAML:1.0:protocol", "AttributeQueryType"), "org.opensaml.SAMLAttributeQuery");
      SAMLQuery.queryTypeMap.put(new QName("urn:oasis:names:tc:SAML:1.0:protocol", "AuthenticationQuery"), "org.opensaml.SAMLAuthenticationQuery");
      SAMLQuery.queryTypeMap.put(new QName("urn:oasis:names:tc:SAML:1.0:protocol", "AuthenticationQueryType"), "org.opensaml.SAMLAuthenticationQuery");
      SAMLQuery.queryTypeMap.put(new QName("urn:oasis:names:tc:SAML:1.0:protocol", "AuthorizationDecisionQuery"), "org.opensaml.SAMLAuthorizationDecisionQuery");
      SAMLQuery.queryTypeMap.put(new QName("urn:oasis:names:tc:SAML:1.0:protocol", "AuthorizationDecisionQueryType"), "org.opensaml.SAMLAuthorizationDecisionQuery");
      SAMLStatement.statementTypeMap.put(new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AttributeStatement"), "org.opensaml.SAMLAttributeStatement");
      SAMLStatement.statementTypeMap.put(new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AttributeStatementType"), "org.opensaml.SAMLAttributeStatement");
      SAMLStatement.statementTypeMap.put(new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AuthenticationStatement"), "org.opensaml.SAMLAuthenticationStatement");
      SAMLStatement.statementTypeMap.put(new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AuthenticationStatementType"), "org.opensaml.SAMLAuthenticationStatement");
      SAMLStatement.statementTypeMap.put(new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AuthorizationDecisionStatement"), "org.opensaml.SAMLAuthorizationDecisionStatement");
      SAMLStatement.statementTypeMap.put(new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AuthorizationDecisionStatementType"), "org.opensaml.SAMLAuthorizationDecisionStatement");
   }

   public static SAMLConfig instance() {
      if (instance == null) {
         instance = new SAMLConfig();
         return instance;
      } else {
         return instance;
      }
   }

   public void setProperties(Properties var1) {
      this.properties.putAll(var1);
   }

   public void loadProperties(InputStream var1) throws IOException {
      Properties var2 = new Properties();
      var2.load(var1);
      this.setProperties(var2);
   }

   public void setProperty(String var1, String var2) {
      this.properties.setProperty(var1, var2);
   }

   public String getProperty(String var1) {
      return this.properties.getProperty(var1);
   }

   public boolean getBooleanProperty(String var1) {
      return new Boolean(this.properties.getProperty(var1));
   }

   public void setBooleanProperty(String var1, Boolean var2) {
      this.setProperty(var1, var2.toString());
   }

   public void refresh() {
      XML.parserPool.registerSchema("urn:oasis:names:tc:SAML:1.0:assertion", this.getBooleanProperty("org.opensaml.compatibility-mode") ? "cs-sstc-schema-assertion-01.xsd" : "cs-sstc-schema-assertion-1.1.xsd", (EntityResolver)null);
      XML.parserPool.registerSchema("urn:oasis:names:tc:SAML:1.0:protocol", this.getBooleanProperty("org.opensaml.compatibility-mode") ? "cs-sstc-schema-protocol-01.xsd" : "cs-sstc-schema-protocol-1.1.xsd", (EntityResolver)null);
   }

   private void verifyUsableXmlParser() {
      try {
         Element.class.getDeclaredMethod("setIdAttributeNS", String.class, String.class, Boolean.TYPE);
      } catch (NoSuchMethodException var2) {
         throw new RuntimeException("OpenSAML requires an xml parser that supports DOM3 calls.  Xerces 2.5.0 (built with DOM3 support) has been included with this release and is recommended.  If you are using Java 1.4, make sure that you have enabled the Endorsed Standards Override Mechanism for this parser.");
      }
   }
}
