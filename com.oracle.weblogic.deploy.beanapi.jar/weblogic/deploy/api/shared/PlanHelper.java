package weblogic.deploy.api.shared;

import java.beans.PropertyChangeListener;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import weblogic.deploy.api.internal.SPIDeployerLogger;
import weblogic.deploy.api.internal.utils.ConfigHelperLowLevel;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.WebLogicDeploymentConfiguration;
import weblogic.deploy.api.spi.config.BasicDConfigBeanRoot;
import weblogic.deploy.api.spi.config.DescriptorSupport;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.DescriptorHelper;
import weblogic.j2ee.descriptor.EmptyBean;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.ModuleDescriptorBean;
import weblogic.j2ee.descriptor.wl.VariableAssignmentBean;
import weblogic.j2ee.descriptor.wl.VariableBean;
import weblogic.logging.Loggable;
import weblogic.utils.ArrayUtils;

public abstract class PlanHelper implements PlanConstants {
   public static final String WSEE_WEB_URI_81 = "WEB-INF/web-services.xml";
   public static final String WSEE_EJB_URI_81 = "META-INF/web-services.xml";
   public static final String WSEE_WEB_URI = "WEB-INF/webservices.xml";
   public static final String WSEE_EJB_URI = "META-INF/webservices.xml";
   private static final boolean debug = Debug.isDebug("config");
   protected static final String FROM_LISTENER = "FROM_LISTENER";
   private boolean isDDBeanRoot;
   private String appName;
   protected List bl = new ArrayList();
   private DescriptorHelper ddhelper = null;
   protected WebLogicDeploymentConfiguration dc;
   private boolean planBasedDBean = false;
   private List newBeans = new ArrayList();
   protected DescriptorSupport descriptorSupport = null;

   public PlanHelper(boolean isDDBeanRoot) {
      this.isDDBeanRoot = isDDBeanRoot;
   }

   public void setAppName(String name) {
      this.appName = name;
   }

   public String getAppName() {
      return this.appName;
   }

   public DescriptorSupport getDescriptorSupport() {
      return this.descriptorSupport;
   }

   public DescriptorHelper getDescriptorHelper() {
      if (this.ddhelper == null) {
         this.ddhelper = DescriptorHelper.getInstance();
      }

      return this.ddhelper;
   }

   public boolean findDescriptorBean(DescriptorBean bean) {
      Iterator regs = this.bl.iterator();

      do {
         if (!regs.hasNext()) {
            return false;
         }
      } while(regs.next() != bean);

      return true;
   }

   public void registerAsListener(DescriptorBean bean) {
      if (!this.findDescriptorBean(bean)) {
         this.registerChildAsListener(bean);
         this.registerWebservices("WEB-INF/webservices.xml");
         this.registerWebservices("WEB-INF/web-services.xml");
         this.registerWebservices("META-INF/webservices.xml");
         this.registerWebservices("META-INF/web-services.xml");
      }

   }

   public void registerChildAsListener(DescriptorBean bean) {
      if (!this.findDescriptorBean(bean) && this.bl.add(bean)) {
         if (debug) {
            Debug.say("Registering listener for " + bean.toString());
         }

         if (this.isDDBeanRoot) {
            this.getDescriptorHelper().addPropertyChangeListener(bean, (PropertyChangeListener)this);
         } else {
            this.getDescriptorHelper().addPropertyChangeListener(bean, (BasicDConfigBeanRoot)this);
         }
      }

      Iterator beans = this.getDescriptorHelper().getChildren(bean);

      while(beans.hasNext()) {
         DescriptorBean o = (DescriptorBean)beans.next();
         this.registerChildAsListener(o);
      }

   }

   public void deregisterAsListener(DescriptorBean bean) {
      if (this.findDescriptorBean(bean)) {
         this.deregisterChildAsListener(bean);
         this.deregisterWebservices("WEB-INF/webservices.xml");
         this.deregisterWebservices("WEB-INF/web-services.xml");
         this.deregisterWebservices("META-INF/webservices.xml");
         this.deregisterWebservices("META-INF/web-services.xml");
      }

   }

   protected void deregisterChildAsListener(DescriptorBean bean) {
      if (this.bl.remove(bean)) {
         if (debug) {
            Debug.say("Deregistering listener for " + bean.toString());
         }

         if (this.isDDBeanRoot) {
            this.getDescriptorHelper().removePropertyChangeListener(bean, (PropertyChangeListener)this);
         } else {
            this.getDescriptorHelper().removePropertyChangeListener(bean, (BasicDConfigBeanRoot)this);
         }
      }

      Iterator beans = this.getDescriptorHelper().getChildren(bean);

      while(beans.hasNext()) {
         DescriptorBean o = (DescriptorBean)beans.next();
         this.deregisterChildAsListener(o);
      }

   }

   protected void registerWebservices(String uri) {
      throw new RuntimeException("registerWebservices called incorrectly");
   }

   protected void deregisterWebservices(String uri) {
      throw new RuntimeException("deregisterWebservices called incorrectly");
   }

   protected void variabilizeDescriptorBeanArray(ModuleDescriptorBean md, Object oldValue, Object newValue, boolean deleteOnly) {
      if (debug) {
         Debug.say("variabilizing array of descriptor beans");
      }

      Object[] oldv = this.convertArray(oldValue);
      if (oldv == null) {
         oldv = new Object[0];
      }

      Object[] newv = this.convertArray(newValue);
      if (newv == null) {
         newv = new Object[0];
      }

      ArrayList added = new ArrayList();
      ArrayList removed = new ArrayList();
      ArrayUtils.computeDiff(oldv, newv, this.createDiffHandler(added, removed));
      this.clearLocalCache(removed.iterator());
      if (!deleteOnly) {
         this.addDescriptorBeans(added);
      }

      this.removeDescriptorBeans(md, removed);
   }

   private String variabilizeArray(Object oldValue, Object newValue, VariableBean var) {
      Object[] newv = this.convertArray(newValue);
      if (newv == null) {
         newv = new Object[0];
      }

      return this.handleSetOperation(newv, var);
   }

   private Object[] convertArray(Object value) {
      if (value == null) {
         return null;
      } else {
         int len = Array.getLength(value);
         Object[] newv = new Object[len];

         for(int i = 0; i < len; ++i) {
            Object o = Array.get(value, i);
            if (o.getClass().isPrimitive()) {
               newv[i] = o.toString();
            } else {
               newv[i] = o;
            }
         }

         return newv;
      }
   }

   private ArrayUtils.DiffHandler createDiffHandler(final ArrayList addList, final ArrayList removeList) {
      return new ArrayUtils.DiffHandler() {
         public void addObject(Object added) {
            addList.add(added);
         }

         public void removeObject(Object removed) {
            removeList.add(removed);
         }
      };
   }

   private void clearLocalCache(Iterator toRemove) {
      while(toRemove.hasNext()) {
         this.bl.remove(toRemove.next());
      }

   }

   private void addDescriptorBeans(ArrayList added) {
      for(Iterator theDBeans = added.iterator(); theDBeans.hasNext(); this.variabilizeBean((DescriptorBean)theDBeans.next())) {
         if (debug) {
            Debug.say("Adding a descriptor bean");
         }
      }

   }

   private void removeDescriptorBeans(ModuleDescriptorBean md, ArrayList removed) {
      Iterator theDBeans = removed.iterator();

      while(theDBeans.hasNext()) {
         DescriptorBean dBean = (DescriptorBean)theDBeans.next();
         this.removeDescriptorBean(md, dBean);
      }

   }

   private void removeDescriptorBean(ModuleDescriptorBean md, DescriptorBean dBean) {
      if (debug) {
         Debug.say("Removing descriptor bean: " + dBean);
      }

      try {
         this.dc.getPlan().findAndRemoveAllBeanVariables(md, dBean);
      } catch (IllegalArgumentException var5) {
         Loggable l = SPIDeployerLogger.logUnableToRemoveDescriptorBeanLoggable(dBean.toString(), var5.getMessage());
         throw new IllegalArgumentException(l.getMessage());
      }
   }

   protected void loadVariable(List values, VariableBean var) {
      String sval = new String();

      for(int i = 0; i < values.size(); ++i) {
         if (i > 0) {
            sval = sval + ",";
         }

         Object o = values.get(i);
         sval = sval + "\"" + o.toString() + "\"";
      }

      var.setValue(sval);
   }

   private String handleSetOperation(Object[] newValue, VariableBean var) {
      this.loadVariable(Arrays.asList(newValue), var);
      return "replace";
   }

   private void variabilizeBean(DescriptorBean ddbean) {
      this.setPlanBasedDBean(true);
      String xpathKey = this.getDescriptorHelper().buildKeyXpath(ddbean);
      if (debug) {
         Debug.say("xpathKey = " + xpathKey);
      }

      this.newBeans.add(xpathKey);
      AbstractDescriptorBean newBean = (AbstractDescriptorBean)ddbean;
      newBean.setMetaData("FROM_LISTENER", new Boolean(true));
      if (debug) {
         Debug.say("Adding properties for new bean: " + newBean);
      }

      List l = newBean._getAlreadySetPropertyNames();
      Iterator i = l.iterator();

      while(true) {
         while(i.hasNext()) {
            String propName = (String)i.next();
            if (debug) {
               Debug.say("Try to set property : " + propName);
            }

            Object value;
            if (this.getDescriptorHelper().isChangable(ddbean, propName) && !this.getDescriptorHelper().isTransient(ddbean, propName)) {
               try {
                  value = invokeAccessor(ddbean, propName);
                  if (debug) {
                     Debug.say("Got value: " + value);
                  }

                  this.handleChange(ddbean, propName, (Object)null, value);
               } catch (Exception var8) {
                  throw new AssertionError("Caught exception trying to invoke accessor on " + ddbean + " - called 'get" + propName + "()'.");
               }
            } else if (this.emptyBean(l, ddbean, propName)) {
               try {
                  value = invokeAccessor(ddbean, propName);
                  this.addEmptyBeanKey(ddbean, propName, value);
               } catch (Exception var9) {
                  throw new AssertionError("Caught exception trying to invoke accessor on " + ddbean + " - called 'get" + propName + "()'.");
               }
            }
         }

         return;
      }
   }

   public void setPlanBasedDBean(boolean planBased) {
      this.planBasedDBean = planBased;
   }

   public boolean getPlanBasedDBean() {
      return this.planBasedDBean;
   }

   public static Object invokeAccessor(Object bean, String propName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
      Class clazz = bean.getClass();

      Method m;
      try {
         m = clazz.getMethod("get" + propName);
      } catch (NoSuchMethodException var7) {
         try {
            m = clazz.getMethod("is" + propName);
         } catch (NoSuchMethodException var6) {
            throw var7;
         }
      }

      return m.invoke(bean);
   }

   protected void handleChange(DescriptorBean bean, String prop, Object oldValue, Object newValue) {
      if (prop != null) {
         PropertyDescriptor pd = this.getDescriptorHelper().getPropertyDescriptor(bean, prop);
         if (pd == null) {
            return;
         }

         String xpathKey = this.getDescriptorHelper().buildKeyXpath(bean);
         if (debug) {
            Debug.say("xpathKey = " + xpathKey);
         }

         if (this.newBeans.contains(xpathKey)) {
            this.setPlanBasedDBean(true);
            if (debug) {
               Debug.say("xpathkey found in newbeans");
            }
         } else if (bean instanceof AbstractDescriptorBean && ((AbstractDescriptorBean)bean).getMetaData("FROM_LISTENER") != null) {
            this.setPlanBasedDBean(true);
            if (debug) {
               Debug.say("from listener found in bean");
            }
         }

         Method m = pd.getReadMethod();
         Class c1 = m.getReturnType();
         Class c2 = c1;
         if (c1.isArray()) {
            c2 = c1.getComponentType();
         }

         if (!c2.isPrimitive() && !c2.getPackage().getName().equals("java.lang") && !EmptyBean.class.isAssignableFrom(c2)) {
            if (debug) {
               Debug.say("Variabilizing object change ");
            }

            if (debug && newValue == null) {
               Debug.say("New object is null");
            }

            this.variabilize(bean, prop, oldValue, newValue, c1);
         } else if (this.isSchemaBased()) {
            this.variabilize(bean, prop, oldValue, newValue, c1);
         } else {
            SPIDeployerLogger.logDTDDDUpdate(prop, this.getDescriptorSupportURI(), this.getAppName());
         }
      }

   }

   protected void reregister(DescriptorBean bean) {
      this.deregisterAsListener(bean);
      this.registerAsListener(bean);
   }

   private boolean emptyBean(List l, DescriptorBean ddbean, String propName) {
      if (debug) {
         Debug.say("Checking list size: " + l.size() + " against Bean with prop: " + propName);
      }

      return l.size() <= 1 && this.getDescriptorHelper().isKey(ddbean, propName);
   }

   private void addEmptyBeanKey(DescriptorBean ddbean, String prop, Object newValue) {
      ModuleDescriptorBean md = this.dc.getPlan().findModuleDescriptor(this.getAppName(), this.getDescriptorSupportURI());
      VariableBean var = this.dc.getPlan().findOrCreateVariable(md, ddbean, prop, this.getPlanBasedDBean());
      var.setValue(newValue.toString());
   }

   public boolean isSchemaBased() {
      throw new RuntimeException("isSchemaBased called incorrectly");
   }

   protected void variabilize(DescriptorBean bean, String prop, Object oldValue, Object newValue, Class propClass) {
      String uri = this.getDescriptorSupportURI();
      if (debug) {
         Debug.say("Getting module descriptor bean for " + this.getAppName() + " and " + uri + " for prop " + prop);
      }

      ModuleDescriptorBean md = this.dc.getPlan().findModuleDescriptor(this.getAppName(), uri);
      if (md != null) {
         if (debug) {
            Debug.say("Changeable: " + this.getDescriptorHelper().isChangable(bean, prop) + " transient: " + this.getDescriptorHelper().isTransient(bean, prop));
         }

         if (this.getDescriptorHelper().isChangable(bean, prop)) {
            md.setChanged(true);
            if (newValue instanceof DescriptorBean) {
               this.variabilizeBean((DescriptorBean)newValue);
               return;
            }

            if (oldValue instanceof DescriptorBean && newValue == null) {
               if (this.dc.getPlan().isRemovable((DescriptorBean)oldValue)) {
                  this.removeDescriptorBean(md, (DescriptorBean)oldValue);
               } else {
                  this.dc.getPlan().findOrCreateVariable(md, bean, prop, this.getPlanBasedDBean());
                  VariableAssignmentBean vab = this.dc.getPlan().findVariableAssignment(md, bean, prop);
                  if (vab != null) {
                     vab.setOperation("remove");
                  }
               }

               return;
            }

            if (!this.isSeparableArray(propClass)) {
               boolean isKey = this.getDescriptorHelper().isKey(bean, prop) || this.getDescriptorHelper().isKeyComponent(bean, prop);
               Object oldKeyValue = isKey ? oldValue : null;
               VariableBean var = null;
               if (isKey && !this.getPlanBasedDBean()) {
                  if (debug) {
                     Debug.say("findOrCreateVariable from is key, old key = " + oldKeyValue);
                  }

                  var = this.dc.getPlan().findOrCreateVariable(md, bean, prop, this.getPlanBasedDBean(), oldKeyValue);
               } else {
                  if (debug) {
                     Debug.say("findOrCreateVariable is plan based = " + this.getPlanBasedDBean());
                  }

                  var = this.dc.getPlan().findOrCreateVariable(md, bean, prop, this.getPlanBasedDBean());
               }

               if (newValue == null) {
                  var.setValue((String)null);
               } else if (propClass.isArray()) {
                  var.setValue(new String((byte[])((byte[])newValue)));
               } else {
                  var.setValue(newValue.toString());
               }
            } else {
               VariableBean var = null;
               if (this.isArrayOfDescriptorBeans(oldValue, newValue)) {
                  this.variabilizeDescriptorBeanArray(md, oldValue, newValue, false);
               } else {
                  if (debug) {
                     Debug.say("Variabilize nonbean array");
                  }

                  var = this.dc.getPlan().findOrCreateVariable(md, bean, prop, this.getPlanBasedDBean());
                  String type = this.variabilizeArray(oldValue, newValue, var);
                  this.updateOperation(type, var);
               }
            }
         } else if (this.isArrayOfDescriptorBeans(oldValue, newValue)) {
            this.variabilizeDescriptorBeanArray(md, oldValue, newValue, true);
         }
      }

   }

   private boolean isSeparableArray(Class propClass) {
      return propClass.isArray() && !Byte.TYPE.isAssignableFrom(propClass.getComponentType());
   }

   private boolean isArrayOfDescriptorBeans(Object oldVal, Object newVal) {
      int len;
      Object o;
      if (newVal != null && newVal.getClass().isArray()) {
         len = Array.getLength(newVal);
         if (len > 0) {
            o = Array.get(newVal, 0);
            if (o instanceof DescriptorBean) {
               return true;
            }
         }
      }

      if (oldVal != null && oldVal.getClass().isArray()) {
         len = Array.getLength(oldVal);
         if (len > 0) {
            o = Array.get(oldVal, 0);
            if (o instanceof DescriptorBean) {
               return true;
            }
         }
      }

      return false;
   }

   private void updateOperation(String operation, VariableBean var) {
      if (operation != null) {
         VariableAssignmentBean[] assigns = this.dc.getPlan().findVariableAssignments(var);

         for(int i = 0; i < assigns.length; ++i) {
            VariableAssignmentBean assign = assigns[i];
            assign.setOperation(operation);
         }
      }

   }

   public abstract DescriptorBean getDescriptorBean();

   public Iterator getChildren(DescriptorBean bean) {
      return this.getDescriptorHelper().getChildren(bean);
   }

   private DescriptorBean[] findBeans(DescriptorBean bean) {
      List beans = new ArrayList();
      beans.add(bean);
      Iterator bi = this.getChildren(bean);

      while(bi.hasNext()) {
         DescriptorBean b = (DescriptorBean)bi.next();
         beans.addAll(Arrays.asList(this.findBeans(b)));
      }

      return (DescriptorBean[])((DescriptorBean[])beans.toArray(new DescriptorBean[0]));
   }

   private String[] getProps(DescriptorBean bean) {
      List pList = new ArrayList();
      PropertyDescriptor[] props = this.getDescriptorHelper().getPropertyDescriptors(bean);

      for(int i = 0; i < props.length; ++i) {
         pList.add(props[i].getName());
      }

      return (String[])((String[])pList.toArray(new String[0]));
   }

   public void export(int type) throws IllegalArgumentException {
      if (!this.isSchemaBased()) {
         SPIDeployerLogger.logDTDDDExport(this.getDescriptorSupportURI(), this.getAppName());
      } else if (this.dc != null) {
         DescriptorHelper helper = this.getDescriptorHelper();
         DeploymentPlanBean plan = this.dc.getPlan();
         ModuleDescriptorBean mod = plan.findModuleDescriptor(this.getAppName(), this.getDescriptorSupportURI());
         DescriptorBean[] beans = null;
         String[] props = null;
         int i;
         DescriptorBean bean;
         switch (type) {
            case 0:
               beans = helper.findDependencies(this.getDescriptorBean().getDescriptor());

               for(i = 0; i < beans.length; ++i) {
                  bean = beans[i];
                  DescriptorBean parent = bean.getParentBean();
                  boolean isSet = true;
                  PropertyDescriptor[] propertyDescriptors = helper.getPropertyDescriptors(parent);

                  for(int j = 0; j < propertyDescriptors.length; ++j) {
                     if (propertyDescriptors[j].getPropertyType().isInstance(bean) && !parent.isSet(propertyDescriptors[j].getName())) {
                        isSet = false;
                        break;
                     }
                  }

                  props = helper.findDependencies(bean);
                  if (isSet) {
                     this.createVariable(props, plan, mod, bean);
                  }
               }

               return;
            case 1:
               beans = helper.findDeclarations(this.getDescriptorBean().getDescriptor());

               for(i = 0; i < beans.length; ++i) {
                  bean = beans[i];
                  props = helper.findDeclarations(bean);
                  this.createVariable(props, plan, mod, bean);
               }

               return;
            case 2:
               beans = helper.findConfigurables(this.getDescriptorBean().getDescriptor());

               for(i = 0; i < beans.length; ++i) {
                  bean = beans[i];
                  props = helper.findConfigurables(bean);
                  this.createVariable(props, plan, mod, bean);
               }

               return;
            case 3:
               beans = helper.findChangables(this.getDescriptorBean().getDescriptor());

               for(i = 0; i < beans.length; ++i) {
                  bean = beans[i];
                  props = helper.findChangables(bean);
                  this.createVariable(props, plan, mod, bean);
               }

               return;
            case 4:
               beans = helper.findDynamics(this.getDescriptorBean().getDescriptor());

               for(i = 0; i < beans.length; ++i) {
                  bean = beans[i];
                  props = helper.findDynamics(bean);
                  this.createVariable(props, plan, mod, bean);
               }

               return;
            case 5:
               beans = this.findBeans(this.getDescriptorBean());

               for(i = 0; i < beans.length; ++i) {
                  bean = beans[i];
                  props = this.getProps(bean);
                  this.createVariable(props, plan, mod, bean);
               }

               return;
            default:
               throw new IllegalArgumentException(SPIDeployerLogger.invalidExport(type));
         }
      }
   }

   private void createVariable(String[] props, DeploymentPlanBean plan, ModuleDescriptorBean mod, DescriptorBean bean) {
      for(int j = 0; j < props.length; ++j) {
         String prop = props[j];
         VariableBean var = null;

         try {
            var = plan.findVariable(mod, bean, prop);
            if (debug && var != null) {
               Debug.say("Variable on Plan is: " + var.getName() + " = " + var.getValue());
            }

            if (var == null) {
               var = plan.findOrCreateVariable(mod, bean, prop, this.getPlanBasedDBean());
               var.setValue((String)null);
               if (debug) {
                  Debug.say("Created variable on plan: " + var.getName() + " = " + var.getValue());
               }
            }
         } catch (IllegalArgumentException var9) {
         }
      }

   }

   public void export(DescriptorBean bean, String[] props) throws IllegalArgumentException {
      ConfigHelperLowLevel.checkParam("DescriptorBean", bean);
      DescriptorHelper helper = this.getDescriptorHelper();
      DeploymentPlanBean plan = this.dc.getPlan();
      ModuleDescriptorBean mod = plan.findModuleDescriptor(this.getAppName(), this.getDescriptorSupportURI());
      DescriptorBean[] beans = helper.findChangables(this.getDescriptorBean().getDescriptor());

      for(int i = 0; i < beans.length; ++i) {
         DescriptorBean descriptorBean = beans[i];
         if (descriptorBean.equals(bean)) {
            for(int j = 0; j < props.length; ++j) {
               String p = props[j];
               if (!helper.isChangable(bean, p)) {
                  throw new IllegalArgumentException(SPIDeployerLogger.notChangable(p));
               }
            }

            this.createVariable(props, plan, mod, bean);
            return;
         }
      }

      throw new IllegalArgumentException(SPIDeployerLogger.noSuchBean(bean.toString()));
   }

   private String getDescriptorSupportURI() {
      return this.isDDBeanRoot ? this.getDescriptorSupport().getBaseURI() : this.getDescriptorSupport().getConfigURI();
   }
}
