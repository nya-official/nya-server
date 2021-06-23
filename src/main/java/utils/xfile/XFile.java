/*
 * Description: Main functions to encode and decode texts
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package utils.xfile;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Date: 2017
 * Place: Darmstadt, Germany
 * @author brito
 */
public class XFile {

    // define the test mode
    private boolean debug = true;
    private static final String ALGORITHM = "AES";
    
    
    // how we identify the beginning of our key
    public static final String 
            magicString = "=x-file.xyz=",
            textFinish = "==",
            sha1Text = "sha1=";

    private String
            cleanText,
            cypherText,
            key;
    
    
    private String 
            censoredPortion,
            sha1,
            rawFooter;
    
    /**
     * Creates the new XFile object based on a clean text and key that we give.
     * @param cleanText
     * @param key 
     */
    public XFile(String cleanText, String key) {
        this.cleanText = cleanText;
        this.key = key;
        // generate the sha1 for this content
        sha1 = generateStringSHA1(cleanText);
        // something went wrong, don't continue;
        if(sha1 == null){
            return;
        }
        
        ArrayList<String> keywords = getProtectedKeywords(cleanText);
        rawFooter = "sha1="
                + sha1 
                  + "\n";
        // collect and list the keywords one by line
        for(String keyword : keywords){
            rawFooter += keyword + "\n";
        }
        
        log("Footer for the message:");
        log(rawFooter);
        
        // generate the footer text that is dispatched
        String finalFooter = "";
        
        try{
            finalFooter = XFile.magicString
                + encrypt(rawFooter, key);
            
            //finalFooter = finalFooter.replaceAll("(.{60})", "$1\n");
        }catch (Exception e){
            // an error occured, this means the footer won't be valid
        }
        
        final String censoredText = convertText(cleanText);
        cypherText = censoredText + finalFooter;
    }

    /**
     * Will start decoding the text that we have from this document.
     * @return 
     */
    public String getDecodedText() {
        return censoredPortion;
    }

    /**
     * Is this document minimally valid? This does not apply to decryption.
     * This function only verifies that from the surface the censored text
     * seems to be OK.
     * @return 
     */
    public boolean isValid() {
        if(cleanText == null 
                || cypherText == null
                || sha1 == null){
            return false;
        }
        // didn't found the magic text?
        if(cypherText.contains(XFile.magicString) == false){
            return false;
        }
        
        if(cypherText.contains("==") == false){
            return false;
        }
        // calling this just to remove the false code optimization warning
        System.gc();
        // all good
        return true;
    }

    public String getCleanText() {
        return cleanText;
    }

    public String getEncryptedText() {
        return cypherText;
    }

    /**
     * The key that is being used for decrypting the text
     * @return 
     */
    public String getKey() {
        return key;
    }
    
    /**
     * 
     * @param encodedText
     * @param key
     * @return 
     * @throws java.lang.Exception 
     */
    public static XFile decodeText(String encodedText, String key) throws Exception {
        // preflight checks
        if(encodedText == null 
                || encodedText.contains(magicString) == false
                || encodedText.contains(textFinish) == false){
            // the provided text was not good enough
            return null;
        }
        
        // where is the magic string located?
        final int 
                magicStringPosition = encodedText.indexOf(magicString),
                textFinishPosition = encodedText.indexOf(textFinish);
        
        // make sure these texts are on the right order and were found
        if(magicStringPosition > textFinishPosition 
                || magicStringPosition == textFinishPosition){
            return null;
        }
        
        final String 
            // get the text that is blanked
            censoredPortion = encodedText.substring(0, magicStringPosition),
            // the footer text that is encrypted
            encryptedPortion = encodedText.substring
                (magicStringPosition + magicString.length(), 
                        textFinishPosition).replace("\n", "");
            // decrypt the text
            final String decryptedText = decrypt(encryptedPortion, key);
        
        // we expect to read sha1= on the first portion, otherwise this failed.
        if(decryptedText == null 
                || decryptedText.startsWith(sha1Text) == false){
            return null;
        }
        // break the text into lines
        String[] lines = decryptedText.split("\n");
        ArrayList<String> items = new ArrayList();
        // needs to contain at least the SHA1 signature and one item
        if(lines == null || lines.length < 1){
            return null;
        }
        
        // the first line needs to be ignored (contains sha1 signature)
        for(int i = 1; i < lines.length; i++){
            items.add(lines[i]);
        }
        String cleanTextPortion = convertCensorToPlainText(censoredPortion, items);
        
        // When something wrong then the output is null
        if(cleanTextPortion == null){
            return null;
        }
        
        // verify that the text was not tampered (modified)
        String sha1Certified = lines[0].substring(sha1Text.length());
        String sha1Calculated = generateStringSHA1(cleanTextPortion);
        if(sha1Certified.equals(sha1Calculated) == false){
            return null;
        }
        
        
        // create the new XFile object
        XFile export = new XFile(cleanTextPortion, key);
        return export; 
    }
    
    
    /**
     * Outputs a text message to the console, if permitted
     * @param text 
     */
    private void log(String text){
        if(debug == false){
            return;
        }
        // the text to be output
        System.out.println(text);
    }
    
    /**
     * Convert the text into super duper secrete
     * @param text
     * @return 
     */
    static String convertText(String text) {
        String data = text;
        Matcher m = Pattern.compile("\\(\\((.*?)\\)\\)").matcher(text);
        while(m.find()) {
            String snippet = "((" + m.group(1) + "))";
            char[] chars = new char[m.group(1).length()];
            Arrays.fill(chars, '█');
            String censor = new String(chars);
            data = data.replace(snippet, censor);
        }
        return data;
    }

    /**
     * Looks at a given text and returns an array with the words that are
     * involved in double parenthesis. The order of these keywords on the array
     * follows the order when there were found on normal left to right, up to
     * down writing pattern.
     * @param text
     * @return 
     */
    public static ArrayList<String> getProtectedKeywords(String text) {
        ArrayList<String> data = new ArrayList();
        Matcher m = Pattern.compile("\\(\\((.*?)\\)\\)").matcher(text);
        while(m.find()) {
            String snippet = m.group(1);
            data.add(snippet);
        }
        return data;
    }
    
        /**
     * Generates an hash from a given string
     * @param content
     * @return 
    */
    public static String generateStringSHA1(final String content){
        
        try {
            // Create the checksum digest
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(content.getBytes());
            // get the converted bytes
            final byte byteData[] = md.digest();
            
            //convert the byte to hex format
            @SuppressWarnings("StringBufferMayBeStringBuilder")
            final StringBuffer hexString = new StringBuffer();
            for (int i=0;i<byteData.length;i++) {
                String hex=Integer.toHexString(0xff & byteData[i]);
                if(hex.length()==1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException ex) {
        }
        return null;
    }
    
     /**
     * Encrypts the given plain text
     *
     * @param text
     * @param key
     * @return 
     * @throws java.lang.Exception
     */
    public static String encrypt(String text, String key) throws Exception{
        String validKey = makeKey128bits(key);
        byte[] byteKey = validKey.getBytes(StandardCharsets.UTF_8);
        byte[] byteText = text.getBytes(StandardCharsets.UTF_8);
        
        SecretKeySpec secretKey = new SecretKeySpec(byteKey, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        
        byte[] encrypted = cipher.doFinal(byteText);
        
        String encodedPortion = Base64.encodeXX(encrypted) + "==";
        return encodedPortion;
    }
    
    /**
     * Decrypts the given byte array
     *
     * @param encodedMessage
     * @param key
     * @return 
     * @throws java.lang.Exception
     */
    public static String decrypt(String encodedMessage, String key) throws Exception{
        String validKey = makeKey128bits(key);
        byte[] byteKey = validKey.getBytes(StandardCharsets.UTF_8);
        byte[] cipherText = Base64.decodeXX(encodedMessage);
        SecretKeySpec secretKey = new SecretKeySpec(byteKey, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        String result = new String(cipher.doFinal(cipherText), "UTF-8");
        return result;
    }
    
    /**
     * Returns a key that is sized on 128 bits as required for AES.
     * The main purpose is surface encryption, not a deep encryption.
     * @param key
     * @return 
     */
    public static String makeKey128bits(String key){
        final int size = 16; // 16 x8 = 128
        // if the key is too big, return the key truncated
        if(key.length()>  size){
            return key.substring(0, size);
        }
        char[] chars = new char[size - key.length()];
        Arrays.fill(chars, '#');
        return key + new String(chars);
    }
    
    /**
     * Looks for the portions of text covered in ((example)) to generate the
     * encrypted output.
     * @param text
     * @param key
     * @return 
     * @throws java.lang.Exception 
     */
    public static String xFileEncode(String text, String key) throws Exception{
        ArrayList<String> keywords = getProtectedKeywords(text);
        String footer = "" //+ "\n" 
                + "sha1=123213213213213123" 
                + "\n";
        // collect and list the keywords one by line
        for(String keyword : keywords){
            footer += keyword + "\n";
        }
        // generate the footer text that is dispatched
        String finalFooter = 
                "\n"+ XFile.magicString
                + encrypt(footer, key);
        
        String censoredText = convertText(text);
        return censoredText + finalFooter;
    } 
 
    /**
     * Given a censored text and an array of items that fill up that text, we
     * provided a clean text version of what is being stored.
     * @param censoredText
     * @param items
     * @return 
     */
    public static String convertCensorToPlainText(String censoredText, 
            ArrayList<String> items){
        String text = censoredText;
        int counter = 0;
        
        while(text.contains("█")){
            int pos = text.indexOf("█");
            String item = "((" +items.get(counter) + "))";
            text = 
                    text.substring(0, pos) // beginning of text
                    +  item // the text to replace
                    + text.substring(pos + items.get(counter).length()); // rest of text
            counter++; // increase the counter
        }
        
        return text;
    }
    
}
