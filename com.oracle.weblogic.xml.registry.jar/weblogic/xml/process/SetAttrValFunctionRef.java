package weblogic.xml.process;

public class SetAttrValFunctionRef extends FunctionRef {
   private static final boolean debug = true;
   private static final boolean verbose = true;
   private String attrName;
   private String text;
   private String fromVar;

   public SetAttrValFunctionRef(String name, String attrVal) {
      this(name);
      this.text = attrVal;
   }

   public SetAttrValFunctionRef(String name) {
      super("SET_ATTRIBUTE_VALUE");
      this.attrName = name;
   }

   public String getAttrName() {
      return this.attrName;
   }

   public String getAttrVal() {
      return this.text;
   }

   public void setAttrVal(String val) {
      this.text = val;
   }

   public void setFromVar(String val) {
      this.fromVar = val;
   }

   public String getFromVar() {
      return this.fromVar;
   }
}
