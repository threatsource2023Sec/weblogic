package antlr;

public class ActionTransInfo {
   public boolean assignToRoot = false;
   public String refRuleRoot = null;
   public String followSetName = null;

   public String toString() {
      return "assignToRoot:" + this.assignToRoot + ", refRuleRoot:" + this.refRuleRoot + ", FOLLOW Set:" + this.followSetName;
   }
}
