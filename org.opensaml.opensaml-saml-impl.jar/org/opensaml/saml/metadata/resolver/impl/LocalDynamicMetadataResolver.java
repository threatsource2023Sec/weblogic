package org.opensaml.saml.metadata.resolver.impl;

import com.google.common.base.Function;
import java.io.IOException;
import java.util.Timer;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.persist.XMLObjectLoadSaveManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalDynamicMetadataResolver extends AbstractDynamicMetadataResolver {
   private Logger log;
   @Nonnull
   private XMLObjectLoadSaveManager sourceManager;
   @Nonnull
   private Function sourceKeyGenerator;

   public LocalDynamicMetadataResolver(@Nonnull XMLObjectLoadSaveManager manager) {
      this((Timer)null, manager, (Function)null);
   }

   public LocalDynamicMetadataResolver(@Nonnull XMLObjectLoadSaveManager manager, @Nullable Function keyGenerator) {
      this((Timer)null, manager, keyGenerator);
   }

   public LocalDynamicMetadataResolver(@Nullable Timer backgroundTaskTimer, @Nonnull XMLObjectLoadSaveManager manager, @Nullable Function keyGenerator) {
      super(backgroundTaskTimer);
      this.log = LoggerFactory.getLogger(LocalDynamicMetadataResolver.class);
      this.sourceManager = (XMLObjectLoadSaveManager)Constraint.isNotNull(manager, "Local source manager was null");
      this.sourceKeyGenerator = keyGenerator;
      if (this.sourceKeyGenerator == null) {
         this.sourceKeyGenerator = new EntityIDDigestGenerator();
      }

   }

   protected XMLObject fetchFromOriginSource(CriteriaSet criteria) throws IOException {
      String key = (String)this.sourceKeyGenerator.apply(criteria);
      if (key != null) {
         this.log.trace("{} Attempting to load from local source manager with generated key '{}'", this.getLogPrefix(), key);
         XMLObject result = this.sourceManager.load(key);
         if (result != null) {
            this.log.trace("{} Successfully loaded target from local source manager source with key '{}' of type: ", new Object[]{this.getLogPrefix(), key, result.getElementQName()});
         } else {
            this.log.trace("{} Found no target in local source manager with key '{}'", this.getLogPrefix(), key);
         }

         return result;
      } else {
         this.log.trace("{} Could not generate source key from criteria, can not resolve", this.getLogPrefix());
         return null;
      }
   }
}
