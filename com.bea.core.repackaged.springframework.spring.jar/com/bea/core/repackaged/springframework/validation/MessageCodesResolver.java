package com.bea.core.repackaged.springframework.validation;

import com.bea.core.repackaged.springframework.lang.Nullable;

public interface MessageCodesResolver {
   String[] resolveMessageCodes(String var1, String var2);

   String[] resolveMessageCodes(String var1, String var2, String var3, @Nullable Class var4);
}
