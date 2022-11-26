package com.bea.core.repackaged.springframework.util.function;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.function.Supplier;

public class SingletonSupplier implements Supplier {
   @Nullable
   private final Supplier instanceSupplier;
   @Nullable
   private final Supplier defaultSupplier;
   @Nullable
   private volatile Object singletonInstance;

   public SingletonSupplier(@Nullable Object instance, Supplier defaultSupplier) {
      this.instanceSupplier = null;
      this.defaultSupplier = defaultSupplier;
      this.singletonInstance = instance;
   }

   public SingletonSupplier(@Nullable Supplier instanceSupplier, Supplier defaultSupplier) {
      this.instanceSupplier = instanceSupplier;
      this.defaultSupplier = defaultSupplier;
   }

   private SingletonSupplier(Supplier supplier) {
      this.instanceSupplier = supplier;
      this.defaultSupplier = null;
   }

   private SingletonSupplier(Object singletonInstance) {
      this.instanceSupplier = null;
      this.defaultSupplier = null;
      this.singletonInstance = singletonInstance;
   }

   @Nullable
   public Object get() {
      Object instance = this.singletonInstance;
      if (instance == null) {
         synchronized(this) {
            instance = this.singletonInstance;
            if (instance == null) {
               if (this.instanceSupplier != null) {
                  instance = this.instanceSupplier.get();
               }

               if (instance == null && this.defaultSupplier != null) {
                  instance = this.defaultSupplier.get();
               }

               this.singletonInstance = instance;
            }
         }
      }

      return instance;
   }

   public Object obtain() {
      Object instance = this.get();
      Assert.state(instance != null, "No instance from Supplier");
      return instance;
   }

   public static SingletonSupplier of(Object instance) {
      return new SingletonSupplier(instance);
   }

   @Nullable
   public static SingletonSupplier ofNullable(@Nullable Object instance) {
      return instance != null ? new SingletonSupplier(instance) : null;
   }

   public static SingletonSupplier of(Supplier supplier) {
      return new SingletonSupplier(supplier);
   }

   @Nullable
   public static SingletonSupplier ofNullable(@Nullable Supplier supplier) {
      return supplier != null ? new SingletonSupplier(supplier) : null;
   }
}
