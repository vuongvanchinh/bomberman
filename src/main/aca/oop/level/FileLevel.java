package aca.oop.level;

import java.io.BufferedReader;
//import java.io.File;
import java.io.FileReader;
import java.io.IOException;
// import java.io.InputStreamReader;
// import java.net.URL;
// import java.util.Scanner;
import java.util.StringTokenizer;

import aca.oop.Board;
import aca.oop.Game;
import aca.oop.entities.LayeredEntity;
import aca.oop.entities.Player;
import aca.oop.entities.mob.enemy.Balloom;
import aca.oop.entities.mob.enemy.Doll;
import aca.oop.entities.mob.enemy.Kondoria;
import aca.oop.entities.mob.enemy.Minvo;
import aca.oop.entities.mob.enemy.Oneal;
import aca.oop.entities.tile.Grass;
import aca.oop.entities.tile.Portal;
import aca.oop.entities.tile.Wall;
import aca.oop.entities.tile.destroyable.Brick;
import aca.oop.entities.tile.item.BombItem;
import aca.oop.entities.tile.item.FlameItem;
import aca.oop.entities.tile.item.SpeedItem;
import aca.oop.exceptions.LoadLevelException;
import aca.oop.graphics.Screen;
import aca.oop.graphics.Sprite;

public class FileLevel extends Level {

   public FileLevel(String path, Board board) throws LoadLevelException {
      super(path, board);
   }

   public void loadLevel(String path) throws LoadLevelException {
      try {// C:\Users\Dell\Desktop\bomberman\res\levels\Level1.txt
         System.out.println(path);
         FileReader fr = new FileReader(path);
         BufferedReader in = new BufferedReader(fr);
         String data = in.readLine();
         StringTokenizer tokens = new StringTokenizer(data);
         this.thLevel = Integer.parseInt(tokens.nextToken());
         this.height = Integer.parseInt(tokens.nextToken());
         this.width  = Integer.parseInt(tokens.nextToken());

         this.lineTiles = new String[height];

         for (int i = 0; i < height; ++i) {
            lineTiles[i] = in.readLine().trim();
            System.out.println(lineTiles[i]);
         }
         in.close();
      } catch (IOException e) {
         throw new LoadLevelException("Error loading level" + path, e);
      }



   }

   @Override
   public void createEntities() {
      for (int y = 0; y < getHeight(); y++) {
         for (int x = 0; x < getWidth(); x++) {
            addLevelEntity(lineTiles[y].charAt(x), x, y);
         }
      }
   }

   public void addLevelEntity(char c, int x, int y) {
      int pos = x + y * this.getWidth();
      System.out.println("width" +this.getWidth());
      System.out.println(this.getWidth());
      switch(c) {
         case '#':
            System.out.println("#");
            board.addEntities(pos, new Wall(x, y, Sprite.wall));
            break;
         case 'b':
            LayeredEntity layer = new LayeredEntity(x, y, 
                  new Grass(x, y, Sprite.grass),
                  new Brick(x, y, Sprite.brick)
                  );
            if (!this.board.isItemUsed(x, y, thLevel)) {
               layer.addBeforeTop(new BombItem(x, y, thLevel,  Sprite.powerup_bombs));
            }
            board.addEntities(pos, layer);
            break;
         case 's':
            layer = new LayeredEntity(x, y, 
               new Grass(x ,y, Sprite.grass), 
               new Brick(x ,y, Sprite.brick));

            if (!this.board.isItemUsed(x, y, thLevel)) {
               layer.addBeforeTop(new SpeedItem(x, y, thLevel,  Sprite.powerup_speed));
            }

            board.addEntities(pos, layer);
            break;
         case 'f':
            layer = new LayeredEntity(x, y, 
               new Grass(x ,y, Sprite.grass), 
               new Brick(x ,y, Sprite.brick));

            if (!this.board.isItemUsed(x, y, thLevel)) {
               layer.addBeforeTop(new FlameItem(x, y, thLevel,  Sprite.powerup_flames));
            }
            board.addEntities(pos, layer);
            break;
         case '*':
            board.addEntities(pos, new LayeredEntity(x, y, 
               new Grass(x, y, Sprite.grass),
               new Brick(x, y, Sprite.brick)
            ));
            
            break;
         case 'x':
            board.addEntities(pos, new LayeredEntity(x, y, 
               new Grass(x, y, Sprite.grass),
               new Portal(x, y, this.board, Sprite.portal),
               new Brick(x, y, Sprite.brick)));
            break;
         case ' ':
            board.addEntities(pos, new Grass(x, y, Sprite.grass));
            break;
         case 'p':
            board.addMob(new Player(
               Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
            Screen.setOffset(0,0);
            board.addEntities(pos, new Grass(x, y, Sprite.grass));
            break;
         case '1':
            board.addMob(new Balloom(
               Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
            board.addEntities(pos, new Grass(x, y, Sprite.grass));
            break;
         case '2':
            board.addMob(new Oneal(
               Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
            board.addEntities(pos, new Grass(x, y, Sprite.grass));
         break;
         case '3':
            board.addMob(new Doll(
               Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
               board.addEntities(pos, new Grass(x, y, Sprite.grass));
            break;
         case '4':
         board.addMob(new Minvo(
            Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
            board.addEntities(pos, new Grass(x, y, Sprite.grass));
         break;
         case '5':
            board.addMob(new Kondoria(
               Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
               board.addEntities(pos, new Grass(x, y, Sprite.grass));
            break;
         default:
            board.addEntities(pos, new Grass(x, y, Sprite.grass));
            break;
      }
   }
}
