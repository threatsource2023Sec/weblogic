package com.bea.core.repackaged.springframework.context;

import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.core.ResolvableTypeProvider;
import com.bea.core.repackaged.springframework.util.Assert;

public class PayloadApplicationEvent extends ApplicationEvent implements ResolvableTypeProvider {
   private final Object payload;

   public PayloadApplicationEvent(Object source, Object payload) {
      super(source);
      Assert.notNull(payload, "Payload must not be null");
      this.payload = payload;
   }

   public ResolvableType getResolvableType() {
      return ResolvableType.forClassWithGenerics(this.getClass(), ResolvableType.forInstance(this.getPayload()));
   }

   public Object getPayload() {
      return this.payload;
   }
}
