package weblogic.messaging.runtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;

public abstract class MessageInfo {
   public static final int STATE_VISIBLE = 1;
   public static final int STATE_ORDERED = 16;
   public static final int STATE_DELAYED = 32;
   public static final int STATE_RECEIVE = 4;
   public static final int STATE_SEND = 2;
   public static final int STATE_TRANSACTION = 8;
   public static final int STATE_PAUSED = 256;
   public static final int STATE_REDELIVERY_COUNT_EXCEEDED = 128;
   public static final int STATE_EXPIRED = 64;
   private static final String OPEN_TYPE_NAME = "MessageInfo";
   private static final String OPEN_DESCRIPTION = "This object represents message meta-data that describes the context of the message at the time the management operation was performed.";
   protected ArrayList openItemNames = new ArrayList();
   protected ArrayList openItemDescriptions = new ArrayList();
   protected ArrayList openItemTypes = new ArrayList();
   protected static final String ITEM_HANDLE = "Handle";
   protected static final String ITEM_STATE = "State";
   protected static final String ITEM_XID_STRING = "XidString";
   protected static final String ITEM_SEQUENCE_NUMBER = "SequenceNumber";
   protected static final String ITEM_CONSUMER_ID = "ConsumerID";
   protected Long handle;
   protected int state;
   protected String stateString;
   protected String xidString;
   protected long sequenceNumber;
   protected String consumerID;

   protected MessageInfo() {
      this.initOpenInfo();
   }

   public MessageInfo(CompositeData cd) throws OpenDataException {
      this.readCompositeData(cd);
      this.initOpenInfo();
   }

   public MessageInfo(long handle, int state, String xidString, long sequenceNumber, String consumerID) {
      this.handle = new Long(handle);
      this.state = state;
      this.stateString = getStateString(state);
      this.xidString = xidString;
      this.sequenceNumber = sequenceNumber;
      this.consumerID = consumerID;
      this.initOpenInfo();
   }

   public CompositeData toCompositeData() throws OpenDataException {
      CompositeDataSupport cds = new CompositeDataSupport(this.getCompositeType(), this.getCompositeDataMap());
      return cds;
   }

   public Long getHandle() {
      return this.handle;
   }

   public void setHandle(Long handle) {
      this.handle = handle;
   }

   public int getState() {
      return this.state;
   }

   public void setState(int state) {
      this.state = state;
      this.stateString = getStateString(state);
   }

   public String getStateString() {
      return this.stateString;
   }

   public String getXidString() {
      return this.xidString;
   }

   public void setXidString(String xidString) {
      this.xidString = xidString;
   }

   public long getSequenceNumber() {
      return this.sequenceNumber;
   }

   public void setSequenceNumber(long sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
   }

   public String getConsumerID() {
      return this.consumerID;
   }

   public void setConsumerID(String consumerID) {
      this.consumerID = consumerID;
   }

   public static String getStateString(int state) {
      StringBuffer states = new StringBuffer();
      if (state == 1) {
         states.append("visible");
         return states.toString();
      } else {
         if ((state & 2) != 0) {
            states.append("send ");
         }

         if ((state & 4) != 0) {
            states.append("receive ");
         }

         if ((state & 8) != 0) {
            states.append("transaction ");
         }

         if ((state & 16) != 0) {
            states.append("ordered ");
         }

         if ((state & 32) != 0) {
            states.append("delayed ");
         }

         if ((state & 64) != 0) {
            states.append("expired ");
         }

         if ((state & 128) != 0) {
            states.append("redelivery-count-exceeded ");
         }

         if ((state & 256) != 0) {
            states.append("paused ");
         }

         if ((state & 512) != 0) {
            states.append("sequenced ");
         }

         if ((state & 1024) != 0) {
            states.append("unit-of-work-component ");
         }

         return states.toString().trim();
      }
   }

   protected void initOpenInfo() {
      this.openItemNames.add("Handle");
      this.openItemNames.add("State");
      this.openItemNames.add("XidString");
      this.openItemNames.add("SequenceNumber");
      this.openItemNames.add("ConsumerID");
      this.openItemDescriptions.add("A handle that identifies this object in the cursor.");
      this.openItemDescriptions.add("The state of the message at the time of the management operation invocation.");
      this.openItemDescriptions.add("The Xid of the transaction for which this message is pending.");
      this.openItemDescriptions.add("The sequence number of the message that indicates its position in the FIFO ordering of the destination.");
      this.openItemDescriptions.add("Information that identifies the consumer of the message");
      this.openItemTypes.add(SimpleType.LONG);
      this.openItemTypes.add(SimpleType.INTEGER);
      this.openItemTypes.add(SimpleType.STRING);
      this.openItemTypes.add(SimpleType.LONG);
      this.openItemTypes.add(SimpleType.STRING);
   }

   protected void readCompositeData(CompositeData cd) throws OpenDataException {
      Long cdHandle = (Long)cd.get("Handle");
      if (cdHandle != null) {
         this.setHandle(cdHandle);
      }

      Integer cdState = (Integer)cd.get("State");
      if (cdState != null) {
         this.setState(cdState);
      }

      String cdXidString = (String)cd.get("XidString");
      if (cdXidString != null) {
         this.setXidString(cdXidString);
      }

      Long cdSequenceNumber = (Long)cd.get("SequenceNumber");
      if (cdSequenceNumber != null) {
         this.setSequenceNumber(cdSequenceNumber);
      }

      String cdConsumerID = (String)cd.get("ConsumerID");
      if (cdConsumerID != null) {
         this.setConsumerID(cdConsumerID);
      }

   }

   protected Map getCompositeDataMap() throws OpenDataException {
      Map data = new HashMap();
      data.put("Handle", this.handle);
      data.put("State", new Integer(this.state));
      data.put("XidString", this.xidString);
      data.put("SequenceNumber", new Long(this.sequenceNumber));
      data.put("ConsumerID", this.consumerID);
      return data;
   }

   protected CompositeType getCompositeType() throws OpenDataException {
      CompositeType ct = new CompositeType("MessageInfo", "This object represents message meta-data that describes the context of the message at the time the management operation was performed.", (String[])((String[])this.openItemNames.toArray(new String[this.openItemNames.size()])), (String[])((String[])this.openItemDescriptions.toArray(new String[this.openItemDescriptions.size()])), (OpenType[])((OpenType[])this.openItemTypes.toArray(new OpenType[this.openItemTypes.size()])));
      return ct;
   }
}
