package weblogic.management.patching.agent;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class ZdtAgentInputOutput {
   private Boolean success = false;
   private String exceptionCause;
   private List messages;

   public void success() {
      this.success = true;
   }

   public void failure(Exception e) {
      this.success = false;
      this.exceptionCause = e.toString();
   }

   public void setMessages(List messages) {
      this.messages = messages;
   }

   public boolean isSuccess() {
      return this.success;
   }

   public String getCause() {
      return this.exceptionCause;
   }

   public List getMessages() {
      return this.messages;
   }

   public void writeResponse(OutputStream out) {
      try {
         JSONObject jsonObject = new JSONObject();
         jsonObject.put("SUCCESS", this.success.toString());
         jsonObject.putOpt("STACK_TRACE", this.exceptionCause);
         JSONArray logMessages = new JSONArray();
         if (this.messages != null) {
            Iterator var4 = this.messages.iterator();

            while(var4.hasNext()) {
               ZdtAgentLogMessage logMessage = (ZdtAgentLogMessage)var4.next();
               JSONObject jsonLogMsg = new JSONObject();
               jsonLogMsg.put("TIME_STAMP", logMessage.getTimeStampMillis());
               jsonLogMsg.put("LEVEL", logMessage.getLevel().toString());
               jsonLogMsg.put("MSG_CONTENTS", logMessage.getMsgContents());
               jsonLogMsg.putOpt("CAUSE", logMessage.getCause());
               logMessages.put(jsonLogMsg);
            }
         }

         jsonObject.put("messages", logMessages);
         out.write(jsonObject.toString().getBytes());
      } catch (JSONException var7) {
         var7.printStackTrace();
      } catch (IOException var8) {
         var8.printStackTrace();
      }

   }

   public void readResponse(String outputFromNMInvocation) {
      try {
         JSONObject jsonResponse = new JSONObject(outputFromNMInvocation);
         this.success = jsonResponse.getBoolean("SUCCESS");
         this.exceptionCause = jsonResponse.optString("STACK_TRACE");
         JSONArray messagesArray = jsonResponse.getJSONArray("messages");
         this.messages = new ArrayList();
         if (messagesArray != null && messagesArray.length() > 0) {
            for(int i = 0; i < messagesArray.length(); ++i) {
               JSONObject msgObject = messagesArray.getJSONObject(i);
               long timeStampMillis = msgObject.getLong("TIME_STAMP");
               Level level = Level.parse(msgObject.getString("LEVEL"));
               String msgContents = msgObject.getString("MSG_CONTENTS");
               String cause = msgObject.optString("CAUSE");
               ZdtAgentLogMessage zdtAgentLogMessage = new ZdtAgentLogMessage(timeStampMillis, level, msgContents, cause);
               this.messages.add(zdtAgentLogMessage);
            }
         }
      } catch (JSONException var12) {
         var12.printStackTrace();
      }

   }
}
