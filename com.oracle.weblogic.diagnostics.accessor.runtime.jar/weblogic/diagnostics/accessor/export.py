# Caution: This file is part of the command scripting implementation. 
# Do not edit or move this file because this may cause commands and scripts to fail. 
# Do not try to reuse the logic in this file or keep copies of this file because this 
# could cause your scripts to fail when you upgrade to a different version.
# Copyright (c) 2004,2016, by Oracle and/or its affiliates.  All rights reserved.

import sys
import java
import weblogic.diagnostics.accessor.DiagnosticDataExporter as DiagnosticDataExporter
import weblogic.diagnostics.i18n.DiagnosticsTextTextFormatter as DiagnosticsTextTextFormatter
import weblogic.kernel.Kernel as Kernel
import weblogic.diagnostics.accessor.HarvestedTimeSeriesDataExporter as HarvestedTimeSeriesDataExporter

def exportDiagnosticData(**dict) :

    formatter = DiagnosticsTextTextFormatter.getInstance()
    
    dict.setdefault('logicalName', 'ServerLog')
    dict.setdefault('logName', 'myserver.log')
    dict.setdefault('logRotationDir', '.')
    dict.setdefault('storeDir', '../data/store/diagnostics')
    dict.setdefault('query', '')
    dict.setdefault('exportFileName', 'export')
    dict.setdefault('beginTimestamp', 0)
    dict.setdefault('endTimestamp', Long.MAX_VALUE)
    dict.setdefault('elfFields', '')
    dict.setdefault('format','xml')
    dict.setdefault('last','')

    System.out.println()    
    System.out.print(formatter.getInputParamsText());
    System.out.print("{logicalName='" + dict.get('logicalName') + "', ")
    System.out.print("logName='" + dict.get('logName') + "', ")
    System.out.print("logRotationDir='" + dict.get('logRotationDir') + "', ")
    System.out.print("storeDir='" + dict.get('storeDir') + "', ")
    System.out.print("query='" + dict.get('query') + "', ")
    System.out.print("exportFileName='" + dict.get('exportFileName') + "', ")
    System.out.print("elfFields='" + dict.get('elfFields') + "'}")
    System.out.println() 
    System.out.println()    
    System.out.println()    
    
    # print dict
    
    logicalName = dict.get('logicalName')
    logName = dict.get('logName')         
    logRotationDir = dict.get('logRotationDir')
    storeDir = dict.get('storeDir')
    query = dict.get('query')
    exportFileName = dict.get('exportFileName')
    elfFields = dict.get('elfFields')
    begin = dict.get('beginTimestamp')
    end = dict.get('endTimestamp')
    format = dict.get('format')
    last = dict.get('last')

    System.out.println()  
    System.out.println()

    Kernel.ensureInitialized()

    DiagnosticDataExporter.exportDiagnosticData(\
    logicalName, \
    logName, \
    logRotationDir, \
    storeDir, \
    query, \
    exportFileName, \
    elfFields, \
    begin, end, format, last)

    System.out.println()    
    System.out.println(formatter.getExportDiagnosticDataSuccessMsgText())
    System.out.println()
    
def exportHarvestedTimeSeriesDataOffline(wldfSystemResource, begin=-1L, end=-1L, exportFile='export.csv', dateFormat="EEE MM/dd/YY k:mm:ss:SSS z", storeDir='.', last='') :
  HarvestedTimeSeriesDataExporter.exportHarvestedTimeSeriesDataOffline(wldfSystemResource, begin, end, exportFile, dateFormat, storeDir, last)  
    
