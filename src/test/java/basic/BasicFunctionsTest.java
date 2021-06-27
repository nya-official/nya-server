/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basic;

import channel.Channel;
import org.testng.Assert;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import user.User;

/**
 *
 * @author brito
 */
public class BasicFunctionsTest {
    
    public BasicFunctionsTest() {
    }

    
    @Test
    public void testCleanChannelName() {
        Assert.assertEquals("213123", utils.text.onlyValidChars("213123"));
        Assert.assertNotEquals("213123", utils.text.onlyValidChars("21312"));
        Assert.assertEquals("channel", utils.text.onlyValidChars("#channel"));
        Assert.assertEquals("channel", utils.text.onlyValidChars("$channel"));
        Assert.assertEquals("channel", utils.text.onlyValidChars("channel!"));
        Assert.assertEquals("channel_1", utils.text.onlyValidChars("channel_1"));
        Assert.assertEquals("channel-1", utils.text.onlyValidChars("channel-1"));
        Assert.assertEquals("channel1", utils.text.onlyValidChars("channel 1"));
        
        // test the name functions
        Assert.assertTrue(Channel.isValidName("#channel1"));
        Assert.assertFalse(Channel.isValidName("channel1"));
        Assert.assertFalse(Channel.isValidName("##channel1"));
        Assert.assertFalse(Channel.isValidName("#channel1#"));
        Assert.assertFalse(Channel.isValidName("#channel1 "));
        Assert.assertFalse(Channel.isValidName(" #channel1"));
        Assert.assertFalse(Channel.isValidName("#chan|nel1"));
    }
    
    
    @Test
    public void testJSON() {
        User user = new User();
        user.setId("test");
        String text = user.jsonExport();
        System.gc();
    }
}
