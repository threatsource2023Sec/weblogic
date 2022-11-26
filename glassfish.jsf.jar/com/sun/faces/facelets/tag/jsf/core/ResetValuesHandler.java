package com.sun.faces.facelets.tag.jsf.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import javax.faces.component.ActionSource;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.view.ActionSource2AttachedObjectHandler;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;

public final class ResetValuesHandler extends ActionListenerHandlerBase implements ActionSource2AttachedObjectHandler {
   private final TagAttribute render = this.getAttribute("render");
   private static Pattern SPLIT_PATTERN = Pattern.compile(" ");

   public ResetValuesHandler(TagConfig config) {
      super(config);
   }

   public void applyAttachedObject(FacesContext context, UIComponent parent) {
      FaceletContext ctx = (FaceletContext)context.getAttributes().get(FaceletContext.FACELET_CONTEXT_KEY);
      ActionSource as = (ActionSource)parent;
      String renderStr = (String)this.render.getObject(ctx, String.class);
      ActionListener listener = new LazyActionListener(toList(renderStr));
      as.addActionListener(listener);
   }

   private static List toList(String strValue) {
      if (strValue.indexOf(32) == -1) {
         return Collections.singletonList(strValue);
      } else {
         String[] values = SPLIT_PATTERN.split(strValue);
         return values != null && values.length != 0 ? Collections.unmodifiableList(Arrays.asList(values)) : null;
      }
   }

   private static final class LazyActionListener implements ActionListener, Serializable {
      Collection render;
      private static final long serialVersionUID = -5676209243297546166L;

      public LazyActionListener(Collection render) {
         this.render = new ArrayList(render);
      }

      public void processAction(ActionEvent event) throws AbortProcessingException {
         FacesContext context = FacesContext.getCurrentInstance();
         UIViewRoot root = context.getViewRoot();
         root.resetValues(context, this.render);
      }
   }
}
