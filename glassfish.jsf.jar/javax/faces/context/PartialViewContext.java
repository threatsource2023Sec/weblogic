package javax.faces.context;

import java.util.Collection;
import java.util.List;
import javax.faces.event.PhaseId;

public abstract class PartialViewContext {
   public static final String PARTIAL_EVENT_PARAM_NAME = "javax.faces.partial.event";
   public static final String PARTIAL_RENDER_PARAM_NAME = "javax.faces.partial.render";
   public static final String PARTIAL_EXECUTE_PARAM_NAME = "javax.faces.partial.execute";
   public static final String RESET_VALUES_PARAM_NAME = "javax.faces.partial.resetValues";
   public static final String ALL_PARTIAL_PHASE_CLIENT_IDS = "@all";

   public abstract Collection getExecuteIds();

   public abstract Collection getRenderIds();

   public abstract List getEvalScripts();

   public abstract PartialResponseWriter getPartialResponseWriter();

   public abstract boolean isAjaxRequest();

   public abstract boolean isPartialRequest();

   public abstract boolean isExecuteAll();

   public abstract boolean isRenderAll();

   public boolean isResetValues() {
      return false;
   }

   public abstract void setRenderAll(boolean var1);

   public abstract void setPartialRequest(boolean var1);

   public abstract void release();

   public abstract void processPartial(PhaseId var1);
}
