package org.python.indexer.ast;

public abstract class GenericNodeVisitor extends DefaultNodeVisitor {
   public boolean dispatch(NNode n) {
      return this.traverseIntoNodes;
   }

   public boolean visit(NAlias n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NAssert n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NAssign n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NAttribute n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NAugAssign n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NBinOp n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NBlock n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NBoolOp n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NBreak n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NCall n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NClassDef n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NCompare n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NComprehension n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NContinue n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NDelete n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NDict n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NEllipsis n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NExceptHandler n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NExec n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NFor n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NFunctionDef n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NGeneratorExp n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NGlobal n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NIf n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NIfExp n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NImport n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NImportFrom n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NIndex n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NKeyword n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NLambda n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NList n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NListComp n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NModule n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NName n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NNum n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NPass n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NPlaceHolder n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NPrint n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NQname n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NRaise n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NRepr n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NReturn n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NExprStmt n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NSlice n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NStr n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NSubscript n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NTryExcept n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NTryFinally n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NTuple n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NUnaryOp n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NUrl n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NWhile n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NWith n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }

   public boolean visit(NYield n) {
      return this.traverseIntoNodes && this.dispatch(n);
   }
}
