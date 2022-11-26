package org.opensaml.saml.common.xml;

import java.io.InputStream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import javax.xml.validation.Schema;
import net.shibboleth.utilities.java.support.annotation.ParameterName;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.xml.ClasspathResolver;
import net.shibboleth.utilities.java.support.xml.SchemaBuilder;
import org.opensaml.core.xml.XMLRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

@ThreadSafe
public class SAMLSchemaBuilder {
   @Nonnull
   @NonnullElements
   @NotEmpty
   private static String[] baseXMLSchemas = new String[]{"/schema/xml.xsd", "/schema/XMLSchema.xsd", "/schema/xmldsig-core-schema.xsd", "/schema/xenc-schema.xsd", "/schema/xmldsig11-schema.xsd", "/schema/xenc11-schema.xsd"};
   @Nonnull
   @NonnullElements
   @NotEmpty
   private static String[] soapSchemas = new String[]{"/schema/soap-envelope.xsd"};
   @Nonnull
   @NonnullElements
   @NotEmpty
   private static String[] saml10Schemas = new String[]{"/schema/oasis-sstc-saml-schema-assertion-1.0.xsd", "/schema/oasis-sstc-saml-schema-protocol-1.0.xsd"};
   @Nonnull
   @NonnullElements
   @NotEmpty
   private static String[] saml11Schemas = new String[]{"/schema/oasis-sstc-saml-schema-assertion-1.1.xsd", "/schema/oasis-sstc-saml-schema-protocol-1.1.xsd"};
   @Nonnull
   @NonnullElements
   @NotEmpty
   private static String[] saml20Schemas = new String[]{"/schema/saml-schema-assertion-2.0.xsd", "/schema/saml-schema-protocol-2.0.xsd", "/schema/saml-schema-metadata-2.0.xsd", "/schema/saml-schema-authn-context-2.0.xsd", "/schema/saml-schema-authn-context-auth-telephony-2.0.xsd", "/schema/saml-schema-authn-context-ip-2.0.xsd", "/schema/saml-schema-authn-context-ippword-2.0.xsd", "/schema/saml-schema-authn-context-kerberos-2.0.xsd", "/schema/saml-schema-authn-context-mobileonefactor-reg-2.0.xsd", "/schema/saml-schema-authn-context-mobileonefactor-unreg-2.0.xsd", "/schema/saml-schema-authn-context-mobiletwofactor-reg-2.0.xsd", "/schema/saml-schema-authn-context-mobiletwofactor-unreg-2.0.xsd", "/schema/saml-schema-authn-context-nomad-telephony-2.0.xsd", "/schema/saml-schema-authn-context-personal-telephony-2.0.xsd", "/schema/saml-schema-authn-context-pgp-2.0.xsd", "/schema/saml-schema-authn-context-ppt-2.0.xsd", "/schema/saml-schema-authn-context-pword-2.0.xsd", "/schema/saml-schema-authn-context-session-2.0.xsd", "/schema/saml-schema-authn-context-smartcard-2.0.xsd", "/schema/saml-schema-authn-context-smartcardpki-2.0.xsd", "/schema/saml-schema-authn-context-softwarepki-2.0.xsd", "/schema/saml-schema-authn-context-spki-2.0.xsd", "/schema/saml-schema-authn-context-srp-2.0.xsd", "/schema/saml-schema-authn-context-sslcert-2.0.xsd", "/schema/saml-schema-authn-context-telephony-2.0.xsd", "/schema/saml-schema-authn-context-timesync-2.0.xsd", "/schema/saml-schema-authn-context-x509-2.0.xsd", "/schema/saml-schema-authn-context-xmldsig-2.0.xsd", "/schema/saml-schema-dce-2.0.xsd", "/schema/saml-schema-ecp-2.0.xsd", "/schema/saml-schema-x500-2.0.xsd", "/schema/saml-schema-xacml-2.0.xsd"};
   @Nonnull
   @NonnullElements
   @NotEmpty
   private static String[] baseExtSchemas = new String[]{"/schema/sstc-saml1x-metadata.xsd", "/schema/sstc-saml-idp-discovery.xsd", "/schema/sstc-saml-protocol-ext-thirdparty.xsd", "/schema/sstc-saml-metadata-ext-query.xsd", "/schema/sstc-saml-delegation.xsd", "/schema/sstc-saml-metadata-ui-v1.0.xsd", "/schema/sstc-metadata-attr.xsd", "/schema/saml-metadata-rpi-v1.0.xsd", "/schema/sstc-saml-metadata-algsupport-v1.0.xsd", "/schema/sstc-saml-channel-binding-ext-v1.0.xsd", "/schema/saml-async-slo-v1.0.xsd", "/schema/ietf-kitten-sasl-saml-ec.xsd"};
   private Logger log = LoggerFactory.getLogger(SAMLSchemaBuilder.class);
   private boolean unresolvedSchemaFatal = true;
   @Nullable
   private Schema cachedSchema;
   @Nonnull
   @NonnullElements
   @NotEmpty
   private String[] saml1xSchemas;
   @Nonnull
   private SchemaBuilder schemaBuilder;

   public SAMLSchemaBuilder(@Nonnull @ParameterName(name = "ver") SAML1Version ver) {
      if (ver == SAMLSchemaBuilder.SAML1Version.SAML_11) {
         this.saml1xSchemas = saml11Schemas;
      } else {
         this.saml1xSchemas = saml10Schemas;
      }

   }

   public void setUnresolvedSchemaFatal(boolean flag) {
      this.unresolvedSchemaFatal = flag;
   }

   public synchronized void setSchemaBuilder(@Nonnull SchemaBuilder builder) {
      this.schemaBuilder = (SchemaBuilder)Constraint.isNotNull(builder, "SchemaBuilder cannot be null");
      this.cachedSchema = null;
      this.configureBuilder();
   }

   @Nonnull
   public synchronized Schema getSAMLSchema() throws SAXException {
      if (this.cachedSchema == null) {
         if (this.schemaBuilder == null) {
            try {
               this.schemaBuilder = new SchemaBuilder();
               this.schemaBuilder.setResourceResolver(new ClasspathResolver());
               this.configureBuilder();
            } catch (RuntimeException var2) {
               this.schemaBuilder = null;
               throw var2;
            }
         }

         this.cachedSchema = this.schemaBuilder.buildSchema();
         return this.cachedSchema;
      } else {
         return this.cachedSchema;
      }
   }

   @Nonnull
   private void configureBuilder() {
      String[] var1 = baseXMLSchemas;
      int var2 = var1.length;

      int var3;
      String source;
      for(var3 = 0; var3 < var2; ++var3) {
         source = var1[var3];
         this.addSchemaToBuilder(source);
      }

      var1 = soapSchemas;
      var2 = var1.length;

      for(var3 = 0; var3 < var2; ++var3) {
         source = var1[var3];
         this.addSchemaToBuilder(source);
      }

      var1 = this.saml1xSchemas;
      var2 = var1.length;

      for(var3 = 0; var3 < var2; ++var3) {
         source = var1[var3];
         this.addSchemaToBuilder(source);
      }

      var1 = saml20Schemas;
      var2 = var1.length;

      for(var3 = 0; var3 < var2; ++var3) {
         source = var1[var3];
         this.addSchemaToBuilder(source);
      }

      var1 = baseExtSchemas;
      var2 = var1.length;

      for(var3 = 0; var3 < var2; ++var3) {
         source = var1[var3];
         this.addSchemaToBuilder(source);
      }

   }

   private void addSchemaToBuilder(@Nonnull String source) {
      Class clazz = SAMLSchemaBuilder.class;
      InputStream stream = clazz.getResourceAsStream(source);
      if (stream != null) {
         this.schemaBuilder.addSchema(stream);
      } else {
         this.log.error("Failed to locate schema resource: {}", source);
         if (this.unresolvedSchemaFatal) {
            throw new XMLRuntimeException("Failed to locate schema resource: " + source);
         }
      }

   }

   public static enum SAML1Version {
      SAML_10,
      SAML_11;
   }
}
