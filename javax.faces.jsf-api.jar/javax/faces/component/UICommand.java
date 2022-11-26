package javax.faces.component;

import javax.el.ELException;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.FacesException;
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
   private Object value = null;
   private Boolean immediate;
   private MethodBinding methodBindingActionListener = null;
   private MethodExpression actionExpression = null;
   private Object[] values;

   public UICommand() {
      this.setRendererType("javax.faces.Button");
   }

   public String getFamily() {
      return "javax.faces.Command";
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
      return this.methodBindingActionListener;
   }

   /** @deprecated */
   public void setActionListener(MethodBinding actionListener) {
      this.methodBindingActionListener = actionListener;
   }

   public boolean isImmediate() {
      if (this.immediate != null) {
         return this.immediate;
      } else {
         ValueExpression ve = this.getValueExpression("immediate");
         if (ve != null) {
            try {
               return Boolean.TRUE.equals(ve.getValue(this.getFacesContext().getELContext()));
            } catch (ELException var3) {
               throw new FacesException(var3);
            }
         } else {
            return false;
         }
      }
   }

   public void setImmediate(boolean immediate) {
      this.immediate = immediate;
   }

   public Object getValue() {
      if (this.value != null) {
         return this.value;
      } else {
         ValueExpression ve = this.getValueExpression("value");
         if (ve != null) {
            try {
               return ve.getValue(this.getFacesContext().getELContext());
            } catch (ELException var3) {
               throw new FacesException(var3);
            }
         } else {
            return null;
         }
      }
   }

   public void setValue(Object value) {
      this.value = value;
   }

   public MethodExpression getActionExpression() {
      return this.actionExpression;
   }

   public void setActionExpression(MethodExpression actionExpression) {
      this.actionExpression = actionExpression;
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

   public Object saveState(FacesContext context) {
      if (this.values == null) {
         this.values = new Object[5];
      }

      this.values[0] = super.saveState(context);
      this.values[1] = saveAttachedState(context, this.methodBindingActionListener);
      this.values[2] = saveAttachedState(context, this.actionExpression);
      this.values[3] = this.immediate;
      this.values[4] = this.value;
      return this.values;
   }

   public void restoreState(FacesContext context, Object state) {
      this.values = (Object[])((Object[])state);
      super.restoreState(context, this.values[0]);
      this.methodBindingActionListener = (MethodBinding)restoreAttachedState(context, this.values[1]);
      this.actionExpression = (MethodExpression)restoreAttachedState(context, this.values[2]);
      this.immediate = (Boolean)this.values[3];
      this.value = this.values[4];
   }

   public void broadcast(FacesEvent event) throws AbortProcessingException {
      super.broadcast(event);
      if (event instanceof ActionEvent) {
         FacesContext context = this.getFacesContext();
         MethodBinding mb = this.getActionListener();
         if (mb != null) {
            mb.invoke(context, new Object[]{event});
         }

         ActionListener listener = context.getApplication().getActionListener();
         if (listener != null) {
            listener.processAction((ActionEvent)event);
         }
      }

   }

   public void queueEvent(FacesEvent e) {
      UIComponent c = e.getComponent();
      if (e instanceof ActionEvent && c instanceof ActionSource) {
         if (((ActionSource)c).isImmediate()) {
            e.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
         } else {
            e.setPhaseId(PhaseId.INVOKE_APPLICATION);
         }
      }

      super.queueEvent(e);
   }
}
