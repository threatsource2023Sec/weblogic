package com.sun.faces.facelets.tag.jstl.core;

import com.sun.faces.facelets.tag.IterationStatus;
import javax.servlet.jsp.jstl.core.LoopTagStatus;

public class JstlIterationStatus extends IterationStatus implements LoopTagStatus {
   private static final long serialVersionUID = 5417430073472479654L;

   public JstlIterationStatus(boolean first, boolean last, int index, Integer begin, Integer end, Integer step) {
      super(first, last, index, begin, end, step);
   }

   public JstlIterationStatus(boolean first, boolean last, int index, Integer begin, Integer end, Integer step, Object current, int iterationCount) {
      super(first, last, index, begin, end, step, current, iterationCount);
   }
}
