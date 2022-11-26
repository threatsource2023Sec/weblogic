package com.bea.adaptive.mbean.typing;

import javax.management.Descriptor;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.OperationsException;
import javax.management.modelmbean.ModelMBeanInfo;

public interface MBeanCategorizerPlugins {
   String MODEL_MBEAN_TYPE_TAG = "DiagnosticTypeName";

   public static class CustomPlugin implements MBeanCategorizer.Plugin {
      static final long serialVersionUID = 1L;
      public static final String CATEGORY_NAME = "Custom-MBean";

      public boolean handles(MBeanServerConnection mbs, ObjectName mbean) {
         return true;
      }

      public String getTypeName(MBeanServerConnection mbs, ObjectName mbean) {
         if (mbs != null && mbean != null) {
            String typeName = null;

            try {
               MBeanInfo mbi = mbs.getMBeanInfo(mbean);
               if (mbs.isInstanceOf(mbean, "javax.management.modelmbean.ModelMBean") && mbi instanceof ModelMBeanInfo) {
                  ModelMBeanInfo mmbi = (ModelMBeanInfo)mbi;
                  Descriptor descr = mmbi.getMBeanDescriptor();
                  if (descr != null) {
                     typeName = (String)descr.getFieldValue("DiagnosticTypeName");
                  }
               } else {
                  typeName = mbi.getClassName();
               }

               return typeName;
            } catch (InstanceNotFoundException var7) {
               return null;
            } catch (Exception var8) {
               throw new RuntimeException(var8);
            }
         } else {
            throw new NullPointerException();
         }
      }

      public String getCategoryName() {
         return "Custom-MBean";
      }
   }

   public static class JMXPlugin extends CustomPlugin {
      static final long serialVersionUID = 1L;
      public static final String JMX_CATEGORY_NAME = "JMX-MBean";

      public boolean handles(MBeanServerConnection mbs, ObjectName mbean) {
         String typeName = this.getTypeName(mbs, mbean);
         return typeName != null && typeName.startsWith("javax.management");
      }

      public String getCategoryName(ObjectName mbean) {
         return "JMX-MBean";
      }
   }

   public static class NonWLSPlugin extends CustomPlugin {
      static final long serialVersionUID = 1L;
      public static final String NON_WLS_CATEGORY_NAME = "Non-WLS-MBean";

      public boolean handles(MBeanServerConnection mbs, ObjectName mbean) {
         try {
            return !mbs.isInstanceOf(mbean, "weblogic.management.jmx.modelmbean.WLSModelMBean");
         } catch (InstanceNotFoundException var4) {
            return false;
         } catch (Exception var5) {
            throw new RuntimeException(var5);
         }
      }

      public String getCategoryName(ObjectName mbean) {
         return "Non-WLS-MBean";
      }

      public String getCategoryName() {
         return "Non-WLS-MBean";
      }
   }

   public static class WLSPlugin implements MBeanCategorizer.Plugin {
      static final String WLSPLUGIN_MBEAN_TYPE_TAG = "interfaceclassname";
      static final long serialVersionUID = 1L;
      public static final String WLS_CATEGORY_NAME = "WLS-MBean";

      public boolean handles(MBeanServerConnection mbs, ObjectName mbean) {
         try {
            return mbs.isInstanceOf(mbean, "weblogic.management.jmx.modelmbean.WLSModelMBean");
         } catch (InstanceNotFoundException var4) {
            return false;
         } catch (Exception var5) {
            throw new RuntimeException(var5);
         }
      }

      public String getTypeName(MBeanServerConnection mbs, ObjectName mbean) {
         String type = null;

         try {
            if (mbean != null) {
               MBeanInfo mbi = mbs.getMBeanInfo(mbean);
               if (mbi != null && mbi instanceof ModelMBeanInfo) {
                  ModelMBeanInfo mmbi = (ModelMBeanInfo)mbi;
                  Descriptor d = mmbi.getMBeanDescriptor();
                  if (d != null) {
                     type = (String)d.getFieldValue("interfaceclassname");
                  }
               }
            }

            return type;
         } catch (OperationsException var7) {
            throw new RuntimeException(var7);
         } catch (Exception var8) {
            throw new RuntimeException(var8);
         }
      }

      public String getCategoryName() {
         return "WLS-MBean";
      }
   }
}
