package weblogic.management.provider.custom;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import javax.management.AttributeNotFoundException;
import javax.management.MBeanException;
import javax.management.Notification;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.ConfigurationException;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.mbeans.custom.WebLogic;

public class ConfigurationMBeanCustomizer extends WebLogic implements ConfigurationMBeanCustomized {
   private ConfigurationMBeanCustomized _base;
   private HashSet tags;
   private static HashMap taggedMbeanMap;
   private static final char[] invalid_chars = new char[]{'!', '"', '#', '$', '%', '&', '(', ')', '*', '+', ',', '.', '/', ':', ';', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '`', '{', '|', '}', '~'};

   public ConfigurationMBeanCustomizer(ConfigurationMBeanCustomized base) {
      super((WebLogicMBean)base);
      this._base = base;
   }

   public ConfigurationMBean getMbean() {
      return this._base.getMbean();
   }

   public void putValue(String name, Object v) {
      this._base.putValue(name, v);
   }

   public void putValueNotify(String name, Object v) {
      this._base.putValueNotify(name, v);
   }

   public Object getValue(String name) {
      return this._base.getValue(name);
   }

   public boolean isAdmin() {
      return this._base.isAdmin();
   }

   public boolean isConfig() {
      return this._base.isConfig();
   }

   public boolean isEdit() {
      return this._base.isEdit();
   }

   public boolean isRuntime() {
      return this._base.isRuntime();
   }

   public Object clone(ConfigurationMBeanCustomized customized) {
      try {
         ConfigurationMBeanCustomizer result = (ConfigurationMBeanCustomizer)super.clone();
         result._base = customized;
         return result;
      } catch (CloneNotSupportedException var4) {
         throw new AssertionError(var4);
      }
   }

   public void sendNotification(Notification notification) {
      this._base.sendNotification(notification);
   }

   public void touch() throws ConfigurationException {
      this._base.touch();
   }

   public void freezeCurrentValue(String attributeName) throws AttributeNotFoundException, MBeanException {
      ((AbstractDescriptorBean)this._base).markSet(attributeName);
   }

   public void restoreDefaultValue(String attributeName) throws AttributeNotFoundException {
      ((AbstractDescriptorBean)this._base).unSet(attributeName);
   }

   public boolean isDynamicallyCreated() {
      ConfigurationMBean bean = this.getMbean();
      if (!(bean instanceof AbstractDescriptorBean)) {
         return false;
      } else {
         AbstractDescriptorBean descBean = (AbstractDescriptorBean)bean;
         return descBean.getInstanceId() != 0 || descBean._isTransient();
      }
   }

   public String[] getTags() {
      if (this.tags == null) {
         this.tags = new HashSet();
      }

      return (String[])((String[])this.tags.toArray(new String[this.tags.size()]));
   }

   public void setTags(String[] tagArray) {
      this.validateTagArray(tagArray);
      if (taggedMbeanMap == null) {
         taggedMbeanMap = new HashMap();
      }

      if (tagArray != null && this._base != null) {
         try {
            WebLogicMBean wmbean = (WebLogicMBean)this._base;
            String key = wmbean.getName() + ":" + wmbean.getType() + ":" + wmbean.getParent();
            if (tagArray.length == 0) {
               taggedMbeanMap.remove(key);
            } else {
               taggedMbeanMap.put(key, wmbean);
            }
         } catch (NullPointerException var4) {
         }
      }

      this.tags = new HashSet(Arrays.asList(tagArray));
   }

   private void validateTag(String tag) throws IllegalArgumentException {
      if (tag != null && tag.length() != 0) {
         char[] c = tag.toCharArray();

         for(int i = 0; i < c.length; ++i) {
            if (Arrays.binarySearch(invalid_chars, c[i]) >= 0) {
               throw new IllegalArgumentException("Tag '" + tag + "' contains illegal character '" + c[i] + "'");
            }
         }

      } else {
         throw new IllegalArgumentException("Tag may not be null or empty string");
      }
   }

   private void validateTagArray(String[] tags) throws IllegalArgumentException {
      String[] var2 = tags;
      int var3 = tags.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String tag = var2[var4];
         this.validateTag(tag);
      }

   }

   protected static HashMap getTaggedMBeanMap() {
      return taggedMbeanMap;
   }

   static {
      Arrays.sort(invalid_chars);
   }
}
