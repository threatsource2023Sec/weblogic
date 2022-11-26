package com.sun.faces.facelets.tag.jstl.core;

import com.sun.faces.facelets.tag.TagHandlerImpl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagException;

public final class ChooseHandler extends TagHandlerImpl {
   private final ChooseOtherwiseHandler otherwise;
   private final ChooseWhenHandler[] when;

   public ChooseHandler(TagConfig config) {
      super(config);
      List whenList = new ArrayList();
      Iterator itr = this.findNextByType(ChooseWhenHandler.class);

      while(itr.hasNext()) {
         whenList.add(itr.next());
      }

      if (whenList.isEmpty()) {
         throw new TagException(this.tag, "Choose Tag must have one or more When Tags");
      } else {
         this.when = (ChooseWhenHandler[])((ChooseWhenHandler[])whenList.toArray(new ChooseWhenHandler[whenList.size()]));
         itr = this.findNextByType(ChooseOtherwiseHandler.class);
         if (itr.hasNext()) {
            this.otherwise = (ChooseOtherwiseHandler)itr.next();
         } else {
            this.otherwise = null;
         }

      }
   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      for(int i = 0; i < this.when.length; ++i) {
         if (this.when[i].isTestTrue(ctx)) {
            this.when[i].apply(ctx, parent);
            return;
         }
      }

      if (this.otherwise != null) {
         this.otherwise.apply(ctx, parent);
      }

   }
}
