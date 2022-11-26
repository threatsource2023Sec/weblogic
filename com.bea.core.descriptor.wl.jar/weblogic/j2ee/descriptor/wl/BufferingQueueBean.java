package weblogic.j2ee.descriptor.wl;

public interface BufferingQueueBean {
   String getName();

   void setName(String var1);

   Boolean getEnabled();

   void setEnabled(Boolean var1);

   String getConnectionFactoryJndiName();

   void setConnectionFactoryJndiName(String var1);

   Boolean getTransactionEnabled();

   void setTransactionEnabled(Boolean var1);
}
