package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;
import com.oracle.jrockit.jfr.InstantEvent;
import com.oracle.jrockit.jfr.UseConstantPool;
import com.oracle.jrockit.jfr.ValueDefinition;
import weblogic.diagnostics.flightrecorder.FlightRecorderEvent;

@EventDefinition(
   name = "Harvester Data Sample",
   description = "Harvester Data Sample",
   path = "wls/Harvester_Data_Sample",
   thread = false
)
public class HarvesterDataSampleEvent extends InstantEvent implements FlightRecorderEvent {
   @ValueDefinition(
      name = "Record ID",
      description = "The data sample record ID",
      relationKey = "http://www.oracle.com/wls/Harvester/recordID"
   )
   protected long recordID;
   @ValueDefinition(
      name = "Timestamp",
      description = "The data sample timestamp"
   )
   protected long timestamp;
   @UseConstantPool(
      name = "LocalPool"
   )
   @ValueDefinition(
      name = "Instance Name",
      description = "The name of the instance"
   )
   protected String instanceName;
   @UseConstantPool(
      name = "LocalPool"
   )
   @ValueDefinition(
      name = "Type Name",
      description = "The type name"
   )
   protected String typeName;
   @UseConstantPool(
      name = "LocalPool"
   )
   @ValueDefinition(
      name = "Attribute Name",
      description = "The attribute name"
   )
   protected String attributeName;
   @ValueDefinition(
      name = "Attribute Value String",
      description = "The attribute value as a String"
   )
   protected String attributeValueString;
   @ValueDefinition(
      name = "Attribute Value Double",
      description = "The attribute value as a Double"
   )
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
   }

   public void callEnd() {
   }

   public void callCommit() {
      this.commit();
   }

   public boolean callShouldWrite() {
      return this.shouldWrite();
   }

   public boolean callIsEnabled() {
      return this.getEventInfo().isEnabled();
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
