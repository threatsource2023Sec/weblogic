package com.sun.faces.facelets.tag.composite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.view.BehaviorHolderAttachedObjectTarget;

public class BehaviorHolderAttachedObjectTargetImpl extends AttachedObjectTargetImpl implements BehaviorHolderAttachedObjectTarget {
   private String event;
   private boolean defaultEvent;

   public String getEvent() {
      return this.event;
   }

   public void setEvent(String event) {
      this.event = event;
   }

   public boolean isDefaultEvent() {
      return this.defaultEvent;
   }

   public void setDefaultEvent(boolean defaultEvent) {
      this.defaultEvent = defaultEvent;
   }

   public List getTargets(UIComponent topLevelComponent) {
      List targets = super.getTargets(topLevelComponent);
      List wrappedTargets = new ArrayList(targets.size());
      Iterator var4 = targets.iterator();

      while(var4.hasNext()) {
         UIComponent component = (UIComponent)var4.next();
         wrappedTargets.add(new BehaviorHolderWrapper(component, this.getName(), this.getEvent()));
      }

      return wrappedTargets;
   }
}
