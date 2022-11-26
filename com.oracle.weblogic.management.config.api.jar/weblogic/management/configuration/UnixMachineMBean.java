package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface UnixMachineMBean extends MachineMBean {
   boolean isPostBindUIDEnabled();

   void setPostBindUIDEnabled(boolean var1);

   String getPostBindUID();

   void setPostBindUID(String var1) throws InvalidAttributeValueException;

   boolean isPostBindGIDEnabled();

   void setPostBindGIDEnabled(boolean var1);

   String getPostBindGID();

   void setPostBindGID(String var1) throws InvalidAttributeValueException;
}
