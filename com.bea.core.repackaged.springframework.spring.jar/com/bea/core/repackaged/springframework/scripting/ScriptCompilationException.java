package com.bea.core.repackaged.springframework.scripting;

import com.bea.core.repackaged.springframework.core.NestedRuntimeException;
import com.bea.core.repackaged.springframework.lang.Nullable;

public class ScriptCompilationException extends NestedRuntimeException {
   @Nullable
   private final ScriptSource scriptSource;

   public ScriptCompilationException(String msg) {
      super(msg);
      this.scriptSource = null;
   }

   public ScriptCompilationException(String msg, Throwable cause) {
      super(msg, cause);
      this.scriptSource = null;
   }

   public ScriptCompilationException(ScriptSource scriptSource, String msg) {
      super("Could not compile " + scriptSource + ": " + msg);
      this.scriptSource = scriptSource;
   }

   public ScriptCompilationException(ScriptSource scriptSource, Throwable cause) {
      super("Could not compile " + scriptSource, cause);
      this.scriptSource = scriptSource;
   }

   public ScriptCompilationException(ScriptSource scriptSource, String msg, Throwable cause) {
      super("Could not compile " + scriptSource + ": " + msg, cause);
      this.scriptSource = scriptSource;
   }

   @Nullable
   public ScriptSource getScriptSource() {
      return this.scriptSource;
   }
}
