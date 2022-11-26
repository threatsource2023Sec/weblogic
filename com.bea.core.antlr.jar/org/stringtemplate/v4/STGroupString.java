package org.stringtemplate.v4;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.stringtemplate.v4.compiler.CompiledST;
import org.stringtemplate.v4.compiler.GroupLexer;
import org.stringtemplate.v4.compiler.GroupParser;
import org.stringtemplate.v4.misc.ErrorType;

public class STGroupString extends STGroup {
   public String sourceName;
   public String text;
   protected boolean alreadyLoaded;

   public STGroupString(String text) {
      this("<string>", text, '<', '>');
   }

   public STGroupString(String sourceName, String text) {
      this(sourceName, text, '<', '>');
   }

   public STGroupString(String sourceName, String text, char delimiterStartChar, char delimiterStopChar) {
      super(delimiterStartChar, delimiterStopChar);
      this.alreadyLoaded = false;
      this.sourceName = sourceName;
      this.text = text;
   }

   public boolean isDictionary(String name) {
      if (!this.alreadyLoaded) {
         this.load();
      }

      return super.isDictionary(name);
   }

   public boolean isDefined(String name) {
      if (!this.alreadyLoaded) {
         this.load();
      }

      return super.isDefined(name);
   }

   protected CompiledST load(String name) {
      if (!this.alreadyLoaded) {
         this.load();
      }

      return this.rawGetTemplate(name);
   }

   public void load() {
      if (!this.alreadyLoaded) {
         this.alreadyLoaded = true;
         GroupParser parser = null;

         try {
            ANTLRStringStream fs = new ANTLRStringStream(this.text);
            fs.name = this.sourceName;
            GroupLexer lexer = new GroupLexer(fs);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            parser = new GroupParser(tokens);
            parser.group(this, "/");
         } catch (Exception var5) {
            this.errMgr.IOError((ST)null, ErrorType.CANT_LOAD_GROUP_FILE, var5, "<string>");
         }

      }
   }

   public String getFileName() {
      return "<string>";
   }
}
