package weblogic.nodemanager.rest.model;

import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.composite.RestModel;
import org.glassfish.admin.rest.composite.RestModelImpl;
import org.glassfish.admin.rest.composite.metadata.LegalValues;
import org.glassfish.admin.rest.composite.metadata.ReadOnly;

public class ServerJob extends RestModelImpl implements RestModel {
   private String name;
   private String description;
   private String operation;
   private boolean completed;
   private String progress;
   private long intervalToPoll;
   private String startTime;
   private String endTime;
   private JSONObject error;

   @ReadOnly
   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   @ReadOnly
   public String getDescription() {
      return this.description;
   }

   public void setDescription(String desc) {
      this.description = desc;
   }

   @ReadOnly
   @LegalValues(
      values = {"start", "kill"}
   )
   public String getOperation() {
      return this.operation;
   }

   public void setOperation(String operation) {
      this.operation = operation;
   }

   @ReadOnly
   public boolean getCompleted() {
      return this.completed;
   }

   public void setCompleted(boolean completed) {
      this.completed = completed;
   }

   @ReadOnly
   @LegalValues(
      values = {"succeeded", "processing", "failed"}
   )
   public String getProgress() {
      return this.progress;
   }

   public void setProgress(String progress) {
      this.progress = progress;
   }

   @ReadOnly
   public long getIntervalToPoll() {
      return this.intervalToPoll;
   }

   public void setIntervalToPoll(long intervalToPoll) {
      this.intervalToPoll = intervalToPoll;
   }

   @ReadOnly
   public String getStartTime() {
      return this.startTime;
   }

   public void setStartTime(String startTime) {
      this.startTime = startTime;
   }

   @ReadOnly
   public String getEndTime() {
      return this.endTime;
   }

   public void setEndTime(String endTime) {
      this.endTime = endTime;
   }

   @ReadOnly
   public JSONObject getError() {
      return this.error;
   }

   public void setError(JSONObject error) {
      this.error = error;
   }
}
