package weblogic.diagnostics.instrumentation;

public class GatheredArgumentImpl implements GatheredArgument {
   private String argumentName;
   private int argumentIndex;

   public GatheredArgumentImpl(String name, int index) {
      this.argumentName = name;
      this.argumentIndex = index;
   }

   public String getArgumentName() {
      return this.argumentName;
   }

   public int getArgumentIndex() {
      return this.argumentIndex;
   }
}
