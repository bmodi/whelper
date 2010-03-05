package ca.svarb.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import ca.svarb.utils.ArgumentChecker;

public class ArgumentCheckerTest {

    @Test
    public void testOneArgument() {
        String arg1="test";
        ArgumentChecker.checkNulls("arg1", arg1);
    }

    @Test
    public void testOneArgumentNull() {
        try {
            String arg1=null;
            ArgumentChecker.checkNulls("arg1", arg1);
            fail("Expected exception");
        } catch (NullPointerException e) {
            assertEquals("ca.svarb.utils.ArgumentCheckerTest.testOneArgumentNull cannot be called with null arg1", e.getMessage());
        }
    }

    @Test
    public void testMultipleArguments() {
        String arg1="test";
        String arg2="test";
        String arg3="test";
        ArgumentChecker.checkNulls("arg1", arg1, "arg2", arg2, "arg3", arg3);
    }


    @Test
    public void testMultipleArgumentsNull() {
        try {
            String arg1="test";
            String arg2=null;
            String arg3="test";
            ArgumentChecker.checkNulls("arg1", arg1, "arg2", arg2, "arg3", arg3);
            fail("Expected exception");
        } catch (NullPointerException e) {
            assertEquals("ca.svarb.utils.ArgumentCheckerTest.testMultipleArgumentsNull cannot be called with null arg2", e.getMessage());
        }
    }

    @Test(expected=IllegalArgumentException.class)
    public void testSingleUnpairedArgument() {
        ArgumentChecker.checkNulls("arg1");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testMultipleUnpairedArgument() {
        String arg1="test";
        String arg2=null;
        ArgumentChecker.checkNulls("arg1", arg1, "arg2", arg2, "arg3");
    }
}
