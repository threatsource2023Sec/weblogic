package com.bea.core.repackaged.springframework.scripting;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.io.IOException;

public interface ScriptSource {
   String getScriptAsString() throws IOException;

   boolean isModified();

   @Nullable
   String suggestedClassName();
}
