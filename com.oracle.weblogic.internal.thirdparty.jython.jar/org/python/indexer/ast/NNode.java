package org.python.indexer.ast;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import org.python.indexer.Indexer;
import org.python.indexer.IndexingException;
import org.python.indexer.Scope;
import org.python.indexer.types.NType;
import org.python.indexer.types.NUnionType;
import org.python.indexer.types.NUnknownType;

public abstract class NNode implements Serializable {
   static final long serialVersionUID = 3682719481356964898L;
   private int start = 0;
   private int end = 1;
   protected NNode parent = null;
   private transient NType type;

   public NNode() {
      this.type = Indexer.idx.builtins.None;
   }

   public NNode(int start, int end) {
      this.type = Indexer.idx.builtins.None;
      this.setStart(start);
      this.setEnd(end);
   }

   public void setParent(NNode parent) {
      this.parent = parent;
   }

   public NNode getParent() {
      return this.parent;
   }

   public NNode getAstRoot() {
      return this.parent == null ? this : this.parent.getAstRoot();
   }

   public void setStart(int start) {
      this.start = start;
   }

   public void setEnd(int end) {
      this.end = end;
   }

   public int start() {
      return this.start;
   }

   public int end() {
      return this.end;
   }

   public int length() {
      return this.end - this.start;
   }

   public Scope getTable() {
      return this.getType().getTable();
   }

   public NType getType() {
      if (this.type == null) {
         this.type = Indexer.idx.builtins.None;
      }

      return this.type;
   }

   public NType setType(NType newType) {
      if (newType == null) {
         throw new IllegalArgumentException();
      } else {
         return this.type = newType;
      }
   }

   public NType addType(NType newType) {
      if (newType == null) {
         throw new IllegalArgumentException();
      } else {
         return this.type = NUnionType.union(this.getType(), newType);
      }
   }

   public boolean bindsName() {
      return false;
   }

   protected void bindNames(Scope s) throws Exception {
      throw new UnsupportedOperationException("Not a name-binding node type");
   }

   public String getFile() {
      return this.parent != null ? this.parent.getFile() : null;
   }

   public void addChildren(NNode... nodes) {
      if (nodes != null) {
         NNode[] var2 = nodes;
         int var3 = nodes.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            NNode n = var2[var4];
            if (n != null) {
               n.setParent(this);
            }
         }
      }

   }

   public void addChildren(List nodes) {
      if (nodes != null) {
         Iterator var2 = nodes.iterator();

         while(var2.hasNext()) {
            NNode n = (NNode)var2.next();
            if (n != null) {
               n.setParent(this);
            }
         }
      }

   }

   private static NType handleExceptionInResolve(NNode n, Throwable t) {
      Indexer.idx.handleException("Unable to resolve: " + n + " in " + n.getFile(), t);
      return new NUnknownType();
   }

   public static NType resolveExpr(NNode n, Scope s) {
      if (n == null) {
         return new NUnknownType();
      } else {
         try {
            NType result = n.resolve(s);
            if (result == null) {
               Indexer.idx.warn(n + " resolved to a null type");
               return n.setType(new NUnknownType());
            } else {
               return result;
            }
         } catch (IndexingException var4) {
            throw var4;
         } catch (Exception var5) {
            return handleExceptionInResolve(n, var5);
         } catch (StackOverflowError var6) {
            String msg = "Unable to resolve: " + n + " in " + n.getFile() + " (stack overflow)";
            Indexer.idx.warn(msg);
            return handleExceptionInResolve(n, var6);
         }
      }
   }

   public NType resolve(Scope s) throws Exception {
      return this.getType();
   }

   public boolean isCall() {
      return this instanceof NCall;
   }

   public boolean isModule() {
      return this instanceof NModule;
   }

   public boolean isClassDef() {
      return false;
   }

   public boolean isFunctionDef() {
      return false;
   }

   public boolean isLambda() {
      return false;
   }

   public boolean isName() {
      return this instanceof NName;
   }

   protected void visitNode(NNode n, NNodeVisitor v) {
      if (n != null) {
         n.visit(v);
      }

   }

   protected void visitNodeList(List nodes, NNodeVisitor v) {
      if (nodes != null) {
         Iterator var3 = nodes.iterator();

         while(var3.hasNext()) {
            NNode n = (NNode)var3.next();
            if (n != null) {
               n.visit(v);
            }
         }
      }

   }

   public abstract void visit(NNodeVisitor var1);

   public Scope getEnclosingNamespace() {
      if (this.parent != null && !this.isModule()) {
         NNode up = this;

         do {
            if ((up = up.parent) == null) {
               return Indexer.idx.globaltable;
            }
         } while(!up.isFunctionDef() && !up.isClassDef() && !up.isModule());

         NType type = up.getType();
         return type != null && type.getTable() != null ? type.getTable() : Indexer.idx.globaltable;
      } else {
         return Indexer.idx.globaltable;
      }
   }

   protected void addWarning(String msg) {
      Indexer.idx.putProblem(this, msg);
   }

   protected void addWarning(NNode loc, String msg) {
      Indexer.idx.putProblem(loc, msg);
   }

   protected void addError(String msg) {
      Indexer.idx.putProblem(this, msg);
   }

   protected void addError(NNode loc, String msg) {
      Indexer.idx.putProblem(loc, msg);
   }

   protected NType resolveListAsUnion(List nodes, Scope s) {
      if (nodes != null && !nodes.isEmpty()) {
         NType result = null;
         Iterator var4 = nodes.iterator();

         while(var4.hasNext()) {
            NNode node = (NNode)var4.next();
            NType nodeType = resolveExpr(node, s);
            if (result == null) {
               result = nodeType;
            } else {
               result = NUnionType.union(result, nodeType);
            }
         }

         return result;
      } else {
         return new NUnknownType();
      }
   }

   protected void resolveList(List nodes, Scope s) {
      if (nodes != null) {
         Iterator var3 = nodes.iterator();

         while(var3.hasNext()) {
            NNode n = (NNode)var3.next();
            resolveExpr(n, s);
         }
      }

   }

   public NNode getDeepestNodeAtOffset(int sourceOffset) {
      NNode ast = this.getAstRoot();
      DeepestOverlappingNodeFinder finder = new DeepestOverlappingNodeFinder(sourceOffset);

      try {
         ast.visit(finder);
      } catch (NNodeVisitor.StopIterationException var5) {
      }

      return finder.getNode();
   }

   static class DeepestOverlappingNodeFinder extends GenericNodeVisitor {
      private int offset;
      private NNode deepest;

      public DeepestOverlappingNodeFinder(int offset) {
         this.offset = offset;
      }

      public NNode getNode() {
         return this.deepest;
      }

      public boolean dispatch(NNode node) {
         if (this.offset > node.end) {
            return false;
         } else if (this.offset >= node.start && this.offset <= node.end) {
            this.deepest = node;
            return true;
         } else {
            throw new NNodeVisitor.StopIterationException();
         }
      }
   }
}
