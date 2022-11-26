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

public class EjbReferenceDescriptionBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private EjbReferenceDescriptionBean beanTreeNode;

   public EjbReferenceDescriptionBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (EjbReferenceDescriptionBean)btn;
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
      return this.getEjbRefName();
   }

   public void initKeyPropertyValue(String value) {
      this.setEjbRefName(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("EjbRefName: ");
      sb.append(this.beanTreeNode.getEjbRefName());
      sb.append("\n");
      sb.append("JNDIName: ");
      sb.append(this.beanTreeNode.getJNDIName());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getEjbRefName() {
      return this.beanTreeNode.getEjbRefName();
   }

   public void setEjbRefName(String value) {
      this.beanTreeNode.setEjbRefName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "EjbRefName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getJNDIName() {
      return this.beanTreeNode.getJNDIName();
   }

   public void setJNDIName(String value) {
      this.beanTreeNode.setJNDIName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "JNDIName", (Object)null, (Object)null));
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
