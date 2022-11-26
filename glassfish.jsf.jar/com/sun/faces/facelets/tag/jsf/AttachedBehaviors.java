package com.sun.faces.facelets.tag.jsf;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.TagHandler;

public class AttachedBehaviors implements Serializable {
   private Map behaviors = new HashMap();
   public static final String COMPOSITE_BEHAVIORS_KEY = "javax.faces.view.ClientBehaviors";

   public void add(String eventName, TagHandler owner) {
      this.behaviors.put(eventName, owner);
   }

   public TagHandler get(String value) {
      return (TagHandler)this.behaviors.get(value);
   }

   public static AttachedBehaviors getAttachedBehaviorsHandler(UIComponent component) {
      Map attributes = component.getAttributes();
      AttachedBehaviors handler = (AttachedBehaviors)attributes.get("javax.faces.view.ClientBehaviors");
      if (null == handler) {
         handler = new AttachedBehaviors();
         attributes.put("javax.faces.view.ClientBehaviors", handler);
      }

      return handler;
   }
}
