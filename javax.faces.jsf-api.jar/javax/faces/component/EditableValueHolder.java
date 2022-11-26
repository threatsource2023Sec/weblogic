package javax.faces.component;

import javax.faces.el.MethodBinding;
import javax.faces.event.ValueChangeListener;
import javax.faces.validator.Validator;

public interface EditableValueHolder extends ValueHolder {
   Object getSubmittedValue();

   void setSubmittedValue(Object var1);

   boolean isLocalValueSet();

   void setLocalValueSet(boolean var1);

   boolean isValid();

   void setValid(boolean var1);

   boolean isRequired();

   void setRequired(boolean var1);

   boolean isImmediate();

   void setImmediate(boolean var1);

   /** @deprecated */
   MethodBinding getValidator();

   /** @deprecated */
   void setValidator(MethodBinding var1);

   /** @deprecated */
   MethodBinding getValueChangeListener();

   /** @deprecated */
   void setValueChangeListener(MethodBinding var1);

   void addValidator(Validator var1);

   Validator[] getValidators();

   void removeValidator(Validator var1);

   void addValueChangeListener(ValueChangeListener var1);

   ValueChangeListener[] getValueChangeListeners();

   void removeValueChangeListener(ValueChangeListener var1);
}
