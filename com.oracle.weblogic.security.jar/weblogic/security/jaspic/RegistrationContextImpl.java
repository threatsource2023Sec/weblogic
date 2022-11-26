package weblogic.security.jaspic;

import javax.security.auth.message.config.AuthConfigFactory;

final class RegistrationContextImpl implements AuthConfigFactory.RegistrationContext {
   private final String messageLayer;
   private final String appContext;
   private final String description;
   private final boolean isPersistent;

   RegistrationContextImpl(String messageLayer, String appContext, String description, boolean persistent) {
      this.messageLayer = messageLayer;
      this.appContext = appContext;
      this.description = description;
      this.isPersistent = persistent;
   }

   RegistrationContextImpl(AuthConfigFactory.RegistrationContext ctx) {
      this.messageLayer = ctx.getMessageLayer();
      this.appContext = ctx.getAppContext();
      this.description = ctx.getDescription();
      this.isPersistent = ctx.isPersistent();
   }

   public String getMessageLayer() {
      return this.messageLayer;
   }

   public String getAppContext() {
      return this.appContext;
   }

   public String getDescription() {
      return this.description;
   }

   public boolean isPersistent() {
      return this.isPersistent;
   }

   public boolean equals(Object o) {
      if (o != null && o instanceof AuthConfigFactory.RegistrationContext) {
         AuthConfigFactory.RegistrationContext target = (AuthConfigFactory.RegistrationContext)o;
         return EntryInfo.matchStrings(this.messageLayer, target.getMessageLayer()) && EntryInfo.matchStrings(this.appContext, target.getAppContext()) && EntryInfo.matchStrings(this.description, target.getDescription());
      } else {
         return false;
      }
   }
}
