package antlr;

import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;

public class NameSpace {
   private Vector names = new Vector();
   private String _name;

   public NameSpace(String var1) {
      this._name = new String(var1);
      this.parse(var1);
   }

   public String getName() {
      return this._name;
   }

   protected void parse(String var1) {
      StringTokenizer var2 = new StringTokenizer(var1, "::");

      while(var2.hasMoreTokens()) {
         this.names.addElement(var2.nextToken());
      }

   }

   void emitDeclarations(PrintWriter var1) {
      Enumeration var2 = this.names.elements();

      while(var2.hasMoreElements()) {
         String var3 = (String)var2.nextElement();
         var1.println("ANTLR_BEGIN_NAMESPACE(" + var3 + ")");
      }

   }

   void emitClosures(PrintWriter var1) {
      for(int var2 = 0; var2 < this.names.size(); ++var2) {
         var1.println("ANTLR_END_NAMESPACE");
      }

   }
}
