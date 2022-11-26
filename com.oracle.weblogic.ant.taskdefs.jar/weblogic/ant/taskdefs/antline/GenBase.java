package weblogic.ant.taskdefs.antline;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ImporterTopLevel;
import org.mozilla.javascript.JavaScriptException;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.tools.debugger.Main;

/** @deprecated */
@Deprecated
public class GenBase implements ResourceProvider {
   private HashMap vars = new HashMap();
   private String cgData;
   private String filename;
   private boolean debug = true;
   private static final boolean verbose = Boolean.getBoolean("weblogic.webservice.util.script.verbose");

   public GenBase(String filename, boolean debug) throws IOException, ScriptException {
      this.debug = debug;
      this.filename = filename;
      this.cgData = this.getResource(filename);
      this.setOutput(System.out);
   }

   public void setOutput(PrintStream stream) {
      this.setVar("out", new Output(stream));
   }

   public String getResource(String name) throws ScriptException {
      URL url = this.getClass().getResource(name);
      if (url == null) {
         throw new ScriptException("unable to find resource:" + name);
      } else {
         return Util.fileToString(url.toString());
      }
   }

   public void setVar(String name, Object value) {
      this.vars.put(name, value);
   }

   public Object getVar(String name) {
      return this.vars.get(name);
   }

   public void removeVar(String name) {
      this.vars.remove(name);
   }

   public void gen() throws ScriptException {
      try {
         this.generate();
      } catch (JavaScriptException var2) {
         this.printDebug(var2);
         throw new ScriptException("java script error:");
      } catch (RuntimeException var3) {
         this.printDebug(var3);
         throw var3;
      }
   }

   private void printDebug(Exception e) {
      if (verbose) {
         System.out.println("************ javascript ****************");
         this.printJavaScript(this.cgData);
         System.out.println("************* error ********************");
         e.printStackTrace();
         System.out.println("****************************************");
      } else {
         System.out.println("set system property 'weblogic.webservice.util.script.verbose=true' for more details");
      }

   }

   private void printJavaScript(String data) {
      StringTokenizer st = new StringTokenizer(data, "\n");

      for(int i = 0; st.hasMoreTokens(); ++i) {
         System.out.print(i + " :  ");
         System.out.println(st.nextToken());
      }

   }

   private void generate() throws JavaScriptException, ScriptException {
      this.cgData = (new LightJspParser(this.cgData, this)).parse();
      if (this.debug) {
         Util.stringToFile(this.filename + ".js", this.cgData);
         this.execDebug();
      } else {
         this.exec();
      }

   }

   public void scopeCreated(ScriptableObject scope) {
      Iterator it = this.vars.keySet().iterator();

      while(it.hasNext()) {
         String key = (String)it.next();
         Object value = this.vars.get(key);
         Scriptable jsArgs = Context.toObject(value, scope);
         ((ImporterTopLevel)scope).defineProperty(key, jsArgs, 2);
      }

   }

   private void execDebug() throws JavaScriptException, ScriptException {
      String[] args = new String[]{this.filename + ".js"};
      Main.main(args);
   }

   private void addGlobalVar(Scriptable scope) {
      Iterator it = this.vars.keySet().iterator();

      while(it.hasNext()) {
         String key = (String)it.next();
         Object value = this.vars.get(key);
         Scriptable jsArgs = Context.toObject(value, scope);
         scope.put(key, scope, jsArgs);
      }

   }

   private void exec() throws JavaScriptException, ScriptException {
      Context cx = Context.enter();
      Scriptable scope = cx.initStandardObjects((ScriptableObject)null);
      this.addGlobalVar(scope);
      cx.evaluateString(scope, this.cgData, "<cg>", 1, (Object)null);
      Context.exit();
   }

   public static void main(String[] args) throws Exception {
      if (args.length != 1) {
         System.out.println("usage: java GenBase <filename.cg>");
      } else {
         GenBase gen = new GenBase(args[0], true);
         gen.gen();
      }

   }
}
