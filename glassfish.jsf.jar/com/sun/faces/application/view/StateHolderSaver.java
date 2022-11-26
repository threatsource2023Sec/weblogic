package com.sun.faces.application.view;

import java.io.Serializable;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

class StateHolderSaver implements Serializable {
   private static final long serialVersionUID = 4866548262845392093L;
   private String className = null;
   private Serializable savedState = null;
   public static final String DYNAMIC_COMPONENT = "com.sun.faces.DynamicComponent";

   public boolean componentAddedDynamically() {
      boolean result = false;
      if (null == this.className && null != this.savedState) {
         return result;
      } else if (this.className == null) {
         return result;
      } else {
         if (null != this.savedState) {
            Serializable[] tuple = (Serializable[])((Serializable[])this.savedState);
            result = (Boolean)tuple[StateHolderSaver.StateHolderTupleIndices.ComponentAddedDynamically.ordinal()];
         }

         return result;
      }
   }

   public StateHolderSaver(FacesContext context, Object toSave) {
      if (toSave == null) {
         this.className = null;
      } else {
         this.className = toSave.getClass().getName();
      }

      if (toSave instanceof StateHolder) {
         if (!((StateHolder)toSave).isTransient()) {
            Serializable[] tuple = new Serializable[StateHolderSaver.StateHolderTupleIndices.LastMember.ordinal()];
            tuple[StateHolderSaver.StateHolderTupleIndices.StateHolderSaverInstance.ordinal()] = (Serializable)((StateHolder)toSave).saveState(context);
            if (toSave instanceof UIComponent) {
               tuple[StateHolderSaver.StateHolderTupleIndices.ComponentAddedDynamically.ordinal()] = ((UIComponent)toSave).getAttributes().containsKey("com.sun.faces.DynamicComponent") ? Boolean.TRUE : Boolean.FALSE;
            }

            this.savedState = tuple;
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
            Serializable[] tuple = (Serializable[])((Serializable[])this.savedState);
            ((StateHolder)result).restoreState(context, tuple[StateHolderSaver.StateHolderTupleIndices.StateHolderSaverInstance.ordinal()]);
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

   private static enum StateHolderTupleIndices {
      StateHolderSaverInstance,
      ComponentAddedDynamically,
      LastMember;
   }
}
