package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.springframework.core.GenericTypeResolver;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationAttributes;
import com.bea.core.repackaged.springframework.core.type.AnnotationMetadata;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;

public abstract class AdviceModeImportSelector implements ImportSelector {
   public static final String DEFAULT_ADVICE_MODE_ATTRIBUTE_NAME = "mode";

   protected String getAdviceModeAttributeName() {
      return "mode";
   }

   public final String[] selectImports(AnnotationMetadata importingClassMetadata) {
      Class annType = GenericTypeResolver.resolveTypeArgument(this.getClass(), AdviceModeImportSelector.class);
      Assert.state(annType != null, "Unresolvable type argument for AdviceModeImportSelector");
      AnnotationAttributes attributes = AnnotationConfigUtils.attributesFor(importingClassMetadata, (Class)annType);
      if (attributes == null) {
         throw new IllegalArgumentException(String.format("@%s is not present on importing class '%s' as expected", annType.getSimpleName(), importingClassMetadata.getClassName()));
      } else {
         AdviceMode adviceMode = (AdviceMode)attributes.getEnum(this.getAdviceModeAttributeName());
         String[] imports = this.selectImports(adviceMode);
         if (imports == null) {
            throw new IllegalArgumentException("Unknown AdviceMode: " + adviceMode);
         } else {
            return imports;
         }
      }
   }

   @Nullable
   protected abstract String[] selectImports(AdviceMode var1);
}
