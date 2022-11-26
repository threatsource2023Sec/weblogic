package com.bea.core.repackaged.springframework.beans.factory;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface ObjectProvider extends ObjectFactory, Iterable {
   Object getObject(Object... var1) throws BeansException;

   @Nullable
   Object getIfAvailable() throws BeansException;

   default Object getIfAvailable(Supplier defaultSupplier) throws BeansException {
      Object dependency = this.getIfAvailable();
      return dependency != null ? dependency : defaultSupplier.get();
   }

   default void ifAvailable(Consumer dependencyConsumer) throws BeansException {
      Object dependency = this.getIfAvailable();
      if (dependency != null) {
         dependencyConsumer.accept(dependency);
      }

   }

   @Nullable
   Object getIfUnique() throws BeansException;

   default Object getIfUnique(Supplier defaultSupplier) throws BeansException {
      Object dependency = this.getIfUnique();
      return dependency != null ? dependency : defaultSupplier.get();
   }

   default void ifUnique(Consumer dependencyConsumer) throws BeansException {
      Object dependency = this.getIfUnique();
      if (dependency != null) {
         dependencyConsumer.accept(dependency);
      }

   }

   default Iterator iterator() {
      return this.stream().iterator();
   }

   default Stream stream() {
      throw new UnsupportedOperationException("Multi element access not supported");
   }

   default Stream orderedStream() {
      throw new UnsupportedOperationException("Ordered element access not supported");
   }
}
