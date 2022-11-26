package org.mozilla.javascript.tools.debugger;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Segment;

class EvalTextArea extends JTextArea implements KeyListener, DocumentListener {
   Main db;
   private Vector history;
   private int historyIndex = -1;
   private int outputMark = 0;

   public EvalTextArea(Main var1) {
      this.db = var1;
      this.history = new Vector();
      Document var2 = this.getDocument();
      var2.addDocumentListener(this);
      this.addKeyListener(this);
      this.setLineWrap(true);
      this.setFont(new Font("Monospaced", 0, 12));
      this.append("% ");
      this.outputMark = var2.getLength();
   }

   public synchronized void changedUpdate(DocumentEvent var1) {
   }

   public synchronized void insertUpdate(DocumentEvent var1) {
      int var2 = var1.getLength();
      int var3 = var1.getOffset();
      if (this.outputMark > var3) {
         this.outputMark += var2;
      }

   }

   public void keyPressed(KeyEvent var1) {
      int var2 = var1.getKeyCode();
      if (var2 != 8 && var2 != 37) {
         int var3;
         if (var2 == 36) {
            var3 = this.getCaretPosition();
            if (var3 == this.outputMark) {
               var1.consume();
            } else if (var3 > this.outputMark && !var1.isControlDown()) {
               if (var1.isShiftDown()) {
                  this.moveCaretPosition(this.outputMark);
               } else {
                  this.setCaretPosition(this.outputMark);
               }

               var1.consume();
            }
         } else if (var2 == 10) {
            this.returnPressed();
            var1.consume();
         } else {
            int var4;
            if (var2 == 38) {
               --this.historyIndex;
               if (this.historyIndex >= 0) {
                  if (this.historyIndex >= this.history.size()) {
                     this.historyIndex = this.history.size() - 1;
                  }

                  if (this.historyIndex >= 0) {
                     String var6 = (String)this.history.elementAt(this.historyIndex);
                     var4 = this.getDocument().getLength();
                     this.replaceRange(var6, this.outputMark, var4);
                     int var5 = this.outputMark + var6.length();
                     this.select(var5, var5);
                  } else {
                     ++this.historyIndex;
                  }
               } else {
                  ++this.historyIndex;
               }

               var1.consume();
            } else if (var2 == 40) {
               var3 = this.outputMark;
               if (this.history.size() > 0) {
                  ++this.historyIndex;
                  if (this.historyIndex < 0) {
                     this.historyIndex = 0;
                  }

                  var4 = this.getDocument().getLength();
                  if (this.historyIndex < this.history.size()) {
                     String var7 = (String)this.history.elementAt(this.historyIndex);
                     this.replaceRange(var7, this.outputMark, var4);
                     var3 = this.outputMark + var7.length();
                  } else {
                     this.historyIndex = this.history.size();
                     this.replaceRange("", this.outputMark, var4);
                  }
               }

               this.select(var3, var3);
               var1.consume();
            }
         }
      } else if (this.outputMark == this.getCaretPosition()) {
         var1.consume();
      }

   }

   public synchronized void keyReleased(KeyEvent var1) {
   }

   public void keyTyped(KeyEvent var1) {
      char var2 = var1.getKeyChar();
      if (var2 == '\b') {
         if (this.outputMark == this.getCaretPosition()) {
            var1.consume();
         }
      } else if (this.getCaretPosition() < this.outputMark) {
         this.setCaretPosition(this.outputMark);
      }

   }

   public synchronized void postUpdateUI() {
      this.setCaret(this.getCaret());
      this.select(this.outputMark, this.outputMark);
   }

   public synchronized void removeUpdate(DocumentEvent var1) {
      int var2 = var1.getLength();
      int var3 = var1.getOffset();
      if (this.outputMark > var3) {
         if (this.outputMark >= var3 + var2) {
            this.outputMark -= var2;
         } else {
            this.outputMark = var3;
         }
      }

   }

   synchronized void returnPressed() {
      Document var1 = this.getDocument();
      int var2 = var1.getLength();
      Segment var3 = new Segment();

      try {
         var1.getText(this.outputMark, var2 - this.outputMark, var3);
      } catch (BadLocationException var6) {
         var6.printStackTrace();
      }

      String var4 = var3.toString();
      if (this.db.stringIsCompilableUnit(var4)) {
         if (var4.trim().length() > 0) {
            this.history.addElement(var4);
            this.historyIndex = this.history.size();
         }

         this.append("\n");
         String var5 = this.db.eval(var4);
         if (var5.length() > 0) {
            this.append(var5);
            this.append("\n");
         }

         this.append("% ");
         this.outputMark = var1.getLength();
      } else {
         this.append("\n");
      }

   }

   public void select(int var1, int var2) {
      super.select(var1, var2);
   }

   public synchronized void write(String var1) {
      this.insert(var1, this.outputMark);
      int var2 = var1.length();
      this.outputMark += var2;
      this.select(this.outputMark, this.outputMark);
   }
}
