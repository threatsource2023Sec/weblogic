package javax.faces.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import javax.faces.render.Renderer;

public abstract class UIComponent implements StateHolder {
   private static final String OPTIMIZED_PACKAGE = "javax.faces.component.";
   List attributesThatAreSet;
   protected Map bindings = null;
   private boolean isUIComponentBase;
   private boolean isUIComponentBaseIsSet = false;

   public abstract Map getAttributes();

   /** @deprecated */
   public abstract ValueBinding getValueBinding(String var1);

   /** @deprecated */
   public abstract void setValueBinding(String var1, ValueBinding var2);

   public ValueExpression getValueExpression(String name) {
      ValueExpression result = null;
      if (name == null) {
         throw new NullPointerException();
      } else if (this.bindings == null) {
         if (!this.isUIComponentBase()) {
            ValueBinding binding = this.getValueBinding(name);
            if (null != binding) {
               result = new ValueExpressionValueBindingAdapter(binding);
               this.bindings = new HashMap();
               this.bindings.put(name, result);
            }
         }

         return result;
      } else {
         return (ValueExpression)this.bindings.get(name);
      }
   }

   public void setValueExpression(String name, ValueExpression binding) {
      if (name == null) {
         throw new NullPointerException();
      } else if (!"id".equals(name) && !"parent".equals(name)) {
         List sProperties;
         if (binding != null) {
            if (!binding.isLiteralText()) {
               if (this.bindings == null) {
                  this.bindings = new HashMap();
               }

               sProperties = this.getAttributesThatAreSet(true);
               if (sProperties != null && !sProperties.contains(name)) {
                  sProperties.add(name);
               }

               this.bindings.put(name, binding);
            } else {
               ELContext context = FacesContext.getCurrentInstance().getELContext();

               try {
                  this.getAttributes().put(name, binding.getValue(context));
               } catch (ELException var5) {
                  throw new FacesException(var5);
               }
            }
         } else if (this.bindings != null) {
            sProperties = this.getAttributesThatAreSet(false);
            if (sProperties != null) {
               sProperties.remove(name);
            }

            this.bindings.remove(name);
            if (this.bindings.isEmpty()) {
               this.bindings = null;
            }
         }

      } else {
         throw new IllegalArgumentException();
      }
   }

   public abstract String getClientId(FacesContext var1);

   public String getContainerClientId(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else {
         return this.getClientId(context);
      }
   }

   public abstract String getFamily();

   public abstract String getId();

   public abstract void setId(String var1);

   public abstract UIComponent getParent();

   public abstract void setParent(UIComponent var1);

   public abstract boolean isRendered();

   public abstract void setRendered(boolean var1);

   public abstract String getRendererType();

   public abstract void setRendererType(String var1);

   public abstract boolean getRendersChildren();

   private boolean isUIComponentBase() {
      if (!this.isUIComponentBaseIsSet) {
         this.isUIComponentBase = this instanceof UIComponentBase;
      }

      return this.isUIComponentBase;
   }

   public abstract List getChildren();

   public abstract int getChildCount();

   public abstract UIComponent findComponent(String var1);

   public boolean invokeOnComponent(FacesContext context, String clientId, ContextCallback callback) throws FacesException {
      if (null != context && null != clientId && null != callback) {
         boolean found = false;
         if (clientId.equals(this.getClientId(context))) {
            try {
               callback.invokeContextCallback(context, this);
               return true;
            } catch (Exception var6) {
               throw new FacesException(var6);
            }
         } else {
            for(Iterator itr = this.getFacetsAndChildren(); itr.hasNext() && !found; found = ((UIComponent)itr.next()).invokeOnComponent(context, clientId, callback)) {
            }

            return found;
         }
      } else {
         throw new NullPointerException();
      }
   }

   public abstract Map getFacets();

   public int getFacetCount() {
      return this.getFacets().size();
   }

   public abstract UIComponent getFacet(String var1);

   public abstract Iterator getFacetsAndChildren();

   public abstract void broadcast(FacesEvent var1) throws AbortProcessingException;

   public abstract void decode(FacesContext var1);

   public abstract void encodeBegin(FacesContext var1) throws IOException;

   public abstract void encodeChildren(FacesContext var1) throws IOException;

   public abstract void encodeEnd(FacesContext var1) throws IOException;

   public void encodeAll(FacesContext context) throws IOException {
      if (context == null) {
         throw new NullPointerException();
      } else if (this.isRendered()) {
         this.encodeBegin(context);
         if (this.getRendersChildren()) {
            this.encodeChildren(context);
         } else if (this.getChildCount() > 0) {
            Iterator var2 = this.getChildren().iterator();

            while(var2.hasNext()) {
               UIComponent kid = (UIComponent)var2.next();
               kid.encodeAll(context);
            }
         }

         this.encodeEnd(context);
      }
   }

   protected abstract void addFacesListener(FacesListener var1);

   protected abstract FacesListener[] getFacesListeners(Class var1);

   protected abstract void removeFacesListener(FacesListener var1);

   public abstract void queueEvent(FacesEvent var1);

   public abstract void processRestoreState(FacesContext var1, Object var2);

   public abstract void processDecodes(FacesContext var1);

   public abstract void processValidators(FacesContext var1);

   public abstract void processUpdates(FacesContext var1);

   public abstract Object processSaveState(FacesContext var1);

   protected abstract FacesContext getFacesContext();

   protected abstract Renderer getRenderer(FacesContext var1);

   List getAttributesThatAreSet(boolean create) {
      String name = this.getClass().getName();
      if (name != null && name.startsWith("javax.faces.component.") && create && this.attributesThatAreSet == null) {
         this.attributesThatAreSet = new ArrayList(6);
      }

      return this.attributesThatAreSet;
   }
}
