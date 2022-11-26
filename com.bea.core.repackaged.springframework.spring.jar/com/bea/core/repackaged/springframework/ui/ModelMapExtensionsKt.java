package com.bea.core.repackaged.springframework.ui;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 11},
   bv = {1, 0, 2},
   k = 2,
   d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0000\n\u0000\u001a\u001d\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0086\u0002¨\u0006\u0007"},
   d2 = {"set", "", "Lorg/springframework/ui/ModelMap;", "attributeName", "", "attributeValue", "", "spring-context"}
)
public final class ModelMapExtensionsKt {
   public static final void set(@NotNull ModelMap $receiver, @NotNull String attributeName, @NotNull Object attributeValue) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Intrinsics.checkParameterIsNotNull(attributeName, "attributeName");
      Intrinsics.checkParameterIsNotNull(attributeValue, "attributeValue");
      $receiver.addAttribute(attributeName, attributeValue);
   }
}
