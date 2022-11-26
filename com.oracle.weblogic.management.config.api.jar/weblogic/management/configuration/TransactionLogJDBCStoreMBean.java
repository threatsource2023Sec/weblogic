package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface TransactionLogJDBCStoreMBean extends JDBCStoreMBean, TransactionLogStoreMBean {
   String getPrefixName();

   void setPrefixName(String var1);

   boolean isEnabled();

   void setEnabled(boolean var1);

   int getMaxRetrySecondsBeforeTLOGFail();

   void setMaxRetrySecondsBeforeTLOGFail(int var1);

   int getMaxRetrySecondsBeforeTXException();

   void setMaxRetrySecondsBeforeTXException(int var1);

   int getRetryIntervalSeconds();

   void setRetryIntervalSeconds(int var1);

   void setDataSource(JDBCSystemResourceMBean var1) throws InvalidAttributeValueException;
}
