package com.sun.faces.facelets.compiler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.CompositeFaceletHandler;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.FaceletHandler;

class CompilationUnit {
   protected static final FaceletHandler LEAF = new FaceletHandler() {
      public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      }

      public String toString() {
         return "FaceletHandler Tail";
      }
   };
   private List children;

   public CompilationUnit() {
   }

   protected void startNotify(CompilationManager manager) {
   }

   protected void finishNotify(CompilationManager manager) {
   }

   public void addChild(CompilationUnit unit) {
      if (this.children == null) {
         this.children = new ArrayList();
      }

      this.children.add(unit);
   }

   public void removeChildren() {
      this.children.clear();
   }

   public FaceletHandler createFaceletHandler() {
      return this.getNextFaceletHandler();
   }

   protected final FaceletHandler getNextFaceletHandler() {
      if (this.children != null && this.children.size() != 0) {
         if (this.children.size() == 1) {
            CompilationUnit u = (CompilationUnit)this.children.get(0);
            return u.createFaceletHandler();
         } else {
            FaceletHandler[] fh = new FaceletHandler[this.children.size()];

            for(int i = 0; i < fh.length; ++i) {
               fh[i] = ((CompilationUnit)this.children.get(i)).createFaceletHandler();
            }

            return new CompositeFaceletHandler(fh);
         }
      } else {
         return LEAF;
      }
   }
}
