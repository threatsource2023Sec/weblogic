package serp.bytecode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import serp.bytecode.visitor.BCVisitor;

public class LineNumberTable extends Attribute implements InstructionPtr {
   private List _lineNumbers = new ArrayList();

   LineNumberTable(int nameIndex, Attributes owner) {
      super(nameIndex, owner);
   }

   public LineNumber[] getLineNumbers() {
      Collections.sort(this._lineNumbers);
      return (LineNumber[])((LineNumber[])this._lineNumbers.toArray(new LineNumber[this._lineNumbers.size()]));
   }

   public LineNumber getLineNumber(int pc) {
      for(int i = this._lineNumbers.size() - 1; i >= 0; --i) {
         if (((LineNumber)this._lineNumbers.get(i))._target.getByteIndex() <= pc) {
            return (LineNumber)this._lineNumbers.get(i);
         }
      }

      return null;
   }

   public LineNumber getLineNumber(Instruction ins) {
      return ins == null ? null : this.getLineNumber(ins.getByteIndex());
   }

   public void setLineNumbers(LineNumber[] lines) {
      this.clear();
      if (lines != null) {
         for(int i = 0; i < lines.length; ++i) {
            this.addLineNumber(lines[i]);
         }
      }

   }

   public LineNumber addLineNumber(LineNumber ln) {
      LineNumber line = this.addLineNumber();
      line.setStartPc(ln.getStartPc());
      line.setLine(ln.getLine());
      return line;
   }

   public LineNumber addLineNumber() {
      LineNumber ln = new LineNumber(this);
      this._lineNumbers.add(ln);
      return ln;
   }

   public LineNumber addLineNumber(int startPc, int line) {
      LineNumber ln = this.addLineNumber();
      ln.setStartPc(startPc);
      ln.setLine(line);
      return ln;
   }

   public LineNumber addLineNumber(Instruction start, int line) {
      LineNumber ln = this.addLineNumber();
      ln.setStart(start);
      ln.setLine(line);
      return ln;
   }

   public void clear() {
      for(int i = 0; i < this._lineNumbers.size(); ++i) {
         ((LineNumber)this._lineNumbers.get(i)).invalidate();
      }

      this._lineNumbers.clear();
   }

   public boolean removeLineNumber(LineNumber ln) {
      if (ln != null && this._lineNumbers.remove(ln)) {
         ln.invalidate();
         return true;
      } else {
         return false;
      }
   }

   public boolean removeLineNumber(int pc) {
      return this.removeLineNumber(this.getLineNumber(pc));
   }

   public boolean removeLineNumber(Instruction ins) {
      return this.removeLineNumber(this.getLineNumber(ins));
   }

   public void updateTargets() {
      for(int i = 0; i < this._lineNumbers.size(); ++i) {
         ((LineNumber)this._lineNumbers.get(i)).updateTargets();
      }

   }

   public void replaceTarget(Instruction oldTarget, Instruction newTarget) {
      for(int i = 0; i < this._lineNumbers.size(); ++i) {
         ((LineNumber)this._lineNumbers.get(i)).replaceTarget(oldTarget, newTarget);
      }

   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterLineNumberTable(this);
      LineNumber[] lines = this.getLineNumbers();

      for(int i = 0; i < lines.length; ++i) {
         lines[i].acceptVisit(visit);
      }

      visit.exitLineNumberTable(this);
   }

   int getLength() {
      return 2 + 4 * this._lineNumbers.size();
   }

   void read(Attribute other) {
      this.setLineNumbers(((LineNumberTable)other).getLineNumbers());
   }

   void read(DataInput in, int length) throws IOException {
      this.clear();
      int numLines = in.readUnsignedShort();

      for(int i = 0; i < numLines; ++i) {
         LineNumber lineNumber = this.addLineNumber();
         lineNumber.read(in);
      }

   }

   void write(DataOutput out, int length) throws IOException {
      LineNumber[] lines = this.getLineNumbers();
      out.writeShort(lines.length);

      for(int i = 0; i < lines.length; ++i) {
         lines[i].write(out);
      }

   }

   public Code getCode() {
      return (Code)this.getOwner();
   }
}
