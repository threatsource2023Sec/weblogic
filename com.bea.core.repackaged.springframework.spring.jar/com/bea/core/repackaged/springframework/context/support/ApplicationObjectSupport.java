package com.bea.core.repackaged.springframework.context.support;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.context.ApplicationContext;
import com.bea.core.repackaged.springframework.context.ApplicationContextAware;
import com.bea.core.repackaged.springframework.context.ApplicationContextException;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;

public abstract class ApplicationObjectSupport implements ApplicationContextAware {
   protected final Log logger = LogFactory.getLog(this.getClass());
   @Nullable
   private ApplicationContext applicationContext;
   @Nullable
   private MessageSourceAccessor messageSourceAccessor;

   public final void setApplicationContext(@Nullable ApplicationContext context) throws BeansException {
      if (context == null && !this.isContextRequired()) {
         this.applicationContext = null;
         this.messageSourceAccessor = null;
      } else if (this.applicationContext == null) {
         if (!this.requiredContextClass().isInstance(context)) {
            throw new ApplicationContextException("Invalid application context: needs to be of type [" + this.requiredContextClass().getName() + "]");
         }

         this.applicationContext = context;
         this.messageSourceAccessor = new MessageSourceAccessor(context);
         this.initApplicationContext(context);
      } else if (this.applicationContext != context) {
         throw new ApplicationContextException("Cannot reinitialize with different application context: current one is [" + this.applicationContext + "], passed-in one is [" + context + "]");
      }

   }

   protected boolean isContextRequired() {
      return false;
   }

   protected Class requiredContextClass() {
      return ApplicationContext.class;
   }

   protected void initApplicationContext(ApplicationContext context) throws BeansException {
      this.initApplicationContext();
   }

   protected void initApplicationContext() throws BeansException {
   }

   @Nullable
   public final ApplicationContext getApplicationContext() throws IllegalStateException {
      if (this.applicationContext == null && this.isContextRequired()) {
         throw new IllegalStateException("ApplicationObjectSupport instance [" + this + "] does not run in an ApplicationContext");
      } else {
         return this.applicationContext;
      }
   }

   protected final ApplicationContext obtainApplicationContext() {
      ApplicationContext applicationContext = this.getApplicationContext();
      Assert.state(applicationContext != null, "No ApplicationContext");
      return applicationContext;
   }

   @Nullable
   protected final MessageSourceAccessor getMessageSourceAccessor() throws IllegalStateException {
      if (this.messageSourceAccessor == null && this.isContextRequired()) {
         throw new IllegalStateException("ApplicationObjectSupport instance [" + this + "] does not run in an ApplicationContext");
      } else {
         return this.messageSourceAccessor;
      }
   }
}
