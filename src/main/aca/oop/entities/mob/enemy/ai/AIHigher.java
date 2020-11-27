package aca.oop.entities.mob.enemy.ai;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;


import aca.oop.Board;
import aca.oop.entities.Entity;
import aca.oop.entities.mob.Player;
import aca.oop.entities.mob.enemy.Enemy;
import aca.oop.level.Coordinates;


public class AIHigher extends AI {
   Player player;
   Enemy e;
   Deque<Integer> direction = new ArrayDeque<Integer>(); 
   Board board;
   boolean found = false; // if found path it will true

   public AIHigher(Player p, Enemy e) {
      this.player = p;
      this.e = e;
      direction = new LinkedList<Integer>();
      board = player.getBoard();
   }


   /** 
    * direction.
      *  __0__
      * |     |
      * 3     1
      * |__2__|
      */
      @Override
      public int calculateDirection() {
         if (!direction.isEmpty()) return direction.removeFirst();
        
         if(player == null || !player.isAlive()) {
            return random.nextInt(4);
         }
         //System.out.println("calculate");
         int xt = e.getXRelateTile();
         int yt = e.getYRelateTile();
         int xtp = player.getXRelateTile();
         int ytp = player.getYRelateTile();

         boolean[][] visited = new boolean[13][31];
         this.found = false;
         //System.out.println("From" + xt + " " + yt +" To " + xtp + " " + ytp);
         depthFSearch(xt, yt, xtp, ytp, visited);
         // for (int i : direction) {
         //    System.out.print(i + " ");
         // }
         // System.out.println();
         if (direction.isEmpty()) return random.nextInt(4);
         return direction.remove();
      }
   
      void depthFSearch(int xt, int yt, int xtp, int ytp, boolean[][] visited) {
         //System.out.println(xt + " " + yt);
         visited[yt][xt] = true;
         //System.out.println(yt + " " + xt);
         if (xt == xtp && yt == ytp) {
            found = true;
         }
         if (found) return;
         boolean selected = false;
         int[] order = new int[4];
         sort(order,xt, yt, xtp, ytp);
         for (int i : order) {
            if (found) {return;}
            if (selected) {
               int t = direction.removeLast(); 
               selected = false;
            }

            Entity t1 = null;
            if (i == 0  && !visited[yt - 1][xt]) { t1 = board.getEntityAt(xt, yt - 1);}
            else if (i == 1 && !visited[yt][xt + 1]) { t1 = board.getEntityAt(xt + 1, yt);}
            else if (i == 2  && !visited[yt + 1][xt]) { t1 = board.getEntityAt(xt, yt + 1);}
            else if (i == 3  && !visited[yt][xt - 1]) { t1 = board.getEntityAt(xt - 1, yt);}

            if (t1 != null && t1.collide(this.e)) {
               direction.addLast(i);
               selected = true;
               //System.out.println("add" + i);
               depthFSearch(t1.getXRelateTile(), t1.getYRelateTile(), xtp, ytp, visited);
            }
         }
      }

      public void sort(int[] arr, int xt, int yt,  int xtp, int ytp) {
         boolean horizontal = xt > xtp;
         boolean vertical = yt > ytp;
         if(horizontal && vertical) {
            arr[0] = 3;
            arr[1] = 0;
            arr[2] = 2;
            arr[3] = 1;
         } else if (horizontal) {
            arr[0] = 3;
            arr[1] = 2;
            arr[2] = 0;
            arr[3] = 1;
         } else if(vertical) {
            arr[0] = 0;
            arr[1] = 1;
            arr[2] = 3;
            arr[3] = 2;
         } else {
            arr[0] = 2;
            arr[1] = 1;
            arr[2] = 3;
            arr[3] = 0;
         }
      } 
      // public static void main(String[] args) {
      //    Deque test = new ArrayDeque<Integer>();
      //    test.add(10);
      //    test.add(20);
      //    System.out.println(test.remove());
      // }
}
