package weblogic.utils.classfile;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.PrintStream;
import weblogic.utils.classfile.cp.ConstantPool;
import weblogic.utils.classfile.ops.BranchOp;
import weblogic.utils.classfile.ops.Resolvable;

public class Bytecodes {
   private static final boolean DEBUG = false;
   private Node firstNode;
   private Node curNode;
   int code_length;
   private Op[] pc_to_op;
   CodeAttribute code_attribute;
   ConstantPool constant_pool;

   public Bytecodes(CodeAttribute code_attribute, ConstantPool constant_pool) {
      this.code_attribute = code_attribute;
      this.constant_pool = constant_pool;
   }

   public void add(Op op) {
      Node n = new Node(op, (Node)null);
      if (this.firstNode == null) {
         this.firstNode = this.curNode = n;
      } else {
         this.curNode.next = n;
         this.curNode = n;
      }

   }

   private void updateLength() {
      this.code_length = 0;
      if (this.firstNode != null) {
         for(Node n = this.firstNode; n != null; n = n.next) {
            this.code_length += n.op.length();
         }

      }
   }

   public int getCodeLength() {
      return this.code_length;
   }

   public void makeMaps() {
      this.updateLength();
      this.pc_to_op = new Op[this.code_length];
      if (this.firstNode != null) {
         int pc = 0;

         for(Node n = this.firstNode; n != null; n = n.next) {
            Op op = n.op;
            op.setPC(pc);
            if (!(op instanceof Label)) {
               this.pc_to_op[pc] = op;
               pc += op.length();
            }
         }

      }
   }

   public Op opAtPC(int start_pc) throws BadBytecodesException {
      if (this.pc_to_op == null) {
         this.makeMaps();
      }

      try {
         Op op = this.pc_to_op[start_pc];
         if (op != null) {
            return op;
         } else {
            System.err.println("pc_to_op.length = " + this.pc_to_op.length);
            int i = 0;

            for(int len = this.pc_to_op.length; i < len; ++i) {
               System.err.println("pc_to_op[" + i + "] = " + this.pc_to_op[i]);
            }

            throw new BadBytecodesException("No op at pc = " + start_pc + " code_length = " + this.code_length);
         }
      } catch (ArrayIndexOutOfBoundsException var5) {
         return null;
      }
   }

   public int pcForOp(Op op) {
      return op.getPC();
   }

   public void read(DataInput in) throws IOException, BadBytecodesException {
      this.code_length = in.readInt();
      int i = 0;

      try {
         this.firstNode = null;

         Op op;
         for(this.curNode = null; i < this.code_length; i += op.length()) {
            op = Op.getOp(in, this, i);
            this.add(op);
         }

      } catch (ArrayIndexOutOfBoundsException var4) {
         throw new IOException("Ran out of bytecodes: i = " + i + "\t" + var4);
      }
   }

   public void write(DataOutput out) throws IOException, BadBytecodesException {
      this.makeMaps();
      this.resolveLabels();

      while(this.resolveBranchTargets()) {
         this.makeMaps();
      }

      DataBuffer buf = new DataBuffer();
      if (this.firstNode != null) {
         for(Node n = this.firstNode; n != null; n = n.next) {
            n.op.write(buf);
         }
      }

      buf.writeWithLenAsInt(out);
      this.makeMaps();
   }

   public boolean resolveBranchTargets() throws BadBytecodesException {
      boolean resolved = false;
      if (this.firstNode != null) {
         for(Node n = this.firstNode; n != null; n = n.next) {
            if (n.op instanceof Resolvable) {
               resolved |= ((Resolvable)n.op).resolve(this);
            }
         }
      }

      return resolved;
   }

   private void resolveLabels() throws BadBytecodesException {
      if (this.firstNode != null) {
         for(Node n = this.firstNode; n != null; n = n.next) {
            if (n.op instanceof BranchOp) {
               BranchOp bo = (BranchOp)n.op;
               bo.target = this.opAtPC(this.pcForOp(bo.target));
            }
         }
      }

   }

   public void dump(PrintStream out) throws BadBytecodesException {
      this.makeMaps();
      this.resolveLabels();
      this.resolveBranchTargets();
   }

   private String to4(int i) {
      StringBuffer sb = (new StringBuffer()).append(i);

      while(sb.length() < 4) {
         sb.insert(0, ' ');
      }

      return sb.toString();
   }

   private static void say(String s) {
      System.out.println(s);
   }

   private static final class Node {
      final Op op;
      Node next;

      Node(Op op, Node next) {
         this.op = op;
         this.next = next;
      }
   }
}
