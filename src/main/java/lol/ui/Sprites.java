package lol.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sprites {
  private Image grassImage;
  private Image archerImage;
  private Image warriorImage;
  private Image blueNexusImage;
  private Image redNexusImage;
  private Image blueNexusOnfire;
  private Image redNexusOnfire;
  private Image redNexusDestroyed;
  private Image blueNexusDestroyed;

  public Sprites() {
    grassImage = new Image("sprites/grass-tile.png");
    archerImage = new Image("sprites/archer.png");
    warriorImage = new Image("sprites/warrior.png");
    blueNexusImage = new Image("sprites/blue-nexus.png");
    redNexusImage = new Image("sprites/red-nexus.png");
    redNexusOnfire = new Image("sprites/red-nexus-onfire.png");
    blueNexusOnfire = new Image("sprites/blue-nexus-onfire.png");
    redNexusDestroyed = new Image("sprites/red-nexus-destroyed.png");
    blueNexusDestroyed = new Image("sprites/blue-nexus-destroyed.png");
  }

  private ImageView makeView(Image image) {
    ImageView view = new ImageView();
    view.setImage(image);
    return view;
  }

  public ImageView grass() {
    return makeView(grassImage);
  }

  public ImageView archer() {
    return makeView(archerImage);
  }

  public ImageView warrior() {
    return makeView(warriorImage);
  }

  public ImageView blueNexus() {
    return makeView(blueNexusImage);
  }

  public ImageView redNexus() {
    return makeView(redNexusImage);
  }

  public ImageView blueNexusOnfire(){
    return makeView(blueNexusOnfire);
  }

  public ImageView redNexusOnfire(){
    return makeView(redNexusOnfire);
  }

  public ImageView blueNexusDestroyed(){
    return makeView(blueNexusDestroyed);
  }

  public ImageView redNexusDestroyed(){
    return makeView(redNexusDestroyed);
  }
}