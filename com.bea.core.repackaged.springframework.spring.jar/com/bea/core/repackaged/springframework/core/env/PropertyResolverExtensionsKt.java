package com.bea.core.repackaged.springframework.core.env;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 11},
   bv = {1, 0, 2},
   k = 2,
   d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\u001a\u0017\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0001H\u0086\u0002\u001a$\u0010\u0004\u001a\u0004\u0018\u0001H\u0005\"\u0006\b\u0000\u0010\u0005\u0018\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0001H\u0086\b¢\u0006\u0002\u0010\u0006\u001a&\u0010\u0007\u001a\u0002H\u0005\"\n\b\u0000\u0010\u0005\u0018\u0001*\u00020\b*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0001H\u0086\b¢\u0006\u0002\u0010\u0006¨\u0006\t"},
   d2 = {"get", "", "Lorg/springframework/core/env/PropertyResolver;", "key", "getProperty", "T", "(Lorg/springframework/core/env/PropertyResolver;Ljava/lang/String;)Ljava/lang/Object;", "getRequiredProperty", "", "spring-core"}
)
public final class PropertyResolverExtensionsKt {
   @Nullable
   public static final String get(@NotNull PropertyResolver $receiver, @NotNull String key) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Intrinsics.checkParameterIsNotNull(key, "key");
      return $receiver.getProperty(key);
   }

   private static final Object getProperty(@NotNull PropertyResolver $receiver, String key) {
      Intrinsics.reifiedOperationMarker(4, "T");
      return $receiver.getProperty(key, Object.class);
   }

   private static final Object getRequiredProperty(@NotNull PropertyResolver $receiver, String key) {
      Intrinsics.reifiedOperationMarker(4, "T");
      Object var10000 = $receiver.getRequiredProperty(key, Object.class);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "getRequiredProperty(key, T::class.java)");
      return var10000;
   }
}
