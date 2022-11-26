package com.bea.core.repackaged.springframework.core.env;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.CollectionUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

public class JOptCommandLinePropertySource extends CommandLinePropertySource {
   public JOptCommandLinePropertySource(OptionSet options) {
      super(options);
   }

   public JOptCommandLinePropertySource(String name, OptionSet options) {
      super(name, options);
   }

   protected boolean containsOption(String name) {
      return ((OptionSet)this.source).has(name);
   }

   public String[] getPropertyNames() {
      List names = new ArrayList();
      Iterator var2 = ((OptionSet)this.source).specs().iterator();

      while(var2.hasNext()) {
         OptionSpec spec = (OptionSpec)var2.next();
         String lastOption = (String)CollectionUtils.lastElement(spec.options());
         if (lastOption != null) {
            names.add(lastOption);
         }
      }

      return StringUtils.toStringArray((Collection)names);
   }

   @Nullable
   public List getOptionValues(String name) {
      List argValues = ((OptionSet)this.source).valuesOf(name);
      List stringArgValues = new ArrayList();
      Iterator var4 = argValues.iterator();

      while(var4.hasNext()) {
         Object argValue = var4.next();
         stringArgValues.add(argValue.toString());
      }

      if (stringArgValues.isEmpty()) {
         return ((OptionSet)this.source).has(name) ? Collections.emptyList() : null;
      } else {
         return Collections.unmodifiableList(stringArgValues);
      }
   }

   protected List getNonOptionArgs() {
      List argValues = ((OptionSet)this.source).nonOptionArguments();
      List stringArgValues = new ArrayList();
      Iterator var3 = argValues.iterator();

      while(var3.hasNext()) {
         Object argValue = var3.next();
         stringArgValues.add(argValue.toString());
      }

      return stringArgValues.isEmpty() ? Collections.emptyList() : Collections.unmodifiableList(stringArgValues);
   }
}
