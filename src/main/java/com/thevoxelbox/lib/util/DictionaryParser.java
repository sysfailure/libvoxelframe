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

import java.util.List;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DictionaryParser {
    
    public static Dictionary getDictionaryFromElement(Element element) 
    {
        Dictionary ret = new Dictionary();

        NodeList l = element.getChildNodes();
        String lastKey = "";
        
        for (int i = 0; i < l.getLength(); ++i) {
            Node node = l.item(i);
            
            if ("key".equalsIgnoreCase(node.getNodeName())) {
                lastKey = node.getTextContent();
                continue;
            } else if ("string".equalsIgnoreCase(node.getNodeName())) {
                if (!lastKey.equals("")) {
                    ret.put(lastKey, node.getTextContent());
                }
                
                continue;
            } else if ("integer".equalsIgnoreCase(node.getNodeName())) {
                if (!lastKey.equals("")) {
                    ret.put(lastKey, Integer.parseInt(node.getTextContent()));
                }
                
                continue;
            } else if ("boolean".equalsIgnoreCase(node.getNodeName())) {
                if (!lastKey.equals("")) {
                    ret.put(lastKey, Boolean.parseBoolean(node.getTextContent()));
                }
                
                continue;
            } else if ("list".equalsIgnoreCase(node.getNodeName())) {
                if (!lastKey.equals("")) {
                    ret.put(lastKey, ListParser.getListFromElement((Element) node));
                }
                
                continue;
            } else if ("dict".equalsIgnoreCase(node.getNodeName())) {
                if (!lastKey.equals("")) {
                    ret.put(lastKey, getDictionaryFromElement((Element) node));
                }
                
                continue;
            }
        }
        
        return ret;
    }
    
    public static void bindDictionaryToElement(Dictionary dict, Document doc, Element element)
    {
        for (Map.Entry<String, Object> entry : dict) {
            String k = entry.getKey();
            Object v = entry.getValue();
            
            Element key = doc.createElement("key");
            key.appendChild(doc.createTextNode(k));
            element.appendChild(key);
            
            if (v instanceof String) {
                Element value = doc.createElement("string");
                value.appendChild(doc.createTextNode(v.toString()));
                element.appendChild(value);
            } else if (v instanceof Integer) {
                Element value = doc.createElement("integer");
                value.appendChild(doc.createTextNode(((Integer) v).toString()));
                element.appendChild(value);
            } else if (v instanceof Boolean) {
                Element value = doc.createElement("boolean");
                value.appendChild(doc.createTextNode(((Boolean) v).toString()));
                element.appendChild(value);
            } else if (v instanceof List<?>) {
                Element value = doc.createElement("list");
                ListParser.bindListToElement((List<Object>) v, doc, value);
                element.appendChild(value);
            } else if (v instanceof Dictionary) {
                Element value = doc.createElement("dict");
                bindDictionaryToElement((Dictionary) v, doc, value);
                element.appendChild(value);
            }
        }
    }
}
