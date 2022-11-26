package com.solarmetric.profile;

public class RootInfoImpl extends MethodInfoImpl implements RootInfo {
   private static final long serialVersionUID = 1L;

   public RootInfoImpl(String name, String description) {
      super(name, description);
   }

   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else {
         return !(o instanceof RootInfoImpl) ? false : super.equals(o);
      }
   }
}
