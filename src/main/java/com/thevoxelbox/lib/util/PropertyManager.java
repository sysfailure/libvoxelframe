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

package com.thevoxelbox.lib.util;

import com.thevoxelbox.lib.logging.ConsoleLogger;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertyManager {
    
    public static String BASE = "plugins";

    public PropertyManager(String dir) {
        BASE = dir;
    }
    
    public static void setBaseDirectory(File dir)
    {
        setBaseDirectory(dir.getAbsolutePath());
    }
    
    public static void setBaseDirectory(String dir)
    {
        BASE = dir;
    }

    public static Map<String, Object> load(String target) {
        return load(target, "");
    }

    public static Map<String, Object> load(String target, String destination) {
        // For example, when destination is "/channels",
        // the target BASE will be "${BASE}/channels/"

        Map<String, Object> map = new HashMap<String, Object>();
        map.clear();

        File f = new File(BASE + destination + "/" + target + ".properties");
        FileInputStream fi = null;

        if (f.exists()) {
            try {
                Properties props = new Properties();
                fi = new FileInputStream(f);

                props.load(fi);

                for (Map.Entry<Object, Object> entry : props.entrySet()) {
                    String key = entry.getKey().toString();

                    try {
                        if (entry.getValue().toString().contains(".") || (Double.parseDouble(entry.getValue().toString()) > 2147483647 || Double.parseDouble(entry.getValue().toString()) < -2147483648)) {
                            Double d = Double.parseDouble(entry.getValue().toString());
                            map.put(key, d);
                            continue;
                        }
                        
                        Integer i = Integer.parseInt(entry.getValue().toString());
                        map.put(key, i);
                    } catch (NumberFormatException ex) {
                        if (entry.getValue().toString().equals(Boolean.TRUE.toString()) || entry.getValue().toString().equals(Boolean.FALSE.toString())) {
                            Boolean bool = Boolean.parseBoolean(entry.getValue().toString());
                            map.put(key, bool);
                            continue;
                        }

                        map.put(key, entry.getValue().toString());
                        continue;
                    }
                }
            } catch (FileNotFoundException ex) {
                ConsoleLogger.log("File not found: " + f.getAbsolutePath(), 2);
            } catch (IOException ex) {
                ConsoleLogger.log("Incorrectly loaded properties from " + f.getAbsolutePath(), 2);
            } finally {
                try {
                    if (fi != null) {
                        fi.close();
                    }
                } catch (IOException ex) {
                    ConsoleLogger.log("##### -- FATAL ERROR -- ##### Failed to store data to " + f.getAbsolutePath(), 2);
                    ex.printStackTrace();
                }
            }
        }

        return map;
    }

    public static void save(String target, Map<String, Object> data) {
        save(target, data, "");
    }

    public static void save(String target, Map<String, Object> data, String destination) {
        // Note: Destination is appended to plugins/ISMain/data
        // For example, when destination is "/channels",
        // the target BASE will be "plugins/ISMain/data/channels/"

        File f = new File(BASE + destination + "/" + target + ".properties");
        FileOutputStream fo = null;

        try {
            if (!f.exists()) {
                f.getParentFile().mkdirs();
                f.createNewFile();
            }

            Properties props = new Properties();
            fo = new FileOutputStream(f);

            for (Map.Entry<String, Object> entry : data.entrySet()) {
                String key = entry.getKey();

                props.setProperty(key, entry.getValue().toString());
            }

            props.store(fo, null);
        } catch (IOException ex) {
            ConsoleLogger.log("Could not create file " + f.getAbsolutePath(), 2);
        } finally {
            try {
                if (fo != null) {
                    fo.close();
                }
            } catch (IOException ex) {
                ConsoleLogger.log("##### -- FATAL ERROR -- ##### Failed to store data to " + f.getAbsolutePath(), 2);
                ex.printStackTrace();
            }
        }
    }
}
