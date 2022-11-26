package com.bea.core.repackaged.springframework.scripting;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Map;

public interface ScriptEvaluator {
   @Nullable
   Object evaluate(ScriptSource var1) throws ScriptCompilationException;

   @Nullable
   Object evaluate(ScriptSource var1, @Nullable Map var2) throws ScriptCompilationException;
}
