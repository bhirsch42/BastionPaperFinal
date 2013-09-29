import org.newdawn.slick.*;
import java.io.*;
import java.util.*;

public class Narrator {
	public static Sound missedSomething0;
	public static Sound missedSomething1;
	public static Sound niceAndSlow;
	public static Sound notRushing;
	public static Sound notTakingTheirTime;
	public static Sound opening;
	public static Sound readSoFast;
	public static Sound someWordsNeedHelp;
	public static Sound wayForward;
	public static Sound whereTheyBegan;
	public static Sound wordsFall;

	static {
		Sound temp1 = null;
		Sound temp2 = null;
		Sound temp3 = null;
		Sound temp4 = null;
		Sound temp5 = null;
		Sound temp6 = null;
		Sound temp7 = null;
		Sound temp8 = null;
		Sound temp9 = null;
		Sound temp10 = null;
		Sound temp11 = null;

		try {
			temp1 = new Sound("missedSomething0.wav");
			temp2 = new Sound("missedSomething1.wav");
			temp3 = new Sound("niceAndSlow.wav");
			temp4 = new Sound("notRushing.wav");
			temp5 = new Sound("notTakingtheirTime.wav");
			temp6 = new Sound("opening.wav");
			temp7 = new Sound("readSoFast.wav");
			temp8 = new Sound("someWordsNeedHelp.wav");
			temp9 = new Sound("wayForward.wav");
			temp10 = new Sound("whereTheyBegan.wav");
			temp11 = new Sound("wordsFall.wav");
		}
		catch (SlickException e) {
			e.printStackTrace();
		}
		missedSomething0	= temp1;
		missedSomething1	= temp2;
		niceAndSlow 		= temp3;
		notRushing 			= temp4;
		notTakingTheirTime	= temp5;
		opening 			= temp6;
		readSoFast 			= temp7;
		someWordsNeedHelp 	= temp8;
		wayForward 			= temp9;
		whereTheyBegan 		= temp10;
		wordsFall 			= temp11;
	}

	public static Sound[] allSounds = {
		missedSomething0, missedSomething1,niceAndSlow,
		notRushing, notTakingTheirTime, opening, readSoFast,
		someWordsNeedHelp, wayForward, whereTheyBegan, wordsFall
	};

	public static boolean[] hasBeenSaid = new boolean[allSounds.length];
	public static LinkedList<Sound> soundQueue = new LinkedList<Sound>();

	public static boolean talking() {
		for (Sound s : allSounds) {
			if (s.playing())
				return true;
		}
		return false;
	}

	public static boolean hasSaid(Sound s) {
		for (int i = 0; i < allSounds.length; i++) {
			if (allSounds[i] == s)
				return hasBeenSaid[i];
		}
		return false;
	}

	public static void markSaid(Sound s) {
		for (int i = 0; i < allSounds.length; i++) {
			if (allSounds[i] == s) {
				hasBeenSaid[i] = true;
				return;
			}
		}
	}

	public static void markNotSaid(Sound s) {
		for (int i = 0; i < allSounds.length; i++) {
			if (allSounds[i] == s) {
				hasBeenSaid[i] = false;
				return;
			}
		}		
	}

	public static boolean say(Sound s) {
		if (hasSaid(s))
			return false;
		soundQueue.add(s);
		markSaid(s);
		return true;
	}

	public static void update(GameContainer container, int delta) {
		if (talking() || soundQueue.size() == 0)
			return;
		soundQueue.remove().play();
	}

	public static boolean toggleMissedSomething = true;
	public static void alertMissedSomething(Paper paper) {
		if (!paper.wholePageRevealed(paper.getCurrentPage()-1)){
			if (toggleMissedSomething) {
				toggleMissedSomething = !toggleMissedSomething;
				Narrator.say(Narrator.missedSomething0);
				Narrator.markNotSaid(Narrator.missedSomething1);
			}
			else {
				toggleMissedSomething = !toggleMissedSomething;
				Narrator.say(Narrator.missedSomething1);
				Narrator.markNotSaid(Narrator.missedSomething0);
			}
		}
	}


}