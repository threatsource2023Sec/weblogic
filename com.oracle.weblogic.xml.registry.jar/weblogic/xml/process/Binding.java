package weblogic.xml.process;

public final class Binding {
   private static final boolean debug = false;
   private static final boolean verbose = false;
   private String className;
   private String variableName;
   private boolean hasDocumentScope = false;
   private boolean initialize = true;

   public void setClassName(String val) {
      this.className = val;
   }

   public String getClassName() {
      return this.className;
   }

   public void setVariableName(String val) {
      this.variableName = val;
   }

   public String getVariableName() {
      return this.variableName;
   }

   public void setHasDocumentScope(boolean val) {
      this.hasDocumentScope = val;
   }

   public boolean hasDocumentScope() {
      return this.hasDocumentScope;
   }

   public void setInitialize(boolean val) {
      this.initialize = val;
   }

   public boolean isInitialize() {
      return this.initialize;
   }
}
