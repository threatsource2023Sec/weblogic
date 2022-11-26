package com.bea.common.classloader.service;

public interface ClassLoaderService {
   String SERVICE_NAME = ClassLoaderService.class.getName();

   ClassLoader getClassLoader(String var1) throws ClassLoaderInitializationException, ClassLoaderNotFoundException;
}
