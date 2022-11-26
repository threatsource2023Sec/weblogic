package com.bea.core.repackaged.springframework.core.env;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.util.List;

public abstract class CommandLinePropertySource extends EnumerablePropertySource {
   public static final String COMMAND_LINE_PROPERTY_SOURCE_NAME = "commandLineArgs";
   public static final String DEFAULT_NON_OPTION_ARGS_PROPERTY_NAME = "nonOptionArgs";
   private String nonOptionArgsPropertyName = "nonOptionArgs";

   public CommandLinePropertySource(Object source) {
      super("commandLineArgs", source);
   }

   public CommandLinePropertySource(String name, Object source) {
      super(name, source);
   }

   public void setNonOptionArgsPropertyName(String nonOptionArgsPropertyName) {
      this.nonOptionArgsPropertyName = nonOptionArgsPropertyName;
   }

   public final boolean containsProperty(String name) {
      if (this.nonOptionArgsPropertyName.equals(name)) {
         return !this.getNonOptionArgs().isEmpty();
      } else {
         return this.containsOption(name);
      }
   }

   @Nullable
   public final String getProperty(String name) {
      List optionValues;
      if (this.nonOptionArgsPropertyName.equals(name)) {
         optionValues = this.getNonOptionArgs();
         return optionValues.isEmpty() ? null : StringUtils.collectionToCommaDelimitedString(optionValues);
      } else {
         optionValues = this.getOptionValues(name);
         return optionValues == null ? null : StringUtils.collectionToCommaDelimitedString(optionValues);
      }
   }

   protected abstract boolean containsOption(String var1);

   @Nullable
   protected abstract List getOptionValues(String var1);

   protected abstract List getNonOptionArgs();
}
