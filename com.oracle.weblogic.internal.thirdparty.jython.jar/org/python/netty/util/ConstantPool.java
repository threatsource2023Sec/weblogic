package org.python.netty.util;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.python.netty.util.internal.ObjectUtil;
import org.python.netty.util.internal.PlatformDependent;

public abstract class ConstantPool {
   private final ConcurrentMap constants = PlatformDependent.newConcurrentHashMap();
   private final AtomicInteger nextId = new AtomicInteger(1);

   public Constant valueOf(Class firstNameComponent, String secondNameComponent) {
      if (firstNameComponent == null) {
         throw new NullPointerException("firstNameComponent");
      } else if (secondNameComponent == null) {
         throw new NullPointerException("secondNameComponent");
      } else {
         return this.valueOf(firstNameComponent.getName() + '#' + secondNameComponent);
      }
   }

   public Constant valueOf(String name) {
      checkNotNullAndNotEmpty(name);
      return this.getOrCreate(name);
   }

   private Constant getOrCreate(String name) {
      Constant constant = (Constant)this.constants.get(name);
      if (constant == null) {
         Constant tempConstant = this.newConstant(this.nextId(), name);
         constant = (Constant)this.constants.putIfAbsent(name, tempConstant);
         if (constant == null) {
            return tempConstant;
         }
      }

      return constant;
   }

   public boolean exists(String name) {
      checkNotNullAndNotEmpty(name);
      return this.constants.containsKey(name);
   }

   public Constant newInstance(String name) {
      checkNotNullAndNotEmpty(name);
      return this.createOrThrow(name);
   }

   private Constant createOrThrow(String name) {
      Constant constant = (Constant)this.constants.get(name);
      if (constant == null) {
         Constant tempConstant = this.newConstant(this.nextId(), name);
         constant = (Constant)this.constants.putIfAbsent(name, tempConstant);
         if (constant == null) {
            return tempConstant;
         }
      }

      throw new IllegalArgumentException(String.format("'%s' is already in use", name));
   }

   private static String checkNotNullAndNotEmpty(String name) {
      ObjectUtil.checkNotNull(name, "name");
      if (name.isEmpty()) {
         throw new IllegalArgumentException("empty name");
      } else {
         return name;
      }
   }

   protected abstract Constant newConstant(int var1, String var2);

   /** @deprecated */
   @Deprecated
   public final int nextId() {
      return this.nextId.getAndIncrement();
   }
}
