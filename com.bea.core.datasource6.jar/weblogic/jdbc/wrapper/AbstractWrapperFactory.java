package weblogic.jdbc.wrapper;

import weblogic.utils.wrapper.WrapperFactory;

public class AbstractWrapperFactory {
   private static WrapperFactory instance = new WrapperFactory();

   public static WrapperFactory getInstance() {
      return instance;
   }
}
