package javax.faces.component.behavior;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorListener;

public class AjaxBehavior extends ClientBehaviorBase {
   public static final String BEHAVIOR_ID = "javax.faces.behavior.Ajax";
   private static final Set HINTS;
   private String onerror;
   private String onevent;
   private String delay;
   private List execute;
   private List render;
   private Boolean disabled;
   private Boolean immediate;
   private Boolean resetValues;
   private Map bindings;
   private static final String ONEVENT = "onevent";
   private static final String ONERROR = "onerror";
   private static final String IMMEDIATE = "immediate";
   private static final String RESET_VALUES = "resetValues";
   private static final String DISABLED = "disabled";
   private static final String EXECUTE = "execute";
   private static final String RENDER = "render";
   private static final String DELAY = "delay";
   private static String ALL;
   private static String FORM;
   private static String THIS;
   private static String NONE;
   private static List ALL_LIST;
   private static List FORM_LIST;
   private static List THIS_LIST;
   private static List NONE_LIST;
   private static Pattern SPLIT_PATTERN;

   public String getRendererType() {
      return "javax.faces.behavior.Ajax";
   }

   public Set getHints() {
      return HINTS;
   }

   public String getOnerror() {
      return (String)this.eval("onerror", this.onerror);
   }

   public void setOnerror(String onerror) {
      this.onerror = onerror;
      this.clearInitialState();
   }

   public String getOnevent() {
      return (String)this.eval("onevent", this.onevent);
   }

   public void setOnevent(String onevent) {
      this.onevent = onevent;
      this.clearInitialState();
   }

   public Collection getExecute() {
      return this.getCollectionValue("execute", this.execute);
   }

   public void setExecute(Collection execute) {
      this.execute = this.copyToList(execute);
      this.clearInitialState();
   }

   public String getDelay() {
      return (String)this.eval("delay", this.delay);
   }

   public void setDelay(String delay) {
      this.delay = delay;
      this.clearInitialState();
   }

   public Collection getRender() {
      return this.getCollectionValue("render", this.render);
   }

   public void setRender(Collection render) {
      this.render = this.copyToList(render);
      this.clearInitialState();
   }

   public boolean isResetValues() {
      Boolean result = (Boolean)this.eval("resetValues", this.resetValues);
      return result != null ? result : false;
   }

   public void setResetValues(boolean resetValues) {
      this.resetValues = resetValues;
      this.clearInitialState();
   }

   public boolean isDisabled() {
      Boolean result = (Boolean)this.eval("disabled", this.disabled);
      return result != null ? result : false;
   }

   public void setDisabled(boolean disabled) {
      this.disabled = disabled;
      this.clearInitialState();
   }

   public boolean isImmediate() {
      Boolean result = (Boolean)this.eval("immediate", this.immediate);
      return result != null ? result : false;
   }

   public void setImmediate(boolean immediate) {
      this.immediate = immediate;
      this.clearInitialState();
   }

   public boolean isImmediateSet() {
      return this.immediate != null || this.getValueExpression("immediate") != null;
   }

   public boolean isResetValuesSet() {
      return this.resetValues != null || this.getValueExpression("resetValues") != null;
   }

   public ValueExpression getValueExpression(String name) {
      if (name == null) {
         throw new NullPointerException();
      } else {
         return this.bindings == null ? null : (ValueExpression)this.bindings.get(name);
      }
   }

   public void setValueExpression(String name, ValueExpression binding) {
      if (name == null) {
         throw new NullPointerException();
      } else {
         if (binding != null) {
            if (binding.isLiteralText()) {
               this.setLiteralValue(name, binding);
            } else {
               if (this.bindings == null) {
                  this.bindings = new HashMap(6, 1.0F);
               }

               this.bindings.put(name, binding);
            }
         } else if (this.bindings != null) {
            this.bindings.remove(name);
            if (this.bindings.isEmpty()) {
               this.bindings = null;
            }
         }

         this.clearInitialState();
      }
   }

   public void addAjaxBehaviorListener(AjaxBehaviorListener listener) {
      this.addBehaviorListener(listener);
   }

   public void removeAjaxBehaviorListener(AjaxBehaviorListener listener) {
      this.removeBehaviorListener(listener);
   }

   public Object saveState(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else {
         Object superState = super.saveState(context);
         Object[] values;
         if (this.initialStateMarked()) {
            if (superState == null) {
               values = null;
            } else {
               values = new Object[]{superState};
            }
         } else {
            values = new Object[]{superState, this.onerror, this.onevent, this.disabled, this.immediate, this.resetValues, this.delay, saveList(this.execute), saveList(this.render), saveBindings(context, this.bindings)};
         }

         return values;
      }
   }

   public void restoreState(FacesContext context, Object state) {
      if (context == null) {
         throw new NullPointerException();
      } else {
         if (state != null) {
            Object[] values = (Object[])((Object[])state);
            super.restoreState(context, values[0]);
            if (values.length != 1) {
               this.onerror = (String)values[1];
               this.onevent = (String)values[2];
               this.disabled = (Boolean)values[3];
               this.immediate = (Boolean)values[4];
               this.resetValues = (Boolean)values[5];
               this.delay = (String)values[6];
               this.execute = restoreList("execute", values[7]);
               this.render = restoreList("render", values[8]);
               this.bindings = restoreBindings(context, values[9]);
               this.clearInitialState();
            }
         }

      }
   }

   private static Object saveBindings(FacesContext context, Map bindings) {
      if (bindings == null) {
         return null;
      } else {
         Object[] values = new Object[]{bindings.keySet().toArray(new String[bindings.size()]), null};
         Object[] bindingValues = bindings.values().toArray();

         for(int i = 0; i < bindingValues.length; ++i) {
            bindingValues[i] = UIComponentBase.saveAttachedState(context, bindingValues[i]);
         }

         values[1] = bindingValues;
         return values;
      }
   }

   private static Map restoreBindings(FacesContext context, Object state) {
      if (state == null) {
         return null;
      } else {
         Object[] values = (Object[])((Object[])state);
         String[] names = (String[])((String[])values[0]);
         Object[] states = (Object[])((Object[])values[1]);
         Map bindings = new HashMap(names.length);

         for(int i = 0; i < names.length; ++i) {
            bindings.put(names[i], (ValueExpression)UIComponentBase.restoreAttachedState(context, states[i]));
         }

         return bindings;
      }
   }

   private static Object saveList(List list) {
      if (list != null && !list.isEmpty()) {
         int size = list.size();
         return size == 1 ? list.get(0) : list.toArray(new String[size]);
      } else {
         return null;
      }
   }

   private static List restoreList(String propertyName, Object state) {
      if (state == null) {
         return null;
      } else {
         List list = null;
         if (state instanceof String) {
            list = toSingletonList(propertyName, (String)state);
         } else if (state instanceof String[]) {
            list = Collections.unmodifiableList(Arrays.asList((String[])((String[])state)));
         }

         return list;
      }
   }

   private Object eval(String propertyName, Object value) {
      if (value != null) {
         return value;
      } else {
         ValueExpression expression = this.getValueExpression(propertyName);
         if (expression != null) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            return expression.getValue(ctx.getELContext());
         } else {
            return null;
         }
      }
   }

   private Collection getCollectionValue(String propertyName, Collection collection) {
      if (collection != null) {
         return collection;
      } else {
         Collection result = null;
         ValueExpression expression = this.getValueExpression(propertyName);
         if (expression != null) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            Object value = expression.getValue(ctx.getELContext());
            if (value != null) {
               if (value instanceof Collection) {
                  return (Collection)value;
               }

               result = toList(propertyName, expression, value);
            }
         }

         return result == null ? Collections.emptyList() : result;
      }
   }

   private void setLiteralValue(String propertyName, ValueExpression expression) {
      assert expression.isLiteralText();

      ELContext context = FacesContext.getCurrentInstance().getELContext();

      Object value;
      try {
         value = expression.getValue(context);
      } catch (ELException var7) {
         throw new FacesException(var7);
      }

      if (null != propertyName) {
         switch (propertyName) {
            case "onevent":
               this.onevent = (String)value;
               break;
            case "delay":
               this.delay = (String)value;
               break;
            case "onerror":
               this.onerror = (String)value;
               break;
            case "immediate":
               this.immediate = (Boolean)value;
               break;
            case "resetValues":
               this.resetValues = (Boolean)value;
               break;
            case "disabled":
               this.disabled = (Boolean)value;
               break;
            case "execute":
               this.execute = toList(propertyName, expression, value);
               break;
            case "render":
               this.render = toList(propertyName, expression, value);
         }
      }

   }

   private static List toList(String propertyName, ValueExpression expression, Object value) {
      if (value instanceof String) {
         String strValue = (String)value;
         if (strValue.indexOf(32) == -1) {
            return toSingletonList(propertyName, strValue);
         } else {
            String[] values = SPLIT_PATTERN.split(strValue);
            return values != null && values.length != 0 ? Collections.unmodifiableList(Arrays.asList(values)) : null;
         }
      } else {
         throw new FacesException(expression.toString() + " : '" + propertyName + "' attribute value must be either a String or a Collection");
      }
   }

   private static List toSingletonList(String propertyName, String value) {
      if (null != value && value.length() != 0) {
         if (value.charAt(0) == '@') {
            if (ALL.equals(value)) {
               return ALL_LIST;
            }

            if (FORM.equals(value)) {
               return FORM_LIST;
            }

            if (THIS.equals(value)) {
               return THIS_LIST;
            }

            if (NONE.equals(value)) {
               return NONE_LIST;
            }
         }

         return Collections.singletonList(value);
      } else {
         return null;
      }
   }

   private List copyToList(Collection collection) {
      return collection != null && !collection.isEmpty() ? Collections.unmodifiableList(new ArrayList(collection)) : null;
   }

   static {
      HINTS = Collections.unmodifiableSet(EnumSet.of(ClientBehaviorHint.SUBMITTING));
      ALL = "@all";
      FORM = "@form";
      THIS = "@this";
      NONE = "@none";
      ALL_LIST = Collections.singletonList("@all");
      FORM_LIST = Collections.singletonList("@form");
      THIS_LIST = Collections.singletonList("@this");
      NONE_LIST = Collections.singletonList("@none");
      SPLIT_PATTERN = Pattern.compile(" ");
   }
}
