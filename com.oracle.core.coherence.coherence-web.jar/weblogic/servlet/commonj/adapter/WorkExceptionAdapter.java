package weblogic.servlet.commonj.adapter;

import com.tangosol.coherence.servlet.commonj.WorkException;

public class WorkExceptionAdapter extends WorkException {
   private static final long serialVersionUID = 1L;

   public WorkExceptionAdapter(commonj.work.WorkException t) {
      super(t.getMessage(), t.getCause());
   }
}
