import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class LocationGraphTest {

    @Test
    public void testAddLocation() {
        LocationGraph lg = new LocationGraph();

        assertTrue(lg.addLocation("State College"));
        assertEquals(1, lg.vertices.size());
        assertTrue(lg.addLocation("Hazleton"));
        assertEquals(2, lg.vertices.size());
    }

    @Test
    public void testAddDistance() {
        LocationGraph lg = new LocationGraph();

        lg.addLocation("A");
        lg.addLocation("B");
        lg.addLocation("C");
        lg.addLocation("D");
        lg.addLocation("E");
        lg.addLocation("F");

        lg.addDistance("A", "B", 23);
        lg.addDistance("B", "D", 32);

        assertFalse(lg.addDistance("B", "A", 23));
    }

    @Test
    public void testToString() {
            LocationGraph lg = new LocationGraph();

            lg.addLocation("A");
            lg.addLocation("B");

            // No Edges
            assertEquals("-1", lg.toString());

            // Has Edges
            lg.addDistance("A", "B", 10.0);
            assertEquals("{A, B, 10.0}; {B, A, 10.0}; ", lg.toString());
        }

    @Test
    public void testFindDistanceBFS() {
        LocationGraph lg = new LocationGraph();

        lg.addLocation("A");
        lg.addLocation("B");
        lg.addLocation("C");
        lg.addLocation("D");

        lg.addDistance("A", "B", 10);
        lg.addDistance("B", "C", 15);
        lg.addDistance("C", "D", 35);


        assertEquals(50, lg.findDistanceBFS("B", "D"));
    }

    @Test
    public void testFindDistanceDFS() {
        LocationGraph lg = new LocationGraph();

        lg.addLocation("A");
        lg.addLocation("B");
        lg.addLocation("C");
        lg.addLocation("D");

        lg.addDistance("A", "B", 10);
        lg.addDistance("C", "A", 15);
        lg.addDistance("C", "D", 5);

        assertEquals(15, lg.findDistanceDFS("A", "C"));
    }

    @Test
    public void testDetectCycle() {
        LocationGraph lg = new LocationGraph();

        lg.addLocation("A");
        lg.addLocation("B");
        lg.addLocation("C");
        lg.addLocation("D");
        lg.addLocation("E");
        lg.addLocation("F");

        // No Cycle
        lg.addDistance("A", "B", 12);
        lg.addDistance("B", "C", 12);
        lg.addDistance("C", "D", 12);
        assertFalse(lg.detectCycle());

        LocationGraph LG = new LocationGraph();

        LG.addLocation("A");
        LG.addLocation("B");
        LG.addLocation("C");
        LG.addLocation("D");

        // Has Cycle
        LG.addDistance("A", "B", 10);
        LG.addDistance("C", "A", 10);
        LG.addDistance("D", "C", 10);
        LG.addDistance("B", "D", 10);
        assertTrue(LG.detectCycle());
    }

}
