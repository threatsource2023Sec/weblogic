package javax.faces.component;

import com.sun.faces.application.MethodBindingMethodExpressionAdapter;
import com.sun.faces.application.MethodExpressionMethodBindingAdapter;
import javax.el.MethodExpression;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;

public class UICommand extends UIComponentBase implements ActionSource2 {
   public static final String COMPONENT_TYPE = "javax.faces.Command";
   public static final String COMPONENT_FAMILY = "javax.faces.Command";

   public UICommand() {
      this.setRendererType("javax.faces.Button");
   }

   public String getFamily() {
      return "javax.faces.Command";
   }

   public boolean isImmediate() {
      return (Boolean)this.getStateHelper().eval(UICommand.PropertyKeys.immediate, false);
   }

   public void setImmediate(boolean immediate) {
      this.getStateHelper().put(UICommand.PropertyKeys.immediate, immediate);
   }

   public Object getValue() {
      return this.getStateHelper().eval(UICommand.PropertyKeys.value);
   }

   public void setValue(Object value) {
      this.getStateHelper().put(UICommand.PropertyKeys.value, value);
   }

   public MethodExpression getActionExpression() {
      return (MethodExpression)this.getStateHelper().get(UICommand.PropertyKeys.actionExpression);
   }

   public void setActionExpression(MethodExpression actionExpression) {
      this.getStateHelper().put(UICommand.PropertyKeys.actionExpression, actionExpression);
   }

   public void addActionListener(ActionListener listener) {
      this.addFacesListener(listener);
   }

   public ActionListener[] getActionListeners() {
      return (ActionListener[])((ActionListener[])this.getFacesListeners(ActionListener.class));
   }

   public void removeActionListener(ActionListener listener) {
      this.removeFacesListener(listener);
   }

   public void broadcast(FacesEvent event) throws AbortProcessingException {
      super.broadcast(event);
      if (event instanceof ActionEvent) {
         FacesContext context = event.getFacesContext();
         this.notifySpecifiedActionListener(context, event);
         ActionListener listener = context.getApplication().getActionListener();
         if (listener != null) {
            listener.processAction((ActionEvent)event);
         }
      }

   }

   public void queueEvent(FacesEvent event) {
      UIComponent component = event.getComponent();
      if (event instanceof ActionEvent && component instanceof ActionSource) {
         if (((ActionSource)component).isImmediate()) {
            event.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
         } else {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
         }
      }

      super.queueEvent(event);
   }

   /** @deprecated */
   public MethodBinding getAction() {
      MethodBinding result = null;
      MethodExpression me;
      if (null != (me = this.getActionExpression())) {
         if (me.getClass().equals(MethodExpressionMethodBindingAdapter.class)) {
            result = ((MethodExpressionMethodBindingAdapter)me).getWrapped();
         } else {
            result = new MethodBindingMethodExpressionAdapter(me);
         }
      }

      return (MethodBinding)result;
   }

   /** @deprecated */
   public void setAction(MethodBinding action) {
      if (null != action) {
         MethodExpressionMethodBindingAdapter adapter = new MethodExpressionMethodBindingAdapter(action);
         this.setActionExpression(adapter);
      } else {
         this.setActionExpression((MethodExpression)null);
      }

   }

   /** @deprecated */
   public MethodBinding getActionListener() {
      return (MethodBinding)this.getStateHelper().get(UICommand.PropertyKeys.methodBindingActionListener);
   }

   /** @deprecated */
   public void setActionListener(MethodBinding actionListener) {
      this.getStateHelper().put(UICommand.PropertyKeys.methodBindingActionListener, actionListener);
   }

   private void notifySpecifiedActionListener(FacesContext context, FacesEvent event) {
      MethodBinding mb = this.getActionListener();
      if (mb != null) {
         mb.invoke(context, new Object[]{event});
      }

   }

   static enum PropertyKeys {
      value,
      immediate,
      methodBindingActionListener,
      actionExpression;
   }
}
