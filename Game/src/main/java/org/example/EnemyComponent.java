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



/*
 Classe de componente para lógica do inimigo
 */
public class EnemyComponent extends Component {

    // Injeta componente fisíca
    private PhysicsComponent physics;

    //Injetando componentes para gerar animação ao visual (sprite) do jogador
    private AnimatedTexture texture;
    private AnimationChannel animIdle, animWalk, animTiro;

    // vida e barra de vida
    private int vida =30;
    private Rectangle barra_de_vida;
    private double escalaDoPersonagem = 0.8;

    // propriedade para lidar com o personagem parado
    private boolean metodoPararFoiUtilizado = false;

    // propriedade para definir uma movimentação contínua
    private double guardaUltimaMovimentacao = 0.00;

    private int velocidadeDoPersonagem = 180;

    // Colocar disparo em espera
    private boolean tiroEmEspera = false;

    // Thread - timer para decisão de movimentação aleatória
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

        // Definindo imagem da barra de vida dinâmica
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

    // Método acionado quando houver qualquer sinal de mudança na entidade
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

    // método resposável pela lógica de perca de vida
    public void tomaDano() {
        vida--;
        barra_de_vida.setWidth(barra_de_vida.getWidth()-3.33);
        if (vida <= 0 ) {
            // Remove o inimigo do jogo após ser eliminado
            tarefaDeMovimentacaoAleatoria.cancel();
            entity.removeFromWorld();
        }

        if (vida == 25 || vida == 17 || vida == 15 || vida == 5) {
            FXGL.play("haha.wav");
        }
    }

    // método reponsável pela lógica de disparo do inimigo
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

            // Thread - timer para tirar o tempo de espera
            Timer timer = new Timer();
            long delay = 600;
            TimerTask tarefa = new TimerTask() {
                @Override
                public void run() {
                    tiroEmEspera = false;
                }
            };
            timer.schedule(tarefa, delay);

            if (vida > 15) {
                FXGL.play("fogo.wav");

            } else if(vida == 15) {
                FXGL.play("vaiqueimar.wav");
                FXGL.play("fire_launcher.wav");
            } else {
                FXGL.play("fire_launcher.wav");
            }
        }
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
        } else if (decisaoAleatoriaDeMovimentacao == 1) {
            moveParaEsquerda();
        } else {
            pararPersonagem();
        }
    }
}
