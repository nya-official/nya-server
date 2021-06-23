/*
 * Description: Routines to calculate the SHA1 from content
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */

package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Date: 2018-02-27
 * Place: Darmstadt, Germany
 * @author brito
 */
public class SHA1 {

    
    /**
     * Calculate the SHA1 signature from a file on disk.
     * There is no file limit, it can be gigabytes in size that it won't
     * bother our calculations the slightest.
     * @param file
     * @return null when something went wrong, otherwise return the SHA1
     * of the file in lower case
     */
    public static String calculateFromFile(File file){
        try {
            final MessageDigest hashSHA1 = MessageDigest.getInstance("SHA-1");
            final byte[] buffer = new byte[16384];
            int len;
            final InputStream inputStream = new FileInputStream(file);
            // main loop of byte copy, calculate all checksums together
            while((len = inputStream.read(buffer)) >= 0){
                // update the hash for the signature hash (typically SHA1)
                hashSHA1.update(buffer, 0, len);
            }
            // no need to keep this stream open
            inputStream.close();
            // compute the file signatures
            final byte[] digestSHA1 = hashSHA1.digest();
            // all done
            return convertHash(digestSHA1);
        } catch (Exception ex) {
            Logger.getLogger(SHA1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static String calculateFromText(String input) {
        try {
            MessageDigest mDigest = MessageDigest.getInstance("SHA1");
            byte[] result = mDigest.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < result.length; i++) {
                sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(SHA1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Generates an hash from a given string
     * @param byteData
     * @return 
    */
    private static String convertHash(final byte byteData[]){
        //convert the byte to hex format
        @SuppressWarnings("StringBufferMayBeStringBuilder")
        final StringBuilder hexString = new StringBuilder();
    	for (int i=0;i<byteData.length;i++) {
    		final String hex=Integer.toHexString(0xff & byteData[i]);
   	     	if(hex.length()==1) hexString.append('0');
   	     	hexString.append(hex);
    	}
    	return hexString.toString();
    }
    
}
