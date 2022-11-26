package org.apache.openjpa.jdbc.meta.strats;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.Discriminator;
import org.apache.openjpa.jdbc.meta.DiscriminatorMappingInfo;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.Schemas;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.JavaTypes;
import org.apache.openjpa.util.MetaDataException;

public class ValueMapDiscriminatorStrategy extends InValueDiscriminatorStrategy {
   public static final String ALIAS = "value-map";
   private static final Localizer _loc = Localizer.forPackage(ValueMapDiscriminatorStrategy.class);
   private Map _vals = null;

   public String getAlias() {
      return "value-map";
   }

   protected int getJavaType() {
      Object val = this.disc.getValue();
      if (val != null && val != Discriminator.NULL) {
         return JavaTypes.getTypeCode(val.getClass());
      } else {
         DiscriminatorMappingInfo info = this.disc.getMappingInfo();
         List cols = info.getColumns();
         Column col = cols.isEmpty() ? null : (Column)cols.get(0);
         if (col != null) {
            if (col.getJavaType() != 8) {
               return col.getJavaType();
            }

            if (col.getType() != 1111) {
               return JavaTypes.getTypeCode(Schemas.getJavaType(col.getType(), col.getSize(), col.getDecimalDigits()));
            }
         }

         return 9;
      }
   }

   protected Object getDiscriminatorValue(ClassMapping cls) {
      Object val = cls.getDiscriminator().getValue();
      return val == Discriminator.NULL ? null : val;
   }

   protected Class getClass(Object val, JDBCStore store) throws ClassNotFoundException {
      if (this._vals == null) {
         ClassMapping cls = this.disc.getClassMapping();
         ClassMapping[] subs = cls.getJoinablePCSubclassMappings();
         Map map = new HashMap((int)((double)(subs.length + 1) * 1.33 + 1.0));
         mapDiscriminatorValue(cls, map);

         for(int i = 0; i < subs.length; ++i) {
            mapDiscriminatorValue(subs[i], map);
         }

         this._vals = map;
      }

      String str = val == null ? null : val.toString();
      Class cls = (Class)this._vals.get(str);
      if (cls != null) {
         return cls;
      } else {
         throw new ClassNotFoundException(_loc.get("unknown-discrim-value", new Object[]{str, this.disc.getClassMapping().getDescribedType().getName(), new TreeSet(this._vals.keySet())}).getMessage());
      }
   }

   private static void mapDiscriminatorValue(ClassMapping cls, Map map) {
      Object val = cls.getDiscriminator().getValue();
      if (val != null) {
         String str = val == Discriminator.NULL ? null : val.toString();
         Class exist = (Class)map.get(str);
         if (exist != null) {
            throw new MetaDataException(_loc.get("dup-discrim-value", str, exist, cls));
         } else {
            map.put(str, cls.getDescribedType());
         }
      }
   }

   public void map(boolean adapt) {
      Object val = this.disc.getMappingInfo().getValue(this.disc, adapt);
      if (val == null && !Modifier.isAbstract(this.disc.getClassMapping().getDescribedType().getModifiers())) {
         throw new MetaDataException(_loc.get("no-discrim-value", (Object)this.disc.getClassMapping()));
      } else {
         this.disc.setValue(val);
         super.map(adapt);
      }
   }
}
