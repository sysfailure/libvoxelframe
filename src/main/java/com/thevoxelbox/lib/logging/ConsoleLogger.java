/*
 * Copyright (C) 2011 - 2012, psanker and contributors
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are 
 * permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice, this list of 
 *   conditions and the following 
 * * Redistributions in binary form must reproduce the above copyright notice, this list of 
 *   conditions and the following disclaimer in the documentation and/or other materials 
 *   provided with the distribution.
 * * Neither the name of The VoxelPlugineering Team nor the names of its contributors may be 
 *   used to endorse or promote products derived from this software without specific prior 
 *   written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS 
 * OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE 
 * COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) 
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR 
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.thevoxelbox.lib.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsoleLogger {
    
    private static ConsoleLogger instance;
    private String logger = "Minecraft";
    
    private final String tag;
    
    public ConsoleLogger(String plugin) 
    {
        tag = plugin;
    }
    
    public static void setInstance(ConsoleLogger logger) 
    {
        instance = logger;
    }
    
    public static void setOutput(String out) 
    {
        instance.logger = out;
    }
    
    public static void log(String str) 
    {
        instance._log(str);
    }
    
    public static void log(String str, final int importance) 
    {
        instance._log(str, importance);
    }
    
    public static void log(String str, String module, final int importance) 
    {
        instance._log(str, module, importance);
    }
    
    private void _log(String str) 
    {
        _log(str, 0);
    }
    
    private void _log(String str, final int importance) 
    {
        switch (importance) {
            case 0:
                Logger.getLogger(logger).log(Level.INFO, "[" + tag + "] " + str);
            case 1:
                Logger.getLogger(logger).log(Level.WARNING, "[" + tag + "] " + str);
            case 2:
                Logger.getLogger(logger).log(Level.SEVERE, "[" + tag + "] " + str);
            default:
                Logger.getLogger(logger).log(Level.INFO, "[" + tag + "] " + str);
        }
    }
    
    private void _log(String str, String module, final int importance) 
    {
        switch (importance) {
            case 0:
                Logger.getLogger(logger).log(Level.INFO, "[" + tag + ": " + module + "] " + str);
            case 1:
                Logger.getLogger(logger).log(Level.WARNING, "[" + tag + ": " + module + "] " + str);
            case 2:
                Logger.getLogger(logger).log(Level.SEVERE, "[" + tag + ": " + module + "] " + str);
            default:
                Logger.getLogger(logger).log(Level.INFO, "[" + tag + ": " + module + "] " + str);
        }
    }
}
