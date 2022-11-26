package javax.faces.component.behavior;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.component.PartialStateHolder;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.BehaviorEvent;
import javax.faces.event.BehaviorListener;

public class BehaviorBase implements Behavior, PartialStateHolder {
   private List listeners;
   private boolean transientFlag = false;
   private boolean initialState = false;

   public void broadcast(BehaviorEvent event) throws AbortProcessingException {
      if (null == event) {
         throw new NullPointerException();
      } else {
         if (null != this.listeners) {
            Iterator var2 = this.listeners.iterator();

            while(var2.hasNext()) {
               BehaviorListener listener = (BehaviorListener)var2.next();
               if (event.isAppropriateListener(listener)) {
                  event.processListener(listener);
               }
            }
         }

      }
   }

   public boolean isTransient() {
      return this.transientFlag;
   }

   public void setTransient(boolean transientFlag) {
      this.transientFlag = transientFlag;
   }

   public Object saveState(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else {
         return this.initialStateMarked() ? null : UIComponentBase.saveAttachedState(context, this.listeners);
      }
   }

   public void restoreState(FacesContext context, Object state) {
      if (context == null) {
         throw new NullPointerException();
      } else {
         if (state != null) {
            this.listeners = (List)UIComponentBase.restoreAttachedState(context, state);
            this.clearInitialState();
         }

      }
   }

   public void markInitialState() {
      this.initialState = true;
   }

   public boolean initialStateMarked() {
      return this.initialState;
   }

   public void clearInitialState() {
      this.initialState = false;
   }

   protected void addBehaviorListener(BehaviorListener listener) {
      if (listener == null) {
         throw new NullPointerException();
      } else {
         if (this.listeners == null) {
            this.listeners = new ArrayList();
         }

         this.listeners.add(listener);
         this.clearInitialState();
      }
   }

   protected void removeBehaviorListener(BehaviorListener listener) {
      if (listener == null) {
         throw new NullPointerException();
      } else if (this.listeners != null) {
         this.listeners.remove(listener);
         this.clearInitialState();
      }
   }
}
