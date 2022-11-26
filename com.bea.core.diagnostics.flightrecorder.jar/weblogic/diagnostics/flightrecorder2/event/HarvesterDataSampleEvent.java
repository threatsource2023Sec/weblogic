package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Event;
import jdk.jfr.Label;
import jdk.jfr.Name;
import weblogic.diagnostics.flightrecorder2.impl.RelationKey;

@Label("Harvester Data Sample")
@Name("com.oracle.weblogic.HarvesterDataSampleEvent")
@Description("Harvester Data Sample")
@Category({"WebLogic Server"})
public class HarvesterDataSampleEvent extends Event {
   @Label("Record ID")
   @Description("The data sample record ID")
   @RelationKey("http://www.oracle.com/wls/Harvester/recordID")
   protected long recordID;
   @Label("Timestamp")
   @Description("The data sample timestamp")
   protected long timestamp;
   @Label("Instance Name")
   @Description("The name of the instance")
   protected String instanceName;
   @Label("Type Name")
   @Description("The type name")
   protected String typeName;
   @Label("Attribute Name")
   @Description("The attribute name")
   protected String attributeName;
   @Label("Attribute Value String")
   @Description("The attribute value as a String")
   protected String attributeValueString;
   @Label("Attribute Value Double")
   @Description("The attribute value as a Double")
   protected double attributeValueDouble;

   public long getRecordID() {
      return this.recordID;
   }

   public void setRecordID(long recordID) {
      this.recordID = recordID;
   }

   public long getTimestamp() {
      return this.timestamp;
   }

   public void setTimestamp(long timestamp) {
      this.timestamp = timestamp;
   }

   public String getInstanceName() {
      return this.instanceName;
   }

   public void setInstanceName(String instanceName) {
      this.instanceName = instanceName;
   }

   public String getTypeName() {
      return this.typeName;
   }

   public void setTypeName(String typeName) {
      this.typeName = typeName;
   }

   public String getAttributeName() {
      return this.attributeName;
   }

   public void setAttributeName(String attributeName) {
      this.attributeName = attributeName;
   }

   public String getAttributeValueString() {
      return this.attributeValueString;
   }

   public void setAttributeValueString(String attributeValueString) {
      this.attributeValueString = attributeValueString;
   }

   public double getAttributeValueDouble() {
      return this.attributeValueDouble;
   }

   public void setAttributeValueDouble(double attributeValueDouble) {
      this.attributeValueDouble = attributeValueDouble;
   }

   public boolean isEventTimed() {
      return false;
   }

   public void callBegin() {
      this.begin();
   }

   public void callEnd() {
      this.end();
   }

   public void callCommit() {
      this.commit();
   }

   public boolean callShouldWrite() {
      return this.shouldCommit();
   }

   public boolean callIsEnabled() {
      return this.isEnabled();
   }

   public boolean isBaseEvent() {
      return false;
   }

   public boolean isLoggingEvent() {
      return false;
   }

   public boolean isThrottleInformationEvent() {
      return false;
   }

   public boolean isWLLogRecordEvent() {
      return false;
   }

   public boolean isLogRecordEvent() {
      return false;
   }

   public boolean isGlobalInformationEvent() {
      return false;
   }
}
