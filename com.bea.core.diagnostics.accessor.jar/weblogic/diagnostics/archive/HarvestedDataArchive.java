package weblogic.diagnostics.archive;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface HarvestedDataArchive {
   DataWriter getDataWriter() throws ArchiveException;
}
