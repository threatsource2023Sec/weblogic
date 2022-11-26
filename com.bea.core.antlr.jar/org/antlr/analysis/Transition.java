package org.antlr.analysis;

public class Transition implements Comparable {
   public Label label;
   public State target;

   public Transition(Label label, State target) {
      this.label = label;
      this.target = target;
   }

   public Transition(int label, State target) {
      this.label = new Label(label);
      this.target = target;
   }

   public boolean isEpsilon() {
      return this.label.isEpsilon();
   }

   public boolean isAction() {
      return this.label.isAction();
   }

   public boolean isSemanticPredicate() {
      return this.label.isSemanticPredicate();
   }

   public int hashCode() {
      return this.label.hashCode() + this.target.stateNumber;
   }

   public boolean equals(Object o) {
      Transition other = (Transition)o;
      return this.label.equals(other.label) && this.target.equals(other.target);
   }

   public int compareTo(Transition other) {
      return this.label.compareTo(other.label);
   }

   public String toString() {
      return this.label + "->" + this.target.stateNumber;
   }
}
