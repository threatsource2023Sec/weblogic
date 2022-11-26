package com.bea.core.repackaged.springframework.core.type;

import com.bea.core.repackaged.springframework.lang.Nullable;

public interface ClassMetadata {
   String getClassName();

   boolean isInterface();

   boolean isAnnotation();

   boolean isAbstract();

   boolean isConcrete();

   boolean isFinal();

   boolean isIndependent();

   boolean hasEnclosingClass();

   @Nullable
   String getEnclosingClassName();

   boolean hasSuperClass();

   @Nullable
   String getSuperClassName();

   String[] getInterfaceNames();

   String[] getMemberClassNames();
}
