package org.python.netty.util;

public final class AttributeKey extends AbstractConstant {
   private static final ConstantPool pool = new ConstantPool() {
      protected AttributeKey newConstant(int id, String name) {
         return new AttributeKey(id, name);
      }
   };

   public static AttributeKey valueOf(String name) {
      return (AttributeKey)pool.valueOf(name);
   }

   public static boolean exists(String name) {
      return pool.exists(name);
   }

   public static AttributeKey newInstance(String name) {
      return (AttributeKey)pool.newInstance(name);
   }

   public static AttributeKey valueOf(Class firstNameComponent, String secondNameComponent) {
      return (AttributeKey)pool.valueOf(firstNameComponent, secondNameComponent);
   }

   private AttributeKey(int id, String name) {
      super(id, name);
   }

   // $FF: synthetic method
   AttributeKey(int x0, String x1, Object x2) {
      this(x0, x1);
   }
}
