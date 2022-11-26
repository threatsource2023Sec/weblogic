package javax.faces.component.behavior;

import java.util.Collection;
import java.util.Collections;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public abstract class ClientBehaviorContext {
   public static final String BEHAVIOR_SOURCE_PARAM_NAME = "javax.faces.source";
   public static final String BEHAVIOR_EVENT_PARAM_NAME = "javax.faces.behavior.event";

   public static ClientBehaviorContext createClientBehaviorContext(FacesContext context, UIComponent component, String eventName, String sourceId, Collection parameters) {
      return new ClientBehaviorContextImpl(context, component, eventName, sourceId, parameters);
   }

   public abstract FacesContext getFacesContext();

   public abstract UIComponent getComponent();

   public abstract String getEventName();

   public abstract String getSourceId();

   public abstract Collection getParameters();

   public static class Parameter {
      private String name;
      private Object value;

      public Parameter(String name, Object value) {
         if (null == name) {
            throw new NullPointerException();
         } else {
            this.name = name;
            this.value = value;
         }
      }

      public String getName() {
         return this.name;
      }

      public Object getValue() {
         return this.value;
      }
   }

   private static final class ClientBehaviorContextImpl extends ClientBehaviorContext {
      private FacesContext context;
      private UIComponent component;
      private String eventName;
      private String sourceId;
      private Collection parameters;

      private ClientBehaviorContextImpl(FacesContext context, UIComponent component, String eventName, String sourceId, Collection parameters) {
         if (null == context) {
            throw new NullPointerException();
         } else if (null == component) {
            throw new NullPointerException();
         } else if (null == eventName) {
            throw new NullPointerException();
         } else {
            this.context = context;
            this.component = component;
            this.eventName = eventName;
            this.sourceId = sourceId;
            this.parameters = (Collection)(parameters == null ? Collections.emptyList() : parameters);
         }
      }

      public FacesContext getFacesContext() {
         return this.context;
      }

      public UIComponent getComponent() {
         return this.component;
      }

      public String getEventName() {
         return this.eventName;
      }

      public String getSourceId() {
         return this.sourceId;
      }

      public Collection getParameters() {
         return this.parameters;
      }

      // $FF: synthetic method
      ClientBehaviorContextImpl(FacesContext x0, UIComponent x1, String x2, String x3, Collection x4, Object x5) {
         this(x0, x1, x2, x3, x4);
      }
   }
}
