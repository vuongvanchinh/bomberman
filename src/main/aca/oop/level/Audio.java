package aca.oop.level;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Audio {

    Clip menuMusic;
   //  Clip playerMove;
   //  Clip playerSelect;
   //  Clip placeBomb;
   //  Clip bombExplode;
   //  Clip victory;
   //  Clip gameSong;
     int useless;

    public Audio(int num){
        useless = num;
    }
   
    //public static AudioInputStream in1 = AudioSystem.getAudioInputStream(new File("res/sound/menuMusic.wav"));
    
   public void playMenu(){
        try{
            AudioInputStream in1 = AudioSystem.getAudioInputStream(new File("res/sound/menuMusic.wav"));
            menuMusic = AudioSystem.getClip();
            menuMusic.open(in1);
            menuMusic.loop(Clip.LOOP_CONTINUOUSLY);
        }
        catch(Exception e){e.printStackTrace();}
    }

    public void stopMenu(){
        menuMusic.stop();
   }

    public static void playMenuMove(){
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
        try{
            AudioInputStream in = AudioSystem.getAudioInputStream(new File("res/sound/entitydie.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(in);
            clip.start();
        }
        catch(Exception e){e.printStackTrace();}
    }

    public static void playPlaceBomb(){
        try{
            AudioInputStream in = AudioSystem.getAudioInputStream(new File("res/sound/bomdrop.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(in);
            clip.start();
        }
        catch(Exception e){e.printStackTrace();}
    }

    public static void playBombExplode(){
        try{//res\sound\BombExplode.wav
            AudioInputStream in = AudioSystem.getAudioInputStream(new File("res/sound/BombExplode.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(in);
            clip.start();
        }
        catch(Exception e){e.printStackTrace();}
    }

    public static void playVictory(){
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
        try{
            AudioInputStream in = AudioSystem.getAudioInputStream(new File("res/sound/soundbomberdie.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(in);
            clip.start();
        }
        catch(Exception e){e.printStackTrace();}
    }
    public static void eat(){
        try{
            AudioInputStream in = AudioSystem.getAudioInputStream(new File("res/sound/eat.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(in);
            clip.start();
        }
        catch(Exception e){e.printStackTrace();}
    }
}


