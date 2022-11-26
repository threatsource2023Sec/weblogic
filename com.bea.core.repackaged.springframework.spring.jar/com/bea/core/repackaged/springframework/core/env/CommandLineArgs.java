package com.bea.core.repackaged.springframework.core.env;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

class CommandLineArgs {
   private final Map optionArgs = new HashMap();
   private final List nonOptionArgs = new ArrayList();

   public void addOptionArg(String optionName, @Nullable String optionValue) {
      if (!this.optionArgs.containsKey(optionName)) {
         this.optionArgs.put(optionName, new ArrayList());
      }

      if (optionValue != null) {
         ((List)this.optionArgs.get(optionName)).add(optionValue);
      }

   }

   public Set getOptionNames() {
      return Collections.unmodifiableSet(this.optionArgs.keySet());
   }

   public boolean containsOption(String optionName) {
      return this.optionArgs.containsKey(optionName);
   }

   @Nullable
   public List getOptionValues(String optionName) {
      return (List)this.optionArgs.get(optionName);
   }

   public void addNonOptionArg(String value) {
      this.nonOptionArgs.add(value);
   }

   public List getNonOptionArgs() {
      return Collections.unmodifiableList(this.nonOptionArgs);
   }
}
