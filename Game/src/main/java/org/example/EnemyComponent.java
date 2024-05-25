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
import org.example.utilitarios.FimDeJogo;

import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


import static com.almasb.fxgl.dsl.FXGL.getAppWidth;
import static com.almasb.fxgl.dsl.FXGL.getGameScene;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static org.example.EntityType.DISPARO_INIMIGO;


public class EnemyComponent extends Component {

    // Atributos
    int x = 0;

    private PhysicsComponent physics;

    //Injetando componentes para gerar animação ao visual (sprite) do jogador
    private AnimatedTexture texture;
    private AnimationChannel animIdle, animWalk, animTiro;

    private int life = 30;

    // Definindo a barra de vida
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

        // Definindo animação para inimigo parado
        animIdle = new AnimationChannel(image, 6, 64, 64, Duration.seconds(1), 0, 0);

        // Definindo animação para inimigo andando
        animWalk = new AnimationChannel(image, 6, 64, 64, Duration.seconds(1), 4, 5);

        // Definindo animação para inimigo atirando
        animTiro = new AnimationChannel(image, 6, 64, 64, Duration.seconds(1.25), 2, 3);


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
                movimentacaoAleatoria();
            }
        }, 500, 1500);
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
        life--;
        barra_de_vida.setWidth(barra_de_vida.getWidth()-3.33);
        if (life <= 0 ) {
            tarefaDeMovimentacaoAleatoria.cancel();
            entity.removeFromWorld();
            FimDeJogo.terminarWinner();
        }

        if (life == 4 || life == 15 || life == 22) {
            FXGL.play("haha.wav");
        }
    }

    public void atirar(Entity entidadeParaAtirar) {
        if(!tiroEmEspera) {
            double direcaoDoProjetil = getEntity().getCenter().getX();;
            double origemDoProjetilEixoY = getEntity().getCenter().getY() - 25;
            double origemDoProjetilEixoX = direcaoDoProjetil;
            double mudaEscalaDaImagemParaDirecaoDoProjetil = 1;


            if (getEntity().getPosition().getX() > entidadeParaAtirar.getPosition().getX()) {
                origemDoProjetilEixoX -= 40;
                direcaoDoProjetil = -direcaoDoProjetil;

                mudaEscalaDaImagemParaDirecaoDoProjetil = -1;
                getEntity().getComponent(EnemyComponent.class).moveParaEsquerda();
            } else {
                getEntity().getComponent(EnemyComponent.class).moveParaDireita();
            }

            Image image = image("tiro_de_fogo.png");
            AnimationChannel animacaoTiro = new AnimationChannel(image, 4, 32, 32, Duration.seconds(0.1), 0, 3);

            AnimatedTexture visualAnimadoTiro = new AnimatedTexture(animacaoTiro);

            Point2D direction = new Point2D(direcaoDoProjetil, 0);

            entityBuilder()
                    .at(origemDoProjetilEixoX, origemDoProjetilEixoY)
                    .type(DISPARO_INIMIGO)
                    .viewWithBBox(visualAnimadoTiro)
                    .collidable()
                    .with(new ProjectileComponent(direction, 500))
                    .with(new OffscreenCleanComponent())
                    .scale(1, mudaEscalaDaImagemParaDirecaoDoProjetil)
                    .buildAndAttach();

            texture.loopAnimationChannel(animTiro);

            tiroEmEspera = true;

            Timer timer = new Timer();
            long delay = 600;

            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    tiroEmEspera = false;
                }
            };

            timer.schedule(task, delay);
        }

        if (x == 3 || x == 6){
            FXGL.play("fogo.wav");
        }

        else if (x == 2 || x == 5){
            FXGL.play("fire_launcher.wav");

            if (x > 6){
                x = 0;
            }
        }

        else if (life == 8 || life == 18 || life == 28){
            FXGL.play("vaiqueimar.wav");
        }

        x++;
    }

    public void moveParaEsquerda() {
        texture.loopAnimationChannel(animWalk);
        getEntity().setScaleX(-escalaDoPersonagem);
        physics.setVelocityX(-velocidadeDoPersonagem);
        guardaUltimaMovimentacao = -velocidadeDoPersonagem;
    }

    public void moveParaDireita() {
        texture.loopAnimationChannel(animWalk);
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
        }

        else if (decisaoAleatoriaDeMovimentacao == 1) {
            moveParaEsquerda();
        }

        else {
            pararPersonagem();
        }
    }
}