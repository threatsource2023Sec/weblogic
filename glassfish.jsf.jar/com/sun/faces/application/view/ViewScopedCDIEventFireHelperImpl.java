package com.sun.faces.application.view;

import java.io.Serializable;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Event;
import javax.faces.component.UIViewRoot;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

public class ViewScopedCDIEventFireHelperImpl implements Serializable, ViewScopedCDIEventFireHelper {
   private static final long serialVersionUID = 5777997951420156171L;
   @Inject
   @Initialized(ViewScoped.class)
   Event viewScopeInitializedEvent;
   @Inject
   @Destroyed(ViewScoped.class)
   Event viewScopeDestroyedEvent;

   public void fireInitializedEvent(UIViewRoot root) {
      this.viewScopeInitializedEvent.fire(root);
   }

   public void fireDestroyedEvent(UIViewRoot root) {
      this.viewScopeDestroyedEvent.fire(root);
   }
}
