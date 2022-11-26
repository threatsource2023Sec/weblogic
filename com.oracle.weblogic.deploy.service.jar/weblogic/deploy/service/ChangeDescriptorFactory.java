package weblogic.deploy.service;

import java.io.Serializable;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ChangeDescriptorFactory {
   ChangeDescriptor createChangeDescriptor(String var1, String var2, String var3, Serializable var4) throws InvalidCreateChangeDescriptorException;

   ChangeDescriptor createChangeDescriptor(String var1, String var2, String var3, Serializable var4, String var5) throws InvalidCreateChangeDescriptorException;

   ChangeDescriptor createChangeDescriptor(Serializable var1, Serializable var2);
}
