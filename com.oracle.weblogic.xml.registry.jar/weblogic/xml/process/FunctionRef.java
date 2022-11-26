package weblogic.xml.process;

public abstract class FunctionRef {
   public static final String FUNCTION_PREFIX = "@";
   public static final String FUNCTION_OPEN_BRACE = "{";
   public static final String FUNCTION_CLOSE_BRACE = "}";
   protected String name;

   public FunctionRef(String n) {
      this.name = n;
   }

   public String getName() {
      return this.name;
   }
}
