package org.glassfish.tyrus.core.uri;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.tyrus.core.DebugContext;

class MatchComparator implements Comparator, Serializable {
   private final transient Logger LOGGER = Logger.getLogger(MatchComparator.class.getName());
   private final transient DebugContext debugContext;

   MatchComparator(DebugContext debugContext) {
      this.debugContext = debugContext;
   }

   public int compare(Match m1, Match m2) {
      this.debugContext.appendTraceMessage(this.LOGGER, Level.FINER, DebugContext.Type.MESSAGE_IN, "Choosing better match from ", m1, " and ", m2);
      boolean m1exact = m1.isExact();
      boolean m2exact = m2.isExact();
      if (m1exact) {
         if (m2exact) {
            this.debugContext.appendTraceMessage(this.LOGGER, Level.FINER, DebugContext.Type.MESSAGE_IN, "Both ", m1, " and ", m2, " are exact matches");
            return 0;
         } else {
            this.debugContext.appendTraceMessage(this.LOGGER, Level.FINER, DebugContext.Type.MESSAGE_IN, m1, " is an exact match");
            return -1;
         }
      } else if (m2exact) {
         this.debugContext.appendTraceMessage(this.LOGGER, Level.FINER, DebugContext.Type.MESSAGE_IN, m2, " is an exact match");
         return 1;
      } else {
         List m1Indices = m1.getVariableSegmentIndices();
         List m2Indices = m2.getVariableSegmentIndices();

         for(int i = 0; i < Math.max(m1Indices.size(), m2Indices.size()); ++i) {
            if (i > m2Indices.size() - 1) {
               this.debugContext.appendTraceMessage(this.LOGGER, Level.FINER, DebugContext.Type.MESSAGE_IN, m2, " is a  better match, because ", m1, " has more variables");
               return 1;
            }

            if (i > m1Indices.size() - 1) {
               this.debugContext.appendTraceMessage(this.LOGGER, Level.FINER, DebugContext.Type.MESSAGE_IN, m1, " is a  better match, because ", m2, " has more variables");
               return -1;
            }

            int m1Index = (Integer)m1Indices.get(i);
            int m2Index = (Integer)m2Indices.get(i);
            if (m1Index > m2Index) {
               this.debugContext.appendTraceMessage(this.LOGGER, Level.FINER, DebugContext.Type.MESSAGE_IN, m1, " is a  better match, because it has longer exact path");
               return -1;
            }

            if (m2Index > m1Index) {
               this.debugContext.appendTraceMessage(this.LOGGER, Level.FINER, DebugContext.Type.MESSAGE_IN, m2, " is a  better match, because it has longer exact path");
               return 1;
            }
         }

         return 0;
      }
   }
}
