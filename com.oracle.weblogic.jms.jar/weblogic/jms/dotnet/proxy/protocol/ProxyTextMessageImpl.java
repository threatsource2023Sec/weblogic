package weblogic.jms.dotnet.proxy.protocol;

import java.io.DataInput;
import java.io.IOException;
import java.io.InputStream;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import weblogic.jms.common.PayloadFactoryImpl;
import weblogic.jms.common.PayloadText;
import weblogic.jms.common.TextMessageImpl;
import weblogic.jms.dotnet.proxy.util.ProxyUtil;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWriter;

public final class ProxyTextMessageImpl extends ProxyMessageImpl {
   private static final int EXTVERSION = 1;
   private static final int _HAS_STRING_DATA = 1;
   private String text;
   private PayloadText payload;
   private static final boolean mydebug = false;

   public ProxyTextMessageImpl() {
   }

   public ProxyTextMessageImpl(TextMessage message) throws JMSException {
      super(message);
      Object object = ((TextMessageImpl)message).getMessageBody();
      if (object instanceof PayloadText) {
         this.payload = (PayloadText)object;
      } else {
         this.text = (String)object;
         if (this.text == null) {
            this.text = message.getText();
         }
      }

   }

   public byte getType() {
      return 6;
   }

   public ProxyTextMessageImpl(String text) {
      this.text = text;
   }

   public void setText(String text) throws JMSException {
      this.text = text;
   }

   public String getText() throws JMSException {
      if (this.text != null) {
         return this.text;
      } else if (this.payload != null) {
         try {
            this.text = this.payload.readUTF8();
            this.payload = null;
            return this.text;
         } catch (IOException var2) {
            throw new weblogic.jms.common.JMSException(var2);
         }
      } else {
         return this.text;
      }
   }

   public String toString() {
      return "TextMessage[" + this.getMessageID() + ", " + (this.text == null ? "null" : (this.text.length() < 40 ? this.text : this.text.substring(0, 30) + "...")) + "]";
   }

   public void populateJMSMessage(TextMessage msg) throws JMSException {
      super.populateJMSMessage(msg);
      if (this.payload != null) {
         ((TextMessageImpl)msg).setUTF8Buffer(this.payload);
      }

   }

   public int getMarshalTypeCode() {
      return 35;
   }

   public void marshal(MarshalWriter mw) {
      super.marshal(mw);
      MarshalBitMask versionFlags = new MarshalBitMask(1);
      if (this.text != null || this.payload != null) {
         versionFlags.setBit(1);
      }

      versionFlags.marshal(mw);
      if (this.text != null) {
         mw.writeString(this.text);
      } else if (this.payload != null) {
         try {
            this.payload.writeLengthAndData(mw.getDataOutputStream());
         } catch (IOException var4) {
            throw new RuntimeException(var4);
         }
      }

   }

   public void unmarshal(MarshalReader mr) {
      super.unmarshal(mr);
      MarshalBitMask versionFlags = new MarshalBitMask();
      versionFlags.unmarshal(mr);
      ProxyUtil.checkVersion(versionFlags.getVersion(), 1, 1);
      if (versionFlags.isSet(1)) {
         try {
            DataInput dis = mr.getDataInputStream();
            this.payload = (PayloadText)PayloadFactoryImpl.createPayload((InputStream)dis);
         } catch (IOException var4) {
            throw new RuntimeException(var4);
         }
      }

   }
}
