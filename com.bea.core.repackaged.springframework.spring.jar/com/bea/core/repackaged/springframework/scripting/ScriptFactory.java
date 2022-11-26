package com.bea.core.repackaged.springframework.scripting;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.io.IOException;

public interface ScriptFactory {
   String getScriptSourceLocator();

   @Nullable
   Class[] getScriptInterfaces();

   boolean requiresConfigInterface();

   @Nullable
   Object getScriptedObject(ScriptSource var1, @Nullable Class... var2) throws IOException, ScriptCompilationException;

   @Nullable
   Class getScriptedObjectType(ScriptSource var1) throws IOException, ScriptCompilationException;

   boolean requiresScriptedObjectRefresh(ScriptSource var1);
}
