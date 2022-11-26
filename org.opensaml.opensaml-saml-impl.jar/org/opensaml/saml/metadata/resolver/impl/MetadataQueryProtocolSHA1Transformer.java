package org.opensaml.saml.metadata.resolver.impl;

import com.google.common.base.Function;
import java.security.NoSuchAlgorithmException;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.codec.StringDigester;
import net.shibboleth.utilities.java.support.codec.StringDigester.OutputFormat;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;

public class MetadataQueryProtocolSHA1Transformer implements Function {
   private StringDigester digester;

   public MetadataQueryProtocolSHA1Transformer() {
      try {
         this.digester = new StringDigester("SHA-1", OutputFormat.HEX_LOWER);
      } catch (NoSuchAlgorithmException var2) {
      }

   }

   @Nullable
   public String apply(@Nullable String input) {
      String entityID = (String)Constraint.isNotNull(StringSupport.trimOrNull(input), "Entity ID was null or empty");
      String digested = this.digester.apply(entityID);
      return digested == null ? null : "{sha1}" + digested;
   }
}
