package javax.faces.webapp;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

/** @deprecated */
public abstract class UIComponentTag extends UIComponentClassicTagBase implements Tag {
   private String binding = null;
   private String rendered = null;
   private boolean suppressed = false;

   public void setBinding(String binding) throws JspException {
      if (!isValueReference(binding)) {
         throw new IllegalArgumentException();
      } else {
         this.binding = binding;
      }
   }

   protected boolean hasBinding() {
      return null != this.binding;
   }

   public void setRendered(String rendered) {
      this.rendered = rendered;
   }

   protected boolean isSuppressed() {
      return this.suppressed;
   }

   public static boolean isValueReference(String value) {
      if (value == null) {
         throw new NullPointerException();
      } else {
         int start = value.indexOf("#{");
         return start != -1 && start < value.indexOf(125, start);
      }
   }

   public void release() {
      this.suppressed = false;
      this.binding = null;
      this.rendered = null;
      super.release();
   }

   protected void setProperties(UIComponent component) {
      if (this.rendered != null) {
         if (isValueReference(this.rendered)) {
            ValueBinding vb = this.getFacesContext().getApplication().createValueBinding(this.rendered);
            component.setValueBinding("rendered", vb);
         } else {
            component.setRendered(Boolean.valueOf(this.rendered));
         }
      }

      if (this.getRendererType() != null) {
         component.setRendererType(this.getRendererType());
      }

   }

   protected UIComponent createComponent(FacesContext context, String newId) {
      Application application = context.getApplication();
      UIComponent component;
      if (this.binding != null) {
         ValueBinding vb = application.createValueBinding(this.binding);
         component = application.createComponent(vb, context, this.getComponentType());
         component.setValueBinding("binding", vb);
      } else {
         component = application.createComponent(this.getComponentType());
      }

      component.setId(newId);
      this.setProperties(component);
      return component;
   }

   public static UIComponentTag getParentUIComponentTag(PageContext context) {
      UIComponentClassicTagBase result = getParentUIComponentClassicTagBase(context);
      return (UIComponentTag)(!(result instanceof UIComponentTag) ? new UIComponentTagAdapter(result) : (UIComponentTag)result);
   }

   private static class UIComponentTagAdapter extends UIComponentTag {
      UIComponentClassicTagBase classicDelegate;

      public UIComponentTagAdapter(UIComponentClassicTagBase classicDelegate) {
         this.classicDelegate = classicDelegate;
      }

      public String getComponentType() {
         return this.classicDelegate.getComponentType();
      }

      public String getRendererType() {
         return this.classicDelegate.getRendererType();
      }

      public int doStartTag() throws JspException {
         throw new IllegalStateException();
      }

      public int doEndTag() throws JspException {
         throw new IllegalStateException();
      }

      public UIComponent getComponentInstance() {
         return this.classicDelegate.getComponentInstance();
      }

      public boolean getCreated() {
         return this.classicDelegate.getCreated();
      }

      public Tag getParent() {
         return this.classicDelegate.getParent();
      }

      public void setParent(Tag parent) {
         throw new IllegalStateException();
      }
   }
}
