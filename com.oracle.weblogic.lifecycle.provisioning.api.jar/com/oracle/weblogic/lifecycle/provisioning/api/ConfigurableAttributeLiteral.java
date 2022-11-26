package com.oracle.weblogic.lifecycle.provisioning.api;

import com.oracle.weblogic.lifecycle.provisioning.api.annotations.ConfigurableAttribute;
import org.glassfish.hk2.api.AnnotationLiteral;

public final class ConfigurableAttributeLiteral extends AnnotationLiteral implements ConfigurableAttribute {
   private static final long serialVersionUID = -3210966824991506886L;
   private final String name;
   private final String description;
   private final String defaultValue;
   private final boolean sensitive;

   public ConfigurableAttributeLiteral(String name) {
      this(name, "", "", false);
   }

   public ConfigurableAttributeLiteral(ConfigurableAttribute configurableAttribute) {
      this(configurableAttribute == null ? "" : configurableAttribute.name(), configurableAttribute == null ? "" : configurableAttribute.description(), configurableAttribute == null ? "" : configurableAttribute.defaultValue(), configurableAttribute == null ? false : configurableAttribute.isSensitive());
   }

   public ConfigurableAttributeLiteral(String name, String description, String defaultValue, boolean sensitive) {
      this.name = name;
      this.description = description;
      this.defaultValue = defaultValue;
      this.sensitive = sensitive;
   }

   public final String name() {
      return this.name == null ? "" : this.name;
   }

   public final String description() {
      return this.description == null ? "" : this.description;
   }

   public final String defaultValue() {
      return this.defaultValue == null ? "" : this.defaultValue;
   }

   public final boolean isSensitive() {
      return this.sensitive;
   }

   public final String toString() {
      return this.name();
   }
}
