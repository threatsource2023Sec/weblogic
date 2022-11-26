package weblogic.javaee;

public enum WarningCode {
   BEA_010001("BEA-010001"),
   BEA_010200("BEA-010200"),
   BEA_010202("BEA-010202"),
   BEA_010054("BEA-010054");

   private final String weblogicCode;

   private WarningCode(String weblogicCode) {
      this.weblogicCode = weblogicCode;
   }

   public String getWeblogicCode() {
      return this.weblogicCode;
   }
}
