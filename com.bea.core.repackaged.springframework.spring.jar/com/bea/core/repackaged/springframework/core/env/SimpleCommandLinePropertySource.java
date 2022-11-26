package com.bea.core.repackaged.springframework.core.env;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.util.Collection;
import java.util.List;

public class SimpleCommandLinePropertySource extends CommandLinePropertySource {
   public SimpleCommandLinePropertySource(String... args) {
      super((new SimpleCommandLineArgsParser()).parse(args));
   }

   public SimpleCommandLinePropertySource(String name, String[] args) {
      super(name, (new SimpleCommandLineArgsParser()).parse(args));
   }

   public String[] getPropertyNames() {
      return StringUtils.toStringArray((Collection)((CommandLineArgs)this.source).getOptionNames());
   }

   protected boolean containsOption(String name) {
      return ((CommandLineArgs)this.source).containsOption(name);
   }

   @Nullable
   protected List getOptionValues(String name) {
      return ((CommandLineArgs)this.source).getOptionValues(name);
   }

   protected List getNonOptionArgs() {
      return ((CommandLineArgs)this.source).getNonOptionArgs();
   }
}
