package com.sun.faces.application.view;

import javax.faces.component.UIViewRoot;

public interface ViewScopedCDIEventFireHelper {
   void fireInitializedEvent(UIViewRoot var1);

   void fireDestroyedEvent(UIViewRoot var1);
}
