package CMPT276.Group22;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class SoundManagerTest {
    private SoundManager soundManager;

    @Before
    public void setUp() {
        soundManager = SoundManager.getInstance();
    }

    @Test
    public void testSingletonInstance() {
        // Test singleton pattern
        SoundManager instance1 = SoundManager.getInstance();
        SoundManager instance2 = SoundManager.getInstance();
        assertSame("Should return same instance", instance1, instance2);
    }

    @Test
    public void testMuteFunctionality() {
        // Only test toggle functionality
        try {
            soundManager.toggleMute();
            soundManager.toggleMute();
            assertTrue(true);
        } catch (Exception e) {
            fail("Should not throw exception during mute operations");
        }
    }

    @Test
    public void testVolumeControl() {
        try {
            soundManager.setVolume(0.5f);
            assertTrue(true);
        } catch (Exception e) {
            fail("Should not throw exception during volume operations");
        }
    }

    @Test
    public void testBackgroundMusicControl() {
        try {
            soundManager.pauseBackgroundMusic();
            soundManager.resumeBackgroundMusic();
            soundManager.stopBackgroundMusic();
            assertTrue(true);
        } catch (Exception e) {
            fail("Should not throw exception during music control operations");
        }
    }
}