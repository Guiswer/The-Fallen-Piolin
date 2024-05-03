package org.example;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


import static com.almasb.fxgl.dsl.FXGL.getGameScene;
import static com.almasb.fxgl.dsl.FXGLForKtKt.image;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;


public class EnemyComponent extends Component {

    private PhysicsComponent physics;

    //Injetando componentes para gerar animação ao visual (sprite) do jogador
    private AnimatedTexture texture;
    private AnimationChannel animIdle, animWalk, test;
    private int life = 10;
    private Rectangle barra_de_vida = new Rectangle(100, 30, Color.BLUE);
    private double escalaDoPersonagem = 0.8;
    private boolean tiroEmEspera = false;

    private Timer tarefaDeMovimentacaoAleatoria;

    public EnemyComponent() {

        // Definindo o PNG com os quadros (frames) de animação
        Image image = image("whole-espalha-lixot.png");
        // Definindo animação para jogador parado
        animIdle = new AnimationChannel(image, 6, 64, 64, Duration.seconds(1), 0, 0);
        // Definindo animação para jogador andando
        animWalk = new AnimationChannel(image, 6, 64, 64, Duration.seconds(1), 4, 5);

        // Colocando a primeira textura do jogodor ao ser invocado
        // animIdle = parado
        texture = new AnimatedTexture(animWalk);
        // Loop para gerar a animação
        texture.loop();

        //BARRA DE VIDA
        barra_de_vida.setX(100);
        barra_de_vida.setY(100);
        getGameScene().addUINode(barra_de_vida);


        // Definindo movimentação aleatória
        tarefaDeMovimentacaoAleatoria = new Timer();
        tarefaDeMovimentacaoAleatoria.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Executando tarefa...");
                movimentacaoAleatoria();
            }
        }, 0, 5000);
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(25, 21));
        entity.getViewComponent().addChild(texture);

        // Escala do personagem
        entity.setScaleX(escalaDoPersonagem);
        entity.setScaleY(escalaDoPersonagem);

    }

    public void tomaDano() {
        System.out.println("Vida do Espalha Lixo: " + life);
        life--;
        barra_de_vida.setWidth(barra_de_vida.getWidth()-10);
        if (life <= 0 ) {
            entity.removeFromWorld();
        }
    }

    public void atirar() {
        //System.out.println("Espalha lixo atirou!");
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

    public void moveParaEsquerda() {
        getEntity().setScaleX(-escalaDoPersonagem);
        physics.setVelocityX(-170);
    }

    public void moveParaDireita() {
        getEntity().setScaleX(escalaDoPersonagem);
        physics.setVelocityX(170);
    }

    public void pararPersonagem() {
        physics.setVelocityX(0);
    }

    public void movimentacaoAleatoria() {
        Random random = new Random();

        // Gerar um número inteiro aleatório entre 0 e 2
        int decisaoAleatoriaDeMovimentacao = random.nextInt(2);
        if (decisaoAleatoriaDeMovimentacao == 0) {
            moveParaDireita();
        } else if (decisaoAleatoriaDeMovimentacao == 1) {
            moveParaEsquerda();
        } else {
            pararPersonagem();
        }
    }
}
