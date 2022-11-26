package weblogic.diagnostics.context;

import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface Correlation extends Serializable, DiagnosticContextConstants {
   int DEFAULT_LOG_LEVEL = -1;

   String getECID();

   String getRID();

   int[] getRIDComponents();

   int[] newChildRIDComponents();

   int getRIDChildCount();

   int incAndGetChildRIDCount();

   long getDyeVector();

   void setDyeVector(long var1);

   Map values();

   Object setDMSObject(Object var1);

   Object getDMSObject();

   Level getLogLevel();

   void setLogLevel(Level var1);

   String getPayload();

   void setPayload(String var1);

   String getLegacyDCID();

   void setDye(int var1, boolean var2) throws InvalidDyeException;

   boolean isDyedWith(int var1) throws InvalidDyeException;

   boolean getInheritable();

   void setInheritable(boolean var1);
}
