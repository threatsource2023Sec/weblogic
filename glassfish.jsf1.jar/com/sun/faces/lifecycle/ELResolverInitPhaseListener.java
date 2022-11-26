package com.sun.faces.lifecycle;

import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.el.ELUtils;
import com.sun.faces.el.FacesCompositeELResolver;
import com.sun.faces.util.FacesLogger;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;

public class ELResolverInitPhaseListener implements PhaseListener {
   private static Logger LOGGER;
   private boolean postInitCompleted;
   private boolean preInitCompleted;

   public synchronized void afterPhase(PhaseEvent event) {
      if (!this.postInitCompleted) {
         LifecycleFactory factory = (LifecycleFactory)FactoryFinder.getFactory("javax.faces.lifecycle.LifecycleFactory");
         Iterator i = factory.getLifecycleIds();

         while(i.hasNext()) {
            Lifecycle lifecycle = factory.getLifecycle((String)i.next());
            lifecycle.removePhaseListener(this);
         }

         this.postInitCompleted = true;
      }

   }

   public synchronized void beforePhase(PhaseEvent event) {
      if (!this.preInitCompleted) {
         ApplicationAssociate associate = ApplicationAssociate.getInstance(FacesContext.getCurrentInstance().getExternalContext());
         associate.setRequestServiced();
         associate.initializeELResolverChains();
         associate.installProgrammaticallyAddedResolvers();
         this.preInitCompleted = true;
      }

   }

   public PhaseId getPhaseId() {
      return PhaseId.ANY_PHASE;
   }

   protected void populateFacesELResolverForJsp(FacesContext context) {
      ApplicationAssociate appAssociate = ApplicationAssociate.getInstance(context.getExternalContext());
      populateFacesELResolverForJsp(context.getApplication(), appAssociate);
   }

   public static void populateFacesELResolverForJsp(Application app, ApplicationAssociate appAssociate) {
      FacesCompositeELResolver compositeELResolverForJsp = appAssociate.getFacesELResolverForJsp();
      if (compositeELResolverForJsp == null) {
         if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.log(Level.INFO, "jsf.lifecycle.initphaselistener.resolvers_not_registered", new Object[]{appAssociate.getContextName()});
         }

      } else {
         ELUtils.buildJSPResolver(compositeELResolverForJsp, appAssociate);
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "jsf.lifecycle.initphaselistener.resolvers_registered", new Object[]{appAssociate.getContextName()});
         }

      }
   }

   public static void removeELResolverInitPhaseListener() {
      LifecycleFactory factory = (LifecycleFactory)FactoryFinder.getFactory("javax.faces.lifecycle.LifecycleFactory");
      Iterator i = factory.getLifecycleIds();

      while(i.hasNext()) {
         Lifecycle lifecycle = factory.getLifecycle((String)i.next());
         PhaseListener[] arr$ = lifecycle.getPhaseListeners();
         int len$ = arr$.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            PhaseListener cur = arr$[i$];
            if (cur instanceof ELResolverInitPhaseListener) {
               lifecycle.removePhaseListener(cur);
            }
         }
      }

   }

   static {
      LOGGER = FacesLogger.LIFECYCLE.getLogger();
   }
}
