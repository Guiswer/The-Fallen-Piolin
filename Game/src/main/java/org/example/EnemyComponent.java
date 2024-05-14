package org.example;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


import static com.almasb.fxgl.dsl.FXGL.getAppWidth;
import static com.almasb.fxgl.dsl.FXGL.getGameScene;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static org.example.EntityType.DISPARO_INIMIGO;


public class EnemyComponent extends Component {

    private PhysicsComponent physics;

    //Injetando componentes para gerar animação ao visual (sprite) do jogador
    private AnimatedTexture texture;
    private AnimationChannel animIdle, animWalk, test;
    private int life =30;
    private Rectangle barra_de_vida;
    private double escalaDoPersonagem = 0.8;
    private boolean metodoPararFoiUtilizado = false;
    private double guardaUltimaMovimentacao = 0.00;

    private int velocidadeDoPersonagem = 180;

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



        // Definindo imagem de borda para a barra de vida do Espalha Lixo
        Image imagemBarraDeVidaDiretorio = new Image("assets/textures/barra_de_vida_espalha_lixo.png");
        ImageView imagemBarraDeVida = new ImageView(imagemBarraDeVidaDiretorio);
        imagemBarraDeVida.setFitHeight(38);

        imagemBarraDeVida.setX(getAppWidth() - 200);
        imagemBarraDeVida.setY(50);
        imagemBarraDeVida.setPreserveRatio(true);

        getGameScene().addUINode(imagemBarraDeVida);

        // Barra de vida dinâmica
        barra_de_vida = new Rectangle(100, 30, Color.DARKRED);
        barra_de_vida.setX(getAppWidth() - 164);
        barra_de_vida.setY(54);

        getGameScene().addUINode(barra_de_vida);

        // Definindo movimentação aleatória
        tarefaDeMovimentacaoAleatoria = new Timer();
        tarefaDeMovimentacaoAleatoria.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Executando tarefa...");
                movimentacaoAleatoria();
            }
        }, 1000, 2000);
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(25, 21));
        entity.getViewComponent().addChild(texture);

        // Escala do personagem
        entity.setScaleX(escalaDoPersonagem);
        entity.setScaleY(escalaDoPersonagem);

    }

    @Override
    public void onUpdate(double tpf) {

        if (!metodoPararFoiUtilizado) {
            if(guardaUltimaMovimentacao > 0) {
                physics.setVelocityX(velocidadeDoPersonagem);
            } else {
                physics.setVelocityX(-velocidadeDoPersonagem);
            }
        }

    }

    public void tomaDano() {
        System.out.println("Vida do Espalha Lixo: " + life);
        life--;
        barra_de_vida.setWidth(barra_de_vida.getWidth()-3.33);
        if (life <= 0 ) {
            tarefaDeMovimentacaoAleatoria.cancel();
            entity.removeFromWorld();
        }
    }

    public void atirar(Entity entidadeParaAtirar) {
        if(!tiroEmEspera) {
            System.out.println("tiro");
            double direcaoDoProjetil = getEntity().getCenter().getX();;
            double origemDoProjetilEixoY = getEntity().getCenter().getY()- 35;
            double origemDoProjetilEixoX = direcaoDoProjetil;
            double mudaEscalaDaImagemParaDirecaoDoProjetil = 0.5;


            if (getEntity().getPosition().getX() > entidadeParaAtirar.getPosition().getX()) {
                direcaoDoProjetil = -direcaoDoProjetil;
                origemDoProjetilEixoX -= 80;
                mudaEscalaDaImagemParaDirecaoDoProjetil = -0.5;
                getEntity().getComponent(EnemyComponent.class).moveParaEsquerda();
            } else {
                getEntity().getComponent(EnemyComponent.class).moveParaDireita();
            }

            Point2D direction = new Point2D(direcaoDoProjetil, 0);

            entityBuilder()
                    .at(origemDoProjetilEixoX, origemDoProjetilEixoY)
                    .type(DISPARO_INIMIGO)
                    .viewWithBBox("normal_feather.png")
                    .collidable()
                    .with(new ProjectileComponent(direction, 1000))
                    .with(new OffscreenCleanComponent())
                    .scale(1, mudaEscalaDaImagemParaDirecaoDoProjetil)
                    .buildAndAttach();

            tiroEmEspera = true;

            Timer timer = new Timer();
            long delay = 1200;
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
        physics.setVelocityX(-velocidadeDoPersonagem);
        guardaUltimaMovimentacao = -velocidadeDoPersonagem;
    }

    public void moveParaDireita() {
        getEntity().setScaleX(escalaDoPersonagem);
        physics.setVelocityX(velocidadeDoPersonagem);
        guardaUltimaMovimentacao = velocidadeDoPersonagem;
    }

    public void pararPersonagem() {
        metodoPararFoiUtilizado = true;

        Timer timer = new Timer();
        long delay = 400;
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                metodoPararFoiUtilizado = false;
            }
        };
        timer.schedule(task, delay);
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
