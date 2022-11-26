package weblogic.deploy.api.spi.config;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.model.XpathEvent;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.exceptions.BeanNotFoundException;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import weblogic.deploy.api.internal.SPIDeployerLogger;
import weblogic.deploy.api.internal.utils.ConfigHelperLowLevel;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.shared.PlanHelper;
import weblogic.deploy.api.spi.WebLogicDConfigBean;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;

public abstract class BasicDConfigBean extends PlanHelper implements WebLogicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   protected boolean modified = false;
   protected BasicDConfigBean parent = null;
   protected List pclList = new ArrayList();
   private DDBean ddbean = null;
   protected String[] xpaths;
   private List childList = new ArrayList();
   private String name = "unknown";
   protected DescriptorBean beanTree;
   private String keyName = null;
   private String parentPropertyName;
   private boolean doNotWriteYet;
   private static final String FROM_DCB = "FROM_DCB";

   public BasicDConfigBean(DDBean ddb) {
      super(false);
      ConfigHelperLowLevel.checkParam("DDBean", ddb);
      if (debug) {
         Debug.say("Creating DConfigBean (" + this.getClass().getName() + ") for " + ddb.getXpath());
      }

      this.setDDBean(ddb);
      this.setName(this.ddbean.getXpath());
   }

   public void addPropertyChangeListener(PropertyChangeListener pcl) {
      ConfigHelperLowLevel.checkParam("PropertyChangeListener", pcl);
      this.pclList.add(pcl);
   }

   public void removePropertyChangeListener(PropertyChangeListener pcl) {
      int ndx = this.pclList.indexOf(pcl);
      if (ndx >= 0) {
         this.pclList.remove(ndx);
      }

   }

   public String[] getXpaths() {
      return this.xpaths;
   }

   public void removeDConfigBean(DConfigBean child) throws BeanNotFoundException {
      int ndx = this.childList.indexOf(child);
      if (ndx >= 0) {
         ((BasicDConfigBean)child).removeAllChildren();
         this.childList.remove(ndx);
      } else {
         String msg = SPIDeployerLogger.notAChild(((BasicDConfigBean)child).getName(), this.getName());
         throw new BeanNotFoundException(msg);
      }
   }

   public DDBean getDDBean() {
      return this.ddbean;
   }

   public DConfigBean getDConfigBean(DDBean bean) throws ConfigurationException {
      ConfigHelperLowLevel.checkParam("DDBean", bean);
      if (debug) {
         Debug.say(this.getName() + " getting DCB for bean: " + bean.getXpath());
      }

      if (this.isEquals(this, bean)) {
         if (debug) {
            Debug.say("Found DCB for bean: " + this.getName());
         }

         return this;
      } else {
         Iterator beans = this.getChildBeans().iterator();

         DConfigBean dcb;
         do {
            if (!beans.hasNext()) {
               if (debug) {
                  Debug.say("No DCB for bean: " + bean.getXpath());
               }

               dcb = this.createDConfigBean(bean, this);
               return dcb;
            }

            dcb = (DConfigBean)beans.next();
         } while(!this.isEquals((BasicDConfigBean)dcb, bean));

         if (debug) {
            Debug.say("Found DCB for bean: " + this.getName());
         }

         return dcb;
      }
   }

   private boolean isEquals(BasicDConfigBean dcb, DDBean bean) {
      return dcb.getDDBean().equals(bean);
   }

   protected String getKeyName() {
      return this.keyName;
   }

   protected void setKeyName(String keyName) {
      this.keyName = keyName;
   }

   public void notifyDDChange(XpathEvent event) {
   }

   public abstract void validate() throws ConfigurationException;

   public boolean isValid() {
      try {
         this.validate();
         return true;
      } catch (ConfigurationException var2) {
         return false;
      }
   }

   protected void firePropertyChange(PropertyChangeEvent pce) {
      Iterator pcls = this.getListeners().iterator();

      while(pcls.hasNext()) {
         ((PropertyChangeListener)pcls.next()).propertyChange(pce);
      }

   }

   protected abstract DConfigBean createDConfigBean(DDBean var1, DConfigBean var2) throws ConfigurationException;

   protected String getName() {
      return this.name;
   }

   protected void addDConfigBean(DConfigBean child) {
      this.childList.add(child);
   }

   public List getChildBeans() {
      return this.childList;
   }

   private List getListeners() {
      return this.pclList;
   }

   public void removeAllChildren() {
      this.childList = new ArrayList();
   }

   public boolean isModified() {
      return this.modified;
   }

   public void setModified(boolean propogate) {
      if (!this.modified) {
         if (debug) {
            Debug.say("modified dcb: " + this.getClass().getName());
         }

         this.modified = true;
         if (propogate && this.parent != null) {
            this.parent.setModified(true);
         }
      }

      if (this.doNotWriteYet) {
         if (debug) {
            Debug.say("set doNotWriteYet to false for dcb " + this);
         }

         this.doNotWriteYet = false;
         ((AbstractDescriptorBean)this.beanTree).setMetaData("FROM_DCB", new Boolean(false));
      }

   }

   public void setUnmodified() {
      this.modified = false;
   }

   protected void addToList(List list, String property, Object value) {
      int ndx = list.indexOf(value);
      if (ndx == -1) {
         list.add(value);
         this.firePropertyChange(new PropertyChangeEvent(this, property, (Object)null, (Object)null));
         this.setModified(true);
      }

   }

   protected void removeFromList(List list, String property, Object value) {
      int ndx = list.indexOf(value);
      if (ndx != -1) {
         list.remove(value);
         this.firePropertyChange(new PropertyChangeEvent(this, property, (Object)null, (Object)null));
         this.setModified(true);
      }

   }

   protected void replaceList(List list, String property, List value) {
      if (value == null) {
         value = new ArrayList();
      }

      list.clear();
      list.addAll((Collection)value);
      this.firePropertyChange(new PropertyChangeEvent(this, property, (Object)null, (Object)null));
      this.setModified(true);
   }

   public String toString() {
      return debug ? this.dumpAll(0) : this.dump(0);
   }

   public DescriptorBean getDescriptorBean() {
      return this.beanTree;
   }

   protected boolean isMatch(DescriptorBean bean, DDBean ddbean, String ddKey) {
      String key = this.getKeyValue(bean);
      return key != null && key.equals(ddKey);
   }

   protected String getDDKey(DDBean ddb, String key) {
      if (key.equals("id")) {
         return ddb.getId();
      } else {
         DDBean[] ddbs = ddb.getChildBean(this.applyNamespace(key));
         return ddbs != null ? ConfigHelperLowLevel.getText(ddbs[0]) : null;
      }
   }

   protected String lastElementOf(String path) {
      int n = path.lastIndexOf("/");
      return n == -1 ? path : path.substring(n + 1);
   }

   protected String getParentXpath(String xpath) {
      if (debug) {
         Debug.say("get parent of " + xpath);
      }

      return xpath.substring(0, xpath.lastIndexOf("/"));
   }

   public String applyNamespace(String xpath) {
      BasicDConfigBean dcb;
      for(dcb = this; !dcb.getDDBean().getXpath().equals("/"); dcb = dcb.getParent()) {
      }

      String ns = ((BasicDConfigBeanRoot)dcb).getNamespace();
      if (debug) {
         Debug.say("using " + ns + " as namespace");
      }

      return ConfigHelperLowLevel.applyNamespace(ns, xpath);
   }

   public BasicDConfigBean getParent() {
      if (debug) {
         Debug.say("Getting parent...");
      }

      return this.parent;
   }

   public String getParentPropertyName() {
      return this.parentPropertyName;
   }

   public void setParentPropertyName(String name) {
      this.parentPropertyName = name;
   }

   private String getKeyValue(DescriptorBean bean) {
      if (bean == null) {
         return null;
      } else {
         String key = null;

         try {
            BeanInfo info = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] props = info.getPropertyDescriptors();

            for(int i = 0; i < props.length; ++i) {
               PropertyDescriptor prop = props[i];
               if (Boolean.TRUE.equals(prop.getValue("key"))) {
                  Method getter = prop.getReadMethod();
                  key = (String)getter.invoke(bean);
                  break;
               }
            }
         } catch (Exception var8) {
            SPIDeployerLogger.logBeanError(bean.getClass().getName(), var8);
         }

         return key;
      }
   }

   private String dumpAll(int indent) {
      StringBuffer sb = new StringBuffer();
      sb.append(this.dump(indent));
      Iterator dcbs = this.getChildBeans().iterator();

      while(dcbs.hasNext()) {
         sb.append(((BasicDConfigBean)dcbs.next()).dumpAll(indent + 1));
      }

      return sb.toString();
   }

   private String dump(int indent) {
      StringBuffer sb = new StringBuffer();
      sb.append(this.indenter(indent));
      sb.append("Name: ");
      sb.append(this.getName());
      sb.append("\n");
      sb.append(this.indenter(indent));
      sb.append(this.getDCBProperties());
      return sb.toString();
   }

   protected abstract String getDCBProperties();

   private String indenter(int n) {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < n; ++i) {
         sb.append(" ");
      }

      return sb.toString();
   }

   private void setDDBean(DDBean ddb) {
      this.ddbean = ddb;
   }

   private void setName(String s) {
      this.name = "DConfigBean." + s;
   }

   protected String _getKeyValue(String[] s) {
      if (s == null) {
         return null;
      } else {
         StringBuffer sb = new StringBuffer(s[0]);

         for(int i = 1; i < s.length; ++i) {
            sb = sb.append(",").append(s[i]);
         }

         return sb.toString();
      }
   }

   protected void processDCB(BasicDConfigBean child, boolean newDCB) {
      BasicDConfigBean p;
      for(p = child; p.getParent() != null; p = p.getParent()) {
      }

      if (((BasicDConfigBeanRoot)p).isExternal()) {
         if (child.beanTree != null && child.beanTree instanceof AbstractDescriptorBean) {
            AbstractDescriptorBean beanTreeNode = (AbstractDescriptorBean)child.beanTree;
            if (newDCB) {
               child.setUnmodified();
               child.doNotWriteYet = true;
               beanTreeNode.setMetaData("FROM_DCB", new Boolean(true));
            } else if (beanTreeNode.getMetaData("FROM_DCB") != null) {
               Boolean fromDCB = (Boolean)beanTreeNode.getMetaData("FROM_DCB");
               if (fromDCB) {
                  child.doNotWriteYet = true;
               }
            }

         }
      }
   }

   void clearUnmodifiedElementsFromDescriptor() {
      if (debug) {
         Debug.say(this.getName() + " clearing unmodified descriptor elements");
      }

      Set unSetProperty = new TreeSet();
      Set setProperty = new TreeSet();
      Iterator beans = this.getChildBeans().iterator();

      while(beans.hasNext()) {
         BasicDConfigBean dcb = (BasicDConfigBean)beans.next();
         if (debug) {
            Debug.say("Checking child dconfig bean: " + dcb.getName());
         }

         if (dcb.isModified()) {
            dcb.clearUnmodifiedElementsFromDescriptor();
            if (dcb.getParentPropertyName() != null) {
               setProperty.add(dcb.getParentPropertyName());
            }
         }

         if (dcb.getParentPropertyName() != null && dcb.doNotWriteYet) {
            if (debug) {
               Debug.say("Child dconfig bean is not modified. Parent property is: " + dcb.getParentPropertyName());
            }

            unSetProperty.add(dcb.getParentPropertyName());
         }
      }

      if (!unSetProperty.isEmpty()) {
         String unSetPropertyName;
         Iterator unSetIter;
         if (!setProperty.isEmpty()) {
            unSetIter = setProperty.iterator();

            while(unSetIter.hasNext()) {
               unSetPropertyName = (String)unSetIter.next();
               unSetProperty.remove(unSetPropertyName);
            }
         }

         if (!unSetProperty.isEmpty()) {
            unSetIter = unSetProperty.iterator();

            while(unSetIter.hasNext()) {
               unSetPropertyName = (String)unSetIter.next();
               this.getDescriptorBean().unSet(unSetPropertyName);
            }
         }
      }

   }

   void restoreUnmodifiedElementsToDescriptor() {
      if (debug) {
         Debug.say(this.getName() + " restoring unmodified descriptor elements");
      }

      Iterator beans = this.getChildBeans().iterator();

      while(beans.hasNext()) {
         BasicDConfigBean dcb = (BasicDConfigBean)beans.next();
         if (debug) {
            Debug.say("Checking child dconfig bean: " + dcb.getName());
         }

         if (dcb.isModified()) {
            dcb.restoreUnmodifiedElementsToDescriptor();
         }

         if (dcb.getParentPropertyName() != null && this.getDescriptorBean() instanceof AbstractDescriptorBean && dcb.doNotWriteYet) {
            if (debug) {
               Debug.say("Child dconfig bean is not modified. Parent property is: " + dcb.getParentPropertyName());
            }

            AbstractDescriptorBean descBean = (AbstractDescriptorBean)this.getDescriptorBean();
            descBean.markSet(dcb.getParentPropertyName());
         }
      }

   }

   public boolean hasCustomInit() {
      return false;
   }
}
