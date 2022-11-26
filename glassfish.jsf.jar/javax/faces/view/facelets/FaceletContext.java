package javax.faces.view.facelets;

import java.io.IOException;
import java.net.URL;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.FunctionMapper;
import javax.el.VariableMapper;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public abstract class FaceletContext extends ELContext {
   public static final String FACELET_CONTEXT_KEY = "javax.faces.FACELET_CONTEXT".intern();

   public abstract FacesContext getFacesContext();

   public abstract String generateUniqueId(String var1);

   public abstract ExpressionFactory getExpressionFactory();

   public abstract void setVariableMapper(VariableMapper var1);

   public abstract void setFunctionMapper(FunctionMapper var1);

   public abstract void setAttribute(String var1, Object var2);

   public abstract Object getAttribute(String var1);

   public abstract void includeFacelet(UIComponent var1, String var2) throws IOException;

   public abstract void includeFacelet(UIComponent var1, URL var2) throws IOException;
}
