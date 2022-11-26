package antlr.debug;

import java.util.Vector;

public class InputBufferEventSupport {
   private Object source;
   private Vector inputBufferListeners;
   private InputBufferEvent inputBufferEvent;
   protected static final int CONSUME = 0;
   protected static final int LA = 1;
   protected static final int MARK = 2;
   protected static final int REWIND = 3;

   public InputBufferEventSupport(Object var1) {
      this.inputBufferEvent = new InputBufferEvent(var1);
      this.source = var1;
   }

   public void addInputBufferListener(InputBufferListener var1) {
      if (this.inputBufferListeners == null) {
         this.inputBufferListeners = new Vector();
      }

      this.inputBufferListeners.addElement(var1);
   }

   public void fireConsume(char var1) {
      this.inputBufferEvent.setValues(0, var1, 0);
      this.fireEvents(0, this.inputBufferListeners);
   }

   public void fireEvent(int var1, ListenerBase var2) {
      switch (var1) {
         case 0:
            ((InputBufferListener)var2).inputBufferConsume(this.inputBufferEvent);
            break;
         case 1:
            ((InputBufferListener)var2).inputBufferLA(this.inputBufferEvent);
            break;
         case 2:
            ((InputBufferListener)var2).inputBufferMark(this.inputBufferEvent);
            break;
         case 3:
            ((InputBufferListener)var2).inputBufferRewind(this.inputBufferEvent);
            break;
         default:
            throw new IllegalArgumentException("bad type " + var1 + " for fireEvent()");
      }

   }

   public void fireEvents(int var1, Vector var2) {
      Vector var3 = null;
      ListenerBase var4 = null;
      synchronized(this) {
         if (var2 == null) {
            return;
         }

         var3 = (Vector)var2.clone();
      }

      if (var3 != null) {
         for(int var5 = 0; var5 < var3.size(); ++var5) {
            var4 = (ListenerBase)var3.elementAt(var5);
            this.fireEvent(var1, var4);
         }
      }

   }

   public void fireLA(char var1, int var2) {
      this.inputBufferEvent.setValues(1, var1, var2);
      this.fireEvents(1, this.inputBufferListeners);
   }

   public void fireMark(int var1) {
      this.inputBufferEvent.setValues(2, ' ', var1);
      this.fireEvents(2, this.inputBufferListeners);
   }

   public void fireRewind(int var1) {
      this.inputBufferEvent.setValues(3, ' ', var1);
      this.fireEvents(3, this.inputBufferListeners);
   }

   public Vector getInputBufferListeners() {
      return this.inputBufferListeners;
   }

   protected void refresh(Vector var1) {
      Vector var2;
      synchronized(var1) {
         var2 = (Vector)var1.clone();
      }

      if (var2 != null) {
         for(int var3 = 0; var3 < var2.size(); ++var3) {
            ((ListenerBase)var2.elementAt(var3)).refresh();
         }
      }

   }

   public void refreshListeners() {
      this.refresh(this.inputBufferListeners);
   }

   public void removeInputBufferListener(InputBufferListener var1) {
      if (this.inputBufferListeners != null) {
         this.inputBufferListeners.removeElement(var1);
      }

   }
}
