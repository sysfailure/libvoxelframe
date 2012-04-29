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
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class PropertyListFileManager {
    
    public static Dictionary load(File f) 
    {
        try {
            if (!(f.getAbsolutePath().toLowerCase().endsWith(".xml")   || 
                  f.getAbsolutePath().toLowerCase().endsWith(".plist") ||
                  f.getAbsolutePath().toLowerCase().endsWith(".dict"))) {
                
                return new Dictionary();
            }
            
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(f);
            doc.getDocumentElement().normalize();
            
            Element dictElement = doc.getDocumentElement();
            Dictionary dict = DictionaryParser.getDictionaryFromElement(dictElement);
            
            return dict;
            
        } catch (ParserConfigurationException ex) {
            ConsoleLogger.log("Could not create XML DOM parser", 2);
        } catch (IOException ex) {
            ConsoleLogger.log("Could not read XML file", 2);
        } catch (SAXException ex) {
            ConsoleLogger.log("Could not parse XML file", 2);
        }
        
        return new Dictionary();
    }
    
    public static void save(Dictionary dict, File f) 
    {
        try {
            if (!f.exists()) {
                f.mkdirs();
                f.createNewFile();
            }
            
            if (!(f.getAbsolutePath().toLowerCase().endsWith(".xml")   || 
                  f.getAbsolutePath().toLowerCase().endsWith(".plist") ||
                  f.getAbsolutePath().toLowerCase().endsWith(".dict"))) {
                
                ConsoleLogger.log("Attempted to write an unsupported format", 1);
                return;
            }
            
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("plist");
            doc.appendChild(rootElement);
            
            DictionaryParser.bindDictionaryToElement(dict, doc, rootElement);
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(f);
            
            transformer.transform(source, result);
            
        } catch (IOException ex) {
            ConsoleLogger.log("Could not create XML file", 2);
        } catch (ParserConfigurationException ex) {
            ConsoleLogger.log("Could not create instance of XML document builder", 2);
        } catch (TransformerException ex) {
            ConsoleLogger.log("Could not transform raw data into XML", 2);
        }
    }
}
