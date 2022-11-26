package com.bea.core.repackaged.springframework.context.support;

import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinitionCustomizer;
import java.util.Arrays;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 11},
   bv = {1, 0, 2},
   k = 2,
   d1 = {"\u0000:\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\u001a\u001f\u0010\u0000\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\b\u0005\u001a2\u0010\u0006\u001a\u00020\u0004\"\n\b\u0000\u0010\u0007\u0018\u0001*\u00020\b*\u00020\u00012\u0012\u0010\t\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000b0\n\"\u00020\u000bH\u0086\b¢\u0006\u0002\u0010\f\u001aH\u0010\u0006\u001a\u00020\u0004\"\n\b\u0000\u0010\u0007\u0018\u0001*\u00020\b*\u00020\u00012\u0012\u0010\t\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000b0\n\"\u00020\u000b2\u0014\b\u0004\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u0002H\u00070\u0003H\u0086\b¢\u0006\u0002\u0010\u000f\u001a:\u0010\u0006\u001a\u00020\u0004\"\n\b\u0000\u0010\u0007\u0018\u0001*\u00020\b*\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u00112\u0012\u0010\t\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000b0\n\"\u00020\u000bH\u0086\b¢\u0006\u0002\u0010\u0012\u001aP\u0010\u0006\u001a\u00020\u0004\"\n\b\u0000\u0010\u0007\u0018\u0001*\u00020\b*\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00112\u0012\u0010\t\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000b0\n\"\u00020\u000b2\u0014\b\u0004\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u0002H\u00070\u0003H\u0086\b¢\u0006\u0002\u0010\u0014¨\u0006\u0015"},
   d2 = {"GenericApplicationContext", "Lorg/springframework/context/support/GenericApplicationContext;", "configure", "Lkotlin/Function1;", "", "Lkotlin/ExtensionFunctionType;", "registerBean", "T", "", "customizers", "", "Lorg/springframework/beans/factory/config/BeanDefinitionCustomizer;", "(Lorg/springframework/context/support/GenericApplicationContext;[Lorg/springframework/beans/factory/config/BeanDefinitionCustomizer;)V", "function", "Lorg/springframework/context/ApplicationContext;", "(Lorg/springframework/context/support/GenericApplicationContext;[Lorg/springframework/beans/factory/config/BeanDefinitionCustomizer;Lkotlin/jvm/functions/Function1;)V", "beanName", "", "(Lorg/springframework/context/support/GenericApplicationContext;Ljava/lang/String;[Lorg/springframework/beans/factory/config/BeanDefinitionCustomizer;)V", "name", "(Lorg/springframework/context/support/GenericApplicationContext;Ljava/lang/String;[Lorg/springframework/beans/factory/config/BeanDefinitionCustomizer;Lkotlin/jvm/functions/Function1;)V", "spring-context"}
)
public final class GenericApplicationContextExtensionsKt {
   private static final void registerBean(@NotNull GenericApplicationContext $receiver, BeanDefinitionCustomizer... customizers) {
      Intrinsics.reifiedOperationMarker(4, "T");
      $receiver.registerBean(Object.class, (BeanDefinitionCustomizer[])Arrays.copyOf(customizers, customizers.length));
   }

   private static final void registerBean(@NotNull GenericApplicationContext $receiver, String beanName, BeanDefinitionCustomizer... customizers) {
      Intrinsics.reifiedOperationMarker(4, "T");
      $receiver.registerBean(beanName, Object.class, (BeanDefinitionCustomizer[])Arrays.copyOf(customizers, customizers.length));
   }

   private static final void registerBean(@NotNull final GenericApplicationContext $receiver, BeanDefinitionCustomizer[] customizers, final Function1 function) {
      Intrinsics.reifiedOperationMarker(4, "T");
      $receiver.registerBean(Object.class, (Supplier)(new Supplier() {
         @NotNull
         public final Object get() {
            return function.invoke($receiver);
         }
      }), (BeanDefinitionCustomizer[])Arrays.copyOf(customizers, customizers.length));
   }

   private static final void registerBean(@NotNull final GenericApplicationContext $receiver, String name, BeanDefinitionCustomizer[] customizers, final Function1 function) {
      Intrinsics.reifiedOperationMarker(4, "T");
      $receiver.registerBean(name, Object.class, (Supplier)(new Supplier() {
         @NotNull
         public final Object get() {
            return function.invoke($receiver);
         }
      }), (BeanDefinitionCustomizer[])Arrays.copyOf(customizers, customizers.length));
   }

   @NotNull
   public static final GenericApplicationContext GenericApplicationContext(@NotNull Function1 configure) {
      Intrinsics.checkParameterIsNotNull(configure, "configure");
      GenericApplicationContext var1 = new GenericApplicationContext();
      configure.invoke(var1);
      return var1;
   }
}
