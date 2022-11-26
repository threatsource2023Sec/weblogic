package antlr.debug;

public class ParserReporter extends Tracer implements ParserListener {
   public void parserConsume(ParserTokenEvent var1) {
      System.out.println(this.indent + var1);
   }

   public void parserLA(ParserTokenEvent var1) {
      System.out.println(this.indent + var1);
   }

   public void parserMatch(ParserMatchEvent var1) {
      System.out.println(this.indent + var1);
   }

   public void parserMatchNot(ParserMatchEvent var1) {
      System.out.println(this.indent + var1);
   }

   public void parserMismatch(ParserMatchEvent var1) {
      System.out.println(this.indent + var1);
   }

   public void parserMismatchNot(ParserMatchEvent var1) {
      System.out.println(this.indent + var1);
   }

   public void reportError(MessageEvent var1) {
      System.out.println(this.indent + var1);
   }

   public void reportWarning(MessageEvent var1) {
      System.out.println(this.indent + var1);
   }

   public void semanticPredicateEvaluated(SemanticPredicateEvent var1) {
      System.out.println(this.indent + var1);
   }

   public void syntacticPredicateFailed(SyntacticPredicateEvent var1) {
      System.out.println(this.indent + var1);
   }

   public void syntacticPredicateStarted(SyntacticPredicateEvent var1) {
      System.out.println(this.indent + var1);
   }

   public void syntacticPredicateSucceeded(SyntacticPredicateEvent var1) {
      System.out.println(this.indent + var1);
   }
}
