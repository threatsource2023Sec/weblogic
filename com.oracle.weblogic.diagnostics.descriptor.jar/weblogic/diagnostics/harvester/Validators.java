package weblogic.diagnostics.harvester;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import weblogic.diagnostics.descriptor.WLDFHarvestedTypeBean;
import weblogic.kernel.KernelStatus;
import weblogic.utils.AssertionError;

public class Validators {
   private static final String validatorClassName = "weblogic.diagnostics.harvester.internal.Validators";
   private static final Class validatorClass = getClass("weblogic.diagnostics.harvester.internal.Validators");
   private static Method beanValidator = getDeclaredMethod("validateHarvestedTypeBean", new Class[]{WLDFHarvestedTypeBean.class});
   private static Method typeValidator = getDeclaredMethod("validateConfiguredType", new Class[]{String.class});
   private static Method attributeValidator = getDeclaredMethod("validateConfiguredAttributes", new Class[]{String.class, String[].class});
   private static Method instanceValidator = getDeclaredMethod("validateConfiguredInstances", new Class[]{String[].class});

   private static Class getClass(String className) {
      try {
         return Class.forName(className);
      } catch (ClassNotFoundException var2) {
         throw new AssertionError(var2);
      }
   }

   private static Method getDeclaredMethod(String methodName, Class[] sig) {
      try {
         return validatorClass.getDeclaredMethod(methodName, sig);
      } catch (NoSuchMethodException var3) {
         throw new AssertionError(var3);
      }
   }

   public static void validateConfiguredType(String typeName) throws IllegalArgumentException {
      if (KernelStatus.isServer()) {
         try {
            typeValidator.invoke((Object)null, typeName);
         } catch (InvocationTargetException var3) {
            Throwable targetX = var3.getTargetException();
            if (targetX instanceof IllegalArgumentException) {
               throw (IllegalArgumentException)var3.getTargetException();
            } else {
               LogSupport.logUnexpectedException("Unable to validate type name: " + typeName, var3);
               if (targetX instanceof RuntimeException) {
                  throw (RuntimeException)targetX;
               } else {
                  throw new AssertionError(var3);
               }
            }
         } catch (RuntimeException var4) {
            LogSupport.logUnexpectedException("Unable to validate type name: " + typeName, var4);
            throw var4;
         } catch (Exception var5) {
            LogSupport.logUnexpectedException("Unable to validate type name: " + typeName, var5);
            throw new RuntimeException(var5);
         }
      }
   }

   public static void validateConfiguredAttribute(String typeName, String attributeName) throws IllegalArgumentException {
      if (KernelStatus.isServer()) {
         try {
            attributeValidator.invoke((Object)null, typeName, new String[]{attributeName});
         } catch (InvocationTargetException var4) {
            Throwable targetX = var4.getTargetException();
            if (targetX instanceof IllegalArgumentException) {
               throw (IllegalArgumentException)var4.getTargetException();
            } else {
               LogSupport.logUnexpectedException("Unable to validate attribute: " + attributeName + " for type: " + typeName, var4);
               if (targetX instanceof RuntimeException) {
                  throw (RuntimeException)targetX;
               } else {
                  throw new AssertionError(var4);
               }
            }
         } catch (RuntimeException var5) {
            LogSupport.logUnexpectedException("Unable to validate attribute: " + attributeName + " for type: " + typeName, var5);
            throw var5;
         } catch (Exception var6) {
            LogSupport.logUnexpectedException("Unable to validate attribute: " + attributeName + " for type: " + typeName, var6);
            throw new RuntimeException(var6);
         }
      }
   }

   public static void validateConfiguredAttributes(WLDFHarvestedTypeBean bean) throws IllegalArgumentException {
      String typeName = bean.getName();
      String[] attributes = bean.getHarvestedAttributes();
      validateConfiguredAttributes(typeName, attributes);
   }

   public static void validateConfiguredAttributes(String typeName, String[] attributes) throws IllegalArgumentException {
      if (KernelStatus.isServer()) {
         try {
            attributeValidator.invoke((Object)null, typeName, attributes);
         } catch (InvocationTargetException var4) {
            Throwable targetX = var4.getTargetException();
            if (targetX instanceof IllegalArgumentException) {
               throw (IllegalArgumentException)var4.getTargetException();
            } else {
               LogSupport.logUnexpectedException("Unable to validate attribute list for bean: " + typeName, var4);
               if (targetX instanceof RuntimeException) {
                  throw (RuntimeException)targetX;
               } else {
                  throw new AssertionError(var4);
               }
            }
         } catch (RuntimeException var5) {
            LogSupport.logUnexpectedException("Unable to validate attribute list for bean: " + typeName, var5);
            throw var5;
         } catch (Exception var6) {
            LogSupport.logUnexpectedException("Unable to validate attribute list for bean: " + typeName, var6);
            throw new RuntimeException(var6);
         }
      }
   }

   public static void validateConfiguredInstances(String[] instances) throws IllegalArgumentException {
      if (KernelStatus.isServer()) {
         try {
            instanceValidator.invoke((Object)null, instances);
         } catch (InvocationTargetException var3) {
            Throwable targetX = var3.getTargetException();
            if (targetX instanceof IllegalArgumentException) {
               throw (IllegalArgumentException)var3.getTargetException();
            } else {
               LogSupport.logUnexpectedException("Unable to validate instance list for bean: ", var3);
               if (targetX instanceof RuntimeException) {
                  throw (RuntimeException)targetX;
               } else {
                  throw new AssertionError(var3);
               }
            }
         } catch (RuntimeException var4) {
            LogSupport.logUnexpectedException("Unable to validate instance list for bean: ", var4);
            throw var4;
         } catch (Exception var5) {
            LogSupport.logUnexpectedException("Unable to validate instance list for bean: ", var5);
            throw new RuntimeException(var5);
         }
      }
   }

   public static void validateHarvestedTypeBean(WLDFHarvestedTypeBean bean) throws IllegalArgumentException {
      if (KernelStatus.isServer()) {
         try {
            beanValidator.invoke((Object)null, bean);
         } catch (InvocationTargetException var3) {
            Throwable targetX = var3.getTargetException();
            if (targetX instanceof IllegalArgumentException) {
               throw (IllegalArgumentException)var3.getTargetException();
            } else {
               LogSupport.logUnexpectedException("Unable to validate bean: " + bean.getName(), var3);
               if (targetX instanceof RuntimeException) {
                  throw (RuntimeException)targetX;
               } else {
                  throw new AssertionError(var3);
               }
            }
         } catch (RuntimeException var4) {
            LogSupport.logUnexpectedException("Unable to validate bean: " + bean.getName(), var4);
            throw var4;
         } catch (Exception var5) {
            LogSupport.logUnexpectedException("Unable to validate bean: " + bean.getName(), var5);
            throw new RuntimeException(var5);
         }
      }
   }
}
