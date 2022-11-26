package org.apache.openjpa.jdbc.meta;

import java.lang.reflect.Modifier;
import org.apache.openjpa.jdbc.meta.strats.NoneFieldStrategy;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.MetaDataException;

public class RuntimeStrategyInstaller extends StrategyInstaller {
   private static final Localizer _loc = Localizer.forPackage(RuntimeStrategyInstaller.class);

   public RuntimeStrategyInstaller(MappingRepository repos) {
      super(repos);
   }

   public void installStrategy(ClassMapping cls) {
      if ((cls.getSourceMode() & 2) == 0) {
         throw new MetaDataException(_loc.get("no-mapping", (Object)cls));
      } else {
         ClassStrategy strat = this.repos.namedStrategy(cls);
         if (strat == null) {
            strat = this.repos.defaultStrategy(cls, false);
         }

         cls.setStrategy(strat, Boolean.FALSE);
      }
   }

   public void installStrategy(FieldMapping field) {
      FieldStrategy strategy = this.repos.namedStrategy(field, true);
      if (strategy == null) {
         try {
            strategy = this.repos.defaultStrategy(field, true, false);
         } catch (MetaDataException var5) {
            Class cls = field.getDefiningMetaData().getDescribedType();
            if (!Modifier.isAbstract(cls.getModifiers()) || field.getMappedBy() != null || field.getMappingInfo().hasSchemaComponents() || field.getValueInfo().hasSchemaComponents() || field.getElementMapping().getValueInfo().hasSchemaComponents() || field.getKeyMapping().getValueInfo().hasSchemaComponents()) {
               throw var5;
            }

            strategy = NoneFieldStrategy.getInstance();
         }
      }

      field.setStrategy((FieldStrategy)strategy, Boolean.FALSE);
   }

   public void installStrategy(Version version) {
      VersionStrategy strat = this.repos.namedStrategy(version);
      if (strat == null) {
         strat = this.repos.defaultStrategy(version, false);
      }

      version.setStrategy(strat, Boolean.FALSE);
   }

   public void installStrategy(Discriminator discrim) {
      DiscriminatorStrategy strat = this.repos.namedStrategy(discrim);
      if (strat == null) {
         strat = this.repos.defaultStrategy(discrim, false);
      }

      discrim.setStrategy(strat, Boolean.FALSE);
   }
}
