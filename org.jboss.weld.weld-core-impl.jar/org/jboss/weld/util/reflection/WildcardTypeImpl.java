package org.jboss.weld.util.reflection;

import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;

public class WildcardTypeImpl implements WildcardType {
   private static final Type[] DEFAULT_UPPER_BOUND = new Type[]{Object.class};
   private static final Type[] DEFAULT_LOWER_BOUND = new Type[0];
   private static final WildcardType DEFAULT_INSTANCE;
   private final Type[] upperBound;
   private final Type[] lowerBound;

   public static WildcardType defaultInstance() {
      return DEFAULT_INSTANCE;
   }

   public static WildcardType withUpperBound(Type type) {
      return new WildcardTypeImpl(new Type[]{type}, DEFAULT_LOWER_BOUND);
   }

   public static WildcardType withLowerBound(Type type) {
      return new WildcardTypeImpl(DEFAULT_UPPER_BOUND, new Type[]{type});
   }

   private WildcardTypeImpl(Type[] upperBound, Type[] lowerBound) {
      this.upperBound = upperBound;
      this.lowerBound = lowerBound;
   }

   public Type[] getUpperBounds() {
      return this.upperBound;
   }

   public Type[] getLowerBounds() {
      return this.lowerBound;
   }

   static {
      DEFAULT_INSTANCE = new WildcardTypeImpl(DEFAULT_UPPER_BOUND, DEFAULT_LOWER_BOUND);
   }
}
