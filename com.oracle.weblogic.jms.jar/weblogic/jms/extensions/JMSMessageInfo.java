package weblogic.jms.extensions;

import java.io.IOException;
import java.util.Map;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.StreamMessage;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.SimpleType;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.common.XMLHelper;
import weblogic.messaging.Message;
import weblogic.messaging.runtime.MessageInfo;

public class JMSMessageInfo extends MessageInfo {
   private static final String OPEN_TYPE_NAME = "JMSMessageInfo";
   private static final String OPEN_DESCRIPTION = "This object represents information about a JMS message.  In addition to the message itself, meta-data is included that describes the context of the message at the time the management operation was invoked.";
   private static final String ITEM_VERSION_NUMBER = "VersionNumber";
   private static final String ITEM_MESSAGE_XMLTEXT = "MessageXMLText";
   private static final String ITEM_MESSAGE_SIZE = "MessageSize";
   private static final String ITEM_DESTINATION_NAME = "DestinationName";
   private static final String ITEM_BODY_INCLUDED = "BodyIncluded";
   private static final int VERSION = 1;
   private WLMessage message;
   private long messageSize;
   private String destinationName;
   private boolean bodyIncluded;

   public JMSMessageInfo(CompositeData cd) throws OpenDataException {
      super(cd);
   }

   public JMSMessageInfo(long handle, int state, String xidString, long sequenceNumber, String consumerID, WLMessage message, String destinationName, boolean bodyIncluded) {
      super(handle, state, xidString, sequenceNumber, consumerID);
      this.message = message;
      if (message != null) {
         this.messageSize = ((Message)message).size();
      }

      this.destinationName = destinationName;
      this.bodyIncluded = bodyIncluded;
   }

   public JMSMessageInfo(WLMessage message) {
      this.message = message;
      this.bodyIncluded = true;
   }

   protected void initOpenInfo() {
      super.initOpenInfo();
      this.openItemNames.add("VersionNumber");
      this.openItemNames.add("MessageXMLText");
      this.openItemNames.add("MessageSize");
      this.openItemNames.add("DestinationName");
      this.openItemNames.add("BodyIncluded");
      this.openItemDescriptions.add("The JMS version number.");
      this.openItemDescriptions.add("The message in XML String representation.  Note that the message body may be ommitted if the IncludeBody attribute is false.");
      this.openItemDescriptions.add("The size of the message in bytes.");
      this.openItemDescriptions.add("The destination name on which the message is pending.");
      this.openItemDescriptions.add("A boolean that indicates whether the JMS message item includes the body.");
      this.openItemTypes.add(SimpleType.INTEGER);
      this.openItemTypes.add(SimpleType.STRING);
      this.openItemTypes.add(SimpleType.LONG);
      this.openItemTypes.add(SimpleType.STRING);
      this.openItemTypes.add(SimpleType.BOOLEAN);
   }

   public WLMessage getMessage() {
      return this.message;
   }

   public void setMessage(WLMessage message) {
      this.message = message;
   }

   public long getMessageSize() {
      return this.messageSize;
   }

   public void setMessageSize(long messageSize) {
      this.messageSize = messageSize;
   }

   public String getDestinationName() {
      return this.destinationName;
   }

   public void setDestinationName(String destinationName) {
      this.destinationName = destinationName;
   }

   public boolean isBodyIncluded() {
      return this.bodyIncluded;
   }

   public void setBodyIncluded(boolean bodyIncluded) {
      this.bodyIncluded = bodyIncluded;
   }

   protected void readCompositeData(CompositeData cd) throws OpenDataException {
      super.readCompositeData(cd);
      String cdMessageXMLText = (String)cd.get("MessageXMLText");
      if (cdMessageXMLText != null) {
         WLMessage wlMessage;
         try {
            wlMessage = XMLHelper.createMessage(cdMessageXMLText);
            ((MessageImpl)wlMessage).setPropertiesWritable(false);
            ((MessageImpl)wlMessage).setBodyWritable(false);
            ((MessageImpl)wlMessage).includeJMSXDeliveryCount(true);
            if (wlMessage instanceof BytesMessage) {
               ((BytesMessage)wlMessage).reset();
            }

            if (wlMessage instanceof StreamMessage) {
               ((StreamMessage)wlMessage).reset();
            }
         } catch (JMSException var6) {
            throw new OpenDataException(var6.toString());
         } catch (IOException var7) {
            throw new OpenDataException(var7.toString());
         } catch (ClassNotFoundException var8) {
            throw new OpenDataException(var8.toString());
         }

         this.setMessage(wlMessage);
      }

      Long cdMessageSize = (Long)cd.get("MessageSize");
      if (cdMessageSize != null) {
         this.setMessageSize(cdMessageSize);
      }

      String cdDestinationName = (String)cd.get("DestinationName");
      if (cdDestinationName != null) {
         this.setDestinationName(cdDestinationName);
      }

      Boolean cdBodyIncluded = (Boolean)cd.get("BodyIncluded");
      if (cdBodyIncluded != null) {
         this.setBodyIncluded(cdBodyIncluded);
      }

   }

   protected Map getCompositeDataMap() throws OpenDataException {
      Map data = super.getCompositeDataMap();
      data.put("VersionNumber", new Integer(1));
      if (this.message != null) {
         try {
            data.put("MessageXMLText", XMLHelper.getXMLText(this.message, this.bodyIncluded));
            data.put("MessageSize", new Long(((Message)this.message).size()));
         } catch (JMSException var3) {
            throw new OpenDataException(var3.toString());
         }
      } else {
         data.put("MessageXMLText", (Object)null);
         data.put("MessageSize", new Long(0L));
      }

      data.put("DestinationName", this.destinationName);
      data.put("BodyIncluded", new Boolean(this.bodyIncluded));
      return data;
   }
}
