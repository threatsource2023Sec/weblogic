package com.bea.core.repackaged.springframework.scripting.support;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.scripting.ScriptSource;
import com.bea.core.repackaged.springframework.util.Assert;

public class StaticScriptSource implements ScriptSource {
   private String script = "";
   private boolean modified;
   @Nullable
   private String className;

   public StaticScriptSource(String script) {
      this.setScript(script);
   }

   public StaticScriptSource(String script, @Nullable String className) {
      this.setScript(script);
      this.className = className;
   }

   public synchronized void setScript(String script) {
      Assert.hasText(script, "Script must not be empty");
      this.modified = !script.equals(this.script);
      this.script = script;
   }

   public synchronized String getScriptAsString() {
      this.modified = false;
      return this.script;
   }

   public synchronized boolean isModified() {
      return this.modified;
   }

   @Nullable
   public String suggestedClassName() {
      return this.className;
   }

   public String toString() {
      return "static script" + (this.className != null ? " [" + this.className + "]" : "");
   }
}
