package com.bea.core.repackaged.springframework.beans.factory.support;

class ImplicitlyAppearedSingletonException extends IllegalStateException {
   public ImplicitlyAppearedSingletonException() {
      super("About-to-be-created singleton instance implicitly appeared through the creation of the factory bean that its bean definition points to");
   }
}
