import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordleBotTest {

    private WordleBot bot;

    @BeforeEach
    public void setup() throws FileNotFoundException {
        bot = new WordleBot("wordle-La.txt", "wordle-Ta.txt");
    }

    @Test
    public void testGetPattern() {

        assertEquals("yyyyy", bot.getPattern("eabcd", "abcde"));

        assertEquals("ybbbg", bot.getPattern("error", "femur"));
        assertEquals("bgybb", bot.getPattern("eerie", "femur"));
        assertEquals("bbbbb", bot.getPattern("waist", "femur"));
        assertEquals("bbbbb", bot.getPattern("chalk", "femur"));
        assertEquals("bygbb", bot.getPattern("bumpy", "femur"));
        assertEquals("ggggg", bot.getPattern("femur", "femur"));

        assertEquals("bybyb", bot.getPattern("sever", "elope"));
        assertEquals("ybbby", bot.getPattern("pupil", "elope"));
        assertEquals("bybyg", bot.getPattern("spree", "elope"));
        assertEquals("bgyyy", bot.getPattern("bleep", "elope"));
        assertEquals("ggggg", bot.getPattern("elope", "elope"));

        assertEquals("bbybb", bot.getPattern("trust", "dumpy"));
        assertEquals("bgggg", bot.getPattern("bumpy", "dumpy"));
        assertEquals("bbbgg", bot.getPattern("poppy", "dumpy"));
        assertEquals("ggggg", bot.getPattern("dumpy", "dumpy"));

        assertEquals("bbbbb", bot.getPattern("eerie", "comfy"));
        assertEquals("bggbb", bot.getPattern("momma", "comfy"));
        assertEquals("bbbbg", bot.getPattern("slyly", "comfy"));
        assertEquals("bbgbg", bot.getPattern("bumpy", "comfy"));
        assertEquals("bbbyb", bot.getPattern("thick", "comfy"));
        assertEquals("ggggg", bot.getPattern("comfy", "comfy"));

        assertEquals("bybbb", bot.getPattern("holla", "cross"));
        assertEquals("ybgbb", bot.getPattern("shoot", "cross"));
        assertEquals("bgggb", bot.getPattern("prosy", "cross"));
        assertEquals("bgggg", bot.getPattern("gross", "cross"));
        assertEquals("ggggg", bot.getPattern("cross", "cross"));

    }

    @Test
    public void testSubmit() {

        System.out.println(bot.submit(null));
        System.out.println(bot.submit("bbbbb"));

    }

}
