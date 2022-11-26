package weblogic.ant.taskdefs.management;

import java.util.ArrayList;
import java.util.Iterator;

public class MBeanQueryCommand implements MBeanCommand {
   private String domainName = "*";
   private String mbeanName = null;
   private String mbeanType = null;
   private String pattern = null;
   private String property = null;
   private ArrayList setCommands = new ArrayList();
   private ArrayList getCommands = new ArrayList();
   private ArrayList createCommands = new ArrayList();
   private MBeanDeleteCommand mbdc = null;
   private MBeanInvokeCommand mbic = null;
   private String mbeanserver;

   public int getCommandType() {
      return 5;
   }

   public void setDomain(String domainName) {
      this.domainName = domainName;
   }

   public String getDomain() {
      return this.domainName;
   }

   public void setType(String mbeanType) {
      this.mbeanType = mbeanType;
   }

   public String getType() {
      return this.mbeanType;
   }

   public void setName(String mbeanName) {
      this.mbeanName = mbeanName;
   }

   public String getName() {
      return this.mbeanName;
   }

   public void setPattern(String pattern) {
      this.pattern = pattern;
   }

   public void setProperty(String property) {
      this.property = property;
   }

   public String getProperty() {
      return this.property;
   }

   public String getPattern() {
      return this.pattern != null ? this.pattern : this.domainName + ":*" + (this.mbeanName != null ? ",Name=" + this.mbeanName : "") + (this.mbeanType != null ? ",Type=" + this.mbeanType : "");
   }

   public String getCommoPattern() {
      return this.pattern != null ? this.pattern : this.domainName + ":*" + (this.mbeanName != null ? ",Name=" + this.mbeanName : "");
   }

   public void addSet(MBeanSetCommand set) {
      this.setCommands.add(set);
   }

   public void addGet(MBeanGetCommand get) {
      this.getCommands.add(get);
   }

   public void addCreate(MBeanCreateCommand create) {
      this.createCommands.add(create);
   }

   public void addDelete(MBeanDeleteCommand mbdc) {
      this.mbdc = mbdc;
   }

   public MBeanDeleteCommand getDeleteCommand() {
      return this.mbdc;
   }

   public void addInvoke(MBeanInvokeCommand mbic) {
      this.mbic = mbic;
   }

   public MBeanInvokeCommand getInvokeCommand() {
      return this.mbic;
   }

   public Iterator getSetCommands() {
      return this.setCommands.iterator();
   }

   public Iterator getGetCommands() {
      return this.getCommands.iterator();
   }

   public Iterator getCreateCommands() {
      return this.createCommands.iterator();
   }

   public String getMbeanserver() {
      return this.mbeanserver;
   }

   public void setMbeanserver(String mbeanserver) {
      this.mbeanserver = mbeanserver;
   }
}
