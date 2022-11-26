package javax.faces.component;

import com.sun.faces.util.Util;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;

public class UINamingContainer extends UIComponentBase implements NamingContainer, UniqueIdVendor, StateHolder {
   private static Logger LOGGER = Logger.getLogger("javax.faces.component", "javax.faces.LogStrings");
   public static final String COMPONENT_TYPE = "javax.faces.NamingContainer";
   public static final String COMPONENT_FAMILY = "javax.faces.NamingContainer";
   public static final String SEPARATOR_CHAR_PARAM_NAME = "javax.faces.SEPARATOR_CHAR";

   public UINamingContainer() {
      this.setRendererType((String)null);
   }

   public String getFamily() {
      return "javax.faces.NamingContainer";
   }

   public static char getSeparatorChar(FacesContext context) {
      if (context == null) {
         if (LOGGER.isLoggable(Level.SEVERE)) {
            LOGGER.log(Level.SEVERE, "UINamingContainer.getSeparatorChar() called with null FacesContext. This indicates a SEVERE error. Returning {0}", ':');
         }

         return ':';
      } else {
         Character separatorChar = (Character)context.getAttributes().get("javax.faces.SEPARATOR_CHAR");
         if (separatorChar == null) {
            String initParam = context.getExternalContext().getInitParameter("javax.faces.SEPARATOR_CHAR");
            separatorChar = ':';
            if (initParam != null) {
               initParam = initParam.trim();
               if (initParam.length() != 0) {
                  separatorChar = initParam.charAt(0);
               }
            }

            context.getAttributes().put("javax.faces.SEPARATOR_CHAR", separatorChar);
         }

         return separatorChar;
      }
   }

   public boolean visitTree(VisitContext context, VisitCallback callback) {
      Collection idsToVisit = context.getSubtreeIdsToVisit(this);
      if (!idsToVisit.isEmpty()) {
         return super.visitTree(context, callback);
      } else if (this.isVisitable(context)) {
         FacesContext facesContext = context.getFacesContext();
         this.pushComponentToEL(facesContext, (UIComponent)null);

         boolean var5;
         try {
            var5 = context.invokeVisitCallback(this, callback) == VisitResult.COMPLETE;
         } finally {
            this.popComponentFromEL(facesContext);
         }

         return var5;
      } else {
         return false;
      }
   }

   public String createUniqueId(FacesContext context, String seed) {
      int lastId = (Integer)Util.coalesce(this.getLastId(), 0);
      ++lastId;
      this.setLastId(lastId);
      return "j_id" + Util.coalesce(seed, lastId);
   }

   private Integer getLastId() {
      return (Integer)this.getStateHelper().get(UINamingContainer.PropertyKeys.lastId);
   }

   private void setLastId(Integer lastId) {
      this.getStateHelper().put(UINamingContainer.PropertyKeys.lastId, lastId);
   }

   static enum PropertyKeys {
      lastId;
   }
}
