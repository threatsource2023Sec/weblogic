package weblogic.management.scripting.core.utils;

import java.io.OutputStream;
import java.io.Writer;
import weblogic.management.scripting.core.InformationCoreHandler;
import weblogic.management.scripting.utils.WLSTInterpreter;
import weblogic.management.scripting.utils.WLSTUtilWrapperFactory;

public class WLSTCoreUtilWrapper implements WLSTUtilWrapperFactory {
   private static final WLSTUtilWrapperFactory SINGLETON_FACTORY_ = new WLSTCoreUtilWrapper();

   public static WLSTUtilWrapperFactory getInstance() {
      return SINGLETON_FACTORY_;
   }

   public void setupOffline(WLSTInterpreter interp) {
      WLSTCoreUtil.setupOffline(interp);
   }

   public void println(String s) {
      WLSTCoreUtil.println(s, (Exception)null);
   }

   public void println(String s, Exception ex) {
      WLSTCoreUtil.println(s, ex);
   }

   public InformationCoreHandler getInfoHandler() {
      return WLSTCoreUtil.getInfoHandler();
   }

   public boolean usingCommand(String command) {
      return WLSTCoreUtil.usingCommand(command);
   }

   public boolean isEditSessionInProgress() {
      return WLSTCoreUtil.isEditSessionInProgress();
   }

   public void setErrOutputMedium(Writer writer) {
      WLSTCoreUtil.setErrOutputMedium(writer);
   }

   public void setErrOutputMedium(OutputStream stream) {
      WLSTCoreUtil.setErrOutputMedium(stream);
   }

   public void setStdOutputMedium(Writer writer) {
      WLSTCoreUtil.setStdOutputMedium(writer);
   }

   public void setStdOutputMedium(OutputStream stream) {
      WLSTCoreUtil.setStdOutputMedium(stream);
   }

   public void setlogToStandardOut(boolean value) {
      WLSTCoreUtil.setlogToStandardOut(value);
   }
}
