package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.IHasSourceLocation;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class PatternNode implements IHasSourceLocation {
   protected int start;
   protected int end;
   protected ISourceContext sourceContext;

   public PatternNode() {
      this.start = this.end = -1;
   }

   public int getStart() {
      return this.start + (this.sourceContext != null ? this.sourceContext.getOffset() : 0);
   }

   public int getEnd() {
      return this.end + (this.sourceContext != null ? this.sourceContext.getOffset() : 0);
   }

   public ISourceContext getSourceContext() {
      return this.sourceContext;
   }

   public String getFileName() {
      return "unknown";
   }

   public void setLocation(ISourceContext sourceContext, int start, int end) {
      this.sourceContext = sourceContext;
      this.start = start;
      this.end = end;
   }

   public void copyLocationFrom(PatternNode other) {
      this.start = other.start;
      this.end = other.end;
      this.sourceContext = other.sourceContext;
   }

   public ISourceLocation getSourceLocation() {
      return this.sourceContext == null ? null : this.sourceContext.makeSourceLocation(this);
   }

   public abstract void write(CompressingDataOutputStream var1) throws IOException;

   public void writeLocation(DataOutputStream s) throws IOException {
      s.writeInt(this.start);
      s.writeInt(this.end);
   }

   public void readLocation(ISourceContext context, DataInputStream s) throws IOException {
      this.start = s.readInt();
      this.end = s.readInt();
      this.sourceContext = context;
   }

   public abstract Object accept(PatternNodeVisitor var1, Object var2);

   public Object traverse(PatternNodeVisitor visitor, Object data) {
      return this.accept(visitor, data);
   }
}
