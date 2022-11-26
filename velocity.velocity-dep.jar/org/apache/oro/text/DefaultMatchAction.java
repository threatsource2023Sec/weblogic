package org.apache.oro.text;

final class DefaultMatchAction implements MatchAction {
   public void processMatch(MatchActionInfo var1) {
      var1.output.println(var1.line);
   }
}
