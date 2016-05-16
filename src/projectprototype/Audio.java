/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectprototype;

import java.net.URL;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author juven1996
 */
public class Audio {

    protected MediaPlayer mediaPlayer;
    protected Media media;

    public Audio() {

    }

    public Audio(String url) {
        URL resource = getClass().getResource(url);
        media = new Media(resource.toString());
        mediaPlayer = new MediaPlayer(media);
    }
    
    protected void play(){
        mediaPlayer.play();
    }
    
    protected void stop(){
        mediaPlayer.stop();
    }
    
    protected void setVolume(double vol){
        mediaPlayer.setVolume(vol);
    }
    
}
