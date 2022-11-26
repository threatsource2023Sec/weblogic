package org.python.modules.sre;

import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.Traverseproc;
import org.python.core.Visitproc;

public class ScannerObject extends PyObject implements Traverseproc {
   public PatternObject pattern;
   PyString string;
   SRE_STATE state;

   public MatchObject match() {
      this.state.state_reset();
      this.state.ptr = this.state.start;
      int status = this.state.SRE_MATCH(this.pattern.code, 0, 1);
      MatchObject match = this.pattern._pattern_new_match(this.state, this.string, status);
      if (status != 0 && this.state.ptr != this.state.start) {
         this.state.start = this.state.ptr;
      } else {
         this.state.start = this.state.ptr + 1;
      }

      return match;
   }

   public MatchObject search() {
      this.state.state_reset();
      this.state.ptr = this.state.start;
      int status = this.state.SRE_SEARCH(this.pattern.code, 0);
      MatchObject match = this.pattern._pattern_new_match(this.state, this.string, status);
      if (status != 0 && this.state.ptr != this.state.start) {
         this.state.start = this.state.ptr;
      } else {
         this.state.start = this.state.ptr + 1;
      }

      return match;
   }

   public int traverse(Visitproc visit, Object arg) {
      if (this.pattern != null) {
         int retVal = visit.visit(this.pattern, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      return this.string != null ? visit.visit(this.string, arg) : 0;
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && (ob == this.pattern || ob == this.string);
   }
}
