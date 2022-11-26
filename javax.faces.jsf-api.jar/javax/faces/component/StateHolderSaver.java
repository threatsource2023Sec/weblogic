package javax.faces.component;

import java.io.Serializable;
import javax.faces.context.FacesContext;

class StateHolderSaver implements Serializable {
   private static final long serialVersionUID = 6470180891722042701L;
   private String className = null;
   private Serializable savedState = null;

   public StateHolderSaver(FacesContext context, Object toSave) {
      this.className = toSave.getClass().getName();
      if (toSave instanceof StateHolder) {
         if (!((StateHolder)toSave).isTransient()) {
            this.savedState = (Serializable)((StateHolder)toSave).saveState(context);
         } else {
            this.className = null;
         }
      } else if (toSave instanceof Serializable) {
         this.savedState = (Serializable)toSave;
         this.className = null;
      }

   }

   public Object restore(FacesContext context) throws IllegalStateException {
      Object result = null;
      if (null == this.className && null != this.savedState) {
         return this.savedState;
      } else if (this.className == null) {
         return null;
      } else {
         Class toRestoreClass;
         try {
            toRestoreClass = loadClass(this.className, this);
         } catch (ClassNotFoundException var7) {
            throw new IllegalStateException(var7);
         }

         if (null != toRestoreClass) {
            try {
               result = toRestoreClass.newInstance();
            } catch (InstantiationException var5) {
               throw new IllegalStateException(var5);
            } catch (IllegalAccessException var6) {
               throw new IllegalStateException(var6);
            }
         }

         if (null != result && null != this.savedState && result instanceof StateHolder) {
            ((StateHolder)result).restoreState(context, this.savedState);
         }

         return result;
      }
   }

   private static Class loadClass(String name, Object fallbackClass) throws ClassNotFoundException {
      ClassLoader loader = Thread.currentThread().getContextClassLoader();
      if (loader == null) {
         loader = fallbackClass.getClass().getClassLoader();
      }

      return Class.forName(name, false, loader);
   }
}
