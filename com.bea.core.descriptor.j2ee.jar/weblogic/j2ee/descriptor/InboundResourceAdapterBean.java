package weblogic.j2ee.descriptor;

public interface InboundResourceAdapterBean {
   MessageAdapterBean getMessageAdapter();

   MessageAdapterBean createMessageAdapter();

   void destroyMessageAdapter(MessageAdapterBean var1);

   String getId();

   void setId(String var1);
}
