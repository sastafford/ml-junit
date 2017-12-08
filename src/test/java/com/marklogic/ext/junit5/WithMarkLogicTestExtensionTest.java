package com.marklogic.ext.junit5;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MarkLogicTest.class)
public class WithMarkLogicTestExtensionTest {

    @Test
    public void helloTest() {
        assertEquals(2, 2);
    }
}
