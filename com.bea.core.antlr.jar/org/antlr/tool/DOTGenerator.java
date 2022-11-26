package org.antlr.tool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.antlr.Tool;
import org.antlr.analysis.DFA;
import org.antlr.analysis.DFAState;
import org.antlr.analysis.NFAConfiguration;
import org.antlr.analysis.NFAState;
import org.antlr.analysis.RuleClosureTransition;
import org.antlr.analysis.SemanticContext;
import org.antlr.analysis.State;
import org.antlr.analysis.Transition;
import org.antlr.misc.Utils;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

public class DOTGenerator {
   public static final boolean STRIP_NONREDUCED_STATES = false;
   protected String arrowhead = "normal";
   protected String rankdir = "LR";
   public static STGroup stlib = new STGroupFile("org/antlr/tool/templates/dot/dot.stg");
   protected Set markedStates = null;
   protected Grammar grammar;

   public DOTGenerator(Grammar grammar) {
      this.grammar = grammar;
   }

   public String getDOT(State startState) {
      if (startState == null) {
         return null;
      } else {
         this.markedStates = new HashSet();
         ST dot;
         if (startState instanceof DFAState) {
            dot = stlib.getInstanceOf("dfa");
            dot.add("startState", Utils.integer(startState.stateNumber));
            dot.add("useBox", Tool.internalOption_ShowNFAConfigsInDFA);
            this.walkCreatingDFADOT(dot, (DFAState)startState);
         } else {
            dot = stlib.getInstanceOf("nfa");
            dot.add("startState", Utils.integer(startState.stateNumber));
            this.walkRuleNFACreatingDOT(dot, startState);
         }

         dot.add("rankdir", this.rankdir);
         return dot.render();
      }
   }

   protected void walkCreatingDFADOT(ST dot, DFAState s) {
      if (!this.markedStates.contains(Utils.integer(s.stateNumber))) {
         this.markedStates.add(Utils.integer(s.stateNumber));
         ST st;
         if (s.isAcceptState()) {
            st = stlib.getInstanceOf("stopstate");
         } else {
            st = stlib.getInstanceOf("state");
         }

         st.add("name", this.getStateLabel(s));
         dot.add("states", st);

         for(int i = 0; i < s.getNumberOfTransitions(); ++i) {
            Transition edge = s.transition(i);
            st = stlib.getInstanceOf("edge");
            st.add("label", this.getEdgeLabel(edge));
            st.add("src", this.getStateLabel(s));
            st.add("target", this.getStateLabel(edge.target));
            st.add("arrowhead", this.arrowhead);
            dot.add("edges", st);
            this.walkCreatingDFADOT(dot, (DFAState)edge.target);
         }

      }
   }

   protected void walkRuleNFACreatingDOT(ST dot, State s) {
      if (!this.markedStates.contains(s)) {
         this.markedStates.add(s);
         ST stateST;
         if (s.isAcceptState()) {
            stateST = stlib.getInstanceOf("stopstate");
         } else {
            stateST = stlib.getInstanceOf("state");
         }

         stateST.add("name", this.getStateLabel(s));
         dot.add("states", stateST);
         if (!s.isAcceptState()) {
            if (((NFAState)s).isDecisionState()) {
               GrammarAST n = ((NFAState)s).associatedASTNode;
               if (n != null && n.getType() != 33) {
                  ST rankST = stlib.getInstanceOf("decision-rank");
                  NFAState alt = (NFAState)s;

                  while(alt != null) {
                     rankST.add("states", this.getStateLabel(alt));
                     if (alt.transition[1] != null) {
                        alt = (NFAState)alt.transition[1].target;
                     } else {
                        alt = null;
                     }
                  }

                  dot.add("decisionRanks", rankST);
               }
            }

            for(int i = 0; i < s.getNumberOfTransitions(); ++i) {
               Transition edge = s.transition(i);
               ST edgeST;
               if (edge instanceof RuleClosureTransition) {
                  RuleClosureTransition rr = (RuleClosureTransition)edge;
                  edgeST = stlib.getInstanceOf("edge");
                  if (rr.rule.grammar != this.grammar) {
                     edgeST.add("label", "<" + rr.rule.grammar.name + "." + rr.rule.name + ">");
                  } else {
                     edgeST.add("label", "<" + rr.rule.name + ">");
                  }

                  edgeST.add("src", this.getStateLabel(s));
                  edgeST.add("target", this.getStateLabel(rr.followState));
                  edgeST.add("arrowhead", this.arrowhead);
                  dot.add("edges", edgeST);
                  this.walkRuleNFACreatingDOT(dot, rr.followState);
               } else {
                  if (edge.isAction()) {
                     edgeST = stlib.getInstanceOf("action-edge");
                  } else if (edge.isEpsilon()) {
                     edgeST = stlib.getInstanceOf("epsilon-edge");
                  } else {
                     edgeST = stlib.getInstanceOf("edge");
                  }

                  edgeST.add("label", this.getEdgeLabel(edge));
                  edgeST.add("src", this.getStateLabel(s));
                  edgeST.add("target", this.getStateLabel(edge.target));
                  edgeST.add("arrowhead", this.arrowhead);
                  dot.add("edges", edgeST);
                  this.walkRuleNFACreatingDOT(dot, edge.target);
               }
            }

         }
      }
   }

   protected String getEdgeLabel(Transition edge) {
      String label = edge.label.toString(this.grammar);
      label = Utils.replace(label, "\\", "\\\\");
      label = Utils.replace(label, "\"", "\\\"");
      label = Utils.replace(label, "\n", "\\\\n");
      label = Utils.replace(label, "\r", "");
      if (label.equals("<EPSILON>")) {
         label = "e";
      }

      State target = edge.target;
      if (!edge.isSemanticPredicate() && target instanceof DFAState) {
         SemanticContext preds = ((DFAState)target).getGatedPredicatesInNFAConfigurations();
         if (preds != null) {
            String predsStr = "&&{" + preds.genExpr(this.grammar.generator, this.grammar.generator.getTemplates(), (DFA)null).render() + "}?";
            label = label + predsStr;
         }
      }

      return label;
   }

   protected String getStateLabel(State s) {
      if (s == null) {
         return "null";
      } else {
         String stateLabel = String.valueOf(s.stateNumber);
         if (s instanceof DFAState) {
            StringBuilder buf = new StringBuilder(250);
            buf.append('s');
            buf.append(s.stateNumber);
            if (Tool.internalOption_ShowNFAConfigsInDFA) {
               if (s instanceof DFAState && ((DFAState)s).abortedDueToRecursionOverflow) {
                  buf.append("\\n");
                  buf.append("abortedDueToRecursionOverflow");
               }

               Set alts = ((DFAState)s).getAltSet();
               if (alts != null) {
                  buf.append("\\n");
                  List altList = new ArrayList();
                  altList.addAll(alts);
                  Collections.sort(altList);
                  Set configurations = ((DFAState)s).nfaConfigurations;

                  for(int altIndex = 0; altIndex < altList.size(); ++altIndex) {
                     Integer altI = (Integer)altList.get(altIndex);
                     int alt = altI;
                     if (altIndex > 0) {
                        buf.append("\\n");
                     }

                     buf.append("alt");
                     buf.append(alt);
                     buf.append(':');
                     List configsInAlt = new ArrayList();
                     Iterator i$ = configurations.iterator();

                     while(i$.hasNext()) {
                        NFAConfiguration c = (NFAConfiguration)i$.next();
                        if (c.alt == alt) {
                           configsInAlt.add(c);
                        }
                     }

                     int n = 0;

                     for(int cIndex = 0; cIndex < configsInAlt.size(); ++cIndex) {
                        NFAConfiguration c = (NFAConfiguration)configsInAlt.get(cIndex);
                        ++n;
                        buf.append(c.toString(false));
                        if (cIndex + 1 < configsInAlt.size()) {
                           buf.append(", ");
                        }

                        if (n % 5 == 0 && configsInAlt.size() - cIndex > 3) {
                           buf.append("\\n");
                        }
                     }
                  }
               }
            }

            stateLabel = buf.toString();
         }

         if (s instanceof NFAState && ((NFAState)s).isDecisionState()) {
            stateLabel = stateLabel + ",d=" + ((NFAState)s).getDecisionNumber();
            if (((NFAState)s).endOfBlockStateNumber != -1) {
               stateLabel = stateLabel + ",eob=" + ((NFAState)s).endOfBlockStateNumber;
            }
         } else if (s instanceof NFAState && ((NFAState)s).endOfBlockStateNumber != -1) {
            NFAState n = (NFAState)s;
            stateLabel = stateLabel + ",eob=" + n.endOfBlockStateNumber;
         } else if (s instanceof DFAState && ((DFAState)s).isAcceptState()) {
            stateLabel = stateLabel + "=>" + ((DFAState)s).getUniquelyPredictedAlt();
         }

         return '"' + stateLabel + '"';
      }
   }

   public String getArrowheadType() {
      return this.arrowhead;
   }

   public void setArrowheadType(String arrowhead) {
      this.arrowhead = arrowhead;
   }

   public String getRankdir() {
      return this.rankdir;
   }

   public void setRankdir(String rankdir) {
      this.rankdir = rankdir;
   }
}
