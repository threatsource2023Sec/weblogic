package com.bea.core.repackaged.springframework.context;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Locale;

public interface MessageSource {
   @Nullable
   String getMessage(String var1, @Nullable Object[] var2, @Nullable String var3, Locale var4);

   String getMessage(String var1, @Nullable Object[] var2, Locale var3) throws NoSuchMessageException;

   String getMessage(MessageSourceResolvable var1, Locale var2) throws NoSuchMessageException;
}
