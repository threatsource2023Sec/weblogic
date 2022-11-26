package antlr.debug;

import antlr.CharStreamException;
import antlr.InputBuffer;
import java.util.Vector;

public class DebuggingInputBuffer extends InputBuffer {
   private InputBuffer buffer;
   private InputBufferEventSupport inputBufferEventSupport;
   private boolean debugMode = true;

   public DebuggingInputBuffer(InputBuffer var1) {
      this.buffer = var1;
      this.inputBufferEventSupport = new InputBufferEventSupport(this);
   }

   public void addInputBufferListener(InputBufferListener var1) {
      this.inputBufferEventSupport.addInputBufferListener(var1);
   }

   public void consume() {
      char var1 = ' ';

      try {
         var1 = this.buffer.LA(1);
      } catch (CharStreamException var3) {
      }

      this.buffer.consume();
      if (this.debugMode) {
         this.inputBufferEventSupport.fireConsume(var1);
      }

   }

   public void fill(int var1) throws CharStreamException {
      this.buffer.fill(var1);
   }

   public Vector getInputBufferListeners() {
      return this.inputBufferEventSupport.getInputBufferListeners();
   }

   public boolean isDebugMode() {
      return this.debugMode;
   }

   public boolean isMarked() {
      return this.buffer.isMarked();
   }

   public char LA(int var1) throws CharStreamException {
      char var2 = this.buffer.LA(var1);
      if (this.debugMode) {
         this.inputBufferEventSupport.fireLA(var2, var1);
      }

      return var2;
   }

   public int mark() {
      int var1 = this.buffer.mark();
      this.inputBufferEventSupport.fireMark(var1);
      return var1;
   }

   public void removeInputBufferListener(InputBufferListener var1) {
      if (this.inputBufferEventSupport != null) {
         this.inputBufferEventSupport.removeInputBufferListener(var1);
      }

   }

   public void rewind(int var1) {
      this.buffer.rewind(var1);
      this.inputBufferEventSupport.fireRewind(var1);
   }

   public void setDebugMode(boolean var1) {
      this.debugMode = var1;
   }
}
