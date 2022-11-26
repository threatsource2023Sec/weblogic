package org.apache.openjpa.jdbc.meta.strats;

import java.sql.SQLException;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.RelationId;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.ImplHelper;
import org.apache.openjpa.util.StoreException;

public class UntypedPCValueHandler extends AbstractValueHandler implements RelationId {
   private static final Localizer _loc = Localizer.forPackage(UntypedPCValueHandler.class);
   private static final UntypedPCValueHandler _instance = new UntypedPCValueHandler();

   public static UntypedPCValueHandler getInstance() {
      return _instance;
   }

   public Column[] map(ValueMapping vm, String name, ColumnIO io, boolean adapt) {
      Column col = new Column();
      col.setName(name);
      col.setJavaType(9);
      col.setRelationId(true);
      return new Column[]{col};
   }

   public boolean isVersionable(ValueMapping vm) {
      return true;
   }

   public boolean objectValueRequiresLoad(ValueMapping vm) {
      return true;
   }

   public Object toDataStoreValue(ValueMapping vm, Object val, JDBCStore store) {
      if (ImplHelper.isManageable(val)) {
         PersistenceCapable pc = ImplHelper.toPersistenceCapable(val, store.getConfiguration());
         if (pc.pcGetStateManager() != null) {
            return pc.pcGetStateManager();
         }
      }

      return RelationStrategies.getStateManager(val, store.getContext());
   }

   public Object toObjectValue(ValueMapping vm, Object val, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch) throws SQLException {
      if (val == null) {
         return null;
      } else {
         String str = (String)val;
         int idx = str.indexOf(58);
         if (idx == -1) {
            throw new StoreException(_loc.get("oid-invalid", str, vm));
         } else {
            String clsName = str.substring(0, idx);
            String oidStr = str.substring(idx + 1);
            StoreContext ctx = store.getContext();
            ClassLoader loader = store.getConfiguration().getClassResolverInstance().getClassLoader(vm.getType(), ctx.getClassLoader());
            Class cls = null;

            try {
               cls = Class.forName(clsName, true, loader);
            } catch (ClassNotFoundException var14) {
               throw new StoreException(var14);
            }

            Object oid = ctx.newObjectId(cls, oidStr);
            return store.find(oid, vm, fetch);
         }
      }
   }

   public Object toRelationDataStoreValue(OpenJPAStateManager sm, Column col) {
      return sm != null && sm.getObjectId() != null ? sm.getMetaData().getDescribedType().getName() + ":" + sm.getObjectId().toString() : null;
   }
}
