package com.sun.faces.facelets.tag.ui;

import com.sun.faces.util.FacesLogger;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.ComponentConfig;
import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.MetaRuleset;
import javax.faces.view.facelets.Metadata;
import javax.faces.view.facelets.TagAttribute;

public class RepeatHandler extends ComponentHandler {
   private static final Logger log;

   public RepeatHandler(ComponentConfig config) {
      super(config);
   }

   protected MetaRuleset createMetaRuleset(Class type) {
      MetaRuleset meta = super.createMetaRuleset(type);
      String myNamespace = this.tag.getNamespace();
      if (!"http://java.sun.com/jsf/facelets".equals(myNamespace) && !"http://xmlns.jcp.org/jsf/facelets".equals(myNamespace)) {
         meta.add(new TagMetaData(type));
      }

      meta.alias("class", "styleClass");
      return meta;
   }

   static {
      log = FacesLogger.FACELETS_COMPOSITION.getLogger();
   }

   private class TagMetaData extends Metadata {
      private final String[] attrs;

      public TagMetaData(Class type) {
         Set s = new HashSet();
         TagAttribute[] ta = RepeatHandler.this.tag.getAttributes().getAll();

         for(int ix = 0; ix < ta.length; ++ix) {
            if ("class".equals(ta[ix].getLocalName())) {
               s.add("styleClass");
            } else {
               s.add(ta[ix].getLocalName());
            }
         }

         try {
            PropertyDescriptor[] pd = Introspector.getBeanInfo(type).getPropertyDescriptors();

            for(int i = 0; i < pd.length; ++i) {
               if (pd[i].getWriteMethod() != null) {
                  s.remove(pd[i].getName());
               }
            }
         } catch (Exception var7) {
            if (RepeatHandler.log.isLoggable(Level.FINEST)) {
               RepeatHandler.log.log(Level.FINEST, "Unable to get bean info", var7);
            }
         }

         this.attrs = (String[])((String[])s.toArray(new String[s.size()]));
      }

      public void applyMetadata(FaceletContext ctx, Object instance) {
         UIComponent c = (UIComponent)instance;
         Map localAttrs = c.getAttributes();
         localAttrs.put("alias.element", RepeatHandler.this.tag.getQName());
         if (this.attrs.length > 0) {
            localAttrs.put("alias.attributes", this.attrs);
         }

      }
   }
}
