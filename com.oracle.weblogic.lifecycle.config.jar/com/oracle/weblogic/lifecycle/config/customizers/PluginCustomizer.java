package com.oracle.weblogic.lifecycle.config.customizers;

import com.oracle.weblogic.lifecycle.config.Plugin;
import java.util.Date;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PluginCustomizer {
   @Inject
   private AuditableCustomizer auditableCustomizer;

   public Date getCreatedOnDate(Plugin auditable) {
      return this.auditableCustomizer.getCreatedOnDate(auditable);
   }

   public Date getUpdatedOnDate(Plugin auditable) {
      return this.auditableCustomizer.getUpdatedOnDate(auditable);
   }
}
