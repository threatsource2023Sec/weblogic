package org.python.apache.xerces.dom.events;

import org.w3c.dom.events.UIEvent;
import org.w3c.dom.views.AbstractView;

public class UIEventImpl extends EventImpl implements UIEvent {
   private AbstractView fView;
   private int fDetail;

   public AbstractView getView() {
      return this.fView;
   }

   public int getDetail() {
      return this.fDetail;
   }

   public void initUIEvent(String var1, boolean var2, boolean var3, AbstractView var4, int var5) {
      this.fView = var4;
      this.fDetail = var5;
      super.initEvent(var1, var2, var3);
   }
}
