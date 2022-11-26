package weblogic.j2ee.descriptor.wl;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.config.BasicDConfigBean;
import weblogic.descriptor.DescriptorBean;

public class LoggingBeanDConfigBeanInfo extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private LoggingBean beanTreeNode;

   public LoggingBeanDConfigBeanInfo(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (LoggingBean)btn;
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

   public String getLogFilename() {
      return this.beanTreeNode.getLogFilename();
   }

   public void setLogFilename(String value) {
      this.beanTreeNode.setLogFilename(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "LogFilename", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isLoggingEnabled() {
      return this.beanTreeNode.isLoggingEnabled();
   }

   public void setLoggingEnabled(boolean value) {
      this.beanTreeNode.setLoggingEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "LoggingEnabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getRotationType() {
      return this.beanTreeNode.getRotationType();
   }

   public void setRotationType(String value) {
      this.beanTreeNode.setRotationType(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RotationType", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isNumberOfFilesLimited() {
      return this.beanTreeNode.isNumberOfFilesLimited();
   }

   public void setNumberOfFilesLimited(boolean value) {
      this.beanTreeNode.setNumberOfFilesLimited(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "NumberOfFilesLimited", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getFileCount() {
      return this.beanTreeNode.getFileCount();
   }

   public void setFileCount(int value) {
      this.beanTreeNode.setFileCount(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "FileCount", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getFileSizeLimit() {
      return this.beanTreeNode.getFileSizeLimit();
   }

   public void setFileSizeLimit(int value) {
      this.beanTreeNode.setFileSizeLimit(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "FileSizeLimit", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isRotateLogOnStartup() {
      return this.beanTreeNode.isRotateLogOnStartup();
   }

   public void setRotateLogOnStartup(boolean value) {
      this.beanTreeNode.setRotateLogOnStartup(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RotateLogOnStartup", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getLogFileRotationDir() {
      return this.beanTreeNode.getLogFileRotationDir();
   }

   public void setLogFileRotationDir(String value) {
      this.beanTreeNode.setLogFileRotationDir(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "LogFileRotationDir", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getRotationTime() {
      return this.beanTreeNode.getRotationTime();
   }

   public void setRotationTime(String value) {
      this.beanTreeNode.setRotationTime(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RotationTime", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getFileTimeSpan() {
      return this.beanTreeNode.getFileTimeSpan();
   }

   public void setFileTimeSpan(int value) {
      this.beanTreeNode.setFileTimeSpan(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "FileTimeSpan", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getDateFormatPattern() {
      return this.beanTreeNode.getDateFormatPattern();
   }

   public void setDateFormatPattern(String value) {
      this.beanTreeNode.setDateFormatPattern(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DateFormatPattern", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getId() {
      return this.beanTreeNode.getId();
   }

   public void setId(String value) {
      this.beanTreeNode.setId(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Id", (Object)null, (Object)null));
      this.setModified(true);
   }
}
