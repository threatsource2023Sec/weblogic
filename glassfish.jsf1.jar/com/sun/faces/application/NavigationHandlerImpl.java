package com.sun.faces.application;

import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class NavigationHandlerImpl extends NavigationHandler {
   private static final Logger logger;
   private Map caseListMap;
   private Set wildCardSet;
   private boolean navigationConfigured;

   public NavigationHandlerImpl() {
      if (logger.isLoggable(Level.FINE)) {
         logger.log(Level.FINE, "Created NavigationHandler instance ");
      }

      ApplicationFactory aFactory = (ApplicationFactory)FactoryFinder.getFactory("javax.faces.application.ApplicationFactory");
      aFactory.getApplication();
      ApplicationAssociate associate = ApplicationAssociate.getInstance(FacesContext.getCurrentInstance().getExternalContext());
      if (associate != null) {
         this.caseListMap = associate.getNavigationCaseListMappings();
         this.wildCardSet = associate.getNavigationWildCardList();
         this.navigationConfigured = this.wildCardSet != null && this.caseListMap != null;
      }

   }

   NavigationHandlerImpl(ApplicationAssociate associate) {
      if (associate == null) {
         throw new NullPointerException();
      } else {
         this.caseListMap = associate.getNavigationCaseListMappings();
         this.wildCardSet = associate.getNavigationWildCardList();
         this.navigationConfigured = this.wildCardSet != null && this.caseListMap != null;
      }
   }

   public void handleNavigation(FacesContext context, String fromAction, String outcome) {
      if (context == null) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "context");
         throw new NullPointerException(message);
      } else if (outcome == null) {
         if (logger.isLoggable(Level.FINE)) {
            logger.fine("No navigation rule found for null outcome and viewId " + context.getViewRoot().getViewId() + " Explicitly remain on the current view ");
         }

      } else {
         CaseStruct caseStruct = this.getViewId(context, fromAction, outcome);
         ExternalContext extContext = context.getExternalContext();
         if (caseStruct != null) {
            ViewHandler viewHandler = Util.getViewHandler(context);

            assert null != viewHandler;

            if (caseStruct.navCase.hasRedirect()) {
               String newPath = viewHandler.getActionURL(context, caseStruct.viewId);

               try {
                  if (logger.isLoggable(Level.FINE)) {
                     logger.fine("Redirecting to path " + newPath + " for outcome " + outcome + "and viewId " + caseStruct.viewId);
                  }

                  extContext.redirect(extContext.encodeActionURL(newPath));
               } catch (IOException var9) {
                  if (logger.isLoggable(Level.SEVERE)) {
                     logger.log(Level.SEVERE, "jsf.redirect_failed_error", newPath);
                  }

                  throw new FacesException(var9.getMessage(), var9);
               }

               context.responseComplete();
               if (logger.isLoggable(Level.FINE)) {
                  logger.fine("Response complete for " + caseStruct.viewId);
               }
            } else {
               UIViewRoot newRoot = viewHandler.createView(context, caseStruct.viewId);
               context.setViewRoot(newRoot);
               if (logger.isLoggable(Level.FINE)) {
                  logger.fine("Set new view in FacesContext for " + caseStruct.viewId);
               }
            }
         }

      }
   }

   private CaseStruct getViewId(FacesContext context, String fromAction, String outcome) {
      UIViewRoot root = context.getViewRoot();
      String viewId = root != null ? root.getViewId() : null;
      CaseStruct caseStruct = null;
      if (viewId != null) {
         caseStruct = this.findExactMatch(viewId, fromAction, outcome);
         if (caseStruct == null) {
            caseStruct = this.findWildCardMatch(viewId, fromAction, outcome);
         }
      }

      if (caseStruct == null) {
         caseStruct = this.findDefaultMatch(fromAction, outcome);
      }

      if (caseStruct == null && logger.isLoggable(Level.WARNING)) {
         if (fromAction == null) {
            logger.log(Level.FINE, "jsf.navigation.no_matching_outcome", new Object[]{viewId, outcome});
         } else {
            logger.log(Level.FINE, "jsf.navigation.no_matching_outcome_action", new Object[]{viewId, outcome, fromAction});
         }
      }

      return caseStruct;
   }

   private CaseStruct findExactMatch(String viewId, String fromAction, String outcome) {
      if (!this.navigationConfigured) {
         return null;
      } else {
         List caseList = (List)this.caseListMap.get(viewId);
         return caseList == null ? null : this.determineViewFromActionOutcome(caseList, fromAction, outcome);
      }
   }

   private CaseStruct findWildCardMatch(String viewId, String fromAction, String outcome) {
      CaseStruct result = null;
      if (!this.navigationConfigured) {
         return null;
      } else {
         Iterator i$ = this.wildCardSet.iterator();

         while(i$.hasNext()) {
            String fromViewId = (String)i$.next();
            if (viewId.startsWith(fromViewId)) {
               String wcFromViewId = (new StringBuilder(32)).append(fromViewId).append('*').toString();
               List caseList = (List)this.caseListMap.get(wcFromViewId);
               if (caseList == null) {
                  return null;
               }

               result = this.determineViewFromActionOutcome(caseList, fromAction, outcome);
               if (result != null) {
                  break;
               }
            }
         }

         return result;
      }
   }

   private CaseStruct findDefaultMatch(String fromAction, String outcome) {
      if (!this.navigationConfigured) {
         return null;
      } else {
         List caseList = (List)this.caseListMap.get("*");
         return caseList == null ? null : this.determineViewFromActionOutcome(caseList, fromAction, outcome);
      }
   }

   private CaseStruct determineViewFromActionOutcome(List caseList, String fromAction, String outcome) {
      CaseStruct result = new CaseStruct();
      Iterator i$ = caseList.iterator();

      ConfigNavigationCase cnc;
      String cncFromAction;
      String fromOutcome;
      String toViewId;
      do {
         if (!i$.hasNext()) {
            return null;
         }

         cnc = (ConfigNavigationCase)i$.next();
         cncFromAction = cnc.getFromAction();
         fromOutcome = cnc.getFromOutcome();
         toViewId = cnc.getToViewId();
         if (cncFromAction != null && fromOutcome != null && cncFromAction.equals(fromAction) && fromOutcome.equals(outcome)) {
            result.viewId = toViewId;
            result.navCase = cnc;
            return result;
         }

         if (cncFromAction == null && fromOutcome != null && fromOutcome.equals(outcome)) {
            result.viewId = toViewId;
            result.navCase = cnc;
            return result;
         }

         if (cncFromAction != null && fromOutcome == null && cncFromAction.equals(fromAction)) {
            result.viewId = toViewId;
            result.navCase = cnc;
            return result;
         }
      } while(cncFromAction != null || fromOutcome != null);

      result.viewId = toViewId;
      result.navCase = cnc;
      return result;
   }

   static {
      logger = FacesLogger.APPLICATION.getLogger();
   }

   private static class CaseStruct {
      String viewId;
      ConfigNavigationCase navCase;

      private CaseStruct() {
      }

      // $FF: synthetic method
      CaseStruct(Object x0) {
         this();
      }
   }
}
