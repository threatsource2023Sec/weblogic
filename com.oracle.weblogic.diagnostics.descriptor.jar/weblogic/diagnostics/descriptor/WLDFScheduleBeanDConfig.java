package weblogic.diagnostics.descriptor;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.config.BasicDConfigBean;
import weblogic.descriptor.DescriptorBean;

public class WLDFScheduleBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private WLDFScheduleBean beanTreeNode;

   public WLDFScheduleBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (WLDFScheduleBean)btn;
      this.beanTree = btn;
      this.parent = (BasicDConfigBean)parent;
      this.initXpaths();
      this.customInit();
   }

   private void initXpaths() throws ConfigurationException {
      List xlist = new ArrayList();
      this.xpaths = (String[])((String[])xlist.toArray(new String[0]));
   }

   private void customInit() throws ConfigurationException {
   }

   public DConfigBean createDConfigBean(DDBean ddb, DConfigBean parent) throws ConfigurationException {
      return null;
   }

   public String keyPropertyValue() {
      return null;
   }

   public void initKeyPropertyValue(String value) {
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getHour() {
      return this.beanTreeNode.getHour();
   }

   public void setHour(String value) {
      this.beanTreeNode.setHour(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Hour", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getMinute() {
      return this.beanTreeNode.getMinute();
   }

   public void setMinute(String value) {
      this.beanTreeNode.setMinute(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Minute", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getSecond() {
      return this.beanTreeNode.getSecond();
   }

   public void setSecond(String value) {
      this.beanTreeNode.setSecond(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Second", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getYear() {
      return this.beanTreeNode.getYear();
   }

   public void setYear(String value) {
      this.beanTreeNode.setYear(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Year", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getMonth() {
      return this.beanTreeNode.getMonth();
   }

   public void setMonth(String value) {
      this.beanTreeNode.setMonth(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Month", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getDayOfMonth() {
      return this.beanTreeNode.getDayOfMonth();
   }

   public void setDayOfMonth(String value) {
      this.beanTreeNode.setDayOfMonth(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DayOfMonth", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getDayOfWeek() {
      return this.beanTreeNode.getDayOfWeek();
   }

   public void setDayOfWeek(String value) {
      this.beanTreeNode.setDayOfWeek(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DayOfWeek", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getTimezone() {
      return this.beanTreeNode.getTimezone();
   }

   public void setTimezone(String value) {
      this.beanTreeNode.setTimezone(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Timezone", (Object)null, (Object)null));
      this.setModified(true);
   }
}
