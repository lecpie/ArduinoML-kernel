package main.groovy.groovuinoml.morse_dsl;

import static main.groovy.groovuinoml.morse_dsl.Morse_Type.*;

import java.util.*;

/**
 * Created by fofo on 26/01/16.
 */
public class MorseAlphabet {

    private static final Map<Character, Collection<Morse_Type>> morse_translation;
    static
    {
        morse_translation = new HashMap<Character, Collection<Morse_Type>>();
        morse_translation.put('a', Arrays.asList(SHORT_MORSE,LONG_MORSE));
        morse_translation.put('b', Arrays.asList(LONG_MORSE, SHORT_MORSE,SHORT_MORSE,SHORT_MORSE));
        morse_translation.put('c', Arrays.asList(LONG_MORSE,SHORT_MORSE,LONG_MORSE,SHORT_MORSE));
        morse_translation.put('d', Arrays.asList(LONG_MORSE, SHORT_MORSE,SHORT_MORSE));
        morse_translation.put('e', Arrays.asList(SHORT_MORSE));
        morse_translation.put('f', Arrays.asList(SHORT_MORSE, SHORT_MORSE,LONG_MORSE,SHORT_MORSE));
        morse_translation.put('g', Arrays.asList(LONG_MORSE, LONG_MORSE, SHORT_MORSE));
        morse_translation.put('h', Arrays.asList(SHORT_MORSE, SHORT_MORSE,SHORT_MORSE,SHORT_MORSE));
        morse_translation.put('i', Arrays.asList(SHORT_MORSE, SHORT_MORSE));
        morse_translation.put('j', Arrays.asList(SHORT_MORSE, LONG_MORSE, LONG_MORSE, LONG_MORSE));
        morse_translation.put('k', Arrays.asList(LONG_MORSE, SHORT_MORSE, LONG_MORSE));
        morse_translation.put('l', Arrays.asList(SHORT_MORSE, LONG_MORSE, SHORT_MORSE, SHORT_MORSE));
        morse_translation.put('m', Arrays.asList(LONG_MORSE, LONG_MORSE));
        morse_translation.put('n', Arrays.asList(LONG_MORSE, SHORT_MORSE));
        morse_translation.put('o', Arrays.asList(LONG_MORSE, LONG_MORSE, LONG_MORSE));
        morse_translation.put('p', Arrays.asList(SHORT_MORSE, LONG_MORSE, LONG_MORSE, SHORT_MORSE));
        morse_translation.put('q', Arrays.asList(LONG_MORSE, LONG_MORSE, SHORT_MORSE, LONG_MORSE));
        morse_translation.put('r', Arrays.asList(SHORT_MORSE,LONG_MORSE, SHORT_MORSE));
        morse_translation.put('s', Arrays.asList(SHORT_MORSE,SHORT_MORSE, SHORT_MORSE));
        morse_translation.put('t', Arrays.asList(LONG_MORSE));
        morse_translation.put('u', Arrays.asList(SHORT_MORSE, SHORT_MORSE, LONG_MORSE));
        morse_translation.put('v', Arrays.asList(SHORT_MORSE, SHORT_MORSE,SHORT_MORSE, LONG_MORSE));
        morse_translation.put('w', Arrays.asList(SHORT_MORSE, LONG_MORSE, LONG_MORSE));
        morse_translation.put('x', Arrays.asList(LONG_MORSE, SHORT_MORSE, SHORT_MORSE, LONG_MORSE));
        morse_translation.put('y', Arrays.asList(LONG_MORSE,SHORT_MORSE, LONG_MORSE, LONG_MORSE));
        morse_translation.put('z', Arrays.asList(LONG_MORSE, LONG_MORSE, SHORT_MORSE, SHORT_MORSE));

        morse_translation.put('0', Arrays.asList(LONG_MORSE, LONG_MORSE,LONG_MORSE,LONG_MORSE,LONG_MORSE));
        morse_translation.put('1', Arrays.asList(SHORT_MORSE,LONG_MORSE,LONG_MORSE,LONG_MORSE,LONG_MORSE));
        morse_translation.put('2', Arrays.asList(SHORT_MORSE,SHORT_MORSE, LONG_MORSE,LONG_MORSE,LONG_MORSE));
        morse_translation.put('3', Arrays.asList(SHORT_MORSE,SHORT_MORSE,SHORT_MORSE,LONG_MORSE,LONG_MORSE));
        morse_translation.put('4', Arrays.asList(SHORT_MORSE,SHORT_MORSE,SHORT_MORSE,SHORT_MORSE,LONG_MORSE));
        morse_translation.put('5', Arrays.asList(SHORT_MORSE,SHORT_MORSE,SHORT_MORSE,SHORT_MORSE,SHORT_MORSE));
        morse_translation.put('6', Arrays.asList(LONG_MORSE,SHORT_MORSE,SHORT_MORSE,SHORT_MORSE,SHORT_MORSE));
        morse_translation.put('7', Arrays.asList(LONG_MORSE,LONG_MORSE,SHORT_MORSE,SHORT_MORSE,SHORT_MORSE));
        morse_translation.put('8', Arrays.asList(LONG_MORSE,LONG_MORSE,LONG_MORSE, SHORT_MORSE, SHORT_MORSE));
        morse_translation.put('9', Arrays.asList(LONG_MORSE,LONG_MORSE,LONG_MORSE,LONG_MORSE,SHORT_MORSE));
    }
    public Collection<Morse_Type> getListByLetter(Character letter){
        return this.morse_translation.get(letter);
    }
}
