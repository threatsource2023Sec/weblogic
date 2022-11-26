package weblogic.ant.taskdefs.management;

public class MBeanDeleteCommand implements MBeanCommand {
   private String mbean;

   public int getCommandType() {
      return 2;
   }

   public void setMBean(String mbean) {
      this.mbean = mbean;
   }

   public String getMBean() {
      return this.mbean;
   }
}
