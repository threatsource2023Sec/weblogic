package org.apache.openjpa.lib.conf;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class PluginListValue extends ObjectValue {
   private static final String[] EMPTY = new String[0];
   private String[] _names;
   private String[] _props;

   public PluginListValue(String prop) {
      super(prop);
      this._names = EMPTY;
      this._props = EMPTY;
   }

   public String[] getClassNames() {
      return this._names;
   }

   public void setClassNames(String[] names) {
      if (names == null) {
         names = EMPTY;
      }

      this._names = names;
      this.set((Object)null, true);
      this.valueChanged();
   }

   public String[] getProperties() {
      return this._props;
   }

   public void setProperties(String[] props) {
      if (props == null) {
         props = EMPTY;
      }

      this._props = props;
      this.set((Object)null, true);
      this.valueChanged();
   }

   public Object instantiate(Class elemType, Configuration conf, boolean fatal) {
      Object[] ret;
      if (this._names.length == 0) {
         ret = (Object[])((Object[])Array.newInstance(elemType, 0));
      } else {
         ret = (Object[])((Object[])Array.newInstance(elemType, this._names.length));

         for(int i = 0; i < ret.length; ++i) {
            ret[i] = this.newInstance(this._names[i], elemType, conf, fatal);
            Configurations.configureInstance(ret[i], conf, this._props[i], this.getProperty());
         }
      }

      this.set(ret, true);
      return ret;
   }

   public String getString() {
      if (this._names.length == 0) {
         return null;
      } else {
         StringBuffer buf = new StringBuffer();

         for(int i = 0; i < this._names.length; ++i) {
            if (i > 0) {
               buf.append(", ");
            }

            buf.append(Configurations.getPlugin(this.alias(this._names[i]), i < this._props.length ? this._props[i] : null));
         }

         if (buf.length() == 0) {
            return null;
         } else {
            return buf.toString();
         }
      }
   }

   public void setString(String str) {
      if (StringUtils.isEmpty(str)) {
         str = this.getDefault();
      }

      if (StringUtils.isEmpty(str)) {
         this._names = EMPTY;
         this._props = EMPTY;
         this.set((Object)null, true);
         this.valueChanged();
      } else {
         List plugins = new ArrayList();
         StringBuffer plugin = new StringBuffer();
         boolean inParen = false;

         for(int i = 0; i < str.length(); ++i) {
            char c = str.charAt(i);
            switch (c) {
               case '(':
                  inParen = true;
                  plugin.append(c);
                  break;
               case ')':
                  inParen = false;
                  plugin.append(c);
                  break;
               case '*':
               case '+':
               default:
                  plugin.append(c);
                  break;
               case ',':
                  if (inParen) {
                     plugin.append(c);
                  } else {
                     plugins.add(plugin.toString());
                     plugin = new StringBuffer();
                  }
            }
         }

         if (plugin.length() > 0) {
            plugins.add(plugin.toString());
         }

         List names = new ArrayList();
         List props = new ArrayList();

         for(int i = 0; i < plugins.size(); ++i) {
            str = (String)plugins.get(i);
            String clsName = this.unalias(Configurations.getClassName(str));
            if (clsName != null) {
               names.add(clsName);
               props.add(Configurations.getProperties(str));
            }
         }

         this._names = (String[])((String[])names.toArray(new String[names.size()]));
         this._props = (String[])((String[])props.toArray(new String[props.size()]));
         this.set((Object)null, true);
         this.valueChanged();
      }
   }

   public Class getValueType() {
      return Object[].class;
   }

   protected void objectChanged() {
      Object[] vals = (Object[])((Object[])this.get());
      if (vals != null && vals.length != 0) {
         this._names = new String[vals.length];

         for(int i = 0; i < vals.length; ++i) {
            this._names[i] = vals[i] == null ? null : vals[i].getClass().getName();
         }
      } else {
         this._names = EMPTY;
      }

      this._props = EMPTY;
   }

   protected String getInternalString() {
      throw new IllegalStateException();
   }

   protected void setInternalString(String str) {
      throw new IllegalStateException();
   }
}
