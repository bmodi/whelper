package ca.svarb.utils;

/**
 * Utility methods to make checking method inputs easier
 * and more consistent
 *
 */
public class ArgumentChecker {

    // Ensure only static access is used and
    // create dummy singleton instance to force
    // Cobertura to account for private constructor
    private ArgumentChecker() {}
    static { new ArgumentChecker(); }
    

    /**
     * Enforce that the given input arguments are not null.
     * Call with paired name/object values,
     * e.g. if your method in class com.example.Moo is:
     * 
     *   <code>foo(String key, String value, String default)</code>
     *   
     * Of which key and value must be non-null, then the following
     * call will check for this:
     * 
     *   <code>ArgumentChecker.checkNulls("key",key,"value",value);
     *  
     *  If a null value, for example, is then passed to the method,
     *  a <code>NullPointerException</code> will be thrown
     *  with the text "com.example.Moo.foo cannot be called with null value".
     *  
     * @param args
     */
    public static void checkNulls(Object... args) {
        if ( args.length%2==1 ) {
            throw new IllegalArgumentException("ArgumentChecker.checkNulls called with unpaired args");
        }
        for (int i = 0; i < args.length; i+=2) {
            if (args[i+1]==null) {
                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
                String className="";
                String methodName = null;
            	for(int j=0;j<stackTrace.length-1;j++) {
            		className = stackTrace[j].getClassName();
                    if ( className!=null && className.endsWith("ArgumentChecker") ){
                        className=stackTrace[j+1].getClassName();
                        methodName = stackTrace[j+1].getMethodName();
                        break;
                    }
            	}
            	if ( methodName==null ) {
                    throw new NullPointerException("Null pointer passed as method argument");
                } else {
                    throw new NullPointerException(className+"."+methodName+" cannot be called with null "+args[i]);
                }
            }
        }
    }
}
