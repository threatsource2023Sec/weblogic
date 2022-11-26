package weblogic.diagnostics.image.descriptor;

public interface InstrumentationEventBean {
   ColumnDataBean[] getColumnDatas();

   ColumnDataBean createColumnData();
}
