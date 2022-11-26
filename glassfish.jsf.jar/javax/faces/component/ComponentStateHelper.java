package javax.faces.component;

import com.sun.faces.util.Util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;

class ComponentStateHelper implements StateHelper, TransientStateHelper {
   private UIComponent component;
   private boolean isTransient;
   private Map deltaMap;
   private Map defaultMap;
   private Map transientState;

   public ComponentStateHelper(UIComponent component) {
      this.component = component;
      this.deltaMap = new HashMap();
      this.defaultMap = new HashMap();
      this.transientState = null;
   }

   public Object put(Serializable key, Object value) {
      if (!this.component.initialStateMarked() && !(value instanceof PartialStateHolder)) {
         return this.defaultMap.put(key, value);
      } else {
         Object retVal = this.deltaMap.put(key, value);
         if (retVal == null) {
            return this.defaultMap.put(key, value);
         } else {
            this.defaultMap.put(key, value);
            return retVal;
         }
      }
   }

   public Object remove(Serializable key) {
      if (this.component.initialStateMarked()) {
         Object retVal = this.deltaMap.remove(key);
         if (retVal == null) {
            return this.defaultMap.remove(key);
         } else {
            this.defaultMap.remove(key);
            return retVal;
         }
      } else {
         return this.defaultMap.remove(key);
      }
   }

   public Object put(Serializable key, String mapKey, Object value) {
      Object ret = null;
      Object map;
      if (this.component.initialStateMarked()) {
         map = (Map)this.deltaMap.get(key);
         if (map == null) {
            map = new HashMap(5);
            this.deltaMap.put(key, map);
         }

         ret = ((Map)map).put(mapKey, value);
      }

      map = (Map)this.get(key);
      if (map == null) {
         map = new HashMap(8);
         this.defaultMap.put(key, map);
      }

      if (ret == null) {
         return ((Map)map).put(mapKey, value);
      } else {
         ((Map)map).put(mapKey, value);
         return ret;
      }
   }

   public Object get(Serializable key) {
      return this.defaultMap.get(key);
   }

   public Object eval(Serializable key) {
      return this.eval(key, (Object)null);
   }

   public Object eval(Serializable key, Object defaultValue) {
      Object retVal = this.get(key);
      if (retVal == null) {
         ValueExpression valueExpression = this.component.getValueExpression(key.toString());
         if (valueExpression != null) {
            retVal = valueExpression.getValue(this.component.getFacesContext().getELContext());
         }
      }

      return Util.coalesce(retVal, defaultValue);
   }

   public void add(Serializable key, Object value) {
      if (this.component.initialStateMarked()) {
         ((List)this.deltaMap.computeIfAbsent(key, (e) -> {
            return new ArrayList(4);
         })).add(value);
      }

      List items = (List)this.get(key);
      if (items == null) {
         items = new ArrayList(4);
         this.defaultMap.put(key, items);
      }

      ((List)items).add(value);
   }

   public Object remove(Serializable key, Object valueOrKey) {
      Object source = this.get(key);
      if (source instanceof Collection) {
         return this.removeFromList(key, valueOrKey);
      } else {
         return source instanceof Map ? this.removeFromMap(key, valueOrKey.toString()) : null;
      }
   }

   public Object saveState(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else {
         return this.component.initialStateMarked() ? this.saveMap(context, this.deltaMap) : this.saveMap(context, this.defaultMap);
      }
   }

   public void restoreState(FacesContext context, Object state) {
      if (context == null) {
         throw new NullPointerException();
      } else if (state != null) {
         if (!this.component.initialStateMarked() && !this.defaultMap.isEmpty()) {
            this.defaultMap.clear();
            if (!Util.isEmpty((Object)this.deltaMap)) {
               this.deltaMap.clear();
            }
         }

         Object[] savedState = (Object[])((Object[])state);
         if (savedState[savedState.length - 1] != null) {
            this.component.initialState = (Boolean)savedState[savedState.length - 1];
         }

         int length = (savedState.length - 1) / 2;

         for(int i = 0; i < length; ++i) {
            Object value = savedState[i * 2 + 1];
            Serializable serializable = (Serializable)savedState[i * 2];
            if (value != null) {
               if (value instanceof Collection) {
                  value = UIComponentBase.restoreAttachedState(context, value);
               } else if (value instanceof StateHolderSaver) {
                  value = ((StateHolderSaver)value).restore(context);
               } else {
                  value = value instanceof Serializable ? value : UIComponentBase.restoreAttachedState(context, value);
               }
            }

            if (value instanceof Map) {
               Iterator var10 = ((Map)value).entrySet().iterator();

               while(var10.hasNext()) {
                  Map.Entry entry = (Map.Entry)var10.next();
                  this.put(serializable, (String)entry.getKey(), entry.getValue());
               }
            } else if (value instanceof List) {
               this.defaultMap.remove(serializable);
               this.deltaMap.remove(serializable);
               List values = (List)value;
               values.stream().forEach((o) -> {
                  this.add(serializable, o);
               });
            } else {
               this.put(serializable, value);
               this.handleAttribute(serializable.toString(), value);
            }
         }

      }
   }

   private void handleAttribute(String name, Object value) {
      List setAttributes = (List)this.component.getAttributes().get("javax.faces.component.UIComponentBase.attributesThatAreSet");
      if (setAttributes == null) {
         String className = this.getClass().getName();
         if (className != null && className.startsWith("javax.faces.component.")) {
            setAttributes = new ArrayList(6);
            this.component.getAttributes().put("javax.faces.component.UIComponentBase.attributesThatAreSet", setAttributes);
         }
      }

      if (setAttributes != null) {
         if (value == null) {
            ValueExpression valueExpression = this.component.getValueExpression(name);
            if (valueExpression == null) {
               ((List)setAttributes).remove(name);
            }
         } else if (!((List)setAttributes).contains(name)) {
            ((List)setAttributes).add(name);
         }
      }

   }

   public boolean isTransient() {
      return this.isTransient;
   }

   public void setTransient(boolean newTransientValue) {
      this.isTransient = newTransientValue;
   }

   private Object saveMap(FacesContext context, Map map) {
      if (map.isEmpty()) {
         return !this.component.initialStateMarked() ? new Object[]{this.component.initialStateMarked()} : null;
      } else {
         Object[] savedState = new Object[map.size() * 2 + 1];
         int i = 0;

         for(Iterator var5 = map.entrySet().iterator(); var5.hasNext(); ++i) {
            Map.Entry entry = (Map.Entry)var5.next();
            Object value = entry.getValue();
            savedState[i * 2] = entry.getKey();
            if (value instanceof Collection || value instanceof StateHolder || value instanceof Map || !(value instanceof Serializable)) {
               value = UIComponentBase.saveAttachedState(context, value);
            }

            savedState[i * 2 + 1] = value;
         }

         if (!this.component.initialStateMarked()) {
            savedState[savedState.length - 1] = this.component.initialStateMarked();
         }

         return savedState;
      }
   }

   private Object removeFromList(Serializable key, Object value) {
      Object ret = null;
      Collection list;
      if (this.component.initialStateMarked() || value instanceof PartialStateHolder) {
         list = (Collection)this.deltaMap.get(key);
         if (list != null) {
            ret = list.remove(value);
            if (list.isEmpty()) {
               this.deltaMap.remove(key);
            }
         }
      }

      list = (Collection)this.get(key);
      if (list != null) {
         if (ret == null) {
            ret = list.remove(value);
         } else {
            list.remove(value);
         }

         if (list.isEmpty()) {
            this.defaultMap.remove(key);
         }
      }

      return ret;
   }

   private Object removeFromMap(Serializable key, String mapKey) {
      Object ret = null;
      Map map;
      if (this.component.initialStateMarked()) {
         map = (Map)this.deltaMap.get(key);
         if (map != null) {
            ret = map.remove(mapKey);
            if (map.isEmpty()) {
               this.deltaMap.remove(key);
            }
         }
      }

      map = (Map)this.get(key);
      if (map != null) {
         if (ret == null) {
            ret = map.remove(mapKey);
         } else {
            map.remove(mapKey);
         }

         if (map.isEmpty()) {
            this.defaultMap.remove(key);
         }
      }

      if (ret != null && !this.component.initialStateMarked()) {
         this.deltaMap.remove(key);
      }

      return ret;
   }

   public Object getTransient(Object key) {
      return this.transientState == null ? null : this.transientState.get(key);
   }

   public Object getTransient(Object key, Object defaultValue) {
      Object returnValue = this.transientState == null ? null : this.transientState.get(key);
      return returnValue != null ? returnValue : defaultValue;
   }

   public Object putTransient(Object key, Object value) {
      if (this.transientState == null) {
         this.transientState = new HashMap();
      }

      return this.transientState.put(key, value);
   }

   public void restoreTransientState(FacesContext context, Object state) {
      this.transientState = (Map)state;
   }

   public Object saveTransientState(FacesContext context) {
      return this.transientState;
   }
}
