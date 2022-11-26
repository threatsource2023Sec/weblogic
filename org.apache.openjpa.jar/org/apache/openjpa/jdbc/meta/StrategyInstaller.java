package org.apache.openjpa.jdbc.meta;

import java.io.Serializable;

public abstract class StrategyInstaller implements Serializable {
   protected final MappingRepository repos;

   public StrategyInstaller(MappingRepository repos) {
      this.repos = repos;
   }

   public boolean isAdapting() {
      return false;
   }

   public abstract void installStrategy(ClassMapping var1);

   public abstract void installStrategy(FieldMapping var1);

   public abstract void installStrategy(Version var1);

   public abstract void installStrategy(Discriminator var1);
}
