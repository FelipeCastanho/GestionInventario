package test.java;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ DevolucionTestCase.class, TransaccionProductoTestCase.class, TransaccionTestCase.class })
public class AllTests {

}
