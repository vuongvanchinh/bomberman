package aca.oop.level;

import aca.oop.exceptions.LoadLevelException;

public interface ILevel {
   
   public void loadLevel(String path) throws LoadLevelException;
}
