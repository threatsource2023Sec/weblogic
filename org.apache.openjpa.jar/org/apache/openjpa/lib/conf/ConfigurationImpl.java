package org.apache.openjpa.lib.conf;

import java.awt.Image;
import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyDescriptor;
import java.io.Externalizable;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.TreeSet;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.log.LogFactory;
import org.apache.openjpa.lib.log.LogFactoryImpl;
import org.apache.openjpa.lib.log.NoneLogFactory;
import org.apache.openjpa.lib.util.Closeable;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.MultiClassLoader;
import org.apache.openjpa.lib.util.ParseException;
import org.apache.openjpa.lib.util.Services;
import org.apache.openjpa.lib.util.StringDistance;
import serp.util.Strings;

public class ConfigurationImpl implements Configuration, Externalizable, ValueListener {
   private static final String SEP = J2DoPrivHelper.getLineSeparator();
   private static final Localizer _loc = Localizer.forPackage(ConfigurationImpl.class);
   public ObjectValue logFactoryPlugin;
   public StringValue id;
   private String _product;
   private int _readOnlyState;
   private Map _props;
   private boolean _globals;
   private String _auto;
   private final List _vals;
   private PropertyChangeSupport _changeSupport;
   private PropertyDescriptor[] _pds;
   private MethodDescriptor[] _mds;

   public ConfigurationImpl() {
      this(true);
   }

   public ConfigurationImpl(boolean loadGlobals) {
      this._product = null;
      this._readOnlyState = 0;
      this._props = null;
      this._globals = false;
      this._auto = null;
      this._vals = new ArrayList();
      this._changeSupport = null;
      this._pds = null;
      this._mds = null;
      this.setProductName("openjpa");
      this.logFactoryPlugin = this.addPlugin("Log", true);
      String[] aliases = new String[]{"true", LogFactoryImpl.class.getName(), "openjpa", LogFactoryImpl.class.getName(), "commons", "org.apache.openjpa.lib.log.CommonsLogFactory", "log4j", "org.apache.openjpa.lib.log.Log4JLogFactory", "none", NoneLogFactory.class.getName(), "false", NoneLogFactory.class.getName()};
      this.logFactoryPlugin.setAliases(aliases);
      this.logFactoryPlugin.setDefault(aliases[0]);
      this.logFactoryPlugin.setString(aliases[0]);
      this.logFactoryPlugin.setInstantiatingGetter("getLogFactory");
      this.id = this.addString("Id");
      if (loadGlobals) {
         this.loadGlobals();
      }

   }

   public boolean loadGlobals() {
      MultiClassLoader loader = (MultiClassLoader)AccessController.doPrivileged(J2DoPrivHelper.newMultiClassLoaderAction());
      loader.addClassLoader((ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getContextClassLoaderAction()));
      loader.addClassLoader(this.getClass().getClassLoader());
      ConfigurationProvider provider = ProductDerivations.loadGlobals(loader);
      if (provider != null) {
         provider.setInto(this);
      }

      try {
         this.fromProperties(new HashMap((Properties)AccessController.doPrivileged(J2DoPrivHelper.getPropertiesAction())));
      } catch (SecurityException var4) {
      }

      this._globals = true;
      if (provider == null) {
         Log log = this.getConfigurationLog();
         if (log.isTraceEnabled()) {
            log.trace(_loc.get("no-default-providers"));
         }

         return false;
      } else {
         return true;
      }
   }

   public String getProductName() {
      return this._product;
   }

   public void setProductName(String name) {
      this._product = name;
   }

   public LogFactory getLogFactory() {
      if (this.logFactoryPlugin.get() == null) {
         this.logFactoryPlugin.instantiate(LogFactory.class, this);
      }

      return (LogFactory)this.logFactoryPlugin.get();
   }

   public void setLogFactory(LogFactory logFactory) {
      this.logFactoryPlugin.set(logFactory);
   }

   public String getLog() {
      return this.logFactoryPlugin.getString();
   }

   public void setLog(String log) {
      this.logFactoryPlugin.setString(log);
   }

   public Log getLog(String category) {
      return this.getLogFactory().getLog(category);
   }

   public String getId() {
      return this.id.get();
   }

   public void setId(String id) {
      this.id.set(id);
   }

   public Log getConfigurationLog() {
      return this.getLog("openjpa.Runtime");
   }

   public Value[] getValues() {
      return (Value[])((Value[])this._vals.toArray(new Value[this._vals.size()]));
   }

   public Value getValue(String property) {
      if (property == null) {
         return null;
      } else {
         for(int i = this._vals.size() - 1; i >= 0; --i) {
            Value val = (Value)this._vals.get(i);
            if (val.getProperty().equals(property)) {
               return val;
            }
         }

         return null;
      }
   }

   public void setReadOnly(int newState) {
      if (newState >= this._readOnlyState) {
         this._readOnlyState = newState;
      }

   }

   public void instantiateAll() {
      StringWriter errs = null;
      PrintWriter stack = null;

      for(int i = 0; i < this._vals.size(); ++i) {
         Value val = (Value)this._vals.get(i);
         String getterName = val.getInstantiatingGetter();
         if (getterName != null) {
            Object getterTarget = this;
            if (getterName.startsWith("this.")) {
               getterName = getterName.substring("this.".length());
               getterTarget = val;
            }

            try {
               Method getter = getterTarget.getClass().getMethod(getterName, (Class[])null);
               getter.invoke(getterTarget, (Object[])null);
            } catch (Throwable var9) {
               Throwable t = var9;
               if (var9 instanceof InvocationTargetException) {
                  t = ((InvocationTargetException)var9).getTargetException();
               }

               if (errs == null) {
                  errs = new StringWriter();
                  stack = new PrintWriter(errs);
               } else {
                  errs.write(SEP);
               }

               t.printStackTrace(stack);
               stack.flush();
            }
         }
      }

      if (errs != null) {
         throw new RuntimeException(_loc.get("get-prop-errs", (Object)errs.toString()).getMessage());
      }
   }

   public boolean isReadOnly() {
      return this._readOnlyState == 2;
   }

   public void addPropertyChangeListener(PropertyChangeListener listener) {
      if (this._changeSupport == null) {
         this._changeSupport = new PropertyChangeSupport(this);
      }

      this._changeSupport.addPropertyChangeListener(listener);
   }

   public void removePropertyChangeListener(PropertyChangeListener listener) {
      if (this._changeSupport != null) {
         this._changeSupport.removePropertyChangeListener(listener);
      }

   }

   public void valueChanged(Value val) {
      if (this._changeSupport != null || this._props != null) {
         String newString = val.getString();
         if (this._changeSupport != null) {
            this._changeSupport.firePropertyChange(val.getProperty(), (Object)null, newString);
         }

         if (this._props != null) {
            if (newString == null) {
               Configurations.removeProperty(val.getProperty(), this._props);
            } else if (Configurations.containsProperty(val.getProperty(), this._props) || val.getDefault() == null || !val.getDefault().equals(newString)) {
               this.put(this._props, val, newString);
            }
         }

      }
   }

   public final void close() {
      ProductDerivations.beforeClose(this);
      this.preClose();

      for(int i = 0; i < this._vals.size(); ++i) {
         if (this._vals.get(i) instanceof Closeable) {
            try {
               ((Closeable)this._vals.get(i)).close();
            } catch (Exception var4) {
            }
         } else if (this._vals.get(i) instanceof ObjectValue) {
            ObjectValue val = (ObjectValue)this._vals.get(i);
            if (val.get() instanceof Closeable) {
               try {
                  ((Closeable)val.get()).close();
               } catch (Exception var5) {
               }
            }
         }
      }

   }

   protected void preClose() {
   }

   public BeanInfo[] getAdditionalBeanInfo() {
      return new BeanInfo[0];
   }

   public BeanDescriptor getBeanDescriptor() {
      return new BeanDescriptor(this.getClass());
   }

   public int getDefaultEventIndex() {
      return 0;
   }

   public int getDefaultPropertyIndex() {
      return 0;
   }

   public EventSetDescriptor[] getEventSetDescriptors() {
      return new EventSetDescriptor[0];
   }

   public Image getIcon(int kind) {
      return null;
   }

   public synchronized MethodDescriptor[] getMethodDescriptors() {
      if (this._mds != null) {
         return this._mds;
      } else {
         PropertyDescriptor[] pds = this.getPropertyDescriptors();
         List descs = new ArrayList();

         for(int i = 0; i < pds.length; ++i) {
            Method write = pds[i].getWriteMethod();
            Method read = pds[i].getReadMethod();
            if (read != null && write != null) {
               descs.add(new MethodDescriptor(write));
               descs.add(new MethodDescriptor(read));
            }
         }

         this._mds = (MethodDescriptor[])((MethodDescriptor[])descs.toArray(new MethodDescriptor[descs.size()]));
         return this._mds;
      }
   }

   public synchronized PropertyDescriptor[] getPropertyDescriptors() {
      if (this._pds != null) {
         return this._pds;
      } else {
         this._pds = new PropertyDescriptor[this._vals.size()];
         List failures = null;

         for(int i = 0; i < this._vals.size(); ++i) {
            Value val = (Value)this._vals.get(i);

            try {
               this._pds[i] = this.getPropertyDescriptor(val);
            } catch (MissingResourceException var5) {
               if (failures == null) {
                  failures = new ArrayList();
               }

               failures.add(val.getProperty());
            } catch (IntrospectionException var6) {
               if (failures == null) {
                  failures = new ArrayList();
               }

               failures.add(val.getProperty());
            }
         }

         if (failures != null) {
            throw new ParseException(_loc.get("invalid-property-descriptors", (Object)failures));
         } else {
            return this._pds;
         }
      }
   }

   private PropertyDescriptor getPropertyDescriptor(Value val) throws IntrospectionException {
      String prop = val.getProperty();
      prop = prop.substring(prop.lastIndexOf(46) + 1);

      PropertyDescriptor pd;
      try {
         pd = new PropertyDescriptor(Introspector.decapitalize(prop), this.getClass());
      } catch (IntrospectionException var14) {
         pd = new PropertyDescriptor(Introspector.decapitalize(prop), (Method)null, (Method)null);
      }

      pd.setDisplayName(this.findLocalized(prop + "-name", true, val.getScope()));
      pd.setShortDescription(this.findLocalized(prop + "-desc", true, val.getScope()));
      pd.setExpert("true".equals(this.findLocalized(prop + "-expert", false, val.getScope())));

      try {
         pd.setReadMethod(this.getClass().getMethod("get" + StringUtils.capitalize(prop), (Class[])null));
         pd.setWriteMethod(this.getClass().getMethod("set" + StringUtils.capitalize(prop), pd.getReadMethod().getReturnType()));
      } catch (Throwable var13) {
      }

      String type = this.findLocalized(prop + "-type", true, val.getScope());
      if (type != null) {
         pd.setValue("propertyType", type);
      }

      String cat = this.findLocalized(prop + "-cat", false, val.getScope());
      if (cat != null) {
         pd.setValue("propertyCategory", cat);
      }

      pd.setValue("xmlName", toXMLName(prop));
      String order = this.findLocalized(prop + "-displayorder", false, val.getScope());
      if (order != null) {
         pd.setValue("propertyCategoryOrder", order);
      }

      Collection allowed = new TreeSet();
      List aliases = Collections.EMPTY_LIST;
      if (val.getAliases() != null) {
         aliases = Arrays.asList(val.getAliases());

         for(int i = 0; i < aliases.size(); i += 2) {
            allowed.add(aliases.get(i));
         }
      }

      String[] vals = Strings.split(this.findLocalized(prop + "-values", false, val.getScope()), ",", 0);

      for(int i = 0; i < vals.length; ++i) {
         if (!aliases.contains(vals[i])) {
            allowed.add(vals[i]);
         }
      }

      try {
         Class intf = Class.forName(this.findLocalized(prop + "-interface", true, val.getScope()), false, this.getClass().getClassLoader());
         pd.setValue("propertyInterface", intf.getName());
         String[] impls = Services.getImplementors(intf);

         for(int i = 0; i < impls.length; ++i) {
            if (!aliases.contains(impls[i])) {
               allowed.add(impls[i]);
            }
         }
      } catch (Throwable var15) {
      }

      if (!allowed.isEmpty()) {
         pd.setValue("allowedValues", (String[])((String[])allowed.toArray(new String[allowed.size()])));
      }

      return pd;
   }

   private String findLocalized(String key, boolean fatal, Class scope) {
      Localizer loc = null;
      if (scope != null) {
         loc = Localizer.forPackage(scope);

         try {
            return loc.getFatal(key).getMessage();
         } catch (MissingResourceException var8) {
         }
      }

      Class cls = this.getClass();

      while(cls != Object.class) {
         loc = Localizer.forPackage(cls);

         try {
            return loc.getFatal(key).getMessage();
         } catch (MissingResourceException var7) {
            cls = cls.getSuperclass();
         }
      }

      if (fatal) {
         throw new MissingResourceException(key, this.getClass().getName(), key);
      } else {
         return null;
      }
   }

   public Map toProperties(boolean storeDefaults) {
      Object clone;
      if (this._props == null) {
         clone = new HashMap();
      } else if (this._props instanceof Properties) {
         clone = (Map)((Properties)this._props).clone();
      } else {
         clone = new HashMap(this._props);
      }

      if (this._props == null || storeDefaults) {
         for(int i = 0; i < this._vals.size(); ++i) {
            Value val = (Value)this._vals.get(i);
            if (this._props == null || !Configurations.containsProperty(val.getProperty(), this._props)) {
               String str = val.getString();
               if (str != null && (storeDefaults || !str.equals(val.getDefault()))) {
                  this.put((Map)clone, val, str);
               }
            }
         }

         if (this._props == null) {
            this._props = new HashMap((Map)clone);
         }
      }

      return (Map)clone;
   }

   public void fromProperties(Map map) {
      if (map != null && !map.isEmpty()) {
         if (this.isReadOnly()) {
            throw new IllegalStateException(_loc.get("read-only").getMessage());
         } else {
            if (this._globals) {
               this._props = null;
               this._globals = false;
            }

            Object map;
            if (map instanceof HashMap) {
               map = (Map)((HashMap)map).clone();
            } else if (map instanceof Properties) {
               map = (Map)((Properties)map).clone();
            } else {
               map = new LinkedHashMap(map);
            }

            Map remaining = new HashMap((Map)map);
            boolean ser = true;

            for(int i = 0; i < this._vals.size(); ++i) {
               Value val = (Value)this._vals.get(i);
               Object o = this.get((Map)map, val, true);
               if (o != null) {
                  if (o instanceof String) {
                     if (!StringUtils.equals((String)o, val.getString())) {
                        val.setString((String)o);
                     }
                  } else {
                     ser &= o instanceof Serializable;
                     val.setObject(o);
                  }

                  Configurations.removeProperty(val.getProperty(), remaining);
               }
            }

            Configurations.removeProperty("properties", remaining);
            Iterator itr = remaining.entrySet().iterator();

            while(itr.hasNext()) {
               Map.Entry entry = (Map.Entry)itr.next();
               Object key = entry.getKey();
               if (key != null) {
                  this.warnInvalidProperty((String)key);
                  ((Map)map).remove(key);
               }
            }

            if (this._props == null && ser) {
               this._props = (Map)map;
            }

         }
      }
   }

   private void put(Map map, Value val, Object o) {
      Object key = val.getLoadKey();
      if (key == null) {
         key = "openjpa." + val.getProperty();
      }

      map.put(key, o);
   }

   private Object get(Map map, Value val, boolean setLoadKey) {
      String key = ProductDerivations.getConfigurationKey(val.getProperty(), map);
      if (map.containsKey(key) && setLoadKey) {
         val.setLoadKey(key);
      }

      return map.get(key);
   }

   private void warnInvalidProperty(String propName) {
      if (this.isInvalidProperty(propName)) {
         Log log = this.getConfigurationLog();
         if (log != null && log.isWarnEnabled()) {
            String closest = StringDistance.getClosestLevenshteinDistance(propName, (Collection)this.newPropertyList(), 15);
            if (closest == null) {
               log.warn(_loc.get("invalid-property", (Object)propName));
            } else {
               log.warn(_loc.get("invalid-property-hint", propName, closest));
            }

         }
      }
   }

   private Collection newPropertyList() {
      String[] prefixes = ProductDerivations.getConfigurationPrefixes();
      List l = new ArrayList(this._vals.size() * prefixes.length);

      for(int i = 0; i < this._vals.size(); ++i) {
         for(int j = 0; j < prefixes.length; ++j) {
            l.add(prefixes[j] + "." + ((Value)this._vals.get(i)).getProperty());
         }
      }

      return l;
   }

   protected boolean isInvalidProperty(String propName) {
      String[] prefixes = ProductDerivations.getConfigurationPrefixes();

      for(int i = 0; i < prefixes.length; ++i) {
         if (propName.toLowerCase().startsWith(prefixes[i]) && propName.length() > prefixes[i].length() + 1 && propName.indexOf(46, prefixes[i].length()) == prefixes[i].length() && propName.indexOf(46, prefixes[i].length() + 1) == -1) {
            return true;
         }
      }

      return false;
   }

   public void setProperties(String resourceName) throws IOException {
      ProductDerivations.load((String)resourceName, (String)null, this.getClass().getClassLoader()).setInto(this);
      this._auto = resourceName;
   }

   public void setPropertiesFile(File file) throws IOException {
      ProductDerivations.load((File)file, (String)null, this.getClass().getClassLoader()).setInto(this);
      this._auto = file.toString();
   }

   public String getPropertiesResource() {
      return this._auto;
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (other == null) {
         return false;
      } else if (!this.getClass().equals(other.getClass())) {
         return false;
      } else {
         ConfigurationImpl conf = (ConfigurationImpl)other;
         if (this._vals.size() != conf.getValues().length) {
            return false;
         } else {
            Iterator values = this._vals.iterator();

            Value v;
            Value thatV;
            do {
               if (!values.hasNext()) {
                  return true;
               }

               v = (Value)values.next();
               thatV = conf.getValue(v.getProperty());
            } while(v.equals(thatV));

            return false;
         }
      }
   }

   public int hashCode() {
      Iterator values = this._vals.iterator();

      int hash;
      Value v;
      for(hash = 0; values.hasNext(); hash += v.hashCode()) {
         v = (Value)values.next();
      }

      return hash;
   }

   public static String toXMLName(String propName) {
      if (propName == null) {
         return null;
      } else {
         StringBuffer buf = new StringBuffer();

         for(int i = 0; i < propName.length(); ++i) {
            char c = propName.charAt(i);
            if (i != 0 && Character.isUpperCase(c) && (Character.isLowerCase(propName.charAt(i - 1)) || i > 1 && i < propName.length() - 1 && Character.isUpperCase(propName.charAt(i - 1)) && Character.isLowerCase(propName.charAt(i + 1)))) {
               buf.append('-');
            }

            if (i != 0 && (!Character.isLetter(c) && Character.isLetter(propName.charAt(i - 1)) || Character.isLetter(c) && !Character.isLetter(propName.charAt(i - 1)))) {
               buf.append('-');
            }

            buf.append(Character.toLowerCase(c));
         }

         return buf.toString();
      }
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.fromProperties((Map)in.readObject());
      this._props = (Map)in.readObject();
      this._globals = in.readBoolean();
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this.toProperties(true));
      out.writeObject(this._props);
      out.writeBoolean(this._globals);
   }

   public Object clone() {
      try {
         Constructor cons = this.getClass().getConstructor(Boolean.TYPE);
         ConfigurationImpl clone = (ConfigurationImpl)cons.newInstance(Boolean.FALSE);
         clone.fromProperties(this.toProperties(true));
         clone._props = this._props == null ? null : new HashMap(this._props);
         clone._globals = this._globals;
         return clone;
      } catch (RuntimeException var3) {
         throw var3;
      } catch (Exception var4) {
         throw new ParseException(var4);
      }
   }

   public boolean removeValue(Value val) {
      if (!this._vals.remove(val)) {
         return false;
      } else {
         val.removeListener(this);
         return true;
      }
   }

   public Value addValue(Value val) {
      this._vals.add(val);
      val.addListener(this);
      return val;
   }

   public StringValue addString(String property) {
      StringValue val = new StringValue(property);
      this.addValue(val);
      return val;
   }

   public FileValue addFile(String property) {
      FileValue val = new FileValue(property);
      this.addValue(val);
      return val;
   }

   public IntValue addInt(String property) {
      IntValue val = new IntValue(property);
      this.addValue(val);
      return val;
   }

   public DoubleValue addDouble(String property) {
      DoubleValue val = new DoubleValue(property);
      this.addValue(val);
      return val;
   }

   public BooleanValue addBoolean(String property) {
      BooleanValue val = new BooleanValue(property);
      this.addValue(val);
      val.setDefault("false");
      return val;
   }

   public StringListValue addStringList(String property) {
      StringListValue val = new StringListValue(property);
      this.addValue(val);
      return val;
   }

   public ObjectValue addObject(String property) {
      ObjectValue val = new ObjectValue(property);
      this.addValue(val);
      return val;
   }

   public PluginValue addPlugin(String property, boolean singleton) {
      PluginValue val = new PluginValue(property, singleton);
      this.addValue(val);
      return val;
   }

   public PluginListValue addPluginList(String property) {
      PluginListValue val = new PluginListValue(property);
      this.addValue(val);
      return val;
   }
}
