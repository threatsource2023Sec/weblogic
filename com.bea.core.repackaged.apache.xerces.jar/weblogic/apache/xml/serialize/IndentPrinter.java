package weblogic.apache.xml.serialize;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/** @deprecated */
public class IndentPrinter extends Printer {
   private StringBuffer _line = new StringBuffer(80);
   private StringBuffer _text = new StringBuffer(20);
   private int _spaces = 0;
   private int _thisIndent;
   private int _nextIndent;

   public IndentPrinter(Writer var1, OutputFormat var2) {
      super(var1, var2);
      this._thisIndent = this._nextIndent = 0;
   }

   public void enterDTD() {
      if (this._dtdWriter == null) {
         this._line.append(this._text);
         this._text = new StringBuffer(20);
         this.flushLine(false);
         this._dtdWriter = new StringWriter();
         this._docWriter = this._writer;
         this._writer = this._dtdWriter;
      }

   }

   public String leaveDTD() {
      if (this._writer == this._dtdWriter) {
         this._line.append(this._text);
         this._text = new StringBuffer(20);
         this.flushLine(false);
         this._writer = this._docWriter;
         return this._dtdWriter.toString();
      } else {
         return null;
      }
   }

   public void printText(String var1) {
      this._text.append(var1);
   }

   public void printText(StringBuffer var1) {
      this._text.append(var1.toString());
   }

   public void printText(char var1) {
      this._text.append(var1);
   }

   public void printText(char[] var1, int var2, int var3) {
      this._text.append(var1, var2, var3);
   }

   public void printSpace() {
      if (this._text.length() > 0) {
         if (this._format.getLineWidth() > 0 && this._thisIndent + this._line.length() + this._spaces + this._text.length() > this._format.getLineWidth()) {
            this.flushLine(false);

            try {
               this._writer.write(this._format.getLineSeparator());
            } catch (IOException var2) {
               if (this._exception == null) {
                  this._exception = var2;
               }
            }
         }

         while(this._spaces > 0) {
            this._line.append(' ');
            --this._spaces;
         }

         this._line.append(this._text);
         this._text = new StringBuffer(20);
      }

      ++this._spaces;
   }

   public void breakLine() {
      this.breakLine(false);
   }

   public void breakLine(boolean var1) {
      if (this._text.length() > 0) {
         while(true) {
            if (this._spaces <= 0) {
               this._line.append(this._text);
               this._text = new StringBuffer(20);
               break;
            }

            this._line.append(' ');
            --this._spaces;
         }
      }

      this.flushLine(var1);

      try {
         this._writer.write(this._format.getLineSeparator());
      } catch (IOException var3) {
         if (this._exception == null) {
            this._exception = var3;
         }
      }

   }

   public void flushLine(boolean var1) {
      if (this._line.length() > 0) {
         try {
            if (this._format.getIndenting() && !var1) {
               int var2 = this._thisIndent;
               if (2 * var2 > this._format.getLineWidth() && this._format.getLineWidth() > 0) {
                  var2 = this._format.getLineWidth() / 2;
               }

               while(var2 > 0) {
                  this._writer.write(32);
                  --var2;
               }
            }

            this._thisIndent = this._nextIndent;
            this._spaces = 0;
            this._writer.write(this._line.toString());
            this._line = new StringBuffer(40);
         } catch (IOException var4) {
            if (this._exception == null) {
               this._exception = var4;
            }
         }
      }

   }

   public void flush() {
      if (this._line.length() > 0 || this._text.length() > 0) {
         this.breakLine();
      }

      try {
         this._writer.flush();
      } catch (IOException var2) {
         if (this._exception == null) {
            this._exception = var2;
         }
      }

   }

   public void indent() {
      this._nextIndent += this._format.getIndent();
   }

   public void unindent() {
      this._nextIndent -= this._format.getIndent();
      if (this._nextIndent < 0) {
         this._nextIndent = 0;
      }

      if (this._line.length() + this._spaces + this._text.length() == 0) {
         this._thisIndent = this._nextIndent;
      }

   }

   public int getNextIndent() {
      return this._nextIndent;
   }

   public void setNextIndent(int var1) {
      this._nextIndent = var1;
   }

   public void setThisIndent(int var1) {
      this._thisIndent = var1;
   }
}
