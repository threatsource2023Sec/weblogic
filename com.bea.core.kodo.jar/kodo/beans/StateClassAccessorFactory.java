package kodo.beans;

import org.apache.openjpa.kernel.StoreContext;

public class StateClassAccessorFactory implements ClassAccessorFactory {
   public ClassAccessor getClassAccessor(Class forClass, Object context) {
      return new StateClassAccessor(forClass, (StoreContext)context);
   }
}
