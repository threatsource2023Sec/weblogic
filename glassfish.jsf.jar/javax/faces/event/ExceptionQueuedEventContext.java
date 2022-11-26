package javax.faces.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public class ExceptionQueuedEventContext implements SystemEventListenerHolder {
   public static final String IN_BEFORE_PHASE_KEY = ExceptionQueuedEventContext.class.getName() + ".IN_BEFORE_PHASE";
   public static final String IN_AFTER_PHASE_KEY = ExceptionQueuedEventContext.class.getName() + ".IN_AFTER_PHASE";
   private FacesContext context;
   private Throwable thrown;
   private UIComponent component;
   private PhaseId phaseId;
   private Map attributes;
   private List listener;

   public ExceptionQueuedEventContext(FacesContext context, Throwable thrown) {
      this(context, thrown, (UIComponent)null, (PhaseId)null);
   }

   public ExceptionQueuedEventContext(FacesContext context, Throwable thrown, UIComponent component) {
      this(context, thrown, component, (PhaseId)null);
   }

   public ExceptionQueuedEventContext(FacesContext context, Throwable thrown, UIComponent component, PhaseId phaseId) {
      this.context = context;
      this.thrown = thrown;
      this.component = component;
      this.phaseId = phaseId == null ? context.getCurrentPhaseId() : phaseId;
   }

   public FacesContext getContext() {
      return this.context;
   }

   public Throwable getException() {
      return this.thrown;
   }

   public UIComponent getComponent() {
      return this.component;
   }

   public PhaseId getPhaseId() {
      return this.phaseId;
   }

   public boolean inBeforePhase() {
      return this.isAttributeDefined(IN_BEFORE_PHASE_KEY);
   }

   public boolean inAfterPhase() {
      return this.isAttributeDefined(IN_AFTER_PHASE_KEY);
   }

   public Map getAttributes() {
      if (null == this.attributes) {
         this.attributes = new HashMap();
      }

      return this.attributes;
   }

   public List getListenersForEventClass(Class facesEventClass) {
      if (null == this.listener) {
         List list = new ArrayList(1);
         list.add(this.context.getExceptionHandler());
         this.listener = Collections.unmodifiableList(list);
      }

      return this.listener;
   }

   private boolean isAttributeDefined(String key) {
      return this.attributes != null && this.attributes.containsKey(key);
   }
}
