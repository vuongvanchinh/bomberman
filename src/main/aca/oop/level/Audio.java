package aca.oop.level;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Audio {

    Clip menuMusic;
    private static boolean playAudio = true;

    public Audio(){}
    
    /**
     * turn off if is being on and vice versa.
     */
    public static void turnAudio() {
        playAudio = !(playAudio);
    }

    public void playMenu(){
        if (!playAudio) return;
        try{
            AudioInputStream in1 = AudioSystem.getAudioInputStream(new File("res/sound/menuMusic.wav"));
            menuMusic = AudioSystem.getClip();
            menuMusic.open(in1);
            menuMusic.loop(Clip.LOOP_CONTINUOUSLY);
        }
        catch(Exception e){e.printStackTrace();}
    }

    public void stopMenu(){
        if (!playAudio) return;
        menuMusic.stop();
   }

    public static void playMenuMove(){
        if (!playAudio) return;
        try
        {
            AudioInputStream in = AudioSystem.getAudioInputStream(new File("res/sound/MenuMove.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(in);
            clip.start();
        }
        catch(Exception e){e.printStackTrace();}
    }

    public static void playEntityDie(){
        if (!playAudio) return;
        try{
            AudioInputStream in = AudioSystem.getAudioInputStream(new File("res/sound/entitydie.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(in);
            clip.start();
        }
        catch(Exception e){e.printStackTrace();}
    }

    public static void playPlaceBomb(){
        if (!playAudio) return;
        try{
            AudioInputStream in = AudioSystem.getAudioInputStream(new File("res/sound/bomdrop.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(in);
            clip.start();
        }
        catch(Exception e){e.printStackTrace();}
    }

    public static void playBombExplode(){
        if (!playAudio) return;
        try{//res\sound\BombExplode.wav
            AudioInputStream in = AudioSystem.getAudioInputStream(new File("res/sound/BombExplode.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(in);
            clip.start();
        }
        catch(Exception e){e.printStackTrace();}
    }

    public static void playVictory(){
        if (!playAudio) return;
        try{//"C:\\Users\\Admin\\Documents\\GitHub\\bomberman-starter\\res\\sound\\Victory (mp3cut.net) (1).wav"
            AudioInputStream in = AudioSystem.getAudioInputStream(new File("res/sound/Victory (mp3cut.net) (1).wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(in);
            clip.start();
        }
        catch(Exception e){e.printStackTrace();}
    }

    public void playGameSong(){

    }

    public static void bomberDie(){
        if (!playAudio) return;
        try{
            AudioInputStream in = AudioSystem.getAudioInputStream(new File("res/sound/soundbomberdie.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(in);
            clip.start();
        }
        catch(Exception e){e.printStackTrace();}
    }
    public static void eat(){
        if (!playAudio) return;
        try{
            AudioInputStream in = AudioSystem.getAudioInputStream(new File("res/sound/eat.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(in);
            clip.start();
        }
        catch(Exception e){e.printStackTrace();}
    }
}


