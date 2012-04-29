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

package com.thevoxelbox.lib.config;

import com.thevoxelbox.lib.util.PropertyManager;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PropertyConfiguration {
    private HashMap<String, Object> map = new HashMap<String, Object>();
    private String target;
    private String destination;
    
    public PropertyConfiguration(String target) 
    {
        HashMap<String, Object> data = (HashMap<String, Object>) PropertyManager.load(target);
        
        if (data == null)
            map.clear();
        else
            map = data;
        
        this.target = target;
        this.destination = "";
    }
    
    public PropertyConfiguration(String target, String destination) 
    {
        HashMap<String, Object> data = (HashMap<String, Object>) PropertyManager.load(target, destination);
        
        if (data == null)
            map.clear();
        else
            map = data;
        
        this.target = target;
        this.destination = destination;
    }
    
    public void load() 
    {
        HashMap<String, Object> data = (HashMap<String, Object>) PropertyManager.load(target, destination);
        
        if (data == null)
            return;
        else {
            // Populate map
            
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                if (entry.getValue() != null)
                    setEntry(entry.getKey(), entry.getValue());
            }
        }
    }
    
    public void assignTarget(String target) 
    {
        this.target = target;
    }
    
    public void assignDestination(String dest) 
    {
        this.destination = dest;
    }
    
    public void save() 
    {
        if (map == null || map.isEmpty()) {
            clear();
        }
        
        PropertyManager.save(target, map, destination);
    }
    
    public void clear() 
    {
        if (!map.isEmpty())
            map.clear();
        
        File f = new File(PropertyManager.BASE + destination + "/" + target + ".properties");
        f.delete();
        
        try {
            f.createNewFile();
        } catch (IOException ex) {
            // Oh well.
        }
    }
    
    public HashMap<String, Object> getAllEntries() 
    {
        return map;
    }
    
    public void removeEntry(String key) 
    {
        map.remove(key);
    }
    
    public boolean hasEntry(String key) {
        return map.containsKey(key);
    }
    
    public Object getEntry(String key) {
        if (map == null)
            return null;
        else if (!map.containsKey(key))
            return null;
        else
            return map.get(key);
    }
    
    public String getString(String key) {
        if (map == null)
            return null;
        else if (!map.containsKey(key))
            return null;
        else if (!(map.get(key) instanceof String))
            return null;
        else
            return map.get(key).toString();
    }
    
    public boolean getBoolean(String key) {
        if (map == null)
            return false;
        else if (!map.containsKey(key))
            return false;
        else if (!(map.get(key) instanceof Boolean))
            return false;
        else
            return ((Boolean) map.get(key)).booleanValue();
    }
    
    public int getInt(String key) {
        if (map == null)
            return -1;
        else if (!map.containsKey(key))
            return -1;
        else if (!(map.get(key) instanceof Integer))
            return -1;
        else
            return ((Integer) map.get(key)).intValue();
    }
    
    public double getDouble(String key) {
        if (map == null)
            return -1;
        else if (!map.containsKey(key))
            return -1;
        else if (!(map.get(key) instanceof Double))
            return -1;
        else
            return ((Double) map.get(key)).doubleValue();
    }
    
    public void setEntry(String key, Object value) {
        if (map == null)
            return;
        
        map.put(key, value);
    }
    
    public void setString(String key, String value) {
        if (map == null)
            return;
        
        map.put(key, value);
    }
    
    public void setBoolean(String key, boolean value) {
        if (map == null)
            return;
        
        map.put(key, Boolean.valueOf(value));
    }
    
    public void setInt(String key, int value) {
        if (map == null)
            return;
        
        map.put(key, Integer.valueOf(value));
    }
    
    public void setDouble(String key, double value) {
        if (map == null)
            return;
        
        map.put(key, Double.valueOf(value));
    }
}
