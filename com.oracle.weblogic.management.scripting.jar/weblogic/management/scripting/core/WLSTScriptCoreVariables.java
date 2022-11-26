package weblogic.management.scripting.core;

import org.python.core.PyObject;
import weblogic.management.scripting.core.utils.ScriptCommandsCoreHelp;
import weblogic.management.scripting.utils.ErrorInformation;

public class WLSTScriptCoreVariables extends PyObject {
   public Object errOutputMedium = null;
   public Object stdOutputMedium = null;
   public ErrorInformation errorInfo = null;
   public NodeManagerCoreService nmService = null;
   public InformationCoreHandler infoHandler = null;
   public ExceptionCoreHandler exceptionHandler = null;
   public String commandType = "";
   public String errorMsg = null;
   public String outputFile = null;
   public String theErrorMessage = "";
   public String prompt = "";
   public String connected = "false";
   public String domainName = "";
   public String domainType = "serverConfig";
   public boolean debug = false;
   public boolean logToStandardOut = true;
   public boolean redirecting = false;
   public boolean hideDumpStack = false;
   public ScriptCommandsCoreHelp scriptCoreCmdHelp = null;
   public Throwable stackTrace = null;
   public String timeAtError = "";
   public boolean recording = false;
   public boolean isNamedEditSessionAvailable = true;
}
