package weblogic.ant.taskdefs.management;

import java.util.ArrayList;
import java.util.Iterator;

public class MBeanInvokeCommand implements MBeanCommand {
   private String mbeanType;
   private String methodName;
   private String mbean;
   private String arguments;
   private String domain;
   private String mbeanName;
   private ArrayList invokeCommands = new ArrayList();

   public int getCommandType() {
      return 7;
   }

   public void setType(String mbeanType) {
      this.mbeanType = mbeanType;
   }

   public String getType() {
      return this.mbeanType;
   }

   public void setDomain(String domain) {
      this.domain = domain;
   }

   public String getDomain() {
      return this.domain;
   }

   public void setName(String mbeanName) {
      this.mbeanName = mbeanName;
   }

   public String getName() {
      return this.mbeanName;
   }

   public void setMethodName(String methodName) {
      this.methodName = methodName;
   }

   public String getMethodName() {
      return this.methodName;
   }

   public void setMBean(String mbean) {
      this.mbean = mbean;
   }

   public String getMBean() {
      return this.mbean;
   }

   public void setArguments(String args) {
      this.arguments = args;
   }

   public String getArguments() {
      return this.arguments;
   }

   public void addInvoke(MBeanInvokeCommand mbic) {
      this.invokeCommands.add(mbic);
   }

   public Iterator getInvokeCommands() {
      return this.invokeCommands.iterator();
   }
}
