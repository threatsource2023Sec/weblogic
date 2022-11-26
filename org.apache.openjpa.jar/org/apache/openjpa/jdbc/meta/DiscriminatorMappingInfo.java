package org.apache.openjpa.jdbc.meta;

import java.lang.reflect.Modifier;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.jdbc.meta.strats.SuperclassDiscriminatorStrategy;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.Index;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.lib.util.Localizer;

public class DiscriminatorMappingInfo extends MappingInfo {
   private static final Localizer _loc = Localizer.forPackage(DiscriminatorMappingInfo.class);
   private String _value = null;

   public String getValue() {
      return this._value;
   }

   public void setValue(String value) {
      this._value = value;
   }

   public Object getValue(Discriminator discrim, boolean adapt) {
      if (discrim.getValue() != null) {
         return discrim.getValue();
      } else if (StringUtils.isEmpty(this._value)) {
         return discrim.getMappingRepository().getMappingDefaults().getDiscriminatorValue(discrim, adapt);
      } else {
         switch (discrim.getJavaType()) {
            case 2:
               return new Character(this._value.charAt(this._value.indexOf(39) + 1));
            case 5:
               return Integer.valueOf(this._value);
            case 9:
            default:
               return this._value;
         }
      }
   }

   public Column[] getColumns(Discriminator discrim, Column[] tmplates, boolean adapt) {
      Table table = discrim.getClassMapping().getTable();
      discrim.getMappingRepository().getMappingDefaults().populateColumns(discrim, table, tmplates);
      return this.createColumns(discrim, (String)null, tmplates, table, adapt);
   }

   public Index getIndex(Discriminator discrim, Column[] cols, boolean adapt) {
      Index idx = null;
      if (cols.length > 0) {
         idx = discrim.getMappingRepository().getMappingDefaults().getIndex(discrim, cols[0].getTable(), cols);
      }

      return this.createIndex(discrim, (String)null, idx, cols, adapt);
   }

   public void syncWith(Discriminator disc) {
      this.clear(false);
      this.setColumnIO(disc.getColumnIO());
      this.syncColumns(disc, disc.getColumns(), disc.getValue() != null && !(disc.getValue() instanceof String));
      this.syncIndex(disc, disc.getIndex());
      if (disc.getValue() == Discriminator.NULL) {
         this._value = "null";
      } else if (disc.getValue() != null) {
         this._value = disc.getValue().toString();
      }

      if (disc.getStrategy() != null && !(disc.getStrategy() instanceof SuperclassDiscriminatorStrategy)) {
         ClassMapping cls = disc.getClassMapping();
         String strat = disc.getStrategy().getAlias();
         boolean sync = false;
         if (cls.isMapped() && (cls.getJoinablePCSuperclassMapping() == null || !Modifier.isFinal(cls.getDescribedType().getModifiers())) && (cls.getJoinablePCSuperclassMapping() != null || cls.getMappedPCSuperclassMapping() == null)) {
            sync = cls.getJoinablePCSuperclassMapping() != null || this._value == null || !"value-map".equals(strat);
         } else {
            sync = !"none".equals(strat);
         }

         if (sync) {
            this.setStrategy(strat);
         }

      }
   }

   protected void clear(boolean canFlags) {
      super.clear(canFlags);
      this._value = null;
   }

   public void copy(MappingInfo info) {
      super.copy(info);
      if (info instanceof DiscriminatorMappingInfo) {
         if (this._value == null) {
            this._value = ((DiscriminatorMappingInfo)info).getValue();
         }

      }
   }
}
