package weblogic.diagnostics.archive;

import java.util.HashMap;
import java.util.Map;
import weblogic.diagnostics.accessor.DataRecord;

public class InstrumentationEventBean {
   private static final String TIMESTAMP = "TIMESTAMP";
   private static final String CONTEXTID = "CONTEXTID";
   private static final String TXID = "TXID";
   private static final String USERID = "USERID";
   private static final String TYPE = "TYPE";
   private static final String DOMAIN = "DOMAIN";
   private static final String SERVER = "SERVER";
   private static final String SCOPE = "SCOPE";
   private static final String MODULE = "MODULE";
   private static final String MONITOR = "MONITOR";
   private static final String FILENAME = "FILENAME";
   private static final String LINENUM = "LINENUM";
   private static final String CLASSNAME = "CLASSNAME";
   private static final String METHODNAME = "METHODNAME";
   private static final String METHODDSC = "METHODDSC";
   private static final String ARGUMENTS = "ARGUMENTS";
   private static final String RETVAL = "RETVAL";
   private static final String PAYLOAD = "PAYLOAD";
   private static final String CTXPAYLOAD = "CTXPAYLOAD";
   private static final String DYES = "DYES";
   private static final String THREADNAME = "THREADNAME";
   private static final String PARTITION_ID_COL_NAME = "PARTITION_ID";
   private static final String PARTITION_NAME_COL_NAME = "PARTITION_NAME";
   private long timeStamp;
   private String contextId;
   private String txId;
   private String userId;
   private String eventType;
   private String domain;
   private String server;
   private String scope;
   private String module;
   private String monitor;
   private String fileName;
   private Integer lineNumber;
   private String className;
   private String methodName;
   private String methodDesc;
   private String arguments;
   private String returnValue;
   private Object payload;
   private String ctxPayload;
   private Long dyeVector;
   private String threadName;
   private String partitionId;
   private String partitionName;

   public void setDataRecord(DataRecord rec) {
      int index = 0;
      ++index;
      this.timeStamp = (Long)rec.get(index);
      ++index;
      this.contextId = (String)rec.get(index);
      ++index;
      this.txId = (String)rec.get(index);
      ++index;
      this.userId = (String)rec.get(index);
      ++index;
      this.eventType = (String)rec.get(index);
      ++index;
      this.domain = (String)rec.get(index);
      ++index;
      this.server = (String)rec.get(index);
      ++index;
      this.scope = (String)rec.get(index);
      ++index;
      this.module = (String)rec.get(index);
      ++index;
      this.monitor = (String)rec.get(index);
      ++index;
      this.fileName = (String)rec.get(index);
      ++index;
      this.lineNumber = (Integer)rec.get(index);
      ++index;
      this.className = (String)rec.get(index);
      ++index;
      this.methodName = (String)rec.get(index);
      ++index;
      this.methodDesc = (String)rec.get(index);
      ++index;
      this.arguments = (String)rec.get(index);
      ++index;
      this.returnValue = (String)rec.get(index);
      ++index;
      this.payload = rec.get(index);
      ++index;
      this.ctxPayload = (String)rec.get(index);
      ++index;
      this.dyeVector = (Long)rec.get(index);
      ++index;
      this.threadName = (String)rec.get(index);
      ++index;
      this.partitionId = (String)rec.get(index);
      ++index;
      this.partitionName = (String)rec.get(index);
   }

   public long getTimeStamp() {
      return this.timeStamp;
   }

   public String getContextId() {
      return this.contextId;
   }

   public String getTxId() {
      return this.txId;
   }

   public String getUserId() {
      return this.userId;
   }

   public String getEventType() {
      return this.eventType;
   }

   public String getDomain() {
      return this.domain;
   }

   public String getServer() {
      return this.server;
   }

   public String getScope() {
      return this.scope;
   }

   public String getModule() {
      return this.module;
   }

   public String getMonitor() {
      return this.monitor;
   }

   public String getFileName() {
      return this.fileName;
   }

   public Integer getLineNumber() {
      return this.lineNumber;
   }

   public String getClassName() {
      return this.className;
   }

   public String getMethodName() {
      return this.methodName;
   }

   public String getMethodDesc() {
      return this.methodDesc;
   }

   public String getArguments() {
      return this.arguments;
   }

   public String getReturnValue() {
      return this.returnValue;
   }

   public Object getPayload() {
      return this.payload;
   }

   public String getContextPayload() {
      return this.ctxPayload;
   }

   public Long getDyeVector() {
      return this.dyeVector;
   }

   public String getThreadName() {
      return this.threadName;
   }

   public String getPartitionId() {
      return this.partitionId;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public Map getBeanData() {
      HashMap props = new HashMap();
      props.put("TIMESTAMP", Long.toString(this.getTimeStamp()));
      props.put("MONITOR", this.getMonitor());
      props.put("DOMAIN", this.getDomain());
      props.put("SERVER", this.getServer());
      props.put("SCOPE", this.getScope());
      props.put("THREADNAME", this.getThreadName());
      props.put("CONTEXTID", this.toString(this.getContextId()));
      props.put("USERID", this.getUserId());
      props.put("TYPE", this.getEventType());
      props.put("TXID", this.toString(this.getTxId()));
      props.put("MODULE", this.getModule());
      props.put("FILENAME", this.getFileName());
      props.put("LINENUM", Integer.toString(this.getLineNumber()));
      props.put("CLASSNAME", this.getClassName());
      props.put("METHODNAME", this.getMethodName());
      props.put("METHODDSC", this.toString(this.getMethodDesc()));
      props.put("ARGUMENTS", this.toString(this.getArguments()));
      props.put("RETVAL", this.toString(this.getReturnValue()));
      props.put("PAYLOAD", this.toString(this.getPayload()));
      props.put("CTXPAYLOAD", this.toString(this.getContextPayload()));
      props.put("DYES", Long.toString(this.getDyeVector()));
      props.put("PARTITION_ID", this.toString(this.getPartitionId()));
      props.put("PARTITION_NAME", this.toString(this.getPartitionName()));
      return props;
   }

   private String toString(Object fieldValue) {
      return fieldValue == null ? "" : fieldValue.toString();
   }
}
