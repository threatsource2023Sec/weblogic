package weblogic.descriptor.internal;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyChangeListener;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.XMLHelper;

public final class DescriptorHelper {
   private Map infos = new WeakHashMap();
   public static final String KEY = "key";
   public static final String DEPENDENCY = "dependency";
   public static final String DECLARATION = "declaration";
   public static final String CONFIGURABLE = "configurable";
   public static final String DYNAMIC = "dynamic";
   public static final String TRANSIENT = "transient";
   public static final String KEY_COMPONENT = "keyComponent";

   public static DescriptorHelper getInstance() {
      return new DescriptorHelper();
   }

   private DescriptorHelper() {
   }

   public boolean isDependency(DescriptorBean bean, String prop) {
      return this._introspect(bean, prop, "dependency");
   }

   public boolean isDeclaration(DescriptorBean bean, String prop) {
      return this._introspect(bean, prop, "declaration");
   }

   public boolean isConfigurable(DescriptorBean bean, String prop) {
      return this._introspect(bean, prop, "configurable");
   }

   public boolean isDynamic(DescriptorBean bean, String prop) {
      return this._introspect(bean, prop, "dynamic");
   }

   public boolean isChangable(DescriptorBean bean, String prop) {
      return !this.isTransient(bean, prop);
   }

   public boolean isKey(DescriptorBean bean, String prop) {
      return this._introspect(bean, prop, "key");
   }

   public boolean isKeyComponent(DescriptorBean bean, String prop) {
      return this._introspect(bean, prop, "keyComponent");
   }

   public boolean isTransient(DescriptorBean bean, String prop) {
      return this._introspect(bean, prop, "transient");
   }

   public String findKey(DescriptorBean bean) {
      String[] props = this._introspect(bean, "key");
      return props.length == 0 ? null : props[0];
   }

   public String[] findDependencies(DescriptorBean bean) {
      return this._introspect(bean, "dependency");
   }

   public String[] findDeclarations(DescriptorBean bean) {
      return this._introspect(bean, "declaration");
   }

   public String[] findConfigurables(DescriptorBean bean) {
      return this._introspect(bean, "configurable");
   }

   public String[] findDynamics(DescriptorBean bean) {
      return this._introspect(bean, "dynamic");
   }

   public String[] findChangables(DescriptorBean bean) {
      Set chgs = new HashSet();
      chgs.addAll(Arrays.asList(this.findDependencies(bean)));
      chgs.addAll(Arrays.asList(this.findDeclarations(bean)));
      chgs.addAll(Arrays.asList(this.findConfigurables(bean)));
      chgs.addAll(Arrays.asList(this.findDynamics(bean)));
      return (String[])((String[])chgs.toArray(new String[0]));
   }

   public DescriptorBean[] findDynamics(Descriptor desc) {
      return this._findBeans(desc.getRootBean(), "dynamic");
   }

   public DescriptorBean[] findDependencies(Descriptor desc) {
      return this._findBeans(desc.getRootBean(), "dependency");
   }

   public DescriptorBean[] findConfigurables(Descriptor desc) {
      return this._findBeans(desc.getRootBean(), "configurable");
   }

   public DescriptorBean[] findDeclarations(Descriptor desc) {
      return this._findBeans(desc.getRootBean(), "declaration");
   }

   public DescriptorBean[] findChangables(Descriptor desc) {
      Set chgs = new HashSet();
      chgs.addAll(Arrays.asList(this.findDependencies(desc)));
      chgs.addAll(Arrays.asList(this.findDeclarations(desc)));
      chgs.addAll(Arrays.asList(this.findConfigurables(desc)));
      chgs.addAll(Arrays.asList(this.findDynamics(desc)));
      return (DescriptorBean[])((DescriptorBean[])chgs.toArray(new DescriptorBean[0]));
   }

   public Iterator getChildren(DescriptorBean bean) {
      return ((AbstractDescriptorBean)bean)._getHelper().getChildren();
   }

   public void addPropertyChangeListener(DescriptorBean bean, PropertyChangeListener listener) {
      ((AbstractDescriptorBean)bean).addPropertyChangeListener(listener);
   }

   public void removePropertyChangeListener(DescriptorBean bean, PropertyChangeListener listener) {
      ((AbstractDescriptorBean)bean).removePropertyChangeListener(listener);
   }

   public PropertyDescriptor getPropertyDescriptor(DescriptorBean bean, String prop) {
      PropertyDescriptor[] props = this._getPropertyDescriptors(bean);

      for(int i = 0; i < props.length; ++i) {
         PropertyDescriptor propertyDescriptor = props[i];
         if (propertyDescriptor.getName().equalsIgnoreCase(prop)) {
            return propertyDescriptor;
         }
      }

      return null;
   }

   private DescriptorBean[] _findBeans(DescriptorBean bean, String attr) {
      List beans = new ArrayList();
      if (this._introspect(bean, attr).length > 0) {
         beans.add(bean);
      }

      Iterator bi = this.getChildren(bean);

      while(bi.hasNext()) {
         DescriptorBean b = (DescriptorBean)bi.next();
         beans.addAll(Arrays.asList(this._findBeans(b, attr)));
      }

      return (DescriptorBean[])((DescriptorBean[])beans.toArray(new DescriptorBean[0]));
   }

   private boolean _introspect(DescriptorBean bean, String prop, String attr) {
      PropertyDescriptor[] props = this._getPropertyDescriptors(bean);

      for(int i = 0; i < props.length; ++i) {
         if (props[i].getName().equalsIgnoreCase(prop) && Boolean.TRUE.equals(props[i].getValue(attr))) {
            return true;
         }
      }

      return false;
   }

   private String[] _introspect(DescriptorBean bean, String attr) {
      List pList = new ArrayList();
      PropertyDescriptor[] props = this._getPropertyDescriptors(bean);

      for(int i = 0; i < props.length; ++i) {
         if (Boolean.TRUE.equals(props[i].getValue(attr))) {
            pList.add(props[i].getName());
         }
      }

      return (String[])((String[])pList.toArray(new String[0]));
   }

   public PropertyDescriptor[] getPropertyDescriptors(DescriptorBean bean) {
      return this._getPropertyDescriptors(bean);
   }

   private PropertyDescriptor[] _getPropertyDescriptors(DescriptorBean bean) {
      BeanInfo info = (BeanInfo)this.infos.get(bean);
      if (info == null) {
         try {
            info = Introspector.getBeanInfo(bean.getClass(), 1);
            this.infos.put(bean, info);
         } catch (IntrospectionException var4) {
            return new PropertyDescriptor[0];
         }
      }

      return info.getPropertyDescriptors();
   }

   public String buildXpath(DescriptorBean bean, String prop) {
      return this.buildXpath(bean, prop, (Object)null, false);
   }

   public String buildXpath(DescriptorBean bean, String prop, Object oldKeyValue, boolean oldKeyPresent) {
      String ename = this.getElementName(bean, prop);
      AbstractDescriptorBean abean = (AbstractDescriptorBean)bean;
      String xpath = "/" + ename;

      for(Object oldValue = oldKeyValue; abean.getParentBean() != null; oldValue = null) {
         ename = this.getElementName((DescriptorBean)abean);
         String keyQualifier = this.getKeyQualifier(abean, prop, oldValue, oldKeyPresent);
         if (oldKeyPresent && keyQualifier != null && keyQualifier.length() > 0 && keyQualifier.charAt(0) != '/') {
            xpath = "/" + ename + "/" + keyQualifier + xpath;
         } else {
            xpath = "/" + ename + keyQualifier + xpath;
         }

         abean = (AbstractDescriptorBean)abean.getParentBean();
      }

      xpath = "/" + this.getElementName((DescriptorBean)abean) + xpath;
      return xpath;
   }

   public String buildKeyXpath(DescriptorBean bean) {
      String xpath = "";

      AbstractDescriptorBean abean;
      for(abean = (AbstractDescriptorBean)bean; abean.getParentBean() != null; abean = (AbstractDescriptorBean)abean.getParentBean()) {
         String ename = this.getElementName((DescriptorBean)abean);
         xpath = "/" + ename + this.getKeyQualifier(abean, (String)null, (Object)null, false) + xpath;
      }

      xpath = "/" + this.getElementName((DescriptorBean)abean) + xpath;
      return xpath;
   }

   private String getKeyQualifier(AbstractDescriptorBean abean, String prop, Object oldKeyValue, boolean oldKeyPresent) {
      Object kv = abean._getKey();
      if (kv != null) {
         if (kv instanceof String) {
            String kn = this.findKey(abean);
            if (kn != null) {
               String ename = this.getElementName(abean, kn);
               String s = "/";
               Object keyValue = kv;
               if (oldKeyPresent && oldKeyValue != null && kn.equals(prop)) {
                  keyValue = oldKeyValue;
               }

               if (this.isAttribute(abean, ename)) {
                  s = "";
               }

               return s + "[" + ename + "=\"" + keyValue + "\"]";
            }
         } else if (kv instanceof CompoundKey) {
            return "/" + this.getCompoundKeyQualifier(abean, prop, oldKeyValue, oldKeyPresent);
         }
      }

      return "";
   }

   private String getCompoundKeyQualifier(AbstractDescriptorBean abean, String prop, Object oldKeyValue, boolean oldKeyPresent) {
      String[] kns = this.findKeyComponents(abean);
      if (kns == null) {
         return "";
      } else {
         String kq = "[";
         String sep = "";

         for(int i = 0; i < kns.length; ++i) {
            String kn = kns[i];
            String kv = this.getKeyValue(abean, kn);
            if (oldKeyPresent && kn.equals(prop)) {
               if (oldKeyValue == null) {
                  continue;
               }

               kv = (String)oldKeyValue;
            }

            kq = kq + sep + this.getElementName(abean, kn) + "=\"" + kv + "\"";
            sep = ",";
         }

         return kq + "]";
      }
   }

   public String[] findKeyComponents(DescriptorBean abean) {
      String[] props = this._introspect(abean, "keyComponent");
      return props.length == 0 ? null : props;
   }

   private boolean isAttribute(DescriptorBean bean, String propertyName) {
      SchemaHelper helper = ((AbstractDescriptorBean)bean)._getSchemaHelper2();
      return helper.isAttribute(helper.getPropertyIndex(propertyName));
   }

   public String getKeyValue(DescriptorBean bean, String key) {
      PropertyDescriptor[] pds = this._getPropertyDescriptors(bean);
      if (pds == null) {
         return null;
      } else {
         for(int i = 0; i < pds.length; ++i) {
            PropertyDescriptor pd = pds[i];
            if (pd.getName().equalsIgnoreCase(key)) {
               Method m = pd.getReadMethod();

               try {
                  return (String)m.invoke(bean);
               } catch (IllegalAccessException var8) {
                  assert false : "invoke error accessing key, " + key;
               } catch (InvocationTargetException var9) {
                  assert false : "invoke error accessing key, " + key;
               }
            }
         }

         return null;
      }
   }

   public String getElementName(DescriptorBean bean, String prop) {
      int ndx = ((AbstractDescriptorBean)bean)._getHelper().getPropertyIndex(prop);
      if (ndx == -1) {
         ndx = ((AbstractDescriptorBean)bean)._getHelper().getPropertyIndex(prop + "s");
      }

      if (ndx == -1) {
         ndx = ((AbstractDescriptorBean)bean)._getHelper().getPropertyIndex(prop.substring(0, prop.length() - 2) + "ies");
      }

      if (ndx == -1) {
         ndx = ((AbstractDescriptorBean)bean)._getHelper().getPropertyIndex(prop + "es");
      }

      if (ndx == -1) {
         ndx = ((AbstractDescriptorBean)bean)._getHelper().getPropertyIndex(prop + "Array");
      }

      if (ndx != -1) {
         String ename = ((AbstractDescriptorBean)bean)._getElementName(ndx);
         return ename;
      } else {
         return this.getElementName(prop);
      }
   }

   public String getElementName(DescriptorBean bean) {
      String b = ((AbstractDescriptorBean)bean)._getBeanElementName();
      if (b == null) {
         String c = bean.getClass().getName();
         c = c.substring(c.lastIndexOf(".") + 1, c.length() - 8);
         b = this.getElementName(c);
      }

      return b;
   }

   public String getElementName(String prop) {
      String ename = this.checkKnownCulprits(prop);
      if (ename == null) {
         ename = XMLHelper.toElementName(prop);
      }

      return ename;
   }

   private String checkKnownCulprits(String prop) {
      if ("JMS".equals(prop)) {
         return "weblogic-jms";
      } else if ("JDBCDataSource".equals(prop)) {
         return "jdbc-data-source";
      } else {
         return "WLDFInstrumentation".equals(prop) ? "instrumentation" : null;
      }
   }
}
