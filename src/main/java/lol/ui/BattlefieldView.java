package lol.ui;

import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;

import lol.game.*;

public class BattlefieldView implements TileVisitor
{
  Battlefield battlefield;
  Sprites sprites;
  TilePane tiles;
  Stage stage;
  Scene scene;

  private void initTilePane() {
    tiles = new TilePane();
    tiles.setPrefColumns(battlefield.width());
    tiles.setPrefRows(battlefield.height());
    tiles.setTileAlignment(Pos.CENTER);
  }

  public BattlefieldView(Battlefield battlefield, Stage stage) {
    this.battlefield = battlefield;
    this.stage = stage;
    sprites = new Sprites();
    stage.setTitle("LOL 2D");
  }

  public void update() {
    Platform.runLater(() -> {
      System.out.println("Updating battefield view...");
      initTilePane();
      battlefield.visitFullMap(this);
      scene = new Scene(tiles);
      stage.setScene(scene);});
  }

  ImageView groundView(Battlefield.GroundTile tile) {
    switch(tile) {
      case GRASS: return sprites.grass();
      default: throw new UnsupportedOperationException(
        "Displaying ground tile `" + tile.name() + "` is not yet supported.");
    }
  }

  ImageView championView(Champion champion) {
    String name = champion.name();
    if(name.equals("Archer")) { return sprites.archer(); }
    else if (name.equals("Warrior")) { return sprites.warrior(); }
    else {
      throw new UnsupportedOperationException(
        "Displaying Champion tile `" + name + "` is not yet supported.");
    }
  }

  ImageView nexusView(Nexus nexus) {
    double hpPercentage = (nexus.getCurrentHP())/(double)(nexus.getInitialHP());
    switch(nexus.teamOfNexus()) {
      case Nexus.BLUE:{
        if (hpPercentage<=1.0 && hpPercentage>=0.5){
          return sprites.blueNexus();
        }
        else if(hpPercentage< 0.5 && hpPercentage> 0.0){
          return sprites.blueNexusOnfire();
        }
        else if(hpPercentage == 0.0){
          return sprites.blueNexusDestroyed();
        }
      }
      case Nexus.RED:{
        if (hpPercentage<=1.0 && hpPercentage>=0.5){
          return sprites.redNexus();
        }
        else if(hpPercentage< 0.5 && hpPercentage> 0.0){
          return sprites.redNexusOnfire();
        }
        else if(hpPercentage == 0.0){
          return sprites.redNexusDestroyed();
        }
      }
      default: throw new RuntimeException("Unsupported Nexus color");
    }
  }

  @Override public void visitGround(Battlefield.GroundTile tile, int x, int y) {
    tiles.getChildren().add(groundView(tile));
  }

  private void displayDestructible(Destructible d, Node dView) {
    StackPane stack = new StackPane();
    stack.getChildren().add(groundView(battlefield.groundAt(d.x(), d.y())));
    stack.getChildren().add(dView);
    displayHealthBar(d,stack);
    tiles.getChildren().add(stack);
  }

  private void displayHealthBar(Destructible d,StackPane s) {
    s.setAlignment(Pos.BOTTOM_CENTER);
    ProgressBar healthBar = new ProgressBar(1);
    double hpPercentage = (d.getCurrentHP())/(double)(d.getInitialHP());
    if( hpPercentage <= 1.0 && hpPercentage >= 0.75 ) {
      healthBar.setStyle("-fx-accent: green");
    }
    else if( hpPercentage < 0.75 && hpPercentage >= 0.5 ) {
      healthBar.setStyle("-fx-accent: yellow");
    }
    else if( hpPercentage < 0.5 && hpPercentage >= 0.25 ) {
      healthBar.setStyle("-fx-accent: orange");
    }
    else {
      healthBar.setStyle("-fx-accent: red");
    }
    healthBar.setProgress(hpPercentage);
    s.getChildren().add(healthBar);
  }

  @Override public void visitChampion(Champion champion) {
    displayDestructible(champion, championView(champion));
  }

  @Override public void visitNexus(Nexus nexus) {
    displayDestructible(nexus, nexusView(nexus));
  }
}
