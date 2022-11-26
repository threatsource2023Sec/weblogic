package kodo.profile;

import org.apache.openjpa.kernel.OpenJPAStateManager;

public class StateManagerFormatter {
   public Object getClassName(Object input) {
      return input == null ? "null" : ((OpenJPAStateManager)input).getMetaData().getDescribedType().getName();
   }
}
