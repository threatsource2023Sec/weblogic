package antlr.debug;

public class Tracer extends TraceAdapter implements TraceListener {
   String indent = "";

   protected void dedent() {
      if (this.indent.length() < 2) {
         this.indent = "";
      } else {
         this.indent = this.indent.substring(2);
      }

   }

   public void enterRule(TraceEvent var1) {
      System.out.println(this.indent + var1);
      this.indent();
   }

   public void exitRule(TraceEvent var1) {
      this.dedent();
      System.out.println(this.indent + var1);
   }

   protected void indent() {
      this.indent = this.indent + "  ";
   }
}
