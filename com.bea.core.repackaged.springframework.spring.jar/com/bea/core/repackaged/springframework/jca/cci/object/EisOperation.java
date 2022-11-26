package com.bea.core.repackaged.springframework.jca.cci.object;

import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.jca.cci.core.CciTemplate;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import javax.resource.cci.ConnectionFactory;
import javax.resource.cci.InteractionSpec;

public abstract class EisOperation implements InitializingBean {
   private CciTemplate cciTemplate = new CciTemplate();
   @Nullable
   private InteractionSpec interactionSpec;

   public void setCciTemplate(CciTemplate cciTemplate) {
      Assert.notNull(cciTemplate, (String)"CciTemplate must not be null");
      this.cciTemplate = cciTemplate;
   }

   public CciTemplate getCciTemplate() {
      return this.cciTemplate;
   }

   public void setConnectionFactory(ConnectionFactory connectionFactory) {
      this.cciTemplate.setConnectionFactory(connectionFactory);
   }

   public void setInteractionSpec(@Nullable InteractionSpec interactionSpec) {
      this.interactionSpec = interactionSpec;
   }

   @Nullable
   public InteractionSpec getInteractionSpec() {
      return this.interactionSpec;
   }

   public void afterPropertiesSet() {
      this.cciTemplate.afterPropertiesSet();
      if (this.interactionSpec == null) {
         throw new IllegalArgumentException("InteractionSpec is required");
      }
   }
}
