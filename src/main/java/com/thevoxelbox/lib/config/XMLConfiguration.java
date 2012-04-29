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

import com.thevoxelbox.lib.util.Dictionary;
import com.thevoxelbox.lib.util.PropertyListFileManager;
import java.io.File;
import java.util.List;

public class XMLConfiguration {
    
    private Dictionary data = new Dictionary();
    private File file;
    
    public XMLConfiguration(File f) 
    {
        data = PropertyListFileManager.load(f);
    }
    
    public void bindToFile(File f)
    {
        file = f;
    }
    
    public void save()
    {
        PropertyListFileManager.save(data, file);
    }
    
    public void setEntry(String k, Object v) 
    {
        data.put(k, v);
    }
    
    public void setString(String k, String v)
    {
        data.put(k, v);
    }
    
    public void setInt(String k, int i)
    {
        data.put(k, Integer.valueOf(i));
    }
    
    public void setBoolean(String k, boolean b)
    {
        data.put(k, Boolean.valueOf(b));
    }
    
    public void setList(String k, List l)
    {
        data.put(k, l);
    }
    
    public void setDictionary(String k, Dictionary d)
    {
        data.put(k, d);
    }
    
    public Object getEntry(String k)
    {
        if (data.hasKey(k))
            return data.get(k);
        else
            return null;
    }
    
    public String getString(String k)
    {
        if (!data.hasKey(k))
            return null;
        if (!(data.get(k) instanceof String))
            return null;
        
        return data.get(k).toString();
    }
    
    public int getInt(String k)
    {
        if (!data.hasKey(k))
            return -1;
        if (!(data.get(k) instanceof Integer))
            return -1;
        
        return ((Integer) data.get(k)).intValue();
    }
    
    public boolean getBoolean(String k)
    {
        if (!data.hasKey(k))
            return false;
        if (!(data.get(k) instanceof Boolean))
            return false;
        
        return ((Boolean) data.get(k)).booleanValue();
    }
    
    public List<?> getList(String k)
    {
        if (!data.hasKey(k))
            return null;
        if (!(data.get(k) instanceof List<?>))
            return null;
        
        return (List<?>) data.get(k);
    }
    
    public Dictionary getDictionary(String k)
    {
        if (!data.hasKey(k))
            return null;
        if (!(data.get(k) instanceof Dictionary))
            return null;
        
        return (Dictionary) data.get(k);
    }
}
