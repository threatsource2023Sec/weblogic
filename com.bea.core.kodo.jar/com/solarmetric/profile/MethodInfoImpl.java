package com.solarmetric.profile;

public class MethodInfoImpl extends EventInfoImpl implements MethodInfo {
   private static final long serialVersionUID = 1L;

   public MethodInfoImpl(String name) {
      super(name, "");
   }

   public MethodInfoImpl(String name, String description) {
      super(name, description);
   }

   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else {
         return !(o instanceof MethodInfoImpl) ? false : super.equals(o);
      }
   }
}
