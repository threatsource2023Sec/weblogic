package weblogic.ant.taskdefs.management;

public class MBeanGetCommand implements MBeanCommand {
   private String mbean;
   private String attribute;
   private String property = null;
   private String mbeanserver;

   public int getCommandType() {
      return 4;
   }

   public void setAttribute(String attribute) {
      this.attribute = attribute;
   }

   public String getAttribute() {
      return this.attribute;
   }

   public void setProperty(String property) {
      this.property = property;
   }

   public String getProperty() {
      return this.property;
   }

   public void setMBean(String mbean) {
      this.mbean = mbean;
   }

   public String getMBean() {
      return this.mbean;
   }

   public String getMbeanserver() {
      return this.mbeanserver;
   }

   public void setMbeanserver(String mbeanserver) {
      this.mbeanserver = mbeanserver;
   }
}
