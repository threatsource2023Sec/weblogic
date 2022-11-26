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

public class CoherenceClusterRefBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private CoherenceClusterRefBean beanTreeNode;

   public CoherenceClusterRefBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (CoherenceClusterRefBean)btn;
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
      return this.getCoherenceClusterName();
   }

   public void initKeyPropertyValue(String value) {
      this.setCoherenceClusterName(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("CoherenceClusterName: ");
      sb.append(this.beanTreeNode.getCoherenceClusterName());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getCoherenceClusterName() {
      return this.beanTreeNode.getCoherenceClusterName();
   }

   public void setCoherenceClusterName(String value) {
      this.beanTreeNode.setCoherenceClusterName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CoherenceClusterName", (Object)null, (Object)null));
      this.setModified(true);
   }
}
