package weblogic.ant.taskdefs.management;

public class MBeanSetCommand implements MBeanCommand {
   private String mbean;
   private String attribute;
   private String value;

   public int getCommandType() {
      return 3;
   }

   public void setAttribute(String attribute) {
      this.attribute = attribute;
   }

   public String getAttribute() {
      return this.attribute;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public String getValue() {
      return this.value;
   }

   public void setMBean(String mbean) {
      this.mbean = mbean;
   }

   public String getMBean() {
      return this.mbean;
   }
}
