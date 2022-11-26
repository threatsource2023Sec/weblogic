package org.opensaml.saml.metadata.resolver.filter.impl;

import java.io.InputStream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Validator;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.xml.ClasspathResolver;
import net.shibboleth.utilities.java.support.xml.SchemaBuilder;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.xml.SAMLSchemaBuilder;
import org.opensaml.saml.metadata.resolver.filter.FilterException;
import org.opensaml.saml.metadata.resolver.filter.MetadataFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

public class SchemaValidationFilter implements MetadataFilter {
   @Nonnull
   private final Logger log;
   @Nullable
   private SchemaBuilder schemaBuilder;
   @Nonnull
   private SAMLSchemaBuilder samlSchemaBuilder;

   public SchemaValidationFilter(@Nonnull SAMLSchemaBuilder builder) {
      this(builder, (String[])null);
   }

   /** @deprecated */
   public SchemaValidationFilter(@Nonnull SAMLSchemaBuilder builder, @Nullable @NonnullElements String[] extensionSchemas) {
      this.log = LoggerFactory.getLogger(SchemaValidationFilter.class);
      this.samlSchemaBuilder = (SAMLSchemaBuilder)Constraint.isNotNull(builder, "SAMLSchemaBuilder cannot be null");
      if (extensionSchemas != null) {
         this.log.info("Overriding SchemaBuilder used to construct schemas to accomodate extension schemas");
         this.log.warn("Supplying extension schemas directly to metadata filter is deprecated");
         SchemaBuilder overriddenSchemaBuilder = new SchemaBuilder();
         overriddenSchemaBuilder.setResourceResolver(new ClasspathResolver());
         Class clazz = SAMLSchemaBuilder.class;
         String[] var5 = extensionSchemas;
         int var6 = extensionSchemas.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            String extension = var5[var7];
            String trimmed = StringSupport.trimOrNull(extension);
            if (trimmed != null) {
               InputStream stream = clazz.getResourceAsStream(trimmed);
               if (stream != null) {
                  overriddenSchemaBuilder.addSchema(stream);
               }
            }
         }

         this.samlSchemaBuilder.setSchemaBuilder(overriddenSchemaBuilder);
      }

   }

   @Nullable
   public XMLObject filter(@Nullable XMLObject metadata) throws FilterException {
      if (metadata == null) {
         return null;
      } else {
         Validator schemaValidator;
         try {
            schemaValidator = this.samlSchemaBuilder.getSAMLSchema().newValidator();
         } catch (SAXException var5) {
            this.log.error("Unable to build metadata validation schema", var5);
            throw new FilterException("Unable to build metadata validation schema", var5);
         }

         try {
            schemaValidator.validate(new DOMSource(metadata.getDOM()));
            return metadata;
         } catch (Exception var4) {
            this.log.error("Incoming metadata was not schema valid", var4);
            throw new FilterException("Incoming metadata was not schema valid", var4);
         }
      }
   }
}
