package com.solarmetric.profile;

import java.util.Stack;

public class ProfilingStack extends Stack {
   public ProfilingStackItem pushItem(ProfilingStackItem item) {
      return (ProfilingStackItem)this.push(item);
   }

   public ProfilingStackItem popItem() {
      return (ProfilingStackItem)this.pop();
   }

   public ProfilingStackItem peekItem() {
      return (ProfilingStackItem)this.peek();
   }

   public ProfilingStackItem firstElementItem() {
      return (ProfilingStackItem)this.firstElement();
   }
}
