package weblogic.transaction.internal;

import weblogic.diagnostics.image.PartitionAwareImageSource;

public interface JTAImageSource extends PartitionAwareImageSource {
   void checkTimeout() throws DiagnosticImageTimeoutException;
}
