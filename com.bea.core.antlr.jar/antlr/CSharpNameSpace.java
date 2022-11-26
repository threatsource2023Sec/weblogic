package antlr;

import java.io.PrintWriter;

public class CSharpNameSpace extends NameSpace {
   public CSharpNameSpace(String var1) {
      super(var1);
   }

   void emitDeclarations(PrintWriter var1) {
      var1.println("namespace " + this.getName());
      var1.println("{");
   }

   void emitClosures(PrintWriter var1) {
      var1.println("}");
   }
}
