import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.Toolkit;
import java.io.File;

public final class SoundEffects {
    private static final int MIDI_VOLUME = 90;
    private static final String BACKGROUND_MUSIC_FILE = "sound/jeopardy.wav";

    private static volatile boolean backgroundMusicRunning;
    private static Thread backgroundMusicThread;
    private static Clip backgroundMusicClip;

    private SoundEffects() {
    }

    public static synchronized void startBackgroundMusic() {
        if (backgroundMusicRunning) {
            return;
        }

        backgroundMusicRunning = true;
        backgroundMusicThread = new Thread(SoundEffects::runBackgroundMusicLoop, "WordGame-BackgroundMusic");
        backgroundMusicThread.setDaemon(false);
        backgroundMusicThread.start();
    }

    public static synchronized void stopBackgroundMusic() {
        backgroundMusicRunning = false;

        // Stop the audio clip if it's playing
        if (backgroundMusicClip != null) {
            try {
                backgroundMusicClip.stop();
                backgroundMusicClip.close();
            } catch (Exception exception) {
                // Ignore errors when stopping
            }
            backgroundMusicClip = null;
        }

        if (backgroundMusicThread != null) {
            backgroundMusicThread.interrupt();
            backgroundMusicThread = null;
        }
    }

    public static void playGameStart() {
        playNotesLater(new int[]{60, 64, 67, 72}, new int[]{120, 120, 120, 240});
    }

    public static void playCorrectGuess() {
        playNotesLater(new int[]{76, 79, 83}, new int[]{110, 110, 170});
    }

    public static void playIncorrectGuess() {
        playNotesLater(new int[]{62, 59, 55}, new int[]{140, 140, 220});
    }

    public static void playPhysicalPrize() {
        playNotesLater(new int[]{79, 83, 86}, new int[]{120, 120, 220});
    }

    public static String getAttributionText() {
        return "Sound attribution\n\n"
                + "Background Music: jeopardy.wav comes from https://freesound.org/people/1000kcirtap/sounds/554635/\n\n"
                + "All Freesound sounds are free to use.";
    }

    private static void runBackgroundMusicLoop() {
        try {
            File audioFile = new File("sound", "jeopardy.wav");
            if (!audioFile.exists()) {
                System.out.println("Audio file not found: " + BACKGROUND_MUSIC_FILE);
                return;
            }

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            backgroundMusicClip = AudioSystem.getClip();
            backgroundMusicClip.open(audioInputStream);

            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);

            while (backgroundMusicRunning && !Thread.currentThread().isInterrupted()) {
                sleep(100);
            }

            if (backgroundMusicClip != null) {
                backgroundMusicClip.stop();
                backgroundMusicClip.close();
                backgroundMusicClip = null;
            }
        } catch (Exception exception) {
            System.out.println("Could not play background music: " + exception.getMessage());
        }
    }

    private static void playNotesLater(int[] notes, int[] durationsMs) {
        Thread soundThread = new Thread(() -> playNotes(notes, durationsMs), "WordGame-Sound");
        soundThread.setDaemon(true);
        soundThread.start();
    }

    private static void playNotes(int[] notes, int[] durationsMs) {
        if (notes == null || durationsMs == null || notes.length == 0 || notes.length != durationsMs.length) {
            return;
        }

        if (playMidiNotes(notes, durationsMs)) {
            return;
        }

        fallbackBeep(durationsMs.length);
    }

    private static boolean playMidiNotes(int[] notes, int[] durationsMs) {
        try (Synthesizer synthesizer = MidiSystem.getSynthesizer()) {
            synthesizer.open();
            MidiChannel[] channels = synthesizer.getChannels();
            if (channels == null || channels.length == 0) {
                return false;
            }

            MidiChannel channel = channels[0];
            channel.programChange(0);

            for (int i = 0; i < notes.length; i++) {
                channel.noteOn(notes[i], MIDI_VOLUME);
                sleep(durationsMs[i]);
                channel.noteOff(notes[i]);
            }

            return true;
        } catch (MidiUnavailableException exception) {
            return false;
        }
    }

    private static void fallbackBeep(int beepCount) {
        for (int i = 0; i < beepCount; i++) {
            Toolkit.getDefaultToolkit().beep();
            sleep(120);
        }
    }

    private static void sleep(int durationMs) {
        try {
            Thread.sleep(durationMs);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }
}

