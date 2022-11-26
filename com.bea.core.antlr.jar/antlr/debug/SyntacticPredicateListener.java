package antlr.debug;

public interface SyntacticPredicateListener extends ListenerBase {
   void syntacticPredicateFailed(SyntacticPredicateEvent var1);

   void syntacticPredicateStarted(SyntacticPredicateEvent var1);

   void syntacticPredicateSucceeded(SyntacticPredicateEvent var1);
}
