import org.junit.Test;

import javax.sound.sampled.Line;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedHashMapTest {

    @Test
    public void testInit() {
        LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>();
    }

    @Test
    public void testIter() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();

//        LinearSet<LinkedHashMap.MyEntry<String, String>> set = (LinearSet) map.entrySet();
//        LinkedHashMap.LinearIterator<> iter = set.iterator();
//        while (iter.hasNext()) {
//            LinkedHashMap.MyEntry s = iter.next();
//        }
    }

}