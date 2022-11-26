package oracle.jrockit.jfr.openmbean;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;
import oracle.jrockit.jfr.JFRStats;

public class JFRStatsType extends JFRMBeanType {
   public JFRStatsType() throws OpenDataException {
      super("Flight Recorder Statistics", "Flight Recorder Statistics", new String[]{"bytesWritten", "bytesLost", "bytesWrittenDirectlyToDisk", "threadBufferBytesAllocated", "threadBufferBytesReleased", "chunksWritten", "threadBuffersCopied", "threadBuffersCopiedDirectToDisk", "threadBuffersLost", "globalBuffersCopied"}, new String[]{"Bytes Written", "Bytes Lost", "Bytes Written Directly To Disk", "Thread Buffer Bytes Allocated", "Thread Buffer Bytes Released", "Chunks Written", "Thread Buffers Copied", "Thread Buffers Copied Direct To Disk", "Thread Buffers Lost", "Global Buffers Copied"}, new OpenType[]{SimpleType.LONG, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG, SimpleType.LONG});
   }

   public CompositeData toCompositeTypeData(JFRStats t) throws OpenDataException {
      return new CompositeDataSupport(this.getType(), this.getNames(), new Object[]{t.bytesWritten(), t.bytesLost(), t.bytesWrittenDirectlyToDisk(), t.threadBufferBytesAllocated(), t.threadBufferBytesReleased(), t.chunksWritten(), t.threadBuffersCopied(), t.threadBuffersCopiedDirectToDisk(), t.threadBufffersLost(), t.globalBuffersCopied()});
   }
}
