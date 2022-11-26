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

public class DriverParamsBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private DriverParamsBean beanTreeNode;

   public DriverParamsBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (DriverParamsBean)btn;
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

   public StatementBean getStatement() {
      return this.beanTreeNode.getStatement();
   }

   public PreparedStatementBean getPreparedStatement() {
      return this.beanTreeNode.getPreparedStatement();
   }

   public boolean isRowPrefetchEnabled() {
      return this.beanTreeNode.isRowPrefetchEnabled();
   }

   public void setRowPrefetchEnabled(boolean value) {
      this.beanTreeNode.setRowPrefetchEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RowPrefetchEnabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getRowPrefetchSize() {
      return this.beanTreeNode.getRowPrefetchSize();
   }

   public void setRowPrefetchSize(int value) {
      this.beanTreeNode.setRowPrefetchSize(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RowPrefetchSize", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getStreamChunkSize() {
      return this.beanTreeNode.getStreamChunkSize();
   }

   public void setStreamChunkSize(int value) {
      this.beanTreeNode.setStreamChunkSize(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "StreamChunkSize", (Object)null, (Object)null));
      this.setModified(true);
   }
}
