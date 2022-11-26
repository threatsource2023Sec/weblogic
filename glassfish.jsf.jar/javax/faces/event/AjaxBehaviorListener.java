package javax.faces.event;

public interface AjaxBehaviorListener extends BehaviorListener {
   void processAjaxBehavior(AjaxBehaviorEvent var1) throws AbortProcessingException;
}
