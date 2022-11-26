package org.apache.openjpa.jdbc.meta;

import org.apache.openjpa.jdbc.meta.strats.NoneClassStrategy;
import org.apache.openjpa.jdbc.meta.strats.NoneDiscriminatorStrategy;
import org.apache.openjpa.jdbc.meta.strats.NoneFieldStrategy;
import org.apache.openjpa.jdbc.meta.strats.NoneVersionStrategy;

public class NoneStrategyInstaller extends StrategyInstaller {
   public NoneStrategyInstaller(MappingRepository repos) {
      super(repos);
   }

   public void installStrategy(ClassMapping cls) {
      cls.clearMapping();
      cls.setStrategy(NoneClassStrategy.getInstance(), (Boolean)null);
      cls.setSourceMode(2, true);
   }

   public void installStrategy(FieldMapping field) {
      field.clearMapping();
      field.setStrategy(NoneFieldStrategy.getInstance(), (Boolean)null);
   }

   public void installStrategy(Version version) {
      version.clearMapping();
      version.setStrategy(NoneVersionStrategy.getInstance(), (Boolean)null);
   }

   public void installStrategy(Discriminator discrim) {
      discrim.clearMapping();
      discrim.setStrategy(NoneDiscriminatorStrategy.getInstance(), (Boolean)null);
   }
}
