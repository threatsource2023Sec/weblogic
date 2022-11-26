package javax.faces.component;

import com.sun.faces.application.MethodBindingMethodExpressionAdapter;
import java.util.Map;
import javax.el.MethodExpression;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextWrapper;
import javax.faces.el.MethodBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;

public class UIViewAction extends UIComponentBase implements ActionSource2 {
   public static final String COMPONENT_TYPE = "javax.faces.ViewAction";
   public static final String COMPONENT_FAMILY = "javax.faces.ViewAction";
   private static final String UIVIEWACTION_BROADCAST = "javax.faces.ViewAction.broadcast";
   private static final String UIVIEWACTION_EVENT_COUNT = "javax.faces.ViewAction.eventCount";

   public UIViewAction() {
      this.setRendererType((String)null);
   }

   public String getFamily() {
      return "javax.faces.ViewAction";
   }

   private void incrementEventCount(FacesContext context) {
      Map attrs = context.getAttributes();
      Integer count = (Integer)attrs.get("javax.faces.ViewAction.eventCount");
      if (null == count) {
         attrs.put("javax.faces.ViewAction.eventCount", 1);
      } else {
         attrs.put("javax.faces.ViewAction.eventCount", count + 1);
      }

   }

   private boolean decrementEventCountAndReturnTrueIfZero(FacesContext context) {
      boolean result = true;
      Map attrs = context.getAttributes();
      Integer count = (Integer)attrs.get("javax.faces.ViewAction.eventCount");
      if (null != count) {
         count = count - 1;
         if (count < 1) {
            attrs.remove("javax.faces.ViewAction.eventCount");
            result = true;
         } else {
            attrs.put("javax.faces.ViewAction.eventCount", count);
            result = false;
         }
      }

      return result;
   }

   /** @deprecated */
   @Deprecated
   public MethodBinding getAction() {
      MethodBinding result = null;
      MethodExpression me;
      if (null != (me = this.getActionExpression())) {
         result = new MethodBindingMethodExpressionAdapter(me);
      }

      return result;
   }

   /** @deprecated */
   @Deprecated
   public void setAction(MethodBinding action) {
   }

   /** @deprecated */
   @Deprecated
   public MethodBinding getActionListener() {
      throw new UnsupportedOperationException("Not supported.");
   }

   /** @deprecated */
   @Deprecated
   public void setActionListener(MethodBinding actionListener) {
      throw new UnsupportedOperationException("Not supported.");
   }

   public boolean isImmediate() {
      return (Boolean)this.getStateHelper().eval(UIViewAction.PropertyKeys.immediate, false);
   }

   public void setImmediate(boolean immediate) {
      this.getStateHelper().put(UIViewAction.PropertyKeys.immediate, immediate);
   }

   public String getPhase() {
      PhaseId myPhaseId = this.getPhaseId();
      String result = null;
      if (null != myPhaseId) {
         result = myPhaseId.getName();
      }

      return result;
   }

   public void setPhase(String phase) {
      PhaseId myPhaseId = PhaseId.phaseIdValueOf(phase);
      if (!PhaseId.ANY_PHASE.equals(myPhaseId) && !PhaseId.RESTORE_VIEW.equals(myPhaseId) && !PhaseId.RENDER_RESPONSE.equals(myPhaseId)) {
         this.getStateHelper().put(UIViewAction.PropertyKeys.phase, myPhaseId.getName());
      } else {
         throw new FacesException("View actions cannot be executed in specified phase: [" + myPhaseId.toString() + "]");
      }
   }

   private void setIsProcessingUIViewActionBroadcast(FacesContext context, boolean value) {
      Map attrs = context.getAttributes();
      if (value) {
         attrs.put("javax.faces.ViewAction.broadcast", Boolean.TRUE);
      } else {
         attrs.remove("javax.faces.ViewAction.broadcast");
      }

   }

   public static boolean isProcessingBroadcast(FacesContext context) {
      boolean result = context.getAttributes().containsKey("javax.faces.ViewAction.broadcast");
      return result;
   }

   private PhaseId getPhaseId() {
      PhaseId myPhaseId = null;
      String phaseIdString = (String)this.getStateHelper().eval(UIViewAction.PropertyKeys.phase);
      if (phaseIdString != null) {
         myPhaseId = PhaseId.phaseIdValueOf(phaseIdString);
      }

      return myPhaseId;
   }

   public void addActionListener(ActionListener listener) {
      this.addFacesListener(listener);
   }

   public ActionListener[] getActionListeners() {
      ActionListener[] al = (ActionListener[])((ActionListener[])this.getFacesListeners(ActionListener.class));
      return al;
   }

   public void removeActionListener(ActionListener listener) {
      this.removeFacesListener(listener);
   }

   public MethodExpression getActionExpression() {
      return (MethodExpression)this.getStateHelper().get(UIViewAction.PropertyKeys.actionExpression);
   }

   public void setActionExpression(MethodExpression actionExpression) {
      this.getStateHelper().put(UIViewAction.PropertyKeys.actionExpression, actionExpression);
   }

   public boolean isOnPostback() {
      return (Boolean)this.getStateHelper().eval(UIViewAction.PropertyKeys.onPostback, false);
   }

   public void setOnPostback(boolean onPostback) {
      this.getStateHelper().put(UIViewAction.PropertyKeys.onPostback, onPostback);
   }

   public boolean isRendered() {
      return (Boolean)this.getStateHelper().eval(UIViewAction.PropertyKeys.renderedAttr, true);
   }

   public void setRendered(boolean condition) {
      this.getStateHelper().put(UIViewAction.PropertyKeys.renderedAttr, condition);
   }

   public void broadcast(FacesEvent event) throws AbortProcessingException {
      super.broadcast(event);
      FacesContext context = event.getFacesContext();
      if (!(event instanceof ActionEvent)) {
         throw new IllegalArgumentException();
      } else {
         if (!context.getResponseComplete() && context.getViewRoot() == this.getViewRootOf(event)) {
            ActionListener listener = context.getApplication().getActionListener();
            if (listener != null) {
               boolean hasMoreViewActionEvents = false;
               UIViewRoot viewRootBefore = context.getViewRoot();

               assert null != viewRootBefore;

               InstrumentedFacesContext instrumentedContext = null;

               try {
                  instrumentedContext = new InstrumentedFacesContext(context);
                  this.setIsProcessingUIViewActionBroadcast(context, true);
                  instrumentedContext.disableRenderResponseControl().set();
                  listener.processAction((ActionEvent)event);
                  hasMoreViewActionEvents = !this.decrementEventCountAndReturnTrueIfZero(context);
               } finally {
                  this.setIsProcessingUIViewActionBroadcast(context, false);
                  if (null != instrumentedContext) {
                     instrumentedContext.restore();
                  }

               }

               if (!context.getResponseComplete()) {
                  UIViewRoot viewRootAfter = context.getViewRoot();

                  assert null != viewRootAfter;

                  String viewIdBefore = viewRootBefore.getViewId();
                  String viewIdAfter = viewRootAfter.getViewId();

                  assert null != viewIdBefore && null != viewIdAfter;

                  boolean viewIdsSame = viewIdBefore.equals(viewIdAfter);
                  if (viewIdsSame && !hasMoreViewActionEvents) {
                     context.renderResponse();
                  }
               }
            }
         }

      }
   }

   public void decode(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else if ((!context.isPostback() || this.isOnPostback()) && this.isRendered()) {
         ActionEvent e = new ActionEvent(context, this);
         PhaseId phaseId = this.getPhaseId();
         if (phaseId != null) {
            e.setPhaseId(phaseId);
         } else if (this.isImmediate()) {
            e.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
         } else {
            e.setPhaseId(PhaseId.INVOKE_APPLICATION);
         }

         this.incrementEventCount(context);
         this.queueEvent(e);
      }
   }

   private UIViewRoot getViewRootOf(FacesEvent e) {
      UIComponent c = e.getComponent();

      while(!(c instanceof UIViewRoot)) {
         c = c.getParent();
         if (c == null) {
            return null;
         }
      }

      return (UIViewRoot)c;
   }

   private class InstrumentedFacesContext extends FacesContextWrapper {
      private boolean viewRootCleared = false;
      private boolean renderedResponseControlDisabled = false;
      private Boolean postback = null;

      public InstrumentedFacesContext(FacesContext wrapped) {
         super(wrapped);
      }

      public UIViewRoot getViewRoot() {
         return this.viewRootCleared ? null : super.getViewRoot();
      }

      public void setViewRoot(UIViewRoot viewRoot) {
         this.viewRootCleared = false;
         super.setViewRoot(viewRoot);
      }

      public boolean isPostback() {
         return this.postback == null ? super.isPostback() : this.postback;
      }

      public void renderResponse() {
         if (!this.renderedResponseControlDisabled) {
            super.renderResponse();
         }

      }

      public InstrumentedFacesContext pushViewIntoRequestMap() {
         this.getExternalContext().getRequestMap().put("javax.servlet.include.servlet_path", super.getViewRoot().getViewId());
         return this;
      }

      public InstrumentedFacesContext clearPostback() {
         this.postback = false;
         return this;
      }

      public InstrumentedFacesContext clearViewRoot() {
         this.viewRootCleared = true;
         return this;
      }

      public InstrumentedFacesContext disableRenderResponseControl() {
         this.renderedResponseControlDisabled = true;
         return this;
      }

      public void set() {
         setCurrentInstance(this);
      }

      public void restore() {
         setCurrentInstance(this.getWrapped());
      }
   }

   static enum PropertyKeys {
      onPostback,
      actionExpression,
      immediate,
      phase,
      renderedAttr("if");

      private String name;

      private PropertyKeys() {
      }

      private PropertyKeys(String name) {
         this.name = name;
      }

      public String toString() {
         return this.name != null ? this.name : super.toString();
      }
   }
}
