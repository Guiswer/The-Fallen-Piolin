package org.example;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;

import static com.almasb.fxgl.dsl.FXGLForKtKt.image;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

public class EnemyComponent extends Component {

    private PhysicsComponent physics;

    //Injetando componentes para gerar animação ao visual (sprite) do jogador
    private AnimatedTexture texture;
    private AnimationChannel animIdle, animWalk, test;

    private int life = 10;

    private boolean tiroEmEspera = false;

    public EnemyComponent() {

        // Definindo o PNG com os quadros (frames) de animação
        Image image = image("whole-espalha-lixot.png");
        // Definindo animação para jogador parado
        animIdle = new AnimationChannel(image, 1, 64, 64, Duration.seconds(1), 0, 0);
        // Definindo animação para jogador andando
        animWalk = new AnimationChannel(image, 6, 64, 64, Duration.seconds(1), 0, 5);

        // Colocando a primeira textura do jogodor ao ser invocado
        // animIdle = parado
        texture = new AnimatedTexture(animWalk);
        // Loop para gerar a animação
        texture.loop();
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(25, 21));
        entity.getViewComponent().addChild(texture);

        // Escala do personagem
        entity.setScaleX(0.8);
        entity.setScaleY(0.8);

    }

    public void tomaDano() {
        System.out.println("Vida do Espalha Lixo: " + life);
        life--;
        if (life <= 0 ) {
            entity.removeFromWorld();
        }
    }

    public void atirar() {
        System.out.println("Espalha lixo atirou!");
        if(!tiroEmEspera) {
            spawn("tiroDoEspalhaLixo");
            tiroEmEspera = true;

            Timer timer = new Timer();
            long delay = 400;
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    tiroEmEspera = false;
                }
            };
            timer.schedule(task, delay);
        }
    }
}
