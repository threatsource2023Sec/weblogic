package com.bea.faces;

import com.sun.faces.spi.InjectionProvider;
import com.sun.faces.spi.InjectionProviderException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import weblogic.servlet.internal.WebComponentCreatorInternal;

public class WeblogicInjectionProvider implements InjectionProvider {
   private static final String INJECTION_BINDING = "java:bea/WebComponentCreator";
   private WebComponentCreatorInternal creator = null;

   public WeblogicInjectionProvider() {
      try {
         Context ctx = new InitialContext();
         this.creator = (WebComponentCreatorInternal)ctx.lookup("java:bea/WebComponentCreator");
      } catch (NamingException var2) {
      }

   }

   public void inject(Object obj) throws InjectionProviderException {
      if (this.creator != null) {
         try {
            this.creator.inject(obj);
         } catch (Throwable var3) {
            throw new InjectionProviderException(var3);
         }
      }
   }

   public void invokePostConstruct(Object obj) throws InjectionProviderException {
      if (this.creator != null) {
         try {
            this.creator.notifyPostConstruct(obj);
         } catch (Throwable var3) {
            throw new InjectionProviderException(var3);
         }
      }
   }

   public void invokePreDestroy(Object obj) throws InjectionProviderException {
      if (this.creator != null) {
         try {
            this.creator.notifyPreDestroy(obj);
         } catch (Throwable var3) {
            throw new InjectionProviderException(var3);
         }
      }
   }
}
