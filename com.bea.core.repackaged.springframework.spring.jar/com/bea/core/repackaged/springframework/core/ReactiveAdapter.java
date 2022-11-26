package com.bea.core.repackaged.springframework.core;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.function.Function;
import org.reactivestreams.Publisher;

public class ReactiveAdapter {
   private final ReactiveTypeDescriptor descriptor;
   private final Function toPublisherFunction;
   private final Function fromPublisherFunction;

   public ReactiveAdapter(ReactiveTypeDescriptor descriptor, Function toPublisherFunction, Function fromPublisherFunction) {
      Assert.notNull(descriptor, (String)"'descriptor' is required");
      Assert.notNull(toPublisherFunction, (String)"'toPublisherFunction' is required");
      Assert.notNull(fromPublisherFunction, (String)"'fromPublisherFunction' is required");
      this.descriptor = descriptor;
      this.toPublisherFunction = toPublisherFunction;
      this.fromPublisherFunction = fromPublisherFunction;
   }

   public ReactiveTypeDescriptor getDescriptor() {
      return this.descriptor;
   }

   public Class getReactiveType() {
      return this.getDescriptor().getReactiveType();
   }

   public boolean isMultiValue() {
      return this.getDescriptor().isMultiValue();
   }

   public boolean isNoValue() {
      return this.getDescriptor().isNoValue();
   }

   public boolean supportsEmpty() {
      return this.getDescriptor().supportsEmpty();
   }

   public Publisher toPublisher(@Nullable Object source) {
      if (source == null) {
         source = this.getDescriptor().getEmptyValue();
      }

      return (Publisher)this.toPublisherFunction.apply(source);
   }

   public Object fromPublisher(Publisher publisher) {
      return this.fromPublisherFunction.apply(publisher);
   }
}
