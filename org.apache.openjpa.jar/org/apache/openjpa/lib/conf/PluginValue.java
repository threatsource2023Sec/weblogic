package org.apache.openjpa.lib.conf;

import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.lib.util.Localizer;

public class PluginValue extends ObjectValue {
   private static final Localizer _loc = Localizer.forPackage(PluginValue.class);
   private final boolean _singleton;
   private String _name = null;
   private String _props = null;

   public PluginValue(String prop, boolean singleton) {
      super(prop);
      this._singleton = singleton;
   }

   public boolean isSingleton() {
      return this._singleton;
   }

   public String getClassName() {
      return this._name;
   }

   public void setClassName(String name) {
      this.assertChangeable();
      String oldName = this._name;
      this._name = name;
      if (!StringUtils.equals(oldName, name)) {
         if (this._singleton) {
            this.set((Object)null, true);
         }

         this.valueChanged();
      }

   }

   public String getProperties() {
      return this._props;
   }

   public void setProperties(String props) {
      String oldProps = this._props;
      this._props = props;
      if (!StringUtils.equals(oldProps, props)) {
         if (this._singleton) {
            this.set((Object)null, true);
         }

         this.valueChanged();
      }

   }

   public Object instantiate(Class type, Configuration conf, boolean fatal) {
      Object obj = this.newInstance(this._name, type, conf, fatal);
      Configurations.configureInstance(obj, conf, this._props, fatal ? this.getProperty() : null);
      if (this._singleton) {
         this.set(obj, true);
      }

      return obj;
   }

   public void set(Object obj, boolean derived) {
      if (!this._singleton) {
         throw new IllegalStateException(_loc.get("not-singleton", (Object)this.getProperty()).getMessage());
      } else {
         super.set(obj, derived);
      }
   }

   public String getString() {
      return Configurations.getPlugin(this.alias(this._name), this._props);
   }

   public void setString(String str) {
      this.assertChangeable();
      this._name = Configurations.getClassName(str);
      this._name = this.unalias(this._name);
      this._props = Configurations.getProperties(str);
      if (this._singleton) {
         this.set((Object)null, true);
      }

      this.valueChanged();
   }

   public Class getValueType() {
      return Object.class;
   }

   protected void objectChanged() {
      Object obj = this.get();
      this._name = obj == null ? this.unalias((String)null) : obj.getClass().getName();
      this._props = null;
   }

   protected String getInternalString() {
      throw new IllegalStateException();
   }

   protected void setInternalString(String str) {
      throw new IllegalStateException();
   }
}
