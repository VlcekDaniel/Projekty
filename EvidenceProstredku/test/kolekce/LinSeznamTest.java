package kolekce;

import java.util.Iterator;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author karel@simerda.cz
 */
public class LinSeznamTest {

    private static class TestClass {

        int a;

        public TestClass(int a) {
            this.a = a;
        }

        @Override
        public String toString() {
            return "T" + a;
        }

    }

    private final TestClass T1 = new TestClass(1);
    private final TestClass T2 = new TestClass(2);
    private final TestClass T3 = new TestClass(3);
    private final TestClass T4 = new TestClass(4);
    private final TestClass T5 = new TestClass(5);
    private final TestClass T6 = new TestClass(6);
    private final TestClass T7 = new TestClass(7);
    private final TestClass T8 = new TestClass(8);
    private final TestClass T9 = new TestClass(9);

    public LinSeznamTest() {
    }

    @Test
    public void testNastavPrvni01() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozPrvni(T1);
        instance.nastavPrvni();
        TestClass result = instance.dejPrvni();
        assertEquals(T1, result);
    }

    @Test(expected = KolekceException.class)
    public void testNastavPrvni02() throws KolekceException {
        LinSeznam instance = new LinSeznam();
        instance.nastavPrvni();
        fail();
    }

    @Test
    public void testNastavPosledni01() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozPrvni(T1);
        instance.nastavPosledni();
        TestClass result = instance.dejPosledni();
        assertEquals(T1, result);
    }

    @Test
    public void testVlozPrvni01() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozPrvni(T1);
        TestClass result = instance.dejPrvni();
        assertEquals(T1, result);
    }

    @Test
    public void testVlozPrvni02() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozPrvni(T1);
        instance.vlozPrvni(T2);
        TestClass result = instance.dejPrvni();
        assertEquals(T2, result);
    }

    @Test
    public void testVlozPrvni03() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozPrvni(T1);
        instance.vlozPrvni(T2);
        instance.vlozPrvni(T3);
        TestClass result = instance.dejPrvni();
        assertEquals(T3, result);
    }

    @Test
    public void testDalsi01() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozPrvni(T1);
        instance.vlozPrvni(T2);
        instance.vlozPrvni(T3);
        instance.nastavPrvni();
        instance.dalsi();
        TestClass result = instance.dejAktualni();
        assertEquals(T2, result);
    }

    @Test
    public void testDalsi02() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozPrvni(T1);
        instance.vlozPrvni(T2);
        instance.vlozPrvni(T3);
        instance.nastavPrvni();
        instance.dalsi();
        instance.dalsi();
        TestClass result = instance.dejAktualni();
        assertEquals(T1, result);
    }

    @Test(expected = KolekceException.class)
    public void testDalsi03() throws Exception {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.dalsi();
        fail();
    }

    @Test(expected = KolekceException.class)
    public void testDalsi04() throws Exception {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozPrvni(T1);
        instance.dalsi();
        fail();
    }

    @Test(expected = KolekceException.class)
    public void testDalsi05() throws Exception {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozPrvni(T1);
        instance.vlozPrvni(T2);
        instance.dalsi();
        fail();
    }

    @Test
    public void testDalsi06() throws Exception {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozPrvni(T1);
        instance.vlozPrvni(T2);
        instance.vlozPrvni(T3);
        instance.nastavPrvni();
        instance.dalsi();
        instance.dalsi();
        boolean result = instance.dalsi();
        assertFalse(result);
    }

    @Test(expected = KolekceException.class)
    public void testDalsi07() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozPrvni(T1);
        instance.vlozPrvni(T2);
        instance.vlozPrvni(T3);
        instance.nastavPrvni();
        instance.dalsi();
        instance.dalsi();
        instance.dalsi();
        instance.dalsi();
        fail();
    }

    @Test
    public void testVlozNaKonec01() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozNaKonec(T1);
        TestClass result = instance.dejPosledni();
        assertEquals(T1, result);
    }

    @Test
    public void testVlozNaKonec02() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozNaKonec(T1);
        instance.vlozNaKonec(T2);
        TestClass result = instance.dejPosledni();
        assertEquals(T2, result);
    }

    @Test
    public void testVlozZaAktualni01() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozPrvni(T1);
        instance.nastavPrvni();
        instance.vlozZaAktualni(T2);
        TestClass result = instance.dejPosledni();
        assertEquals(T2, result);
    }

    @Test
    public void testVlozZaAktualni02() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozPrvni(T1);
        instance.nastavPrvni();
        instance.vlozZaAktualni(T2);
        assertEquals(2, instance.size());
    }

    @Test
    public void testVlozZaAktualni03() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozPrvni(T1);
        instance.nastavPrvni();
        instance.vlozZaAktualni(T2);
        instance.vlozZaAktualni(T3);
        assertEquals(3, instance.size());
    }

    @Test
    public void testVlozZaAktualni04() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozPrvni(T1);
        instance.nastavPrvni();
        instance.vlozZaAktualni(T2);
        instance.vlozZaAktualni(T3);
        TestClass result = instance.dejPosledni();
        assertEquals(T2, result);
    }

    @Test
    public void testVlozZaAktualni05() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozPrvni(T1);
        instance.nastavPrvni();
        instance.vlozZaAktualni(T2);
        instance.vlozZaAktualni(T3);
        TestClass result = instance.dejPrvni();
        assertEquals(T1, result);
    }

    @Test
    public void testVlozZaAktualni06() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozPrvni(T1);
        instance.nastavPrvni();
        instance.vlozZaAktualni(T2);
        instance.vlozZaAktualni(T3);
        instance.nastavPrvni();
        instance.dalsi();
        TestClass result = instance.dejAktualni();
        assertEquals(T3, result);
    }

    @Test
    public void testJePrazdny01() {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        assertTrue(instance.jePrazdny());
    }

    @Test
    public void testJePrazdny02() {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozPrvni(T1);
        assertFalse(instance.jePrazdny());
    }

    @Test
    public void testDejPrvni01() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozPrvni(T1);
        assertEquals(T1, instance.dejPrvni());
    }

    @Test(expected = KolekceException.class)
    public void testDejPrvni02() throws Exception {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        assertEquals(T1, instance.dejPrvni());
        fail();
    }

    @Test
    public void testDejPrvni03() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozPrvni(T1);
        instance.vlozPrvni(T2);
        assertEquals(T2, instance.dejPrvni());
    }

    @Test
    public void testDejPosledni01() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozPrvni(T2);
        assertEquals(T2, instance.dejPosledni());
    }

    @Test
    public void testDejPosledni02() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozNaKonec(T2);
        assertEquals(T2, instance.dejPosledni());
    }

    @Test
    public void testDejPosledni03() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozNaKonec(T2);
        instance.vlozNaKonec(T1);
        assertEquals(T1, instance.dejPosledni());
    }

    @Test
    public void testDejAktualni01() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozNaKonec(T2);
        instance.vlozNaKonec(T1);
        instance.nastavPosledni();
        assertEquals(T1, instance.dejAktualni());
    }

    @Test
    public void testDejAktualni02() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozNaKonec(T2);
        instance.vlozNaKonec(T1);
        instance.nastavPrvni();
        assertEquals(T2, instance.dejAktualni());
    }

    @Test
    public void testDejAktualni03() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozNaKonec(T2);
        instance.vlozNaKonec(T1);
        instance.vlozNaKonec(T3);
        instance.nastavPrvni();
        instance.dalsi();
        assertEquals(T1, instance.dejAktualni());
    }

    @Test
    public void testDejAktualni04() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozNaKonec(T2);
        instance.vlozNaKonec(T1);
        instance.vlozNaKonec(T3);
        instance.nastavPrvni();
        instance.dalsi();
        instance.dalsi();
        assertEquals(T3, instance.dejAktualni());
    }

    @Test
    public void testDejZaAktualnim01() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozNaKonec(T2);
        instance.vlozNaKonec(T1);
        instance.vlozNaKonec(T3);
        instance.nastavPrvni();
        assertEquals(T1, instance.dejZaAktualnim());
    }

    @Test
    public void testOdeberAktualni01() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozNaKonec(T2);
        instance.nastavPrvni();
        TestClass result = instance.odeberAktualni();
        assertEquals(T2, result);
    }

    @Test
    public void testOdeberAktualni02() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozNaKonec(T2);
        instance.vlozNaKonec(T1);
        instance.nastavPrvni();
        TestClass result = instance.odeberAktualni();
        assertEquals(T2, result);
    }

    @Test
    public void testOdeberAktualni03() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozNaKonec(T2);
        instance.vlozNaKonec(T1);
        instance.nastavPrvni();
        instance.dalsi();
        TestClass result = instance.odeberAktualni();
        assertEquals(T1, result);
    }

    @Test
    public void testOdeberAktualni04() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozNaKonec(T2);
        instance.vlozNaKonec(T1);
        instance.vlozNaKonec(T3);
        instance.nastavPrvni();
        instance.dalsi();
        instance.dalsi();
        TestClass result = instance.odeberAktualni();
        assertEquals(T3, result);
    }

    @Test
    public void testOdeberAktualni05() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozNaKonec(T2);
        instance.vlozNaKonec(T1);
        instance.vlozNaKonec(T3);
        instance.nastavPrvni();
        TestClass result = instance.odeberAktualni();
        assertEquals(T2, result);
    }

    @Test
    public void testOdeberAktualni06() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozNaKonec(T2);
        instance.nastavPrvni();
        instance.odeberAktualni();
        assertEquals(0, instance.size());
    }

    @Test
    public void testOdeberZaAktualnim01() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozNaKonec(T2);
        instance.vlozNaKonec(T1);
        instance.vlozNaKonec(T3);
        instance.nastavPrvni();
        TestClass result = instance.odeberZaAktualnim();
        assertEquals(T1, result);
    }

    @Test
    public void testOdeberZaAktualnim02() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozNaKonec(T2);
        instance.vlozNaKonec(T1);
        instance.vlozNaKonec(T3);
        instance.nastavPrvni();
        instance.dalsi();
        TestClass result = instance.odeberZaAktualnim();
        assertEquals(T3, result);
    }

    @Test(expected = KolekceException.class)
    public void testOdeberZaAktualnim03() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozNaKonec(T2);
        instance.vlozNaKonec(T1);
        instance.vlozNaKonec(T3);
        instance.nastavPrvni();
        instance.dalsi();
        instance.dalsi();
        instance.odeberZaAktualnim();
        fail();
    }

    @Test
    public void testOdeberAktualni07() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozNaKonec(T1);
        instance.vlozNaKonec(T2);
        instance.vlozNaKonec(T3);
        instance.vlozNaKonec(T4);
        instance.vlozNaKonec(T5);
        instance.vlozNaKonec(T6);
        instance.nastavPrvni();
        instance.odeberAktualni();
        instance.nastavPrvni();
        TestClass result = instance.dejAktualni();
        assertEquals(T2, result);
    }

    @Test
    public void testOdeberAktualni08() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozNaKonec(T1);
        instance.vlozNaKonec(T2);
        instance.vlozNaKonec(T3);
        instance.vlozNaKonec(T4);
        instance.vlozNaKonec(T5);
        instance.vlozNaKonec(T6);
        instance.nastavPrvni();
        instance.dalsi();           
        instance.odeberAktualni();      
        instance.nastavPrvni();
        instance.dalsi();
        TestClass result = instance.dejAktualni();
        assertEquals(T3, result);
    }

    @Test
    public void testOdeberAktualni09() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozNaKonec(T1);
        instance.vlozNaKonec(T2);
        instance.vlozNaKonec(T3);
        instance.vlozNaKonec(T4);
        instance.vlozNaKonec(T5);
        instance.vlozNaKonec(T6);
        instance.nastavPrvni();
        instance.dalsi();
        instance.dalsi();
        instance.odeberAktualni();
        instance.nastavPrvni();
        instance.dalsi();
        instance.dalsi();
        TestClass result = instance.dejAktualni();
        assertEquals(T4, result);
    }

    @Test
    public void testOdeberAktualni10() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozNaKonec(T1);
        instance.vlozNaKonec(T2);
        instance.vlozNaKonec(T3);
        instance.vlozNaKonec(T4);
        instance.vlozNaKonec(T5);
        instance.vlozNaKonec(T6);
        instance.nastavPrvni();
        instance.dalsi();
        instance.dalsi();
        instance.dalsi();
        instance.odeberAktualni();
        instance.nastavPrvni();
        instance.dalsi();
        instance.dalsi();
        instance.dalsi();
        TestClass result = instance.dejAktualni();
        assertEquals(T5, result);
    }

    @Test
    public void testOdeberAktualni11() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozNaKonec(T1);
        instance.vlozNaKonec(T2);
        instance.vlozNaKonec(T3);
        instance.vlozNaKonec(T4);
        instance.vlozNaKonec(T5);
        instance.vlozNaKonec(T6);
        instance.nastavPrvni();
        instance.dalsi();
        instance.dalsi();
        instance.dalsi();
        instance.dalsi();
        instance.odeberAktualni();
        instance.nastavPrvni();
        instance.dalsi();
        instance.dalsi();
        instance.dalsi();
        instance.dalsi();
        TestClass result = instance.dejAktualni();
        assertEquals(T6, result);
    }

    @Test(expected = KolekceException.class)
    public void testOdeberAktualni12() throws KolekceException {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozNaKonec(T1);
        instance.vlozNaKonec(T2);
        instance.vlozNaKonec(T3);
        instance.vlozNaKonec(T4);
        instance.vlozNaKonec(T5);
        instance.vlozNaKonec(T6);
        instance.nastavPrvni();
        instance.dalsi();
        instance.dalsi();
        instance.dalsi();
        instance.dalsi();
        instance.dalsi();
        instance.odeberAktualni();
        instance.nastavPrvni();
        instance.dalsi();
        instance.dalsi();
        instance.dalsi();
        instance.dalsi();
        instance.dalsi();
        instance.dejAktualni();
        fail();
    }

    @Test
    public void testSize01() {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        assertEquals(0, instance.size());
    }

    @Test
    public void testSize02() {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozNaKonec(T2);
        assertEquals(1, instance.size());
    }

    @Test
    public void testZrus01() {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozNaKonec(T2);
        instance.zrus();
        assertEquals(0, instance.size());
    }

    @Test
    public void testIterator01() {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozNaKonec(T2);
        Iterator<TestClass> iterator = instance.iterator();
        assertEquals(T2, iterator.next());
    }

    @Test
    public void testIterator02() {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozNaKonec(T2);
        instance.vlozNaKonec(T1);
        Iterator<TestClass> iterator = instance.iterator();
        assertEquals(T2, iterator.next());
        assertEquals(T1, iterator.next());
    }

    @Test
    public void testIterator03() {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozNaKonec(T2);
        instance.vlozNaKonec(T1);
        instance.vlozNaKonec(T3);
        Iterator<TestClass> iterator = instance.iterator();
        assertEquals(T2, iterator.next());
        assertEquals(T1, iterator.next());
        assertEquals(T3, iterator.next());
    }

    @Test
    public void testIterator04() {
        LinSeznam<TestClass> instance = new LinSeznam<>();
        instance.vlozNaKonec(T2);
        instance.vlozNaKonec(T1);
        instance.vlozNaKonec(T3);
        Iterator<TestClass> iterator = instance.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            iterator.next();
            i++;
        }
        assertEquals(3, i);
    }

}
