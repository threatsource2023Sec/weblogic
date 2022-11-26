package weblogic.diagnostics.accessor.runtime;

public interface FileArchiveRuntimeMBean extends ArchiveRuntimeMBean {
   int getRotatedFilesCount();

   int getIndexCycleCount();

   long getIndexTime();

   int getIncrementalIndexCycleCount();

   long getIncrementalIndexTime();
}
