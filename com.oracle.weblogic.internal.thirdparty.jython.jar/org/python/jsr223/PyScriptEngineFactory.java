package org.python.jsr223;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import org.python.Version;
import org.python.core.Py;

public class PyScriptEngineFactory implements ScriptEngineFactory {
   public String getEngineName() {
      return "jython";
   }

   public String getEngineVersion() {
      return String.format("%s.%s.%s", Version.PY_MAJOR_VERSION, Version.PY_MINOR_VERSION, Version.PY_MICRO_VERSION);
   }

   public List getExtensions() {
      return Collections.unmodifiableList(Arrays.asList("py"));
   }

   public String getLanguageName() {
      return "python";
   }

   public String getLanguageVersion() {
      return String.format("%s.%s", Version.PY_MAJOR_VERSION, Version.PY_MINOR_VERSION);
   }

   public Object getParameter(String key) {
      if (key.equals("javax.script.engine")) {
         return this.getEngineName();
      } else if (key.equals("javax.script.engine_version")) {
         return this.getEngineVersion();
      } else if (key.equals("javax.script.name")) {
         return this.getEngineName();
      } else if (key.equals("javax.script.language")) {
         return this.getLanguageName();
      } else if (key.equals("javax.script.language_version")) {
         return this.getLanguageVersion();
      } else {
         return key.equals("THREADING") ? "MULTITHREADED" : null;
      }
   }

   public String getMethodCallSyntax(String obj, String m, String... args) {
      StringBuilder buffer = new StringBuilder();
      buffer.append(String.format("%s.%s(", obj, m));
      int i = args.length;
      String[] var6 = args;
      int var7 = args.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         String arg = var6[var8];
         buffer.append(arg);
         if (i-- > 0) {
            buffer.append(", ");
         }
      }

      buffer.append(")");
      return buffer.toString();
   }

   public String getOutputStatement(String toDisplay) {
      StringBuilder buffer = new StringBuilder(toDisplay.length() + 8);
      buffer.append("print ");
      buffer.append(Py.newUnicode(toDisplay).__repr__());
      return buffer.toString();
   }

   public String getProgram(String... statements) {
      StringBuilder buffer = new StringBuilder();
      String[] var3 = statements;
      int var4 = statements.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String statement = var3[var5];
         buffer.append(statement);
         buffer.append("\n");
      }

      return buffer.toString();
   }

   public ScriptEngine getScriptEngine() {
      return new PyScriptEngine(this);
   }

   public List getMimeTypes() {
      return Collections.unmodifiableList(Arrays.asList("text/python", "application/python", "text/x-python", "application/x-python"));
   }

   public List getNames() {
      return Collections.unmodifiableList(Arrays.asList("python", "jython"));
   }
}
