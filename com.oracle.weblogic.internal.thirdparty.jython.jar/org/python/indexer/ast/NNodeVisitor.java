package org.python.indexer.ast;

public interface NNodeVisitor {
   boolean visit(NAlias var1);

   boolean visit(NAssert var1);

   boolean visit(NAssign var1);

   boolean visit(NAttribute var1);

   boolean visit(NAugAssign var1);

   boolean visit(NBinOp var1);

   boolean visit(NBlock var1);

   boolean visit(NBoolOp var1);

   boolean visit(NBreak var1);

   boolean visit(NCall var1);

   boolean visit(NClassDef var1);

   boolean visit(NCompare var1);

   boolean visit(NComprehension var1);

   boolean visit(NContinue var1);

   boolean visit(NDelete var1);

   boolean visit(NDict var1);

   boolean visit(NEllipsis var1);

   boolean visit(NExceptHandler var1);

   boolean visit(NExec var1);

   boolean visit(NFor var1);

   boolean visit(NFunctionDef var1);

   boolean visit(NGeneratorExp var1);

   boolean visit(NGlobal var1);

   boolean visit(NIf var1);

   boolean visit(NIfExp var1);

   boolean visit(NImport var1);

   boolean visit(NImportFrom var1);

   boolean visit(NIndex var1);

   boolean visit(NKeyword var1);

   boolean visit(NLambda var1);

   boolean visit(NList var1);

   boolean visit(NListComp var1);

   boolean visit(NModule var1);

   boolean visit(NName var1);

   boolean visit(NNum var1);

   boolean visit(NPass var1);

   boolean visit(NPlaceHolder var1);

   boolean visit(NPrint var1);

   boolean visit(NQname var1);

   boolean visit(NRaise var1);

   boolean visit(NRepr var1);

   boolean visit(NReturn var1);

   boolean visit(NExprStmt var1);

   boolean visit(NSlice var1);

   boolean visit(NStr var1);

   boolean visit(NSubscript var1);

   boolean visit(NTryExcept var1);

   boolean visit(NTryFinally var1);

   boolean visit(NTuple var1);

   boolean visit(NUnaryOp var1);

   boolean visit(NUrl var1);

   boolean visit(NWhile var1);

   boolean visit(NWith var1);

   boolean visit(NYield var1);

   public static final class StopIterationException extends RuntimeException {
   }
}
