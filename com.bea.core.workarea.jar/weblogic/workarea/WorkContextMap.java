package weblogic.workarea;

import java.util.Iterator;

public interface WorkContextMap {
   WorkContext put(String var1, WorkContext var2) throws PropertyReadOnlyException;

   WorkContext put(String var1, WorkContext var2, int var3) throws PropertyReadOnlyException;

   WorkContext get(String var1);

   int getPropagationMode(String var1);

   boolean isPropagationModePresent(int var1);

   WorkContext remove(String var1) throws NoWorkContextException, PropertyReadOnlyException;

   boolean isEmpty();

   Iterator iterator();

   Iterator keys();
}
