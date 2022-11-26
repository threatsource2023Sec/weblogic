package org.mozilla.javascript.regexp;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.JavaScriptException;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

class MatchData extends GlobData {
   Scriptable arrayobj;

   void doGlobal(Context var1, Scriptable var2, int var3, RegExpImpl var4) throws JavaScriptException {
      if (this.arrayobj == null) {
         Scriptable var7 = ScriptableObject.getTopLevelScope(var2);
         this.arrayobj = ScriptRuntime.newObject(var1, (Scriptable)var7, (String)"Array", (Object[])null);
      }

      SubString var9 = var4.lastMatch;
      String var8 = var9.toString();
      this.arrayobj.put(var3, this.arrayobj, var8);
   }
}
