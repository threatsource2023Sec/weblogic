package com.sun.faces.facelets.tag.ui;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.facelets.el.VariableMapperWrapper;
import com.sun.faces.facelets.tag.TagHandlerImpl;
import com.sun.faces.util.FacesLogger;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.VariableMapper;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagAttributeException;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagException;

public final class IncludeHandler extends TagHandlerImpl {
   private static final Logger log;
   private final TagAttribute src;

   public IncludeHandler(TagConfig config) {
      super(config);
      TagAttribute attr = null;
      attr = this.getAttribute("src");
      if (null == attr) {
         attr = this.getAttribute("file");
      }

      if (null == attr) {
         attr = this.getAttribute("page");
      }

      if (null == attr) {
         throw new TagException(this.tag, "Attribute 'src', 'file' or 'page' is required");
      } else {
         this.src = attr;
      }
   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      String path = this.src.getValue(ctx);
      if (path != null && path.length() != 0) {
         VariableMapper orig = ctx.getVariableMapper();
         ctx.setVariableMapper(new VariableMapperWrapper(orig));

         try {
            this.nextHandler.apply(ctx, (UIComponent)null);
            WebConfiguration webConfig = WebConfiguration.getInstance();
            if (path.startsWith(webConfig.getOptionValue(WebConfiguration.WebContextInitParameter.WebAppContractsDirectory))) {
               throw new TagAttributeException(this.tag, this.src, "Invalid src, contract resources cannot be accessed this way : " + path);
            }

            ctx.includeFacelet(parent, path);
         } catch (IOException var9) {
            if (log.isLoggable(Level.FINE)) {
               log.log(Level.FINE, var9.toString(), var9);
            }

            throw new TagAttributeException(this.tag, this.src, "Invalid path : " + path);
         } finally {
            ctx.setVariableMapper(orig);
         }

      }
   }

   static {
      log = FacesLogger.FACELETS_INCLUDE.getLogger();
   }
}
