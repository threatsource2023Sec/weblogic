package com.oracle.pitchfork.interfaces;

public class SpringComponentFactory implements ComponentFactory {
   public Object newInstance(Class cls) {
      Object object = null;

      try {
         object = cls.newInstance();
      } catch (InstantiationException var4) {
         var4.printStackTrace();
      } catch (IllegalAccessException var5) {
         var5.printStackTrace();
      }

      return object;
   }
}
