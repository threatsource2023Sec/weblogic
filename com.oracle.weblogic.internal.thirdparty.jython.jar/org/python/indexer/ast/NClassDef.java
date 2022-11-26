package org.python.indexer.ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.python.indexer.Builtins;
import org.python.indexer.Indexer;
import org.python.indexer.NBinding;
import org.python.indexer.Scope;
import org.python.indexer.types.NClassType;
import org.python.indexer.types.NDictType;
import org.python.indexer.types.NTupleType;
import org.python.indexer.types.NType;
import org.python.indexer.types.NUnknownType;

public class NClassDef extends NNode {
   static final long serialVersionUID = 7513873538009667540L;
   public NName name;
   public List bases;
   public NBody body;

   public NClassDef(NName name, List bases, NBlock body) {
      this(name, bases, body, 0, 1);
   }

   public NClassDef(NName name, List bases, NBlock body, int start, int end) {
      super(start, end);
      this.name = name;
      this.bases = bases;
      this.body = new NBody(body);
      this.addChildren(new NNode[]{name, this.body});
      this.addChildren(bases);
   }

   public boolean isClassDef() {
      return true;
   }

   public boolean bindsName() {
      return true;
   }

   protected void bindNames(Scope s) throws Exception {
      Scope container = s.getScopeSymtab();
      this.setType(new NClassType(this.name.id, container));
      NType existing = container.lookupType(this.name.id);
      if (existing == null || !existing.isClassType()) {
         NameBinder.make(NBinding.Kind.CLASS).bind(container, (NNode)this.name, this.getType());
      }
   }

   public NType resolve(Scope s) throws Exception {
      NClassType thisType = this.getType().asClassType();
      List baseTypes = new ArrayList();

      NType baseType;
      for(Iterator var4 = this.bases.iterator(); var4.hasNext(); baseTypes.add(baseType)) {
         NNode base = (NNode)var4.next();
         baseType = resolveExpr(base, s);
         if (baseType.isClassType()) {
            thisType.addSuper(baseType);
         }
      }

      Builtins builtins = Indexer.idx.builtins;
      this.addSpecialAttribute("__bases__", new NTupleType(baseTypes));
      this.addSpecialAttribute("__name__", builtins.BaseStr);
      this.addSpecialAttribute("__module__", builtins.BaseStr);
      this.addSpecialAttribute("__doc__", builtins.BaseStr);
      this.addSpecialAttribute("__dict__", new NDictType(builtins.BaseStr, new NUnknownType()));
      resolveExpr(this.body, this.getTable());
      return this.getType();
   }

   private void addSpecialAttribute(String name, NType proptype) {
      NBinding b = this.getTable().update(name, (NNode)Builtins.newTutUrl("classes.html"), proptype, NBinding.Kind.ATTRIBUTE);
      b.markSynthetic();
      b.markStatic();
      b.markReadOnly();
   }

   public String toString() {
      return "<ClassDef:" + this.name.id + ":" + this.start() + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNode(this.name, v);
         this.visitNodeList(this.bases, v);
         this.visitNode(this.body, v);
      }

   }
}
