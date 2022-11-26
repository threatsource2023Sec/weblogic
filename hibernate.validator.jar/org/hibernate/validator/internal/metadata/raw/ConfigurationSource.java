package org.hibernate.validator.internal.metadata.raw;

public enum ConfigurationSource {
   ANNOTATION(0),
   XML(1),
   API(2);

   private int priority;

   private ConfigurationSource(int priority) {
      this.priority = priority;
   }

   public int getPriority() {
      return this.priority;
   }

   public static ConfigurationSource max(ConfigurationSource a, ConfigurationSource b) {
      return a.getPriority() >= b.getPriority() ? a : b;
   }
}
