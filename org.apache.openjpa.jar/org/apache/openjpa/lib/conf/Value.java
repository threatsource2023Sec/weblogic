package org.apache.openjpa.lib.conf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.ParseException;

public abstract class Value implements Cloneable {
   private static final String[] EMPTY_ALIASES = new String[0];
   private static final Localizer s_loc = Localizer.forPackage(Value.class);
   private String prop = null;
   private String loadKey = null;
   private String def = null;
   private String[] aliases = null;
   private String getter = null;
   private List listeners = null;
   private boolean aliasListComprehensive = false;
   private Class scope = null;
   private boolean isDynamic = false;
   private String originalValue = null;

   public Value() {
   }

   public Value(String prop) {
      this.setProperty(prop);
   }

   public String getProperty() {
      return this.prop;
   }

   public void setProperty(String prop) {
      this.prop = prop;
   }

   public String getLoadKey() {
      return this.loadKey;
   }

   public void setLoadKey(String loadKey) {
      this.loadKey = loadKey;
   }

   public String[] getAliases() {
      return this.aliases == null ? EMPTY_ALIASES : this.aliases;
   }

   public void setAliases(String[] aliases) {
      String[] aStrings = new String[aliases.length];
      System.arraycopy(aliases, 0, aStrings, 0, aStrings.length);
      this.aliases = aStrings;
   }

   public void setAlias(String key, String value) {
      this.aliases = this.setAlias(key, value, this.aliases);
   }

   protected String[] setAlias(String key, String value, String[] aliases) {
      if (aliases == null) {
         aliases = EMPTY_ALIASES;
      }

      for(int i = 0; i < aliases.length; i += 2) {
         if (key.equals(aliases[i])) {
            aliases[i + 1] = value;
            return aliases;
         }
      }

      String[] newAliases = new String[aliases.length + 2];
      System.arraycopy(aliases, 0, newAliases, 2, aliases.length);
      newAliases[0] = key;
      newAliases[1] = value;
      return newAliases;
   }

   public boolean isAliasListComprehensive() {
      return this.aliasListComprehensive;
   }

   public void setAliasListComprehensive(boolean aliasListIsComprehensive) {
      this.aliasListComprehensive = aliasListIsComprehensive;
   }

   public String alias(String str) {
      return this.alias(str, this.aliases, false);
   }

   protected String alias(String str, String[] aliases, boolean nullNotFound) {
      if (str != null) {
         str = str.trim();
      }

      if (aliases != null && aliases.length != 0) {
         boolean empty = str != null && str.length() == 0;

         for(int i = 1; i < aliases.length; i += 2) {
            if (StringUtils.equals(str, aliases[i]) || empty && aliases[i] == null) {
               return aliases[i - 1];
            }
         }

         return nullNotFound ? null : str;
      } else {
         return nullNotFound ? null : str;
      }
   }

   public String unalias(String str) {
      return this.unalias(str, this.aliases, false);
   }

   protected String unalias(String str, String[] aliases, boolean nullNotFound) {
      if (str != null) {
         str = str.trim();
      }

      boolean empty = str != null && str.length() == 0;
      if (str == null || empty && this.def != null) {
         str = this.def;
      }

      if (aliases != null) {
         for(int i = 0; i < aliases.length; i += 2) {
            if (StringUtils.equals(str, aliases[i]) || StringUtils.equals(str, aliases[i + 1]) || empty && aliases[i] == null) {
               return aliases[i + 1];
            }
         }
      }

      if (this.isAliasListComprehensive() && aliases != null) {
         throw new ParseException(s_loc.get("invalid-enumerated-config", this.getProperty(), str, Arrays.asList(aliases)));
      } else {
         return nullNotFound ? null : str;
      }
   }

   public String getDefault() {
      return this.def;
   }

   public void setDefault(String def) {
      this.def = def;
   }

   public String getInstantiatingGetter() {
      return this.getter;
   }

   public void setInstantiatingGetter(String getter) {
      this.getter = getter;
   }

   public Class getScope() {
      return this.scope;
   }

   public void setScope(Class cls) {
      this.scope = cls;
   }

   public String getString() {
      return this.alias(this.getInternalString());
   }

   public void setString(String val) {
      this.assertChangeable();
      String str = this.unalias(val);

      try {
         this.setInternalString(str);
         if (this.originalValue == null && val != null && !this.isDefault(val)) {
            this.originalValue = this.getString();
         }

      } catch (ParseException var4) {
         throw var4;
      } catch (RuntimeException var5) {
         throw new ParseException(this.prop + ": " + val, var5);
      }
   }

   public void setObject(Object obj) {
      if (obj == null && this.def != null) {
         this.setString((String)null);
      } else {
         try {
            this.setInternalObject(obj);
            if (this.originalValue == null && obj != null && !this.isDefault(obj)) {
               this.originalValue = this.getString();
            }
         } catch (ParseException var3) {
            throw var3;
         } catch (RuntimeException var4) {
            throw new ParseException(this.prop + ": " + obj, var4);
         }
      }

   }

   public String getOriginalValue() {
      return this.originalValue == null ? this.getDefault() : this.originalValue;
   }

   boolean isDefault(Object val) {
      return val != null && val.toString().equals(this.getDefault());
   }

   public abstract Class getValueType();

   protected abstract String getInternalString();

   protected abstract void setInternalString(String var1);

   protected abstract void setInternalObject(Object var1);

   public List getListeners() {
      return Collections.unmodifiableList(this.listeners);
   }

   public void addListener(ValueListener listener) {
      if (listener != null) {
         if (this.listeners == null) {
            this.listeners = new ArrayList();
         }

         this.listeners.add(listener);
      }
   }

   public void removeListener(ValueListener listener) {
      if (listener != null) {
         this.listeners.remove(listener);
      }
   }

   public void valueChanged() {
      if (this.listeners != null) {
         Iterator i$ = this.listeners.iterator();

         while(i$.hasNext()) {
            ValueListener listener = (ValueListener)i$.next();
            listener.valueChanged(this);
         }

      }
   }

   protected void assertChangeable() {
      if (!this.isDynamic() && this.containsReadOnlyConfigurationAsListener()) {
         throw new RuntimeException(s_loc.get("veto-change", (Object)this.getProperty()).toString());
      }
   }

   boolean containsReadOnlyConfigurationAsListener() {
      if (this.listeners == null) {
         return false;
      } else {
         Iterator i$ = this.listeners.iterator();

         ValueListener listener;
         do {
            if (!i$.hasNext()) {
               return false;
            }

            listener = (ValueListener)i$.next();
         } while(!(listener instanceof Configuration) || !((Configuration)listener).isReadOnly());

         return true;
      }
   }

   public void setDynamic(boolean flag) {
      this.isDynamic = flag;
   }

   public boolean isDynamic() {
      return this.isDynamic;
   }

   public int hashCode() {
      String str = this.isDynamic() ? this.getOriginalValue() : this.getString();
      int strHash = str == null ? 0 : str.hashCode();
      int propHash = this.prop == null ? 0 : this.prop.hashCode();
      return strHash ^ propHash;
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (!(other instanceof Value)) {
         return false;
      } else {
         Value o = (Value)other;
         String thisStr = this.isDynamic() ? this.getOriginalValue() : this.getString();
         String thatStr = this.isDynamic() ? o.getOriginalValue() : o.getString();
         return this.isDynamic() == o.isDynamic() && StringUtils.equals(this.prop, o.getProperty()) && StringUtils.equals(thisStr, thatStr);
      }
   }

   public Object clone() {
      try {
         return super.clone();
      } catch (CloneNotSupportedException var2) {
         return null;
      }
   }
}
