package javax.faces.component;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.context.FacesContext;

class AttachedObjectListHolder implements PartialStateHolder {
   private boolean initialState;
   private List attachedObjects = new ArrayList(2);

   public void markInitialState() {
      if (!this.attachedObjects.isEmpty()) {
         Iterator var1 = this.attachedObjects.iterator();

         while(var1.hasNext()) {
            Object t = var1.next();
            if (t instanceof PartialStateHolder) {
               ((PartialStateHolder)t).markInitialState();
            }
         }
      }

      this.initialState = true;
   }

   public boolean initialStateMarked() {
      return this.initialState;
   }

   public void clearInitialState() {
      if (!this.attachedObjects.isEmpty()) {
         Iterator var1 = this.attachedObjects.iterator();

         while(var1.hasNext()) {
            Object t = var1.next();
            if (t instanceof PartialStateHolder) {
               ((PartialStateHolder)t).clearInitialState();
            }
         }
      }

      this.initialState = false;
   }

   public Object saveState(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else if (this.attachedObjects == null) {
         return null;
      } else {
         Object[] attachedObjects;
         int i;
         if (this.initialState) {
            attachedObjects = new Object[this.attachedObjects.size()];
            boolean stateWritten = false;
            i = 0;

            for(int len = attachedObjects.length; i < len; ++i) {
               Object attachedObject = this.attachedObjects.get(i);
               if (attachedObject instanceof StateHolder) {
                  StateHolder stateHolder = (StateHolder)attachedObject;
                  if (!stateHolder.isTransient()) {
                     attachedObjects[i] = stateHolder.saveState(context);
                  }

                  if (attachedObjects[i] != null) {
                     stateWritten = true;
                  }
               }
            }

            return stateWritten ? attachedObjects : null;
         } else {
            attachedObjects = new Object[this.attachedObjects.size()];
            int i = 0;

            for(i = attachedObjects.length; i < i; ++i) {
               attachedObjects[i] = UIComponentBase.saveAttachedState(context, this.attachedObjects.get(i));
            }

            return attachedObjects;
         }
      }
   }

   public void restoreState(FacesContext context, Object state) {
      if (context == null) {
         throw new NullPointerException();
      } else if (state != null) {
         Object[] attachedObjects = (Object[])((Object[])state);
         int i;
         int len;
         Object restored;
         if (attachedObjects.length > 0 && attachedObjects[0] instanceof StateHolderSaver) {
            if (this.attachedObjects != null) {
               this.attachedObjects.clear();
            } else {
               this.attachedObjects = new ArrayList(2);
            }

            i = 0;

            for(len = attachedObjects.length; i < len; ++i) {
               restored = ((StateHolderSaver)attachedObjects[i]).restore(context);
               if (restored != null) {
                  this.add(restored);
               }
            }
         } else {
            i = 0;

            for(len = attachedObjects.length; i < len; ++i) {
               restored = this.attachedObjects.get(i);
               if (restored instanceof StateHolder) {
                  ((StateHolder)restored).restoreState(context, attachedObjects[i]);
               }
            }
         }

      }
   }

   public boolean isTransient() {
      return false;
   }

   public void setTransient(boolean newTransientValue) {
   }

   void add(Object attachedObject) {
      this.clearInitialState();
      this.attachedObjects.add(attachedObject);
   }

   void remove(Object attachedObject) {
      this.clearInitialState();
      this.attachedObjects.remove(attachedObject);
   }

   Object[] asArray(Class type) {
      return (new ArrayList(this.attachedObjects)).toArray((Object[])((Object[])Array.newInstance(type, this.attachedObjects.size())));
   }

   Iterator iterator() {
      return this.attachedObjects.iterator();
   }
}
