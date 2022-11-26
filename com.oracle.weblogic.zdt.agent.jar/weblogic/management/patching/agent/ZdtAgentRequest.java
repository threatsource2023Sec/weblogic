package weblogic.management.patching.agent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ZdtAgentRequest {
   private Map requestParameters = new HashMap();
   private ZdtAgentAction action;

   public ZdtAgentRequest(String[] command) {
      for(int i = 0; i < command.length - 1; i += 2) {
         this.requestParameters.put(command[i], command[i + 1]);
         ZdtAgentParam incomingParam = ZdtAgentParam.get(command[i]);
         if (incomingParam == null) {
            throw new IllegalArgumentException("TODO: Unrecognized request parameter: " + command[i]);
         }

         if (incomingParam.equals(ZdtAgentParam.ACTION_PARAM)) {
            this.action = ZdtAgentAction.get(command[i + 1]);
         }
      }

      if (this.action == null) {
         throw new IllegalArgumentException("TODO: action must be specified");
      }
   }

   public ZdtAgentRequest(Map requestParameters) {
      this.requestParameters = requestParameters;
      String actionVal = (String)requestParameters.get(ZdtAgentParam.ACTION_PARAM.displayString);
      if (actionVal != null) {
         this.action = ZdtAgentAction.get(actionVal);
      }

      if (this.action == null) {
         throw new IllegalArgumentException("TODO: action must be specified");
      }
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      Iterator var2 = this.requestParameters.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         sb.append((String)entry.getKey());
         sb.append("-");
         sb.append((String)entry.getValue());
         sb.append(", ");
      }

      return sb.toString();
   }

   public String[] generateCommand() {
      String[] command = new String[this.requestParameters.size() * 2];
      int i = 0;

      Map.Entry entry;
      for(Iterator var3 = this.requestParameters.entrySet().iterator(); var3.hasNext(); command[i++] = (String)entry.getValue()) {
         entry = (Map.Entry)var3.next();
         command[i++] = (String)entry.getKey();
      }

      return command;
   }

   public String get(ZdtAgentParam zdtAgentParam) {
      return (String)this.requestParameters.get(zdtAgentParam.displayString);
   }

   public ZdtAgentAction getAction() {
      return this.action;
   }

   public void setDomainDirectory(String domainDirectory) {
      this.requestParameters.put(ZdtAgentParam.DOMAIN_DIR_PARAM.getDisplayString(), domainDirectory);
   }
}
