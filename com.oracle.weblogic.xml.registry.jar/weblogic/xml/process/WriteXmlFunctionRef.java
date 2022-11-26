package weblogic.xml.process;

import java.util.Collection;

public final class WriteXmlFunctionRef extends FunctionRef {
   private static final boolean debug = true;
   private static final boolean verbose = true;
   private String elementName;
   private String elementContext;
   private String fromVar;
   private Collection args;

   public WriteXmlFunctionRef(String elt, String eltCtx) {
      super("WRITE_XML");
      this.elementName = elt;
      this.elementContext = eltCtx;
   }

   public String getElementName() {
      return this.elementName;
   }

   public String getElementContext() {
      return this.elementContext;
   }

   public void setFromVar(String val) {
      this.fromVar = val;
   }

   public String getFromVar() {
      return this.fromVar;
   }

   public void setArgs(Collection val) {
      this.args = val;
   }

   public Collection getArgs() {
      return this.args;
   }
}
