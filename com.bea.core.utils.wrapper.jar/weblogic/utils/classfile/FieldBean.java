package weblogic.utils.classfile;

public class FieldBean extends BaseClassBean {
   private String name;
   private String type;

   FieldBean(String n, String t, int m) {
      this.name = n;
      this.type = t;
      this.modifiers = m;
   }

   public String getName() {
      return this.name;
   }

   public String getType() {
      return this.type;
   }

   public String toString() {
      return "[field: name=" + this.name + " type=" + this.type + "]";
   }
}
