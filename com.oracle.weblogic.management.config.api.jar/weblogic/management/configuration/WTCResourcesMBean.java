package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface WTCResourcesMBean extends ConfigurationMBean {
   void setFldTbl16Classes(String[] var1) throws InvalidAttributeValueException;

   String[] getFldTbl16Classes();

   void setFldTbl32Classes(String[] var1) throws InvalidAttributeValueException;

   String[] getFldTbl32Classes();

   void setViewTbl16Classes(String[] var1) throws InvalidAttributeValueException;

   String[] getViewTbl16Classes();

   void setViewTbl32Classes(String[] var1) throws InvalidAttributeValueException;

   String[] getViewTbl32Classes();

   void setAppPassword(String var1) throws InvalidAttributeValueException;

   String getAppPassword();

   void setAppPasswordIV(String var1) throws InvalidAttributeValueException;

   String getAppPasswordIV();

   void setTpUsrFile(String var1) throws InvalidAttributeValueException;

   String getTpUsrFile();

   void setRemoteMBEncoding(String var1) throws InvalidAttributeValueException;

   String getRemoteMBEncoding();

   void setMBEncodingMapFile(String var1) throws InvalidAttributeValueException;

   String getMBEncodingMapFile();
}
