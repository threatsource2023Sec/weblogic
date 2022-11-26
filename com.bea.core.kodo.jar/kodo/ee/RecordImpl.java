package kodo.ee;

import javax.resource.cci.Record;

public class RecordImpl implements Record, Cloneable {
   private String recordName;
   private String recordShortDescription;

   public Object clone() throws CloneNotSupportedException {
      return super.clone();
   }

   public void setRecordName(String recordName) {
      this.recordName = recordName;
   }

   public String getRecordName() {
      return this.recordName;
   }

   public void setRecordShortDescription(String recordShortDescription) {
      this.recordShortDescription = recordShortDescription;
   }

   public String getRecordShortDescription() {
      return this.recordShortDescription;
   }
}
