package weblogic.management.scripting.utils;

import java.io.OutputStream;
import java.io.Writer;
import weblogic.management.scripting.core.InformationCoreHandler;

public class WLSTUtilWrapper implements WLSTUtilWrapperFactory {
   private static final WLSTUtilWrapperFactory SINGLETON_FACTORY_ = new WLSTUtilWrapper();

   public static WLSTUtilWrapperFactory getInstance() {
      return SINGLETON_FACTORY_;
   }

   public void setupOffline(WLSTInterpreter interp) {
      WLSTUtil.setupOffline(interp);
   }

   public void println(String s) {
      this.println(s, (Exception)null);
   }

   public void println(String s, Exception ex) {
      WLSTUtil.println(s, ex);
   }

   public InformationCoreHandler getInfoHandler() {
      return WLSTUtil.getInfoHandler();
   }

   public boolean usingCommand(String command) {
      return WLSTUtil.usingCommand(command);
   }

   public boolean isEditSessionInProgress() {
      return WLSTUtil.isEditSessionInProgress();
   }

   public void setErrOutputMedium(Writer writer) {
      WLSTUtil.setErrOutputMedium(writer);
   }

   public void setErrOutputMedium(OutputStream stream) {
      WLSTUtil.setErrOutputMedium(stream);
   }

   public void setStdOutputMedium(Writer writer) {
      WLSTUtil.setStdOutputMedium(writer);
   }

   public void setStdOutputMedium(OutputStream stream) {
      WLSTUtil.setStdOutputMedium(stream);
   }

   public void setlogToStandardOut(boolean value) {
      WLSTUtil.setlogToStandardOut(value);
   }
}
