package com.sun.faces.application;

import java.beans.PropertyEditorSupport;

public abstract class ConverterPropertyEditorBase extends PropertyEditorSupport {
   protected abstract Class getTargetClass();

   public void setAsText(String textValue) throws IllegalArgumentException {
      try {
         Object appAssociate = this.getPropertyEditorHelper();
         Class targetClass = Thread.currentThread().getContextClassLoader().loadClass(this.getTargetClass().getName());
         Object value = appAssociate.getClass().getMethod("convertToObject", Class.class, String.class).invoke(appAssociate, targetClass, textValue);
         if (value != null) {
            this.setValue(value);
         }

      } catch (RuntimeException var5) {
         throw var5;
      } catch (Exception var6) {
         throw new IllegalStateException("Unexpected Error attempting to use this ConverterPropertyEditor.  You're deployment environment may not supportConverterPropertyEditors.  Try restarting your server or disabling com.sun.faces.registerConverterPropertyEditors", var6);
      }
   }

   private Object getPropertyEditorHelper() throws Exception {
      Class facesContextClass = Thread.currentThread().getContextClassLoader().loadClass("com.sun.faces.application.ApplicationAssociate");
      Object appAssociate = facesContextClass.getMethod("getCurrentInstance").invoke((Object)null);
      if (appAssociate == null) {
         throw new IllegalStateException("Unable to find Deployed JSF Application.  You're deployment environment may not supportConverterPropertyEditors.  Try restarting your server or turn off com.sun.faces.registerConverterPropertyEditors");
      } else {
         return appAssociate.getClass().getMethod("getPropertyEditorHelper").invoke(appAssociate);
      }
   }

   public String getAsText() {
      try {
         Object application = this.getPropertyEditorHelper();
         Class targetClass = Thread.currentThread().getContextClassLoader().loadClass(this.getTargetClass().getName());
         String text = (String)application.getClass().getMethod("convertToString", Class.class, Object.class).invoke(application, targetClass, this.getValue());
         return text != null ? text : super.getAsText();
      } catch (Exception var4) {
         return super.getAsText();
      }
   }
}
