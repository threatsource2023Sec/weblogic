package com.sun.faces.facelets.tag;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.view.facelets.CompositeFaceletHandler;
import javax.faces.view.facelets.FaceletHandler;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagHandler;

public abstract class TagHandlerImpl extends TagHandler {
   public TagHandlerImpl(TagConfig config) {
      super(config);
   }

   protected final Iterator findNextByType(Class type) {
      List found = new ArrayList();
      if (type.isAssignableFrom(this.nextHandler.getClass())) {
         found.add(this.nextHandler);
      } else if (this.nextHandler instanceof CompositeFaceletHandler) {
         FaceletHandler[] h = ((CompositeFaceletHandler)this.nextHandler).getHandlers();

         for(int i = 0; i < h.length; ++i) {
            if (type.isAssignableFrom(h[i].getClass())) {
               found.add(h[i]);
            }
         }
      }

      return found.iterator();
   }

   public static final Iterator findNextByType(FaceletHandler nextHandler, Class type) {
      List found = new ArrayList();
      if (type.isAssignableFrom(nextHandler.getClass())) {
         found.add(nextHandler);
      } else if (nextHandler instanceof CompositeFaceletHandler) {
         FaceletHandler[] h = ((CompositeFaceletHandler)nextHandler).getHandlers();

         for(int i = 0; i < h.length; ++i) {
            if (type.isAssignableFrom(h[i].getClass())) {
               found.add(h[i]);
            }
         }
      }

      return found.iterator();
   }
}
