package javax.faces.component;

import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class UIOutput extends UIComponentBase implements ValueHolder {
   public static final String COMPONENT_TYPE = "javax.faces.Output";
   public static final String COMPONENT_FAMILY = "javax.faces.Output";
   private static final String FORCE_FULL_CONVERTER_STATE = "com.sun.faces.component.UIOutput.forceFullConverterState";
   private Converter converter;

   public UIOutput() {
      this.setRendererType("javax.faces.Text");
   }

   public String getFamily() {
      return "javax.faces.Output";
   }

   public Converter getConverter() {
      return this.converter != null ? this.converter : (Converter)this.getStateHelper().eval(UIOutput.PropertyKeys.converter);
   }

   public void setConverter(Converter converter) {
      this.clearInitialState();
      this.converter = converter;
   }

   public Object getLocalValue() {
      return this.getStateHelper().get(UIOutput.PropertyKeys.value);
   }

   public Object getValue() {
      return this.getStateHelper().eval(UIOutput.PropertyKeys.value);
   }

   public void setValue(Object value) {
      this.getStateHelper().put(UIOutput.PropertyKeys.value, value);
   }

   public void resetValue() {
      this.getStateHelper().remove(UIOutput.PropertyKeys.value);
   }

   public void markInitialState() {
      super.markInitialState();
      Converter c = this.getConverter();
      if (c instanceof PartialStateHolder) {
         ((PartialStateHolder)c).markInitialState();
      }

   }

   public void clearInitialState() {
      if (this.initialStateMarked()) {
         super.clearInitialState();
         Converter c = this.getConverter();
         if (c != null && c instanceof PartialStateHolder) {
            ((PartialStateHolder)c).clearInitialState();
         }
      }

   }

   public Object saveState(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else {
         Object converterState = null;
         if (this.converter != null) {
            if (this.initialStateMarked() && !this.getAttributes().containsKey("com.sun.faces.component.UIOutput.forceFullConverterState")) {
               if (this.converter instanceof StateHolder) {
                  StateHolder stateHolder = (StateHolder)this.converter;
                  if (!stateHolder.isTransient()) {
                     converterState = ((StateHolder)this.converter).saveState(context);
                  }
               }
            } else {
               if (this.getParent() != null && this.getParent().initialStateMarked()) {
                  this.getAttributes().put("com.sun.faces.component.UIOutput.forceFullConverterState", true);
                  if (this.converter instanceof PartialStateHolder) {
                     PartialStateHolder partialStateHolder = (PartialStateHolder)this.converter;
                     partialStateHolder.clearInitialState();
                  }
               }

               converterState = saveAttachedState(context, this.converter);
            }
         }

         Object[] values = (Object[])((Object[])super.saveState(context));
         return converterState == null && values == null ? values : new Object[]{values, converterState};
      }
   }

   public void restoreState(FacesContext context, Object state) {
      if (context == null) {
         throw new NullPointerException();
      } else if (state != null) {
         Object[] values = (Object[])((Object[])state);
         super.restoreState(context, values[0]);
         Object converterState = values[1];
         if (converterState instanceof StateHolderSaver) {
            this.converter = (Converter)restoreAttachedState(context, converterState);
         } else if (converterState != null && this.converter instanceof StateHolder) {
            ((StateHolder)this.converter).restoreState(context, converterState);
         }

      }
   }

   static enum PropertyKeys {
      value,
      converter;
   }
}
