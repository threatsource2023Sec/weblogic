package weblogic.management.scripting.utils;

import java.io.OutputStream;
import java.io.Writer;
import weblogic.management.scripting.core.InformationCoreHandler;

public interface WLSTUtilWrapperFactory {
   void setupOffline(WLSTInterpreter var1);

   InformationCoreHandler getInfoHandler();

   boolean usingCommand(String var1);

   boolean isEditSessionInProgress();

   void println(String var1);

   void println(String var1, Exception var2);

   void setErrOutputMedium(Writer var1);

   void setErrOutputMedium(OutputStream var1);

   void setStdOutputMedium(Writer var1);

   void setStdOutputMedium(OutputStream var1);

   void setlogToStandardOut(boolean var1);
}
