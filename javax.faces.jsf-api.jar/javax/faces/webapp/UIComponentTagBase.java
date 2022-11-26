package javax.faces.webapp;

import java.util.logging.Logger;
import javax.el.ELContext;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.jsp.tagext.JspTag;

public abstract class UIComponentTagBase implements JspTag {
   protected static Logger log = Logger.getLogger("javax.faces.webapp", "javax.faces.LogStrings");

   protected abstract FacesContext getFacesContext();

   protected ELContext getELContext() {
      FacesContext fc = this.getFacesContext();
      ELContext result = null;
      if (null != fc) {
         result = fc.getELContext();
      }

      return result;
   }

   protected abstract void addChild(UIComponent var1);

   protected abstract void addFacet(String var1);

   public abstract void setId(String var1);

   public abstract String getComponentType();

   public abstract String getRendererType();

   public abstract UIComponent getComponentInstance();

   public abstract boolean getCreated();

   protected abstract int getIndexOfNextChildTag();
}
