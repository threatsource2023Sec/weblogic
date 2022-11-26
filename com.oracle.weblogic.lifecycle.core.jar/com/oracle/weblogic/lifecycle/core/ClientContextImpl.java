package com.oracle.weblogic.lifecycle.core;

import com.oracle.weblogic.lifecycle.ClientContext;
import java.util.Locale;
import org.jvnet.hk2.annotations.Service;

@Service
public class ClientContextImpl implements ClientContext {
   private ThreadLocal locale = new ThreadLocal() {
      protected Locale initialValue() {
         return Locale.US;
      }
   };

   public Locale getLocale() {
      return (Locale)this.locale.get();
   }

   public void setLocale(Locale locale) {
      this.locale.set(locale);
   }
}
