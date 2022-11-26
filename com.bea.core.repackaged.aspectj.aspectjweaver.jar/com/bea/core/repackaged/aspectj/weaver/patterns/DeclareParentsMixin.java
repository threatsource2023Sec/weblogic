package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import java.io.IOException;
import java.util.List;

public class DeclareParentsMixin extends DeclareParents {
   private int bitflags = 0;

   public DeclareParentsMixin(TypePattern child, List parents) {
      super(child, parents, true);
   }

   public DeclareParentsMixin(TypePattern child, TypePatternList parents) {
      super(child, parents, true);
   }

   public boolean equals(Object other) {
      if (!(other instanceof DeclareParentsMixin)) {
         return false;
      } else {
         DeclareParentsMixin o = (DeclareParentsMixin)other;
         return o.child.equals(this.child) && o.parents.equals(this.parents) && o.bitflags == this.bitflags;
      }
   }

   public int hashCode() {
      int result = 23;
      result = 37 * result + this.child.hashCode();
      result = 37 * result + this.parents.hashCode();
      result = 37 * result + this.bitflags;
      return result;
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      s.writeByte(6);
      this.child.write(s);
      this.parents.write(s);
      this.writeLocation(s);
      s.writeInt(this.bitflags);
   }

   public static Declare read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      DeclareParentsMixin ret = new DeclareParentsMixin(TypePattern.read(s, context), TypePatternList.read(s, context));
      ret.readLocation(context, s);
      ret.bitflags = s.readInt();
      return ret;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("declare parents mixin: ");
      buf.append(this.child);
      buf.append(" implements ");
      buf.append(this.parents);
      buf.append(";");
      buf.append("bits=0x").append(Integer.toHexString(this.bitflags));
      return buf.toString();
   }

   public boolean isMixin() {
      return true;
   }
}
