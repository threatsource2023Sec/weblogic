package com.sun.faces.facelets.tag.jsf;

import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.application.annotation.FacesComponentUsage;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagHandler;

public class FacesComponentTagLibrary extends LazyTagLibrary {
   private static final Logger LOGGER;
   private ApplicationAssociate appAss;

   public FacesComponentTagLibrary(String ns) {
      super(ns);
      if (null == ns) {
         throw new NullPointerException();
      } else {
         this.appAss = ApplicationAssociate.getCurrentInstance();
      }
   }

   public boolean containsTagHandler(String ns, String localName) {
      Util.notNull("namespace", ns);
      Util.notNull("tagName", localName);
      if (!ns.equals(this.getNamespace())) {
         return false;
      } else {
         boolean containsTagHandler = super.containsTagHandler(ns, localName);
         if (!containsTagHandler) {
            FacesComponentUsage matchingFacesComponentUsage = this.findFacesComponentUsageForLocalName(ns, localName);
            containsTagHandler = null != matchingFacesComponentUsage;
         }

         return containsTagHandler;
      }
   }

   private FacesComponentUsage findFacesComponentUsageForLocalName(String ns, String localName) {
      FacesComponentUsage result = null;
      Util.notNull("namespace", ns);
      Util.notNull("tagName", localName);
      if (!ns.equals(this.getNamespace())) {
         return result;
      } else {
         List componentsForNamespace = this.appAss.getComponentsForNamespace(ns);
         Iterator var6 = componentsForNamespace.iterator();

         while(var6.hasNext()) {
            FacesComponentUsage cur = (FacesComponentUsage)var6.next();
            FacesComponent curFacesComponent = cur.getAnnotation();
            String tagName = curFacesComponent.tagName();
            if (null != tagName && 0 < tagName.length()) {
               if (localName.equals(tagName)) {
                  result = cur;
                  break;
               }
            } else if (null != tagName) {
               tagName = cur.getTarget().getSimpleName();
               tagName = tagName.substring(0, 1).toLowerCase() + tagName.substring(1);
               if (localName.equals(tagName)) {
                  result = cur;
                  break;
               }
            }
         }

         return result;
      }
   }

   public TagHandler createTagHandler(String ns, String localName, TagConfig tag) throws FacesException {
      assert this.containsTagHandler(ns, localName);

      TagHandler result = super.createTagHandler(ns, localName, tag);
      if (null == result) {
         FacesComponentUsage facesComponentUsage = this.findFacesComponentUsageForLocalName(ns, localName);
         String componentType = facesComponentUsage.getAnnotation().value();
         if (null == componentType || 0 == componentType.length()) {
            componentType = facesComponentUsage.getTarget().getSimpleName();
            componentType = Character.toLowerCase(componentType.charAt(0)) + componentType.substring(1);
         }

         UIComponent throwAwayComponent = FacesContext.getCurrentInstance().getApplication().createComponent(componentType);
         String rendererType = throwAwayComponent.getRendererType();
         super.addComponent(localName, componentType, rendererType);
         result = super.createTagHandler(ns, localName, tag);
      }

      return result;
   }

   public boolean tagLibraryForNSExists(String ns) {
      boolean result = false;
      List componentsForNamespace = this.appAss.getComponentsForNamespace(ns);
      result = !componentsForNamespace.isEmpty();
      return result;
   }

   static {
      LOGGER = FacesLogger.FACELETS_COMPONENT.getLogger();
   }
}
