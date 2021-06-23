/*
 * Description: Connect to a public IPFS gateway
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package database;

import static base.Log.log;
import base.core;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;
import java.io.IOException;

/**
 * Date: 2021-06-15 Place: Zwingenberg, Germany
 *
 * @author brito
 */
public class ConnectIPFS {

    IPFS ipfs = null;

    public void start() {
        // connect the instance
        try{
            ipfs = new IPFS("/dnsaddr/" + core.genesis.gateway);
        }catch (Exception e){
            log("Error IPFS-30: Failed to connect");
        }

    }

    /**
     * Can we use the IPFS instance?
     *
     * @return
     */
    public boolean canBeUsed() {
        return ipfs != null;
    }

    /**
     * Places bytes on the network and provides back the id
     * @param bytes 
     * @return null when something went wrong
     */
    public String putContent(byte[] bytes) {
        try {
            NamedStreamable.ByteArrayWrapper bytearray = new NamedStreamable.ByteArrayWrapper(bytes);
            MerkleNode response = ipfs.add(bytearray).get(0);
            return response.hash.toBase58();
        } catch (IOException ex) {
            log("ERROR IPFS-53: Unable to put content");
            return null;
        }
    }
    
    /**
     * Puts the content as text file
     * @param text
     * @return 
     */
    public String putContentAsText(String text) {
        return putContent(text.getBytes());
    }

    /**
     * Gets the binary version of a file (no checking about file size)
     *
     * @param id
     * @return null when something went wrong
     */
    public byte[] getContent(String id) {
        if(id == null){
            return null;
        }
        try {
            Multihash multihash = Multihash.fromBase58(id);
            //TODO: Add file size download restrictions for the future
            //InputStream inputStream = ipfs.catStream(multihash);
            return ipfs.cat(multihash);
        } catch (IOException ex) {
            log("ERROR IPFS-72: Unable to get content");
            return null;
        }
    }

    public String getContentAsString(String id) {
        byte[] bytes = this.getContent(id);
        if (bytes == null) {
            return null;
        }
        // convert to string
        return new String(bytes);
    }

    public void pin(String id) {
        try {
            Multihash multihash = Multihash.fromBase58(id);
            ipfs.pin.add(multihash);
        } catch (IOException ex) {
            log("ERROR IPFS-91: Unable to pin content");
        }
    }

    public void pinRemove(String id) {
        try {
            Multihash multihash = Multihash.fromBase58(id);
            ipfs.pin.rm(multihash);
        } catch (IOException ex) {
            log("ERROR IPFS-100: Unable to remove pin content");
        }
    }

}
