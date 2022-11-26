package org.apache.openjpa.lib.rop;

import java.util.Map;
import org.apache.commons.collections.ReferenceMap;

public class SoftRandomAccessResultList extends RandomAccessResultList {
   public SoftRandomAccessResultList(ResultObjectProvider rop) {
      super(rop);
   }

   protected Map newRowMap() {
      return new ReferenceMap();
   }
}
