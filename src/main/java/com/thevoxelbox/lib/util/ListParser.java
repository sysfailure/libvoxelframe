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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ListParser {
    
    public static List<Object> getListFromElement(Element element) 
    {
        List<Object> ret = new ArrayList<Object>();
        
        NodeList l = element.getChildNodes();
        
        for (int i = 0; i < l.getLength(); ++i) {
            Node node = l.item(i);
            
            if ("string".equalsIgnoreCase(node.getNodeName())) {
                ret.add(node.getTextContent());
                continue;
            } else if ("integer".equalsIgnoreCase(node.getNodeName())) {
                ret.add(Integer.parseInt(node.getTextContent()));
                continue;
            } else if ("boolean".equalsIgnoreCase(node.getNodeName())) {
                ret.add(Boolean.parseBoolean(node.getTextContent()));
                continue;
            } else if ("list".equalsIgnoreCase(node.getNodeName())) {
                ret.add(getListFromElement((Element) node));
                continue;
            } else if ("dict".equalsIgnoreCase(node.getNodeName())) {
                ret.add(DictionaryParser.getDictionaryFromElement((Element) node));
                continue;
            }
        }
        
        return ret;
    }
    
    public static void bindListToElement(List<Object> l, Document doc, Element e) {
        
        Iterator<Object> it = l.listIterator();
        
        while (it.hasNext()) {
            Object next = it.next();
            
            if (next instanceof String) {
                Element value = doc.createElement("string");
                value.appendChild(doc.createTextNode(next.toString()));
                e.appendChild(value);
            } else if (next instanceof Integer) {
                Element value = doc.createElement("integer");
                value.appendChild(doc.createTextNode(((Integer) next).toString()));
                e.appendChild(value);
            } else if (next instanceof Boolean) {
                Element value = doc.createElement("boolean");
                value.appendChild(doc.createTextNode(((Boolean) next).toString()));
                e.appendChild(value);
            } else if (next instanceof List<?>) {
                Element value = doc.createElement("list");
                bindListToElement((List<Object>) next, doc, value);
                e.appendChild(value);
            } else if (next instanceof Dictionary) {
                Element value = doc.createElement("dict");
                DictionaryParser.bindDictionaryToElement((Dictionary) next, doc, value);
                e.appendChild(value);
            }
        }
    }
}
