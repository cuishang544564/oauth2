package com.twenty.test;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author cuishang
 * @since 2020/1/15
 */
public class PasswordEncoder {

    @Test
    public void testEncode(){
        System.out.println(new BCryptPasswordEncoder(16).encode("pwd"));
        //$2a$16$sZkttjHJAe9jEiPbQN6F8OaCmwjJ40ihDHqBzpPLydQSw16kkXQX6
    }
}
