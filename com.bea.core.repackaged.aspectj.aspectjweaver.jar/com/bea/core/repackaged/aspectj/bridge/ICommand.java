package com.bea.core.repackaged.aspectj.bridge;

public interface ICommand {
   boolean runCommand(String[] var1, IMessageHandler var2);

   boolean repeatCommand(IMessageHandler var1);
}
