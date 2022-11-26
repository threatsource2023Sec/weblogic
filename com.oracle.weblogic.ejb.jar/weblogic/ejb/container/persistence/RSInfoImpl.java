package weblogic.ejb.container.persistence;

import java.sql.ResultSet;
import weblogic.ejb.container.persistence.spi.CMPBean;
import weblogic.ejb.container.persistence.spi.RSInfo;
import weblogic.utils.Debug;

public final class RSInfoImpl implements RSInfo {
   private static final boolean debug = System.getProperty("weblogic.ejb.container.persistence.debug") != null;
   private final ResultSet rs;
   private final int groupIndex;
   private final int offset;
   private final String cmrField;
   private final int cmrFieldOffset;
   private final Object pk;
   private final CMPBean cmpBean;

   public RSInfoImpl(ResultSet rs, int groupIndex, int offset, Object pk) {
      this(rs, groupIndex, offset, (String)null, 0, pk);
   }

   public RSInfoImpl(ResultSet rs, int groupIndex, int offset, String cmrField, int cmrFieldOffset, Object pk) {
      if (debug) {
         Debug.assertion(rs != null);
      }

      this.rs = rs;
      this.offset = offset;
      this.groupIndex = groupIndex;
      this.cmrField = cmrField;
      this.cmrFieldOffset = cmrFieldOffset;
      this.pk = pk;
      this.cmpBean = null;
   }

   public RSInfoImpl(CMPBean cmpBean, Object pk) {
      this.cmpBean = cmpBean;
      this.pk = pk;
      this.rs = null;
      this.offset = 0;
      this.groupIndex = 0;
      this.cmrField = null;
      this.cmrFieldOffset = 0;
   }

   public ResultSet getRS() {
      return this.rs;
   }

   public int getGroupIndex() {
      return this.groupIndex;
   }

   public Integer getOffset() {
      return new Integer(this.offset);
   }

   public String getCmrField() {
      return this.cmrField;
   }

   public Integer getCmrFieldOffset() {
      return new Integer(this.cmrFieldOffset);
   }

   public Object getPK() {
      return this.pk;
   }

   public CMPBean getCmpBean() {
      return this.cmpBean;
   }

   public boolean usesCmpBean() {
      return this.cmpBean != null;
   }

   public String toString() {
      return "(rs=" + this.rs + ", groupIndex=" + this.groupIndex + ", offset=" + this.offset + ", cmrField=" + this.cmrField + ", cmrFieldOffset=" + this.cmrFieldOffset + ", pk=" + this.pk + ", cmpBean=" + this.cmpBean + ")";
   }
}
