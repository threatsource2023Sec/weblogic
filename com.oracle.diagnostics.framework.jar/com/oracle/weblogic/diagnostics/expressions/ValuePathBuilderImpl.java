package com.oracle.weblogic.diagnostics.expressions;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

class ValuePathBuilderImpl implements PathBuilder {
   private Deque termPath;

   ValuePathBuilderImpl(Deque chain) {
      this.termPath = chain;
   }

   public String getPath() {
      return this.buildPathToTerm(new ArrayDeque(this.termPath));
   }

   private String buildPathToTerm(Deque termPath) {
      Term lastTerm = null;
      List resolvedPath = new ArrayList();

      while(!termPath.isEmpty()) {
         lastTerm = (Term)termPath.pop();
         if (lastTerm.isTraceable()) {
            break;
         }

         resolvedPath.add(0, lastTerm);
      }

      String path = this.buildPathString(resolvedPath);
      return path;
   }

   private String buildPathString(List resolvedPath) {
      StringBuffer path = new StringBuffer();
      Iterator it = resolvedPath.iterator();

      while(it.hasNext()) {
         path.append(it.next());
         if (it.hasNext()) {
            path.append('.');
         }
      }

      return path.toString();
   }
}
