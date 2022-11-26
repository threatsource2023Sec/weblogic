package com.bea.core.repackaged.springframework.ui.context;

import com.bea.core.repackaged.springframework.lang.Nullable;

public interface HierarchicalThemeSource extends ThemeSource {
   void setParentThemeSource(@Nullable ThemeSource var1);

   @Nullable
   ThemeSource getParentThemeSource();
}
