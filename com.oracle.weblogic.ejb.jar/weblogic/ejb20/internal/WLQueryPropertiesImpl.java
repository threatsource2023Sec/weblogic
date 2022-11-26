package weblogic.ejb20.internal;

import java.io.Serializable;
import java.util.Properties;
import javax.ejb.FinderException;
import weblogic.ejb.WLQueryProperties;

public class WLQueryPropertiesImpl implements WLQueryProperties, Serializable {
   private static final long serialVersionUID = 1917450515366196167L;
   private short txAttribute = 1;
   private int maxElements = 0;
   private int isolationLevel = -1;
   private boolean includeUpdates = false;
   private boolean resultTypeRemote = false;
   private boolean sqlSelectDistinct = false;
   private String fieldGroupName = null;
   private String relationshipCachingName = null;
   private String sqlShapeName = null;
   private boolean enableCaching = false;

   public void setTransaction(short value) throws FinderException {
      switch (value) {
         case 1:
         case 3:
         case 4:
            this.txAttribute = value;
            return;
         case 2:
         default:
            throw new FinderException("Invalid Transaction Attribute setting: " + value);
      }
   }

   public short getTransaction() throws FinderException {
      return this.txAttribute;
   }

   public void setMaxElements(int value) throws FinderException {
      if (value < 0) {
         throw new FinderException("Invalid setting for MaxElements: " + value);
      } else {
         this.maxElements = value;
      }
   }

   public int getMaxElements() throws FinderException {
      return this.maxElements;
   }

   public void setIncludeUpdates(boolean value) throws FinderException {
      this.includeUpdates = value;
   }

   public boolean getIncludeUpdates() throws FinderException {
      return this.includeUpdates;
   }

   public void setResultTypeRemote(boolean value) throws FinderException {
      this.resultTypeRemote = value;
   }

   public boolean isResultTypeRemote() throws FinderException {
      return this.resultTypeRemote;
   }

   public void setFieldGroupName(String value) throws FinderException {
      this.fieldGroupName = value;
   }

   public String getFieldGroupName() throws FinderException {
      return this.fieldGroupName;
   }

   public void setSQLSelectDistinct(boolean value) throws FinderException {
      this.sqlSelectDistinct = value;
   }

   public boolean getSQLSelectDistinct() throws FinderException {
      return this.sqlSelectDistinct;
   }

   public void setIsolationLevel(int value) throws FinderException {
      switch (value) {
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
            this.isolationLevel = value;
            return;
         default:
            throw new FinderException("Invalid Transaction Isolation setting: " + value);
      }
   }

   public int getIsolationLevel() throws FinderException {
      return this.isolationLevel;
   }

   public String getRelationshipCachingName() throws FinderException {
      return this.relationshipCachingName;
   }

   public void setRelationshipCachingName(String cachingName) throws FinderException {
      this.relationshipCachingName = cachingName;
   }

   protected void setProperties(Properties props) throws FinderException {
      if (props != null) {
         String fg = props.getProperty("GROUP_NAME");
         if (fg != null && !fg.equals("")) {
            this.setFieldGroupName(fg);
         }

         String distinct = props.getProperty("SQL_SELECT_DISTINCT");
         if (distinct != null) {
            if (distinct.equalsIgnoreCase("true")) {
               this.setSQLSelectDistinct(true);
            } else {
               if (!distinct.equalsIgnoreCase("false")) {
                  throw new FinderException("Invalid SQL_SELECT_DISTINCT property value: " + distinct);
               }

               this.setSQLSelectDistinct(false);
            }
         }

         String il = props.getProperty("ISOLATION_LEVEL");
         if (il != null && !il.equals("")) {
            try {
               this.setIsolationLevel(Integer.parseInt(il));
            } catch (NumberFormatException var6) {
               throw new FinderException("Invalid isolation level property: " + il);
            }
         }

         String cachingName = props.getProperty("RELATIONSHIP_CACHING_NAME");
         if (cachingName != null && !cachingName.equals("")) {
            this.setRelationshipCachingName(cachingName);
         }
      }

   }

   public String getSqlShapeName() throws FinderException {
      return this.sqlShapeName;
   }

   public void setSqlShapeName(String string) throws FinderException {
      this.sqlShapeName = string;
   }

   public void setEnableQueryCaching(boolean value) throws FinderException {
      this.enableCaching = value;
   }

   public boolean getEnableQueryCaching() throws FinderException {
      return this.enableCaching;
   }
}
