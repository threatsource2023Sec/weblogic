package weblogic.ant.taskdefs.antline;

import java.io.PrintStream;

/** @deprecated */
@Deprecated
public class Output {
   private PrintStream output;

   public Output(PrintStream output) {
      this.output = output;
   }

   public void print(Object value) {
      this.output.print(value == null ? "null" : value.toString());
   }

   public void println(Object value) {
      this.output.println(value == null ? "null" : value.toString());
   }
}
