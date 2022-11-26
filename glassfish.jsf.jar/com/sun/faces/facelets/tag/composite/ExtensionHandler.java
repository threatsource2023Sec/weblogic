package com.sun.faces.facelets.tag.composite;

import com.sun.faces.facelets.compiler.CompilationMessageHolder;
import com.sun.faces.facelets.compiler.EncodingHandler;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagHandler;

public class ExtensionHandler extends TagHandler {
   public ExtensionHandler(TagConfig tagConfig) {
      super(tagConfig);
   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      if (null != this.nextHandler) {
         String content = this.nextHandler.toString().trim();
         int i;
         if (content.startsWith("<") && -1 != (i = content.indexOf(":"))) {
            content = content.substring(1, i);
            CompilationMessageHolder messageHolder = EncodingHandler.getCompilationMessageHolder(ctx);
            if (null != messageHolder) {
               messageHolder.removeNamespacePrefixMessages(content);
            }
         }
      }

   }
}
