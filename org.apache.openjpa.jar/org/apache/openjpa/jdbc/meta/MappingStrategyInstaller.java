package org.apache.openjpa.jdbc.meta;

public class MappingStrategyInstaller extends StrategyInstaller {
   public MappingStrategyInstaller(MappingRepository repos) {
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

      cls.setStrategy(strat, Boolean.TRUE);
      cls.setSourceMode(2, true);
   }

   public void installStrategy(FieldMapping field) {
      FieldStrategy strategy = this.repos.namedStrategy(field, true);
      if (strategy == null) {
         strategy = this.repos.defaultStrategy(field, true, true);
      }

      field.setStrategy(strategy, Boolean.TRUE);
   }

   public void installStrategy(Version version) {
      VersionStrategy strat = this.repos.namedStrategy(version);
      if (strat == null) {
         strat = this.repos.defaultStrategy(version, true);
      }

      version.setStrategy(strat, Boolean.TRUE);
   }

   public void installStrategy(Discriminator discrim) {
      DiscriminatorStrategy strat = this.repos.namedStrategy(discrim);
      if (strat == null) {
         strat = this.repos.defaultStrategy(discrim, true);
      }

      discrim.setStrategy(strat, Boolean.TRUE);
   }
}
