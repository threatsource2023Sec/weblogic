package weblogic.management.descriptors.application.weblogic.jdbc;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class DriverParamsMBeanImpl extends XMLElementMBeanDelegate implements DriverParamsMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_streamChunkSize = false;
   private int streamChunkSize;
   private boolean isSet_statement = false;
   private StatementMBean statement;
   private boolean isSet_enableTwoPhaseCommit = false;
   private boolean enableTwoPhaseCommit = false;
   private boolean isSet_rowPrefetchEnabled = false;
   private boolean rowPrefetchEnabled;
   private boolean isSet_preparedStatement = false;
   private PreparedStatementMBean preparedStatement;
   private boolean isSet_rowPrefetchSize = false;
   private int rowPrefetchSize;

   public int getStreamChunkSize() {
      return this.streamChunkSize;
   }

   public void setStreamChunkSize(int value) {
      int old = this.streamChunkSize;
      this.streamChunkSize = value;
      this.isSet_streamChunkSize = value != -1;
      this.checkChange("streamChunkSize", old, this.streamChunkSize);
   }

   public StatementMBean getStatement() {
      return this.statement;
   }

   public void setStatement(StatementMBean value) {
      StatementMBean old = this.statement;
      this.statement = value;
      this.isSet_statement = value != null;
      this.checkChange("statement", old, this.statement);
   }

   public boolean getEnableTwoPhaseCommit() {
      return this.enableTwoPhaseCommit;
   }

   public void setEnableTwoPhaseCommit(boolean value) {
      boolean old = this.enableTwoPhaseCommit;
      this.enableTwoPhaseCommit = value;
      this.isSet_enableTwoPhaseCommit = true;
      this.checkChange("enableTwoPhaseCommit", old, this.enableTwoPhaseCommit);
   }

   public boolean isRowPrefetchEnabled() {
      return this.rowPrefetchEnabled;
   }

   public void setRowPrefetchEnabled(boolean value) {
      boolean old = this.rowPrefetchEnabled;
      this.rowPrefetchEnabled = value;
      this.isSet_rowPrefetchEnabled = true;
      this.checkChange("rowPrefetchEnabled", old, this.rowPrefetchEnabled);
   }

   public PreparedStatementMBean getPreparedStatement() {
      return this.preparedStatement;
   }

   public void setPreparedStatement(PreparedStatementMBean value) {
      PreparedStatementMBean old = this.preparedStatement;
      this.preparedStatement = value;
      this.isSet_preparedStatement = value != null;
      this.checkChange("preparedStatement", old, this.preparedStatement);
   }

   public int getRowPrefetchSize() {
      return this.rowPrefetchSize;
   }

   public void setRowPrefetchSize(int value) {
      int old = this.rowPrefetchSize;
      this.rowPrefetchSize = value;
      this.isSet_rowPrefetchSize = value != -1;
      this.checkChange("rowPrefetchSize", old, this.rowPrefetchSize);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<driver-params");
      result.append(">\n");
      result.append(ToXML.indent(indentLevel)).append("</driver-params>\n");
      return result.toString();
   }
}
