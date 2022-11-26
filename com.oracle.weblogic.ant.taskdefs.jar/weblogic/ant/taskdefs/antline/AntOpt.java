package weblogic.ant.taskdefs.antline;

public class AntOpt {
   private String antName;
   private String attrName;
   private ArgConverter converter;
   private String eltPath;
   private String optValue;

   public AntOpt(String name, ArgConverter converter) {
      this.optValue = null;
      this.antName = name;
      int atIndex = this.antName.lastIndexOf(64);
      if (atIndex >= 0) {
         this.attrName = this.antName.substring(atIndex + 1);
         this.eltPath = this.antName.substring(0, atIndex);
      } else {
         this.eltPath = this.antName;
      }

      if (AntTool.debug || AntLineTask.debug) {
         System.out.println("AntOpt[" + System.identityHashCode(this) + "] = " + this.eltPath + "@" + this.attrName);
      }

   }

   public AntOpt(String name, String value) {
      this(name, (ArgConverter)null);
      this.setValue(value);
   }

   public String getAntName() {
      return this.antName;
   }

   public String getAntAttrName() {
      return this.attrName;
   }

   public String getAntElementPath() {
      return this.eltPath;
   }

   public void setValue(String val) {
      this.optValue = val;
   }

   public String getValue() {
      return this.optValue;
   }

   public void setConverter(ArgConverter c) {
      this.converter = c;
   }

   public ArgConverter getConverter() {
      return this.converter;
   }
}
