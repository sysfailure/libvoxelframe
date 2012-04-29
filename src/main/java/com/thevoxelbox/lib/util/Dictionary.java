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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Dictionary implements Iterable<Map.Entry<String, Object>> {
    
    private Map<String, Object> map = new HashMap<String, Object>();
    
    public void put(String k, Object v) 
    {
        try {
            if (k.contains(".")) {
                String[] nodes = k.split(".");

                Dictionary fetch = this; // temp pointer to this dictionary

                for (int i = 0; i < nodes.length; ++i) {
                    if (i == (nodes.length)) {
                        fetch.map.put(nodes[i], v);
                        return;
                    } else {
                        fetch = (Dictionary) fetch.map.get(nodes[i]);
                    }
                }
            }
        } catch (NullPointerException ex) {
            return;
        }
        
        map.put(k, v);
    }
    
    public void putAll(Dictionary dict) 
    {
        for (Map.Entry<String, Object> entry : dict) {
            put(entry.getKey(), entry.getValue());
        }
    }
    
    public Object get(String k) 
    {
        if (map.containsKey(k))
            return map.get(k);
        
        try {
            if (k.contains(".")) {
                String[] nodes = k.split(".");

                Dictionary fetch = this; // temp pointer to this dictionary

                for (int i = 0; i < nodes.length; ++i) {
                    if (i == (nodes.length)) {
                        if (fetch.map.containsKey(nodes[i]))
                            return fetch.map.get(nodes[i]);
                    } else {
                        fetch = (Dictionary) fetch.map.get(nodes[i]);
                    }
                }
            }
        } catch (NullPointerException ex) {
            return null;
        }
        
        return null;
    }
    
    public boolean hasKey(String k) 
    {
        if (map.containsKey(k))
            return true;
        
        try {
            if (k.contains(".")) {
                String[] nodes = k.split(".");

                Dictionary fetch = this; // temp pointer to this dictionary

                for (int i = 0; i < nodes.length; ++i) {
                    if (i == (nodes.length)) {
                        if (fetch.map.containsKey(nodes[i]))
                            return true;
                    } else {
                        fetch = (Dictionary) fetch.map.get(nodes[i]);
                    }
                }
            }
        } catch (NullPointerException ex) {
            return false;
        }
        
        return false;
    }
    
    public boolean isClear() 
    {
        return map.isEmpty();
    }
    
    public void clear() 
    {
        map.clear();
    }

    @Override
    public Iterator<Map.Entry<String, Object>> iterator() 
    {
        return map.entrySet().iterator();
    }
}
