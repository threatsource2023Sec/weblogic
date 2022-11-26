# Caution: This file is part of the command scripting implementation. 
# Do not edit or move this file because this may cause commands and scripts to fail. 
# Do not try to reuse the logic in this file or keep copies of this file because this 
# could cause your scripts to fail when you upgrade to a different version.
# Copyright (c) 2004,2016, Oracle and/or its affiliates. All rights reserved.

"""
This is WLST Module that a user can import into other Jython Modules in standalone 
Cam environment.

"""
from weblogic.management.scripting.core.utils import WLSTCoreUtil
import sys
origPrompt = sys.ps1
key='WLSTMODULE_CORE'
theInterpreter = WLSTCoreUtil.ensureInterpreter();
WLSTCoreUtil.ensureWLCtx(theInterpreter)
execfile(WLSTCoreUtil.getWLSTCoreScriptPath())
execfile(WLSTCoreUtil.getWLSTNMScriptPath())
execfile(WLSTCoreUtil.getOfflineWLSTScriptPath())
theInterpreter.set(key, WLS)
WLSTCoreUtil.initOfflineContext(theInterpreter, key)
theInterpreter = None
sys.ps1 = origPrompt
modules = WLSTCoreUtil.getWLSTModules()
for mods in modules:
  execfile(mods.getAbsolutePath())
jmodules = WLSTCoreUtil.getWLSTJarModules()
for jmods in jmodules:
  fis = jmods.openStream()
  execfile(fis, jmods.getFile())
  fis.close()
wlstPrompt = "false"

def invoke(methodName, parameters, signatures):
  return wlstInvoke(methodName, parameters, signatures)
