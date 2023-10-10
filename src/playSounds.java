import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.MidiChannel;

/**
 * A little example showing how to play a tune in Java.
 *
 * Inputs are not sanitized or checked, this is just to show how simple it is.
 *
 */
public class playSounds {

    /**
     * Constant representing one of the standard Midi "instruments".
     */
    private final static int PIANO = 0, HARPSICHORD = 6, XYLOPHONE = 13, CHURCH_ORGAN = 19, REED_ORGAN = 20,
            HARMONICA = 22, GUITAR = 24, ELECTRIC_GUITAR = 27, VIOLIN = 40, HARP = 46, TIMPANI = 47, TRUMPET = 56,
            TROMBONE = 57, OBOE = 68, FLUTE = 73, BANJO = 105, STEEL_DRUMS = 114;

    private static List<String> notes = Arrays.asList("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B");
    private static MidiChannel[] channels;
    private static int INSTRUMENT = PIANO;
    private static int VOLUME = 60; // between 0 and 127

    public static void main(String[] args) {

        try {
            // * Open a synthesizer
            Synthesizer synth = MidiSystem.getSynthesizer();
            synth.open();
            channels = synth.getChannels();
            channels[0].programChange(INSTRUMENT);
            play("4A",1000);
            rest(800);

            // 8 adjacent musical notes
            List<String> notes1 = Arrays.asList("5C","5D","5E","5F","5G","5A","5B","6C");

            // play notes in sequence using the List
            for (String n : notes1) {
                play(n, 800);
                rest(200);
            }
            /////////////////
            // TODO: play notes in reverse order using Stack
            rest(1000);

            /////////////////
            // TODO: play notes in the same order using Queue
            rest(1000);

            /////////////////
            // TODO: play notes in mixed order (one from front, one from back) using Deque
            rest(1000);

            /////////////////
            // TODO: play notes in random order using ArrayList
            // Use the following method to generate random numbers
            // int randomNum = ThreadLocalRandom.current().nextInt(arrayList1.size());
            rest(1000);

            /////////////////

            // * finish up
            synth.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Plays the given note for the given duration
     */
    private static void play(String note, int duration) throws InterruptedException {
        // * start playing a note
        channels[0].noteOn(id(note), VOLUME);
        // * wait
        Thread.sleep(duration);
        // * stop playing a note
        channels[0].noteOff(id(note));
    }

    /**
     * Plays nothing for the given duration
     */
    private static void rest(int duration) throws InterruptedException {
        Thread.sleep(duration);
    }

    /**
     * Returns the MIDI id for a given note: eg. 4C -> 60
     *
     * @return
     */
    private static int id(String note) {
        int octave = Integer.parseInt(note.substring(0, 1));
        return notes.indexOf(note.substring(1)) + 12 * octave + 12;
    }
}