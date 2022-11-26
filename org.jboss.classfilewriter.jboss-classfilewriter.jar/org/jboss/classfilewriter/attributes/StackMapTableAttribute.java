package org.jboss.classfilewriter.attributes;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jboss.classfilewriter.ClassMethod;
import org.jboss.classfilewriter.code.CodeAttribute;
import org.jboss.classfilewriter.code.StackEntry;
import org.jboss.classfilewriter.code.StackEntryType;
import org.jboss.classfilewriter.code.StackFrame;
import org.jboss.classfilewriter.code.StackFrameType;
import org.jboss.classfilewriter.constpool.ConstPool;
import org.jboss.classfilewriter.util.ByteArrayDataOutputStream;
import org.jboss.classfilewriter.util.LazySize;

public class StackMapTableAttribute extends Attribute {
   private static final int FULL_FRAME = 255;
   private static final int SAME_FRAME_EXTENDED = 251;
   public static final String NAME = "StackMapTable";
   private final ClassMethod method;

   public StackMapTableAttribute(ClassMethod classMethod, ConstPool constPool) {
      super("StackMapTable", constPool);
      this.method = classMethod;
   }

   public void writeData(ByteArrayDataOutputStream stream) throws IOException {
      CodeAttribute ca = this.method.getCodeAttribute();
      LazySize size = stream.writeSize();
      stream.writeShort(ca.getStackFrames().size());
      int lastPos = -1;
      Iterator var5 = this.method.getCodeAttribute().getStackFrames().entrySet().iterator();

      while(true) {
         while(true) {
            while(var5.hasNext()) {
               Map.Entry entry = (Map.Entry)var5.next();
               int offset = (Integer)entry.getKey() - lastPos - 1;
               lastPos = (Integer)entry.getKey();
               StackFrame frame = (StackFrame)entry.getValue();
               if (frame.getType() != StackFrameType.SAME_FRAME && frame.getType() != StackFrameType.SAME_FRAME_EXTENDED) {
                  if (frame.getType() == StackFrameType.SAME_LOCALS_1_STACK && offset < 63) {
                     this.writeSameLocals1Stack(stream, offset, lastPos, frame);
                  } else {
                     this.writeFullFrame(stream, offset, lastPos, (StackFrame)entry.getValue());
                  }
               } else {
                  this.writeSameFrame(stream, offset, lastPos, frame);
               }
            }

            size.markEnd();
            return;
         }
      }
   }

   private void writeSameLocals1Stack(DataOutputStream dstream, int offset, int lastPos, StackFrame frame) throws IOException {
      dstream.writeByte(offset + 64);
      ((StackEntry)frame.getStackState().getContents().get(0)).write(dstream);
   }

   private void writeSameFrame(DataOutputStream dstream, int offset, int lastPos, StackFrame frame) throws IOException {
      if (offset > 63) {
         dstream.writeByte(251);
         dstream.writeShort(offset);
      } else {
         dstream.writeByte(offset);
      }

   }

   private void writeFullFrame(DataOutputStream dstream, int offset, int position, StackFrame value) throws IOException {
      dstream.writeByte(255);
      dstream.writeShort(offset);
      List realLocalVars = new ArrayList(value.getLocalVariableState().getContents().size());
      Iterator var6 = value.getLocalVariableState().getContents().iterator();

      StackEntry i;
      while(var6.hasNext()) {
         i = (StackEntry)var6.next();
         if (i.getType() != StackEntryType.TOP) {
            realLocalVars.add(i);
         }
      }

      dstream.writeShort(realLocalVars.size());
      var6 = realLocalVars.iterator();

      while(var6.hasNext()) {
         i = (StackEntry)var6.next();
         i.write(dstream);
      }

      List realStack = new ArrayList(value.getStackState().getContents().size());
      Iterator var10 = value.getStackState().getContents().iterator();

      StackEntry i;
      while(var10.hasNext()) {
         i = (StackEntry)var10.next();
         if (i.getType() != StackEntryType.TOP) {
            realStack.add(i);
         }
      }

      dstream.writeShort(realStack.size());
      var10 = realStack.iterator();

      while(var10.hasNext()) {
         i = (StackEntry)var10.next();
         i.write(dstream);
      }

   }
}
