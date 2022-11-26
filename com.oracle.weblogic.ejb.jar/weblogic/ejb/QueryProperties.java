package weblogic.ejb;

import javax.ejb.FinderException;

public interface QueryProperties {
   short TX_REQUIRED = 1;
   short TX_REQUIRES_NEW = 3;
   short TX_MANDATORY = 4;

   void setTransaction(short var1) throws FinderException;

   short getTransaction() throws FinderException;

   void setMaxElements(int var1) throws FinderException;

   int getMaxElements() throws FinderException;

   void setIncludeUpdates(boolean var1) throws FinderException;

   boolean getIncludeUpdates() throws FinderException;

   void setResultTypeRemote(boolean var1) throws FinderException;

   boolean isResultTypeRemote() throws FinderException;

   void setEnableQueryCaching(boolean var1) throws FinderException;

   boolean getEnableQueryCaching() throws FinderException;
}
