package org.apache.openjpa.jdbc.meta.strats;

import java.util.List;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.meta.JavaSQLTypes;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.meta.ValueMappingInfo;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.kernel.DetachedValueStateManager;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.MetaDataException;
import org.apache.openjpa.util.UserException;

public class RelationStrategies {
   private static final Localizer _loc = Localizer.forPackage(RelationStrategies.class);

   public static MetaDataException unjoinable(ValueMapping vm) {
      return new MetaDataException(_loc.get("cant-join", (Object)vm));
   }

   public static MetaDataException unloadable(ValueMapping vm) {
      return new MetaDataException(_loc.get("cant-load", (Object)vm));
   }

   public static MetaDataException uninversable(ValueMapping vm) {
      return new MetaDataException(_loc.get("cant-inverse", (Object)vm));
   }

   public static Object toDataStoreValue(ValueMapping vm, Object val, JDBCStore store) {
      ClassMapping rel;
      if (val == null) {
         ClassMapping[] clss = vm.getIndependentTypeMappings();
         rel = clss.length > 0 ? clss[0] : vm.getTypeMapping();
      } else if (val.getClass() == vm.getType()) {
         rel = vm.getTypeMapping();
      } else {
         rel = vm.getMappingRepository().getMapping(val.getClass(), store.getContext().getClassLoader(), true);
      }

      if (!rel.isMapped()) {
         throw new UserException(_loc.get("unmapped-datastore-value", (Object)rel.getDescribedType()));
      } else {
         Column[] cols;
         if (vm.getJoinDirection() == 1) {
            cols = rel.getPrimaryKeyColumns();
         } else {
            cols = vm.getForeignKey(rel).getPrimaryKeyColumns();
         }

         return rel.toDataStoreValue(val, cols, store);
      }
   }

   public static void mapRelationToUnmappedPC(ValueMapping vm, String name, boolean adapt) {
      if (vm.getTypeMapping().getIdentityType() == 0) {
         throw new MetaDataException(_loc.get("rel-to-unknownid", (Object)vm));
      } else {
         ValueMappingInfo vinfo = vm.getValueInfo();
         Column[] tmplates = newUnmappedPCTemplateColumns(vm, name);
         vm.setColumns(vinfo.getColumns(vm, name, tmplates, vm.getFieldMapping().getTable(), adapt));
         vm.setColumnIO(vinfo.getColumnIO());
      }
   }

   private static Column[] newUnmappedPCTemplateColumns(ValueMapping vm, String name) {
      ClassMapping rel = vm.getTypeMapping();
      if (rel.getIdentityType() == 1) {
         Column col = new Column();
         col.setName(name);
         col.setJavaType(6);
         col.setRelationId(true);
         return new Column[]{col};
      } else {
         FieldMapping[] pks = rel.getPrimaryKeyFieldMappings();
         Column[] cols = new Column[pks.length];

         for(int i = 0; i < pks.length; ++i) {
            cols[i] = mapPrimaryKey(vm, pks[i]);
            if (cols.length == 1) {
               cols[i].setName(name);
            } else if (cols[i].getName() == null) {
               cols[i].setName(name + "_" + pks[i].getName());
            } else {
               cols[i].setName(name + "_" + cols[i].getName());
            }

            cols[i].setTargetField(pks[i].getName());
            cols[i].setRelationId(true);
         }

         return cols;
      }
   }

   private static Column mapPrimaryKey(ValueMapping vm, FieldMapping pk) {
      List cols = pk.getValueInfo().getColumns();
      if (cols.size() > 1) {
         throw new MetaDataException(_loc.get("bad-unmapped-rel", vm, pk));
      } else {
         Column tmplate = null;
         if (cols.size() == 1) {
            tmplate = (Column)cols.get(0);
         }

         Column col = new Column();
         switch (pk.getTypeCode()) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
               col.setJavaType(pk.getTypeCode());
               break;
            case 8:
            case 11:
            case 12:
            case 13:
            case 15:
            default:
               throw new MetaDataException(_loc.get("bad-unmapped-rel", vm, pk));
            case 14:
               col.setJavaType(JavaSQLTypes.getDateTypeCode(pk.getType()));
         }

         if (tmplate != null) {
            col.setName(tmplate.getName());
            col.setType(tmplate.getType());
            col.setTypeName(tmplate.getTypeName());
            col.setSize(tmplate.getSize());
            col.setDecimalDigits(tmplate.getDecimalDigits());
         }

         return col;
      }
   }

   public static OpenJPAStateManager getStateManager(Object obj, StoreContext ctx) {
      if (obj == null) {
         return null;
      } else {
         OpenJPAStateManager sm = ctx.getStateManager(obj);
         return (OpenJPAStateManager)(sm == null ? new DetachedValueStateManager(obj, ctx) : sm);
      }
   }
}
