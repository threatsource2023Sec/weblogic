package org.apache.openjpa.jdbc.meta.strats;

import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.ClassMappingInfo;
import org.apache.openjpa.jdbc.meta.JavaSQLTypes;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.MetaDataException;

public class EmbeddedClassStrategy extends AbstractClassStrategy {
   private static final Localizer _loc = Localizer.forPackage(EmbeddedClassStrategy.class);

   public void map(boolean adapt) {
      ValueMapping vm = this.cls.getEmbeddingMapping();
      if (vm != null && vm.getType() == this.cls.getDescribedType()) {
         ClassMappingInfo info = this.cls.getMappingInfo();
         info.assertNoSchemaComponents(this.cls, true);
         ClassMapping owner = vm.getFieldMapping().getDefiningMapping();
         this.cls.setIdentityType(owner.getIdentityType());
         this.cls.setObjectIdType(owner.getObjectIdType(), owner.isObjectIdTypeShared());
         this.cls.setTable(vm.getFieldMapping().getTable());
         this.cls.setPrimaryKeyColumns(owner.getPrimaryKeyColumns());
         this.cls.setColumnIO(owner.getColumnIO());
      } else {
         throw new MetaDataException(_loc.get("not-embed", (Object)this.cls));
      }
   }

   public Object getNullIndicatorValue(OpenJPAStateManager sm) {
      Column[] cols = this.cls.getEmbeddingMapping().getColumns();
      if (cols.length != 1) {
         return null;
      } else if (sm == null && !cols[0].isNotNull()) {
         return null;
      } else {
         return sm == null ? JavaSQLTypes.getEmptyValue(cols[0].getJavaType()) : JavaSQLTypes.getNonEmptyValue(cols[0].getJavaType());
      }
   }

   public boolean indicatesNull(Object val) {
      Column[] cols = this.cls.getEmbeddingMapping().getColumns();
      if (cols.length != 1) {
         return false;
      } else if (val == null) {
         return true;
      } else if (cols[0].isNotNull() && val.equals(JavaSQLTypes.getEmptyValue(cols[0].getJavaType()))) {
         return true;
      } else {
         return cols[0].getDefaultString() != null && val.toString().equals(cols[0].getDefaultString());
      }
   }

   public boolean isPrimaryKeyObjectId(boolean hasAll) {
      return this.cls.getEmbeddingMapping().getFieldMapping().getDefiningMapping().isPrimaryKeyObjectId(hasAll);
   }
}
