package weblogic.deploy.api.internal.utils;

import java.io.File;
import org.jvnet.hk2.annotations.Contract;
import weblogic.application.library.LoggableLibraryProcessingException;

@Contract
public interface LibraryDataFactory {
   SimpleLibraryData create(String var1, String var2, String var3, File var4) throws LoggableLibraryProcessingException;
}
