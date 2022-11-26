package weblogic.apache.xerces.dom.events;

import org.w3c.dom.events.EventTarget;
import org.w3c.dom.events.MouseEvent;
import org.w3c.dom.views.AbstractView;

public class MouseEventImpl extends UIEventImpl implements MouseEvent {
   private int fScreenX;
   private int fScreenY;
   private int fClientX;
   private int fClientY;
   private boolean fCtrlKey;
   private boolean fAltKey;
   private boolean fShiftKey;
   private boolean fMetaKey;
   private short fButton;
   private EventTarget fRelatedTarget;

   public int getScreenX() {
      return this.fScreenX;
   }

   public int getScreenY() {
      return this.fScreenY;
   }

   public int getClientX() {
      return this.fClientX;
   }

   public int getClientY() {
      return this.fClientY;
   }

   public boolean getCtrlKey() {
      return this.fCtrlKey;
   }

   public boolean getAltKey() {
      return this.fAltKey;
   }

   public boolean getShiftKey() {
      return this.fShiftKey;
   }

   public boolean getMetaKey() {
      return this.fMetaKey;
   }

   public short getButton() {
      return this.fButton;
   }

   public EventTarget getRelatedTarget() {
      return this.fRelatedTarget;
   }

   public void initMouseEvent(String var1, boolean var2, boolean var3, AbstractView var4, int var5, int var6, int var7, int var8, int var9, boolean var10, boolean var11, boolean var12, boolean var13, short var14, EventTarget var15) {
      this.fScreenX = var6;
      this.fScreenY = var7;
      this.fClientX = var8;
      this.fClientY = var9;
      this.fCtrlKey = var10;
      this.fAltKey = var11;
      this.fShiftKey = var12;
      this.fMetaKey = var13;
      this.fButton = var14;
      this.fRelatedTarget = var15;
      super.initUIEvent(var1, var2, var3, var4, var5);
   }
}
