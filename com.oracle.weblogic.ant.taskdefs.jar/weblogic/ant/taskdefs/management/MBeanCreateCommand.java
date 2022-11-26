package weblogic.ant.taskdefs.management;

import java.util.ArrayList;
import java.util.Iterator;

public class MBeanCreateCommand implements MBeanCommand {
   private String mbeanType;
   private String mbeanName;
   private String mbean;
   private String property;
   private String domain;
   private String realm;
   private ArrayList setCommands = new ArrayList();
   private ArrayList createCommands = new ArrayList();
   private ArrayList invokeCommands = new ArrayList();

   public int getCommandType() {
      return 1;
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

   public void setRealm(String realm) {
      this.realm = realm;
   }

   public String getRealm() {
      return this.realm;
   }

   public void setName(String mbeanName) {
      this.mbeanName = mbeanName;
   }

   public String getName() {
      return this.mbeanName;
   }

   public void setProperty(String property) {
      this.property = property;
   }

   public String getProperty() {
      return this.property;
   }

   public void addSet(MBeanSetCommand set) {
      this.setCommands.add(set);
   }

   public Iterator getSetCommands() {
      return this.setCommands.iterator();
   }

   public void addInvoke(MBeanInvokeCommand invokes) {
      this.invokeCommands.add(invokes);
   }

   public Iterator getInvokeCommands() {
      return this.invokeCommands.iterator();
   }

   public void addCreate(MBeanCreateCommand mbcc) {
      this.createCommands.add(mbcc);
   }

   public Iterator getCreateCommands() {
      return this.createCommands.iterator();
   }
}
