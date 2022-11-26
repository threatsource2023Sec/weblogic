package org.mozilla.javascript.tools.shell;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Vector;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Segment;

public class ConsoleTextArea extends JTextArea implements KeyListener, DocumentListener {
   private ConsoleWriter console1 = new ConsoleWriter(this);
   private ConsoleWriter console2 = new ConsoleWriter(this);
   private PrintStream out;
   private PrintStream err;
   private PrintWriter inPipe;
   private PipedInputStream in;
   private Vector history = new Vector();
   private int historyIndex = -1;
   private int outputMark = 0;

   public ConsoleTextArea(String[] var1) {
      this.out = new PrintStream(this.console1);
      this.err = new PrintStream(this.console2);
      PipedOutputStream var2 = new PipedOutputStream();
      this.inPipe = new PrintWriter(var2);
      this.in = new PipedInputStream();

      try {
         var2.connect(this.in);
      } catch (IOException var4) {
         var4.printStackTrace();
      }

      this.getDocument().addDocumentListener(this);
      this.addKeyListener(this);
      this.setLineWrap(true);
      this.setFont(new Font("Monospaced", 0, 12));
   }

   public synchronized void changedUpdate(DocumentEvent var1) {
   }

   public void eval(String var1) {
      this.inPipe.write(var1);
      this.inPipe.write("\n");
      this.inPipe.flush();
      this.console1.flush();
   }

   public PrintStream getErr() {
      return this.err;
   }

   public InputStream getIn() {
      return this.in;
   }

   public PrintStream getOut() {
      return this.out;
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
      this.requestFocus();
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
      } catch (BadLocationException var5) {
         var5.printStackTrace();
      }

      if (var3.count > 0) {
         this.history.addElement(var3.toString());
      }

      this.historyIndex = this.history.size();
      this.inPipe.write(var3.array, var3.offset, var3.count);
      this.append("\n");
      this.outputMark = var1.getLength();
      this.inPipe.write("\n");
      this.inPipe.flush();
      this.console1.flush();
   }

   public void select(int var1, int var2) {
      this.requestFocus();
      super.select(var1, var2);
   }

   public synchronized void write(String var1) {
      this.insert(var1, this.outputMark);
      int var2 = var1.length();
      this.outputMark += var2;
      this.select(this.outputMark, this.outputMark);
   }
}
