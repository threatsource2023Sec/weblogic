package weblogic.tools.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ClipboardKeyEventProcessor extends KeyAdapter {
   private boolean controlPressed = false;

   public void keyPressed(KeyEvent e) {
      if (e.getKeyCode() == 17) {
         this.controlPressed = true;
      }

      if (this.controlPressed) {
         char pressedChar = e.getKeyChar();
         if (pressedChar != 'c' && pressedChar == 'C') {
         }

         if (pressedChar != 'x' && pressedChar == 'X') {
         }

         if (pressedChar != 'v' && pressedChar == 'V') {
         }
      }

   }

   public void keyReleased(KeyEvent e) {
      if (e.getKeyCode() == 17) {
         this.controlPressed = false;
      }

   }
}
