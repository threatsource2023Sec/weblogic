package org.antlr.analysis;

import org.antlr.tool.ErrorManager;
import org.antlr.tool.GrammarAST;
import org.antlr.tool.Rule;

public class NFAState extends State {
   public static final int LOOPBACK = 1;
   public static final int BLOCK_START = 2;
   public static final int OPTIONAL_BLOCK_START = 3;
   public static final int BYPASS = 4;
   public static final int RIGHT_EDGE_OF_BLOCK = 5;
   public static final int MAX_TRANSITIONS = 2;
   int numTransitions = 0;
   public Transition[] transition = new Transition[2];
   public Label incidentEdgeLabel;
   public NFA nfa = null;
   protected int decisionNumber = 0;
   public int decisionStateType;
   public Rule enclosingRule;
   protected String description;
   public GrammarAST associatedASTNode;
   protected boolean EOTTargetState = false;
   public int endOfBlockStateNumber = -1;

   public NFAState(NFA nfa) {
      this.nfa = nfa;
   }

   public int getNumberOfTransitions() {
      return this.numTransitions;
   }

   public void addTransition(Transition e) {
      if (e == null) {
         throw new IllegalArgumentException("You can't add a null transition");
      } else if (this.numTransitions > this.transition.length) {
         throw new IllegalArgumentException("You can only have " + this.transition.length + " transitions");
      } else {
         if (e != null) {
            this.transition[this.numTransitions] = e;
            ++this.numTransitions;
            Label label = e.label;
            if (label.isAtom() || label.isSet()) {
               if (((NFAState)e.target).incidentEdgeLabel != null) {
                  ErrorManager.internalError("Clobbered incident edge");
               }

               ((NFAState)e.target).incidentEdgeLabel = e.label;
            }
         }

      }
   }

   public void setTransition0(Transition e) {
      if (e == null) {
         throw new IllegalArgumentException("You can't use a solitary null transition");
      } else {
         this.transition[0] = e;
         this.transition[1] = null;
         this.numTransitions = 1;
      }
   }

   public Transition transition(int i) {
      return this.transition[i];
   }

   public int translateDisplayAltToWalkAlt(int displayAlt) {
      if (this.decisionNumber != 0 && this.decisionStateType != 0) {
         int walkAlt = 0;
         int nAlts = this.nfa.grammar.getNumberOfAltsForDecisionNFA(this);
         switch (this.decisionStateType) {
            case 1:
               walkAlt = displayAlt % nAlts + 1;
               break;
            case 2:
            case 3:
               walkAlt = displayAlt;
               break;
            case 4:
               if (displayAlt == nAlts) {
                  walkAlt = 2;
               } else {
                  walkAlt = 1;
               }
         }

         return walkAlt;
      } else {
         return displayAlt;
      }
   }

   public void setDecisionASTNode(GrammarAST decisionASTNode) {
      decisionASTNode.setNFAStartState(this);
      this.associatedASTNode = decisionASTNode;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public int getDecisionNumber() {
      return this.decisionNumber;
   }

   public void setDecisionNumber(int decisionNumber) {
      this.decisionNumber = decisionNumber;
   }

   public boolean isEOTTargetState() {
      return this.EOTTargetState;
   }

   public void setEOTTargetState(boolean eot) {
      this.EOTTargetState = eot;
   }

   public boolean isDecisionState() {
      return this.decisionStateType > 0;
   }

   public String toString() {
      return String.valueOf(this.stateNumber);
   }
}
