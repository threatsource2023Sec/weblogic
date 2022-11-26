package org.jboss.weld.logging;

public enum Category {
   BOOTSTRAP("Bootstrap"),
   BOOTSTRAP_TRACKER("BootstrapTracker"),
   VERSION("Version"),
   UTIL("Utilities"),
   BEAN("Bean"),
   SERVLET("Servlet"),
   REFLECTION("Reflection"),
   JSF("JSF"),
   EVENT("Event"),
   CONVERSATION("Conversation"),
   CONTEXT("Context"),
   EL("El"),
   RESOLUTION("Resolution"),
   BEAN_MANAGER("BeanManager"),
   VALIDATOR("Validator"),
   INTERCEPTOR("Interceptor"),
   SERIALIZATION("Serialization"),
   CONFIGURATION("Configuration");

   private static final String LOG_PREFIX = "org.jboss.weld.";
   private final String name;

   private Category(String name) {
      this.name = createName(name);
   }

   public String getName() {
      return this.name;
   }

   private static String createName(String name) {
      return "org.jboss.weld." + name;
   }
}
