package org.apache.openjpa.jdbc.meta;

import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.MetaDataException;

public class RefreshStrategyInstaller extends StrategyInstaller {
   private static final Localizer _loc = Localizer.forPackage(RefreshStrategyInstaller.class);

   public RefreshStrategyInstaller(MappingRepository repos) {
      super(repos);
   }

   public boolean isAdapting() {
      return true;
   }

   public void installStrategy(ClassMapping cls) {
      ClassStrategy strat = this.repos.namedStrategy(cls);
      if (strat == null) {
         strat = this.repos.defaultStrategy(cls, true);
      }

      try {
         cls.setStrategy(strat, Boolean.TRUE);
      } catch (MetaDataException var4) {
         if (isCustomStrategy(strat)) {
            throw var4;
         }

         this.repos.getLog().warn(_loc.get("fatal-change", cls, var4.getMessage()));
         cls.clearMapping();
         cls.setStrategy(this.repos.defaultStrategy(cls, true), Boolean.TRUE);
      }

      cls.setSourceMode(2, true);
   }

   public void installStrategy(FieldMapping field) {
      FieldStrategy strategy = this.repos.namedStrategy(field, true);
      if (strategy == null) {
         strategy = this.repos.defaultStrategy(field, true, true);
      }

      try {
         field.setStrategy(strategy, Boolean.TRUE);
      } catch (MetaDataException var4) {
         if (isCustomStrategy(strategy)) {
            throw var4;
         }

         this.repos.getLog().warn(_loc.get("fatal-change", field, var4.getMessage()));
         field.clearMapping();
         field.setHandler((ValueHandler)null);
         field.getKeyMapping().setHandler((ValueHandler)null);
         field.getElementMapping().setHandler((ValueHandler)null);
         field.setStrategy(this.repos.defaultStrategy(field, true, true), Boolean.TRUE);
      }

   }

   public void installStrategy(Version version) {
      VersionStrategy strat = this.repos.namedStrategy(version);
      if (strat == null) {
         strat = this.repos.defaultStrategy(version, true);
      }

      try {
         version.setStrategy(strat, Boolean.TRUE);
      } catch (MetaDataException var4) {
         if (isCustomStrategy(strat)) {
            throw var4;
         }

         this.repos.getLog().warn(_loc.get("fatal-change", version, var4.getMessage()));
         version.clearMapping();
         version.setStrategy(this.repos.defaultStrategy(version, true), Boolean.TRUE);
      }

   }

   public void installStrategy(Discriminator discrim) {
      DiscriminatorStrategy strat = this.repos.namedStrategy(discrim);
      if (strat == null) {
         strat = this.repos.defaultStrategy(discrim, true);
      }

      try {
         discrim.setStrategy(strat, Boolean.TRUE);
      } catch (MetaDataException var5) {
         if (isCustomStrategy(strat)) {
            throw var5;
         }

         this.repos.getLog().warn(_loc.get("fatal-change", discrim, var5.getMessage()));
         String val = discrim.getMappingInfo().getValue();
         discrim.clearMapping();
         discrim.getMappingInfo().setValue(val);
         discrim.setStrategy(this.repos.defaultStrategy(discrim, true), Boolean.TRUE);
      }

   }

   private static boolean isCustomStrategy(Strategy strat) {
      return !strat.getClass().getName().startsWith("org.apache.openjpa.");
   }
}
