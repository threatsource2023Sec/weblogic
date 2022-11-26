package antlr;

public interface LLkGrammarAnalyzer extends GrammarAnalyzer {
   boolean deterministic(AlternativeBlock var1);

   boolean deterministic(OneOrMoreBlock var1);

   boolean deterministic(ZeroOrMoreBlock var1);

   Lookahead FOLLOW(int var1, RuleEndElement var2);

   Lookahead look(int var1, ActionElement var2);

   Lookahead look(int var1, AlternativeBlock var2);

   Lookahead look(int var1, BlockEndElement var2);

   Lookahead look(int var1, CharLiteralElement var2);

   Lookahead look(int var1, CharRangeElement var2);

   Lookahead look(int var1, GrammarAtom var2);

   Lookahead look(int var1, OneOrMoreBlock var2);

   Lookahead look(int var1, RuleBlock var2);

   Lookahead look(int var1, RuleEndElement var2);

   Lookahead look(int var1, RuleRefElement var2);

   Lookahead look(int var1, StringLiteralElement var2);

   Lookahead look(int var1, SynPredBlock var2);

   Lookahead look(int var1, TokenRangeElement var2);

   Lookahead look(int var1, TreeElement var2);

   Lookahead look(int var1, WildcardElement var2);

   Lookahead look(int var1, ZeroOrMoreBlock var2);

   Lookahead look(int var1, String var2);

   void setGrammar(Grammar var1);

   boolean subruleCanBeInverted(AlternativeBlock var1, boolean var2);
}
