package com.sun.faces.component.behavior;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.faces.application.Application;
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHint;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;

public class AjaxBehaviors implements Serializable {
   private static final long serialVersionUID = 1617682489423771119L;
   private static final String AJAX_BEHAVIORS = "javax.faces.component.AjaxBehaviors";
   private ArrayDeque behaviorStack = null;

   public AjaxBehaviors() {
      this.behaviorStack = new ArrayDeque();
   }

   public static AjaxBehaviors getAjaxBehaviors(FacesContext context, boolean createIfNull) {
      Map attrs = context.getAttributes();
      AjaxBehaviors ajaxBehaviors = (AjaxBehaviors)attrs.get("javax.faces.component.AjaxBehaviors");
      if (ajaxBehaviors == null && createIfNull) {
         ajaxBehaviors = new AjaxBehaviors();
         attrs.put("javax.faces.component.AjaxBehaviors", ajaxBehaviors);
      }

      return ajaxBehaviors;
   }

   public void addBehaviors(FacesContext context, ClientBehaviorHolder behaviorHolder) {
      if (this.behaviorStack != null && !this.behaviorStack.isEmpty()) {
         Iterator descendingIter = this.behaviorStack.descendingIterator();

         while(descendingIter.hasNext()) {
            ((BehaviorInfo)descendingIter.next()).addBehavior(context, behaviorHolder);
         }

      }
   }

   public void pushBehavior(FacesContext context, AjaxBehavior ajaxBehavior, String eventName) {
      this.behaviorStack.add(new BehaviorInfo(context, ajaxBehavior, eventName));
   }

   public void popBehavior() {
      if (this.behaviorStack.size() > 0) {
         this.behaviorStack.removeLast();
      }

   }

   public static class BehaviorInfo implements Serializable {
      private String eventName;
      private Object behaviorState;
      private static final long serialVersionUID = -7679229822647712959L;

      public BehaviorInfo(FacesContext context, AjaxBehavior ajaxBehavior, String eventName) {
         this.eventName = eventName;
         this.behaviorState = ajaxBehavior.saveState(context);
      }

      public void addBehavior(FacesContext context, ClientBehaviorHolder behaviorHolder) {
         String myEventName = this.eventName;
         if (myEventName == null) {
            myEventName = behaviorHolder.getDefaultEventName();
            if (myEventName == null) {
               return;
            }
         }

         if (this.shouldAddBehavior(behaviorHolder, myEventName)) {
            ClientBehavior behavior = this.createBehavior(context);
            behaviorHolder.addClientBehavior(myEventName, behavior);
         }

      }

      private boolean shouldAddBehavior(ClientBehaviorHolder behaviorHolder, String eventName) {
         if (!behaviorHolder.getEventNames().contains(eventName)) {
            return false;
         } else {
            Map allBehaviors = behaviorHolder.getClientBehaviors();
            List eventBehaviors = (List)allBehaviors.get(eventName);
            if (eventBehaviors != null && !eventBehaviors.isEmpty()) {
               Iterator var5 = eventBehaviors.iterator();

               Set hints;
               do {
                  if (!var5.hasNext()) {
                     return true;
                  }

                  ClientBehavior behavior = (ClientBehavior)var5.next();
                  hints = behavior.getHints();
               } while(!hints.contains(ClientBehaviorHint.SUBMITTING));

               return false;
            } else {
               return true;
            }
         }
      }

      private ClientBehavior createBehavior(FacesContext context) {
         Application application = context.getApplication();
         AjaxBehavior behavior = (AjaxBehavior)application.createBehavior("javax.faces.behavior.Ajax");
         behavior.restoreState(context, this.behaviorState);
         return behavior;
      }

      private BehaviorInfo() {
      }
   }
}
