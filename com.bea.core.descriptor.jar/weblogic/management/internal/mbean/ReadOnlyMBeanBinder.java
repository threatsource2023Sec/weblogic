package weblogic.management.internal.mbean;

import java.util.ArrayList;
import java.util.List;
import weblogic.descriptor.DescriptorBean;
import weblogic.utils.codegen.AttributeBinder;
import weblogic.utils.codegen.AttributeBinderBase;

public class ReadOnlyMBeanBinder extends AttributeBinderBase {
   private DescriptorBean bean;
   private List deprecation_info = new ArrayList();
   private static String[] IGNORE_LIST = new String[]{"NetworkAccessPoints", "ExpectedToRun"};
   private static final String LINK_PREFIX = "{@link";
   private static final String LINK_POSTFIX = "}";

   protected ReadOnlyMBeanBinder(DescriptorBean bean) {
      this.bean = bean;
   }

   public DescriptorBean getBean() {
      return this.bean;
   }

   protected boolean isInstance(Class clazz, String elementName) {
      String className = null;
      if (elementName.indexOf(".") != -1) {
         className = elementName + "MBean";
      } else {
         className = clazz.getPackage().getName() + '.' + elementName + "MBean";
      }

      try {
         ClassLoader ldr = Thread.currentThread().getContextClassLoader();
         Class elementClass = null;
         if (ldr != null) {
            elementClass = ldr.loadClass(className);
         } else {
            elementClass = Class.forName(className);
         }

         return clazz.isAssignableFrom(elementClass);
      } catch (ClassNotFoundException var6) {
         return false;
      }
   }

   public AttributeBinder bindAttribute(String name, Object o) {
      if (o instanceof AttributeBinder) {
         return super.bindAttribute(name, o);
      } else if (name.equals("Parent")) {
         return this;
      } else {
         String val = o == null ? null : o.toString();
         boolean log_warning = true;

         for(int i = 0; i < IGNORE_LIST.length; ++i) {
            if (IGNORE_LIST[i].equals(name)) {
               log_warning = false;
               break;
            }
         }

         if (log_warning) {
            System.out.println("Unrecognized attribute: Name=" + name + " Value=" + val);
         }

         return this;
      }
   }

   protected void handleDeprecatedProperty(String name, String txt) {
      String type = this.getType();
      String version = "'unknown'";
      String message = "Check documentation for more information";
      if (txt != null && txt.trim().length() > 0) {
         txt = txt.trim();
         boolean hasVersion = Character.isDigit(txt.charAt(0));
         if (hasVersion) {
            int idx = txt.indexOf(" ");
            if (idx < 0) {
               version = txt;
            } else {
               version = txt.substring(0, idx);
               message = this.fixDeprecationMessage(txt.substring(idx));
            }
         } else {
            message = this.fixDeprecationMessage(txt);
         }
      }

      this.logDeprecation(new DeprecationInfo(name, type, version, message));
   }

   private String fixDeprecationMessage(String msg) {
      for(int start = msg.indexOf("{@link"); start >= 0; start = msg.indexOf("{@link")) {
         StringBuffer sb = new StringBuffer(msg);
         int end = sb.indexOf("}", start);
         if (end >= 0) {
            sb.deleteCharAt(end);
         }

         sb.delete(start, start + "{@link".length());
         msg = sb.toString();
      }

      return msg;
   }

   private String getType() {
      if (this.bean == null) {
         return "'unknown'";
      } else {
         String className = this.bean.getClass().getName();
         String type = className.substring(className.lastIndexOf(".") + 1);
         int idx = type.indexOf("MBeanImpl");
         if (idx >= 0) {
            type = type.substring(0, idx);
         }

         return type;
      }
   }

   private void logDeprecation(DeprecationInfo info) {
      this.deprecation_info.add(info);
   }

   public List getDeprecationInfo() {
      return this.deprecation_info;
   }

   public class DeprecationInfo {
      String name;
      String type;
      String version;
      String message;

      public DeprecationInfo(String n, String t, String v, String m) {
         this.name = n;
         this.type = t;
         this.version = v;
         this.message = m;
      }

      public String getName() {
         return this.name;
      }

      public String getType() {
         return this.type;
      }

      public String getVersion() {
         return this.version;
      }

      public String getMessage() {
         return this.message;
      }

      public String toString() {
         return "DeprecationInfo{" + this.name + "," + this.type + "," + this.version + "," + this.message + "}";
      }
   }
}
