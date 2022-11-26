package org.apache.taglibs.standard.lang.jstl.test.beans;

public class Factory {
   public static PublicBean1 createBean1() {
      return new PublicBean1();
   }

   public static PublicBean1 createBean2() {
      return new PrivateBean1a();
   }

   public static PublicBean1 createBean3() {
      return new PublicBean1b();
   }

   public static PublicInterface2 createBean4() {
      return new PublicBean2a();
   }

   public static PublicInterface2 createBean5() {
      return new PrivateBean2b();
   }

   public static PublicInterface2 createBean6() {
      return new PrivateBean2c();
   }

   public static PublicInterface2 createBean7() {
      return new PrivateBean2d();
   }
}
