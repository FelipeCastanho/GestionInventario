package test.java;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({TransaccionProductoTestCase.class, TransaccionTestCase.class, ProductoTestCase.class })
public class AllTests {

}
