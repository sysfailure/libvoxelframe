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
package com.thevoxelbox.lib.permissions;

import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class OpPermissionsHandler extends PermissionsHandler { // BARE BONES AT ITS FINEST :D

    @Override
    public PermissionsHandler initialize(Server server) 
    {
        return new OpPermissionsHandler(server);
    }
    
    public OpPermissionsHandler(Server srv) 
    {
        super(srv);
    }

    @Override
    public String getDetectionMessage() 
    {
        return "Using default OP permissions";
    }

    @Override
    public boolean hasPermission(String name, String permission) 
    {
        OfflinePlayer op = server.getOfflinePlayer(name);
        Player p = op.getPlayer();
        
        if (p != null) {
            return p.isOp();
        } else {
            return op.isOp();
        }
    }

    @Override
    public boolean hasPermission(String world, String name, String permission) 
    {
        OfflinePlayer op = server.getOfflinePlayer(name);
        Player p = op.getPlayer();
        
        if (p != null) {
            return p.isOp();
        } else {
            return op.isOp();
        }
    }

    @Override
    public void givePermission(String world, String name, String permission) 
    {
        // Not supported
    }

    @Override
    public void givePermission(String name, String permission) 
    {
        // Not supported
    }

    @Override
    public void removePermission(String world, String name, String permission) 
    {
        // Not supported
    }

    @Override
    public void removePermission(String name, String permission) 
    {
        // Not supported
    }

    @Override
    public boolean inGroup(String name, String group) 
    {
        OfflinePlayer op = server.getOfflinePlayer(name);
        Player p = op.getPlayer();
        
        if (p != null) {
            if (p.isOp())
                return group.equalsIgnoreCase("op");
            else
                return !group.equalsIgnoreCase("op");
        } else {
            if (op.isOp())
                return group.equalsIgnoreCase("op");
            else
                return !group.equalsIgnoreCase("op");
        }
    }

    @Override
    public String[] getGroups(String name) 
    {
        OfflinePlayer op = server.getOfflinePlayer(name);
        Player p = op.getPlayer();
        
        if (p != null) {
            if (p.isOp())
                return new String[] {"op"};
            else
                return null;
        } else {
            if (op.isOp())
                return new String[] {"op"};
            else
                return null;
        }
    }

    @Override
    public void addGroup(String username, String groupname) 
    {
        // Not supported
    }

    @Override
    public void removeGroup(String username, String groupname) 
    {
        // Not supported
    }

    @Override
    public void giveGroupPermission(String world, String name, String permission) 
    {
        // Not supported
    }

    @Override
    public void giveGroupPermission(String name, String permission) 
    {
        // Not supported
    }

    @Override
    public void removeGroupPermission(String world, String name, String permission) 
    {
        // Not supported
    }

    @Override
    public void removeGroupPermission(String name, String permission) 
    {
        // Not supported
    }   
}
