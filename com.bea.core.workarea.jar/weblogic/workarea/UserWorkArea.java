package weblogic.workarea;

import java.io.Serializable;

public interface UserWorkArea extends Serializable {
   void begin(String var1);

   void begin(String var1, int var2);

   void complete() throws NoWorkContextException;

   void complete(String var1) throws NoWorkContextException;

   String getName();

   String[] getKeys();

   void setProperty(String var1, WorkContext var2) throws NoWorkContextException, PropertyReadOnlyException;

   void setProperty(String var1, WorkContext var2, int var3) throws NoWorkContextException, PropertyReadOnlyException;

   WorkContext getProperty(String var1);

   int getMode(String var1);

   void removeProperty(String var1) throws NoWorkContextException;
}
