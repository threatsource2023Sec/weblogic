package com.bea.core.repackaged.springframework.beans.factory;

import java.lang.annotation.Annotation;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 11},
   bv = {1, 0, 2},
   k = 2,
   d1 = {"\u00002\n\u0000\n\u0002\u0010\u001b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u0002\u001a#\u0010\u0000\u001a\u0004\u0018\u00010\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0001*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0086\b\u001a&\u0010\u0006\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u0007\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0001*\u00020\u0003H\u0086\b¢\u0006\u0002\u0010\b\u001a:\u0010\t\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u0007\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\n*\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\fH\u0086\b¢\u0006\u0002\u0010\u000e\u001a9\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u00020\u0010\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\n*\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\fH\u0086\b\u001a%\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\n0\u0010\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0001*\u00020\u0003H\u0086\b¨\u0006\u0012"},
   d2 = {"findAnnotationOnBean", "", "T", "Lorg/springframework/beans/factory/ListableBeanFactory;", "beanName", "", "getBeanNamesForAnnotation", "", "(Lorg/springframework/beans/factory/ListableBeanFactory;)[Ljava/lang/String;", "getBeanNamesForType", "", "includeNonSingletons", "", "allowEagerInit", "(Lorg/springframework/beans/factory/ListableBeanFactory;ZZ)[Ljava/lang/String;", "getBeansOfType", "", "getBeansWithAnnotation", "spring-beans"}
)
public final class ListableBeanFactoryExtensionsKt {
   private static final String[] getBeanNamesForType(@NotNull ListableBeanFactory $receiver, boolean includeNonSingletons, boolean allowEagerInit) {
      Intrinsics.reifiedOperationMarker(4, "T");
      String[] var10000 = $receiver.getBeanNamesForType(Object.class, includeNonSingletons, allowEagerInit);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "getBeanNamesForType(T::c…ngletons, allowEagerInit)");
      return var10000;
   }

   private static final Map getBeansOfType(@NotNull ListableBeanFactory $receiver, boolean includeNonSingletons, boolean allowEagerInit) {
      Intrinsics.reifiedOperationMarker(4, "T");
      Map var10000 = $receiver.getBeansOfType(Object.class, includeNonSingletons, allowEagerInit);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "getBeansOfType(T::class.…ngletons, allowEagerInit)");
      return var10000;
   }

   private static final String[] getBeanNamesForAnnotation(@NotNull ListableBeanFactory $receiver) {
      Intrinsics.reifiedOperationMarker(4, "T");
      String[] var10000 = $receiver.getBeanNamesForAnnotation(Annotation.class);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "getBeanNamesForAnnotation(T::class.java)");
      return var10000;
   }

   private static final Map getBeansWithAnnotation(@NotNull ListableBeanFactory $receiver) {
      Intrinsics.reifiedOperationMarker(4, "T");
      Map var10000 = $receiver.getBeansWithAnnotation(Annotation.class);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "getBeansWithAnnotation(T::class.java)");
      return var10000;
   }

   private static final Annotation findAnnotationOnBean(@NotNull ListableBeanFactory $receiver, String beanName) {
      Intrinsics.reifiedOperationMarker(4, "T");
      return $receiver.findAnnotationOnBean(beanName, Annotation.class);
   }
}
