package CMPT276.Group22;

import org.junit.Test;
import static org.junit.Assert.*;

public class CoordinateTest {
    
    @Test
    public void testGetters() {
        Coordinate coord = new Coordinate(1, 2);
        assertEquals(1, coord.getRow());
        assertEquals(2, coord.getCol());
    }

    @Test
    public void testEquals() {
        Coordinate coord1 = new Coordinate(1, 2);
        Coordinate coord2 = new Coordinate(1, 2);
        Coordinate coord3 = new Coordinate(2, 1);
        
        assertEquals(coord1, coord2);
        assertNotEquals(coord1, coord3);
    }

    @Test
    public void testMove() {
        Coordinate coord = new Coordinate(1, 1);
        
        coord.move(Direction.UP);
        assertEquals(0, coord.getRow());
        assertEquals(1, coord.getCol());
        
        coord.move(Direction.RIGHT);
        assertEquals(0, coord.getRow());
        assertEquals(2, coord.getCol());
    }
}