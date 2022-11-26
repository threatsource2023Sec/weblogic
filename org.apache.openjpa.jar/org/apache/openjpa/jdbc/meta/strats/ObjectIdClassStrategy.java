package org.apache.openjpa.jdbc.meta.strats;

import org.apache.openjpa.jdbc.meta.ClassMappingInfo;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.MetaDataException;

public class ObjectIdClassStrategy extends AbstractClassStrategy {
   private static final Localizer _loc = Localizer.forPackage(ObjectIdClassStrategy.class);

   public void map(boolean adapt) {
      ValueMapping vm = this.cls.getEmbeddingMapping();
      if (vm != null && vm.getType() == this.cls.getDescribedType() && vm.getTypeCode() == 29) {
         ClassMappingInfo info = this.cls.getMappingInfo();
         info.assertNoSchemaComponents(this.cls, true);
         this.cls.setTable(vm.getFieldMapping().getTable());
      } else {
         throw new MetaDataException(_loc.get("not-oid", (Object)this.cls));
      }
   }
}
