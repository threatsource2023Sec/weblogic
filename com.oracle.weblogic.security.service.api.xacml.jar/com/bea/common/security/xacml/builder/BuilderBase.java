package com.bea.common.security.xacml.builder;

import com.bea.common.security.xacml.InvalidParameterException;
import com.bea.common.security.xacml.policy.Actions;
import com.bea.common.security.xacml.policy.Environments;
import com.bea.common.security.xacml.policy.Resources;
import com.bea.common.security.xacml.policy.Subjects;

public interface BuilderBase {
   Object setDescription(String var1);

   Object addSubject(int var1, String var2) throws InvalidParameterException;

   Object addSubject(int var1, String var2, String var3, String var4, Function var5) throws InvalidParameterException;

   Object addSubject(int var1, String var2, String var3, String var4, Function var5, boolean var6, String var7, String var8) throws InvalidParameterException;

   Object addResource(int var1, String var2) throws InvalidParameterException;

   Object addResource(int var1, String var2, String var3, String var4, Function var5) throws InvalidParameterException;

   Object addResource(int var1, String var2, String var3, String var4, Function var5, boolean var6, String var7) throws InvalidParameterException;

   Object addAction(int var1, String var2) throws InvalidParameterException;

   Object addAction(int var1, String var2, String var3, String var4, Function var5) throws InvalidParameterException;

   Object addAction(int var1, String var2, String var3, String var4, Function var5, boolean var6, String var7) throws InvalidParameterException;

   Object addEnvironment(int var1, String var2, String var3, String var4, Function var5) throws InvalidParameterException;

   Object addEnvironment(int var1, String var2, String var3, String var4, Function var5, boolean var6, String var7) throws InvalidParameterException;

   Subjects removeSubjects();

   Resources removeResources();

   Actions removeActions();

   Environments removeEnvironments();
}
